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

        //4个判断条件判断图书是否能被预约
        if (book.getStatus() == 3) { //书被借阅时才能预约
            if(user.getStatus() != 2) {
                long judge = (reserve.getEndDate().getTime() / (24 * 60 * 60 * 1000)) - (book.getFinalDay().getTime() / (24 * 60 * 60 * 1000));
                if (judge < 0) {
                    long res = (reserve.getEndDate().getTime() / (24 * 60 * 60 * 1000)) - (rent.getEndDate().getTime() / (24 * 60 * 60 * 1000));
                    if (res > 0) {
                        reserveDAO.addReserve(reserve);
                        //设置书为"已预约"状态
                        bookDAO.updateBookStatus(reserve.getBookId(), 2);
                        return 0;
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
            return 1; //图书状态非借阅，无法预约
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

    public boolean deleteReserve(int id) {
        ReserveDAO reserveDAO = new ReserveDAO();
        if (reserveDAO.findReserveById(id) != null) {
            reserveDAO.deleteReserve(id);
        } else {
            return false;
        }
        return true;
    }

    public List<Map> allReserve(int userId) {
        ReserveDAO reserveDAO = new ReserveDAO();
        BookDAO bookDAO = new BookDAO();
        List list = reserveDAO.allReserve(userId);
        List list1 = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            Reserve reserve = (Reserve) list.get(i);
            Book book = bookDAO.findBookByBookId(reserve.getBookId());
            map.put("bookId", book.getId());
            map.put("bookName", book.getName());
            map.put("status", book.getStatus());
            map.put("endDay",reserve.getEndDate());
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
