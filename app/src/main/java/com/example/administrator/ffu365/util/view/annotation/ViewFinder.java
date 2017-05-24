package com.example.administrator.ffu365.util.view.annotation;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceActivity;
import android.view.View;

/**
 * Author: wyouflf
 * Date: 13-9-9
 * Time: 下午12:29
 */
public class ViewFinder {

    private View view;
    private Activity activity;
    private PreferenceActivity preferenceActivity;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int id) {
        return activity == null ? view.findViewById(id) : activity.findViewById(id);
    }

    public Context getContext() {
        if (view != null) return view.getContext();
        if (activity != null) return activity;
        if (preferenceActivity != null) return preferenceActivity;
        return null;
    }
}
