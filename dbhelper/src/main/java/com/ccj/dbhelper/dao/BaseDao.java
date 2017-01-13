package com.ccj.dbhelper.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ccj.dbhelper.ExceptionHander;
import com.ccj.dbhelper.TLog;
import com.ccj.dbhelper.annotion.DBPrimaryKey;
import com.ccj.dbhelper.annotion.DBTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ccj on 2017/1/10.
 * 对象的属性 就是表的属性名
 */

public  class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase database;//持有数据库操作类的引用

    private boolean isInit = false;//保证实例化一次

    private Class<T> bean;//操作的bean对象
    
    private  String tableName = null;//操作的表名

    private ArrayList<Field> fieldArray; //存储 bean对象的成员变量的集合

    private String primaryKey;//主键在bean中属性的名称


    public synchronized boolean init(SQLiteDatabase database, Class<T> bean) throws ExceptionHander {
        if (!isInit) {
            this.database = database;
            this.bean = bean;

            if (bean.getAnnotation(DBTable.class) == null) {
                throw new ExceptionHander("请在bean的类名上加上DBTable.class类型的注解");
            }

            if (!database.isOpen()) {
                throw new ExceptionHander("请先打开数据库");
            }

            tableName = bean.getAnnotation(DBTable.class).value();

            initCache();

            String sqlCreate = createTable(tableName, bean);
            TLog.error(sqlCreate);

            if (sqlCreate == null || sqlCreate.isEmpty()) {
                throw new ExceptionHander("请检查建表语句createTable");
            }

            database.execSQL(sqlCreate);

            isInit = true;
        }


        return true;
    }

    protected void initCache() {
        fieldArray = new ArrayList<>();

        Field[] fields = bean.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            if ("$change".equals(fields[i].getName())
                    || "serialVersionUID".equals(fields[i].getName())) {//这是什么鬼...不知道,应该是install run的差分编译原因
                continue;
            }

            DBPrimaryKey ss = fields[i].getAnnotation(DBPrimaryKey.class);

            if (ss != null) {
                primaryKey = fields[i].getName();
            }

            fieldArray.add(fields[i]);
        }
    }

    /**
     * 创建表或者更新表, 并且将bean 的属性保存在@see "fieldArray";
     * primary key autoincrement
     *
     * @param tableName
     * @param bean
     * @return
     */
    protected String createTable(String tableName, Class<T> bean) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists " + tableName + " ( ");
        for (int i = 0; i < fieldArray.size(); i++) {
            Field field = fieldArray.get(i);
          /*  if ("$change".equals(field.getName())
                    || "serialVersionUID".equals(field.getName())) {//这是什么鬼...不知道,应该是install run的差分编译原因
                continue;
            }
*/

            if (i == fieldArray.size() - 1) {
                if (primaryKey != null && field.getName().equals(primaryKey)) {

                    stringBuilder.append(" " + field.getName() + " integer PRIMARY KEY autoincrement");
                } else {
                    stringBuilder.append(" " + field.getName() + " text");
                }
            } else {
                if (primaryKey != null && field.getName().equals(primaryKey)) {

                    stringBuilder.append(" " + field.getName() + " integer PRIMARY KEY autoincrement,");
                } else {
                    stringBuilder.append(" " + field.getName() + " text,");
                }
            }
        }
        stringBuilder.append(" );");
        return stringBuilder.toString();

    }

    ;


    @Override
    public long insert(T insert) {
        ContentValues vulues = getContentValues(insert);
        return database.insert(tableName, null, vulues);
    }


    private ContentValues getContentValues(T insert) {
        ContentValues vulues = new ContentValues();

        for (int i = 0; i < fieldArray.size(); i++) {
            fieldArray.get(i).setAccessible(true);
            Object cacheValue = null;

            try {
                cacheValue = fieldArray.get(i).get(insert);
                if (cacheValue != null) {
                    vulues.put(fieldArray.get(i).getName().toLowerCase(), (String) cacheValue);

                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        TLog.error(vulues.toString());
        return vulues;
    }

    @Override
    public long update(T where, T news) {

        Condition condition = new Condition(where);

        ContentValues contentValues = getContentValues(news);

        return database.update(tableName, contentValues, condition.getWhereClause(), condition.getWhereArgs());
        // return database.update(tableName, getContentValues(news), primaryKey + " =" + id, null);
    }

    //chenchangjun@flyedt.com
    @Override
    public List<T> query(T where, Integer startIndex, Integer limit) {


        //database.rawQuery("SELECT * FROM " + tableName + " order by " + orderBy + "limit 5 offset 3  ", null);
        String limitString = null;
        limitString = getLimitString(startIndex, limit, limitString);

        Condition condition = new Condition(where);
        TLog.error(condition.toString());
        Cursor cursor = database.query(tableName, null, condition.getWhereClause()
                , condition.getWhereArgs(), null, null, null, limitString);

        List<T> result = getResult(cursor, where);
        cursor.close();

        return result;
    }

    private List<T> getResult(Cursor cursor, T where) {
        ArrayList<T> list = new ArrayList();
        while (cursor.moveToNext()) {

            T item = null;
            try {
                item = (T) where.getClass().newInstance();
                getItemByField(cursor, item);
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private void getItemByField(Cursor cursor, Object item) throws IllegalAccessException {
        int colmunIndex;
        for (int i = 0; i < fieldArray.size(); i++) {
            Field field = fieldArray.get(i);

            colmunIndex = cursor.getColumnIndex(field.getName().toLowerCase());
            Class type = field.getType();

            if (colmunIndex != -1) {
                if (type == String.class) {
                    //反射方式赋值
                    field.set(item, cursor.getString(colmunIndex));
                } else if (type == Double.class) {
                    field.set(item, cursor.getDouble(colmunIndex));
                } else if (type == Integer.class) {
                    field.set(item, cursor.getInt(colmunIndex));
                } else if (type == Long.class) {
                    field.set(item, cursor.getLong(colmunIndex));
                } else if (type == byte[].class) {
                    field.set(item, cursor.getBlob(colmunIndex));
                    /*
                    不支持的类型
                     */
                } else {
                    continue;
                }
            }

        }
    }

    private String getLimitString(Integer startIndex, Integer limit, String limitString) {
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }
        return limitString;
    }


    @Override
    public List<T> query(T where) {

        return query(where, null, null);
    }

    @Override
    public long delete(T where) {
        Condition condition = new Condition(where);
        TLog.error(condition.toString());
        return database.delete(tableName, condition.getWhereClause(), condition.getWhereArgs());
    }


    class Condition {

        private String whereClause;


        private String[] whereArgs;

        private T where;


        public Condition(T where) {
            this.where = where;
            getWhere(where);
        }

        public void getWhere(T where) {

            ArrayList list = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" 1=1 ");


            for (int i = 0; i < fieldArray.size(); i++) {
                fieldArray.get(i).setAccessible(true);
                Object value = null;

                try {
                    value = fieldArray.get(i).get(where);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (value != null) {
                    stringBuilder.append(" and " + fieldArray.get(i).getName().toLowerCase() + " =?");
                    list.add(value);
                }


            }

            this.whereClause = stringBuilder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);

        }

        public String[] getWhereArgs() {
            return whereArgs;
        }

        public void setWhereArgs(String[] whereArgs) {
            this.whereArgs = whereArgs;
        }

        public String getWhereClause() {
            return whereClause;
        }

        public void setWhereClause(String whereClause) {
            this.whereClause = whereClause;
        }

        @Override
        public String toString() {
            return "Condition{" +
                    "whereClause='" + whereClause + '\'' +
                    ", whereArgs=" + Arrays.toString(whereArgs) +
                    ", where=" + where +
                    '}';
        }
    }


}
