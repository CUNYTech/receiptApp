package com.next.groupmeal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by qiman on 3/21/2018.
 */

public class ReceiptActivity extends AppCompatActivity {

    public static final int CAMERA_PREMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);


        //request permission if not enabled
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {android.Manifest.permission.CAMERA}, CAMERA_PREMISSION);
        }





    }

}
