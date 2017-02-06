package com.ccj.archdesigns;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ccj.archdesigns.bean.User;
import com.ccj.archdesigns.bean.LoginRespense;
import com.ccj.nethelper.helper.NetHelper;
import com.ccj.nethelper.helper.interfaces.CallBackListener;


public class HttpMainActivity extends AppCompatActivity {
    public  static  final String url="http://123.234.82.23/flyapp/Login.ashx/";
    private static final String TAG = "HttpMainActivity";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_http);

    }

    /**
     *  1
     *  2
     * @param view
     */
    public  void login(View view)
    {
        User user=new User();
        user.name="13343491234";
        user.password="123456";
        for (int i=0;i<50;i++)
        {
            NetHelper.postJsonRequest( url,user, LoginRespense.class, new CallBackListener<LoginRespense>() {
                @Override
                public void onSuccess(LoginRespense loginRespense) {
                    Log.i(TAG,loginRespense.toString());
                }

                @Override
                public void onErro() {
                    Log.i(TAG,"获取失败");
                }
            });
        }


    }
}
