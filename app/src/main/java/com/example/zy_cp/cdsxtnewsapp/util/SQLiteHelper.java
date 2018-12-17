package com.example.zy_cp.cdsxtnewsapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{

    /**
     *
     * @param context   数据库上下文
     * @param name  数据库名称
     * @param factory   数据库游标
     * @param version   数据库版本
     */
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL("create table user (_id integer primary key autoincrement , username text not null unique,password text not null,email text not null unique,pic blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据库
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("create table user (_id integer primary key autoincrement , username text not null unique,password text not null,email text not null unique,pic blob)");
    }
}
