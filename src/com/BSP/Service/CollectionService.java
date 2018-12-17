package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.CollectionDAO;
import com.BSP.bean.Book;
import com.BSP.bean.Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionService {
    public int addCollection(Collection collection) {
        CollectionDAO collectionDAO = new CollectionDAO();
        //查看该用户是否收藏过这本书, false 是没有收藏过
        if (collectionDAO.findCollectionByBookIdAndUserId(collection) == false) {
            int id = collectionDAO.addCollection(collection);
            return id;
        } else {
            return -1;
        }
    }

    public void deleteCollection(Collection collection) {
        CollectionDAO collectionDAO = new CollectionDAO();
        collectionDAO.deleteCollection(collection);
    }

    public ArrayList<Map> findCollectionByUserId(int userId) {
        BookDAO bookDAO = new BookDAO();
        CollectionDAO collectionDAO = new CollectionDAO();
        List<Collection> collections = collectionDAO.findAllCollectionByUserId(userId);

        ArrayList<Map> list = new ArrayList<Map>();
        for (Collection c : collections) {
            Map<String, String> map = new HashMap<>();
            int bookId = c.getBookId();
            Book book = bookDAO.findBookByBookId(bookId);
            map.put("bookId", Integer.toString(bookId));
            map.put("bookName", book.getName());
            map.put("author", book.getAuthor());
            map.put("status", Integer.toString(book.getStatus()));
            list.add(map);
        }
        return list;
    }
}
