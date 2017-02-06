package com.ccj.nethelper.helper.interfaces;

import org.apache.http.HttpEntity;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public interface IHttpListener {
    /**
     * 网络访问
     * 处理结果  回调
     * @param httpEntity
     */
    void onSuccess(String httpEntity);

    void onErro();
}
