package com.example.andro.android_hid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

/**
 * Broadcast Receiver used to listen to change on device from "Not charging" to "Charging"
 * so it can start script when user connects device with computer
 * Created by Andro on 19.12.2014.
 */
public class PlugInControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            try {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String path = MainActivity.getScriptPath(prefs);
                MainActivity.RunAsRoot(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
