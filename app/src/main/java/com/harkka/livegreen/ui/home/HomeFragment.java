package com.harkka.livegreen.ui.home;

import android.content.Context;
import android.os.Build;
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
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    // Variables for user management
    UserManager userManager = UserManager.getInstance(getContext()); // Singleton for User class usage
    // Variables for entry management
    EntryManager entryManager = EntryManager.getInstance(); // Singleton for Entry class usage
    UserDatabase userDatabase;
    UserDao userDao;
    DataDao dataDao;
    UserEntity userEntity = UserEntity.getInstance();
    DataEntity dataEntity = DataEntity.getInstance();
    UUID auxGuid;
    UUID entryGuid;
    DataEntity[] dataEntities;

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
                // add slider values to the URL
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
                    total = total - baseValueMeat - baseValueDairy - baseValueVege;
                } catch (NumberFormatException e) {
                    System.out.println("Could not parse");
                }

                // from year amount to day's value --> to String
                total = (total / 365);
                String result = String.format("%.2f", total);
                System.out.println("This is the correct value of today's inputs " + result + "kg of CO2");

                // initialize needed methods
                Context context = this.getContext();
                userDatabase = UserDatabase.getUserDatabase(context.getApplicationContext());
                userDao = userDatabase.userDao();
                dataDao = userDatabase.dataDao();
                String testString = "123 ";
                UUID uGuid = null;
                //uGuid = uManager.createUser().getUserId(); // New user creation
                uGuid = userManager.getCurrentUserUUID();
                System.out.println(testString + ": " + uGuid);

                System.out.println("THIS IS TEST SECTION FOR ENTRY DATA INSERT");

                // THIS SECTION HANDLES DATA ENTRY IN DB --->
                Entry entry = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    entry = entryManager.createEntry(uGuid);
                }
                System.out.println(testString + "***" + entry.getTotalResult() + "****");
                entryManager.setEntry(entry);
                entry = entryManager.getEntry();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    entry = entryManager.createEntry(uGuid);
                }
                entryGuid = entry.getEntryGuid();
                entry.setUserGuid(userManager.getCurrentUserUUID());
                ;
                entry.setEntryGuid(entryGuid);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    entry.setDateTime(LocalDateTime.now());
                }

                entry.setDairyConsumption(Float.parseFloat(dairyGrams));
                entry.setMeatConsumption(Float.parseFloat(meatGrams));
                entry.setVegeConsumption(Float.parseFloat(vegeGrams));
                entry.setTotalResult(Float.parseFloat(result));
                entry.insertDBEntry();
                /*
                System.out.println("111111111111111111111111111111111111111111  " + dairyGrams +"g");
                System.out.println("111111111111111111111111111111111111111111  " + meatGrams +"g");
                System.out.println("111111111111111111111111111111111111111111  " + vegeGrams +"g");
                System.out.println("111111111111111111111111111111111111111111  " + result +"kg of CO2");
                 */

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataDao.insertDataEntity(dataEntity);
                        System.out.println("IN TEST *************** " + DataEntity.getInstance().getEntryId().toString() + " ************");
                        System.out.println("IN TEST *************** " + dataEntity.getTotalResult() + "kg of CO2 ************ TOTAL RESULT");
                    }
                }).start();

                // give thread 1 second to process data transfer
                Toast.makeText(getContext(), "Processing data", Toast.LENGTH_SHORT).show();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Data insert complete", Toast.LENGTH_SHORT).show();

                //TODO ENDS HERE

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