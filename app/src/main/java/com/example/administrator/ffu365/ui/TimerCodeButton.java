package com.example.administrator.ffu365.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.ffu365.model.UserLoginResult;
import com.example.administrator.ffu365.util.MD5Util;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/22.
 */

public class TimerCodeButton extends Button{
    private int mCurrentCount=0;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mCurrentCount>0){
                --mCurrentCount;
                countDown(mCurrentCount);
            }else{
              ableStates();

            }

        }
    };
    public TimerCodeButton(Context context) {
        this(context,null);
    }

    public TimerCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimerCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ableStates();
    }
    public void laterOnState(int color){
        this.setEnabled(false);
        this.setText("请稍后...");
        this.setTextColor(color);
    }

    public void ableStates(){
        setEnabled(true);
        setText("获取验证码");
        setBackgroundColor(Color.GREEN);
    }

    public void countDown(int count){
        mCurrentCount=count;
        this.setEnabled(false);
        this.setText(count+"秒后重复");
        mHandler.sendEmptyMessageDelayed(0,1000);

    }


}
