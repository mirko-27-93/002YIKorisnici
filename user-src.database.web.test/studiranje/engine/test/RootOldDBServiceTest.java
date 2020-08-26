package studiranje.engine.test;

import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import studiranje.ip.database.model.DBRecordInfo;
import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;
import studiranje.ip.engine.DBRootDAOOldDBSevice;
import studiranje.ip.service.object.DatabaseListRequest;
import studiranje.ip.service.object.DatabaseListResponse;
import studiranje.ip.service.object.TableDescriptionRequest;
import studiranje.ip.service.object.TableDescriptionResponse;
import studiranje.ip.service.object.TableListRequest;
import studiranje.ip.service.object.TableListResponse;
import studiranje.ip.service.object.UserListRequest;
import studiranje.ip.service.object.UserListResponse;

/**
 * Тестирање операција схемама и листом база података, односо табела 
 * када су у питању, операције код удаљене, 
 * старе релационе MySQL 5.4, 5.6, 5.7 базе података.  
 * Дакле, ту је у питању баратање преко веб сервиса. 
 * @author mirko
 * @version 1.0
 */
public class RootOldDBServiceTest {
	public static final Scanner scanner = new Scanner(System.in); 
	public static final String database = "http://sql7355822:ujHkfPwHN7@sql7.freemysqlhosting.net:3306/sql7355822";
	public static final String services = "http://localhost:8085/003YIKorisniciODS"; 
	public static final DBRootDAOOldDBSevice service = new DBRootDAOOldDBSevice(database, services); 
	public static final boolean ERROR_REMIX = false; 
	
	
	public static void main(String ... args) {
		System.out.println("OPERACIJE : ");
		System.out.println("\t1. Pregled korisnika (BAP)");
		System.out.println("\t2. Pregled baza podataka");
		System.out.println("\t3. Pregled tabela");
		System.out.println("\t4. Pregled opisa tabela (kolona)");
		System.out.println(); 
		System.out.print("IZBOR : ");
		try {
			int izbor = Integer.parseInt(scanner.nextLine()); 
			switch(izbor) {
				case 1:
					System.out.println("PREGLED KORISNIKA");
					previewUsers();  
					break;
				case 2: 
					System.out.println("PREGLED BAZA PODATAKA");
					previewDatabases(); 
					break;
				case 3:
					System.out.println("PREGLED TABELA "); 
					previewTables(); 
					break;
				case 4: 
					System.out.println("PREGLED TABELE");
					previewTable(); 
					break;
				default: 
					System.out.println("POGRESAN IZBOR"); 
					break;
			}
		}catch(Exception ex) {
			if(ERROR_REMIX) ex.printStackTrace();
			System.out.println("POGRESAN IZBOR ILI GRESKA."); 
		}
	}
	
	public static void previewUsers() throws IOException {
		UserListRequest request = new UserListRequest(); 
		request.setDatabaseAPIAddress(database);
		ClientConfig config = new ClientConfig()
							.register(UserListRequest.class)
							.register(UserListResponse.class); 
		Client client = ClientBuilder.newClient(config);
		WebTarget resource = client.target("http://localhost:8085/003YIKorisniciODS/api/database/root/users"); 
		UserListResponse resp = resource.request(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON), UserListResponse.class);
		System.out.println("KORISNICI : "); 
		for(DBUserData user: resp.getUsers()) {
			System.out.println();
			System.out.println("\tKorisnicko ime: "+user.getUserName());
			System.out.println("\tHost korisnika BAP: "+user.getHostName()); 
			System.out.println("\tString sifre BAP: "+user.getAuthenticationString()); 
		}
	}
	
	public static void previewDatabases() {
		DatabaseListRequest request = new DatabaseListRequest();
		request.setDatabaseAPIAddress(database);
		ClientConfig config = new ClientConfig()
							.register(DatabaseListRequest.class)
							.register(DatabaseListResponse.class); 
		Client client = ClientBuilder.newClient(config);
		WebTarget resource = client.target("http://localhost:8085/003YIKorisniciODS/api/database/root/databases"); 
		DatabaseListResponse resp = resource.request(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON), DatabaseListResponse.class);
		System.out.println("BAZE PODATAKA : "); 
		System.out.println();
		for(String database: resp.getDatabases()) {
			System.out.println("\t"+database);
		}
	}
	
	public static void previewTables() {
		TableListRequest request = new TableListRequest();
		request.setDatabaseAPIAddress(database);
		ClientConfig config = new ClientConfig()
				.register(TableListRequest.class)
				.register(TableListResponse.class); 
		Client client = ClientBuilder.newClient(config);
		WebTarget resource = client.target("http://localhost:8085/003YIKorisniciODS/api/database/root/tables"); 
	
		System.out.print("BAZA PODATAKA : ");
		String bap = scanner.nextLine(); 
		try {
			System.out.println("TABELE : ");
			request.setDatabaseName(bap);
			TableListResponse resp = resource.request(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON), TableListResponse.class);
			for(DBTableInfo info: resp.getTableInfo()) {
				System.out.println("\t"+info.getTableName());
			}
		}catch(Exception ex) {
			System.out.println("BAZA PODATAKA NE POSTOJI ILI SE DESILA GRESKA."); 
		}
	}
	
	public static void previewTable() {
		TableDescriptionRequest request = new TableDescriptionRequest();
		request.setDatabaseAPIAddress(database);
		ClientConfig config = new ClientConfig()
				.register(TableDescriptionRequest.class)
				.register(TableDescriptionResponse.class); 
		Client client = ClientBuilder.newClient(config);
		WebTarget resource = client.target("http://localhost:8085/003YIKorisniciODS/api/database/root/table"); 
		System.out.print("BAZA PODATAKA : ");
		String bap = scanner.nextLine(); 
		System.out.print("TABELA : ");
		String tab = scanner.nextLine(); 
		try {
			System.out.println("KOLONE TABELE - OPIS : ");
			request.setDatabaseName(bap);
			request.setTableName(tab);
			TableDescriptionResponse resp = resource.request(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON), TableDescriptionResponse.class);
			for(DBRecordInfo info: resp.getColumns()) {
				System.out.println();
				System.out.println("\tPolje (naziv) kolone: "+info.getField());
				System.out.println("\tTip podataka kolone : "+info.getType());
				System.out.println("\tPodrazumjevano : "+info.getClassic());
				System.out.println("\tNula podatak : "+info.getZero());
				System.out.println("\tEkstra informacije : "+info.getExtra());
				System.out.println("\tKljuc : "+info.getKey());
			}
		}catch(Exception ex) {
			System.out.println("BAZA PODATAKA ILI TABELA NE POSTOJI, ILI SE DESILA GRESKA."); 
		}
	}
}
