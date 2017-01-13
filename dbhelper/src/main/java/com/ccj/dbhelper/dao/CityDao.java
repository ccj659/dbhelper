package com.ccj.dbhelper.dao;

/**
 * Created by ccj on 2017/1/10.
 */
@Deprecated
public class CityDao extends BaseDao{


/*    @Override
    protected String createTable(String tableName,Class bean) {


        Field[]  fields= bean.getDeclaredFields();
        StringBuilder stringBuilder =new StringBuilder();
        stringBuilder.append("create table if not exists "+tableName+" ( ");
        for (int i = 0; i <fields.length ; i++) {
            TLog.error(fields[i].getName());
            if (i==fields.length-1){
                stringBuilder.append(" "+fields[i].getName()+" varchar(40)");
            }else {
                stringBuilder.append(" "+fields[i].getName()+" varchar(40),");

            }
        }

        stringBuilder.append(" );");
        return stringBuilder.toString();
    }*/
}
