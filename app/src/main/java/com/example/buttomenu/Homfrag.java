package com.example.buttomenu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class Homfrag extends Fragment {

private FloatingActionButton btnlogout;
private Button playbutton;
    public Homfrag() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homfrag, container, false);
        btnlogout=view.findViewById(R.id.btn_logout);
        playbutton=view.findViewById(R.id.playbutton);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                MainActivity.homFrame.setVisibility(View.INVISIBLE);
                MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.firstRoundFrame.setVisibility(View.VISIBLE);
                MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
    private void logout(){
        FirebaseAuth.getInstance().signOut();
        MainActivity.islogin=false;
        MainActivity.loginFrame.setVisibility(View.VISIBLE);
        MainActivity.homFrame.setVisibility(View.INVISIBLE);
        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);

    }
}