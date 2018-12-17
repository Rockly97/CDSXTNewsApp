package com.example.zy_cp.cdsxtnewsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zy_cp.cdsxtnewsapp.po.User;
import com.example.zy_cp.cdsxtnewsapp.service.UserService;
import com.example.zy_cp.cdsxtnewsapp.util.CodeUtils;
import com.example.zy_cp.cdsxtnewsapp.util.Param;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView loginUser;
    private TextView loginPassword;
    private TextView loginCode;
    private InputFilter blankFilter;
    private InputFilter speSymbolFilter;
    private ImageView loginCodeImage;
    private CodeUtils codeUtils;
    private Bitmap bitmap;
    private User user;
    private Intent intent;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化模块
        init();
    }

    private void init() {
        loginUser = findViewById(R.id.login_user);
        loginPassword = findViewById(R.id.login_password);
        loginCode = findViewById(R.id.login_code);
        loginCodeImage = findViewById(R.id.login_code_image);

        //设置空格过滤器
        blankFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (charSequence.equals(" ")){
                    return "";
                }
                return null;
            }
        };
        //设置特殊符号过滤器
        speSymbolFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                String speChar="[`~!#$%^&*()_\\-+=|{}\\[\\]':;',<>/?~！#￥%……&*（）——+|｛｝【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChar);
                Matcher matcher = pattern.matcher(charSequence);
                if (matcher.find()){
                    return "";
                }
                return null;
            }
        };
        //为组件添加过滤器
        loginUser.setFilters(new InputFilter[]{blankFilter,speSymbolFilter});
        loginPassword.setFilters(new InputFilter[]{blankFilter,speSymbolFilter});

        getCodeImage();
    }

    private void getCodeImage(){
        //验证码图片
        codeUtils = CodeUtils.getInstance();
        bitmap = codeUtils.createBitmap();
        loginCodeImage.setImageBitmap(bitmap);
    }

    //图片刷新
    public void refreshImage(View view){
        getCodeImage();
    }

    //注册按钮事件
    public void toRegister(View view){
        //连接页面
        intent = new Intent(LoginActivity.this,RegisterActivity.class);
        //启动
        startActivity(intent);
    }

    //登录按钮事件
    public void loginTo(View view){
        //登录验证
        if (!(loginUser.getText().toString().isEmpty()||loginPassword.getText().toString().isEmpty())){
            if (loginCode.getText().toString().equalsIgnoreCase(codeUtils.getCode())){
                UserService userService = new UserService(LoginActivity.this);
                user = userService.login(loginUser.getText().toString(),loginPassword.getText().toString());
                if (user.getUsername()!=null){
                    Param.setSessionName(user.getUsername());
                    Param.setSessionEmail(user.getEmail());
                    Toast.makeText(LoginActivity.this,"欢迎进入尚学堂新闻APP",Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"登录失败，账号或密码不正确！",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginActivity.this,"验证码不正确！",Toast.LENGTH_SHORT).show();
                loginCode.setText("");
                getCodeImage();
            }
        }else {
            Toast.makeText(LoginActivity.this,"请输入完整信息！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
