package com.hw.mad.hw03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends Activity {
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        int totalQuestionsCount = 0;
        int correctAnswersCount = 0;
        int percentage = 0;

        TextView txtViewCorrectPercentage = (TextView) findViewById(R.id.text_view_correct_percentage);
        ProgressBar pBarCorrectPercentage = (ProgressBar) findViewById(R.id.progress_bar_correct_percentage);

        if (getIntent().getExtras() != null) {
            questions = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QUESTIONS_LIST_KEY);

            if (questions != null) {
                totalQuestionsCount = questions.size();
                correctAnswersCount = getIntent().getExtras().getInt(TriviaActivity.CORRECT_ANS_COUNT_KEY);

                percentage = (correctAnswersCount * 100) / totalQuestionsCount;
            }

            txtViewCorrectPercentage.setText(getResources().getString(R.string.text_view_correct_percentage, percentage));
            pBarCorrectPercentage.setProgress(percentage);
        }
    }

    public void quitAction(View view) {
        finish();
    }

    public void tryAgainAction(View view) {
        Intent intent = new Intent(StatsActivity.this, TriviaActivity.class);
        intent.putExtra(MainActivity.QUESTIONS_LIST_KEY, questions);
        startActivity(intent);
        finish();
    }
}
