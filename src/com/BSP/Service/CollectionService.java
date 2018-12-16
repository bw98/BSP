package com.BSP.Service;

import com.BSP.DAO.CollectionDAO;
import com.BSP.bean.Collection;

import java.util.List;

public class CollectionService {
    public int addCollection(Collection collection) {
        CollectionDAO collectionDAO = new CollectionDAO();
        int id = collectionDAO.addCollection(collection);
        return id;
    }

    public void deleteCollection(Collection collection) {
        CollectionDAO collectionDAO = new CollectionDAO();
        collectionDAO.deleteCollection(collection);
    }

    public List<Collection> findCollectionByUserId(int userId) {
        CollectionDAO collectionDAO = new CollectionDAO();
        return collectionDAO.findAllCollectionByUserId(userId);
    }
}
