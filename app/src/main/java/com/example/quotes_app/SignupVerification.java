package com.example.quotes_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author tharu
 */
public class SignupVerification extends AppCompatActivity {
    Dialog dialog;
    Button verifyOtp;
    TextView verificationMsg, resendCode;
    String phone, username, password, mailId;
    private PinView pv;
    private FirebaseAuth mAuth;
    private String verificationId;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            // initializing our callbacks for on verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // below method is used when OTP is sent from Firebase
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it contains a unique id which we are storing in our string which we have already created.
            verificationId = s;
        }

        // this method is called when user receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            // checking if the code is null or not.
            if (code != null) {
                // if the code is not null then we are setting that code to our OTP edittext field.
                pv.setText(code);
                // after setting this code to OTP edittext field we are calling our verify code method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(SignupVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_verification);
        pv = findViewById(R.id.verification_number);
        verifyOtp = findViewById(R.id.verify_otp_button);
        verificationMsg = findViewById(R.id.quotes_app_verification_msg);
        resendCode = findViewById(R.id.resend_code);

        Intent i = getIntent();
        phone = i.getStringExtra("phone_key").toString();
        username = i.getStringExtra("username_key").toString();
        password = i.getStringExtra("password_key").toString();
        mailId = phone.substring(3) + "@gmail.com";


        String verMsg = "verification code sent to " + phone + " please enter the 6 digit code to get verified";
        verificationMsg.setText(verMsg);
        dialog = new Dialog(this);
        pv.requestFocus();
        mAuth = FirebaseAuth.getInstance();
        InputMethodManager ipm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ipm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        sendVerificationCode(phone);
        verifyOtp.setOnClickListener(v -> {
            verifyCode(Objects.requireNonNull(pv.getText()).toString());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });
        resendCode.setOnClickListener(v -> {
            new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    resendCode.setText("Resend code in: " + millisUntilFinished / 1000);
                    resendCode.setEnabled(false);
                }

                @Override
                public void onFinish() {
                    resendCode.setText("Resend Code");
                    resendCode.setEnabled(true);
                }
            }.start();
            sendVerificationCode(phone);
            verifyCode(Objects.requireNonNull(pv.getText()).toString());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Toast.makeText(SignupVerification.this, "verification code resent", Toast.LENGTH_SHORT).show();
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        openDialog();
                        // if the code is correct and the task is successful we are sending our user to new activity.
                    } else {
                        // if the code is not correct then we are displaying an error message to the user.
                        Toast.makeText(SignupVerification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openDialog() {
        mAuth
                .createUserWithEmailAndPassword(mailId, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                        "Registration successful!",
                                        Toast.LENGTH_LONG)
                                .show();
                        dialog.setContentView(R.layout.activity_dialog_verification_successful);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView enterPassword = (TextView) dialog.findViewById(R.id.enter_password_btn);
                        enterPassword.setOnClickListener(v -> {
                            Intent i = new Intent(SignupVerification.this, Homepage.class);
                            i.putExtra("username_key", username);
                            i.putExtra("password_key", password);
                            i.putExtra("mail_key", mailId);
                            startActivity(i);
                            finish();
                        });
                        dialog.show();
                    } else {
                        // Registration failed
                        Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later",
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                });

    }

    private void sendVerificationCode(String number) {
        // this method is used for getting OTP on user phone number.
        PhoneAuthOptions options;
        options = PhoneAuthOptions.newBuilder(mAuth)
                // Phone number to verify
                .setPhoneNumber(number)
                // Timeout and unit
                .setTimeout(60L, TimeUnit.SECONDS)
                // Activity (for callback binding)
                .setActivity(this)
                // OnVerificationStateChangedCallbacks
                .setCallbacks(mCallBack)
                // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        try {
            // below line is used for getting credentials from our verification id and code.
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            // after getting credential we are calling sign in method.
            signInWithCredential(credential);
        } catch (Exception e) {
            Toast.makeText(SignupVerification.this, "Wrong Code.. \nPlease Verify2", Toast.LENGTH_SHORT).show();
        }
    }
}
