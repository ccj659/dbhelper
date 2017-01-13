package com.ccj.dbhelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ccj.dbhelper.dao.BaseDao;


/**
 * Created by ccj on 2017/1/10.
 * 生产数据库工厂,在此处要初始化
 */

public class BaseDaoFactory {

    private SQLiteDatabase database;

    private String databasePath;

    //单例工厂

    private  static BaseDaoFactory instance=new BaseDaoFactory();

    private  BaseDaoFactory(){
        databasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/design.db";
        database=SQLiteDatabase.openOrCreateDatabase(databasePath,null);
    }


    public static BaseDaoFactory getInstance(){

        return instance;
    }


    public synchronized <M> BaseDao getDBDao (Class<M> bean){

        BaseDao mBaseDao=null;
        try {
            mBaseDao=  new BaseDao();
            mBaseDao.init(database,bean);
        } catch (ExceptionHander exceptionHander) {
            exceptionHander.printStackTrace();
        }

        return mBaseDao;
    }



}
