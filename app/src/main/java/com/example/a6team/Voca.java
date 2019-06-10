package com.example.a6team;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;

public class Voca extends Activity implements TextToSpeech.OnInitListener {

    static int a = 0;
    static String search = "dd";
    private TextToSpeech tts;
    private ImageButton btSpeak;
    private ImageButton naver;
    private ImageButton next;
    private ImageButton prev;
    private TextView getTextToSpeek;
    private TextView speech;
    private TextView ye;
    private TextView ye2;
    private TextView mean;
    private TextView mean2;
    private TextView mean3;
    String[] split1;

    private ImageButton btn1;
    private ImageButton btn2;
    TextView voca;
    TextView num1;
    TextView num2;
    static int number = 1;
    private View view1;
    private View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voca);

        tts = new TextToSpeech(this, this); //첫번째는 Context 두번째는 리스너

        btSpeak = (ImageButton) findViewById(R.id.headset);
        btSpeak.setEnabled(false);
        getTextToSpeek = (TextView)findViewById(R.id.voca);
        ye = (TextView)findViewById(R.id.ye);
        ye2 = (TextView)findViewById(R.id.ye2);
        mean = (TextView)findViewById(R.id.mean);
        mean2 = (TextView)findViewById(R.id.mean2);
        mean3 = (TextView)findViewById(R.id.mean3);
        speech = (TextView)findViewById(R.id.speech);
        naver = (ImageButton)findViewById(R.id.naver);
        btn1 = (ImageButton)findViewById(R.id.shuffle);

        insertoToDatabase(Book_Plus.voca);

        getTextToSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTextToSpeek.setText(split1[0]);
                mean.setText(split1[1]);
                mean2.setText(split1[2]);
                mean3.setText(split1[3]);
                ye.setText(split1[4]);
                ye2.setText(split1[5]);
                speech.setText(split1[6]);

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 0) {

                    btn1.setImageResource(R.drawable.shuffle1);
                    a= 1;
                }
                else{

                    btn1.setImageResource(R.drawable.shuffle);
                    a=0;
                }

            }
        });
        btSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speakOutNow();

    }
});
        naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = getTextToSpeek.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.search.naver.com/search.naver?where=m_ldic&query="+search+"&sm=mtb_stc"));
                startActivity(intent);



            }
        });
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getTextToSpeek.setText(split1[0]);
                mean.setText(split1[1]);
                mean2.setText(split1[2]);
                mean3.setText(split1[3]);
                ye.setText(split1[4]);
                ye2.setText(split1[5]);
                speech.setText(split1[6]);
                // TODO
            }
        }, 700);


    }


    public void onBackPressed() {

        Intent intent=new Intent(Voca.this,Book_Plus.class);
        startActivity(intent);
    }


    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.KOREAN);

            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                btSpeak.setEnabled(false);
                Toast.makeText(this, "지원하지 않는 언어입니다.", Toast.LENGTH_SHORT).show();
            } else {
                btSpeak.setEnabled(true);

            }
        } else {
            Toast.makeText(this, "TTS 실패!", Toast.LENGTH_SHORT).show();
        }
    }

    //Speak out...
    private void speakOutNow() {
        String text = getTextToSpeek.getText().toString();
        //tts.setPitch((float) 0.1); //음량
        //tts.setSpeechRate((float) 0.5); //재생속도
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }



    private void insertoToDatabase(String Id) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Voca.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();



            }
            @Override
            protected String doInBackground(String... params) {

                try {

                    String link = "http://203.234.62.79/voca_search.php";
                    String data = URLEncoder.encode("word", "UTF-8") + "=" + URLEncoder.encode(Book_Plus.voca, "UTF-8");

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
                    split1 = sb.toString().split("\\,"); // 점을 기준으로해서 나눈다. [particle/a/08a, plist]
                    Log.i("ashisland1",split1[0]);
                    Log.i("ashisland2",split1[1]);
                    Log.i("ashisland3",split1[2]);
                    Log.i("ashisland4",split1[3]);
                    Log.i("ashisland5",split1[4]);
                    Log.i("ashisland6",split1[5]);
                    Log.i("ashisland7",split1[6]);
                    Log.i("ashisland8","----------");

                    mean.setText(split1[1]);
                    mean2.setText(split1[2]);
                    mean3.setText(split1[3]);
                    ye.setText(split1[4]);
                    ye2.setText(split1[5]);
                    speech.setText(split1[6]);

                    getTextToSpeek.setText(split1[0]);

                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id);
    }


}

