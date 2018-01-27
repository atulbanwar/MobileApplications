package com.example.mad.finalexam;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad.finalexam.DB.PojoForDB;
import com.example.mad.finalexam.POJO.ParcelablePojoForIntent;
import com.example.mad.finalexam.POJO.Place;
import com.example.mad.finalexam.POJO.PojoForJSONParsing;
import com.example.mad.finalexam.POJO.Trip;
import com.example.mad.finalexam.RealmAdapter.RealmListViewAdapter;
import com.example.mad.finalexam.RealmAdapter.RlmRecyclerViewAdapter;
import com.example.mad.finalexam.Utility.GeoCodingTask;
import com.example.mad.finalexam.Utility.GetDataForJSONParsingTask;
import com.example.mad.finalexam.Adapter.ListViewAdapter;
import com.example.mad.finalexam.Adapter.RecyclerViewAdapter;
import com.example.mad.finalexam.DB.DatabaseDataManager;
import com.example.mad.finalexam.POJO.SerializablePojoForIntent;
import com.example.mad.finalexam.Utility.DownloadImageTask;
import com.example.mad.finalexam.Utility.Util;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;

public class MainActivity extends AppCompatActivity implements GetDataForJSONParsingTask.IJSONParsedData {
    // ____________________ PROGRESS DIALOG ____________________
    private ProgressDialog progressDialog;

    // ____________________ INTERNAL DATABASE ____________________
    public static DatabaseDataManager dm;

    // ____________________ FIREBASE ____________________
    public static FirebaseAuth firebaseAuth;
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    public static boolean isSignUpInProgress = false;

    // ____________________ GOOGLE API CLIENT ____________________
    public static GoogleApiClient googleApiClient;

    // ____________________ REALM  ____________________
    private Realm realm;
    RealmResults<PojoForJSONParsing> realmPojos;
    RealmListViewAdapter realmListViewAdapter;
    RlmRecyclerViewAdapter rlmRecyclerViewAdapter;

    // ____________________ LOCATION MANAGER  ____________________
    LocationManager locationManager;
    LocationListener locationListener;

    public static DatabaseReference currentTripRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        // region ____________________ PROGRESS DIALOG ____________________
        /*
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        //progressDialog.setMax(1);
        //progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();
        progressDialog.dismiss();
        */
        // endregion

        // region ____________________ ALERT DIALOG ____________________
        /*AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Select Items");

        final CharSequence[] alertDialogItems = new CharSequence[2];
        alertDialogItems[0] = "Item 1";
        alertDialogItems[1] = "Item 2";

        alertDialogBuilder.setItems(alertDialogItems, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String selectedAlertDialogItem = alertDialogItems[which].toString();
            }
        });
        alertDialogBuilder.show();*/
        // endregion

        // region ____________________ SEEK BAR ____________________
        /*
        SeekBar skBarObj = (SeekBar) findViewById(R.id.skBarName);
        skBarObj.setProgress(5);
        skBarObj.setMax(25);
        skBarObj.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 5;
                progress = progress * 5;
                seekBar.setProgress(progress);
                //txtViewObj.setText(String.valueOf(progress) + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //DO Nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //DO Nothing
            }
        });

        // will return progress
        skBarObj.getProgress();
        */
        // endregion

        // region ____________________ EMPTY ASYNC TASK  ____________________
        //new EmptyAsyncTask().execute(1);
        // endregion

        // region ____________________ DOWNLOAD IMAGE ASYNC TASK  ____________________
        //new DownloadImageTask(this).execute("https://pbs.twimg.com/profile_images/655066410087940096/QSUlrrlm.png");
        // endregion

        // region ____________________ JSON PARSE TASK  ____________________
        //new GetDataForJSONParsingTask(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
        // endregion

        // region ____________________ DATABASE  ____________________
        /*dm = new DatabaseDataManager(this);
        PojoForDB pojo = new PojoForDB();
        pojo.setVariable1("var1");
        pojo.setVariable2("var2");
        dm.savePojoForDB(pojo);
        ArrayList pojos = dm.getALL();
        pojo.setId(1);
        pojo.setVariable1("updated var 1");
        dm.updatePojoForDB(pojo);
        PojoForDB pojo1 = dm.getPojo("updated var 1", "var2");
        dm.delete(pojo);*/
        // endregion

