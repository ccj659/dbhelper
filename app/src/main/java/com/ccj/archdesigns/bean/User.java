package com.ccj.archdesigns.bean;


import com.ccj.dbhelper.annotion.DBPrimaryKey;
import com.ccj.dbhelper.annotion.DBTable;

/**
 * Created by ccj on 2017/1/10.
 */

/**
 * Bean文件 用注解#{@DBTable},绑定表名,
 * 用#{@DBPrimaryKey},来设置自增长,主外键关系.
 * 其他,用属性名作为表属性
 */
@DBTable("tb_user")
public class User {

    @DBPrimaryKey
    private Long id;

    public String name;

    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
