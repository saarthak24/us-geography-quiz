package com.app.saarthak.USGeographyQuiz;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    private Button myesButton;
    private Button mnoButton;
    private ImageButton mnextButton;
    private ImageButton mprevButton;
    private Button manswerButton;
    private Button msubmitButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    public int mCurrentIndex = 0;
    public double score = 0.0;
    private static final int REQUEST_CODE_CHEAT = 0;
    private Vibrator myVib;
    private String malertText = "";

    public Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.question1, false),
                    new Question(R.string.question2, false),
                    new Question(R.string.question3, true),
                    new Question(R.string.question4, false),
                    new Question(R.string.question5, true),
            };

    private void nextQuestion() {
        mCurrentIndex++;
        if(mCurrentIndex == mQuestionBank.length-1)
        {
            msubmitButton.setVisibility(View.VISIBLE);
        }
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void prevQuestion() {
        mCurrentIndex--;
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressed) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (userPressed == answer) {
            messageResId = R.string.positive;
            Toast toast = Toast.makeText(WelcomeActivity.this, messageResId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            if (mQuestionBank[mCurrentIndex].isCheated())
                score += 0.5;
            else
                score += 1.0;
            mScoreTextView.setText("Score: " + Double.toString(score));
        } else {
            messageResId = R.string.negative;
            Toast toast = Toast.makeText(WelcomeActivity.this, messageResId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            if (mQuestionBank[mCurrentIndex].isCheated()) {
                score -= 0.5;
            }
            mScoreTextView.setText("Score: " + Double.toString(score));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent c) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (c == null)
            return;

        mQuestionBank[mCurrentIndex].setCheated(CheatActivity.wasCheated(c));
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        mScoreTextView.setText("Score: " + Double.toString(score));
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        myesButton = (Button) findViewById(R.id.yesButton);
        mnoButton = (Button) findViewById(R.id.noButton);
        mnextButton = (ImageButton) findViewById(R.id.nextButton);
        mprevButton = (ImageButton) findViewById(R.id.prevButton);
        manswerButton = (Button) findViewById(R.id.hintButton);
        msubmitButton = (Button) findViewById(R.id.submitButton);

        manswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(WelcomeActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        myesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                if (mQuestionBank[mCurrentIndex].isAnswered()) {
                    int messageResId;
                    messageResId = R.string.answered;
                    Toast toast = Toast.makeText(WelcomeActivity.this, messageResId, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                } else {
                    mQuestionBank[mCurrentIndex].setAnswered(true);
                    checkAnswer(true);
                    if(mCurrentIndex != mQuestionBank.length-1)
                    nextQuestion();
                }
            }
        });

        mnoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                if (mQuestionBank[mCurrentIndex].isAnswered()) {
                    int messageResId;
                    messageResId = R.string.answered;
                    Toast toast = Toast.makeText(WelcomeActivity.this, messageResId, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                } else {
                    mQuestionBank[mCurrentIndex].setAnswered(true);
                    checkAnswer(false);
                    if(mCurrentIndex != mQuestionBank.length-1)
                    nextQuestion();
                }
            }
        });

        mprevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                if (mCurrentIndex < 1) {
                    mCurrentIndex = 0;
                } else {
                    msubmitButton.setVisibility(v.INVISIBLE);
                    prevQuestion();
                }
            }
        });

        mnextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                if (mCurrentIndex > mQuestionBank.length-2) {
                    mCurrentIndex = mQuestionBank.length-1;
                } else {
                    nextQuestion();
                }
            }
        });

        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                final EditText input = new EditText(WelcomeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                AlertDialog.Builder alert = new AlertDialog.Builder(WelcomeActivity.this);
                alert.setView(input);
                alert.setTitle("Save High Score?");
                alert.setMessage("Enter Name");
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        malertText = input.getText().toString();

                        Intent sendScore = new Intent(WelcomeActivity.this, HomePage.class);
                        sendScore.putExtra("valueScore", score);
                        sendScore.putExtra("nameScore", malertText);
                        startActivity(sendScore);

                        /*Intent intent = new Intent(WelcomeActivity.this, HomePage.class);
                        startActivity(intent);*/
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(WelcomeActivity.this, HomePage.class);
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        });
    }
}
