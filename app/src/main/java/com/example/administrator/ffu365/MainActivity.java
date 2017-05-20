package com.example.administrator.ffu365;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.administrator.ffu365.adapter.HomePagerAdapter;
import com.example.administrator.ffu365.fragment.CenterFragment;
import com.example.administrator.ffu365.fragment.CollectionFragment;
import com.example.administrator.ffu365.fragment.HomeFragment;
import com.example.administrator.ffu365.fragment.MessageFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public ViewPager viewPager;
    private RadioButton mHomeRb,mCollectRb,mMessageRb,mCenterRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        initView();
        initData();
    }

    private void initView() {
       mHomeRb= (RadioButton) findViewById(R.id.home_rb);
        mCollectRb= (RadioButton) findViewById(R.id.collection_rb);
        mMessageRb= (RadioButton) findViewById(R.id.message_rb);
        mCenterRb= (RadioButton) findViewById(R.id.center_rb);
        mHomeRb.setOnClickListener(this);
        mCollectRb.setOnClickListener(this);
        mMessageRb.setOnClickListener(this);
        mCenterRb.setOnClickListener(this);
    }

    private void initData() {
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CollectionFragment());
        fragments.add(new MessageFragment());
        fragments.add(new CenterFragment());
        HomePagerAdapter homePagerAdapter=new HomePagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(homePagerAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.home_rb:
                  viewPager.setCurrentItem(0,false);
                  break;
              case R.id.collection_rb:
                  viewPager.setCurrentItem(1,false);
                  break;
              case R.id.message_rb:
                  viewPager.setCurrentItem(2,false);
                  break;
              case R.id.center_rb:
                  viewPager.setCurrentItem(3,false);
                  break;
          }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
       switch (position){
           case 0:
               mHomeRb.setChecked(true);
               break;
           case 1:
               mCollectRb.setChecked(true);
               break;
           case 2:
               mMessageRb.setChecked(true);
               break;
           case 3:
               mCenterRb.setChecked(true);
               break;
       }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
