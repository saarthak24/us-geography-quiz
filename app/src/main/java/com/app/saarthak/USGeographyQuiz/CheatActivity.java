package com.app.saarthak.USGeographyQuiz;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private Button mshowAnswer;
    private TextView mWarningTextView;
    private TextView mAnswerTextView;
    private boolean mAnswerIsTrue;
    private Vibrator myVib;
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.saarthak.helloworld2.answer_is_true";
    private static final String EXTRA_IS_CHEATED = "com.example.saarthak.helloworld2.is_cheated";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasCheated(Intent c)
    {
        return c.getBooleanExtra(EXTRA_IS_CHEATED, false);
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mWarningTextView = (TextView) findViewById(R.id.warning_text_view);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mshowAnswer = (Button) findViewById(R.id.show_answer_button);

        mshowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myVib.vibrate(50);
                mshowAnswer.setVisibility(v.INVISIBLE);
                mshowAnswer.setEnabled(false);
                mAnswerTextView.setVisibility(v.VISIBLE);
                if(mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.yesButton);
                else
                    mAnswerTextView.setText(R.string.noButton);
                setCheated(true);
            }
        });
    }

    private void setCheated(boolean isCheated){
        Intent j = new Intent();
        j.putExtra(EXTRA_IS_CHEATED, isCheated);
        setResult(RESULT_OK, j);

    }
}
