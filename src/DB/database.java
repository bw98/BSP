package DB;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

/*
 * 访问数据库
 * */
public class database {
	public SqlSession getSqlsession() throws IOException{
		Reader reader = Resources.getResourceAsReader("config/Configuration.xml");
		SqlSessionFactory sqlsessionfactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlsession=sqlsessionfactory.openSession();
		return sqlsession;
	}
}
