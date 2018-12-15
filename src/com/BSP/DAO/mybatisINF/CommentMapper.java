package com.BSP.DAO.mybatisINF;

import com.BSP.bean.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    Comment selectCommentById(@Param("id") int id);

    void addComment(Comment comment);

    void deleteComment(@Param("id") int id);

    List<Comment> findAllCommentByBookId(@Param("bookId") int bookId);

    List<Comment> selectCommentByUserId(@Param("userId") int userId);

    List<Comment> selectCommentByBookId(@Param("bookId") int bookId);

    List<Comment> pageAllComment(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("bookId") int bookId);
}
