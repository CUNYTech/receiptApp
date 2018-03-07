package com.next.groupmeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private TextView mMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViewById(R.id.listMenu).setOnClickListener(this);
    }

    public void OpenMenu(){

        Intent intent = new Intent(HomePage.this, MenuPage.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_rigth);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
                //if the user click on the listMenu bar, then the menu activity start

            case R.id.listMenu:
               OpenMenu();
                break;
        }

    }
}
