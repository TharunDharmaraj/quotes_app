package com.example.quotes_app.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.quotes_app.R;

/**
 * @author tharu
 */
public class SettingsFragment extends Fragment {
    RelativeLayout themeMode;
    private boolean themeBoolean;


    public SettingsFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        themeMode = view.findViewById(R.id.change_theme_menu);
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
        return view;
    }
}
