package com.clh.email;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        //沉浸式状态栏和导航栏
        ImmersionBar
                .with(this)  //绑定上下文环境
                .transparentStatusBar()  //设置状态栏为透明
                .statusBarDarkFont(true)  //设置状态栏字体为黑色
                .navigationBarColor(R.color.transparent)  //设置导航栏为透明
                .navigationBarDarkIcon(true)  //设置导航栏字体为黑色
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)  //隐藏导航栏
                .fitsSystemWindows(true)  //不被控件覆盖状态栏
                .init();  //初始化和应用


    }
}