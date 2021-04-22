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

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.harkka.livegreen.R;
import com.harkka.livegreen.user.UserManager;

import java.util.UUID;

import static com.harkka.livegreen.user.UserManager.*;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Slider sliderMeat;
    private Slider sliderDairy;
    private Slider sliderVege;

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

        return root;
    }

}