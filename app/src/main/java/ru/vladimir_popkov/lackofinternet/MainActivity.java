package ru.vladimir_popkov.lackofinternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mStatus;
    SharedPreferences.OnSharedPreferenceChangeListener stateListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            if (s.equals(Check.NETWORK_KEY)){
                updateStatus(sharedPreferences.getInt(Check.NETWORK_KEY, Check.UNKNOWN));
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatus = (TextView) findViewById(R.id.text);
        SharedPreferences sp = getSharedPreferences(Check.NETWORK_PREFS, Context.MODE_PRIVATE);
        updateStatus(sp.getInt(Check.NETWORK_KEY, Check.UNKNOWN));
        sp.registerOnSharedPreferenceChangeListener(stateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = getSharedPreferences(Check.NETWORK_PREFS, Context.MODE_PRIVATE);
        sp.unregisterOnSharedPreferenceChangeListener(stateListener);
    }

    private void updateStatus(int state){
        switch (state){
            case Check.MOBILE:
                mStatus.setText(R.string.str_mobile);
                break;
            case Check.WIFI:
                mStatus.setText(R.string.str_wifi);
                break;
            case Check.UNKNOWN:
                mStatus.setText(R.string.str_unknown);
                break;
            case Check.NO_INTERNET:
                mStatus.setText(R.string.str_no_conn);
                break;
        }
    }
}
