package com.bread.addgesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
    GestureOverlayView gestureView ;
    EditText editText;
    Button recogniseBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.gesture_name);
        recogniseBtn = (Button) findViewById(R.id.recognise_btn);
        gestureView = (GestureOverlayView) findViewById(R.id.gesture);
        gestureView.setGestureColor(Color.RED);
        gestureView.setGestureStrokeWidth(4);
        gestureView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, final Gesture gesture) {
                View saveDialog = getLayoutInflater().inflate(R.layout.save, null);
                ImageView imageView = (ImageView) saveDialog.findViewById(R.id.show);
                final EditText gestureName = (EditText) saveDialog.findViewById(R.id.gesture_name);
                Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xffff0000);
                imageView.setImageBitmap(bitmap);
                new AlertDialog.Builder(MainActivity.this).setView(saveDialog).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GestureLibrary gestureLibrary = GestureLibraries.fromFile(Environment.getExternalStorageDirectory() + "/mygesture");
                        gestureLibrary.addGesture(gestureName.getText().toString(), gesture);
                        gestureLibrary.save();
                    }
                }).setNegativeButton("取消", null).show();

            }
        });
        recogniseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecogniseGesture.class);
                startActivity(intent);
            }
        });
    }
}
