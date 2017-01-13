package com.ccj.archdesigns.bean;


import com.ccj.dbhelper.annotion.DBPrimaryKey;
import com.ccj.dbhelper.annotion.DBTable;

/**
 * Created by ccj on 2017/1/10.
 */
@DBTable("tb_city")
public class City {

    public City( String name, String cityCode) {
        this.name = name;
        this.cityCode = cityCode;
    }

    @DBPrimaryKey
    private Long id;

    public String name;

    public String cityCode;



}
