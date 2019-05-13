package com.example.a6team;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Signup extends Activity {
    private EditText editTextId;
    private EditText editTextPw;
    private EditText editTextname;
    private Button Sign_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup);

        editTextId = (EditText) findViewById(R.id.idInput);
        editTextname = (EditText) findViewById(R.id.name_input);
        editTextPw = (EditText) findViewById(R.id.passwordInput);

        Sign_btn = (Button)findViewById(R.id.sign_btn);


    }
    public void insert(View view) {
        String Id = editTextId.getText().toString();
        String Name = editTextname.getText().toString();
        String Pw = editTextPw.getText().toString();

        insertoToDatabase(Id,Name, Pw);
    }
    private void insertoToDatabase(String Id, final String Name, String Pw) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Signup.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "가입되었습니다", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);

            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Name = (String) params[1];
                    String Pw = (String) params[2];

                    String link = "http://203.234.62.79/signup.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");

                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");

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
        task.execute(Id, Name, Pw);
    }
}