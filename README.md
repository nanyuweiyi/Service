# Service
## 安卓Service&amp;IntentService使用

1、启动Service的2种方式：

a、Activity直接启动:startService  生命周期：onCreate()->onStartCommand()->onDestory()

    oncrate()方法只会运行一次，每次都调用onStartCommand（）。
    
    这种service可以无限地运行下去，必须调用stopSelf()方法或者其他组件调用stopService()方法来停止它。

    当service被停止时，系统会销毁它。

b、Bind启动:bindService  生命周期：onCreate()->onBind()->onUnBind()->onDestory()

    客户可以通过一个IBinder接口和service进行通信。

　　客户可以通过 unbindService()方法来关闭这种连接。

　　一个service可以同时和多个客户绑定，当多个客户都解除绑定之后，系统会销毁service。
  
  
 这两条路径并不是完全分开的。

　　即是说，你可以和一个已经调用了 startService()而被开启的service进行绑定。

　　比如，一个后台音乐service可能因调用 startService()方法而被开启了，稍后，可能用户想要控制播放器或者得到一些当前歌曲
  的信息，可以通过bindService()将一个activity和service绑定。这种情况下，stopService()或 stopSelf()实际上并不能停
  止这个service，除非所有的客户都解除绑定。
   
 
 ### IntentService
 
 onHandleIntent() 可以执行耗时操作，并且执行完自动销毁。
 
### 如何保证Service不被杀死：

1、onStartCommand方法，返回START_STICKY。
```
@Override  
public int onStartCommand(Intent intent, int flags, int startId) {  
   flags = START_STICKY;  
   return super.onStartCommand(intent, flags, startId);  
} 
```
2、提升service进程优先级
```
<service  
    android:name="com.dbjtech.acbxt.waiqin.UploadService"  
    android:enabled="true" >  
    <intent-filter android:priority="1000" >  
        <action android:name="com.dbjtech.myservice" />  
    </intent-filter>  
</service>  
```
3、将Service变为前台服务
4、在onDestroy方法里重启service




    








