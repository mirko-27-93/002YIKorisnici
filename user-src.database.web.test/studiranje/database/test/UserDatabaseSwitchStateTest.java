package studiranje.database.test;

import studiranje.ip.database.controller.DatabaseSwitchConfigurationController;

/**
 * Тестови за функционисање очитавања конфигурација свича, када је у питању тест. 
 * @author mirko
 * @version 1.0
 */
public class UserDatabaseSwitchStateTest {
	private static DatabaseSwitchConfigurationController controller = new DatabaseSwitchConfigurationController();

	public static void main(String ... args) {
		System.out.println(controller.getProperties()); 
	}
}