        // region ____________________ FIREBASE  ____________________
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null && !isSignUpInProgress) {
                    Intent intentObj = new Intent(MainActivity.this, NextActivity.class);
                    startActivity(intentObj);
                }
            }
        });
        // endregion

        // region ____________________ GOOGLE SIGN IN  ____________________
        // implement GoogleApiClient.OnConnectionFailedListener
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                progressDialog.show();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
            }
        });*/
        // endregion

        // region ____________________ REALM  ____________________
        //realm = Realm.getDefaultInstance();
        // endregion

        // region ____________________ GEOCODING  ____________________
        /*if (Geocoder.isPresent()) {
            new GeoCodingTask(getApplicationContext()).execute("Charlotte, NC");
        }*/
        // endregion

        // region ____________________ LOCATION MANAGER  ____________________
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // endregion

        // region ____________________ GOOGLE MAPS  ____________________
        // implement OnMapReadyCallback
        /*MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mapFragment);
        fragmentTransaction.commit();

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    }
                })
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();*/
        // endregion

    }

    // region ____________________ INTENT ____________________
    public static String KEY = "KEY";
    public static String DATA_KEY = "DATA_KEY";
    public static int REQ_CODE_KEY_1 = 1;
    public static String VALUE_KEY = "VALUE_KEY";
    ParcelablePojoForIntent parcelablePojoForIntent;
    SerializablePojoForIntent serializablePojoForIntent;

    public void goToNextActivityAction(View view) {
        // region ____________________ 1 Empty Intent ____________________
        /*
        Intent intentObj = new Intent(this, NextActivity.class);
        startActivity(intentObj);
        */
        // endregion

        // region ____________________ 2 Intent Passing Values ____________________
        /*
        Intent intentObj = new Intent(this, NextActivity.class);
        parcelablePojoForIntent = new ParcelablePojoForIntent(1, "var");
        intentObj.putExtra(KEY, parcelablePojoForIntent);
        //intentObj.putExtra(KEY, "some text");
        startActivity(intentObj);
        */
        // endregion

        // region ____________________ 3 Intent Passing Values - Serializable ____________________
        /*Intent intentObj = new Intent(this, NextActivity.class);
        serializablePojoForIntent = new SerializablePojoForIntent();
        serializablePojoForIntent.setVariable1(1);
        serializablePojoForIntent.setVariable2("var2");
        serializablePojoForIntent.setVariable3("var3");
        intentObj.putExtra(KEY, serializablePojoForIntent);
        startActivity(intentObj);*/
        // endregion

        // region ____________________ 4 Intent waiting for return value ____________________
        /*
        Intent intentObj = new Intent(this, NextActivity.class);
        parcelablePojoForIntent = new ParcelablePojoForIntent(1, "var");
        intentObj.putExtra(DATA_KEY, parcelablePojoForIntent);
        intentObj.setAction(DATA_KEY);    // required only if you need to check what action to perform
        startActivityForResult(intentObj, REQ_CODE_KEY_1);
        */
        // endregion
    }

    // endregion

    // region ____________________ INTENT ACTIVITY / CALENDAR RESULT ____________________
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String value = "";

        // Move this code in onCreate.
        //imageButtonGallery = (ImageButton) findViewById(R.id.image_button_gallery);

        if (resultCode == RESULT_OK) {
            // region ____________________ RESULT FROM INTENT RETURN ____________________
            /*if (requestCode == REQ_CODE_KEY_1) {
                value = data.getExtras().getString(VALUE_KEY); // Do something with value
            }*/
            // endregion

            // region ____________________ IMAGE FROM GALLERY ____________________
            /*if (requestCode == SELECT_PICTURE && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = Util.getBitmapFromUri(selectedImageUri, getContentResolver());
                    imageButtonGallery.setImageBitmap(bitmap);
                    imageButtonGallery.setAdjustViewBounds(true);
                    imageButtonGallery.setMaxHeight(150);
                    imageButtonGallery.setMaxWidth(150);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            // endregion

            // region ____________________ GOOGLE SIGN IN RESULT ____________________
            /*if (requestCode == GOOGLE_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }*/
            // endregion
        } else if (resultCode == RESULT_CANCELED) { //DO Nothing
        }
    }
    // endregion

    // region ____________________ CONNECTION CHECK ____________________
    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    // endregion

    // region  ____________________ CALENDAR  ____________________
    // *** implement interface DatePickerDialog.OnDateSetListener ***
    /*public void calendarAction(View view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        // set date
    }*/
    // endregion

    // region  ____________________ GALLERY ____________________
    /*private static final int SELECT_PICTURE = 2;
    private ImageButton imageButtonGallery;

    public void galleryAction(View view) {
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }*/

    // remaining code of image select from gallery is in OnResultAction
    // endregion

    // region ____________________ POP UP (CONTEXT MENU) ____________________
    /*ArrayList popUpItems;

    public void selectItemAction(View view) {
        popUpItems = new ArrayList<String>();
        popUpItems.add("One");
        popUpItems.add("Two");

        if (popUpItems != null && popUpItems.size() != 0) {
            registerForContextMenu(view);
            openContextMenu(view);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Pick an Item");

        for (int i = 0; i < popUpItems.size(); i++) {
            menu.add(0, i, 0, popUpItems.get(i).toString());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        String selectedItem = popUpItems.get(selectedItemId).toString();

        return super.onContextItemSelected(item);
    }*/
    // endregion

    // region ____________________ DOWNLOAD IMAGE TASK ____________________
    // *** implements DownloadImageTask.IDownloadImageTask ***
    /*@Override
    public void setupImageData(Bitmap image) {
        // set image
    }

    @Override
    public void startDownloadImageTaskProgress() {
        // progressDialog.show();
    }

    @Override
    public void stopDownloadImageTaskProgress() {
        // progressDialog.dismiss();
    }*/
    // endregion

    // region ____________________ JSON PARSING ____________________
    ListViewAdapter listViewAdapter;
    ListView listViewName;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerViewName;

    LinearLayoutManager horizontalLayoutManager;

    //FirebaseRecyclerAdapter<PojoForJSONParsing, RecyclerViewHolder> adapter;
    FirebaseListAdapter<PojoForJSONParsing> adapter;

    Query query;

    @Override
    public void setupJSONParsedData(final ArrayList<PojoForJSONParsing> result) {
        //progressDialog.dismiss();

        // region ____________________ LIST VIEW ____________________
        // move this line to onCreate method
        /*listViewName = (ListView) findViewById(R.id.list_view_name);

        listViewAdapter = new ListViewAdapter(this, result);
        listViewAdapter.setNotifyOnChange(true);
        listViewName.setAdapter(listViewAdapter);

        listViewName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listViewAdapter.remove(result.get(position));
            }
        });*/
        // endregion

        // region ____________________ RECYCLER VIEW ____________________
        /*// move this line to onCreate method
        recyclerViewName = (RecyclerView) findViewById(R.id.recycler_view_name);

        recyclerViewAdapter = new RecyclerViewAdapter(this, result);
        recyclerViewName.setAdapter(recyclerViewAdapter);

        // Vertical
        recyclerViewName.setLayoutManager(new LinearLayoutManager(this));

        // Horizonatal
        //horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //horizontalLayoutManager.setSmoothScrollbarEnabled(true);
        //recyclerViewName.setLayoutManager(horizontalLayoutManager);*/
        // endregion

        // region ____________________ FIREBASE UI - RECYCLER VIEW ADAPTER ____________________
        /*// Move this line in OnCreateMethod
        recyclerViewName = (RecyclerView) findViewById(R.id.recycler_view_name);
        recyclerViewName.setLayoutManager(new LinearLayoutManager(this));

        // Inserting into Firebase database
        for (int i = 0; i < result.size(); i++) {
            rootRef.child("Items").push().setValue(result.get(i));
        }

        adapter = new FirebaseRecyclerAdapter<PojoForJSONParsing, RecyclerViewHolder>(
                PojoForJSONParsing.class,
                R.layout.recycler_view_item_1,
                RecyclerViewHolder.class,
                rootRef.child("Items").orderByChild("variable1")
        ) {
            @Override
            protected void populateViewHolder(RecyclerViewHolder viewHolder, final PojoForJSONParsing model, final int position) {
                TextView fullNameTextView = viewHolder.textViewTitle;
                ImageView thumbnailImageView = viewHolder.imageViewThumbnail;

                final View view = viewHolder.view;
                try {
                    fullNameTextView.setText(model.getVariable1() + " " + model.getVariable2() + " " + Util.prettyDate(model.getVariable4().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Picasso.with(MainActivity.this)
                        .load(model.getVariable3())
                        .resize(200, 200)
                        .centerCrop().into(thumbnailImageView);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getRef(position).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };

        recyclerViewName.setAdapter(adapter);*/
        // endregion

        // region ____________________ FIREBASE UI - LIST VIEW ADAPTER ____________________
        /*// Move this line in OnCreateMethod
        listViewName = (ListView) findViewById(R.id.list_view_name);

        // Inserting into Firebase database
        for (int i = 0; i < result.size(); i++) {
            rootRef.child("Items").push().setValue(result.get(i));
        }

        query = rootRef.child("Items").orderByChild("variable1");
        adapter = new FirebaseListAdapter<PojoForJSONParsing>(
                this,
                PojoForJSONParsing.class,
                R.layout.list_view_item_1,
                query
        ) {
            @Override
            protected void populateView(View v, PojoForJSONParsing model, final int position) {
                TextView fullNameTextView = (TextView) v.findViewById(R.id.text_view_title);
                ImageView thumbnailImageView = (ImageView) v.findViewById(R.id.image_view_thumb);

                try {
                    fullNameTextView.setText(model.getVariable1() + " " + model.getVariable2() + " " + Util.prettyDate(model.getVariable4().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Picasso.with(MainActivity.this)
                        .load(model.getVariable3())
                        .resize(200, 200)
                        .centerCrop().into(thumbnailImageView);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getRef(position).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };

        listViewName.setAdapter(adapter);*/
        // endregion

        // region ____________________ REALM LIST VIEW ____________________
        /*// move this line to onCreate method
        listViewName = (ListView) findViewById(R.id.list_view_name);

        final ArrayList<PojoForJSONParsing> pojos = result;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < pojos.size(); i++) {
                    PojoForJSONParsing pojo = pojos.get(i);
                    PojoForJSONParsing realmPojo = realm.createObject(PojoForJSONParsing.class);
                    realmPojo.setVariable1(pojo.getVariable1());
                    realmPojo.setVariable2(pojo.getVariable2());
                    realmPojo.setVariable3(pojo.getVariable3());
                    realmPojo.setVariable4(pojo.getVariable4());

                    //realm.copyToRealm(pojos.get(i));
                }
            }
        });

        realmPojos = realm.where(PojoForJSONParsing.class).findAll();
        realmListViewAdapter = new RealmListViewAdapter(this, realmPojos);

        listViewName.setAdapter(realmListViewAdapter);

        listViewName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realmPojos.deleteFromRealm(position);
                    }
                });
            }
        });*/
        // endregion

        // region ____________________ REALM RECYCLER VIEW ____________________
        /*// move this line to onCreate method
        recyclerViewName = (RecyclerView) findViewById(R.id.recycler_view_name);
        recyclerViewName.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<PojoForJSONParsing> pojos = result;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < pojos.size(); i++) {
                    PojoForJSONParsing pojo = pojos.get(i);
                    PojoForJSONParsing realmPojo = realm.createObject(PojoForJSONParsing.class);
                    realmPojo.setVariable1(pojo.getVariable1());
                    realmPojo.setVariable2(pojo.getVariable2());
                    realmPojo.setVariable3(pojo.getVariable3());
                    realmPojo.setVariable4(pojo.getVariable4());

                    //realm.copyToRealm(pojos.get(i));
                }
            }
        });

        realmPojos = realm.where(PojoForJSONParsing.class).findAll();
        rlmRecyclerViewAdapter = new RlmRecyclerViewAdapter(this, realmPojos);

        recyclerViewName.setAdapter(rlmRecyclerViewAdapter);*/
        // endregion
    }
    // endregion

    // region ____________________ TOP MENU ____________________
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_1) {
            // do something
        } else if (item.getItemId() == R.id.action_settings) {
            Intent preferenceActivity = new Intent(MainActivity.this, PreferenceActivity.class);
            startActivity(preferenceActivity);
        }

        return super.onOptionsItemSelected(item);
    }*/
    // endregion

    // region ____________________ LOGIN ____________________
    private EditText editTextEmail;
    private EditText editTextPassword;

    public void loginAction(View view) {
        editTextEmail = (EditText) findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) findViewById(R.id.edit_text_password);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.equals("")) {
            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
        }
    }
    // endregion

    // region ____________________ GO TO SIGNUP ____________________
    public void GoToSignUpAction(View view) {
        Intent intentObj = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intentObj);
    }
    // endregion

    // region ____________________ GOOGLE SIGNIN ____________________
    /*public static int GOOGLE_SIGN_IN = 3;
    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void googleSignOut(View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                // Signed Out
            }
        });
    }*/
    // endregion

    // region ____________________ FIREBASE UI - RECYCLER VIEW ADAPTER ____________________
   /* public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageViewThumbnail;
        TextView textViewTitle;

        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            imageViewThumbnail = (ImageView) itemView.findViewById(R.id.image_view_thumb);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            view = itemView;
        }
    }*/
    // endregion

    // region ____________________ REALM RECYCLER VIEW - DELETE ____________________
    /*public void deleteItem(final int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmPojos.deleteFromRealm(position);
            }
        });
    }*/
    // endregion


    RecyclerView recyclerViewTrip;
    FirebaseRecyclerAdapter<Trip, TripRecyclerViewHolder> adapterTrip;

    @Override
    protected void onResume() {
        super.onResume();

        // region ____________________ LOCATION MANAGER  ____________________
        /*if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS not enabled.")
                    .setMessage("Would you like to enable the GPS settings?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Permission Denied! Closing App.", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("demo", location.getLatitude() + ", " + location.getLongitude());

                    // ____________________ REMOVING LISTNER  ____________________
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.removeUpdates(locationListener);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, locationListener);
            }
        }*/
        // endregion

        recyclerViewTrip = (RecyclerView) findViewById(R.id.recycler_view_trips);
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(this));

        adapterTrip = new FirebaseRecyclerAdapter<Trip, TripRecyclerViewHolder>(
                Trip.class,
                R.layout.recycler_view_item_trips,
                TripRecyclerViewHolder.class,
                rootRef.child("Trips")
        ) {
            @Override
            protected void populateViewHolder(TripRecyclerViewHolder viewHolder, final Trip model, final int position) {
                TextView textViewTrip = viewHolder.textViewTrip;
                TextView textViewCity = viewHolder.textViewCity;
                ImageView imageViewMap = viewHolder.imageViewMap;
                ImageView imageViewAddPlace = viewHolder.imageViewAddPlace;
                LinearLayout containerLayout = viewHolder.containerLayout;

                final View view = viewHolder.view;

                textViewTrip.setText(model.getVariable2());
                textViewCity.setText(model.getVariable3());

                HashMap<String, Place> mp = model.getVariable5();
                if (mp != null) {
                    for (Map.Entry<String, Place> entry : mp.entrySet()) {
                        String key = entry.getKey();
                        Place value = entry.getValue();

                        TextView textView = new TextView(MainActivity.this);
                        textView.setText(value.getVariable3());
                        containerLayout.addView(textView);

                        ImageView imageView = new ImageView(MainActivity.this);
                        imageView.setImageResource(R.drawable.d);
                        imageView.setMaxWidth(5);
                        imageView.setMaxHeight(5);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        containerLayout.addView(imageView);
                        // ...
                    }
                }

                imageViewAddPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentTripRef = adapterTrip.getRef(position);
                        Intent intentObj = new Intent(MainActivity.this, AddPlaceActivity.class);
                        intentObj.putExtra(KEY, model);
                        startActivity(intentObj);
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //adapterTrip.getRef(position).removeValue();
                        //adapterTrip.notifyDataSetChanged();
                    }
                });
            }
        };

        recyclerViewTrip.setAdapter(adapterTrip);
    }

    public static class TripRecyclerViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textViewTrip;
        TextView textViewCity;
        ImageView imageViewMap;
        ImageView imageViewAddPlace;
        LinearLayout containerLayout;

        public TripRecyclerViewHolder(final View itemView) {
            super(itemView);
            textViewTrip = (TextView) itemView.findViewById(R.id.text_view_trip_name);
            textViewCity = (TextView) itemView.findViewById(R.id.text_view_city_name);
            imageViewMap = (ImageView) itemView.findViewById(R.id.image_view_map);
            imageViewAddPlace = (ImageView) itemView.findViewById(R.id.image_view_add_places);
            containerLayout = (LinearLayout) itemView.findViewById(R.id.PlacesContainer);
            view = itemView;
        }
    }

    // region ____________________ LOCATION MANAGER - PERMISSION RESULT ____________________
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Denied! Closing App.", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }*/
    // endregion

    // region ____________________ GOOGLE MAPS - MAP READY ____________________
    /*@Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(80.84, 35.22))
                .title("Charlotte"));
    }*/
    // endregion

    public void GoToAddTrips(View view) {
        Intent intentObj = new Intent(this, AddTripActivity.class);
        startActivity(intentObj);
    }
}