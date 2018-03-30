package com.next.groupmeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class resetPassword extends AppCompatActivity {
    private FirebaseAuth  mAuth;
    private EditText rEmailEditText;
    Button sendEmailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        this.mAuth = FirebaseAuth.getInstance();
        rEmailEditText = (EditText) findViewById(R.id.resetEmail);
        sendEmailButton=(Button)findViewById(R.id.Send);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = rEmailEditText.getText().toString().trim();
                sendingEmail(email);

            }
        });

    }
    private void sendingEmail(String email){
        if (!isEmailValid(email))
        {
            Toast.makeText(getApplicationContext(), getString(R.string.error_invalid_email), Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(resetPassword.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Fail to send email", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }
}
