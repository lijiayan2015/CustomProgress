package com.ljy.myprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CustomProgress progress1,progress2,progress3;
    private int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress1 = (CustomProgress) findViewById(R.id.progress1);
        progress2 = (CustomProgress) findViewById(R.id.progress2);
        progress3 = (CustomProgress) findViewById(R.id.progress3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress<=100){
                    progress+=3;
                    progress1.setProgress(progress);
                    progress2.setProgress(progress);
                    progress3.setProgress(progress);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
