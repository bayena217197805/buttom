package com.example.buttomenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class Homfrag extends Fragment {

private FloatingActionButton btnlogout;
    public Homfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homfrag, container, false);
        btnlogout=view.findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }
    private void logout(){
        FirebaseAuth.getInstance().signOut();
        MainActivity.islogin=false;
        MainActivity.loginFrame.setVisibility(View.VISIBLE);
        MainActivity.homFrame.setVisibility(View.INVISIBLE);
        MainActivity.dashFrame.setVisibility(View.INVISIBLE);

    }
}