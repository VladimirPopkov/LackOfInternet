package ru.vladimir_popkov.lackofinternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by v.popkov on 02.05.2018.
 */
public class Check extends BroadcastReceiver {

    public static final int MOBILE = 0;
    public static final int WIFI = 1;
    public static final int UNKNOWN = 2;
    public static final int NO_INTERNET = -1;
    public static final String NETWORK_KEY = "key_network_state";
    public static final String NETWORK_PREFS = "NetWorkState";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        int type;

        if (networkInfo != null && networkInfo.isConnected()){
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                type = MOBILE;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                type = WIFI;
            } else {
                type = UNKNOWN;
            }
        } else {
            type = NO_INTERNET;
        }

        switch (type){
            case MOBILE:
                Toast.makeText(context, context.getText(R.string.str_mobile), Toast.LENGTH_LONG).show();
                break;
            case WIFI:
                Toast.makeText(context, context.getText(R.string.str_wifi), Toast.LENGTH_LONG).show();
                break;
            case UNKNOWN:
                Toast.makeText(context, context.getText(R.string.str_unknown), Toast.LENGTH_LONG).show();
                break;
            case NO_INTERNET:
                Toast.makeText(context, context.getText(R.string.str_no_conn), Toast.LENGTH_LONG).show();
                break;
        }

        SharedPreferences sp = context.getSharedPreferences(NETWORK_PREFS, Context.MODE_PRIVATE);
        sp.edit().putInt(NETWORK_KEY, type).apply();
    }
}
