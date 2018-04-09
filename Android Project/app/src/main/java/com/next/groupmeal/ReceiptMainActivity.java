package com.next.groupmeal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by qiman on 4/4/2018.
 */

public class ReceiptMainActivity extends AppCompatActivity {
    TextView textViewReceipt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_main);

        textViewReceipt = findViewById(R.id.TextViewReceiptDisplay);

        ArrayList<String> lastReceipt = OcrDetectorProcessor.receipts.get(OcrDetectorProcessor.receipts.size()-1);
        for(String receiptItem : lastReceipt){
            textViewReceipt.append(receiptItem + "\n\n");
        }

    }//on create
}
