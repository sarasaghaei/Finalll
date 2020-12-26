package ir.sarasaghaei.final_project.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;


public class CheckInternet extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final AlertDialog alertDialog;
        View view = LayoutInflater.from(context).inflate(R.layout.alertdialog, null);
        AlertDialog.Builder bilder = new AlertDialog.Builder(context);
        bilder.setView(view);
        alertDialog = bilder.create();
        if (intent.getExtras() != null) {
            if (!isOnline(context)) {



                alertDialog.show();
                alertDialog.setCancelable(false);
                Button btn_ok = view.findViewById(R.id.btn_ok);
                Button btn_exit = view.findViewById(R.id.btn_exit);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isOnline(context)) {
                            alertDialog.dismiss();
                            //context.startActivity(((Activity) context).getIntent());
                        }
                    }
                });
                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Log.e(Const.TAG, "Exit in app");

                    }
                });
            } else {
                alertDialog.dismiss();

            }

        }

    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            return false;
        }
    }


}

