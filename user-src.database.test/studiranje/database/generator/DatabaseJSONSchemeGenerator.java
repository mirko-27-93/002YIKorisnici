package studiranje.database.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.RootConnectionPool;
import studiranje.ip.database.controller.DatabaseSchemaIOController;

/**
 * Примјер генерисања, ЈСОН схема за базе података и њихове табле. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseJSONSchemeGenerator {
	private static RootConnectionPool connection =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
	private static DBRootDAO dao   = new DBRootDAO(connection);
	private static DatabaseSchemaIOController controller = new DatabaseSchemaIOController(dao);
	
	public static void main(String ... args) throws FileNotFoundException, SQLException, IOException {
		 File res1 = new File("test.temp.io/db.yi.json");
		 controller.describeDatabaseInJSON("yi", res1);
	}
}
