package com.next.groupmeal;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    //Reference the UI

    private  FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;
    Button loginButton;
    private TextView ErrorTextView;
    private TextView loginTextView;
    private TextView resetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginbuton);
        ErrorTextView = (TextView) findViewById(R.id.errotext);
        loginTextView = (TextView) findViewById(R.id.signupTextView);
        resetTextView = (TextView) findViewById(R.id.resetPasswordTextView);

        mAuth = FirebaseAuth.getInstance();

        //LoginUserWithEmailAndPasswrod() method perform the login authentication

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUserWithEmailAndPasswrod();
            }
        });


        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);


            }
        });

        //Reset password
        resetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, resetPassword.class);
                startActivity(intent);
            }
        });


    }

    private void LoginUserWithEmailAndPasswrod()
    {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        //Check the validity of the email and the password
        if (!isEmailValid(email))
        {
            mEmail.setError(getString(R.string.error_invalid_email));
            mEmail.requestFocus();
            return;
        }

        if (!isPasswordValid(password))
        {
            mPassword.setError(getString(R.string.error_invalid_password));
            mPassword.requestFocus();
            return;
        }

        //Check if the user did not forget to enter the password of the email
        if (email.isEmpty())
        {
            mEmail.setError(getString(R.string.error_field_required));
            mEmail.requestFocus();
            return;
        }



        if (password.isEmpty())
        {
            mPassword.setError(getString(R.string.error_field_required));
            mPassword.requestFocus();
            return;
        }

        //if every is correct then perform the authentication

		SharedPreferences pf = getSharedPreferences("fs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pf.edit();
        edit.putString("username", email);
        edit.putString("password", password);
        edit.apply();


        
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                else
                {
                    ErrorTextView.setText(getString(R.string.errortext));
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //isEmailValid() method check if the email is in the valid format



    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    //isPasswordValid() method check the length of the password
    private boolean isPasswordValid(String password)
    {
        return password.length() >= 6;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

