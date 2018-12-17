package com.example.zy_cp.cdsxtnewsapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zy_cp.cdsxtnewsapp.po.User;
import com.example.zy_cp.cdsxtnewsapp.util.SQLiteHelper;

public class UserService {

    private SQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private String sql;
    private User user;

    public UserService(Context context) {
        dbHelper = new SQLiteHelper(context,"user",null,1);
    }

    //注册
    public boolean register(String username,String password,String email){
        database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        values.put("email",email);
        long result = database.insert("user",null,values);
        values.clear();
        if (result!=-1){
            return true;
        }
        return false;
    }

    //登录
    public User login(String str, String password){
        if (str.indexOf("@")==-1){
            sql = "select * from user where username=? and password=? ;";
        }else {
            sql = "select * from user where email=? and password=? ;";
        }
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,new String[]{str,password});
        user = new User();
        while (cursor.moveToNext()){
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }
        cursor.close();
        return user;
    }
}
