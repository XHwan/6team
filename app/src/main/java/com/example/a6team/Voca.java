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
    private TextView getTextToSpeek;

    private ImageButton btn1;
    private ImageButton btn2;
    TextView voca;
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
        naver = (ImageButton)findViewById(R.id.naver);
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

