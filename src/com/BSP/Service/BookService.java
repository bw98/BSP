package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.Util.ImgBinUtil;
import com.BSP.bean.Book;

import java.util.List;

public class BookService {
    public List<Book> searchbook(String name) {
        BookDAO bookDAO = new BookDAO();
        List booklist = bookDAO.searchBook(name);
        return booklist;
    }

    public String uploadBookImg(String bookId, String imgBin) {
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findBookByBookId(bookId);
        if (book == null) {
            return null;
        }

        String savePath = "WEB-INF/BookPhoto/" + bookId + ".jpg";
        ImgBinUtil.base64StringToImage(imgBin, bookId, savePath);
        bookDAO.updateImgUrl(bookId, savePath);
        return savePath;
    }
}
