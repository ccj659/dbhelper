package com.ccj.archdesigns.bean;


import com.ccj.dbhelper.annotion.DBPrimaryKey;
import com.ccj.dbhelper.annotion.DBTable;

/**
 * Created by ccj on 2017/1/10.
 */

@DBTable("tb_user")
public class User {


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public User() {
    }

    @DBPrimaryKey
    private Long id;

    public String name;

    public String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
