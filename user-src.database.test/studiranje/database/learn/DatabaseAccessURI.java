package studiranje.database.learn;

import java.net.URI;

/**
 * Манервисање са приступним URI када је у питању база података. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseAccessURI {
	public static void main(String ... args) {
		URI uri = URI.create("http://localhost:3306");
		System.out.println(uri.getScheme());
		System.out.println(uri.getHost());
		System.out.println(uri.getPort());
		URI fullURI = URI.create(uri.getScheme()+"://root:password@"+uri.getHost()+":"+uri.getPort()+"/database"); 
		System.out.println(fullURI);
		System.out.println(fullURI.getUserInfo());
		System.out.println(fullURI.getPath()); 
	}
}
