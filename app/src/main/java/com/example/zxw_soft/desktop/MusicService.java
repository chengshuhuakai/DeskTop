package com.example.zxw_soft.desktop;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class MusicService extends Service implements Runnable{

    private static final String TAG = "MusicPlayerService";
    private static final int NOTIFICATION_ID = 1; // 如果id设置为0,会导致不能设置为前台service
    public static MediaPlayer mediaPlayer = null; //创建MediaPlayer 进行播放
    private int currentPosition = 0;// 设置默认进度条当前位置
    private String url = null;
    private String MSG = null;
    private static int curposition;//第几首音乐
    private musicBinder musicbinder = null;

    public MusicService() {
        Log.i(TAG, "MusicService:---------------------1");
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

    // 刷新进度条 ,时间
    @Override
    public void run() {
        Log.i(TAG,Thread.currentThread().getName()+"......run...");
        int total = mediaPlayer.getDuration();// 总时长
        while (mediaPlayer != null && currentPosition < total) {
            try {
                Thread.sleep(1000);
                if (mediaPlayer != null) {
                    currentPosition = mediaPlayer.getCurrentPosition();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        // MusicActivity.audioSeekBar.setProgress(CurrentPosition);
        }

    }


    @Override
    public void onCreate() {
        Log.i(TAG,"onCreat......2");
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


    /**
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand......3");
        // /storage/emulated/0/Music/Download/Selena Gomez - Revival/Hands to Myself.mp3
        if(intent!=null){
            MSG = intent.getStringExtra("MSG");
            if(MSG.equals("0")){
                //通过传递的Intent 拿到url curpostion等关键信息
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
        // 释放
        Log.i(TAG,"onDestroy......");
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //关闭线程
        Thread.currentThread().interrupt();
        stopForeground(true);
    }


    private void player(){
        //对多种状态进行分析  1. 正在播放 2.暂停
         /* if(mediaPlayer.isPlaying()){
            Log.i(TAG,"palyer......running....");
            // 暂停
            mediaPlayer.pause();
            mediaPlayer.reset();
        }*/
        try {
//            Log.i(TAG,"palyer......new....");
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.setLooping(true);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            // 设置进度条最大值
//            MusicActivity.audioSeekBar.setMax(mediaPlayer.getDuration());
            //开启新线程
//            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


