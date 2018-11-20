package com.example.zxw_soft.desktop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MusicActivity extends Activity {

    /**
     * 规定开始音乐、暂停音乐、结束音乐的标志
     */
    public  static final int PLAT_MUSIC=1;
    public  static final int PAUSE_MUSIC=2;
    public  static final int STOP_MUSIC=3;
    public static SharedPreferences.Editor editor;//保存播放模式
    private MediaPlayer mMediaPlayer;
    private String url;
    private ArrayList<MusicBean> musicList;
    private ArrayList<Map<String, Object>> listems;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private ImageView playMode ,playaccelerometer;
    private CheckBox mChkPlayPause;
    private ImageButton mBtnNext;
    private ImageButton mBtnPrev;
    private ImageButton mBtnLoopMode;
    private ImageButton mBtnFileList;
    private ImageButton mBtnSoundEffect;
    private ImageButton mBtnHalfSize;
    private static int currentposition = -1;//当前播放列表里哪首音乐


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //setFullScreen(this);
        transparencyStatusBar(this);
        setContentView(R.layout.activity_music);
        initView(MusicActivity.this);
        //初始化數據
        initData();
        //初始化
        init();
    }

    private void init() {
       intent =  new Intent();
       intent.setAction("player");
       intent.setPackage(getPackageName());

        //默认随机播放,播放模式
        playMode = (ImageView) findViewById(R.id.BtnLoopMode);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        int playmode = sharedPreferences.getInt("play_mode", -1);
        if(playmode == -1){//没有设置模式，默认随机
            editor.putInt("play_mode",0).commit();
        }else{
            //changeMode(playmode);
        }
        //分享按钮
/*        handler = new Handler();
        imageView = (ImageView)findViewById(R.id.click_share);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"我的博客地址：http://blog.csdn.net/i_do_can");
                shareIntent.setType("text/plain");
                //设置分享列表
                startActivity(Intent.createChooser(shareIntent,"分享到"));
            }
        });*/

       /* textView  = (TextView)findViewById(R.id.musicinfo);
        musicListView = (ListView)findViewById(R.id.musicListView);
*/
        //开启服务

    }



    //修改播放模式  单曲循环 随机播放 顺序播放
    int clicktimes = 0;
    private void changeMode(int playmode) {
        switch (clicktimes){
            case 0://随机 --> 顺序
                clicktimes++;
                changeMode(clicktimes);
                break;
            case 1://顺序 --> 单曲
                clicktimes++;
                changeMode(clicktimes);
                break;
            case 2://单曲 --> 随机
                clicktimes = 0;
                changeMode(clicktimes);
                break;
            default:
                break;
        }
    }

    private void initData() {
        //调用扫描方法
        musicList = scanAllAudioFiles();
        //这里其实可以直接在扫描时返回 ArrayList<Map<String, Object>>()
        listems =new ArrayList<>();
        /*解析数据，放置到ListView*/

        for (Iterator iterator = musicList.iterator();iterator.hasNext();) {
            // 创建键值对集合，将 获取的数据生成键值对，重新组合，其实可以放在scanAllAudioFiles()里面
            Map<String, Object> map = new HashMap<String, Object>();
            MusicBean mp3Info = (MusicBean) iterator.next();
//          map.put("id",mp3Info.getId());
            map.put("title", mp3Info.getTitle());
            map.put("artist", mp3Info.getArtist());
            map.put("album", mp3Info.getAlbum());
//          map.put("albumid", mp3Info.getAlbumId());
            map.put("duration", mp3Info.getTime());
            map.put("size", mp3Info.getSize());
            map.put("url", mp3Info.getUrl());
            //map.put("bitmap", R.drawable.musicfile);
            listems.add(map);
        }


           /*SimpleAdapter的参数说明
         * 第一个参数 表示访问整个android应用程序接口，基本上所有的组件都需要
         * 第二个参数表示生成一个Map(String ,Object)列表选项
         * 第三个参数表示界面布局的id  表示该文件作为列表项的组件
         * 第四个参数表示该Map对象的哪些key对应value来生成列表项
         * 第五个参数表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
         * 注意的是map对象可以key可以找不到 但组件的必须要有资源填充  因为 找不到key也会返回null 其实就相当于给了一个null资源
         * 下面的程序中如果 new String[] { "name", "head", "desc","name" } new int[] {R.id.name,R.id.head,R.id.desc,R.id.head}
         * 这个head的组件会被name资源覆盖
         * */
        SimpleAdapter mSimpleAdapter =new SimpleAdapter(this,listems,R.layout.menu_item,new String[] {"bitmap","title","artist", "size","duration"},
                new int[] {R.id.video_imageView,R.id.video_title,R.id.video_singer,R.id.video_size,R.id.video_duration}
        );
        //listView 里加载数据
        //musicListView.setAdapter(mSimpleAdapter);
    }
    private void initView(MusicActivity view) {
        //TODO 设置监听事件
        mChkPlayPause = (CheckBox) findViewById(R.id.ChkPlayPause);
        mBtnNext = (ImageButton) findViewById(R.id.BtnNext);
        mBtnPrev= (ImageButton) findViewById(R.id.BtnPrev);
        mBtnLoopMode = (ImageButton) findViewById(R.id.BtnLoopMode);
        mBtnFileList = (ImageButton) findViewById(R.id.BtnFileList);
        mBtnSoundEffect = (ImageButton) findViewById(R.id.BtnSoundEffect);
        mBtnHalfSize = (ImageButton) findViewById(R.id.BtnHalfSize);

        // 播放
        mChkPlayPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ==true){
                    Toast.makeText(MusicActivity.this,"开始播放",Toast.LENGTH_SHORT).show();
                    // 绑定服务，并开启
                    //点击播放音乐，不过需要判断一下当前是否有音乐在播放，需要关闭正在播放的
                    //position 可以获取到点击的是哪一个，去 musicList 里寻找播放
                    // currentposition = position;
                    Player(currentposition);
                }
            }
        });
        int[] imageButtons  ={R.id.BtnNext,R.id.BtnPrev,R.id.BtnLoopMode,R.id.BtnFileList,R.id.BtnSoundEffect,R.id.BtnHalfSize};
        ImageButton[] buttonsName= new ImageButton[]{mBtnNext,mBtnPrev,mBtnLoopMode,mBtnFileList,mBtnSoundEffect,mBtnHalfSize};
        View btn = null;
        for (int i = 0; i <imageButtons.length ; i++) {
            buttonsName[i] = (ImageButton) findViewById(imageButtons[i]);
            buttonsName[i].setOnClickListener(new ButtonListener());
        }

    }


    private void Player(int position) {
        intent.putExtra("curposition", position);//把位置传回去，方便再启动时调用
        intent.putExtra("url", musicList.get(position).getUrl());
        intent.putExtra("MSG","0");
        //播放时就改变btn_play_pause图标，下面这个过期了
//        btn_play_pause.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause));
        mChkPlayPause.setBackgroundResource(R.mipmap.pause);
        startService(intent);
        //bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.i("MusicPlayerService","MusicActivity...bindService.......");
    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.BtnNext:
                    showNext();
                    Log.d(TAG, "showNext()方法执行了");
                    break;
                case R.id.BtnPrev:
                    showPrev();
                    Log.d(TAG, "showPrev()方法执行了");
                    break;
                case R.id.BtnLoopMode:
                    showLoopMode();
                    Log.d(TAG, "showLoopMode()方法执行了");
                    break;
                case R.id.BtnFileList:
                    showFileList();
                    Log.d(TAG, "showFileList()方法执行了");
                    break;
                case R.id.BtnSoundEffect:
                    showSoundEffect();
                    Log.d(TAG, "showSoundEffect()方法执行了");
                    break;
                case R.id.BtnHalfSize:
                    showHalfSize();
                    Log.d(TAG, "showHalfSize()方法执行了");
                    break;
            }
        }
    }

    private void showSoundEffect() {

    }

    private void showHalfSize() {

    }

    private void showFileList() {
        Intent intent  =new Intent(this,MusicListActivity.class) ;

        startActivity(intent);



    }

    private void showLoopMode() {

    }

    private void showPrev() {

    }

    // 下一曲
    private void showNext() {
    }
    //

    private static void transparencyStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及其以上
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4及其以上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /*查询媒体库音频*/
    private ArrayList<MusicBean> scanAllAudioFiles(){
        //1.生成动态数组，并转载数据。
        ArrayList<MusicBean>  myList=new ArrayList<MusicBean>();
        /*查询媒体数据库
          参数分别为（路径,要查询的列名,条件语句,条件参数,排序）
          视频：MediaStore.Video.Media.EXTERNAL_CONTENT_URI
          图片;MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        */
        Cursor cursor  = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //遍历媒体数据库
        if(!cursor.moveToNext()){
            while(!cursor.isAfterLast()){
                //歌曲编号
                int  id= cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲標題
                String tilte =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲專輯名
                String album =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                int albumId =cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                if (size >1024*800){//大于800K
                    MusicBean musicMedia = new MusicBean();
                    musicMedia.setId(id);
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setTitle(tilte);
                    musicMedia.setTime(duration);
                    musicMedia.setUrl(url);
                    musicMedia.setAlbum(album);
                    musicMedia.setAlbumId(albumId);
                    myList.add(musicMedia);
                }
                cursor.moveToNext();
            }
        }
        return myList;
    }

}
