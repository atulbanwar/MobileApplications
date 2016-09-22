package com.hw.mad.hw03;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements GetTriviaData.IData {

    private Button btnExit, btnStartTrivia;
    private ProgressBar pgDiagLoading;
    private ImageView imgViewTrivia;
    private TextView txtViewLoadingReady;
    private ArrayList<Question> questionList;
    public static final String QUESTIONS_LIST_KEY = "QUESTIONS";
    public static final int REQ_CODE_START = 1;
    public static final String JSON_URL = "http://dev.theappsdr.com/apis/trivia_json/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExit = (Button) findViewById(R.id.button_exit);
        btnStartTrivia = (Button) findViewById(R.id.button_start_trivia);
        imgViewTrivia = (ImageView) findViewById(R.id.image_view_trivia);
        pgDiagLoading = (ProgressBar) findViewById(R.id.progress_bar_loading);
        txtViewLoadingReady = (TextView) findViewById(R.id.text_loading_ready);
        btnStartTrivia.setEnabled(false);

        questionList = new ArrayList<Question>();

        if (isConnectedOnline() && questionList.isEmpty()) {
            pgDiagLoading.setVisibility(View.VISIBLE);
            txtViewLoadingReady.setText(getResources().getString(R.string.progress_bar_loading));
            new GetTriviaData(this).execute(JSON_URL);
        } else if (isConnectedOnline()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    public void startTrivia(View view) {
        //Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
        //intent.putExtra(QUESTIONS_LIST_KEY, questionList);
        //startActivityForResult(intent, REQ_CODE_START);
    }

    public void exit(View view) {
        finish();
        System.exit(0);
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setupData(ArrayList<Question> result) {

        pgDiagLoading.setVisibility(View.INVISIBLE);
        txtViewLoadingReady.setText("");
        if (!result.isEmpty()) {

            txtViewLoadingReady.setText(getResources().getString(R.string.progress_bar_ready));
            imgViewTrivia.setImageResource(R.drawable.trivia);
            btnStartTrivia.setEnabled(true);
            questionList.addAll(result);
            //Your Question list is available here
        } else {
            txtViewLoadingReady.setText(getResources().getString(R.string.error_no_questions));
        }

    }

}
