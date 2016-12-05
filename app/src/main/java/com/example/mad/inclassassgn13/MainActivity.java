package com.example.mad.inclassassgn13;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mad.inclassassgn13.Adapter.ListViewAdapter;
import com.example.mad.inclassassgn13.Adapter.RecyclerViewAdapter;
import com.example.mad.inclassassgn13.Pojo.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends Activity {
    private Realm realm;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        */

        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User myUser = realm.createObject(User.class);
                myUser.setName("Atul");
                myUser.setAge(1);

                myUser = realm.createObject(User.class);
                myUser.setName("Sanket");
                myUser.setAge(2);
            }
        });

        RealmResults<User> users = realm.where(User.class).findAll();
        final ListViewAdapter adapter = new ListViewAdapter(this, users);

        ListView listView = (ListView) findViewById(R.id.list_view_name);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String userName = adapter.getItem(i).getName();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(User.class).equalTo("name", userName).findAll().deleteAllFromRealm();
                    }
                });
                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(this, realm.where(User.class).findAllAsync()));
        recyclerView.setHasFixedSize(true);
    }

    public void deleteItem(User item) {
        final String id = item.getName();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(User.class).equalTo("name", id)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
