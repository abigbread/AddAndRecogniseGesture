package com.bread.addgesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/9.
 */
public class RecogniseGesture extends Activity {
    GestureOverlayView gestureOverlayView;
    GestureLibrary gestureLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognise_gesture_layout);
        gestureLibrary = GestureLibraries.fromFile(Environment.getExternalStorageDirectory() + "/mygesture");
        if (gestureLibrary.load()) {
            Toast.makeText(RecogniseGesture.this, "手势文件装载成功", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(RecogniseGesture.this, "手势文件装载失败", Toast.LENGTH_SHORT).show();
        }
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gesture_recognise);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
                ArrayList<String> result = new ArrayList<String>();
                for (Prediction pred : predictions) {
                    if (pred.score > 2.0) {
                        result.add("与手势【" + pred.name + "】相似度为" + pred.score);
                    }
                }
                if (result.size() > 0) {
                    ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(RecogniseGesture.this, android.R.layout.simple_dropdown_item_1line, result.toArray());
                    new AlertDialog.Builder(RecogniseGesture.this).setAdapter(adapter, null).setPositiveButton("确定", null).show();
                } else {
                    Toast.makeText(RecogniseGesture.this, "无法找到能匹配的手势！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
