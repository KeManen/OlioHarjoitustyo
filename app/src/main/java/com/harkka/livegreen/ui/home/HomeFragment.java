package com.harkka.livegreen.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.harkka.livegreen.R;
import com.harkka.livegreen.entry.Entry;
import com.harkka.livegreen.entry.EntryManager;
import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserManager;
import com.harkka.livegreen.user.UserProfile;

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


public class HomeFragment extends Fragment {

    private Slider sliderMeat;
    private Slider sliderDairy;
    private Slider sliderVege;
    private SwitchMaterial ecofriendlySwitch;
    private Button buttonSubmit;

    // TODO remove when insert to db works
    // Variables for data entity management
    private UserDatabase userDatabase;
    private UserDao userDao;
    private DataDao dataDao;
    private UUID auxGuid;
    private DataEntity[] dataEntities;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //get components
        sliderMeat = root.findViewById(R.id.sliderMeat);
        sliderDairy = root.findViewById(R.id.sliderDairy);
        sliderVege = root.findViewById(R.id.sliderVege);
        ecofriendlySwitch = root.findViewById(R.id.ecofriendlySwitch);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);

        //set labels to be grams, numbers are from national average
        sliderMeat.setLabelFormatter(value -> Math.round(260 * (value / 100)) + "g");
        sliderDairy.setLabelFormatter(value -> Math.round(440 * (value / 100)) + "g");
        sliderVege.setLabelFormatter(value -> Math.round(585 * (value / 100)) + "g");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        buttonSubmit.setOnClickListener(v -> {

            System.out.println("Button is working");
            String ecoFriendlyFood;
            double baseValueMeat = 0;
            double baseValueDairy = 0;
            double baseValueVege = 0;

            // Check whether to use EcoFriendly food values
            if (ecofriendlySwitch.isChecked()) {
                ecoFriendlyFood = "lowCarbonPreference=true&query";
                baseValueMeat = 23.39;
                baseValueDairy = 31.68;
                baseValueVege = 323.15;
            } else {
                ecoFriendlyFood = "lowCarbonPreference=false&query";
                baseValueMeat = 24.62;
                baseValueDairy = 33.35;
                baseValueVege = 340.15;
            }

            // takes values from sliders + format to String to use in URL (0-200)
            String meatInput = String.format("%.0f", sliderMeat.getValue());
            String dairyInput = String.format("%.0f", sliderDairy.getValue());
            String vegeInput = String.format("%.0f", sliderVege.getValue());
            // takes values from sliders to set to database as grams
            String meatGrams = String.format("%.0f", (sliderMeat.getValue() / 100) * 260);
            String dairyGrams = String.format("%.0f", (sliderDairy.getValue() / 100) * 440);
            String vegeGrams = String.format("%.0f", (sliderVege.getValue() / 100) * 585);

            try {

                // get the document builder
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

                // meat = chicken + beef + pork
                // dairy = eggs, milk etc.
                // vege = fruits, cheese, grain
                String urlString = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/" +
                        "v1/FoodCalculator?query.diet=omnivore&query." + ecoFriendlyFood + ".beefLevel=" + meatInput +
                        "&query.fishLevel=" + meatInput + "&query." + "porkPoultryLevel=" + meatInput + "&query.dairyLevel="
                        + dairyInput + "&query.cheeseLevel=" + dairyInput + "&query.riceLevel=" + vegeInput +
                        "&query.eggLevel=" + dairyInput + "&query.winterSaladLevel=" + vegeInput;
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

                // format the CO2 values to doubles and get the total CO2/kg/year
                // decrease the site default values from new values
                double total = 0;
                double dairy2 = Double.parseDouble(dairy);
                double meat2 = Double.parseDouble(meat);
                double vege2 = Double.parseDouble(vege);

                try {
                    total = dairy2 + meat2 + vege2;
                    //         System.out.println(total +" total value is here before decreasing sites defaults");
                    total = total - baseValueMeat - baseValueDairy - baseValueVege;
                    //          System.out.println(total +" total value without sites year defaults");
                } catch (NumberFormatException e) {
                    System.out.println("Could not parse");
                }

                // from year amount to day's value --> to String
                total = (total / 365);
                String result = String.format("%.2f", total);
                System.out.println("This is the correct value of today's inputs " + result + "kg of CO2");


                //TODO remove when insert to db works

                Context context = getContext();
                UserManager um = UserManager.getInstance(getContext());
                EntryManager em = EntryManager.getInstance();
                DataEntity dataEntity = DataEntity.getInstance();
                UserEntity userEntity = UserEntity.getInstance();
                UUID uGuid = um.getCurrentUserUUID();

                // New entry object for data transfer and insert to DB
                Entry entry = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    entry = em.createEntry(uGuid);
                }

                // Entry data insert
                entry.insertDBEntry(); // Prepare Entry object for db insert, copy data to DataEntity
                new Thread(() -> {
                    System.out.println("IN DB Entry***************" + dataEntity.getEntryId().toString() + "************");
                    dataEntity.setTotalResult(result);
                    System.out.println(result);
                    dataEntity.setMeatUsed(meatGrams);
                    System.out.println(meatGrams);
                    dataEntity.setDairyUsed(dairyGrams);
                    System.out.println(dairyGrams);
                    dataEntity.setVegeUsed(vegeGrams);
                    System.out.println(vegeGrams);
                    System.out.println("IN DB Entry***************" + "************");
                    dataDao.insertDataEntities(dataEntity); // Do the thing
                    System.out.println("IN DB Entry***************" + "************");
                }).start();

                // TODO ENDS Here


            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } finally {
                System.out.println("At the end");
            }
        });

        return root;
    }
}