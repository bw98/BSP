package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.RentDAO;
import com.BSP.DAO.UserDAO;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Book;
import com.BSP.bean.Rent;
import com.BSP.bean.User;
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

    public void back(Rent rent){
        RentDAO rentDAO=new RentDAO();
        BookDAO bookDAO=new BookDAO();

        Rent rent1=rentDAO.findRentByUseridBookid(rent);
        rentDAO.deleteRent(rent1.getId());
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

    public List<Map> allRent(int userId){
        RentDAO rentDAO=new RentDAO();
        List list=rentDAO.allRent(userId);
        BookDAO bookDAO=new BookDAO();
        List list1=new ArrayList();


        for(int i=0;i<list.size();i++){
            Map map=new HashMap();
            Rent rent=(Rent) list.get(i);
            Book book=bookDAO.findBookByBookId(rent.getBookId());
            map.put("bookId",book.getId());
            map.put("bookName",book.getName());

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();

            Date date=rent.getBeginData();
            c.setTime(date);
            c.add(Calendar.DATE,30);
            Date deadline=c.getTime();//到期时间
            map.put("day",sf.format(deadline));
            list1.add(map);
        }
        return list1;
    }



    public List<Map> overdue(int userId) throws ParseException {
        RentDAO rentDAO=new RentDAO();
        List<Rent> list=rentDAO.allRent(userId);
        List<Map> overlist=new ArrayList<Map>();

        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);

        for(int i=0;i<list.size();i++){
            Rent rent=list.get(i);
            Date date=rent.getBeginData();
            BookDAO bookDAO=new BookDAO();
            Map map=new HashMap();

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();

            c.setTime(date);
            c.add(Calendar.DATE,30);
            Date deadline=c.getTime();//到期时间
            Calendar c1 = Calendar.getInstance();
            Date nowdate = c1.getTime();//当前时间

            long deadline1 = deadline.getTime();
            long nowdate1 = nowdate.getTime();
            long day = (nowdate1-deadline1)/(24*60*60*1000);

            if(deadline.compareTo(nowdate)==-1){
                Book book=bookDAO.findBookByBookId(rent.getBookId());
                map.put("name",book.getName());
                map.put("day",day);
                overlist.add(map);
            }
        }
        return overlist;
    }

    //返回所有逾期借阅情况
    public List<Map> allOverdue(){
        RentDAO rentDAO=new RentDAO();
        List<Rent> list=rentDAO.allOverRent();
        List<Map> alloverlist=new ArrayList<Map>();
        Map map=new HashMap();
        BookDAO bookDAO=new BookDAO();
        UserDAO userDAO=new UserDAO();

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

            long deadline1 = deadline.getTime();
            long nowdate1 = nowdate.getTime();
            long day = (nowdate1-deadline1)/(24*60*60*1000);

            if(deadline.compareTo(nowdate)==-1){
                Book book=bookDAO.findBookByBookId(rent.getBookId());
                map.put("bookName",book.getName());
                User user=userDAO.findUserById(book.getUserId());
                map.put("userName",user.getUserName());
                user=userDAO.findUserById(rent.getUserId());
                map.put("borrowName",user.getUserName());
                map.put("day",day);
                map.put("borrowTel", user.getTel());

                alloverlist.add(map);
            }
        }
        return alloverlist;
    }
}
