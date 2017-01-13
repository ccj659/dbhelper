package com.ccj.dbhelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ccj.dbhelper.dao.BaseDao;

import java.io.File;
import java.io.IOException;


/**
 * Created by ccj on 2017/1/10.
 * 生产数据库工厂,在此处要初始化
 */

public class BaseDaoFactory {

    private SQLiteDatabase database;

    private static String mdatabaseDir = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ "database"+File.separator;
    private static String mdatabaseName ="app.db";
    private static String databasePath =mdatabaseDir+mdatabaseName;


    //单例工厂

    private static BaseDaoFactory instance;

    private BaseDaoFactory() {

        database = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
    }

    /**
     * 初始化SQLite位置,默认位置是sd卡的/database/design.db
     * @param databaseDir
     * @param dbName
     */
    public static void init(String databaseDir,String dbName) {
        TLog.error(" init");
        //databasePath = path;

        databasePath=databaseDir+dbName;

        File file =new File(databaseDir);

        if(!file.exists()){
            file.mkdirs();
        }

        File dbFile =new File(databaseDir,dbName);

        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TLog.error(dbFile+" file.createNewFile();");
        }else {
            TLog.error(dbFile+" file.exists()");
        }
        getInstance();

    }



    public synchronized static BaseDaoFactory getInstance() {
        if (instance == null) {
            instance = new BaseDaoFactory();
        }
        return instance;
    }


    public synchronized <M> BaseDao getDBDao(Class<M> bean) {

        BaseDao mBaseDao = null;
        try {
            mBaseDao = new BaseDao();
            mBaseDao.init(database, bean);
        } catch (ExceptionHander exceptionHander) {
            exceptionHander.printStackTrace();
        }

        return mBaseDao;
    }


}
