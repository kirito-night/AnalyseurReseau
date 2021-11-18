package Couches;
import java.util.*;

//import static org.junit.Assert.*;
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
	
	public ICouches analyseEthernet() {
		int i ;
		String destMac = trame.get(pointeur);
		pointeur++;
		for(i = pointeur ; i < 6 ; i++) {
			destMac= destMac+":" + trame.get(i);
		}
		pointeur= i;
		
		String srcMac = trame.get(pointeur);
		
		for(i = pointeur ; i < 12 ; i++) {
			srcMac = srcMac+":" + trame.get(i);
		}
		pointeur= i;
		String type= trame.get(pointeur) + trame.get(++pointeur);
		pointeur++;
		Ethernet ether = new Ethernet(srcMac, destMac, type);
		
		return ether;
		
	}
}
