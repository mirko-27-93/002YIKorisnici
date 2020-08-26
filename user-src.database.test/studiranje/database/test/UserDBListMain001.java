package studiranje.database.test;

import java.util.List;

import studiranje.ip.database.UserinfoDBList;

/**
 * Тестирање кандидатске листе база података за табелу userinfo. 
 * @author mirko
 * @version 1.0
 */
public class UserDBListMain001 {
	public static void main(String ... args) {
		List<String> dbCandidates = UserinfoDBList.getListOfCandidate();
		for(String dbCandidate: dbCandidates) {
			System.out.println(dbCandidate);
		}
	}
}
