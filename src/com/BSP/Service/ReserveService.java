package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.ReserveDAO;
import com.BSP.bean.Reserve;
import org.apache.coyote.http11.filters.VoidInputFilter;

import java.util.List;

public class ReserveService {
    public boolean reserve(Reserve reserve){
        BookDAO bookDAO =new BookDAO();
        ReserveDAO reserveDAO=new ReserveDAO();
        if(bookDAO.findBookByBookId(reserve.getBookId())!=null){
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

    public List<Reserve> allReserve(int userId){
        ReserveDAO reserveDAO=new ReserveDAO();
        List list=reserveDAO.allReserve(userId);
        return list;
    }
}
