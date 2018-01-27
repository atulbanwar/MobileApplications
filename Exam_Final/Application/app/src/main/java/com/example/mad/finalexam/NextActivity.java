package com.example.mad.finalexam;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.storage.FirebaseStorage;

public class NextActivity extends Activity {
    // ____________________ INTENT 4 - MAIN ____________________
    String requestAction;

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // region ____________________ INTENT - 2 - MAIN ____________________
        /*
        ParcelablePojoForIntent parcelablePojoForIntent = null;
        if (getIntent().getExtras() != null) {
            parcelablePojoForIntent = getIntent().getExtras().getParcelable(MainActivity.KEY);
            //String str = getIntent().getExtras().getString(DisplayActivity.KEY);
        }
        String variable2 = parcelablePojoForIntent.getVariable2();
        */
        // endregion

        // region ____________________ INTENT - 3 - MAIN ____________________
        /*
        SerializablePojoForIntent serializablePojoForIntent = null;
        if (getIntent().getExtras() != null) {
            serializablePojoForIntent = (SerializablePojoForIntent) getIntent().getExtras().getSerializable(MainActivity.KEY);
        }
        String variable2 = serializablePojoForIntent.getVariable2();
        */
        // endregion

        // region ____________________ INTENT - 4 - MAIN ____________________
        /*
        if (getIntent().getExtras() != null) {
            requestAction = getIntent().getAction();
            if (requestAction == MainActivity.DATA_KEY1) {}
        }
        */
        // endregion

    }

    public void cancelAction(View view) {
        // region ____________________ SIGN OUT  ____________________
        MainActivity.firebaseAuth.signOut();
        // endregion

        // region ____________________ INTENT DATA RETURN - 4 - MAIN ____________________
        /*
        if (requestAction == MainActivity.DATA_KEY1) {
            Intent intentObj = new Intent();
            intentObj.putExtra(MainActivity.VALUE_KEY, "some text");
            setResult(RESULT_OK, intentObj);
            finish();
        }
        */
        // endregion

        finish();
    }
}
