package com.example.mad.inclassassgn11;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccountFragment extends Fragment {
    EditText editTextFullName;
    EditText editTextEmail;
    EditText editTextPassword;

    Button buttonSignup;
    Button buttonCancel;

    SignupFragmentInterface listner;

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
                } else if (!isValidEmail(email)) {
                    Toast.makeText(getActivity(), "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (!email.contains(".com")) {
                    Toast.makeText(getActivity(), "Please enter valid email (xyz@xyx.com)", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(getActivity(), "Please enter password with length > 6.", Toast.LENGTH_SHORT).show();
                } else {
                    listner.signUp(fullName, email, password);
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

    public final static boolean isValidEmail(CharSequence emailString) {
        if (emailString == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(emailString).matches();
        }
    }

    public interface SignupFragmentInterface {
        void goToLogin();
        void signUp(String fullName, String email, String password);
    }
}
