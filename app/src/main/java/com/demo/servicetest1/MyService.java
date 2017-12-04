package com.demo.servicetest1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * desc
 *
 * @author tnn
 * @time 2017/12/4.
 */
public class MyService extends Service {

    private MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyService:", "---oncreate");

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("这是通知的标题");
        builder.setContentText("这是通知的内容");
        builder.setContentInfo("这是通知的补充内容");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        Log.d("MyService:", "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService:", "---onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyService:", "---onDestroy");
        handler.removeCallbacks(runnable);
    }

    //--------------------------------------------------
    Handler handler = new Handler();
    class MyBinder extends Binder {
        public void startDownload(){
            handler.postDelayed(runnable, 1000); //每隔1s执行
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, 1000);
                Log.e("MyService:", "执行具体方法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
