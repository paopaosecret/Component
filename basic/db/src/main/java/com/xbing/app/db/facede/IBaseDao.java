package com.xbing.app.db.facede;

import java.util.List;

/**
 * Created by zhaobing04 on 2018/5/19.
 */

public interface IBaseDao<T> {

    /**
     * 增加
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 删除
     * @param entity
     * @return
     */
    Long delete(T entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    boolean Update(T entity);

    /**
     * 查询
     * @param entity
     * @return
     */
    List<T> query(T entity);

    /**
     * 查询
     * @param where
     * @param oerder
     * @param startIndex
     * @param endIndex
     * @return
     */
    List<T> query(T where, String oerder, Integer startIndex, Integer endIndex);

}
