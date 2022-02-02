package com.example.projetandroid4a.servercomm;

import android.app.Activity;
import android.content.Context;

/**
 * motherclass of classes implementing Server Response Interfaces while
 * outside of the context scope
 */
public abstract class SRExternalClassAbs {
    public Context context;
    public Activity activity;
    public SRExternalClassAbs(Activity acitivity){
        this.activity = acitivity;
        this.context=acitivity;
    }
}
