package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.ReserveDAO;
import com.BSP.bean.Book;
import com.BSP.bean.Reserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveService {
    public boolean reserve(Reserve reserve){
        BookDAO bookDAO =new BookDAO();
        ReserveDAO reserveDAO=new ReserveDAO();
        Book book = bookDAO.findBookByBookId(reserve.getBookId());
        //被借阅时才能预定
        if(book.getStatus()==3){
            reserveDAO.addReserve(reserve);
            //设置书为"已预约"状态
            bookDAO.updateBookStatus(reserve.getBookId(),2);
        }else{
            return false;
        }
        return true;
    }

    public boolean deleteReserve(int id){
        ReserveDAO reserveDAO=new ReserveDAO();
        if(reserveDAO.findReserveById(id)!=null){
            reserveDAO.deleteReserve(id);
        }else{
            return false;
        }
        return true;
    }

    public List<Map> allReserve(int userId){
        ReserveDAO reserveDAO=new ReserveDAO();
        BookDAO bookDAO=new BookDAO();
        List list=reserveDAO.allReserve(userId);
        List list1=new ArrayList();

        for(int i=0;i<list.size();i++){
            Map map=new HashMap();
            Reserve reserve=(Reserve) list.get(i);
            Book book=bookDAO.findBookByBookId(reserve.getBookId());
            map.put("bookId",book.getId());
            map.put("bookName",book.getName());
            map.put("status",book.getStatus());
            list1.add(map);
        }
        return list1;
    }
}
