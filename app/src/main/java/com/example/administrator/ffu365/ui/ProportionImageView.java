package com.example.administrator.ffu365.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.ffu365.R;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ProportionImageView extends ImageView {
    float width;
    float height;

    public ProportionImageView(Context context) {
        this(context,null);
    }

    public ProportionImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProportionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context,attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView);
        width = array.getFloat(R.styleable.ProportionImageView_widthProportion, 0);
        height = array.getFloat(R.styleable.ProportionImageView_heightProportion, 0);
        Log.i("Proportion",width+height+"");
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidth=MeasureSpec.getSize(widthMeasureSpec);
        int myHeight= (int) (myWidth*height/width);
        setMeasuredDimension(myWidth,myHeight);


    }




}
