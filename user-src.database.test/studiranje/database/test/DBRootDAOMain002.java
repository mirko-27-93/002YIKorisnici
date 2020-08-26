package studiranje.database.test;

import java.sql.SQLException;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.DBUserDAO;
import studiranje.ip.data.RootConnectionPool;

/**
 * Тест око конекционих пула подразумјеваних за ову DAO хијерархију.  
 * @author mirko
 * @version 1.0
 */
public class DBRootDAOMain002 {
	public static void main(String ... args) throws SQLException {
		RootConnectionPool connection =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
		DBRootDAO dao1 = new DBRootDAO(connection); 
		DBUserDAO dao2 = new DBUserDAO(connection);
		DBUserDAO dao3 = new DBUserDAO(connection);
		System.out.println(dao1); 
		System.out.println(dao2);
		System.out.println(dao3);
		System.out.println(dao1.getConnections()); 
		System.out.println(dao2.getConnections());
		System.out.println(dao3.getConnections());
		System.out.println(dao3.getEmail("petar"));
		System.out.println(dao3.getEmail("marko"));
	}
}
