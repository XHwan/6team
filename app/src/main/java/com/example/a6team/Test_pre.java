package com.example.a6team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Test_pre extends Activity {

    private Button voca_btn;
    private Button mean_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_pre);


        voca_btn = (Button)findViewById(R.id.voca_btn);
        mean_btn = (Button)findViewById(R.id.mean_btn);

        voca_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Test_pre.this,Study_teset2.class);

                startActivity(intent);
            }
        });
        mean_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Test_pre.this,Study_teset.class);

                startActivity(intent);
            }
        });




    } // end of onCreate
} // end of class

