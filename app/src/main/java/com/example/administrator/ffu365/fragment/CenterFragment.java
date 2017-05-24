package com.example.administrator.ffu365.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ffu365.MyCoinActivity;
import com.example.administrator.ffu365.R;
import com.example.administrator.ffu365.RechargeCoinActivity;
import com.example.administrator.ffu365.UserInfoActivity;
import com.example.administrator.ffu365.UserLoginActivity;
import com.example.administrator.ffu365.model.UserLoginResult;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CenterFragment extends Fragment implements View.OnClickListener {
    View mCenterView;
    TextView mLoginTextView,mUserExitLoginTv,mUserNameTv,mUserLocationTv;
    private Context mContext;
    private LinearLayout userLoginedLL,mRechargeCoinLl;
    private ImageView mUserHeadIv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCenterView=inflater.inflate(R.layout.fragment_center,null);
        mContext=getActivity();
        return mCenterView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         mLoginTextView= (TextView) mCenterView.findViewById(R.id.user_login_tv);
        mUserExitLoginTv= (TextView) mCenterView.findViewById(R.id.user_exit_login);
        userLoginedLL= (LinearLayout) mCenterView.findViewById(R.id.user_logined_ll);
        mRechargeCoinLl = (LinearLayout) mCenterView.findViewById(R.id.recharge_coin_ll);
        mUserHeadIv= (ImageView) mCenterView.findViewById(R.id.user_head_iv);
        mUserNameTv= (TextView) mCenterView.findViewById(R.id.user_name_tv);
        mUserLocationTv= (TextView) mCenterView.findViewById(R.id.user_location_tv);
        mLoginTextView.setOnClickListener(this);
        mUserExitLoginTv.setOnClickListener(this);
        userLoginedLL.setOnClickListener(this);
        mRechargeCoinLl.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("is_login", false);
        if(isLogin){
            userLoginedLL.setVisibility(View.VISIBLE);
            mLoginTextView.setVisibility(View.GONE);
            String userInfoStr=sp.getString("userInfo","");
            //String userInfoStr = mContext.getSharedPreferences("info", Context.MODE_PRIVATE).getString("userInfo", "");
            Log.e("CenterFragment",userInfoStr);
            if(!TextUtils.isEmpty(userInfoStr)){
                Gson gson=new Gson();
                UserLoginResult.DataBean userInfo = gson.fromJson(userInfoStr, UserLoginResult.DataBean.class);
                Glide.with(mContext).load(userInfo.getMember_info().getMember_avatar()).into(mUserHeadIv);
                mUserNameTv.setText(userInfo.getMember_info().getMember_name());
                mUserLocationTv.setText(userInfo.getMember_info().getMember_location_text());
            }
        }else{
            mLoginTextView.setVisibility(View.VISIBLE);
            userLoginedLL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_login_tv:
                Intent it=new Intent(mContext,UserLoginActivity.class);
                startActivity(it);
                break;
            case R.id.user_exit_login:
                SharedPreferences sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
                sp.edit().putBoolean("is_login",false).commit();
                break;
            case R.id.user_logined_ll:
                Intent intent1 = new Intent(mContext, UserInfoActivity.class);
                startActivity(intent1);
                break;
            case R.id.recharge_coin_ll:
                Intent intent2 = new Intent(mContext, MyCoinActivity.class);
                startActivity(intent2);
                break;
        }

    }
}
