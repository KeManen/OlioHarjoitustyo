package com.harkka.livegreen.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.harkka.livegreen.R;
import com.harkka.livegreen.user.UserManager;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.harkka.livegreen.user.UserManager.*;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Slider sliderMeat;
    private Slider sliderDairy;
    private Slider sliderVege;
    private Switch ecofriendlySwitch;
    private Button buttonSubmit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        textView.setText(s);
                    }
                });

        sliderMeat = root.findViewById(R.id.sliderMeat);
        sliderDairy = root.findViewById(R.id.sliderDairy);
        sliderVege = root.findViewById(R.id.sliderVege);
        ecofriendlySwitch = root.findViewById(R.id.ecofriendlySwitch);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);


        sliderMeat.setLabelFormatter(new LabelFormatter() {
                    @NonNull
                    @Override
                    public String getFormattedValue(float value) {
                        return Math.round(260*(value/100))+"g";
                    }
                }
        );
        sliderDairy.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return Math.round(440*(value/100))+"g";
            }
        });
        sliderVege.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return Math.round(585*(value/100))+"g";
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Button is working");
                String ecoFriendlyFood;
                double baseValue = 0;

                // Check whether to use EcoFriendly food values
                if (ecofriendlySwitch.isChecked()) {
                    ecoFriendlyFood = "lowCarbonPreference=true&query";
                    baseValue = 23.39 + 31.68 + 323.15;
                } else {
                    ecoFriendlyFood = "lowCarbonPreference=false&query";
                    baseValue = 24.62 + 33.35 + 340.15;
                }

                // TODO values from sliders here --> 0% - 200%
                float firstInput = sliderMeat.getValue();
                String firstInput2 = String.format("%.0f", firstInput);
                float secondInput = sliderDairy.getValue();
                String secondInput2 = String.format("%.0f", secondInput);
                float thirdInput = sliderVege.getValue();
                String thirdInput2 = String.format("%.0f", thirdInput);
                // Finnish daily average consumptions are:
                // meat - 260g, dairy - 440g, vege - 585g
                // https://www.luke.fi/uutinen/mita-suomessa-syotiin-vuonna-2019/

                System.out.println(firstInput2);
                System.out.println(secondInput2);
                System.out.println(thirdInput2);

                try {
                    String meatInput = firstInput2;
                    String dairyInput = secondInput2;
                    String vegeInput = thirdInput2;

                    // get the document builder
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

                    // meat = chicken + beef + pork
                    // dairy = eggs, milk etc.
                    // vege = fruits, cheese, grain
                    String urlString = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/" +
                            "v1/FoodCalculator?query.diet=omnivore&query."+ ecoFriendlyFood +".beefLevel="+ meatInput +
                            "&query.fishLevel="+ meatInput +"&query." + "porkPoultryLevel="+ meatInput +"&query.dairyLevel="
                            + dairyInput +"&query.cheeseLevel="+ dairyInput + "&query.riceLevel="+ vegeInput +
                            "&query.eggLevel=" + dairyInput + "&query.winterSaladLevel="+vegeInput;

                    System.out.println(urlString);

                    // get document, parse and normalize it
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(urlString).addHeader("Accept", "text/xml").build();
                    Response response = client.newCall(request).execute();
                    Document doc = builder.parse(response.body().byteStream());
                    doc.getDocumentElement().normalize();

                    // get the wanted data from document
                    String dairy = doc.getElementsByTagName("Dairy").item(0).getTextContent();
                    String meat = doc.getElementsByTagName("Meat").item(0).getTextContent();
                    String vege = doc.getElementsByTagName("Plant").item(0).getTextContent();

                    // here format the values to floats and get the total consumption
                    // decrease the site default values from new values
                    double total = 0;
                    double dairy2 = Double.parseDouble(dairy);
                    double meat2 = Double.parseDouble(meat);
                    double vege2 = Double.parseDouble(vege);

                    try {
                        total = dairy2 + meat2 + vege2;
                        //         System.out.println(total +" total value is here before decreasing sites defaults");
                        total = total - baseValue;
                        //          System.out.println(total +" total value without sites year defaults");
                    } catch (NumberFormatException e) {
                        System.out.println("Could not parse");
                    }

                    total = (total / 365);
                    System.out.println("This is the correct value of today's inputs " + total + "kg without formatting");
                    String result = String.format("%.2f", total);
                    System.out.println("This is the correct value of today's inputs " + result + "kg of CO2");
                    // TODO add data to database

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("At the end");
                }

            }
        });

        return root;
    }

}