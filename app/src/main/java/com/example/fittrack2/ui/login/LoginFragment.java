package com.example.fittrack2.ui.login;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.example.fittrack2.R;
import com.example.fittrack2.ui.homepage.HomeFragment;
import com.example.fittrack2.ui.runpage.RunHistoryAdapter;
import com.example.fittrack2.ui.runpage.RunViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private RecyclerView recyclerView;
    private Button btnSignIn;
    private TextView email, pass;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.login_layout, container, false);

        btnSignIn = root.findViewById(R.id.btn_login);
        email = root.findViewById(R.id.txt_email);
        pass = root.findViewById(R.id.txt_pass);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Amplify.Auth.signIn(
                        email.getText().toString(),
                        pass.getText().toString(),
                        result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentArea, new HomeFragment()).commit();
            }
        });



        return root;
    }
}
