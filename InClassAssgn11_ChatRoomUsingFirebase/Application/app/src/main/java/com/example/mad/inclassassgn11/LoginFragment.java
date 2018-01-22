package com.example.mad.inclassassgn11;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    EditText editTextEmail;
    EditText editTextPassword;

    Button buttonLogin;
    Button buttonCreateAccount;

    LoginFragmentInterface listner;

    String email = "";
    String password = "";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listner = (LoginFragmentInterface) getActivity();

        editTextEmail = (EditText) getActivity().findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) getActivity().findViewById(R.id.edit_text_password);

        buttonLogin = (Button) getActivity().findViewById(R.id.button_login);
        buttonCreateAccount = (Button) getActivity().findViewById(R.id.button_create_new_account);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    listner.signIn(email, password);
                }
            }
        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.goToCreateAccount();
            }
        });
    }

    public interface LoginFragmentInterface {
        void goToCreateAccount();

        void signIn(String email, String password);
    }
}
