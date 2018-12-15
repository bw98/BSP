package com.BSP.Service;

import com.BSP.DAO.CollectionDAO;
import com.BSP.bean.Collection;

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

}
