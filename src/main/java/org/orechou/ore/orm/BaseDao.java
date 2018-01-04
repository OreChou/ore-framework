package org.orechou.ore.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * ORM提供的基础Dao
 *
 * @author OreChou
 * @create 2017-12-22 10:49
 */
public interface BaseDao<M, ID extends Serializable> {

    M add(M entity);

    List<M> add(Collection<M> entities);

    void delete(M entity);

    void delete(Collection<M> entities);

    void deleteById(ID id);

    void deleteByIds(Collection<ID> ids);

    M update(M entity);

    List<M> update(Collection<M> entities);

    M get(ID id);

    List<M> list(Collection<ID> ids);

    M getByProperty(M entity);

    List<M> listByProperty(M entity);
}
