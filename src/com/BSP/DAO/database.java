package com.BSP.DAO;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/*
 * 访问数据库
 * */
public class database {
	public SqlSession getSqlsession() throws IOException{
		Reader reader = Resources.getResourceAsReader("com/BSP/config/Configuration.xml");
		SqlSessionFactory sqlsessionfactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession sqlsession=sqlsessionfactory.openSession();
		return sqlsession;
	}
}
