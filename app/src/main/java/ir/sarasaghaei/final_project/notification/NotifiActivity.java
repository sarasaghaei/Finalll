package ir.sarasaghaei.final_project.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;

import ir.sarasaghaei.final_project.R;

public class NotifiActivity extends AppCompatActivity {
    String medianame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi);

        Intent intent = getIntent();
        medianame = intent.getStringExtra("medianame");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        androidx.core.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Downlode..");
        builder.setContentText(medianame);
        builder.setSmallIcon(R.drawable.end_downloade);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1","downlode_end", NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId("1");
            notificationManager.createNotificationChannel(channel);
        }
        Notification notify = builder.build();
    }
}