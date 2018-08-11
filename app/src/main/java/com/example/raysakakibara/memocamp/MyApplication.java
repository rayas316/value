package com.example.raysakakibara.memocamp;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
