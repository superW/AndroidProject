package com.autoai.android.download.db;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.autoai.android.download.constant.InnerConstant;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBOpenHelper";

    public DbOpenHelper(Context context) {
        super(context, InnerConstant.Db.NAME_DB, null, getVersionCode(context));
    }

    private static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
//            LogUtils.e(TAG, "创建数据库失败 ");
        }
        return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String info = "create table if not exists " + InnerConstant.Db.NAME_TABALE
                + "(" +
                InnerConstant.Db.id + " varchar(500)," +
                InnerConstant.Db.downloadUrl + " varchar(100)," +
                InnerConstant.Db.filePath + " varchar(100)," +
                InnerConstant.Db.size + " integer," +
                InnerConstant.Db.downloadLocation + " integer," +
                InnerConstant.Db.downloadStatus + " integer)";

//        LogUtils.i(TAG, "onCreate() -> info=" + info);
        db.execSQL(info);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 对于下载来讲，其实是不存在这种升级数据库的业务的.所以可以直接删除重新建表
        db.execSQL("drop table if exists " + InnerConstant.Db.NAME_TABALE);
        onCreate(db);
    }
}
