package com.ccj.nethelper.helper;

import com.ccj.nethelper.helper.interfaces.IHttpService;
/**
 * Created by ccj on 2017/1/18.
 */

public class RequestTask<T> implements Runnable {

    private IHttpService httpService;


    public RequestTask(RequestHolder<T> requestHodler) {
        this.httpService = requestHodler.getHttpService();
        httpService.setUrl(requestHodler.getUrl());
        httpService.setRequestData(requestHodler.getRequestInfo());
        httpService.setHttpListener(requestHodler.getHttpListener());
    }

    @Override
    public void run() {
        httpService.excute();

    }
}
