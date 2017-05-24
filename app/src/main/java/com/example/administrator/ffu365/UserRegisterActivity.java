package com.example.administrator.ffu365;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ffu365.model.UserLoginResult;
import com.example.administrator.ffu365.model.UserRequestCodeResult;
import com.example.administrator.ffu365.ui.TimerCodeButton;
import com.example.administrator.ffu365.ui.VerificationCodeButton;
import com.example.administrator.ffu365.util.ActivityManagerUtil;
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

public class UserRegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private Button mUserRegisterBt;
    private VerificationCodeButton mSendCodeBt;
    private TextView mUserAgreementTv;
    private CheckBox mCheckPasswordCb;
    private EditText mUserPhoneEt,mUserPasswordEt,mUserCodeEt;

    private static Handler mHandler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerUtil.getInstance().addActivity(this);
        setContentView(R.layout.activity_user_register);
        mUserRegisterBt= (Button) findViewById(R.id.user_register_bt);
        mCheckPasswordCb = (CheckBox) findViewById(R.id.check_password_cb);
        mUserAgreementTv = (TextView) findViewById(R.id.user_agreement_tv);
        mSendCodeBt = (VerificationCodeButton) findViewById(R.id.send_code_bt);
        mUserPhoneEt = (EditText) findViewById(R.id.user_phone_et);
        mUserPasswordEt = (EditText) findViewById(R.id.user_password_et);
        mUserCodeEt = (EditText) findViewById(R.id.user_code_et);

        mUserAgreementTv.setText(Html.fromHtml("我已阅读并同意<font color='#24cfa2'>《天天防腐》用户协议</font>"));
        mCheckPasswordCb.setOnCheckedChangeListener(this);
        mUserRegisterBt.setOnClickListener(this);
        mSendCodeBt.setOnClickListener(this);
        //mSendCodeBt.bindPhoneEditText(mUserPhoneEt);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.send_code_bt:
                 mSendCodeBt.startLoad();
                 //Toast.makeText(UserRegisterActivity.this,"hhh",Toast.LENGTH_SHORT).show();
                 requestUserCode();
                 break;
             case R.id.user_register_bt:
                 dealUserRegister();
                 //setResult(RESULT_OK);
                 //finish();

                 break;
         }
    }

    private void dealUserRegister() {
        String userPhone = mUserPhoneEt.getText().toString().trim();
        String password = mUserPasswordEt.getText().toString().trim();
        String userCode = mUserCodeEt.getText().toString().trim();

        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(userCode)){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
            return;
        }


        OkHttpClient okHttpClient = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("appid", "1");
        builder.addFormDataPart("verify_code",userCode);
        builder.addFormDataPart("cell_phone",userPhone); //  添加多个参数
        builder.addFormDataPart("password", password);// MD5 AES
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Member&a=register")
                .post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {// 请求的回调
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {  // 这个不是运行在主线程中

                String result = response.body().string();
                Log.e("TAG", result);
                Gson gson = new Gson();
                final UserLoginResult loginResult = gson.fromJson(result, UserLoginResult.class);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dealRegisterResult(loginResult);
                    }
                });
            }
        });
    }
    private void dealRegisterResult(UserLoginResult loginResult){
        if(loginResult.getErrcode() == 1){
            SharedPreferences sp =  getSharedPreferences("info",MODE_PRIVATE);
            sp.edit().putBoolean("is_login",true).commit();
            UserLoginResult.DataBean userData =  loginResult.getData();
            Gson gson = new Gson();
            String uesrInfoStr =  gson.toJson(userData);
            sp.edit().putString("user_info",uesrInfoStr).commit();
            ActivityManagerUtil.getInstance().finishActivity(this);
            ActivityManagerUtil.getInstance().finishActivity(UserLoginActivity.class);

        }else {
            Toast.makeText(this,loginResult.getErrmsg(),Toast.LENGTH_LONG).show();
        }
    }

    public void requestUserCode(){
        String userPhone=mUserPhoneEt.getText().toString().trim();
        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient okHttpClient=new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("appid", "1");
        builder.addFormDataPart("sms_type","3");
        builder.addFormDataPart("cell_phone",userPhone);
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Util&a=sendVerifyCode")
                .post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {// 请求的回调
            @Override
            public void onFailure(Call call, IOException e) {
                // 失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {  // 这个不是运行在主线程中
                String result = response.body().string();
                Log.e("TAG", result);
                Gson gson = new Gson();
                UserRequestCodeResult codeResult = gson.fromJson(result, UserRequestCodeResult.class);
                dealCodeResult(codeResult);



            }
        });
    }

    private void dealCodeResult(final UserRequestCodeResult codeResult) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if(codeResult.errcode == 1){
                    mSendCodeBt.aginAfterTime(60);
                }else{
                    Toast.makeText(UserRegisterActivity.this,codeResult.errmsg,Toast.LENGTH_LONG).show();
                    mSendCodeBt.setNoraml();
                }
            }
        });

    }
}
