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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Тестирање клијента и пријавних односно одјавних сесија. 
 * @author mirko
 * @version 1.0
 */
public class HttpServerClientSessionTest {
	public static Scanner scanner = new Scanner(System.in);  
	
	private static CloseableHttpClient httpClient; 
	private static String sessionId = ""; 
	private static String username = ""; 
	
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public String getSessionId() {
		return sessionId;
	}

	public static void logoutAll() {
		System.out.println("OPSTA ODJAVA KORISNIKA");
		String username = HttpServerClientSessionTest.username; 
		if(httpClient==null) {
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
		}catch(Exception ex) {
			System.out.println("Greska.");
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
		try {
			if(httpClient!=null) httpClient.close();
			httpClient = null;
			sessionId = "";
		}catch(Exception ex) {
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void logout() {
		System.out.println("ODJAVA KORISNIKA");
		if(httpClient==null) {
			System.out.println("Klijent nije prijavljen."); 
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
			if(httpClient!=null) httpClient.close();
			httpClient = null;
			sessionId = "";
		}catch(Exception ex) {
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void login() {
		System.out.println("PRIJAVA KORISNIKA");
		if(httpClient!=null) {
			System.out.println("Klijent je prijavljen."); 
			return; 
		}
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine(); 
		System.out.print("Lozinka : ");
		String password = scanner.nextLine(); 
		HttpServerClientSessionTest.username = username;
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGIN");
			httpClient = HttpClients.createDefault(); 
			HttpPost httpPost = new HttpPost(url.toString());
			ArrayList<NameValuePair> postParameters = new ArrayList<>();
			   
			postParameters.add(new BasicNameValuePair("username", username));
			postParameters.add(new BasicNameValuePair("password", password));
			postParameters.add(new BasicNameValuePair("operation", "LOGIN"));
			
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters, Charset.forName("UTF-8")));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			sessionId = httpResponse.getFirstHeader("Set-Cookie").getValue();
			JsonParser parser = new JsonParser(); 
			JsonObject root = parser.parse(new InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8")).getAsJsonObject(); 
			System.out.println(root); 
		}catch(Exception ex) {
			System.out.println("Greska.");
			System.out.println(ex.getClass().getName()); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void preview() {
		System.out.println(sessionId); 
	}
	
	public static void menu() {
		System.out.println("IZBORNIK : \n"); 
		System.out.println("\t0. Izlaz"); 
		System.out.println("\t1. Pregled");
		System.out.println("\t2. Prijava");
		System.out.println("\t3. Odjava");
		System.out.println("\t4. Opsta odjava");
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
				default: 
					System.out.println("POGRESAN IZBOR."); 
			}
			System.out.println(); 
		}
		logout(); 
		System.out.println("POZDRAV."); 
	}
}
