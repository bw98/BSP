package com.BSP.Service;

import com.BSP.DAO.CommentDAO;
import com.BSP.DAO.UserDAO;
import com.BSP.bean.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ArrayList<Map> findCommentByBookId(Comment comment) {
        CommentDAO commentDAO = new CommentDAO();
        UserDAO userDAO = new UserDAO();
        List<Comment> comments = commentDAO.findCommentByBookId(comment);
        ArrayList<Map> list = new ArrayList<Map>();
        for (Comment c : comments) {
            Map<String, String> map = new HashMap<>();
            map.put("createTime", c.getCreateTime().toString());
            map.put("content", c.getContent());
            map.put("name", userDAO.findUserById(c.getUserId()).getUserName());
            list.add(map);
        }
        return list;
    }
}
