package com.harkka.livegreen.ui.testi;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.harkka.livegreen.R;
import com.harkka.livegreen.entry.Entry;
import com.harkka.livegreen.entry.EntryManager;
import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.user.UserManager;

import java.util.UUID;

public class TestFragment extends Fragment {
    //Todo: remove if not really needed
    enum EntryType {WEIGHT, HEIGHT, DAIRY, MEAT, VEGE, OTHER};

    // Variables for user management
    UserManager uManager = UserManager.getInstance(); // Singleton for User class usage

    // Todo: Entrymanager test code 1
    // Variables for entry management
    EntryManager entryManager = EntryManager.getInstance(); // Singleton for Entry class usage


    // Variables for test purposes Todo: Remove these when not needed anymore (jka)
    Button testButton;
    int testInt = 0;

    private TestViewModel mViewModel;

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_fragment, container, false);

        // Todo: remove after test use
        Button testButton = (Button) root.findViewById(R.id.buttonTest);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root){
                pushTestButton(root);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        // TODO: Use the ViewModel
    }

    // Todo: remove after test use
    public void pushTestButton(View v) {
        UUID uGuid = null;

        testButton = v.findViewById(R.id.buttonTest);
        testInt++;
        if (testInt % 2 == 0) {
            testButton.setText("Test Button");
        } else
            testButton.setText("Clicked");


        uGuid = uManager.createUser(); // New user creation
        System.out.println("Home F: " + uGuid);

        if (uManager.user.getUserIsLogged()) {
            // To be used for fetching existing user by guid
            //uManager.getUser(uGuid);

            //uManager.setUserProfile(uGuid, "TestFname", "testLname", testInt, "Stadi"); // Set user profile data by guid

            //uManager.createUserProfile(uGuid); // Empty

            //uManager.getUserProfile(uGuid);
 // // Todo: Entrymanager test code 2
            Entry entry = entryManager.createEntry(uGuid);
            System.out.println("In Test Fragment: " + entry.toString() + " " + entryManager.entry.getEntryDateTime());

            float value = (float).34566;
            for (int i = 0; i < 5; i++)
                entryManager.setEntryValue(i, value);

        }
    }
}