package com.expenses.breakexpenses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    private static final int SLEEP_TIMER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        ImageView imageView =findViewById(R.id.logo);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        imageView.startAnimation(animation);
        TextView textView= findViewById(R.id.appName);
        textView.startAnimation(animation);
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();

    }

    private class LogoLauncher extends Thread{
        public void run(){
            try{
                sleep(500 * SLEEP_TIMER);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if(load())
            {
                Intent intent = new Intent(SplashActivity.this, LoginPage.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
            else
            {
                SharedPreferences sharedPreferences = getSharedPreferences("userData",Context.MODE_PRIVATE);
                Intent intent = new Intent(SplashActivity.this, HomePage.class);
                intent.putExtra("phone",sharedPreferences.getString("user_phone",""));
                intent.putExtra("password",sharedPreferences.getString("user_password",""));
                startActivity(intent);
                SplashActivity.this.finish();
            }

        }
    }

    private boolean load() {

        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_phone","");
        String password = sharedPreferences.getString("user_password","");
        if(email.equals("") || password.equals(""))
        {
            return true;
        }
        else
            return false;

    }
}