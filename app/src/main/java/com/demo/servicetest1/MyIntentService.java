package com.demo.servicetest1;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * desc
 *
 * @author tnn
 * @time 2017/12/4.
 */
public class MyIntentService extends IntentService {

    private boolean isRunning = true;
    private int count = 0;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendServiceStatus("服务启动");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {//执行耗时操作
        if(intent != null){
            handleUploadImg();
        }
    }

    public static void startUploadImg(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        context.startService(intent);
    }

    private void handleUploadImg(){
        //handler.postDelayed(runnable, 1000); //每隔1s执行
        try {
            sendThreadStatus("线程启动", count);
            Thread.sleep(1000);
            sendServiceStatus("服务运行中...");

            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
            }
            sendThreadStatus("线程结束", count);
            sendServiceStatus("服务结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 发送服务状态信息
    private void sendServiceStatus(String status) {
        Log.e("result---", status);
    }

    // 发送线程状态信息
    private void sendThreadStatus(String status, int progress) {
        Log.e("result---", status+"----progress:"+progress);
        NotificationManager manager = showNotification(progress);
//        if(progress == 100){
//            manager.cancel(1);//参数为对应id
//        }
    }

    NotificationManager showNotification(int pro){
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("这是通知的标题");
        if(pro == 100){
            builder.setContentText("下载完成");
        }else{
            builder.setContentText("下载进度："+ pro + "%");
        }

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
        return notificationManager;
    }
}
