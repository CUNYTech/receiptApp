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


public class Receipt2Activity extends AppCompatActivity {

    ImageView imageView;
    TextView mReceiptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt2);

        Button btnCamera = findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();


        imageView.setImageBitmap(bitmap);

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, " NOT WORKING SOME HOW!", Toast.LENGTH_SHORT).show();
        } else {
            Frame outputFrame = new Frame.Builder().setBitmap(bitmap).build();
            final SparseArray<TextBlock> items = textRecognizer.detect(outputFrame);
            Toast.makeText(this, " textRecongnizer is working", Toast.LENGTH_SHORT).show();
            Log.i("word size", items.size() + "");

            StringBuilder mStringBuilder = new StringBuilder();

            int wordsToPost = items.size();
//                if (items.size() < wordsToPost) {
//                    wordsToPost = items.size();
//                }

            Log.i("rece info", "found words");
            for (int i = 0; i < wordsToPost; i++) {
                Log.i("rece info", "found words11");
                TextBlock item = items.valueAt(i);
                mStringBuilder.append(item.getValue());
                mStringBuilder.append("\n");
                String row = mStringBuilder.toString();
                Log.i("rece info", row);
            }//for
            String row = mStringBuilder.toString();
            Log.i("rece info", row);
            //mReceiptTextView.setText(mStringBuilder.toString());
            //Toast.makeText(this, mStringBuilder.toString(), Toast.LENGTH_SHORT).show();
//            }


        }//else
    }
}//class
