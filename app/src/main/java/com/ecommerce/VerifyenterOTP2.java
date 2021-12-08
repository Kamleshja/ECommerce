package com.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyenterOTP2 extends AppCompatActivity {

    EditText inputnum1,inputnum2,inputnum3,inputnum4,inputnum5,inputnum6;
    TextView textView;
    Button verifyotp;
    ProgressBar pbverifyotp;

    String getotpbackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyenter_otp2);

        verifyotp = findViewById(R.id.buttonOTP);
        inputnum1 = findViewById(R.id.inputotp1);
        inputnum2 = findViewById(R.id.inputotp2);
        inputnum3 = findViewById(R.id.inputotp3);
        inputnum4 = findViewById(R.id.inputotp4);
        inputnum5 = findViewById(R.id.inputotp5);
        inputnum6 = findViewById(R.id.inputotp6);
        pbverifyotp = findViewById(R.id.verifyotppb);


        textView = findViewById(R.id.txtmobileshownumber);
        textView.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        getotpbackend = getIntent().getStringExtra("backendotp");

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!inputnum1.getText().toString().trim().isEmpty() && !inputnum2.getText().toString().trim().isEmpty() && !inputnum3.getText().toString().trim().isEmpty() && !inputnum4.getText().toString().trim().isEmpty() && !inputnum5.getText().toString().trim().isEmpty() && !inputnum6.getText().toString().trim().isEmpty() ){
                    String enterCodeOtp = inputnum1.getText().toString()+
                            inputnum2.getText().toString()+
                            inputnum3.getText().toString()+
                            inputnum4.getText().toString()+
                            inputnum5.getText().toString()+
                            inputnum6.getText().toString();

                    if(getotpbackend != null){
                        pbverifyotp.setVisibility(View.VISIBLE);
                        verifyotp.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend,enterCodeOtp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pbverifyotp.setVisibility(View.GONE);
                                verifyotp.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){
                                    Intent intent = new Intent(VerifyenterOTP2.this,Dashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    //Toast.makeText(VerifyenterOTP2.this,"OTP Verified",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(VerifyenterOTP2.this,"Enter the correct OTP",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(VerifyenterOTP2.this,"Please check internet connection",Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(VerifyenterOTP2.this,"otp verfiy",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(VerifyenterOTP2.this,"Please enter all number",Toast.LENGTH_LONG).show();
                }
            }
        });
           numberotpmove();

           // Resend OTP Code
        // Using direct findViewById because we are not going to use it in future

//           findViewById(R.id.textresendotp).setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//
//               }
//           });

           TextView resendlabel = findViewById(R.id.textresendotp);
           resendlabel.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           "+91" + getIntent().getStringExtra("mobile"),
                           Long.parseLong("60"), TimeUnit.SECONDS,
                           VerifyenterOTP2.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                               @Override
                               public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                               }

                               @Override
                               public void onVerificationFailed(@NonNull FirebaseException e) {

                                   Toast.makeText(VerifyenterOTP2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                               }

                               @Override
                               public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                   getotpbackend = newbackendotp;
                                   Toast.makeText(VerifyenterOTP2.this,"OTP sended successfully",Toast.LENGTH_LONG).show();
                               }
                           }
                   );
               }
           });
    }

    private void numberotpmove() {
        inputnum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  if(!charSequence.toString().trim().isEmpty()){
                      inputnum2.requestFocus();
                  }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputnum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputnum4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputnum5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputnum6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
