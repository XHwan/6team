package com.example.a6team;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Write extends Activity {
    private EditText editText;
    private TextView voca;
    private TextView speech;
    private TextView mean;
    private TextView mean2;
    private TextView ye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);

        editText = (EditText) findViewById(R.id.ed1);
        voca = (TextView) findViewById(R.id.voca);
        speech = (TextView) findViewById(R.id.speech);
        mean = (TextView) findViewById(R.id.mean);
        mean2 = (TextView) findViewById(R.id.mean2);
        ye = (TextView) findViewById(R.id.ye);

    }
    public void insert(View view) {
        String Id = editText.getText().toString();

        insertoToDatabase(Id);
    }
    public void insert2(View view) {
        String Id = editText.getText().toString();


        String Id1 = voca.getText().toString();
        String Id2 = speech.getText().toString();
        String Id3 = mean.getText().toString();
        String Id4 = mean2.getText().toString();
        String Id5 = ye.getText().toString();

        insertoToDatabase2(Id1, MyBook_Test.bookname,Login2.Id,Id2,Id3,Id4,Id5);
    }
    private void insertoToDatabase(String Id) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Write.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "검색되었습니다", Toast.LENGTH_LONG).show();



            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];

                    String link = "http://203.234.62.79/voca_search.php";
                    String data = URLEncoder.encode("word", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        Log.i("seojeog",line);
                        Log.i("seojeog22222",sb.substring(1,3));
                        break;
                    }
                    String[] split1 = sb.toString().split("\\,"); // 점을 기준으로해서 나눈다. [particle/a/08a, plist]

                    voca.setText(split1[0].toString());;
                    mean.setText(split1[1].toString());;
                    ye.setText(split1[2].toString());
                    mean2.setText(split1[3].toString());
                    speech.setText(split1[4].toString());
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id);
    }
    private void insertoToDatabase2(final String word , String bookname, String user_id, String mean, String example, String example2, String symbol) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Write.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(Write.this,Book_Plus.class);
                startActivity(intent);


            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String word = (String) params[0];
                    String bookname = (String) params[1];
                    String user_id = (String) params[2];
                    String symbol = (String) params[3];
                    String mean = (String) params[4];
                    String example2 = (String) params[5];
                    String example = (String) params[6];



                    String link = "http://203.234.62.79/voca_plus.php";
                    String data = URLEncoder.encode("word", "UTF-8") + "=" + URLEncoder.encode(word, "UTF-8");
                    data += "&" + URLEncoder.encode("book_name", "UTF-8") + "=" + URLEncoder.encode(bookname, "UTF-8");
                    data += "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
Log.i("50000 2",user_id);
                    Log.i("50000 6",symbol);
                    Log.i("50000 3",mean);
                    Log.i("50000 5",example2);
                    Log.i("50000 4",example);
                    Log.i("50000 1",bookname);
                    Log.i("50000 0",word);
                    data += "&" + URLEncoder.encode("mean", "UTF-8") + "=" + URLEncoder.encode(mean, "UTF-8");

                    data += "&" + URLEncoder.encode("example", "UTF-8") + "=" + URLEncoder.encode(example, "UTF-8");
                    data += "&" + URLEncoder.encode("example2", "UTF-8") + "=" + URLEncoder.encode(example2, "UTF-8");
                    data += "&" + URLEncoder.encode("symbol", "UTF-8") + "=" + URLEncoder.encode(symbol, "UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }


                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(word,bookname,user_id,mean,example,example2,symbol);
    }
}