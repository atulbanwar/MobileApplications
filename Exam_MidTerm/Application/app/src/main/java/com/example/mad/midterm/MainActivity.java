package com.example.mad.midterm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity implements FetchAppDetailsTask.IData {
    private String api = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    private AppAdapter appAdapter;
    private ListView lstViewApps;
    private TextView txtViewNoApps;
    private Switch switchSort;
    private TextView txtViewSortLable;
    RecyclerView recycleView;
    SavedAppAdapter savedAppAdapter;
    List<App> savedApps;

    private ProgressDialog progressDialog;
    public static DatabaseDataManager dm;

    ArrayList<App> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstViewApps = (ListView) findViewById(R.id.list_view_apps);
        //txtViewNoApps = (TextView) findViewById(R.id.text_view_no_filtered_apps);
        switchSort = (Switch) findViewById(R.id.switch_sort);
        txtViewSortLable = (TextView) findViewById(R.id.text_view_sort_lable);

        savedApps = new ArrayList<>();
        dm = new DatabaseDataManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        apps = new ArrayList<>();

        if (isConnectedOnline()) {
            new FetchAppDetailsTask(this).execute(api);
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.msg_no_internet_conn), Toast.LENGTH_SHORT).show();
        }

        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sort(true);
                    appAdapter.notifyDataSetChanged();
                    txtViewSortLable.setText(getResources().getString(R.string.text_view_ascending));
                } else {
                    sort(false);
                    appAdapter.notifyDataSetChanged();
                    txtViewSortLable.setText(getResources().getString(R.string.text_view_descending));
                }
            }
        });
    }

    @Override
    public void startAppDataFetch() {
        progressDialog.show();
    }

    @Override
    public void setupData(ArrayList<App> result) {
        apps = result;
        progressDialog.dismiss();
        sort(false);

        appAdapter = new AppAdapter(this, apps);
        appAdapter.setNotifyOnChange(true);
        lstViewApps.setAdapter(appAdapter);

        lstViewApps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dm.saveApp(apps.get(position));
                appAdapter.remove(apps.get(position));

                Toast.makeText(MainActivity.this, "app added to database", Toast.LENGTH_SHORT).show();
                showSavedApps();
                return false;
            }
        });
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void sort(boolean isAscending) {
        if (isAscending) {
            Collections.sort(apps, new Comparator<App>() {
                @Override
                public int compare(App o1, App o2) {
                    return Double.compare(o1.getPrice(), o2.getPrice());
                }
            });
        } else {
            Collections.sort(apps, new Comparator<App>() {
                @Override
                public int compare(App o1, App o2) {
                    return Double.compare(o2.getPrice(), o1.getPrice());
                }
            });
        }
    }

    public void refreshAction(View view) {
        if (isConnectedOnline()) {
            new FetchAppDetailsTask(this).execute(api);
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.msg_no_internet_conn), Toast.LENGTH_SHORT).show();
        }
    }

    private void showSavedApps() {
        savedApps = dm.getALL();

        //savedAppAdapter = new SavedAppAdapter(this, savedApps);
        //recycleView.setAdapter(savedAppAdapter);
        //recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}