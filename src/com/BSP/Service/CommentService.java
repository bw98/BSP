package com.BSP.Service;

import com.BSP.DAO.CommentDAO;
import com.BSP.bean.Comment;

public class CommentService {
    public int addComment(Comment comment) {
        CommentDAO commentDAO = new CommentDAO();
        int id = commentDAO.addComment(comment);
        return id;
    }

    public boolean deleteComment(Comment comment, int userId) {
        CommentDAO commentDAO = new CommentDAO();
        Comment com = commentDAO.findCommentById(comment);
        //对比token的uerId和该评论的userId，判断是否为当前用户的评论
        if (com.getUserId() == userId) {
            commentDAO.deleteComment(comment);
            return true;
        }
        return false;
    }
}
