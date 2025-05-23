package com.example.buttomenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class Homfrag extends Fragment {

private FloatingActionButton btnlogout;
private Button playbutton;
private TextView textView;
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
        textView=view.findViewById(R.id.text);
        Animation blinkAnimation= AnimationUtils.loadAnimation(getContext(),R.anim.blink);
        textView.startAnimation(blinkAnimation);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                FirebaseFirestore.getInstance().collection("clinet")
                        .whereEqualTo("Email", email)
                        .get()
                        .addOnSuccessListener(querySnapshot -> {
                            if (!querySnapshot.isEmpty()) {

                                Object roundValue = querySnapshot.getDocuments().get(0).get("CurrentRound");
                                if (roundValue != null) {
                                    MainActivity.currentRound = Integer.parseInt(roundValue.toString());
                                }



                                // وجّهه حسب الجولة الحالية
                                switch (MainActivity.currentRound) {
                                    case 1:
                                        MainActivity.firstRoundFrame.setVisibility(View.VISIBLE);
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);
                                        break;
                                    case 2:
                                        MainActivity.secondRoundFrame.setVisibility(View.VISIBLE);
                                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                                        break;
                                    case 3:
                                        MainActivity.thirdRoundFrame.setVisibility(View.VISIBLE);
                                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);

                                        break;
                                    case 4:
                                        MainActivity.roundFourFrame.setVisibility(View.VISIBLE);
                                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);
                                        break;
                                    case 5:
                                        MainActivity.roundFiveFrame.setVisibility(View.VISIBLE);
                                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);

                                        break;
                                    case 6: // النهاية
                                        MainActivity.theEndFrame.setVisibility(View.VISIBLE);
                                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        break;
                                    default:
                                        MainActivity.firstRoundFrame.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        });
        return view;
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        MainActivity.islogin=false;

        Fragment signupFragment = new Signup();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.signup_fram, signupFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Fragment firstRoundFragment = new FirstRound();
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.firstRound_fram, firstRoundFragment); // استبدال الـ Fragment الحالي بـ FirstRound

        transaction1.commit();
        Fragment secondroundFragment = new SecondRound();
        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
        transaction2.replace(R.id.secondRound_fram, secondroundFragment); // استبدال الـ Fragment الحالي بـ FirstRound

        transaction2.commit();
        Fragment thirdroundFragment = new ThirdRound();
        FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
        transaction3.replace(R.id.thirdRound_fram, thirdroundFragment); // استبدال الـ Fragment الحالي بـ FirstRound

        transaction3.commit();
        Fragment fourthroundFragment = new RoundFour();
        FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
        transaction4.replace(R.id.roundFour_fram, fourthroundFragment); // استبدال الـ Fragment الحالي بـ FirstRound

        transaction4.commit();
        Fragment fifthroundFragment = new RoundFive();
        FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
        transaction5.replace(R.id.roundFive_fram, fifthroundFragment); // استبدال الـ Fragment الحالي بـ FirstRound

        transaction5.commit();
        MainActivity.loginFrame.setVisibility(View.VISIBLE);
        MainActivity.homFrame.setVisibility(View.INVISIBLE);
        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);


    }
}