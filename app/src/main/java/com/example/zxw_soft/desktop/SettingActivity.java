package com.example.zxw_soft.desktop;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends FragmentActivity  {

    public ImageButton mBt_boda;
    public ImageButton mBt_tonghuajilu;
    public ImageButton mBt_dianhuaben;
    public ImageButton mBt_lanyashebei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //setFullScreen(this);
        transparencyStatusBar(this);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mBt_boda = (ImageButton) findViewById(R.id.BtnDial);
        mBt_tonghuajilu = (ImageButton) findViewById(R.id.BtnCallLog);
        mBt_dianhuaben = (ImageButton) findViewById(R.id.BtnTelBook);
        mBt_lanyashebei = (ImageButton) findViewById(R.id.BtnBluetoothtools);

        mBt_boda.setOnClickListener(new ButtonListener());
        mBt_tonghuajilu.setOnClickListener(new ButtonListener());
        mBt_dianhuaben.setOnClickListener(new ButtonListener());
        mBt_lanyashebei.setOnClickListener(new ButtonListener());
       // replaceRightFragment(new BodaFragment());
    }
    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.BtnDial:
                    Toast.makeText(getApplicationContext(),"bodaFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new BodaFragment());
                    break;
                case R.id.BtnCallLog:
                    Toast.makeText(getApplicationContext(),"TonghuajiluFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new TongHuaJiLuFragment());
                    break;
                case R.id.BtnTelBook:
                    Toast.makeText(getApplicationContext(),"dianhuabenFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new DianHuaBenFragment());
                    break;
                case R.id.BtnBluetoothtools:
                    Toast.makeText(getApplicationContext(),"lanyashebeiFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;

            }
        }
    }

    private void replaceRightFragment(Fragment displayFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_right,displayFragment);
        transaction.commit();
    }


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



}
