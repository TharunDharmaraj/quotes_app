package com.example.quotes_app.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quotes_app.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author tharu
 */
public class ForgotPassword extends AppCompatActivity {
    TextInputLayout phoneNumberField;
    Button forgotPasswordButton;
    private FirebaseAuth mAuth;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        phoneNumberField = findViewById(R.id.forgot_password_field);
        Intent i = new Intent(ForgotPassword.this, ForgotPasswordVerification.class);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phoneNumberField.getEditText().getText())){
                    Toast.makeText(ForgotPassword.this,
                            "Please enter password", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(phoneNumberField.getEditText().getText())) {
                    phone = "+91" + phoneNumberField.getEditText().getText().toString();
                    i.putExtra("phone_key", phone);
                    Log.d("details", phone);
                    startActivity(i);
                }
            }
        });
    }

}