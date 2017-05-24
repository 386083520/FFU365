package com.example.administrator.ffu365.util;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hui on 2016/8/26.
 */
public class ActivityManagerUtil {
    private Map<String,Activity>  mAcitivities;
    public static ActivityManagerUtil mInstance;
    private ActivityManagerUtil(){
        mAcitivities = new HashMap<>();
    }
    public static ActivityManagerUtil getInstance(){
        if(mInstance == null){
            synchronized (ActivityManagerUtil.class){
                if(mInstance == null){
                    mInstance = new ActivityManagerUtil();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity){
        mAcitivities.put(activity.getClass().getName() ,activity);
    }

    public void finishActivity(Activity activity){
        Activity finishActivity = mAcitivities.get(activity.getClass().getName());
        finishActivity.finish();
        mAcitivities.remove(activity.getClass().getName());
    }

    public void finishActivity(Class<? extends  Activity> activityClazz){
        Activity finishActivity = mAcitivities.get(activityClazz.getName());
        finishActivity.finish();
        mAcitivities.remove(activityClazz.getName());
    }

}
