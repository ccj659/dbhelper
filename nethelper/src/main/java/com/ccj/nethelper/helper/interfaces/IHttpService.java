package com.ccj.nethelper.helper.interfaces;

/**
 * Created by ccj on 2017/1/18.
 */

public interface IHttpService {

    /**
     * 设置url
     * @param url
     */
    void setUrl(String url);

    /**
     * 执行获取网络
     */
    void excute();

    /**
     * 设置处理接口
     * @param httpListener
     */
    void setHttpListener(IHttpListener httpListener);

    /**
     * 设置请求参数
     *
     */
    <T> void setRequestData(T data );


}
