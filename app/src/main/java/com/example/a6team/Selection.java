package com.example.a6team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Selection extends Activity {

    private ImageButton camera;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        camera = (ImageButton)findViewById(R.id.camera_btn) ;
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Selection.this,Camera.class);

                startActivity(intent);
            }
        });


}}
