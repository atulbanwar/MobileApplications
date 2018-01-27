package com.example.mad.finalexam.Utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.List;

public class GeoCodingTask extends AsyncTask<String, Void, Address> {
    private Context context;
    private GeoCodingTask.IGeoCodingTask activity;

    public GeoCodingTask(Context context) {
        this.context = context;
        //this.activity = activity;
    }

    @Override
    protected Address doInBackground(String... params) {
        String location = params[0];
        Address address = null;
        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> addressList = geocoder.getFromLocationName(location, 1);

            if (addressList != null) {
                address = addressList.get(0);
            }
        } catch (IOException ex) {}

        return address;
    }

    @Override
    protected void onPostExecute(Address address) {
        Log.d("demo", address.toString());
        super.onPostExecute(address);
    }

    static public interface IGeoCodingTask {
        public void setupData(String data);
    }
}