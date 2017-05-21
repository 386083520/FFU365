package com.example.administrator.ffu365.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.administrator.ffu365.DetailLinkActivity;
import com.example.administrator.ffu365.R;
import com.example.administrator.ffu365.adapter.HomeInfoListAdapter;
import com.example.administrator.ffu365.model.HomeDataResult;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/19.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private ImageView adbannerIv,commendCompanyIv;
    private Context mContext;
    private ListView newsList;
    HomeDataResult homeDataResult;
    View view;
    Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,null);
        mContext=getActivity();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adbannerIv = (ImageView) view.findViewById(R.id.adbanner_iv);
        commendCompanyIv= (ImageView) view.findViewById(R.id.recommended_company);
       newsList= (ListView) view.findViewById(R.id.industry_information_lv);
        adbannerIv.setOnClickListener(this);
        requestHomeData();
    }

    private void requestHomeData() {
        OkHttpClient okHttpClient=new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("appid", "1");
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Index&a=home")
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
                Log.e("TAG", result);
                Gson gson=new Gson();
               homeDataResult=gson.fromJson(result,HomeDataResult.class);

                showHomeDate(homeDataResult.getData());



            }
        });
    }

    private void showHomeDate(final HomeDataResult.DataBean data) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                String adBannerImage=data.getAd_list().get(0).getImage();
                Glide.with(mContext).load(adBannerImage).into(adbannerIv);
                Glide.with(mContext).load(data.getCompany_list().get(0).getImage()).into(commendCompanyIv);
                newsList.setAdapter(new HomeInfoListAdapter(mContext,data.getNews_list()));

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adbanner_iv:
                String adBannerImage=homeDataResult.getData().getAd_list().get(0).getImage();
                Intent it=new Intent(getActivity(), DetailLinkActivity.class);
                it.putExtra(DetailLinkActivity.URL_KEY,adBannerImage);
                startActivity(it);
                break;
        }
    }
}
