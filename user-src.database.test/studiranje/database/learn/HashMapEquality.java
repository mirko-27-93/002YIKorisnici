package studiranje.database.learn;

import java.util.HashMap;

/**
 * Провјера једначења две (хеш) мапе. 
 * @author mirko
 * @version 1.0
 */
public class HashMapEquality {
	public static void main(String ... args) {
		HashMap<String, String> hm1 = new HashMap<String, String>();  
		HashMap<String, String> hm2 = new HashMap<String, String>();
		hm1.put("A", "C"); 
		hm1.put("D", "C"); 
		hm1.put("C", "D"); 
		hm2.put("C", "D");
		hm2.put("A", "C"); 
		hm2.put("D", "C"); 
		System.out.println(hm1.equals(hm2));
	}
}
