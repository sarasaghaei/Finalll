package ir.sarasaghaei.final_project.Ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.broadcast.CheckInternet;
import ir.sarasaghaei.final_project.service.downloade;

public class DownMusicActivity extends AppCompatActivity {
    TextView btnback;
    ProgressDialog progressDialog;
    String medianame;
    downloade mydownloade = new downloade(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downmusic);

        Check_user();
        Notifi();


        //---- for exit app
        exit.activityList.add(this);

        CheckInternet checkInternet = new CheckInternet();
        registerReceiver(checkInternet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));


        init();
        setsetting();
    }

    private void init() {
        btnback = findViewById(R.id.btnback);
        //----------------- back and exit in activity-------------
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void Check_user() {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
        if (!sharedPreferences.contains(Const.USER)) {
            Toast.makeText(this, "کابر گرامی شما ثبت نام نکرده اید", Toast.LENGTH_SHORT).show();
            View view_dialog_Checkuser = LayoutInflater.from(this).inflate(R.layout.alertdialog_checkuser, null);
            final AlertDialog dialog_Checkuser;
            AlertDialog.Builder bilder = new AlertDialog.Builder(this);
            bilder.setView(view_dialog_Checkuser);
            dialog_Checkuser = bilder.create();
            dialog_Checkuser.show();
            Button btndialog_login = (Button) view_dialog_Checkuser.findViewById(R.id.btndialog_login);
            Button btndialog_cancel = (Button) view_dialog_Checkuser.findViewById(R.id.btndialog_cancel);
            final View layout_view = (View) view_dialog_Checkuser.findViewById(R.id.l_login);
            btndialog_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_view.setVisibility(View.VISIBLE);
                }
            });
            btndialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_Checkuser.dismiss();
                    finish();
                }
            });
            Button btnlogin = (Button) view_dialog_Checkuser.findViewById(R.id.btnlogin);
            final EditText etphone = (EditText) view_dialog_Checkuser.findViewById(R.id.etphone);
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone_number = etphone.getText().toString();
                    if (!phone_number.matches("(\\+98|0)?9\\d{9}")) {
                        Toast.makeText(getApplicationContext(), "شماره وارد شده معتبر نمی باشد دوباره بررسی کنید", Toast.LENGTH_SHORT).show();
                    } else {
                        if (phone_number != null && !phone_number.equals("")) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Const.SHEAREDPRF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Const.USER, phone_number);
                            Log.e(Const.TAG, "SharedPreferences :           creat user");
                            editor.apply();
                            Const.CHECK_USER = true;
                            dialog_Checkuser.dismiss();
                            Toast.makeText(getApplicationContext(), "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            });


            Log.e(Const.TAG, "SharedPreferences :      Not   exist user");
        }
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
                View drawer_layout = findViewById(R.id.layou_toolbar);
                drawer_layout.setBackgroundColor(Color.parseColor(Toolbarcolor));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = this.getWindow();
                    window.setStatusBarColor(Color.parseColor(Toolbarcolor));
                    window.setNavigationBarColor(Color.parseColor(Toolbarcolor));
                }
            }
        }

    }

    public void onclick_download(View view) {
        String url = view.getTag().toString();
        medianame = ((TextView) view).getText().toString();

        if (isNetworkConnected()) {

            mydownloade.downloade(url,medianame);
        }

       /* medianame = "a1";
        String path = "/sdcard/" + medianame + ".mp3";

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath()+ path));
        mediaPlayer.setLooping(true);
        mediaPlayer.start();*/
    }

    public void Notifi() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        androidx.core.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Downlode..");
        builder.setContentText(medianame);
        builder.setSmallIcon(R.drawable.end_downloade);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.playmusic));
        builder.addAction(R.drawable.play_music, "playyyyyyyyyyyyy", null);
        builder.setOngoing(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "downlode_end", NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId("1");
            notificationManager.createNotificationChannel(channel);
        }
        Notification notify = builder.build();
        notificationManager.notify(1, notify);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }
}

