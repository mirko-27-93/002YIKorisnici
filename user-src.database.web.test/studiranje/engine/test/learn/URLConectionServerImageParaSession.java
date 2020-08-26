package studiranje.engine.test.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/**
 * Тестирање конекција, за потребе слике о сесијама 
 * на бази података у зависности да ли су конекције у 
 * апликацијама, односно процесима, нитима, и слично. 
 * Гледајући од клијента. 
 * @author mirko
 * @version 1.0
 */
public class URLConectionServerImageParaSession {
	public static void test1(String ... args) throws IOException {
		System.out.println("ZDRAVO"); 
		URL url1 = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGIN");
		URL url2 = new URL("http://localhost:8085/003YIKorisniciODS/");
		URLConnection conn1 = url1.openConnection();
		URLConnection conn2 = url2.openConnection();
		conn1.setDoOutput(true);
		conn2.setDoOutput(true);
		HttpURLConnection hc1 = (HttpURLConnection) conn1; 
		HttpURLConnection hc2 = (HttpURLConnection) conn2; 
		hc1.setAllowUserInteraction(true);
		hc2.setAllowUserInteraction(true);
		System.out.println(hc1.getAllowUserInteraction()); 
		System.out.println(hc2.getAllowUserInteraction());
		hc1.connect();
		hc2.connect();
		hc1.getInputStream().close();
		hc2.getInputStream().close();
		hc1.disconnect();
		hc2.disconnect();
		new Thread(() -> {
			try {
				URLConnection conn = url2.openConnection();
				HttpURLConnection hc = (HttpURLConnection) conn2;
				conn.setDoOutput(true);
				conn.connect();
				conn.getInputStream().close();
				hc.disconnect();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}).start();
		InetAddress ip1 = InetAddress.getByName(url1.getHost()); 
		Socket socket1 = new Socket(ip1, url1.getPort());
		socket1.close();
		System.out.println("POZDRAV");
	}
	
	public static void test2(String ... args) throws IOException {
	    for(int i=0; i<5; i++) {    
	    	new Thread(()-> {
	    		try {
			    	System.out.println("A"); 
			    	String addressString = "localhost";
			        int port =8085;
			        int timeoutMs = 30000;
			        String temp; 
		
			        InetAddress addr = InetAddress.getByName(addressString);
			        SocketAddress sockaddr = new InetSocketAddress(addr, port);
			        Socket socket = new Socket();
		
			        socket.connect(sockaddr,timeoutMs);
			        
			        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		
			        pw.print("GET / HTTP/1.1\r\n");
			        pw.print("Host: "+ addressString +"\r\n\r\n");
			        while((temp=br.readLine())!=null){
			            System.out.println(temp);
		
			        }
			        pw.close();
			        br.close();
			        socket.close();
			        System.out.println("B");
	    		}catch(Exception ex) {
	    			ex.printStackTrace();
	    		}
	    }).start();
	   }
	}
	
	public static void main(String ... args) throws IOException, URISyntaxException {
		for(int i=0; i<5; i++) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			URL url = new URL("http://localhost:8085/003YIKorisniciODS/UserResolveServlet/LOGIN");
			HttpPost httpPost = new HttpPost(url.toString());
			httpPost.setHeader("User-Agent","Mozilla/5.0");
			System.out.println(httpPost.getUri()); 
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost); 
			String sessionID = httpResponse.getFirstHeader("Set-Cookie").getValue();
			System.out.println(sessionID); 
			CloseableHttpClient httpClient1 = HttpClients.createDefault();
			HttpPost httpPost1 = new HttpPost(url.toString());
			httpPost.setHeader("User-Agent","Mozilla/5.0");
			httpPost.addHeader("Cookie", sessionID);
			httpClient1.execute(httpPost1);
			httpClient1.execute(httpPost);
		}
	}
}
