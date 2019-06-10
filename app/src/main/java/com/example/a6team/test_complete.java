package com.example.a6team;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class test_complete extends Activity {

TextView count;
    TextView xcount;
    Button main;
    ArrayList<String> countvoca = new ArrayList<String>();
ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_complete);
main = (Button)findViewById(R.id.main);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(test_complete.this,Main.class);

                startActivity(intent);
            }
        });

        count = (TextView)findViewById(R.id.count);
        xcount = (TextView)findViewById(R.id.xcount);
lv = (ListView)findViewById(R.id.listView);
        Intent intent = getIntent(); /*데이터 수신*/

        int count2 = intent.getExtras().getInt("count"); /*int형*/
        count.setText(String.valueOf(count2));

        String xcount2 = intent.getExtras().getString("xcount"); /*String형*/
        xcount.setText(xcount2);

        String voca2 = intent.getExtras().getString("voca"); /*String형*/


        String[] split2 = voca2.toString().split("\\,");
        for (int i=0; i<split2.length; i+=2){
            countvoca.add(split2[i]);

        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //context(액티비티 인스턴스)
                android.R.layout.simple_list_item_1, // 한 줄에 하나의 텍스트 아이템만 보여주는 레이아웃 파일
                // 한 줄에 보여지는 아이템 갯수나 구성을 변경하려면 여기에 새로만든 레이아웃을 지정하면 됩니다.
                countvoca  // 데이터가 저장되어 있는 ArrayList 객체
        );

        lv.setAdapter(adapter);






    }
}

