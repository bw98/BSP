package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.RentDAO;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Book;
import com.BSP.bean.Rent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentService {
    public boolean rent(Rent rent){
        BookDAO bookDAO =new BookDAO();
        RentDAO rentDAO=new RentDAO();
        Book book=bookDAO.findBookByBookId(rent.getBookId());
        if(book.getStatus()==0){
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

    public List<Rent> overdue(int userId) throws ParseException {
        RentDAO rentDAO=new RentDAO();
        List<Rent> list=rentDAO.allRent(userId);
        List<Rent> overlist=new ArrayList<Rent>();

        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);

        for(int i=0;i<list.size();i++){
            Rent rent=list.get(i);
            Date date=rent.getBeginData();

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();

            c.setTime(date);
            c.add(Calendar.DATE,30);
            Date deadline=c.getTime();//到期时间
            Calendar c1 = Calendar.getInstance();
            Date nowdate = c1.getTime();//当前时间

            if(deadline.compareTo(nowdate)==-1){
                overlist.add(rent);
            }
        }
        return overlist;
    }

    public List<Rent> allOverdue(){
        RentDAO rentDAO=new RentDAO();
        List<Rent> list=rentDAO.allOverRent();
        List<Rent> alloverlist=new ArrayList<Rent>();

        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);

        for(int i=0;i<list.size();i++){
            Rent rent=list.get(i);
            Date date=rent.getBeginData();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE,30);
            Date deadline=c.getTime();//到期时间
            Calendar c1 = Calendar.getInstance();
            Date nowdate = c1.getTime();//当前时间
            if(deadline.compareTo(nowdate)==-1){
                alloverlist.add(rent);
            }
        }
        return alloverlist;
    }
}
