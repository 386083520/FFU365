package com.example.administrator.ffu365;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.ffu365.model.UserLoginResult;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.security.AccessControlException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/22.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mUserLogoIv;
    private static final int ALBUM_OK = 0x0011;
    private static final int CUT_OK = 0x0013;
    private static final int CAMERA_REQUEST = 0x0012;
    private File tempFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mUserLogoIv = (ImageView) findViewById(R.id.user_logo);
        tempFile = new File(Environment.getExternalStorageDirectory(),"temp.png");
        mUserLogoIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showDialog();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this,R.style.dialog);
        View dialogView = View.inflate(this,R.layout.photo_choose_dialog,null);
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button cancelBt = (Button) dialogView.findViewById(R.id.user_cancel);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 关闭dialog
                dialog.dismiss();
            }
        });
        Button imageDepotBt = (Button) dialogView.findViewById(R.id.image_depot);
        imageDepotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, ALBUM_OK);
                dialog.dismiss();
            }
        });
        Button photoCamreBt = (Button) dialogView.findViewById(R.id.photo_camre);
        photoCamreBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempFile));
                startActivityForResult(getImageByCamera, CAMERA_REQUEST);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            if(requestCode == ALBUM_OK){
                Uri uri = data.getData();
                clipImage(uri);
            }
            if(requestCode == CAMERA_REQUEST){
                clipImage(Uri.fromFile(tempFile));
            }
            if(requestCode == CUT_OK){
                Bundle extras = data.getExtras();
                if (extras != null){
                    Bitmap bitmap = extras.getParcelable("data");
                    mUserLogoIv.setImageBitmap(bitmap);
                    //saveBitmapToFile(bitmap);
                    upLoadImage();
                }
            }
        }
    }

    private void upLoadImage() {
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2.构建参数的body  MultipartBody.FORM 表单形式
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // 2.2封装参数
        builder.addFormDataPart("appid", "1");

        // 2.3获取uid
        String userInfoStr =  getSharedPreferences("info", Context.MODE_PRIVATE).getString("user_info",null);
        if(!TextUtils.isEmpty(userInfoStr)) {

            Gson gson = new Gson();
            UserLoginResult.DataBean userInfo = gson.fromJson(userInfoStr, UserLoginResult.DataBean.class);
            builder.addFormDataPart("uid",userInfo.getMember_info().getUid());
            Log.e("TAG",userInfo.getMember_info().getUid());
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), tempFile);
        builder.addFormDataPart("file", tempFile.getName(), fileBody);

        Request request = new Request.Builder().url("http://v2.ffu365.com/index.php?m=Api&c=Member&a=userUploadAvatar")
                .post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {  // 这个不是运行在主线程中
                String result = response.body().string();
                Log.e("TAG", result);
            }
        });
    }

    private void clipImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_OK);
    }
}
