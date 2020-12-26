package ir.sarasaghaei.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

import ir.sarasaghaei.final_project.Ui.exit;
import ir.sarasaghaei.final_project.Ui.myanimatin;
import ir.sarasaghaei.final_project.broadcast.BroadCastEndCreatDB;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;
import ir.sarasaghaei.final_project.entity.database.BiyabDBHelper;
import ir.sarasaghaei.final_project.service.LoadedbService;

public class SplashActivity extends AppCompatActivity {
    TextView tverror, tvrefresh;
    myanimatin myview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //---- for exit app
        exit.activityList.add(this);

        init();

        Chech_Exsit_Database();
        setsetting();

    }

    private void Chech_Exsit_Database() {
        if (new BiyabDBHelper(this).checkDataBase()) {
            Log.e(Const.TAG, "database is exist");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 2000);
        } else {
            //-----Check internet statuse
            CheckInternet checkInternet = new CheckInternet();
            registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            Log.e(Const.TAG, "database is NOT exist");
            if (isNetworkConnected()) {
                Log.e(Const.TAG, "start service");
                Intent intent = new Intent(this, LoadedbService.class);
                startService(intent);
                Log.e(Const.TAG, "end service");
                BroadCastEndCreatDB getbradcast = new BroadCastEndCreatDB();
                registerReceiver(getbradcast, new IntentFilter("DB_Created_successfully"));

            } else {

                tverror.setVisibility(View.VISIBLE);
                tvrefresh.setVisibility(View.VISIBLE);
            }

        }
    }

    private void init() {
        myview = findViewById(R.id.showlogo);
        tverror = findViewById(R.id.tverror);
        tvrefresh = findViewById(R.id.tverror);
        tverror.setVisibility(View.INVISIBLE);
        tvrefresh.setVisibility(View.INVISIBLE);

        tvrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (myview.cv > 800) {
                    myview.cv -= 100;
                    myview.invalidate();
                }
            }
        }, 0, 100);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    private void setsetting() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains("Backgroundcolor")) {
            String Backgroundcolor = sharedPreferences.getString("Backgroundcolor", "");
            if (Backgroundcolor != "") {
                View whole_layout = findViewById(R.id.base_layout);
                whole_layout.setBackgroundColor(Color.parseColor(Backgroundcolor));
            }
        }
        if (sharedPreferences.contains("Toolbarcolor")) {
            String Toolbarcolor = sharedPreferences.getString("Toolbarcolor", "");
            if (Toolbarcolor != "") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = this.getWindow();
                    window.setStatusBarColor(Color.parseColor(Toolbarcolor));
                    window.setNavigationBarColor(Color.parseColor(Toolbarcolor));
                }
            }
        }

    }


}