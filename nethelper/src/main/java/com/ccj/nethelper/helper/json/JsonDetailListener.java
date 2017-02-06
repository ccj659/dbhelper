package com.ccj.nethelper.helper.json;

import android.util.Log;

import com.ccj.nethelper.helper.interfaces.CallBackListener;
import com.ccj.nethelper.helper.interfaces.IHttpListener;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;

/**
 * Created by ccj on 2017/1/18.
 */

public class JsonDetailListener<M> implements IHttpListener {


    private Class<M> responceClzz;
    private CallBackListener<M> dataListener;

    public JsonDetailListener(Class<M> responceClzz, CallBackListener<M> dataListener) {
        this.responceClzz = responceClzz;
        this.dataListener = dataListener;
    }


    @Override
    public void onSuccess(String httpEntity) {
        Log.e("JsonDetailonSuccess","IHttpListener onSuccess in JsonDetailListener");
        Gson gson = new Gson();
        Log.e("JsonDetailonSuccess","httpEntity-->"+httpEntity+"responceClass"+responceClzz.getName());


        dataListener.onSuccess((M) gson.fromJson(httpEntity, responceClzz));
    }

    @Override
    public void onErro() {
        dataListener.onErro();
    }
}
