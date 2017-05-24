package com.example.administrator.ffu365;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.ffu365.model.AlipayResult;
import com.example.administrator.ffu365.model.WXPayResult;
import com.example.administrator.ffu365.util.alipay.PayUtil;
import com.example.administrator.ffu365.util.view.ViewUtils;
import com.example.administrator.ffu365.util.view.annotation.ViewById;
import com.example.administrator.ffu365.util.view.annotation.event.OnClick;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class RechargeCoinActivity extends AppCompatActivity implements PayUtil.PayListener {
    @ViewById(R.id.coin_number)
    private EditText mCoinNumberEt;
    @ViewById(R.id.alipay_cb)
    private Checkable mAlipayCb;
    @ViewById(R.id.wx_cb)
    private Checkable mWXCb;
    private IWXAPI mWxApi;
    private static Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_coin);
        ViewUtils.inject(this);

        mWxApi = WXAPIFactory.createWXAPI(this,"wxa8080d15a32e2ff7");
        mWxApi.registerApp("wxa8080d15a32e2ff7");
    }
    @OnClick(R.id.payment_bt)
    private void paymentBtClick(){
        String coinNumber = mCoinNumberEt.getText().toString().trim();
        if(TextUtils.isEmpty(coinNumber)){
            Toast.makeText(this,"请输入需要充值的金币个数",Toast.LENGTH_LONG).show();
            return;
        }

        if(mAlipayCb.isChecked()){
            alipay(coinNumber);
        }

        if(mWXCb.isChecked()){
            wxpay(coinNumber);
        }



    }

    private void wxpay(String coinNumber) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("appid", "1");
        builder.addFormDataPart("uid", "71");
        builder.addFormDataPart("coin_nums",coinNumber);
        builder.addFormDataPart("payment_tool","2");
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=V2Payment&a=coinPrepareToPay")
                .post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String result = response.body().string();
                Log.e("TAG", result);
                Gson gson = new Gson();

                WXPayResult wxPayResult = gson.fromJson(result, WXPayResult.class);

                callLoadWxPay(wxPayResult.getData().getPayinfo());
            }
        });
    }

    private void callLoadWxPay(WXPayResult.DataBean.PayinfoBean payInfo) {
        PayReq req = new PayReq();
        req.appId = payInfo.getAppid();
        req.partnerId = payInfo.getPartnerid();
        req.prepayId = payInfo.getPrepayid();
        req.packageValue = payInfo.getPackage_value();
        req.nonceStr = payInfo.getNoncestr();
        req.timeStamp = payInfo.getTimestamp()+"";
        req.sign = payInfo.getSign();

        mWxApi.sendReq(req);


    }

    private void alipay(String coinNumber) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("appid", "1");
        builder.addFormDataPart("uid", "71");
        builder.addFormDataPart("coin_nums",coinNumber);
        builder.addFormDataPart("payment_tool","1");
        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=V2Payment&a=coinPrepareToPay")
                .post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }
            // 请求的回调
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                // 成功  数据在response里面  获取后台给我们的JSON 字符串
                String result = response.body().string();
                Log.e("TAG", result);
                Gson gson = new Gson();
                final AlipayResult alipayResult = gson.fromJson(result, AlipayResult.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callLocalAlipay(alipayResult.getData().getPayinfo());
                    }
                });
            }
        });
    }

    private void callLocalAlipay(String payinfo) {
        PayUtil payUtil = new PayUtil(this);
        payUtil.alipay(payinfo);
        payUtil.setPayListener(this);
    }

    @OnClick({ R.id.wx_ll, R.id.alipay_ll,R.id.wx_cb ,R.id.alipay_cb})
    private void paymentOptionClick(View view) {
        mWXCb.setChecked(false);
        mAlipayCb.setChecked(false);

        switch (view.getId()) {
            case R.id.wx_ll:
            case R.id.wx_cb:
                mWXCb.setChecked(true);
                break;

            case R.id.alipay_ll:
            case R.id.alipay_cb:
                mAlipayCb.setChecked(true);
                break;
        }
    }

    @Override
    public void paySuccess() {
        Toast.makeText(this,"支付成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void payFail() {
        Toast.makeText(this,"支付失败",Toast.LENGTH_LONG).show();
    }
}
