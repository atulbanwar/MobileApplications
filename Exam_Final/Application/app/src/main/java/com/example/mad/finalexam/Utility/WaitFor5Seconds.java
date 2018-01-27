package com.example.mad.finalexam.Utility;

import android.os.AsyncTask;

public class WaitFor5Seconds extends AsyncTask<String, Void, Void> {
    IData activity;

    public WaitFor5Seconds(IData activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(null);
        activity.finishActivity();
    }

    static public interface IData {
        public void finishActivity();
    }
}