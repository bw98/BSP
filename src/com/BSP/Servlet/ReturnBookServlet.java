package com.BSP.Servlet;

import com.BSP.Service.BookService;
import com.BSP.Service.RentService;
import com.BSP.Service.ReserveService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.Book;
import com.BSP.bean.Rent;
import com.BSP.bean.Reserve;
import com.BSP.bean.User;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //接受JSON并转化为对象
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        RentService rentService = new RentService();
        UserService userService = new UserService();
        BookService bookService = new BookService();
        ReserveService reserveService = new ReserveService();

        try {
            //从token中获取得到访问该接口的用户名
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            String userName = (String) c.get("user_name");
            int userId  = userService.findIdByUserName(userName); //借阅者id
            User user = new User();
            user.setUserName(userName);
            user.setId(userId);
            //通过bookId得到book bean
            Book book = new Book();
            int bookId = Integer.valueOf(jsonObject.getString("bookId"));
            Map bookInfo = bookService.findBookById(bookId);
            book.setName((String) bookInfo.get("bookName"));
            book.setAuthor((String) bookInfo.get("author"));
            book.setType((String) bookInfo.get("type"));
            book.setUserId((int)bookInfo.get("userId"));
            book.setId(bookId);
            book.setImgUrl((String)bookInfo.get("imgUrl"));
            book.setIntro((String) bookInfo.get("intro"));
            book.setStatus((int)bookInfo.get("status"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            book.setFinalDay(java.sql.Date.valueOf(df.format((Date) bookInfo.get("finalDay"))));

            //查询当前用户的所有逾期图书
            List<Map> overdueBooks = rentService.overdue(userId);
            // 逾期图书和还的书是同一本，修改用户状态为正常
            if((overdueBooks.size() == 1) && (overdueBooks.get(0).get("bookName").equals(book.getName()))) {
                userService.updateUserStatus(user, 0);
            }

            Reserve reserve  = reserveService.BookInReserveNow(book);//该书的最新预约记录
            Map<String, String> resultMap = new HashMap<String, String>();
            Rent rent = new Rent();

            if(reserve != null) { //该书存在预约者
                //判断预约者状态是否正常
                if(reserveService.isReserveExpire(reserve)) {// 预约者状态为逾期用户
                    //还书
                    rent.setUserId(userService.findIdByUserName(userName));
                    rent.setBookId(bookId);
                    rentService.back(rent);

                    resultMap.put("status", "true");
                    resultMap.put("reserveStatus", "0");
                    JSONObject jsonMap = JSONObject.fromObject(resultMap);
                    response.getWriter().print(jsonMap);
                    reserveService.finishReserve(reserve, 1);

                } else { //预约者状态正常
                    Date reserveEndDate = reserve.getEndDate();
                    //需要先还书，然后再执行借书操作
                    rent.setBookId(bookId);
                    rent.setUserId(userService.findIdByUserName(userName));
                    rentService.back(rent);
                    Rent newRent = new Rent();
                    newRent.setBookId(bookId);
                    newRent.setUserId(reserve.getUserId());
                    newRent.setEndDate(reserveEndDate);
                    boolean rentStatus = rentService.rent(newRent);
                    if (rentStatus) {
                        resultMap.put("status", "true");
                        resultMap.put("reserveStatus", "1");
                        JSONObject jsonMap = JSONObject.fromObject(resultMap);
                        response.getWriter().print(jsonMap);
                        reserveService.finishReserve(reserve, 0);
                    } else {
                        resultMap.put("status", "true");
                        resultMap.put("reserveStatus", "0");
                        JSONObject jsonMap = JSONObject.fromObject(resultMap);
                        response.getWriter().print(jsonMap);
                        reserveService.finishReserve(reserve, 1);
                    }
                }
            }
            else { //该书没有预约，只执行正常的还书操作即可
                rent.setBookId(bookId);
                rent.setUserId(userService.findIdByUserName(userName));
                rentService.back(rent);
                resultMap.put("status", "true");
                resultMap.put("reserveStatus", "2");
                JSONObject jsonMap = JSONObject.fromObject(resultMap);
                response.getWriter().print(jsonMap);
            }

        } catch (Exception e) {
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap = new HashMap<String, String>();
            resultMap.put("status", "false");
            resultMap.put("error", e.getMessage());
            JSONObject jsonMap = JSONObject.fromObject(resultMap);
            response.getWriter().print(jsonMap);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
