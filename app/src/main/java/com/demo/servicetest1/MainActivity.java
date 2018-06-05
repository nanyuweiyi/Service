package com.demo.servicetest1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.start_service)
    Button startServiceBtn;
    @BindView(R.id.stop_service)
    Button stopServiceBtn;
    @BindView(R.id.bind_service)
    Button bindService;
    @BindView(R.id.unbind_service)
    Button unbindService;
    @BindView(R.id.intent_service)
    Button intentService;

    private MyService.MyBinder myBinder;

    private boolean isBind;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.startDownLoad();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_service)
    public void onViewClickStart() {
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
    }

    @OnClick(R.id.stop_service)
    public void onViewClickStop() {
        Intent stoptIntent = new Intent(this, MyService.class);
        stopService(stoptIntent);
    }


    @OnClick({R.id.bind_service, R.id.unbind_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                isBind = bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                if (isBind) {
                    unbindService(connection);
                    isBind = false;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind) {
            unbindService(connection);
            isBind = false;
        }
    }

    @OnClick(R.id.intent_service)
    public void onViewClickedIntent() {
        MyIntentService.startUploadImg(this);
    }
}
