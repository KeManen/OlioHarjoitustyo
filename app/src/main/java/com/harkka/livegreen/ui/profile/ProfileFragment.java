package com.harkka.livegreen.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.harkka.livegreen.MainActivity;
import com.harkka.livegreen.R;
import com.harkka.livegreen.roomdb.LoginActivity;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserManager;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private Button login_button;
    private ImageButton profilePicture;
    private ImageButton ecorankPicture;
    private TextView profileName;
    private CardView card;
    private UserManager userManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        login_button = root.findViewById(R.id.buttonProfileViewLogout);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle_profileview_loginbutton();
            }
        });
        card = root.findViewById(R.id.profileCardView);
        profilePicture = root.findViewById(R.id.imageButtonProfile);
        ecorankPicture = root.findViewById(R.id.imageButtonEcorank);
        profileName = root.findViewById(R.id.textViewProfileName);
        userManager = UserManager.getInstance();
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void handle_profileview_loginbutton(){
        // TODO toggle current login position
        handle_profileview_login_state(false);
    }

    // Change profileview according to loginstate
    public void handle_profileview_login_state(boolean is_logged){
        //login_button = v.findViewById(R.id.buttonProfileViewLogout);
        //card = v.findViewById(R.id.profileCardView);
        Context context = getContext();


        if(userManager.isAnyoneLogged()){
            login_button.setText(getText(R.string.log_out));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.red));

            card.setVisibility(View.VISIBLE);
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            User user = userManager.getCurrentUser();
            switch(user.getRank()){
                case 1:
                    ecorankPicture.setImageResource(R.drawable.eco_1);
                    break;
                case 2:
                    ecorankPicture.setImageResource(R.drawable.eco_2);
                    break;
                case 3:
                    ecorankPicture.setImageResource(R.drawable.eco_3);
                    break;
                case 4:
                    ecorankPicture.setImageResource(R.drawable.eco_4);
                    break;
                case 5:
                    ecorankPicture.setImageResource(R.drawable.eco_5);
                    break;
                default:
                    //TODO create art for unlogged
                    break;
            }

            profileName.setText(user.userName);

            //TODO change to userpicture
            //profilePicture.setImageResource();

        } else {
            login_button.setText(getText(R.string.log_in));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.green_dark));

            card.setVisibility(View.GONE);
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LoginActivity.class));
                }
            });
            // TODO create art for unlogged
            //ecorankPicture.setImageResource(R.drawable.eco_unlogged);
            //profilePicture.setImageResource(R.drawable.profileUnlogged);


            profileName.setText("Profilename");
        }
    }
}