package com.ccj.nethelper.helper;

import com.ccj.nethelper.helper.interfaces.CallBackListener;
import com.ccj.nethelper.helper.interfaces.IHttpListener;
import com.ccj.nethelper.helper.interfaces.IHttpService;
import com.ccj.nethelper.helper.json.JsonDetailListener;
import com.ccj.nethelper.helper.json.JsonRequestService;

import java.util.concurrent.FutureTask;

/**
 * Created by ccj on 2017/1/18.
 */

public class NetHelper  {


    public static <T,M> void postJsonRequest( String URL
            , T jsonBean, Class<M> responseBeanClzz
            ,CallBackListener callBackListener){

        RequestHolder<T> tRequestHolder =new RequestHolder<>();


        tRequestHolder.setUrl(URL);
        tRequestHolder.setRequestInfo(jsonBean);
        IHttpService httpService=new JsonRequestService();
        tRequestHolder.setHttpService(httpService);

        IHttpListener iHttpListener=new JsonDetailListener<>(responseBeanClzz,callBackListener);
        tRequestHolder.setHttpListener(iHttpListener);

        RequestTask<T> httpTask=new RequestTask<>(tRequestHolder);

        try {
            ThreadPoolManager.getInstance().execte(new FutureTask<Object>(httpTask,null));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
