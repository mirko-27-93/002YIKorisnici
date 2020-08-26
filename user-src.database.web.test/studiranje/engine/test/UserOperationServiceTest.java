package studiranje.engine.test;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Тестирање клијента и пријавних односно одјавних сесија. 
 * @author mirko
 * @version 1.0
 */
public class UserOperationServiceTest {
	public static Scanner scanner = new Scanner(System.in);  
	
	private static CloseableHttpClient httpClient; 
	private static String sessionId = ""; 
	private static String username = ""; 
	
	static {
		try {
			try {
				httpClient = HttpClients.createDefault();
				URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet");
				
				HttpPost httpPost =  new HttpPost(url.toString());
				httpPost.setEntity(new UrlEncodedFormEntity(new ArrayList<>(), Charset.forName("UTF-8")));
				CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
				sessionId = httpResponse.getFirstHeader("Set-Cookie").getValue();  
				
				System.out.println("INIT SUCCESS"); 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}
		}catch(Exception ex) {
			System.out.println("INIT ERROR");
		}
	}
	
	static {
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/DatabaseChangeServlet");
			
			HttpPost httpPost =  new HttpPost(url.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(new ArrayList<>(), Charset.forName("UTF-8")));
			
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			postParameters.add(new BasicNameValuePair("db_name", "http://root:root@localhost:3306/yimp"));

			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
		
			boolean success = root.get("success").getAsBoolean();
			String message = root.get("message").getAsString();
			
			System.out.println(root);
			
			if(!success) throw new RuntimeException(message);
			System.out.println("DATABASE INIT SUCCESS"); 
		}catch(Exception ex) {
			System.out.println("DATABASE INIT ERROR"); 
		}
	}
	
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public String getSessionId() {
		return sessionId;
	}

	public static void logoutAll() {
		System.out.println("OPSTA ODJAVA KORISNIKA");
		String username = UserOperationServiceTest.username; 
		if(username==null || username.trim().length()==0) {
			System.out.println("Klijent nije prijavljen."); 
			return; 
		} 
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGOUT_ALL_SESSIONS_FOR_USER");
			HttpPost httpPost = new HttpPost(url.toString());
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			   
			postParameters.add(new BasicNameValuePair("username", username));
			postParameters.add(new BasicNameValuePair("operation", "LOGOUT_ALL_SESSIONS_FOR_USER"));
			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			System.out.println(root); 
			
			UserOperationServiceTest.username = "";
		}catch(Exception ex) {
			System.out.println("Greska.");
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
		try {
			username = ""; 
		}catch(Exception ex) {
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void logout() {
		System.out.println("ODJAVA KORISNIKA");
		if(username==null || username.trim().length()==0) {
			return; 
		} 
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGOUT");
			HttpPost httpPost = new HttpPost(url.toString());
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			   
			postParameters.add(new BasicNameValuePair("operation", "LOGOUT"));
			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			System.out.println(root); 
		}catch(Exception ex) {
			System.out.println("Greska.");
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
		try {
			username = ""; 
		}catch(Exception ex) {
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void login() {
		System.out.println("PRIJAVA KORISNIKA");
		if(username!=null && username.trim().length()!=0) {
			System.out.println("Klijent je prijavljen."); 
			return; 
		}
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine();
		System.out.print("Lozinka : ");
		String password = scanner.nextLine();
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGIN");
			HttpPost httpPost = new HttpPost(url.toString());
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			   
			postParameters.add(new BasicNameValuePair("username", username));
			postParameters.add(new BasicNameValuePair("password", password));
			postParameters.add(new BasicNameValuePair("operation", "LOGIN"));
			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			System.out.println(root); 
			
			boolean success = root.get("success").getAsBoolean();
			String message = root.get("message").getAsString();
			if(!success) throw new RuntimeException(message); 
			
			UserOperationServiceTest.username = username;
		}catch(Exception ex) {
			System.out.println("Greska.");
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void preview() {
		System.out.println(sessionId); 
		System.out.println(username); 
	}
	
	public static void menu() {
		System.out.println("IZBORNIK : \n"); 
		System.out.println("\t0. Izlaz"); 
		System.out.println("\t1. Pregled");
		System.out.println("\t2. Prijava");
		System.out.println("\t3. Odjava");
		System.out.println("\t4. Opsta odjava");
		System.out.println("\t5. Registracija");
		System.out.println("\t6. Izmjene");
		System.out.println("\t7. Brisanje"); 
		System.out.println("\t8. Ocitavanje"); 
		System.out.println("\t9. Broj korisnika");
		System.out.println("\t10. Korisnici");
		System.out.println("\t11. Opis"); 
		System.out.println("\t12. Konfiguracije");
		System.out.println("\t13. Slike");
		
		System.out.println();
	}
	
	public static int inputChoose() {
		System.out.print("IZBOR : ");
		try {
			return Integer.parseInt(scanner.nextLine());
		}catch(Exception ex) {
			return -1; 
		}
	}
	
	public static void register() {
		System.out.println("DODAVANJE NOVOG KORISNIKA"); 
		try {
			if(username.trim().length()!=0) {
				throw new RuntimeException("USER DUPLICATE"); 
			}
			System.out.print("Novo korisnicko ime : "); 
			String username = scanner.nextLine();
			System.out.print("Nova loznika : "); 
			String password = scanner.nextLine();
			System.out.print("Nova lozinka, potvrda : "); 
			String password2 = scanner.nextLine();
			System.out.print("Ime : "); 
			String name = scanner.nextLine();  
			System.out.print("Prezime : "); 
			String surname = scanner.nextLine();
			System.out.print("Email : "); 
			String email = scanner.nextLine(); 
			
			try {
				if(!password.contentEquals(password2)) throw new RuntimeException("PASSWORD 12 MISSMATCH"); 
				URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/REGISTER"); 
				
				HttpPost httpPost = new HttpPost(url.toString());
				ArrayList<NameValuePair> postParameters = new ArrayList<>();
				   
				postParameters.add(new BasicNameValuePair("username", username));
				postParameters.add(new BasicNameValuePair("password", password));
				postParameters.add(new BasicNameValuePair("operation", "REGISTER"));
				postParameters.add(new BasicNameValuePair("firstname", name));
				postParameters.add(new BasicNameValuePair("secondname", surname));
				postParameters.add(new BasicNameValuePair("useremail", email));
				postParameters.add(new BasicNameValuePair("old_password", password));
				
				httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
				CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
				JsonParser parser = new JsonParser(); 
				JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
				
				boolean success = root.get("success").getAsBoolean(); 
				if(success)  UserOperationServiceTest.username = username;
				
				if(!success) throw new RuntimeException(root.get("message").getAsString()); 
			}catch(RuntimeException ex) {
				System.out.println("Greska pri registriovanja.");
			}catch(Exception ex) {
				System.out.println("Greska pri registriovanja.");
			}
		}catch(Exception ex) {
			System.out.println("GRESKA PRI REGISTROVANJU"); 
		}
	}
	
	
	public static void erase() {
		System.out.println("BRISANJE PODATAKA");
		try {
			if(username==null || username.trim().length()==0) {
				throw new RuntimeException("USER NOT FOUND"); 
			}
			
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/DELETE"); 
			HttpPost httpPost = new HttpPost(url.toString());
			
			System.out.print("Lozinka : "); 
			String password = scanner.nextLine();
			
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			postParameters.add(new BasicNameValuePair("operation", "DELETE"));
			postParameters.add(new BasicNameValuePair("old_password", password));
			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			
			System.out.println(root);
			
			boolean success = root.get("success").getAsBoolean();
			String message = root.get("message").getAsString(); 
			
			if(!success) throw new RuntimeException(message); 
			
			username = ""; 
		}catch(RuntimeException ex) {
			System.out.println(ex.getMessage());
			System.out.println("GRESKA PRI BRISANJU"); 
		}catch(Exception ex) {
			System.out.println(ex.getMessage()); 
			System.out.println("GRESKA PRI BRISANJU"); 
		}
	}
	
	
	public static void update() {
		System.out.println("POSTAVLJANJE PODATAKA");
		
		try {
			if(username==null || username.trim().length()==0) 
				throw new RuntimeException("NO LOGGED USER");
			
			
			System.out.print("Stara lozinka : ");
			String oldPassword = scanner.nextLine(); 
			System.out.print("Novo korisnicko ime : "); 
			String newUsername = scanner.nextLine();
			System.out.print("Nova loznika : "); 
			String password = scanner.nextLine();
			System.out.print("Nova lozinka, potvrda : "); 
			String password2 = scanner.nextLine();
			System.out.print("Ime : "); 
			String name = scanner.nextLine();  
			System.out.print("Prezime : "); 
			String surname = scanner.nextLine();
			System.out.print("Email : "); 
			String email = scanner.nextLine(); 
			
			
			if(!password.contentEquals(password2)) throw new RuntimeException("PASSWORD MISSMATCH EXCEPTION"); 
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/UPDATE"); 
			HttpPost httpPost = new HttpPost(url.toString());
			
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			postParameters.add(new BasicNameValuePair("operation", "UPDATE"));
			postParameters.add(new BasicNameValuePair("old_password", oldPassword));
			postParameters.add(new BasicNameValuePair("username", newUsername));
			postParameters.add(new BasicNameValuePair("password", password));
			postParameters.add(new BasicNameValuePair("firstname", name));
			postParameters.add(new BasicNameValuePair("secondname", surname));
			postParameters.add(new BasicNameValuePair("useremail", email));
			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			
			System.out.println(root);
			
			boolean success = root.get("success").getAsBoolean();
			String message = root.get("message").getAsString(); 
			
			if(!success) throw new RuntimeException(message); 
			
			username = newUsername;
		}
		catch(RuntimeException ex) {
			System.out.println(ex.getMessage()); 
			System.out.println("GRESKA PRI IZMJENAMA."); 
		}catch(Exception ex) {
			System.out.println(ex.getMessage()); 
			System.out.println("GRESKA PRI IZMJENAMA.");   
		}
	}
	
	public static void detailsBasic() {
		try {
			System.out.println("Podaci o korisniku : ");
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/CountryUserInfoServlet?detail=full"); 
			if(username==null || username.trim().length()==0) 
				throw new RuntimeException("NO LOGGED USER");
			
			HttpPost httpPost = new HttpPost(url.toString());
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			
			System.out.println(root); 
		}catch(Exception ex) {
			System.out.println("GRESKA PRI OCITAVANJU PODATAKA O KORISNIKU"); 
		}
	}
	
	
	public static void detailsUserList() {
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserListInfoServlet");
			
			HttpPost httpPost = new HttpPost(url.toString());
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			
			System.out.println(root); 
		}catch(Exception ex) {
			System.out.println("Greska pri informisanju o korisniku baze podataka.");
		}
	}
	
	
	public static void userList() {
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserListServlet");
			
			HttpPost httpPost = new HttpPost(url.toString());
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			
			JsonParser parser = new JsonParser(); 
			JsonArray root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonArray(); 
			
			System.out.println(root); 
		}catch(Exception ex) {
			System.out.println("Greska pri ocitavanju liste baze podataka."); 
		}
	}
	
	public static void userDescription() {
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserDescriptionGetServlet");
			
			HttpPost httpPost = new HttpPost(url.toString());
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			String text = new String(httpResponse.getEntity().getContent().readAllBytes(), "UTF-8"); 
			
			System.out.println(httpResponse.getCode()); 
			System.out.println(text); 
		}catch(Exception ex) {
			System.out.println("Greska pri informisanju o konfiguracijama korisnika");
		}
	}
	
	public static void userConfiguration() {
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserFlagsGetServlet");
			
			HttpPost httpPost = new HttpPost(url.toString());
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			
			System.out.println(root); 
		}catch(Exception ex) {
			System.out.println("Greska pri informisanju o konfiguracijama korisnika");
		}
	}
	
	public static void images() {
		if(username==null || username.trim().length()==0) {
			System.out.println("Korisnik nije prijavljen"); 
			return; 
		}
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserRealPicturesInfoServlet");
			HttpPost httpPost = new HttpPost(url.toString());
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			
			if(httpResponse.getCode()!=200) {
				System.out.println("Greska. "+httpResponse.getCode());
				return;
			}
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			
			System.out.println(root);
			
			JsonElement countryImage = root.get("image.country");
			JsonElement profileImage = root.get("image.profile");
			JsonElement userImage    = root.get("image.user");
			
			if(countryImage==null) System.out.println("Zastava drzave korisnka ne postoji.");
			if(profileImage==null) System.out.println("Profilna slika drzave korisnika ne postoji.");
			if(userImage==null)	   System.out.println("Korisnicka slika za korisnika ne postoji.");
			
			if(countryImage!=null) System.out.println("Zastava drzave korisnika : "+countryImage);
			if(profileImage!=null) System.out.println("Profilna slika : "+profileImage);
			if(userImage!=null) System.out.println("Korisnicka slika : " +userImage);
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Ocitavanje imena datoteka slika prekinuto.");
		}
	}
	
	public static void main(String ... args) {
		System.out.println("ZDRAVO."); 
		while(true) {
			menu(); 
			int izbor = inputChoose(); 
			System.out.println(); 
			if(izbor==0) break;
			switch(izbor) {
				case 1: 
					preview(); 
					break;
				case 2: 
					login(); 
					break;
				case 3: 
					logout(); 
					break;
				case 4: 
					logoutAll(); 
					break;
				case 5:
					register(); 
					break; 
				case 6: 
					update();
					break; 
				case 7: 
					erase(); 
					break; 
				case 8: 
					detailsBasic();
					break;
				case 9: 
					detailsUserList(); 
					break;
				case 10: 
					userList();
					break; 
				case 11: 
					userDescription(); 
					break; 
				case 12:
					userConfiguration();
					break;
				case 13:
					images();
					break;
				default: 
					System.out.println("POGRESAN IZBOR."); 
			}
			System.out.println(); 
		}
		logout(); 
		System.out.println("POZDRAV."); 
	}
}
