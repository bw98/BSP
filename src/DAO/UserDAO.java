package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import DB.database;
import bean.Book;
import bean.User;

public class UserDAO {
	//获得当前所有书籍信息
	public List<Book> queryBookList(){
		database DB=new database();
		List<Book> booklist=new ArrayList<Book>();
		SqlSession sqlsession=null;
		try {
			sqlsession=DB.getSqlsession();
			booklist=sqlsession.selectList("User.allbook");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sqlsession!=null){
				sqlsession.close();
			}
		}
		return booklist;
	}
	
	//获得单个书籍信息
		public Book findBook(){
			database DB=new database();
			SqlSession sqlsession=null;
			Book book=null;
			try {
				sqlsession=DB.getSqlsession();
				book=sqlsession.selectOne("User.findbook","");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(sqlsession!=null){
					sqlsession.close();
				}
			}
			return book;
		}
		
}
