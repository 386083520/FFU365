package com.example.administrator.ffu365;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ffu365.model.HomeDataResult;
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
 * Created by Administrator on 2017/5/21.
 */

public class UserLoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private CheckBox mCheckPasswordCb;
    private EditText mUserPhoneEt,mUserPassEt;
    private Button userLoginBtn;
    private TextView userRegisterTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mCheckPasswordCb= (CheckBox) findViewById(R.id.check_password_cb);
        mUserPhoneEt= (EditText) findViewById(R.id.user_phone_et);
        mUserPassEt= (EditText) findViewById(R.id.user_password_et);
        userLoginBtn= (Button) findViewById(R.id.user_login_bt);
        userRegisterTv= (TextView) findViewById(R.id.user_register_tv);
        mCheckPasswordCb.setOnCheckedChangeListener(this);
        userLoginBtn.setOnClickListener(this);
        userRegisterTv.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            mUserPassEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        }else {
            mUserPassEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Editable etext=mUserPassEt.getText();
        Selection.setSelection(etext,etext.length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_login_bt:
                dealUserLogin();
                break;
            case R.id.user_register_tv:
                Intent intent = new Intent(this,UserRegisterActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }

    private void dealUserLogin() {
        String userPhone=mUserPhoneEt.getText().toString().trim();
        String userPass=mUserPassEt.getText().toString().trim();
        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPass)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient okHttpClient=new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("appid", "1");
        builder.addFormDataPart("cell_phone", userPhone);
        builder.addFormDataPart("password", MD5Util.strToMd5(userPass));
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Member&a=login")
                .post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {// 请求的回调
            @Override
            public void onFailure(Call call, IOException e) {
                // 失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {  // 这个不是运行在主线程中
                // 成功  数据在response里面  获取后台给我们的JSON 字符串
                String result = response.body().string();
                Log.e("TAG2", result);
                Gson gson=new Gson();
                UserLoginResult loginResult=gson.fromJson(result,UserLoginResult.class);
                dealLoginResult(loginResult);



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
          finish();
        }
    }

    private void dealLoginResult(UserLoginResult loginResult) {
        if(loginResult.getErrcode()==1){
            SharedPreferences sp=getSharedPreferences("info",MODE_PRIVATE);
            sp.edit().putBoolean("is_login",true).commit();
            UserLoginResult.DataBean userData=loginResult.getData();
            Gson gson=new Gson();
            String userInfoStr=gson.toJson(userData);
            sp.edit().putString("userInfo",userInfoStr).commit();
            finish();
        }else {
            //Toast.makeText(this,loginResult.getErrmsg(),Toast.LENGTH_SHORT).show();
        }
    }


}
