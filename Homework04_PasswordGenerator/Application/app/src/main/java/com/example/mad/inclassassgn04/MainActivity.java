package com.example.mad.inclassassgn04;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Home Work 3
 * MainActivity.java
 * Sanket Patil
 * Atul Banwar
 */

public class MainActivity extends Activity {
    private TextView txtViewPwdCountValue;
    private TextView txtViewPwdLengthValue;
    private TextView txtViewPwd;
    AlertDialog.Builder alertDialogPassword;

    private int pwdCount = 1;
    private int pwdLength = 8;

    private Handler handler;
    private static ProgressDialog progressDialog;

    private static final String PASSWORD = "PASSWORD";
    private static final String INDEX = "INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtViewPwdCountValue = (TextView) findViewById(R.id.text_view_pwd_count_value);
        txtViewPwdLengthValue = (TextView) findViewById(R.id.text_view_pwd_length_value);
        txtViewPwd = (TextView) findViewById(R.id.text_view_pwd_value);
        SeekBar skBarPwdCount = (SeekBar) findViewById(R.id.seek_bar_pwd_count);
        SeekBar skBarPwdLength = (SeekBar) findViewById(R.id.seek_bar_pwd_length);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(1);
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        skBarPwdCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pwdCount = progress + 1;
                txtViewPwdCountValue.setText(String.valueOf(pwdCount));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skBarPwdLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pwdLength = progress + 8;
                txtViewPwdLengthValue.setText(String.valueOf(pwdLength));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /**
     * On click handler for generate password thread
     *
     * @param view
     */
    public void generatePasswordThread(final View view) {
        progressDialog.setMax(pwdCount);

        handler = new Handler(new Handler.Callback() {
            Bundle bundle;
            CharSequence[] passwords = new CharSequence[pwdCount];

            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case GeneratePasswordUsingThread.PASSWORD_GENERATION_START:
                        progressDialog.show();
                        progressDialog.setProgress(0);
                        break;

                    case GeneratePasswordUsingThread.PASSWORD_GENERATION_PROGRESS:
                        bundle = (Bundle) msg.obj;
                        int index = ((int) bundle.get(INDEX));
                        passwords[index - 1] = ((String) bundle.get(PASSWORD));
                        progressDialog.incrementProgressBy(1);
                        break;

                    case GeneratePasswordUsingThread.PASSWORD_GENERATION_END:
                        progressDialog.dismiss();
                        alertDialogPassword = new AlertDialog.Builder(MainActivity.this);
                        alertDialogPassword.setTitle(getResources().getString(R.string.alert_dialog_title));
                        alertDialogPassword.setItems(passwords, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                txtViewPwd.setText(passwords[which]);
                            }
                        });

                        alertDialogPassword.show();
                        break;
                }

                return false;
            }
        });

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(new GeneratePasswordUsingThread(pwdLength));
    }

    /**
     * Class implementing Runnable. Returns the generated password using handler.
     */
    class GeneratePasswordUsingThread implements Runnable {
        static final int PASSWORD_GENERATION_START = 1;
        static final int PASSWORD_GENERATION_PROGRESS = 2;
        static final int PASSWORD_GENERATION_END = 3;
        int pwdLength = 0;

        GeneratePasswordUsingThread(int pwdLength) {
            this.pwdLength = pwdLength;
        }

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = PASSWORD_GENERATION_START;
            handler.sendMessage(msg);

            for (int index = 1; index <= pwdCount; index++) {
                String pwd = Util.getPassword(this.pwdLength);
                Bundle bundle = new Bundle();
                bundle.putString(PASSWORD, pwd);
                bundle.putInt(INDEX, index);

                msg = new Message();
                msg.obj = bundle;
                msg.what = PASSWORD_GENERATION_PROGRESS;
                handler.sendMessage(msg);
            }

            msg = new Message();
            msg.what = PASSWORD_GENERATION_END;
            handler.sendMessage(msg);
        }
    }

    /**
     * On Click handler for Generate Password (Async Task) button
     *
     * @param view
     */
    public void generatePasswordAsync(View view) {
        new GetPasswordWorker().execute(pwdCount, pwdLength);
    }

    // Implantation of Generating passwords using AsyncTask
    class GetPasswordWorker extends AsyncTask<Integer, Integer, Void> {
        ArrayList<String> passwordList = new ArrayList<String>();

        @Override
        protected Void doInBackground(Integer... params) {
            for (int i = 1; i <= params[0]; i++) {
                passwordList.add(Util.getPassword(params[1]));
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMax(pwdCount);
            progressDialog.show();
            progressDialog.setProgress(0);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            //Interactive Alert Dialog, pops up on the screen once the passwords are generated.
            AlertDialog.Builder alertDialogPassword = new AlertDialog.Builder(MainActivity.this);
            alertDialogPassword.setTitle("Passwords");
            final CharSequence[] csPasswords = passwordList.toArray(new CharSequence[passwordList.size()]);
            alertDialogPassword.setItems(csPasswords, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int which) {
                    txtViewPwd.setText(csPasswords[which]);
                }
            });
            alertDialogPassword.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }
}
