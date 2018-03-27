package com.next.groupmeal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.BitSet;

/**
 * Created by qiman on 3/22/2018.
 */


public class Receipt3Activity extends AppCompatActivity {

    private TextRecognizer detetcor ;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_result);

        textView = (TextView) findViewById(R.id.receipt_result_TextView);

        detetcor = new TextRecognizer().Builder(this).build();

        this.findViewById(R.id.button2).setOnClickListener( );
    }
}//class
