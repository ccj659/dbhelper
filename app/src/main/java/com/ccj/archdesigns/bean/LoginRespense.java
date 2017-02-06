package com.ccj.archdesigns.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class LoginRespense
{

    /**
     * code : 400
     * msg : 手机号或密码错误登录失败
     * result : {}
     */

    public String code;
    public String msg;
    public ResultBean result;

    public static LoginRespense objectFromData(String str) {

        return new Gson().fromJson(str, LoginRespense.class);
    }

    public static List<LoginRespense> arrayLoginRespenseFromData(String str) {

        Type listType = new TypeToken<ArrayList<LoginRespense>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class ResultBean {
        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }
    }


    @Override
    public String toString() {
        return "LoginRespense{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}

