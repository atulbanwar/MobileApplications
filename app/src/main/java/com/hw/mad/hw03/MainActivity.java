package com.hw.mad.hw03;

import android.app.Activity;
import android.app.ProgressDialog;
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
    private ImageView imageViewTrivia;
    private TextView textViewLoadingReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExit = (Button) findViewById(R.id.button_exit);
        btnStartTrivia = (Button) findViewById(R.id.button_start_trivia);
        imageViewTrivia = (ImageView) findViewById(R.id.image_view_trivia);
        pgDiagLoading = (ProgressBar) findViewById(R.id.progress_bar_loading);
        textViewLoadingReady = (TextView) findViewById(R.id.text_loading_ready);

        btnStartTrivia.setEnabled(false);
        if (isConnectedOnline()) {
            pgDiagLoading.setVisibility(View.VISIBLE);
            textViewLoadingReady.setText(getResources().getString(R.string.progess_bar_loading));
            new GetTriviaData(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");

        } else {
            Toast.makeText(MainActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    public void startTrivia(View view) {
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
        textViewLoadingReady.setText("");
        if (!result.isEmpty()) {

            textViewLoadingReady.setText(getResources().getString(R.string.progess_bar_ready));
            imageViewTrivia.setImageResource(R.drawable.trivia);
            btnStartTrivia.setEnabled(true);
            //Your Question list is available here
        }else
        {
            textViewLoadingReady.setText(getResources().getString(R.string.no_questions));
        }

    }

}
