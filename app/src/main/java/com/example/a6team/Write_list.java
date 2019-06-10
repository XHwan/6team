package com.example.a6team;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Write_list  extends Activity {

    ArrayList<Song5> al = new ArrayList<Song5>();  // Top10 곡명을 담을 리스트

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_list);


for (int i = 0; i<Camera.al.size(); i++){

    al.add(new Song5(Camera.al.get(i).word,Camera.al.get(i).mean,Camera.al.get(i).example,Camera.al.get(i).example2,Camera.al.get(i).symbol));
}
        // 인기가요 순위 데이터 (다량의 데이터 준비)




        MyAdapter5 adapter = new MyAdapter5(
                getApplicationContext(), // 현재화면의 제어권자
                R.layout.voca_row_camera,  // 리스트뷰의 한행의 레이아웃
                al);         // 데이터

        ListView lv = (ListView)findViewById(R.id.listView1);
        lv.setAdapter(adapter);

     } // end of onCreate



    public void insert(View view) {


        for(int i = 0; i<al.size(); i++){
            String Id1 = al.get(i).word;
            String Id2 = al.get(i).mean;
            String Id3 = al.get(i).example;
            String Id4 = al.get(i).example2;
            String Id5 = al.get(i).symbol;

            insertoToDatabase(Id1, MyBook_Test.bookname,Login2.Id,Id2,Id3,Id4,Id5);
        }

    }


    private void insertoToDatabase(final String word , String bookname, String user_id, String mean, String example, String example2, String symbol) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Write_list.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Intent intent=new Intent(Write_list.this,Book_Plus.class);
                startActivity(intent);

            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String word = (String) params[0];
                    String bookname = (String) params[1];
                    String user_id = (String) params[2];
                    String mean = (String) params[3];
                    String example = (String) params[4];
                    String example2 = (String) params[5];
                    String symbol = (String) params[6];


                    String link = "http://203.234.62.79/voca_plus.php";
                    String data = URLEncoder.encode("word", "UTF-8") + "=" + URLEncoder.encode(word, "UTF-8");
                    data += "&" + URLEncoder.encode("book_name", "UTF-8") + "=" + URLEncoder.encode(bookname, "UTF-8");
                    data += "&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                    ;

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

class MyAdapter5 extends BaseAdapter { // 리스트 뷰의 아답타
    Context context;
    int layout;
    ArrayList<Song5> al;
    LayoutInflater inf;
    public MyAdapter5(Context context, int layout, ArrayList<Song5> al) {
        this.context = context;
        this.layout = layout;
        this.al = al;
        inf = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return al.size();
    }
    @Override
    public Object getItem(int position) {
        return al.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = inf.inflate(layout, null);
        }
        TextView textView1 = (TextView)convertView.findViewById(R.id.textView1);
        TextView textView2 = (TextView)convertView.findViewById(R.id.textView2);
        TextView textView3 = (TextView)convertView.findViewById(R.id.textView3);
        TextView textView4 = (TextView)convertView.findViewById(R.id.textView4);
        TextView textView5 = (TextView)convertView.findViewById(R.id.textView5);

        Song5 m = al.get(position);
        textView1.setText(m.word);
        textView2.setText(m.mean);
        textView3.setText(m.example);
        textView4.setText(m.example2);
        textView5.setText(m.symbol);

        return convertView;
    }
}





class Song5 { // 자바빈
    String word = ""; // 곡 title
    String mean = ""; // 곡 title
    String example = ""; // 곡 title
    String example2 = ""; // 곡 title
    String symbol = ""; // artist


    public Song5(String word,String mean, String example, String example2, String symbol) {
        super();
        this.word = word;
        this.mean = mean;
        this.example = example;
        this.example2 = example2;
        this.symbol = symbol;
    }
    public Song5() {}
}

