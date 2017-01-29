package com.app.saarthak.USGeographyQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HighScores extends AppCompatActivity {

    private TextView mhighScoreTitle_text_view;
    private TextView mhighScore_text_view;
    private double[] scores = new double[10];
    private String[] names = new String[10];

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        mhighScoreTitle_text_view = (TextView) findViewById(R.id.highScoreTitle_text_view);
        mhighScore_text_view = (TextView) findViewById(R.id.highScore_text_view);

        Intent receiveScores = this.getIntent();
        scores = receiveScores.getDoubleArrayExtra("scoresArray");
        names = receiveScores.getStringArrayExtra("namesArray");

        for (int i = 0; i < scores.length; i++) {
            if (names[i] == null) {
                mhighScore_text_view.append(Integer.toString(i + 1) + ") " + "\n");
            } else {
                mhighScore_text_view.append(Integer.toString(i + 1) + ") " + names[i] + " - " + Double.toString(scores[i]) + "\n");
            }
        }
    }
}
