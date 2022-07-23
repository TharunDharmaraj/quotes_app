package com.example.quotes_app.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quotes_app.R;
import com.example.quotes_app.fragments.Homepage;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @author tharu
 */
public class Login extends AppCompatActivity {
    Button signUpButton, loginButton;
    TextInputLayout phoneNumber, passwrd;
    TextView forgotPasswordField;
    String mailId, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        phoneNumber = findViewById(R.id.phone_number_field);
        passwrd = findViewById(R.id.password_login_field);
        signUpButton = findViewById(R.id.signup_button);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordField = findViewById(R.id.login_forgot_password);


        forgotPasswordField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

        loginButton.setOnClickListener(v -> {
            mailId = phoneNumber.getEditText().getText().toString() + "@gmail.com";
            password = passwrd.getEditText().getText().toString();
            if (mailId.isEmpty() || password.isEmpty()) {
                showMessage("Please Verify All Field");
            } else {
                signIn(mailId, password);
            }
        });
        signUpButton.setOnClickListener(v -> startActivity(new Intent(Login.this, SignUp.class)));
    }


    private void signIn(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail, password).
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Login successful!!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this, Homepage.class);
                        i.putExtra("username_key", "ok");
                        i.putExtra("password_key", password);
                        i.putExtra("mail_key", mailId);
                        startActivity(i);
                    } else {
                        showMessage("Login Failed!!!");
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}