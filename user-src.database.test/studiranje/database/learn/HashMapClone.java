package studiranje.database.learn;

import java.io.File;
import java.io.Serializable;

/**
 * Клонирање - максимално дубока копија. Хеш мапа са клонабилним елементима. 
 * @author mirko
 * @version 1.0
 */
public class HashMapClone {
	public static void main(String ... args) {
		SRZCloneable original =  new SRZCloneable(); 
		SRZCloneable copy = original.clone(); 
		original.setA(new File("/bin/ascript.sh/"));
		System.out.println(original);
		System.out.println(copy);
		System.out.println(original.getA());
		System.out.println(copy.getA());
	}
}

class SRZCloneable implements Serializable, Cloneable{
	private static final long serialVersionUID = 3556329539944563647L;
	
	private File a = new File("/bin/scripta.sh"); 
	
	public File getA() {
		return a;
	}
	public void setA(File a) {
		this.a = a;
	} 
	
	@Override 
	public SRZCloneable clone() {
		System.out.println(a.getPath()); 
		SRZCloneable clone = new SRZCloneable();
		clone.a = new File(a.getPath());
		return clone; 
	}
}
