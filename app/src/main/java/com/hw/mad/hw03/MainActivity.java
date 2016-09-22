package com.hw.mad.hw03;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button btnExit, btnStartTrivia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExit=(Button)findViewById(R.id.button_exit);
        btnStartTrivia=(Button) findViewById(R.id.button_start_trivia);

        new GetTriviaData().execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
    }

    public void startTrivia(View view) {
    }

    public void exit(View view) {
        finish();
        System.exit(0);
    }
}
