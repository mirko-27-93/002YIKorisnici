package studiranje.database.test;

import java.sql.SQLException;
import java.util.List;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.RootConnectionPool;

/**
 * Листа база података у систему за управљање истим. 
 * @author mirko
 * @version 1.0
 */
public class DBRootDAOMain001 {
	public static void main(String ... args) throws SQLException {
		RootConnectionPool connection =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
		DBRootDAO dao = new DBRootDAO(connection); 
		List<String> databases = dao.getDatabases(); 
		for(String database: databases)
		System.out.println(database); 
	}
}
