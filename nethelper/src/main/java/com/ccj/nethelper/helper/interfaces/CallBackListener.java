package com.ccj.nethelper.helper.interfaces;

/**
 * Created by ccj on 2017/1/18.
 */

public interface CallBackListener<M> {


    /**
     * 回调结果给调用层
     * @param m
     */
    void onSuccess(M m);


    void onErro();

}
