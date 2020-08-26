package studiranje.database.test;

import studiranje.ip.database.controller.DatabaseSwitchController;

/**
 * Тест за контролу избора базе података. 
 * Технички тест 001. 
 * @author mirko
 * @version 1.0
 */
public class UserDatabaseSwitchT001 {
	private static DatabaseSwitchController controller = new DatabaseSwitchController(); 
	public static void main(String ... args) {
		for(String dbName : controller.getDatabaseNames()) {
			System.out.println(dbName);
			System.out.println(controller.getDatabaseAdrressSplited(dbName).get("user")); 
			System.out.println(controller.getDatabaseAdrressSplited(dbName).get("password"));
			System.out.println(controller.getDatabaseAdrressSplited(dbName).get("host"));
			System.out.println(controller.getDatabaseAdrressSplited(dbName).get("port"));
			System.out.println(controller.getDatabaseAdrressSplited(dbName).get("database")); 
			System.out.println(); 
		}
	}
}
