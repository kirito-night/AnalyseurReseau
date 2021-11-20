package Couches;
import java.util.*;

import pobj.tools.Tools;

import static org.junit.Assert.*;
public class Trame {
	private Integer numTrame;
	private List<String> trame;
	private List<ICouches> couches;
	
	
	public Trame(Integer numTrame, List<String> trame)  {
		this.numTrame = numTrame;
		this.trame = trame;
		
		couches = new ArrayList<>();
		
		try {
			if(analyseCouche() == null) {
				throw new Exception("format incomprehensible non traite dans notre programme");
			}
		}catch (Exception e) {
			e.getStackTrace();
			// TODO: handle exception
		}
	}
	
	public Integer getNumTrame() {
		return numTrame;
	}

	public void setNumTrame(Integer numTrame) {
		this.numTrame = numTrame;
	}

	public List<String> getTrame() {
		return trame;
	}

	public void setTrame(List<String> trame) {
		this.trame = trame;
	}

	public List<ICouches> getCouches() {
		return couches;
	}

	public void setCouches(List<ICouches> couches) {
		this.couches = couches;
	}

	public static List<Trame> generateListTrame(Map<Integer, List<String>> mapTrame){
		List<Trame> res = new ArrayList<>();
		for(Map.Entry<Integer, List<String>> entry : mapTrame.entrySet()) {
			Trame currentTrame = new Trame(entry.getKey(), entry.getValue());
			res.add(currentTrame);
		}
		
		return res;
	}
	
	public ICouches analyseEthernet(List<String> trame) {
		Ethernet ether = new Ethernet(trame);
		
		return ether;
		
	}
	
	public ICouches analyseIP(List<String> trame) {
		
		
		Ip ip = new Ip(trame);
		return ip;
	}
	
	public String  analyseCouche() {
		Ethernet e  =  (Ethernet)analyseEthernet(this.trame);
		couches.add(e);
		if(Tools.convertHextoDec(e.getType()) == Tools.convertHextoDec("0800")) {
			Ip  ip = (Ip) analyseIP(e.getData());
			couches.add(ip);
			String proto = ip.getProtocol();
			switch(Tools.convertHextoDec(proto)) {
				case 17 :
					//UDP
					break;
				case 1 :
					//ICMP
					break;
				case 6 :
					//TCP
					break;
			}
		}
		
		
		if(Tools.convertHextoDec(e.getType()) == Tools.convertHextoDec("0806")) {
			//analyse ARP non obligatoire 
		}
		
		
		
		return null;
	}
	
	public String[] resultattAnalyse() {
		String [] result = new String[this.couches.size()];
		int i = 0;
		for(ICouches c :couches) {
			c.analyse();
			result[i++] = c.analyse();
		}
		
		return result;
		
	}
	
	
	public static String trameToString (Trame trame) {
		List<String> list = trame.getTrame();
		
		return String.join(" ", list);
	}
	
	
}
