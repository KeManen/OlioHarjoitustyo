package com.harkka.livegreen.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.harkka.livegreen.R;
import com.harkka.livegreen.user.UserManager;

import java.util.UUID;

import static com.harkka.livegreen.user.UserManager.*;

public class HomeFragment extends Fragment {

    // Variables for user management
    UserManager uManager = UserManager.getInstance(); // Singleton for User class usage

    // Variables for test purposes Todo: Remove these when not needed anymore (jka)
    Button testButton;
    int testInt = 0;

    private HomeViewModel homeViewModel;

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

    // Todo: remove after test use
    public void pushTestButton(View v) {
        UUID uGuid;

        testButton = v.findViewById(R.id.buttonTest);
        testInt++;
        if (testInt % 2 == 0) {
            testButton.setText("Test Button");
        } else
            testButton.setText("Clicked");

        uGuid = uManager.createUser(); // New user creation
        System.out.println("Home F: " + uGuid);

        uManager.getUser(uGuid); // To be used for fetching existing user by guid

        uManager.setUserProfile(uGuid, "TestUname", "testFname", "testLname"); // Set user profile data by guid

        uManager.createUserProfile(uGuid); // Empty

        uManager.getUserProfile(uGuid);

    }

}