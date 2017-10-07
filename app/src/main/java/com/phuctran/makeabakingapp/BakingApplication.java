package com.phuctran.makeabakingapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by phuctran on 10/3/17.
 */

public class BakingApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

}
