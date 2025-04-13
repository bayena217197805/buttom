package com.example.buttomenu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Loginfrag extends Fragment {

TextInputEditText et_email,txtpassword;
Button btnlogin,buttonsignup;
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
        buttonsignup=view.findViewById(R.id.buttonsignup);
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                MainActivity.homFrame.setVisibility(View.INVISIBLE);
                MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                MainActivity.signupFrame.setVisibility(View.VISIBLE);
                MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                MainActivity.theEndFrame.setVisibility(View.INVISIBLE);


            }
        });

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

    @SuppressLint("SuspiciousIndentation")
    private void checkEmailpass() {
        String email, password;
        email = et_email.getText().toString();
        password = txtpassword.getText().toString();
        if (!(email.equals("") || password.equals(""))) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                        et_email.setText(null);
                        txtpassword.setText(null);
                        MainActivity.islogin = true;

                        // إضافة الكود لتحميل البيانات من Firestore
                        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        FirebaseFirestore.getInstance().collection("clinet")
                                .whereEqualTo("Email", userEmail)
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    if (!querySnapshot.isEmpty()) {
                                        // جلب البيانات من قاعدة البيانات
                                        DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
                                        Long score = doc.getLong("Score");
                                        Long round = doc.getLong("Round");

                                        MainActivity.score = score != null ? score.intValue() : 0;
                                        MainActivity.currentRound = round != null ? round.intValue() : 1;

                                        Log.d("Login", "Score: " + MainActivity.score + ", Round: " + MainActivity.currentRound);

                                        // الانتقال إلى الصفحة الرئيسية أو اللعبة
                                        MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.homFrame.setVisibility(View.VISIBLE);
                                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);
                                    } else {
                                        Toast.makeText(getContext(), "User not found in database", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Failed to load data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please fill in the fields", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            et_email.setText(null);
            txtpassword.setText(null);
            MainActivity.islogin=true;
            MainActivity.loginFrame.setVisibility(View.INVISIBLE);
            MainActivity.homFrame.setVisibility(View.VISIBLE);
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
}