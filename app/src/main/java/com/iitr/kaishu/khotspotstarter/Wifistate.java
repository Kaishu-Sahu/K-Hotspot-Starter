package com.iitr.kaishu.khotspotstarter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.widget.Toast;

import java.lang.reflect.Method;

import static android.content.Context.WIFI_SERVICE;

public class Wifistate extends BroadcastReceiver {
SharedPreferences check;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION .equals(intent.getAction())) {
            check = context.getSharedPreferences("switch", context.MODE_PRIVATE);
            if(check.getBoolean("one",false)){
            SupplicantState state = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
            if (SupplicantState.isValidState(state)
                    && state == SupplicantState.COMPLETED) {

                if(checkConnectedToDesiredWifi(context)){
                    starthotspot(context);
                }
            }}
        }

    }
    private boolean checkConnectedToDesiredWifi(Context context) {
        boolean connected = false;

        String desiredMacAddress = "xx:xx:xx:xx:xx:xx";
        WifiManager wifiManager =
                (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);

        WifiInfo wifi = wifiManager.getConnectionInfo();
        if (wifi != null) {
            String bssid = wifi.getBSSID();
            String anothermac=check.getString("mac","");
                if(desiredMacAddress.equals(bssid)||anothermac.equals(bssid)){
                    Toast.makeText(context, "Mac Matched", Toast.LENGTH_SHORT).show();
                    connected = true;
                }


        }

        return connected;
    }
    public boolean starthotspot(Context context) {
        WifiManager wifimanager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiConfiguration wificonfiguration = new WifiConfiguration();

        try {
            wifimanager.setWifiEnabled(false);
            Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifimanager, wificonfiguration, true);
            Toast.makeText(context, "Hotspot started", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed to start hotspot", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }
}
