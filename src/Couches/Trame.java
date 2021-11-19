package Couches;
import java.util.*;
import javafx.base.*;
import static org.junit.Assert.*;
public class Trame {
	private Integer numTrame;
	private List<String> trame;
	private List<ICouches> couches;
	private int pointeur;
	
	public Trame(Integer numTrame, List<String> trame) {
		this.numTrame = numTrame;
		this.trame = trame;
		this.pointeur = 0;
		couches = new ArrayList<>();
	}
	
	public static List<Trame> generateListTrame(Map<Integer, List<String>> mapTrame){
		List<Trame> res = new ArrayList<>();
		for(Map.Entry<Integer, List<String>> entry : mapTrame.entrySet()) {
			Trame currentTrame = new Trame(entry.getKey(), entry.getValue());
			res.add(currentTrame);
		}
		
		return res;
	}
	
	public ICouches analyseEthernet() {
		Ethernet ether = new Ethernet(trame);
		
		return ether;
		
	}
	
	public ICouches analyseIP() {
		try {
			if(pointeur != 14) {
				throw new Exception("pointeur pb");
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
