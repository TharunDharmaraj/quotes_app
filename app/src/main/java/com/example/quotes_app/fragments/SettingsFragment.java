package com.example.quotes_app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.quotes_app.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author tharu
 */
public class SettingsFragment extends Fragment {
    RelativeLayout themeMode, signOut, language, profile, log_info;
    private boolean themeBoolean;


    public SettingsFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        themeMode = view.findViewById(R.id.change_theme_menu);
        signOut = view.findViewById(R.id.logout_menu);
        language = view.findViewById(R.id.language_menu);
        profile = view.findViewById(R.id.my_profile_menu);
        log_info = view.findViewById(R.id.log_info_menu);

        TextView tv = view.findViewById(R.id.theme_mode);
        themeMode.setOnClickListener(v -> {
            int nightModeFlags = view.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    themeBoolean = false;
                    break;

                case Configuration.UI_MODE_NIGHT_NO:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    themeBoolean = true;
                    break;

                default:
                    break;
            }
            SharedPreferences mPrefs = this.getActivity().getSharedPreferences("THEME", 0);
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putBoolean("theme_boolean", themeBoolean).apply();
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), com.example.quotes_app.Authentication.Login.class);
                startActivity(i);
                getActivity().finish();
                Toast.makeText(getContext(), "Signed Out", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
