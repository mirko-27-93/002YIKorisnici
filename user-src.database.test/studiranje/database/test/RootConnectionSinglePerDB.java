package studiranje.database.test;

import studiranje.ip.data.RootConnectionPool;

/**
 * Провијера једнострукости коријенских конекција по бази података. 
 * @author mirko
 * @version 1.0
 */
public class RootConnectionSinglePerDB {
	public static void main(String ... args) {
		RootConnectionPool connection1 =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
		RootConnectionPool connection2 =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
		System.out.println(connection1);
		System.out.println(connection2);
	}
}
