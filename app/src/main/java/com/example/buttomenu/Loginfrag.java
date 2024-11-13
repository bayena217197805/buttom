package com.example.buttomenu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Loginfrag extends Fragment {

TextInputEditText et_email,txtpassword;
Button btnlogin;
private FirebaseAuth mAuth;

    public Loginfrag() {
        // Required empty public constructor
    }






    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_loginfrag, container, false);
        et_email=view.findViewById(R.id.et_email);
        txtpassword=view.findViewById(R.id.et_pass);
        btnlogin=view.findViewById(R.id.buttonlogij);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailpass();
                String userName=et_email.getText().toString();
                String password=txtpassword.getText().toString();
                if(userName.equals("bayena")&&password.equals("123456")){
                    Toast.makeText(getContext(), "loginsucsses", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void checkEmailpass(){
        String email,password;
        email=et_email.getText().toString();
        password=txtpassword.getText().toString();
        if(!(email.equals("")||password.equals(""))){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "login sucsseful", Toast.LENGTH_SHORT).show();
                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                        MainActivity.homFrame.setVisibility(View.VISIBLE);
                        MainActivity.dashFrame.setVisibility(View.INVISIBLE);
                    }
                    else Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        Toast.makeText(getActivity(), "please fill in files", Toast.LENGTH_SHORT).show();


    }
}