package com.example.zy_cp.cdsxtnewsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zy_cp.cdsxtnewsapp.service.UserService;
import com.example.zy_cp.cdsxtnewsapp.util.CodeUtils;
import com.example.zy_cp.cdsxtnewsapp.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity{

    private CodeUtils codeUtils;
    private Bitmap bitmap;
    private EditText registerUsername;
    private EditText registerPassword;
    private EditText registerRepetitionPassword;
    private EditText registerEmail;
    private EditText registerCode;
    private ImageView registerCodeImage;
    private InputFilter speSymbolFilter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化组件
        init();
    }

    private void init() {
        //获取组件
        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);
        registerRepetitionPassword = findViewById(R.id.register_repetition_password);
        registerEmail = findViewById(R.id.register_email);
        registerCode = findViewById(R.id.register_code);
        registerCodeImage = findViewById(R.id.register_code_image);

        //设置空格过滤器
        InputFilter blankFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (charSequence.equals(" ")){
                    return "";
                }
                return null;
            }
        };

        //设置特殊字符过滤器
        speSymbolFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                String speChar = "[`~!#$%^&*()_\\-+=|{}\\[\\]':;',<>/?~！#￥%……&*（）——+|｛｝【】‘；：”“’。，、？ ]";
                Pattern pattern = Pattern.compile(speChar);
                Matcher matcher = pattern.matcher(charSequence);
                if (matcher.find()){
                    return "";
                }
                return null;
            }
        };

        //设置过滤器
        registerUsername.setFilters(new InputFilter[]{speSymbolFilter});
        registerPassword.setFilters(new InputFilter[]{speSymbolFilter});
        registerEmail.setFilters(new InputFilter[]{speSymbolFilter});

        //设置图片
        getCodeImage();
        
    }
    private void getCodeImage(){
        //验证码图片
        codeUtils = CodeUtils.getInstance();
        bitmap = codeUtils.createBitmap();
        registerCodeImage.setImageBitmap(bitmap);
    }

    //设置图片点击事件
    public void refreshRegImage(View view){
        getCodeImage();
    }

    //设置跳转按钮事件
    public void toLogin(View view){
        //连接页面
        intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    //设置注册按钮事件
    public void registerTo(View view){
        //是否为空
        if (registerUsername.getText().toString().isEmpty()||
                registerPassword.getText().toString().isEmpty()||
                registerEmail.getText().toString().isEmpty()){
            Toast.makeText(RegisterActivity.this,"请检查注册信息是否填写完整！",Toast.LENGTH_SHORT).show();
        }else {
            //验证码判断
            if (registerCode.getText().toString().equalsIgnoreCase(codeUtils.getCode())){
                //用户名规则
                if (Validator.isUsername(registerUsername.getText().toString())&&(registerUsername.getText().toString().indexOf("@")==-1)){
                    //密码规则
                    if (Validator.isPassword(registerPassword.getText().toString())){
                        //邮箱规则
                        if (Validator.isEmail(registerEmail.getText().toString())){
                            //两次密码判断
                            if (registerPassword.getText().toString().equals(registerRepetitionPassword.getText().toString())){
                                //注册功能
                                UserService userService = new UserService(RegisterActivity.this);
                                //结果
                                boolean result = userService.register(registerUsername.getText().toString(),registerPassword.getText().toString(),registerEmail.getText().toString());
                                //判断
                                if (result){
                                    Toast.makeText(RegisterActivity.this,"注册成功，返回登录界面！",Toast.LENGTH_SHORT).show();
                                    //跳转页面
                                    intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"注册失败，该用户名或邮箱已存在！",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(RegisterActivity.this,"两次密码输入不正确！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this,"请填写正确的邮箱！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"请填写6-16位的密码！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"请填写字母开头6-16位的用户名！",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this,"验证码不正确！",Toast.LENGTH_SHORT).show();
                registerCode.setText("");
                getCodeImage();
            }
        }
    }

}
