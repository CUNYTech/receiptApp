package com.next.groupmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MenuPage extends AppCompatActivity implements View.OnClickListener {


    private TextView BackArray;
    private TextView LogOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        findViewById(R.id.backToHome).setOnClickListener(this);
        findViewById(R.id.logoutTextView).setOnClickListener(this);
        findViewById(R.id.createGroupTextView).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }


    //Log out the user

    private void LogUserOut()
    {
            mAuth.signOut();
            startActivity(new Intent(MenuPage.this, loginPage.class));
    }

    public void BackToHome(){

        Intent intent = new Intent(MenuPage.this, HomePage.class);
        startActivity(intent);


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()){

                //if the user decided to go back to the home page
            case R.id.backToHome:

                BackToHome();
                break;

                //if the user logout then sent user back to the login page
            case R.id.logoutTextView:
                LogUserOut();
                break;
            case R.id.createGroupTextView:
                startActivity(new Intent(MenuPage.this, Create_Group.class));
                break;
        }

    }
}
