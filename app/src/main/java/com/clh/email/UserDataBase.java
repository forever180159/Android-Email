package com.clh.email;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserDataBase extends SQLiteOpenHelper {
    public UserDataBase(@Nullable Context context){
        super(context,"UserData",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        * APP相关设置信息表，包括：
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
