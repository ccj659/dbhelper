package com.ccj.nethelper.helper.json;

import com.ccj.nethelper.helper.interfaces.CallBackListener;
import com.ccj.nethelper.helper.interfaces.IHttpListener;
import com.ccj.nethelper.helper.interfaces.IHttpService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ccj on 2017/1/18.
 */

public class JsonRequestService implements IHttpService {

    private String url = null;
    private IHttpListener httpListener;

    private String  jsonRequest;


    @Override
    public void setUrl(String url) {

        this.url = url;

    }

    @Override
    public void excute() {
        //创建连接
        HttpURLConnection connection = null;
        PrintWriter out=null;
        BufferedReader reader=null;
        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            //POST请求
             out =new PrintWriter(connection.getOutputStream());
            out.write(jsonRequest);
            out.flush();
            out.close();

            //读取响应
             reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            System.out.println(httpListener);
            httpListener.onSuccess(sb.toString());
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            httpListener.onErro();
        }finally {
            connection.disconnect();
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
       this. httpListener=httpListener;
    }

    @Override
    public <T>  void setRequestData(T requestData) {
        Gson gson=new Gson();
        jsonRequest=gson.toJson(requestData);
    }


}
