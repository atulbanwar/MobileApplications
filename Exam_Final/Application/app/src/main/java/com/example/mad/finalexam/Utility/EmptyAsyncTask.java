package com.example.mad.finalexam.Utility;

import android.os.AsyncTask;

public class EmptyAsyncTask extends AsyncTask<Integer, Integer, Void> {
    @Override
    protected Void doInBackground(Integer... params) {
        int input = params[0];
        publishProgress(1);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        //progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //progressDialog.setProgress(values[0]);
    }
}
