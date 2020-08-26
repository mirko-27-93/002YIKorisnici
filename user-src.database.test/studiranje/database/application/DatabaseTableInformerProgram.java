package studiranje.database.application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.RootConnectionPool;
import studiranje.ip.database.model.DBRecordInfo;
import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;

/** 
 * Апликација (тест) за описивање табела. 
 */
public class DatabaseTableInformerProgram {
	private static RootConnectionPool connection =  RootConnectionPool.getConnectionPool("http://root:root@localhost:3306");
	private static DBRootDAO dao   = new DBRootDAO(connection);
	private static Scanner scanner = new Scanner(System.in);
	private static String buffer = ""; 
	
	public static void outputln() {
		System.out.println();
	}
	
	public static void output(String text) {
		System.out.print(text);
	}
	
	public static void outputln(String text) {
		System.out.println(text);
	}
	
	public static String input() {
		try {
			if(buffer.trim().length()>0) return buffer;
			return scanner.nextLine(); 
		}finally {
			buffer = ""; 
		}
	}
	
	public static String inputWord() {
		if(buffer.trim().length()==0) buffer=input(); 
		String[] parts = buffer.split("\\W",2); 
		if(parts.length==1) try { return buffer.trim(); } finally { buffer=""; } 
		try { return parts[1]; } finally { buffer = parts[2]; } 
	}
	
	public static int inputInt() {
		if(buffer.trim().length()==0) buffer=input(); 
		String[] parts = buffer.split("\\W",2); 
		if(parts.length==1) try { return Integer.parseInt(buffer.trim()); } finally { buffer=""; } 
		try {return Integer.parseInt(parts[1]); } finally { buffer = parts[2]; } 
	}
	
	public static String input(String text) {
		output(text); 
		try {
			if(buffer.trim().length()>0) return buffer;
			return scanner.nextLine(); 
		}finally {
			buffer = ""; 
		}
	}
	
	public static String inputWord(String text) {
		output(text); 
		if(buffer.trim().length()==0) buffer=input(); 
		String[] parts = buffer.split("\\W",2); 
		if(parts.length==1) try { return buffer.trim(); } finally { buffer=""; } 
		try { return parts[1]; } finally { buffer = parts[2]; } 
	}
	
	public static int inputInt(String text) {
		output(text); 
		if(buffer.trim().length()==0) buffer=input(); 
		String[] parts = buffer.split("\\W",2); 
		if(parts.length==1) try { return Integer.parseInt(buffer.trim()); } finally { buffer=""; } 
		try {return Integer.parseInt(parts[1]); } finally { buffer = parts[2]; } 
	}

	public static String inputln(String text) {
		outputln(text); 
		try {
			if(buffer.trim().length()>0) return buffer;
			return scanner.nextLine(); 
		}finally {
			buffer = ""; 
		}
	}
	
	public static String inputlnWord(String text) {
		outputln(text); 
		if(buffer.trim().length()==0) buffer=input(); 
		String[] parts = buffer.split("\\W",2); 
		if(parts.length==1) try { return buffer.trim(); } finally { buffer=""; } 
		try { return parts[1]; } finally { buffer = parts[2]; } 
	}
	
	public static int inputlnInt(String text) {
		outputln(text); 
		if(buffer.trim().length()==0) buffer=input(); 
		String[] parts = buffer.split("\\W",2); 
		if(parts.length==1) try { return Integer.parseInt(buffer.trim()); } finally { buffer=""; } 
		try {return Integer.parseInt(parts[1]); } finally { buffer = parts[2]; } 
	}
	
	public static void menu() {
		outputln(); 
		outputln("\tMENI : ");
		outputln("0. Izlaz");
		outputln("1. Lista baza podataka");
		outputln("2. Lista tabela");
		outputln("3. Opis tabele");
		outputln("4. Lista korisika");
	}
	
	public static int chosse() {
		try {
			output("\tIZBOR : ");
			return inputInt();  
		}catch(Exception ex) {
			return -1; 
		}
	}
	
	public static void main(String ... args) throws SQLException {
		outputln("INFORMACIJE O TABELAMA I KORISNICIMA BAZE PODATAKA"); 
		outputln("DOBRODOSLI !!!");
		while(true) {
			int izbor = -1; 
			menu();
			outputln();
			izbor = chosse();
			try {
				if(izbor==0) break; 
			}finally {
				outputln();
			}
			switch(izbor) {
				case 1: 
					try{previewDatabases();}
					catch(Exception ex) {System.out.println("Konekcija ne postoji");}
					break;
				case 2:
					try{previewTables();}
					catch(Exception ex) {System.out.println("Konekcija ili baza podataka ne postoji");}
					break;
				case 3:
					try{ previewTable();}
					catch(Exception ex) {System.out.println("Konekcija, baza podataka ili tabela ne postoji");}
					break;
				case 4:
					try{ previewUsers();}
					catch(Exception ex) {System.out.println("Korisnik ne postoji");}
					break;
				default:
					outputln("\tPOGRESAN IZBOR.");
			}
			
		}
		outputln("POZDRAV !!!"); 
	}
	
	public static void previewDatabases() throws SQLException {
		outputln("\tBAZE PODATAKA");
		outputln();
		List<String> databases = dao.getDatabases(); 
		for(String database: databases) 
			outputln(database.trim());
	}
	
	public static void previewTables() throws SQLException {
		outputln("\tTABELE");
		outputln();
		String database = input("Unesi naziv baze podataka : ");
		List<String> tables = dao.getTables(database); 
		outputln();
		for(String table: tables) 
			outputln(table.trim());
	}
	
	public static void previewTable() {
		outputln("\tTABELE");
		outputln();
		String database = input("Unesi naziv baze podataka: ");
		String tablename    = input("Unesi naziv tabele: "); 
		try {
			DBTableInfo table = dao.getTableInfo(database, tablename);
			if(table==null) throw new NullPointerException(); 
			for(DBRecordInfo record: table.getTableCoulumnsSchema().values()) {
				outputln();
				outputln(record.getClassicName()+":"+record.getClassic());
				outputln(record.getExtraName()+":"+record.getExtra());
				outputln(record.getFieldName()+":"+record.getField());
				outputln(record.getKeyName()+":"+record.getKey()); 
				outputln(record.getTypeName()+":"+record.getType());
			}
		}catch(Exception ex){
			outputln("Tabela ili baza podataka ne postoji."); 
		}
	}
	
	public static void previewUsers() throws SQLException {
		outputln("\tKORISNICI");
		outputln();
		List<DBUserData> users = dao.getUsers(); 
		for(DBUserData user: users) {
			outputln("User: "+user.getUserName());
			outputln("Host: "+user.getHostName());
			outputln("Password: "+user.getAuthenticationString());
			outputln(); 
		}
	}
}
