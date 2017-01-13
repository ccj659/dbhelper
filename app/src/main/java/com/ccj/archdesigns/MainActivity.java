package com.ccj.archdesigns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ccj.archdesigns.bean.City;
import com.ccj.archdesigns.bean.User;
import com.ccj.dbhelper.BaseDaoFactory;
import com.ccj.dbhelper.TLog;
import com.ccj.dbhelper.dao.BaseDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    int index = 0;
    BaseDao cityDao;
    BaseDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //要保证初始化一次,避免重复新建对象
        userDao = BaseDaoFactory.getInstance().getDBDao(User.class);
        cityDao = BaseDaoFactory.getInstance().getDBDao(City.class);

    }

    /**
     * 保存城市
     * @param v
     */
    public void saveCity(View v) {
        City city = new City("青岛" + (index++), "0200");
        cityDao.insert(city);


    }

    /**
     * 保存用户
     * @param v
     */
    public void saveUser(View v) {
        index++;
        User user = new User("ccj" + index, "123456");
        TLog.error(user.toString());
        userDao.insert(user);

    }

    /**
     * 更新用户
     * @param v
     */
    public void updateUser(View v) {
        User old = new User();
        old.name = "ccj1";
        User news = new User("ccj1", "123");
        userDao.update(old, news);

    }

    /**
     * 查询用户
     * @param v
     */
    public void getUser(View v) {
        User user = new User();
        List<Object> userList = userDao.query(user, null, null);
        for (Object user1 : userList) {
            TLog.error(user1.toString());
        }
    }

    /**
     * 删除用户
     * @param v
     */
    public void deleteUser(View v) {
        User user = new User();
        userDao.delete(user);

    }

}
