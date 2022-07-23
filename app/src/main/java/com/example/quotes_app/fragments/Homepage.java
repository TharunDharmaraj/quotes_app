package com.example.quotes_app.fragments;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.quotes_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author tharu
 */
public class Homepage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BottomNavigationView navigationView;
    private String mailId, username, password;
    private FirebaseAuth mAuth;
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.home_button:
                    HomeFragment fragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.categories_button:
                    CategoriesFragment catFrag = new CategoriesFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, catFrag, "");
                    fragmentTransaction1.commit();
                    return true;

                case R.id.favourites_button:
                    FavouritesFragment favFrag = new FavouritesFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, favFrag, "");
                    fragmentTransaction2.commit();
                    return true;

                case R.id.myrewards_button:
                    MyRewardsFragment myRewardsFrag = new MyRewardsFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content, myRewardsFrag, "");
                    fragmentTransaction3.commit();
                    return true;

                case R.id.settings_button:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content, settingsFragment, "");
                    fragmentTransaction4.commit();
                    return true;

                default:
                    return false;
            }
        }
    };

    public Homepage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        navigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);


        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();

        TextView answerText = (TextView) findViewById(R.id.extra2);
        Button signOut = (Button) findViewById(R.id.signout_button);
//        Intent i = getIntent();
//        mailId = i.getStringExtra("mail_key").toString();
//        username = i.getStringExtra("username_key").toString();
//        password = i.getStringExtra("password_key").toString();
//        answerText.setText(mailId + "\n" + username + "\n" + password);

//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.signout_button) {
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(Homepage.this, Login.class));
//                    finish();
//                }
//            }
//        });
    }
}