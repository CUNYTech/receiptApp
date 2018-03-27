package com.next.groupmeal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Created by qiman on 3/21/2018.
 */

public class ReceiptActivity extends AppCompatActivity {

    SurfaceView mSurfaceView;
    TextView mTextView;
    CameraSource mCameraSource;
    Button takePicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        takePicButton = findViewById(R.id.takePicButton);


        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mCameraSource.takePicture(new CameraSource.ShutterCallback() {
                                          @Override
                                          public void onShutter() {
                                              System.out.println("lol ???");
                                          }
                                      },
                    new CameraSource.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] bytes) {

                        }
                    });
            }
        });

        //
        mSurfaceView = findViewById(R.id.surfaceView);
        mTextView = findViewById(R.id.textView);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Toast.makeText(this, " NOT WORKING SOME HOW!", Toast.LENGTH_SHORT).show();
        }else{

            //build the camera source
            mCameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setAutoFocusEnabled(true)
                    .build();



            mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try{
                        mCameraSource.start(mSurfaceView.getHolder());
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                    mCameraSource.stop();

                }
            });
        }//else

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();

                if(items.size() !=0){
                    mTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder mStringBuilder = new StringBuilder();

                            int wordsToPost = 5;
                            if(items.size() < wordsToPost){
                                wordsToPost = items.size();
                            }

                            for(int i=0; i < wordsToPost; i++){
                                TextBlock item = items.valueAt(i);
                                mStringBuilder.append(item.getValue());
                                mStringBuilder.append("\n");
                            }//for
                            String row = mStringBuilder.toString();
                            Log.i("rece info" ,row );
                            mTextView.setText(row);

                        }
                    });//post method for textview
                }
            }
        });




    }//on create



}