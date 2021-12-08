package com.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginOTP extends AppCompatActivity {

    EditText enternumber;
    Button getotp;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enternumber = findViewById(R.id.mobile_number);
        getotp = findViewById(R.id.buttonOTP);
        pb = findViewById(R.id.getotppb);

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enternumber.getText().toString().trim().isEmpty()){
                    if((enternumber.getText().toString().trim()).length() == 10){
                        pb.setVisibility(View.VISIBLE);
                        getotp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(),
                                Long.parseLong("60"), TimeUnit.SECONDS,
                                LoginOTP.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        pb.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        pb.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                        Toast.makeText(LoginOTP.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        pb.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(LoginOTP.this, VerifyenterOTP2.class);
                                        intent.putExtra("mobile",enternumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }else {
                        Toast.makeText(LoginOTP.this,"Please enter corrent number",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LoginOTP.this,"Enter mobile number",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}