package com.example.buttomenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
private Homfrag homfrag;
private Instructions instructionsfrag;
private Signup signupfrag;
private Loginfrag loginfrag;
private Details detailsfrag;
private SecondRound secondRoundfrag;
private FirstRound firstRoundfrag;
private ThirdRound thirdRoundfrag;
private RoundFour roundFourfrag;
private RoundFive roundFivefrag;
private BottomNavigationView bottomNavigationView;
public static FrameLayout homFrame;
public static FrameLayout signupFrame;
public static FrameLayout instructionsFrame;
public static FrameLayout loginFrame;
public static FrameLayout detailsfram;
public static FrameLayout secondRoundFrame;
public static FrameLayout firstRoundFrame;
public static FrameLayout  thirdRoundFrame;
public static FrameLayout roundFourFrame;
public static FrameLayout roundFiveFrame;
public static boolean islogin=false;
public static int score=0;
public static int currentRound = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homFrame=findViewById(R.id.home_fram);
        signupFrame=findViewById(R.id.signup_fram);
        instructionsFrame=findViewById(R.id.instructions_fram);
        loginFrame=findViewById(R.id.login_fram);
        detailsfram=findViewById(R.id.details_fram);
        secondRoundFrame=findViewById(R.id.secondRound_fram);
        firstRoundFrame=findViewById(R.id.firstRound_fram);
        thirdRoundFrame=findViewById(R.id.thirdRound_fram);
        roundFourFrame=findViewById(R.id.roundFour_fram);
        roundFiveFrame=findViewById(R.id.roundFive_fram);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            String userEmail = mAuth.getCurrentUser().getEmail();

            FirebaseFirestore.getInstance().collection("clinet")
                    .whereEqualTo("Email", userEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Map<String, Object> data = queryDocumentSnapshots.getDocuments().get(0).getData();

                            Object scoreObj = data.get("Score");
                            if (scoreObj != null) {
                                MainActivity.score = Integer.parseInt(scoreObj.toString());
                            }




                        }
                    });
        } else {
            // المستخدم مش مسجل، عرض واجهة تسجيل الدخول
            loginFrame.setVisibility(View.VISIBLE);
        }
        begin();
    }

    private void begin() {
        homfrag=new Homfrag();
        instructionsfrag=new Instructions();
        loginfrag=new Loginfrag();
        signupfrag=new Signup();
        detailsfrag=new Details();
        secondRoundfrag=new SecondRound();
        firstRoundfrag=new FirstRound();
        thirdRoundfrag=new ThirdRound();
        roundFourfrag=new RoundFour();
        roundFivefrag=new RoundFive();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fram,homfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.instructions_fram,instructionsfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fram,loginfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.details_fram,detailsfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.signup_fram,signupfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.secondRound_fram,secondRoundfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.firstRound_fram,firstRoundfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.thirdRound_fram,thirdRoundfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.roundFour_fram,roundFourfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.roundFive_fram,roundFivefrag).commit();


        instructionsFrame.setVisibility(View.INVISIBLE);
        homFrame.setVisibility(View.INVISIBLE);
        detailsfram.setVisibility(View.INVISIBLE);
        signupFrame.setVisibility(View.INVISIBLE);
        secondRoundFrame.setVisibility(View.INVISIBLE);
        firstRoundFrame.setVisibility(View.INVISIBLE);
        thirdRoundFrame.setVisibility(View.INVISIBLE);
        roundFourFrame.setVisibility(View.INVISIBLE);
        roundFiveFrame.setVisibility(View.INVISIBLE);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_login&&!islogin){
                    homFrame.setVisibility(View.INVISIBLE);
                    instructionsFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.VISIBLE);
                    detailsfram.setVisibility(View.INVISIBLE);
                    signupFrame.setVisibility(View.INVISIBLE);
                    secondRoundFrame.setVisibility(View.INVISIBLE);
                    firstRoundFrame.setVisibility(View.INVISIBLE);
                    thirdRoundFrame.setVisibility(View.INVISIBLE);
                    roundFourFrame.setVisibility(View.INVISIBLE);
                    roundFiveFrame.setVisibility(View.INVISIBLE);

                }
                if(item.getItemId()==R.id.menuhome&&islogin){
                    homFrame.setVisibility(View.VISIBLE);
                    instructionsFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    detailsfram.setVisibility(View.INVISIBLE);
                    signupFrame.setVisibility(View.INVISIBLE);
                    secondRoundFrame.setVisibility(View.INVISIBLE);
                    firstRoundFrame.setVisibility(View.INVISIBLE);
                    thirdRoundFrame.setVisibility(View.INVISIBLE);
                    roundFourFrame.setVisibility(View.INVISIBLE);
                    roundFiveFrame.setVisibility(View.INVISIBLE);

                }
                if(item.getItemId()==R.id.menuinstructions&&islogin){
                    homFrame.setVisibility(View.INVISIBLE);
                    instructionsFrame.setVisibility(View.VISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    detailsfram.setVisibility(View.INVISIBLE);
                    signupFrame.setVisibility(View.INVISIBLE);
                    secondRoundFrame.setVisibility(View.INVISIBLE);
                    firstRoundFrame.setVisibility(View.INVISIBLE);
                    thirdRoundFrame.setVisibility(View.INVISIBLE);
                    roundFourFrame.setVisibility(View.INVISIBLE);
                    roundFiveFrame.setVisibility(View.INVISIBLE);

                }
                if(item.getItemId()==R.id.menu_details&&islogin){
                    homFrame.setVisibility(View.INVISIBLE);
                    instructionsFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    detailsfram.setVisibility(View.VISIBLE);
                    signupFrame.setVisibility(View.INVISIBLE);
                    secondRoundFrame.setVisibility(View.INVISIBLE);
                    firstRoundFrame.setVisibility(View.INVISIBLE);
                    thirdRoundFrame.setVisibility(View.INVISIBLE);
                    roundFourFrame.setVisibility(View.INVISIBLE);
                    roundFiveFrame.setVisibility(View.INVISIBLE);

                }
                return true;
            }
        });
    }
}