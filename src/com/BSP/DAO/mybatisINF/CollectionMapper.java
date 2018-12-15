package com.BSP.DAO.mybatisINF;

import com.BSP.bean.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {

    void addCollection(Collection collection);

    void deleteCollection(@Param("id") int id);

    List<Collection> findAllCollectionByUserId(@Param("userId") int userId);

    List<Collection> pageAllCollection(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("userId") int userId);

}
