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

        userDao = BaseDaoFactory.getInstance().getDBDao(User.class);
        cityDao = BaseDaoFactory.getInstance().getDBDao(City.class);
        TLog.error(userDao.hashCode() + "----" + cityDao.hashCode());

    }


    public void saveCity(View v) {
        City city = new City("青岛" + (index++), "0200");
        cityDao.insert(city);


    }


    public void saveUser(View v) {
        index++;
        User user = new User("ccj" + index, "123456");
        TLog.error(user.toString());
        userDao.insert(user);

    }

    public void updateUser(View v) {
        User old = new User();
        old.name = "ccj1";
        User news = new User("ccj1", "123");
        userDao.update(old, news);

    }


    public void getUser(View v) {
        User user = new User();
        List<Object> userList = userDao.query(user, null, null);
        for (Object user1 : userList) {
            TLog.error(user1.toString());
        }
    }


    public void deleteUser(View v) {
        User user = new User();
        userDao.delete(user);

    }

}
