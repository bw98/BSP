package com.BSP.DAO;

import com.BSP.DAO.mybatisINF.CollectionMapper;
import com.BSP.bean.Collection;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CollectionDAO {
    public int addCollection(Collection collection) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CollectionMapper mapper = sqlSession.getMapper(CollectionMapper.class);
            mapper.addCollection(collection);
            sqlSession.commit();
            return collection.getId();

        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return -1;
    }

    public void deleteCollection(Collection collection) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CollectionMapper mapper = sqlSession.getMapper(CollectionMapper.class);
            mapper.deleteCollection(collection.getId());
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public List<Collection> findAllCollectionByUserId(int userId) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CollectionMapper mapper = sqlSession.getMapper(CollectionMapper.class);
            return mapper.findAllCollectionByUserId(userId);
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

        return null;
    }

    public List<Collection> pageAllCollection(int startIndex, int pageSize, int userId) {
        //需要注意mysql limit中start是从0开始的
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CollectionMapper mapper = sqlSession.getMapper(CollectionMapper.class);
            return mapper.pageAllCollection(startIndex, pageSize, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return null;
    }
}
