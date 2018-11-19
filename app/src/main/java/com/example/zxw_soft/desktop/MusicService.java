package com.example.zxw_soft.desktop;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MusicService extends Service implements Runnable{

    private static final String TAG = "MusicPlayerService";
    private static final int NOTIFICATION_ID = 1; // 如果id设置为0,会导致不能设置为前台service
    public static MediaPlayer mediaPlayer = null; //创建MediaPlayer 进行播放

    private String url = null;
    private String MSG = null;
    private static int curposition;//第几首音乐
    private musicBinder musicbinder = null;

    public MusicService() {
        Log.i(TAG, "MusicService:-----------------------");
        musicbinder = new musicBinder();

    }

    /**
     * 自定义的 Binder对象
     */
    public class musicBinder extends Binder {
        public MusicService getPlayInfo(){
            return MusicService.this;
        }
    }

    //通过bind 返回一个IBinder对象，然后改对象调用里面的方法实现参数的传递
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        if(mediaPlayer == null){
            mediaPlayer= new MediaPlayer();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand......3");
        // /storage/emulated/0/Music/Download/Selena Gomez - Revival/Hands to Myself.mp3
        if(intent!=null){
            MSG = intent.getStringExtra("MSG");
            if(MSG.equals("0")){
                url = intent.getStringExtra("url");
                curposition = intent.getIntExtra("curposition",0);
                Log.i(TAG, url + "......." + Thread.currentThread().getName());
                player();
            }else if(MSG.equals("1")){
                mediaPlayer.pause();
            }else if(MSG.equals("2")){
                mediaPlayer.start();
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void player(){


    }

}
