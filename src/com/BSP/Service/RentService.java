package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.RentDAO;
import com.BSP.bean.Rent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

public class RentService {
    public boolean rent(Rent rent){
        BookDAO bookDAO =new BookDAO();
        RentDAO rentDAO=new RentDAO();
        if(bookDAO.findBookByBookId(rent.getBookId())!=null){
            rentDAO.addRent(rent);
            //设置书为"已借出"状态
            bookDAO.updateBookStatus(rent.getBookId(),3);
        }else{
            return false;
        }
        return true;
    }

    public void back(int id){
        RentDAO rentDAO=new RentDAO();
        BookDAO bookDAO=new BookDAO();

        Rent rent=rentDAO.findRentById(id);
        rentDAO.deleteRent(rent.getId());
        bookDAO.updateBookStatus(rent.getBookId(),0);

    }


    public boolean deleteRent(int id){
        RentDAO rentDAO=new RentDAO();
        if(rentDAO.findRentById(id)!=null){
            rentDAO.deleteRent(id);
        }else{
            return false;
        }
        return true;
    }

    public List<Rent> allRent(int userId){
        RentDAO rentDAO=new RentDAO();
        List list=rentDAO.allRent(userId);
        return list;
    }
}
