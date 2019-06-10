package com.example.a6team;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Study_teset extends Activity {

    TextView textView2;
    TextView textView;
    String[] split1;
    String[] split2;
    StringBuilder xarray = new StringBuilder();
    String xcount;
    Button start;
    EditText input;
    Button send;
    int i = 0;
    int quiz = 0;
    int o = 0;
    int x = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        textView = (TextView) findViewById(R.id.voca);

        textView2 = (TextView) findViewById(R.id.mean);

        input = (EditText) findViewById(R.id.input);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (start.getText().toString().equals("시작하기")) {

                    textView.setText(split1[0]);

                    textView2.setText(split1[1]);
                    start.setVisibility(View.INVISIBLE);
                } else {

                    Intent intent = new Intent(getApplicationContext(), test_complete.class);

                    intent.putExtra("count", split1.length / 2); /*송신*/
                    intent.putExtra("xcount", xcount);

                    for (int k = 0; k < split2.length; k += 2) {
                        intent.putExtra("voca", xarray.toString());
                    }


                    Log.i("put 추출", String.valueOf(split1.length / 2));
                    Log.i("put 추출", String.valueOf(xcount));
                    startActivity(intent);
                }

            }
        });


        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                if (i == split1.length) {

                    start.setVisibility(View.VISIBLE);
                    split2 = xarray.toString().split("\\,");
                    xcount = String.valueOf(split2.length / 2);

                    for (int i = 0; i < split2.length; i++) {

                        Log.i("오답셋 추출", split2[i]);
                    }
                    start.setText("결과확인");
                } else {

                    if (input.getText().toString().equals(textView2.getText().toString())) {
                        o++;

                        Log.i("metoo", String.valueOf(o));
                        Log.i("metoo", input.getText().toString());
                        Log.i("metoo", textView.getText().toString());

                    } else {
                        x++;

                        Log.i("meto", String.valueOf(x));
                        Log.i("meto", input.getText().toString());
                        Log.i("meto", textView.getText().toString());
                        xarray.append(textView.getText().toString() + ",");
                        xarray.append(textView2.getText().toString() + ",");


                        Log.i("오답셋", xarray.toString());

                    }
                    textView.setText(split1[i]);
                    textView2.setText(split1[i + 1]);

                    i += 2;
                }


            }
        });
//MyBook.bookname;


        insertoToDatabase(Login2.Id, MyBook_Test.bookname);

    } // end of onCreate


    private void insertoToDatabase(String Id, final String Name) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected String doInBackground(String... params) {

                try {
                    Log.i("와우", "10");

                    String Id = (String) params[0];
                    String Name = (String) params[1];

                    String link = "http://203.234.62.79/Study.php";
                    String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("book_name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    Log.i("와우", "11");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        Log.i("BEWHY", "DIADO");
                        break;
                    }

                    split1 = sb.toString().split("\\,"); // 점을 기준으로해서 나눈다. [particle/a/08a, plist]
                    for (int i = 0; i < split1.length; i++) {

                        Log.i("changmo " + i + "번째", split1[i]);
                    }
                    return sb.toString();
                } catch (Exception e) {
                    Log.i("와우24442", "와우");
                    return new String("Exception: " + e.getMessage());

                }
            }
        }
        InsertData task = new InsertData();
        Log.i("와우22", "와우");
        task.execute(Id, Name);
        Log.i("와우322", "와우3");
    }


}
