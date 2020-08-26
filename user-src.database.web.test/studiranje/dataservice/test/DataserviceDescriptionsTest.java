package studiranje.dataservice.test;

import java.io.IOException;

import studiranje.ip.database.controller.DatasourceMapController;
import studiranje.ip.datasource.description.model.DataSourceDescription;
import studiranje.ip.datasource.description.util.BasicString;

/**
 * Тестни примјери за описе базе података. 
 * @author mirko
 * @version 1.0
 */
public class DataserviceDescriptionsTest {
	public static final DatasourceMapController controller = new DatasourceMapController(); 
	public static void main(String ... args) throws IOException {
		controller.load();
		for(DataSourceDescription<BasicString> ds : controller.getDataDescription().values()) {
			System.out.println(); 
			System.out.println("Ime : "+ds.getName().toString());
			System.out.println("Tip : "+ds.getType().toString());
			if(ds.isDatabase())    System.out.println("Baza podataka : "+ds.productDatabase().getDatabase().toString());
			if(ds.isDataservice()) System.out.println("Servis : "+ds.productDataservice().getService().toString());
			if(ds.isDataservice()) System.out.println("Klasa fabrike adaptera i kontrole : "+ds.productDataengine().getEngine().toString());
		}
	}
}
