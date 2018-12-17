package com.example.zy_cp.cdsxtnewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //获取组件
        ImageView imageView = findViewById(R.id.loding);
        //创建动画实例，设置动画格式
        AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
        //设定时间
        animation.setDuration(3000);
        //组件动画关联
        imageView.setAnimation(animation);
        //设置监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //启动执行
                Toast.makeText(LoadingActivity.this,"尚学堂新闻APP欢迎你",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //结束执行
                //启动Intent机制，连接页面
                Intent intent = new Intent(LoadingActivity.this,LoginActivity.class);
                //启动
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //重复执行
            }
        });

    }
}
