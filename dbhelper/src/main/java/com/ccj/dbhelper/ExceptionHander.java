package com.ccj.dbhelper;

/**
 * Created by ccj on 2017/1/10.
 */

public class ExceptionHander extends Exception  implements Thread.UncaughtExceptionHandler{


    public ExceptionHander (String detailMesg){super(detailMesg);};

    public static ExceptionHander throwExceptionHander(String detailMesg){

        return  new ExceptionHander(detailMesg);
    }




    @Override
    public void uncaughtException(Thread t, Throwable e) {
        TLog.exceptionError("This is:" + t.getName() + ",Message:"
                + e.getMessage());
        System.out.println();
        e.printStackTrace();


    }
}
