package com.a75f;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomView customView = new CustomView(getApplicationContext());
        setContentView(customView);

    }
}
