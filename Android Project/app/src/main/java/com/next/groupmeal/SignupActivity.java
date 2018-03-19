package com.next.groupmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    //Declare the variable
    private TextView mLoginTextView;
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignupButton;
    FirebaseAuth mAuth;
    private TextView ErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Assign the variable to UI
        ErrorTextView = (TextView) findViewById(R.id.errotext);
        mSignupButton = (Button) findViewById(R.id.signupbutton);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginTextView = (TextView) findViewById(R.id.loginTextView);
        mAuth = FirebaseAuth.getInstance();

        //perform the action in case the have an account text is clicked
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUpUserWithEmailAndPassword();
            }
        });
    }

    private void SignUpUserWithEmailAndPassword()
    {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (!isEmailValid(email))
        {
            mEmail.setError(getString(R.string.error_invalid_email));
            mEmail.requestFocus();
            return;
        }
        if (email.isEmpty())
        {
            mEmail.setError(getString(R.string.error_field_required));
            mEmail.requestFocus();
            return;
        }
            //Validate the password 's length
        if (!isPasswordValid(password))
        {
            mPassword.setError(getString(R.string.error_invalid_password));
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            mPassword.setError(getString(R.string.error_field_required));
            mPassword.requestFocus();
            return;
        }

        //If everthing is correct then sign the user

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Successffully Sign up", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);

                    //Will be using this later
                    FirebaseUser user = mAuth.getCurrentUser();
                }else{

                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        ErrorTextView.setText(getString(R.string.errorTextView));
                        Log.d("GroupMeal", getString(R.string.errorTextView));
                    }
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
}
