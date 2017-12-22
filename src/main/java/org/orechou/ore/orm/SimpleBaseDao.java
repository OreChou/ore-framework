package org.orechou.ore.orm;

import java.util.Collection;
import java.util.List;

/**
 * ORM提供的基础Dao的实现类
 *
 * @author OreChou
 * @create 2017-12-22 10:55
 */
public class SimpleBaseDao<M, ID> implements BaseDao<M, ID> {
    @Override
    public M add(M entity) {
        return null;
    }

    @Override
    public List<M> add(Collection<M> entities) {
        return null;
    }

    @Override
    public void delete(M entity) {

    }

    @Override
    public void delete(Collection<M> entities) {

    }

    @Override
    public M update(M entity) {
        return null;
    }

    @Override
    public List<M> update(Collection<M> entities) {
        return null;
    }

    @Override
    public M get(ID id) {
        return null;
    }

    @Override
    public List<M> get(Collection<ID> ids) {
        return null;
    }
}
