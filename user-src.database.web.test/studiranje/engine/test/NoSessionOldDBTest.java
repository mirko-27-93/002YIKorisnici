package studiranje.engine.test;

import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Тестирање сесијских операција, када је у питању 
 * старат база података, преко сервиса.  
 * @author mirko
 * @version 1.0
 */
public class NoSessionOldDBTest {
	public static Scanner scanner = new Scanner(System.in); 
	
	public static void logout() {
		System.out.println("KLIJENT NE PODRZAVA HTTP I KORISNICKE SESIJE");
	}
	
	public static void login() {
		System.out.println("PRIJAVA KORISNIKA"); 
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine(); 
		System.out.print("Lozinka : ");
		String password = scanner.nextLine(); 
		try {
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGIN");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setRequestProperty("username", username);
			connection.setRequestProperty("password", password);
			connection.setRequestProperty("operation", "LOGIN");
			
			InputStream stream = connection.getInputStream();
			JsonParser parser = new JsonParser(); 
			JsonObject object = parser.parse(new InputStreamReader(stream, "UTF-8")).getAsJsonObject();
			stream.close();
			
			connection.disconnect();
			
			boolean success = object.get("success").getAsBoolean();
			String message = object.get("message").getAsString();
			
			if(!success) throw new RuntimeException(message); 
			
			System.out.println("Prijava uspjesna."); 
		}catch(Exception ex) {
			System.out.println("Greska pri prijavi."); 
			System.out.println(ex.getMessage()); 
		}
	}
	
	public static void preview() {
		System.out.println("SESSIJA NA PRIJAVNOJ STRANI: NIJE PODRZANA"); 
	}
	
	public static void menu() {
		System.out.println("IZBORNIK : \n"); 
		System.out.println("\t0. Izlaz"); 
		System.out.println("\t1. Pregled");
		System.out.println("\t2. Prijava");
		System.out.println("\t3. Odjava");
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
				default: 
					System.out.println("POGRESAN IZBOR."); 
			}
			System.out.println(); 
		}
		logout(); 
		System.out.println("POZDRAV."); 
	}
}
