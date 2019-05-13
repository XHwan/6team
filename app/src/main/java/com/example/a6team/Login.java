package com.example.a6team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends Activity
{
private Button Sign_btn;
    private Button Login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Login_btn = (Button)findViewById(R.id.login_btn) ;
        Sign_btn = (Button)findViewById(R.id.sign_btn) ;
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "로그인 되었습니다", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Login.this,Main.class);
                startActivity(intent);



            }
        });

        Sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Login.this,Signup.class);
                startActivity(intent);



            }
        });

    }





}
