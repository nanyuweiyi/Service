# Service
安卓Service&amp;intentService使用

1、启动Service的2种方式：

a、Activity直接启动:startService  生命周期：oncreate()->onStartCommand()->onDestory()

    oncrate()方法只会运行一次，每次都调用onStartCommand（）。
    
    这种service可以无限地运行下去，必须调用stopSelf()方法或者其他组件调用stopService()方法来停止它。

    当service被停止时，系统会销毁它。

b、Bind启动:bindService  生命周期：oncreate()->onBind()->onUnBind()->onDestory()

    客户可以通过一个IBinder接口和service进行通信。

　　客户可以通过 unbindService()方法来关闭这种连接。

　　一个service可以同时和多个客户绑定，当多个客户都解除绑定之后，系统会销毁service。
  
  
 这两条路径并不是完全分开的。

　　即是说，你可以和一个已经调用了 startService()而被开启的service进行绑定。

　　比如，一个后台音乐service可能因调用 startService()方法而被开启了，稍后，可能用户想要控制播放器或者得到一些当前歌曲
   的信息，可以通过bindService()将一个activity和service绑定。这种情况下，stopService()或 stopSelf()实际上并不能停
   止这个service，除非所有的客户都解除绑定。
