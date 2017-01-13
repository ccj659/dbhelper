package com.ccj.archdesigns;

import android.app.Application;
import android.os.Environment;

import com.ccj.dbhelper.BaseDaoFactory;
import com.ccj.dbhelper.TLog;

import java.io.File;

/**
 * Created by ccj on 2017/1/13.
 */

public class MyApplication  extends Application{


    private  String databaseDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/database/";
    private  String databaseName = "design.db";

    @Override
    public void onCreate() {
        super.onCreate();

        BaseDaoFactory.init(databaseDir,databaseName);

    }
}
