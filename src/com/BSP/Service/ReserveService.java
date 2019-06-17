package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.RentDAO;
import com.BSP.DAO.ReserveDAO;
import com.BSP.DAO.UserDAO;
import com.BSP.bean.Book;
import com.BSP.bean.Rent;
import com.BSP.bean.Reserve;
import com.BSP.bean.User;

import java.util.*;

public class ReserveService {
    public int reserve(Reserve reserve) throws Exception {
        BookDAO bookDAO = new BookDAO();
        ReserveDAO reserveDAO = new ReserveDAO();
        Book book = bookDAO.findBookByBookId(reserve.getBookId());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUserById(reserve.getUserId());
        RentDAO rentDAO = new RentDAO();
        Rent rent = rentDAO.findRentByBookId(reserve.getBookId());

        //预约成功返回0。预约失败的话有四种情况：
        //1、图书状态非借阅，无法预约，此时返回值为1；
        //2、逾期的用户不能预约，此时返回值为2；
        //3、预约时的endDate晚于图书上传者规定的finalDay，此时返回值为3；
        //4、预约时的endDate早于借书者的endDate，此时返回值为4
        if (book.getStatus() == 3) { //书被借阅时才能预约
            if(user.getStatus() != 2) {
                long judge = (reserve.getEndDate().getTime() / (24 * 60 * 60 * 1000)) - (book.getFinalDay().getTime() / (24 * 60 * 60 * 1000));
                if (judge < 0) {
                    long res = (reserve.getEndDate().getTime() / (24 * 60 * 60 * 1000)) - (rent.getEndDate().getTime() / (24 * 60 * 60 * 1000));
                    if (res > 0) {
                        //给图书加锁、预约并设置书为"已预约"状态
                        boolean returnFlag = reserveDAO.addReserveUnderLock(reserve, reserve.getBookId(), 2);
                        if (returnFlag) {
                            return 0; //预约成功
                        }
                        else {
                            return 1; //存在并发问题，比如图书状态突然被改成非借阅而导致无法预约
                        }
                    } else {
                     return 4; //预约时的endDate早于借书者的endDate
                    }
                } else {
                    return 3; //预约时的endDate晚于图书上传者规定的finalDay
                }
            } else {
                return 2; //逾期的用户不能预约
            }
        } else {
            return 1; //存在并发问题，比如图书状态突然被改成非借阅而导致无法预约
        }
    }

    //通知
    public List notice(int userId) {
        ReserveDAO reserveDAO = new ReserveDAO();
        List<Reserve> list = reserveDAO.allNotice(userId);
        for (int i = 0; i < list.size(); i++) {
            Reserve reserve = list.get(i);
            //通知后使其失效
            reserveDAO.deleteNotice(reserve.getId());
        }
        return list;
    }

    //带书名的通知
    public List notice2(int userId) {
        ReserveDAO reserveDAO = new ReserveDAO();
        BookDAO bookDAO = new BookDAO();
        List<Reserve> reserveList = reserveDAO.allNotice(userId);
        List<Map> resultList = new ArrayList<Map>();
        for (int i = 0; i < reserveList.size(); i++) {
            Reserve reserve = reserveList.get(i);
            Book book = bookDAO.findBookByBookId(reserve.getBookId());
            Map map = new HashMap();
            map.put("bookName",book.getName());
            map.put("finalStatus", reserve.getFinalStatus());
            resultList.add(map);
            //通知后使其失效
            reserveDAO.deleteNotice(reserve.getId());
        }
        return resultList;
    }

    public boolean deleteReserve(int id) {
        ReserveDAO reserveDAO = new ReserveDAO();
        if (reserveDAO.findReserveById(id) != null) {
            reserveDAO.deleteReserve(id);
        } else {
            return false;
        }
        return true;
    }

    public List<Map> allReserve(int userId) throws Exception {
        ReserveDAO reserveDAO = new ReserveDAO();
        BookDAO bookDAO = new BookDAO();
        RentDAO rentDAO = new RentDAO();
        List list = reserveDAO.allReserve(userId);
        List list1 = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            Reserve reserve = (Reserve) list.get(i);
            Book book = bookDAO.findBookByBookId(reserve.getBookId());
            Rent rent = rentDAO.findRentByBookId(reserve.getBookId());
            map.put("bookId", book.getId());
            map.put("bookName", book.getName());
            map.put("status", book.getStatus());
            map.put("endDate",rent.getEndDate());
            list1.add(map);
        }
        return list1;
    }

    //根据bookId查找该书正在被预约的记录
    public Reserve BookInReserveNow(Book book) throws Exception {
        ReserveDAO reserveDAO = new ReserveDAO();
        Reserve reserve = reserveDAO.findCurrentReserveByBookId(book.getId());
        if (reserve != null) {
            return reserve;
        } else {
            return null;
        }
    }

    public boolean isReserveExpire(Reserve reserve) throws Exception {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUserById(reserve.getUserId());
        if (user.getStatus() == 2) {
            return true;
        } else {
            return false;
        }
    }

    //结束一条预约记录时需要记录相关信息，"是否预约成功"、"该预约记录已失效"、"预约记录未提醒"
    public void finishReserve(Reserve reserve, int finalStatus) throws Exception {
        ReserveDAO reserveDAO = new ReserveDAO();

        reserve.setStatus(1); //标志该预约记录已失效
        reserve.setNoticeStatus(0); //预约记录未提醒
        reserve.setFinalStatus(finalStatus);//是否预约成功

        reserveDAO.updateStatus(reserve);
        reserveDAO.updateNoticeStatus(reserve);
        reserveDAO.updateFinalStatus(reserve);
    }
}
