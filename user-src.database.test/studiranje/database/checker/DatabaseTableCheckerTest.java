package studiranje.database.checker;

import java.sql.SQLException;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.RootConnectionPool;
import studiranje.ip.database.controller.DatabaseSchemaIOController;

/**
 * Тестирање табела база података, на схему која је потребна, односно
 * детектовање табела база података које одговарају схемама у базама података
 * односно хосту сервера база података. Тест опције. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseTableCheckerTest {
	private static RootConnectionPool connection =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
	private static DBRootDAO dao = new DBRootDAO(connection);
	private static DatabaseSchemaIOController controller = new DatabaseSchemaIOController(dao);
	
	public static void main(String ... args) throws SQLException {
		System.out.println("=================================");
		System.out.println(controller.checkDatabaseInSchema(DatabaseTableCheckerTest.class.getResourceAsStream("/studiranje/ip/database/schema/db.yi.json"), "ip"));
		System.out.println(controller.checkDatabaseInSchema(DatabaseTableCheckerTest.class.getResourceAsStream("/studiranje/ip/database/schema/db.yi.json"), "yi"));
		System.out.println(controller.checkDatabaseInSchema(DatabaseTableCheckerTest.class.getResourceAsStream("/studiranje/ip/database/schema/db.yi.json"), "sakila"));
		System.out.println("=================================");
		System.out.println(controller.checkTablesInSchema(DatabaseTableCheckerTest.class.getResourceAsStream("/studiranje/ip/database/schema/db.yi.json"), "ip"));
		System.out.println(controller.checkTablesInSchema(DatabaseTableCheckerTest.class.getResourceAsStream("/studiranje/ip/database/schema/db.yi.json"), "yi"));
		System.out.println(controller.checkTablesInSchema(DatabaseTableCheckerTest.class.getResourceAsStream("/studiranje/ip/database/schema/db.yi.json"), "sakila"));
		System.out.println("=================================");
	}
}
