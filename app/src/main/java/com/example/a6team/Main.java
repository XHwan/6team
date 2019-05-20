package com.example.a6team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main extends Activity
{
    Button btn = null;
    Button voca_plus;
    ImageView iv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        voca_plus = (Button)findViewById(R.id.voca_plus);

        btn = (Button)findViewById(R.id.myvoca);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main.this,Voca.class);

                startActivity(intent);
            }
        });
        voca_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main.this,Selection.class);

                startActivity(intent);
            }
        });
}





}
