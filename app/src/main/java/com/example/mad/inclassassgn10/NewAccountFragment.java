package com.example.mad.inclassassgn10;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NewAccountFragment extends Fragment {
    EditText editTextFullName;
    EditText editTextEmail;
    EditText editTextPassword;

    Button buttonSignup;
    Button buttonCancel;

    SignupFragmentInterface listner;
    private FirebaseAuth firebaseAuth;

    public NewAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listner = (SignupFragmentInterface) getActivity();

        editTextFullName = (EditText) getActivity().findViewById(R.id.edit_text_full_name);
        editTextEmail = (EditText) getActivity().findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) getActivity().findViewById(R.id.edit_text_password);

        buttonSignup = (Button) getActivity().findViewById(R.id.button_sing_up);
        buttonCancel = (Button) getActivity().findViewById(R.id.button_cancel);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (fullName.equals("")) {
                    Toast.makeText(getActivity(), "Please enter full name", Toast.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        listner.goToLogin();
                                    } else {
                                        Toast.makeText(getActivity(), "Incorrect Data Passed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.goToLogin();
            }
        });
    }

    public interface SignupFragmentInterface {
        public void goToLogin();
    }
}
