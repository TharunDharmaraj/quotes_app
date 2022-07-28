package com.example.quotes_app.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quotes_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RetypePassword extends AppCompatActivity {

    String password, rePassword;
    private TextInputLayout typePassword, retypePassword;
    private Button changePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retype_password);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);
        typePassword = findViewById(R.id.new_password_field);
        retypePassword = findViewById(R.id.retype_password_field);
        changePassword = findViewById(R.id.change_password_button);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(typePassword.getEditText().getText()) && TextUtils.isEmpty(retypePassword.getEditText().getText())) {
                    Toast.makeText(RetypePassword.this,
                            "Please enter password in both fields", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(retypePassword.getEditText().getText()) && !TextUtils.isEmpty(retypePassword.getEditText().getText())) {
                    password = typePassword.getEditText().getText().toString();
                    Log.d("new password:",password);
                    rePassword = retypePassword.getEditText().getText().toString();
                    if (!password.equals(rePassword)) {
                        Toast.makeText(RetypePassword.this, "Passwords are not same!", Toast.LENGTH_SHORT).show();
                    }
                    if (user != null) {
                        user.updatePassword(password)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RetypePassword.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RetypePassword.this, Login.class));
                                        } else {
                                            Toast.makeText(RetypePassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}