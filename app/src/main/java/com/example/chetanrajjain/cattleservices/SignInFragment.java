package com.example.chetanrajjain.cattleservices;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.zip.Inflater;

public class SignInFragment extends Fragment {
    private Button btnGenerateOtp, btnSignin;
    private EditText etPhonenumber, etOTP;
    private Activity main_activity;
    private TextView tv;
    private setNumber setNumber;
    public interface setNumber{
        public void getNumber(String number);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sign_in,null);
        btnGenerateOtp = v.findViewById(R.id.generate_otp_button);
        etPhonenumber = v.findViewById(R.id.phone_number);
        tv = v.findViewById(R.id.textview);
        btnGenerateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etPhonenumber.getText().toString();
                if(number.equals("")){
                    tv.setText("Please enter phone number");
                }
                else if((number.length()) < 9){
                    tv.setText("please enter a valid phone number");
                }else{
                        setNumber.getNumber(number);
                }
            }
        });


        return v;
         }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //setNumber = (SignInFragment.setNumber) activity;
    }
}
