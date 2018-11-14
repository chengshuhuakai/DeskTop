package com.example.zxw_soft.desktop;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends FragmentActivity  {

    public String TAG ="SETTING" ;
    private RadioButton mBtnBackLight;
    private RadioButton mBtnFactorySetting;
    private RadioButton mBtnNav;
    private RadioButton mBtnSteeringWheel;
    private RadioButton mBtnMore;
    private RadioButton mBtnGps;
    private RadioButton mBtnTime;
    private RadioButton mBtnSystemSetting;
    private RadioButton mBtnSystemMessage;
    private RadioButton mBtnEffectSetting;
    private RadioButton mBtnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //setFullScreen(this);
        transparencyStatusBar(this);
        setContentView(R.layout.activity_setting);
        initView();
    }

     void initView() {
     /*   mBtnBackLight=(RadioButton)findViewById(R.id.BtnBackLight);
        mBtnFactorySetting=(RadioButton)findViewById(R.id.BtnFactorySetting);
        mBtnNav=(RadioButton)findViewById(R.id.BtnNav);
        mBtnSteeringWheel=(RadioButton)findViewById(R.id.BtnSteeringWheel);
        mBtnMore=(RadioButton)findViewById(R.id.BtnMore);
        mBtnGps=(RadioButton)findViewById(R.id.BtnGPS);
        mBtnTime=(RadioButton)findViewById(R.id.BtnTime);
        mBtnSystemSetting=(RadioButton)findViewById(R.id.BtnSystemSetting);
        mBtnSystemMessage=(RadioButton)findViewById(R.id.BtnSystemMessage);
        mBtnEffectSetting=(RadioButton)findViewById(R.id.BtnEffectSetting);
        mBtnLanguage=(RadioButton)findViewById(R.id.BtnLanguage);*/
        int[] buttons =new int[]{R.id.BtnBackLight,R.id.BtnFactorySetting,R.id.BtnNav,R.id.BtnSteeringWheel,R.id.BtnMore,R.id.BtnGPS,R.id.BtnTime,R.id.BtnSystemSetting,R.id.BtnSystemMessage,R.id.BtnEffectSetting,R.id.BtnLanguage};
        RadioButton[] buttonsName= new RadioButton[]{mBtnBackLight,mBtnFactorySetting,mBtnNav,mBtnSteeringWheel,mBtnMore,mBtnGps,mBtnTime,mBtnSystemSetting,mBtnSystemMessage,mBtnEffectSetting,mBtnLanguage};
        for (int i = 0; i <buttons.length ; i++) {
                buttonsName[i]= (RadioButton) findViewById(buttons[i]);
                buttonsName[i].setOnClickListener(new ButtonListener());
                 //Log.d(TAG, "initView:"+ buttonsName[i]);
        }

       // replaceRightFragment(new BodaFragment());
    }
    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.BtnBackLight:
                    Toast.makeText(getApplicationContext(),"BackLightFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new BodaFragment());
                    break;
                case R.id.BtnFactorySetting:
                    Toast.makeText(getApplicationContext(),"FactorySettingFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new TongHuaJiLuFragment());
                    break;
                case R.id.BtnNav:
                    Toast.makeText(getApplicationContext(),"NavFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new DianHuaBenFragment());
                    break;
                case R.id.BtnSteeringWheel:
                    Toast.makeText(getApplicationContext(),"SteeringWheelFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnMore:
                    Toast.makeText(getApplicationContext(),"MoreFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnGPS:
                    Toast.makeText(getApplicationContext(),"GPSFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnTime:
                    Toast.makeText(getApplicationContext(),"TimeFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnSystemSetting:
                    Toast.makeText(getApplicationContext(),"lanyashebeiFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnSystemMessage:
                    Toast.makeText(getApplicationContext(),"SystemSettingFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnEffectSetting:
                    Toast.makeText(getApplicationContext(),"EffectSettingFragment创建了",Toast.LENGTH_LONG).show();
                    replaceRightFragment(new LanYaSheBeiFragment());
                    break;
                case R.id.BtnLanguage:
                    Toast.makeText(getApplicationContext(),"LanguageFragment创建了",Toast.LENGTH_LONG).show();
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
