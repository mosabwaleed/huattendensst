package com.hu.huattendensst;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class qr_capture extends AppCompatActivity implements Detector.Processor {
    SurfaceView surfaceView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_capture);

        // cast surface view . . . .  ;
        surfaceView = (SurfaceView) findViewById(R.id.cameraview);

        // initialize barcode detector with all format( qr , matrices , 1d)...... ;
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        barcodeDetector.setProcessor(this);

        // build camera source and put barcode detector inside ... with auto focus enabled . . . . ;
        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .setAutoFocusEnabled(true)
                .build();

        // create message dialog for permission users to use camera in surface view . .  . . ;

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                // if user doesn't Approves for this perrmission don't open surface view . . .

                if (ActivityCompat.checkSelfPermission(qr_capture.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(qr_capture.this, new String[]{Manifest.permission.CAMERA}, 1024);

                    return;
                }
                try {
                    //if approves put camera with barcode detector in surface view . . . . ;
                    cameraSource.start(surfaceView.getHolder());
                    // if find exception put him in toast message . . . . ;
                } catch (IOException e) {
                    Toast.makeText(qr_capture.this, "problem is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            // when surface view destroyed stop camera sources . . . . . ;
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

    }

    // detect for the barcode . . . .  ;
    @Override
    public void release() {

    }

    // get the result of detection . . . . . ;
    @Override
    public void receiveDetections(Detector.Detections detections) {
        // get detected items and but in sparse array . . . ;
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

// if barcode dosen't equal 0 then put the barcode value in result variable . . . . ;
        if (barcodes.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < barcodes.size(); i++) {
                stringBuilder.append(barcodes.valueAt(i).rawValue);

            }

            // execute method for intent from barcode if succesfully result . . .
            successfullResult(stringBuilder.toString());
        }
    }

    // method for intent from barcode capture to items activity . . . ;
    public void successfullResult(String barcode) {
        Intent intent = new Intent(qr_capture.this, MainActivity.class);
        // this attribute for pass barcode value to item activity . . . ;
        intent.putExtra("key", barcode);
        intent.putExtra("lecture",getIntent().getStringExtra("lecture"));
        intent.putExtra("doctorid",getIntent().getStringExtra("doctorid"));
        intent.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(intent);
        finish();
    }



    // when choose the permission dialog . . . . ;
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1024:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(qr_capture.this,"Permission Denided",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
        }
    }
}
