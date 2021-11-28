package Couches;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pobj.tools.Tools;
public class Trame {
	private Integer numTrame;
	private List<String> trame;
	private List<ICouches> couches;
	
	
	public Trame(Integer numTrame, List<String> trame) {
		this.numTrame = numTrame;
		this.trame = trame;
		
		couches = new ArrayList<>();
		//analyseCouche();
		
		try {
			analyseCouche();
		}catch (Exception e) {
			e.getMessage();
			System.out.println(e.getMessage());
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

	public static List<Trame> generateListTrame(Map<Integer, List<String>> mapTrame) {
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
	
	public ICouches analyseUDP(List<String> trame) {
		Udp udp = new Udp(trame);
		return udp;
	}
	public ICouches analyseDHCP( Udp udp) throws Exception {
		Dhcp dhcp = new Dhcp( udp);
		return dhcp;
	}
	
	public ICouches analyseDNS (Udp udp) {
		Dns dns = new Dns(udp)
	}
	
	public void  analyseCouche() throws Exception  {
		Ethernet e  =  (Ethernet)analyseEthernet(this.trame);
		couches.add(e);
		if(Tools.convertHextoDec(e.getType()) == Tools.convertHextoDec("0800")) {
			Ip  ip = (Ip) analyseIP(e.getData());
			couches.add(ip);
			String proto = ip.getProtocol();
			switch(Tools.convertHextoDec(proto)) {
				case 17 :
					//UDP
					Udp udp = (Udp) analyseUDP(ip.getData());
					couches.add(udp);
					
					String srcPort = udp.getSrcPort();
					String destPort = udp.getDestPort();
					int choose =0 ; 
					if(Tools.convertHextoDec(srcPort) == 67 || Tools.convertHextoDec(srcPort) == 68 ||Tools.convertHextoDec(destPort) == 67 ||  Tools.convertHextoDec(destPort)==68) {
						choose = 1;
					}
					if(Tools.convertHextoDec(srcPort) == 53 ||Tools.convertHextoDec(destPort) == 53  ) {
						choose = 2;
					}
					
					switch (choose) {
					case 1: {
						Dhcp dhcp = (Dhcp) analyseDHCP(udp);
						couches.add(dhcp);
						break;
					}
					case 2 : //dns
					
					default:
						throw new IllegalArgumentException("Unexpected value: " + choose);
					}
					
					
					
					
					break;
				case 1 :
					//ICMP
					break;
				case 6 :
					//TCP
					break;
				default: 
					throw new Exception("unable to analyse protocol");
			}
			return;
		}
		
		
		if(Tools.convertHextoDec(e.getType()) == Tools.convertHextoDec("0806")) {
			//analyse ARP non obligatoire 
			return;
		}
		
		
		throw new Exception("unable to analyse ethernet type");
		
	}
	
	public String[] resultattAnalyse() {
		String [] result = new String[this.couches.size()+1];
		int i = 0;
		result[i++] = this.trameToString(this);
		for(ICouches c :couches) {
			
			result[i++] = c.analyse()+ "\n";
		}
		
		return result;
		
	}
	
	
	public static String trameToString (Trame trame) {
		List<String> list = trame.getTrame();
		
		return String.join(" ", list);
	}
	
	
}
