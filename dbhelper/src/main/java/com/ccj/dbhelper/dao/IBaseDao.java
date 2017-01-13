package com.ccj.dbhelper.dao;

import java.util.List;

/**
 * Created by ccj on 2017/1/10.
 */

public interface IBaseDao<T> {

    /**
     * 插入数据
     * @param insert 要插入的bean
     * @return 插入数据的id
     */
    long insert(T insert);

    /**
     * 更新数据
     * @param old 旧的数据bean
     * @param where 新的数据bean
     * @return
     */
    long update(T old, T where);

    /**
     * 普通查询
     * @param where 要检索的bean
     * @return 结果集合
     */
    List<T> query(T where);

    /**
     * 分页查询
     * @param where 要检索的bean
     * @param startIndex 查询起始下标 0开始
     * @param limit 查询个数
     * @return 结果集合
     */
    List<T> query(T where, Integer startIndex, Integer limit);

    /**
     * 删除数据
     * @param insert 删除的条件bean
     * @return 删除的id
     */
    long delete(T insert);


}
