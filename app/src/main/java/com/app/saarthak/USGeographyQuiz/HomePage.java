package com.app.saarthak.USGeographyQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class HomePage extends AppCompatActivity {

    private Button mHighScoreButton;
    private Button mStartButton;
    private Vibrator myVib;
    private double[] scores = {-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0};
    private String[] names = new String[10];
    private int scoreIndex;
    private Score tempScore;
    private Score junkScore = new Score("Junk", 100);

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private static void quickSort(double[] array, String[] array2, int first, int last) {
        int splitPt;
        if (first < last) {
            splitPt = split(array, array2, first, last);
            quickSort(array, array2, first, splitPt - 1);    // sort left side

            quickSort(array, array2, splitPt + 1, last);    // sort right side

        }
    }

    private static int split(double[] info, String[] info2, int first, int last) {
        int splitPt = first;
        double pivot = info[first];
        while (first <= last) {
            if (info[first] >= pivot) {
                first++;
            } else if (info[last] < pivot) {
                last--;
            } else {
                swap(info, info2, first, last);
                first++;
                last--;
            }
        }
        swap(info, info2, last, splitPt);
        splitPt = last;
        return splitPt;
    }

    private static void swap(double[] array, String[] array2, int a, int b) {
        double temp = array[a];
        array[a] = array[b];
        array[b] = temp;
        String temp2 = array2[a];
        array2[a] = array2[b];
        array2[b] = temp2;
    }

    /*private void incrementScoreIndex()
    {
        SharedPreferences scoreIndexPrefs = this.getSharedPreferences("scoreIndex", MODE_PRIVATE);
        SharedPreferences.Editor e = scoreIndexPrefs.edit();
        e.putInt("index", (scoreIndex + 1));
        e.apply();
    }

    private int getScoreIndex()
    {
        SharedPreferences scoreIndexPrefs = getSharedPreferences("scoreIndex", MODE_PRIVATE);
        int scoreIndex = scoreIndexPrefs.getInt("index", 0);
        return scoreIndex;
    }

    public void saveScores()
    {
        SharedPreferences scorePrefs = this.getSharedPreferences("highScores", MODE_PRIVATE);
        SharedPreferences.Editor e = scorePrefs.edit();
        long[] longHighScores = new long[scores.length];
        for(int i = 0; i < scores.length; i++)
            longHighScores[i] = (long)(scores[i]);
        for(int i = 0; i < longHighScores.length; i++) {
            e.putLong("score" + Integer.toString(i), longHighScores[i]);
        }
        e.commit();
    }

    public void saveScores()
    {
        SharedPreferences scorePrefs = this.getSharedPreferences("highScores", MODE_PRIVATE);
        SharedPreferences.Editor e = scorePrefs.edit();
        long[] longHighScores = new long[scores.length];
        for(int i = 0; i < scores.length; i++)
            longHighScores[i] = (long)(scores[i]);
        for(int i = 0; i < longHighScores.length; i++) {
            e.putLong("score" + Integer.toString(i), longHighScores[i]);
        }
        e.commit();
    }

    public double[] getScores(double[] scores)
    {
        SharedPreferences scorePrefs = this.getSharedPreferences("highScores", MODE_PRIVATE);
        long scoreGotten;
        for(int i = 0; i < scores.length; i++) {
            scoreGotten = scorePrefs.getLong("score" + Integer.toString(i), 0);
            scores[i] = (double)scoreGotten;
        }
        return scores;
    }

    public String[] getNames(String[] names)
    {
        SharedPreferences namePrefs = this.getSharedPreferences("highNames", MODE_PRIVATE);
        String nameGotten;
        for(int i = 0; i < names.length; i++) {
            nameGotten = namePrefs.getString("score" + Integer.toString(i), "");
            names[i] = nameGotten;
        }
        return names;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        /*scoreIndex = getScoreIndex();
        scores = getScores(scores);
        if(scoreIndex > 9) {
            scoreIndex = 0;
            for(int i = 0; i < score.length; i++)
                score[i] = 0.0;
        }

        Intent receiveScore = this.getIntent();
        double tempScore = receiveScore.getDoubleExtra("doubleScore", 100.0);
        if(tempScore != 100.0) {
            score[scoreIndex] = tempScore;
            incrementScoreIndex();
            saveScores();
        }*/

        Firebase.setAndroidContext(this);
        Intent receiveScore = this.getIntent();
        double score = receiveScore.getDoubleExtra("valueScore", 100.0);
        String name = receiveScore.getStringExtra("nameScore");
        Score tempScore = new Score(name, score);
        Firebase ref = new Firebase(Config.FIREBASE_URL);
        ref.child("Junk").setValue(junkScore);
        if (score != 100) {
            ref.child(tempScore.getUsername()).setValue(tempScore);
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot topSnapshot) {
                scoreIndex = 0;
                for (DataSnapshot snapshot : topSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    Score s = snapshot.getValue(Score.class);
                    if (s.getValue() != 100)
                    {
                        if (scoreIndex > 9)
                        {
                            System.out.println("here1");
                            if (s.getValue() > scores[9])
                            {
                                System.out.println("here2");
                                scores[9] = s.getValue();
                                names[9] = s.getUsername();
                                for(int i = 8; i >= 0; i--)
                                {
                                    if(scores[i+1] > scores[i])
                                    {
                                        swap(scores, names, i, i+1);
                                    }
                                }
                            }
                        }
                        else {
                            scores[scoreIndex] = s.getValue();
                            names[scoreIndex] = s.getUsername();
                        }
                        scoreIndex++;
                        quickSort(scores, names, 0, 9);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        mHighScoreButton = (Button) findViewById(R.id.highScoreButton);
        mStartButton = (Button) findViewById(R.id.startButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                startActivity(new Intent(HomePage.this, WelcomeActivity.class));
            }
        });

        mHighScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                Intent scoresPage = new Intent(HomePage.this, HighScores.class);
                scoresPage.putExtra("scoresArray", scores);
                scoresPage.putExtra("namesArray", names);
                /*sendScoresMatrix.putExtra("matrixScore", score);*/
                startActivity(scoresPage);
            }
        });
    }
}


