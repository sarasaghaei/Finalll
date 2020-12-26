package ir.sarasaghaei.final_project.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.MainActivity;
import ir.sarasaghaei.final_project.SplashActivity;

public class BroadCastEndCreatDB extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(Const.TAG, "getdBroadcast :           successful");
        intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        Log.e(Const.TAG, "close SplashActivity and open MainActivity");
    }
}
