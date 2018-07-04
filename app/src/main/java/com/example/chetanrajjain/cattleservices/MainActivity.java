package com.example.chetanrajjain.cattleservices;

import android.app.Activity;
import android.app.Fragment;
import android.app.LauncherActivity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements BlankFragment.sendCode{
    private Button generateOTP;
    private EditText phonenumber;
    private TextView text;
    private String phoneVerificationId;
    private String number;
    private android.support.v4.app.Fragment fragment;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

     static FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateOTP = findViewById(R.id.generate_otp_button);
        text = findViewById(R.id.textview);
        phonenumber = findViewById(R.id.phone_number);

        fbAuth = FirebaseAuth.getInstance();

        generateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = phonenumber.getText().toString();
                PhoneAuthCallbacks();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60,TimeUnit.SECONDS,MainActivity.this,verificationCallbacks);
            }
        });



    }

    private void PhoneAuthCallbacks() {

        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.i("verID",s);
                phoneVerificationId = s;
                fragment = new BlankFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.main_layout,fragment,"enter otp").commit();

            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Log.i("v c",phoneAuthCredential.getSmsCode());

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("failed",e.getMessage());
            }
        };
    }


    @Override
    public void sendCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId,code);
        fbAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //fragment.onDestroy();
                Intent intent = new Intent(MainActivity.this,User.class);
                intent.putExtra("Phone number",task.getResult().getUser().getPhoneNumber());
                Log.i("phone number",task.getResult().getUser().getPhoneNumber());
                    startActivity(intent);
                    Log.i("co","com");


            }
        });
        //Log.i("credentials",credential.getSmsCode());

    }
    public static void signOut(View view){
        Log.i("so","so");
        fbAuth.signOut();

    }
}
