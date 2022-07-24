package com.example.quotes_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quotes_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    private String mailId, username, password;
    private FirebaseAuth mAuth;

    public HomeFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView answerText = (TextView) view.findViewById(R.id.extra2);
        Button signOut = (Button) view.findViewById(R.id.signout_button);
        Intent i = getActivity().getIntent();
        mailId = i.getStringExtra("mail_key").toString();
        username = i.getStringExtra("username_key").toString();
        password = i.getStringExtra("password_key").toString();
        answerText.setText(mailId + "\n" + username + "\n" + password);
        Log.d("message",mailId + "\n" + username + "\n" + password);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.signout_button) {
                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(HomeFragment.this, Login.class));
//                    finish();
                }
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
