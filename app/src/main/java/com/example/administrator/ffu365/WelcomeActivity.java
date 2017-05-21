package com.example.administrator.ffu365;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/19.
 */

public class WelcomeActivity extends Activity{
    private ImageView welcomeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeImg= (ImageView) findViewById(R.id.welcome_img);
        ObjectAnimator animator=ObjectAnimator.ofFloat(welcomeImg,"alpha",0.7f,1.0f);
        animator.setDuration(3000);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent it=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

}
