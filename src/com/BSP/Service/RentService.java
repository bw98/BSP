package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.RentDAO;
import com.BSP.DAO.UserDAO;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Book;
import com.BSP.bean.Rent;
import com.BSP.bean.User;
import net.sf.json.JsonConfig;

import javax.tools.JavaCompiler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentService {
    public boolean rent(Rent rent)  throws Exception{
        BookDAO bookDAO = new BookDAO();
        RentDAO rentDAO = new RentDAO();
        Book book = bookDAO.findBookByBookId(rent.getBookId());
        //判断是否超出借书最大期限（天数）
        long judge = (book.getFinalDay().getTime() / (24 * 60 * 60 * 1000)) - (rent.getEndDate().getTime() / (24 * 60 * 60 * 1000));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUserById(rent.getUserId());
        if (book.getStatus() == 0 && judge > 0 && user.getStatus() != 2) {
            //设置书为"已借出"状态
            bookDAO.fingAndUpdateBook(rent.getBookId(), 3);
            rentDAO.addRent(rent);
        } else {
            return false;
        }
        return true;
    }

    public void back(Rent rent) {
        RentDAO rentDAO = new RentDAO();
        BookDAO bookDAO = new BookDAO();

        Rent rent1 = rentDAO.findRentByUseridBookid(rent);
        rentDAO.deleteRent(rent1.getId());
        bookDAO.updateBookStatus(rent.getBookId(), 0);

    }

    public boolean deleteRent(int id) {
        RentDAO rentDAO = new RentDAO();
        if (rentDAO.findRentById(id) != null) {
            rentDAO.deleteRent(id);
        } else {
            return false;
        }
        return true;
    }

    public List<Map> allRent(int userId) {
        RentDAO rentDAO = new RentDAO();
        List list = rentDAO.allRent(userId);
        BookDAO bookDAO = new BookDAO();
        List list1 = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            Rent rent = (Rent) list.get(i);
            Book book = bookDAO.findBookByBookId(rent.getBookId());
            Date endDay=rent.getEndDate();
            map.put("bookId", book.getId());
            map.put("bookName", book.getName());
            map.put("endDay", endDay);
            list1.add(map);
        }
        return list1;
    }


    public List<Map> overdue(int userId) throws ParseException {
        int overdue_flag = 0;//标志位，如果有逾期则为1，没有则为0
        RentDAO rentDAO = new RentDAO();
        List<Rent> list = rentDAO.allRent(userId);
        List<Map> overlist = new ArrayList<Map>();

        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);

        for (int i = 0; i < list.size(); i++) {
            Rent rent = list.get(i);
            BookDAO bookDAO = new BookDAO();
            Map map = new HashMap();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            Date nowdate = java.sql.Date.valueOf(df.format(new Date()));
            Date enddate = rent.getEndDate();
            long judge = (nowdate.getTime() / (24 * 60 * 60 * 1000)) - (enddate.getTime() / (24 * 60 * 60 * 1000));
            if (judge > 0) {
                Book book = bookDAO.findBookByBookId(rent.getBookId());
                map.put("bookName", book.getName());
                overlist.add(map);
                overdue_flag = 1;
            }
            if (overdue_flag == 1) {
                UserDAO userDAO = new UserDAO();
                userDAO.updateUserStatus(userId, 2);
            }
        }
        return overlist;
    }

    //返回个人所有逾期借阅情况
    /*private List<> allPastdue(){
        RentDAO rentDAO=new RentDAO();
        List<Rent> list=rentDAO.allOverRent()
    }*/

    //返回所有逾期借阅情况
    public List<Map> allOverdue() {
        RentDAO rentDAO = new RentDAO();
        List<Rent> list = rentDAO.allOverRent();
        List<Map> alloverlist = new ArrayList<Map>();
        Map map = new HashMap();
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();

        for (int i = 0; i < list.size(); i++) {
            Rent rent = list.get(i);
            Date date = rent.getBeginDate();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 30);
            Date deadline = c.getTime();//到期时间
            Calendar c1 = Calendar.getInstance();
            Date nowdate = c1.getTime();//当前时间

            long deadline1 = deadline.getTime();
            long nowdate1 = nowdate.getTime();
            long day = (nowdate1 - deadline1) / (24 * 60 * 60 * 1000);

            if (deadline.compareTo(nowdate) == -1) {
                Book book = bookDAO.findBookByBookId(rent.getBookId());
                map.put("bookName", book.getName());
                User user = userDAO.findUserById(book.getUserId());
                map.put("userName", user.getUserName());
                user = userDAO.findUserById(rent.getUserId());
                map.put("borrowName", user.getUserName());
                map.put("day", day);
                map.put("borrowTel", user.getTel());

                alloverlist.add(map);
            }
        }
        return alloverlist;
    }


}
