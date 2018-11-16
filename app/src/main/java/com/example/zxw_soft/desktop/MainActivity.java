package com.example.zxw_soft.desktop;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener {
    private ViewPager mViewPager;

    private RadioButton rb_daohang;
    private RadioButton rb_shouyin;
    private RadioButton rb_yinyue;
    private RadioButton rb_dsp;
    private RadioButton rb_shipin;
    private RadioButton rb_lanya;
    private RadioButton rb_kongtiao;
    private RadioButton rb_yingyong;
    private RadioButton rb_yuanche;
    private RadioButton rb_liulanqi;
    private RadioButton rb_ziyuanguanli;
    private RadioButton rb_shezhi;
    private RadioGroup[] rg_compare_intent;
    private Boolean changeGroup = false;
    private static RadioGroup mRadiogroup1 =null;
    private static RadioGroup mRadiogroup2 =  null;
    private View mView1;
    private View mView2;
    private View[] mMeneItem;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //setFullScreen(this);
        transparencyStatusBar(this);
         setContentView(R.layout.activity_main);
        //setContentView(R.layout.bluetooth_setting);
        //setContentView(R.layout.bluetooth_dial);
        //兩個RadioGroup實現單選
        initView();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewpaper);
        mMeneItem = new View[2];
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());

   /*    mPage0 = (ImageView) findViewById(R.id.page0);
        mPage1 = (ImageView) findViewById(R.id.page1);*/
        LayoutInflater mLi = LayoutInflater.from(this);

        mMeneItem[0] = mLi.inflate(R.layout.page_menu0_1920x1080, null);
        mMeneItem[1] = mLi.inflate(R.layout.page_menu1_1920x1080, null);
        for (int loop = 0; loop < mMeneItem.length; loop++) {
            initViewListener(mMeneItem[loop]);
        }
        mViewPager.setAdapter(new MyAdapter());
        for (int loop = 0; loop < mMeneItem.length; loop++) {
            ViewParent vp = mMeneItem[loop].getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(mMeneItem[loop]);
            }
        }

        mViewPager.setCurrentItem((mMeneItem.length) * 100000);
       // mViewAdapter.notifyDataSetChanged();

/*
        int[] btnList;
        int[] btnList2;
        btnList = new int[]{R.id.bg_daohang, R.id.bg_shouyin, R.id.bg_yinyue, R.id.bg_dsp, R.id.bg_shipin,R.id.bg_lanya};
        btnList2 = new int[]{R.id.bg_kongtiao, R.id.bg_yingyong, R.id.bg_yuanche, R.id.bg_liulanqi, R.id.bg_ziyuanguanli,R.id.bg_shezhi};
        RadioButton  btn = null;
        //initRadioGroup();*/

      /*  final ArrayList<View> views = new ArrayList<View>();
        views.add(mView1);
        views.add(mView2);*/
        //初始化指示器位置
     /*   //1. 初始化適配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        };
*/
    }

    private MyAdapter mViewAdapter = new MyAdapter();
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {

        }
        @Override
        public Object instantiateItem(View container, int position) {
            //Log.i(TAG, "position = " + position);
            try {
                position %= mMeneItem.length;
                if (position < 0) {
                    position = mMeneItem.length + position;
                }
                ViewParent vp = mMeneItem[position].getParent();
                if (vp != null) {
                    ViewGroup parent = (ViewGroup) vp;
                    parent.removeView(mMeneItem[position]);
                }
                ((ViewPager) container).addView(mMeneItem[position], 0);
            } catch (Exception e) {
            }
            return mMeneItem[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
       /*  switch (checkedId){
           case R.id.bg_daohang:
           case R.id.bg_shouyin:
           case R.id.bg_yinyue:
           case R.id.bg_dsp:
           case R.id.bg_shipin:
           case R.id.bg_lanya:
               radiogroup2.clearCheck();
               break;

           case R.id.bg_kongtiao:
           case R.id.bg_yingyong:
           case R.id.bg_yuanche:
           case R.id.bg_liulanqi:
           case R.id.bg_ziyuanguanli:
           case R.id.bg_shezhi:
               radiogroup1.clearCheck();
               break;
       }*/


    private void initViewListener(View view) {
        Log.e(TAG, "-----------------initViewListener-----------------------------------------------" );
        int[] btnResLst = { R.id.btnNav,R.id.btnRadio,R.id.btnMusic,R.id.btnDsp, R.id.btnMovie,R.id.btnBluetooth,R.id.btnCarAir,R.id.btnAppList,R.id.btnCarConsole,R.id.btnExplorer,R.id.btnFileManager,R.id.btnSetting};
        View btn = null;
        for (int loop = 0; loop < btnResLst.length; loop++) {
            btn = view.findViewById(btnResLst[loop]);
            if (btn != null) btn.setOnClickListener(this);
        }

    }

 /*   *//**
     * 设置全屏
     *
     * @param activity
     *//*
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }*/
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNav:
                showDaohang();
                break;
            case R.id.btnSetting:
                showShezhi();
                Log.d(TAG,"showSheZhi()方法执行了");
                break;
            case R.id.btnBluetooth:
                showLanya();
                Log.d(TAG,"showLanYa()方法执行了");
                break;
            case R.id.btnMusic:
                showMusic();
        }

    }

    private void showMusic() {
        Intent  intent  = new Intent(MainActivity.this,MusicActivity.class);
        startActivity(intent);
    }

    private void showLanya() {
        Intent  intent  = new Intent(MainActivity.this,LanYaActivity.class);
        startActivity(intent);
    }

    private void showShezhi() {
        Intent  intent  = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
        //finish();
    }

    private void showDaohang() {
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
