package com.BSP.DAO;

import com.BSP.DAO.mybatisINF.CommentMapper;
import com.BSP.bean.Comment;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CommentDAO {

    public Comment findCommentById(Comment comment) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            return mapper.selectCommentById(comment.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return null;
    }

    public int addComment(Comment comment) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            mapper.addComment(comment);
            sqlSession.commit();
            return comment.getId();

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

    public void deleteComment(Comment comment) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            mapper.deleteComment(comment.getId());
            sqlSession.commit();

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
    }

    public List<Comment> findAllComment() {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            return mapper.findAllComment();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return null;
    }

    public List<Comment> findCommentByUserId(Comment comment) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            return mapper.selectCommentByUserId(comment.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return null;
    }

    public List<Comment> findCommentByBookId(Comment comment) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            return mapper.selectCommentByBookId(comment.getBookId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return null;
    }

    public List<Comment> pageAllComment(int startIndex, int pageSize) {
        //需要注意mysql limit中start是从0开始的
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
            return mapper.pageAllComment(startIndex, pageSize);
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
