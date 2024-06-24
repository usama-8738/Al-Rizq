package com.example.alrizq.fcm;

import static androidx.core.app.NotificationCompat.BADGE_ICON_LARGE;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.alrizq.R;
import com.example.alrizq.utils.Constant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notification extends FirebaseMessagingService {
    int id = 1;
    String NotificationType;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Notofication Tag", remoteMessage.toString());

        NotificationType = remoteMessage.getData().get(Constant.REMOTE_MSG_CONTENT_TYPE);
        if (!TextUtils.isEmpty(NotificationType)) {
            showNotification(
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("message"), remoteMessage);
        }

    }

    public void showNotification(String title, String message, RemoteMessage remoteMessage) {
        PendingIntent pendingIntent = null;

        String channel_id = "AlRizk";
//        if (TextUtils.isEmpty(NotificationType)) {
//            Intent intent = new Intent(this, Chat.class);
//            intent.putExtra("receiverId", remoteMessage.getData().get("sender"));
//            intent.putExtra("fcmToken", remoteMessage.getData().get("senderToken"));
//            intent.putExtra("name", remoteMessage.getData().get("title"));
//            intent.putExtra("activity", remoteMessage.getData().get("1"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(channel_id, "Al_Rizk");
            notificationManager.createNotificationChannelGroup(notificationChannelGroup);

            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "Al_Rizk", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(getApplicationContext(), channel_id)
                .setContentTitle(title)
                .setContentText(message)
                .setGroup("Al_Rizk")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setBadgeIconType(BADGE_ICON_LARGE)
                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentIntent(pendingIntent)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(id)
                .setAutoCancel(true);

        notificationManager.notify(id++, builder.build());
    }
}
