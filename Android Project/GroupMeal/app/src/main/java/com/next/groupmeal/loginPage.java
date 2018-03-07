package com.next.groupmeal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.*;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class loginPage extends AppCompatActivity implements View.OnClickListener{

    //Declare the variable

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginbtn;
    private TextView SignUpTextView;
    private static final String TAG = "GroupMeal";
    private TextView ErrorTextView;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Initialize the variable
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        ErrorTextView = (TextView) findViewById(R.id.errorTextView);

        findViewById(R.id.signUptextView).setOnClickListener(this);
        findViewById(R.id.loginbtn).setOnClickListener(this);

    }
    @Override
    public void onStart(){
        super.onStart();
        checkInterned();

    }

    //Perform the user login method

    private void LoginUser()
    {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        //Validate the input value from the user
        if(email.isEmpty()){
            mEmail.setError("This field is required");
            mEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please enter the valid email");
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            mPassword.setError("This field is required");
            mPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //If the user successfully login launch the home page
                if (task.isSuccessful()){
                    startActivity(new Intent(loginPage.this, HomePage.class));
                }else{
                    ErrorTextView.setText("Error: email and/password is incorrect");
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


        //Check the internect connection

    private boolean isInternetAvailabe(){


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;

        return (activeNetwork != null) && activeNetwork.isConnectedOrConnecting();




    }

    //Check the connectivity

    @SuppressLint("SetTextI18n")
    private void checkInterned()
    {
        if(!isInternetAvailabe()){

            ErrorTextView.setText("No Internet Connection");
        }
    }



    //Launch the clickable button/text
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signUptextView:
                startActivity(new Intent(loginPage.this, SignUpActivity.class));
                break;

            case R.id.loginbtn:
                LoginUser();
                break;

        }

    }


}
