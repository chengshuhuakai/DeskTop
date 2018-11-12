package com.example.zxw_soft.desktop;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LanYaActivity extends FragmentActivity {
    private List<MenuItem> mMenuItems =new ArrayList<>();
    private ImageButton gps_lanya;
    private ImageButton gengduo_lanya;
    private ImageButton beiguang_lanya;
    private ImageButton fangxiangpan_lanya;
    private ImageButton mBt_boda;
    private ImageButton mBt_tonghuajilu;
    private ImageButton mBt_dianhuaben;
    private ImageButton mBt_lanyashebei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //setFullScreen(this);

        transparencyStatusBar(this);
        setContentView(R.layout.activity_lan_ya);
        //initView();
        //初始化目录数据
       // initMenuItem();
        initView();
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }
    private void initMenuItem() {
        //ArrayList<>
        MenuItem menuItem1 =new MenuItem("背光",R.mipmap.boda_d);
        MenuItem menuItem2 =new MenuItem("方向盘",R.mipmap.boda_d);
        MenuItem menuItem3 =new MenuItem("gps",R.drawable.boda_bt);
        MenuItem menuItem4 =new MenuItem("时间",R.drawable.boda_bt);
        MenuItem menuItem5 =new MenuItem("音效设置",R.drawable.boda_bt);
        MenuItem menuItem6 =new MenuItem("语言",R.drawable.boda_bt);
        MenuItem menuItem7 =new MenuItem("系统信息",R.drawable.boda_bt);
        MenuItem menuItem8 =new MenuItem("系统设置",R.drawable.boda_bt);
        MenuItem menuItem9 =new MenuItem("更多",R.drawable.boda_bt);
        mMenuItems.add(menuItem1);
        mMenuItems.add(menuItem2);
        mMenuItems.add(menuItem3);
        mMenuItems.add(menuItem4);
        mMenuItems.add(menuItem5);
        mMenuItems.add(menuItem6);
        mMenuItems.add(menuItem7);
        mMenuItems.add(menuItem8);
        mMenuItems.add(menuItem9);


    }



    private void initMenuView() {


/*      fangxiangpan_lanya = (ImageButton) findViewById(R.id.fangxiangpan);
        beiguang_lanya = (ImageButton) findViewById(R.id.beiguang);
        gengduo_lanya = (ImageButton) findViewById(R.id.gengduo);
        gps_lanya = (ImageButton) findViewById(R.id.gps);

        fangxiangpan_lanya.setOnClickListener(new LanYaModelOnClickListener());
        beiguang_lanya.setOnClickListener(new LanYaModelOnClickListener());
        gengduo_lanya.setOnClickListener(new LanYaModelOnClickListener());
        gps_lanya.setOnClickListener(new LanYaModelOnClickListener());*/
    }

    private void initView() {
        mBt_boda = (ImageButton) findViewById(R.id.BtnDial);
        mBt_tonghuajilu = (ImageButton) findViewById(R.id.BtnCallLog);
        mBt_dianhuaben = (ImageButton) findViewById(R.id.BtnTelBook);
        mBt_lanyashebei = (ImageButton) findViewById(R.id.BtnBluetoothtools);

        mBt_boda.setOnClickListener(new LanYaActivity.ButtonListener());
        mBt_tonghuajilu.setOnClickListener(new LanYaActivity.ButtonListener());
        mBt_dianhuaben.setOnClickListener(new LanYaActivity.ButtonListener());
        mBt_lanyashebei.setOnClickListener(new LanYaActivity.ButtonListener());
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

    private class LanYaModelOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
         /*   switch (v.getId()){
                case R.id.fangxiangpan:
                    replaceMenuFragment(new FangXiangPanFragment());
                    break;
                case R.id.beiguang:
                    replaceMenuFragment(new BeiJingFragment());
                    break;
                case R.id.gengduo:
                    break;
                case R.id.gps:
                    break;
            }*/
        }
    }


}
