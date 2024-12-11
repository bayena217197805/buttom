package com.example.buttomenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
private Homfrag homfrag;
private DashFrag dashfrag;
private Loginfrag loginfrag;
private Signup signupfrag;
private BottomNavigationView bottomNavigationView;
public static FrameLayout homFrame;
public static FrameLayout dashFrame;
public static FrameLayout loginFrame;
public static FrameLayout signupfram;
public static boolean islogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homFrame=findViewById(R.id.home_fram);
        dashFrame=findViewById(R.id.dash_fram);
        loginFrame=findViewById(R.id.login_fram);
        signupfram=findViewById(R.id.signup_fram);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        begin();
    }

    private void begin() {
        homfrag=new Homfrag();
        dashfrag=new DashFrag();
        loginfrag=new Loginfrag();
        signupfrag=new Signup();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fram,homfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.dash_fram,dashfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fram,loginfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.signup_fram,signupfrag).commit();
        dashFrame.setVisibility(View.INVISIBLE);
        homFrame.setVisibility(View.INVISIBLE);
        signupfram.setVisibility(View.INVISIBLE);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_login&&!islogin){
                    homFrame.setVisibility(View.INVISIBLE);
                    dashFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.VISIBLE);
                    signupfram.setVisibility(View.INVISIBLE);

                }
                if(item.getItemId()==R.id.menuhome&&islogin){
                    homFrame.setVisibility(View.VISIBLE);
                    dashFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    signupfram.setVisibility(View.INVISIBLE);

                }
                if(item.getItemId()==R.id.menudashboard&&islogin){
                    homFrame.setVisibility(View.INVISIBLE);
                    dashFrame.setVisibility(View.VISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    signupfram.setVisibility(View.INVISIBLE);

                }
                if(item.getItemId()==R.id.menu_signup&&islogin){
                    homFrame.setVisibility(View.INVISIBLE);
                    dashFrame.setVisibility(View.INVISIBLE);
                    loginFrame.setVisibility(View.INVISIBLE);
                    signupfram.setVisibility(View.VISIBLE);

                }
                return true;
            }
        });
    }
}