package com.example.a6team;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
num1 = (TextView)findViewById(R.id.num1);
        num2 = (TextView)findViewById(R.id.num2);
        getTextToSpeek = (TextView)findViewById(R.id.voca);
        ye = (TextView)findViewById(R.id.ye);
        speech = (TextView)findViewById(R.id.speech);
        naver = (ImageButton)findViewById(R.id.naver);
        next = (ImageButton)findViewById(R.id.next);
        prev = (ImageButton)findViewById(R.id.prev);
        btn1 = (ImageButton)findViewById(R.id.shuffle);

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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 2) {

                    Toast.makeText(getApplicationContext(), "마지막 단어입니다", Toast.LENGTH_LONG).show();
                }
                else{

                    number ++;
                    getTextToSpeek.setText("study");
                    speech.setText("[ˈstʌdi]");
                    ye.setText("Stop bothering me while I'm trying to study!");
                    num1.setText("("+number + "/");
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 1) {

                    Toast.makeText(getApplicationContext(), "처음 단어입니다", Toast.LENGTH_LONG).show();
                }
                else{

                    number --;
                    getTextToSpeek.setText("debate");
                    speech.setText("[dɪˈbeɪt]");
                    ye.setText("Tell me your impression of the debate");
                    num1.setText("("+number + "/");
                }

            }
        });


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

}

