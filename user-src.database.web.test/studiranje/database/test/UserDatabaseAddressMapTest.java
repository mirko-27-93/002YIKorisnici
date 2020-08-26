package studiranje.database.test;

import studiranje.ip.database.controller.DatabaseMapController;

/**
 * Тестирање када је у питању мапа база података, на серверу. 
 * @author mirko
 * @version 1.0
 */
public class UserDatabaseAddressMapTest {
	private static DatabaseMapController controller = new DatabaseMapController();
	
	public static void main(String ... args) {
		System.out.println(controller.getMap()); 
	}
}
