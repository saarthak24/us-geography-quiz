package com.app.saarthak.USGeographyQuiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Saarthak on 10/11/16.
 */
public class SplashScreen extends Activity {

    private ImageView mAppIcon;
    private ProgressBar mredProgressBar;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mAppIcon = (ImageView) findViewById(R.id.appIcon);
        mredProgressBar = (ProgressBar) findViewById(R.id.redProgressBar);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.rotate_around_center_point);
                    mAppIcon.startAnimation(animation);
                    while(progress < 2500)
                    {
                        Thread.sleep(1);
                        mredProgressBar.setProgress(progress);
                        progress++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, HomePage.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
