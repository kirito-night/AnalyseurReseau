package Couches;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pobj.tools.Tools;
public class Trame {
	private Integer numTrame;
	private List<String> trame;
	private List<ICouches> couches;
	
	private String src = "untreated";
	private String dest = "untreated"; 
	private int  length;
	private String protocol = "untreated";
	
	
	public Trame(Integer numTrame, List<String> trame) throws Exception {
		this.numTrame = numTrame;
		this.trame = trame;
		
		couches = new ArrayList<>();
		//analyseCouche();
		
		length = this.trame.size();
		
		try {
			analyseCouche();
		}catch (Exception e) {
			System.out.println(e.getStackTrace());
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

	public static List<Trame> generateListTrame(Map<Integer, List<String>> mapTrame) throws Exception  {
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
	
	public ICouches analyseIP(List<String> trame) throws Exception {
		
		
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
	
	public ICouches analyseDNS (Udp udp) throws Exception {
		Dns dns = new Dns(udp);
		return dns ;
	}
	
	public void  analyseCouche() throws Exception  {
		Ethernet e  =  (Ethernet)analyseEthernet(this.trame);
		couches.add(e);
		if(Tools.convertHextoDec(e.getType()) == Tools.convertHextoDec("0800")) {
			Ip  ip = (Ip) analyseIP(e.getData());
			couches.add(ip);
			this.src = ip.getSrcIpAdress();
			this.dest = ip.getDestIpAdress();
			String proto = ip.getProtocol();
			switch(Tools.convertHextoDec(proto)) {
				case 17 :
					//UDP
					Udp udp = (Udp) analyseUDP(ip.getData());
					couches.add(udp);
					this.protocol ="UDP";
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
						this.protocol = "DHCP";
						break;
					}
					case 2 : //dns
						Dns dns =(Dns) analyseDNS(udp);
						couches.add(dns);
						this.protocol = "DNS";
						break;
					default:
						
						throw new IllegalArgumentException("Unexpected value: " + choose);
					}
					
					
					
					
					break;
				case 1 :
					//ICMP
					this.protocol = "ICMP";
					break;
				case 6 :
					//TCP
					this.protocol = "TCP";
					break;
				default: 
					this.protocol = "unknown";
					throw new Exception("unable to analyse protocol");
			}
			return;
		}
		
		
		if(Tools.convertHextoDec(e.getType()) == Tools.convertHextoDec("0806")) {
			//analyse ARP non obligatoire 
			this.src = e.getSrcMac();
			this.dest = e.getDestMac();
			
			return;
		}
		
		
		//throw new Exception("unable to analyse ethernet type");
		return;
	}
	
	public String[] resultattAnalyse() {
		String [] result = new String[this.couches.size()+1];
		int i = 0;
		result[i++] = Trame.trameToString(this)+"\n\n";
		for(ICouches c :couches) {
			
			result[i++] = c.analyse()+ "\n";
		}
		
		return result;
		
	}
	
	
	public static String trameToString (Trame trame) {
		List<String> list = trame.getTrame();
		StringBuilder sb = new StringBuilder();
		int fromIndex = 0 ; 
		int toIndex = 16;
		String offset = "0000";
		while(toIndex <= list.size()) {
			sb.append(offset + "  ");
			sb.append(String.join(" ", list.subList(fromIndex, toIndex)));
			sb.append("\n");
			
			fromIndex = toIndex;
			toIndex+=16;
			int tmp = Tools.convertHextoDec(offset);
			tmp+= 16;
			offset = Tools.convertDectoHex(tmp);
			while(offset.length() < 4) {
				offset  = "0" + offset;
			}
			
			
		}
		
		//return String.join(" ", list);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Trame : " + numTrame + "  Source : "+ src +"  Destination : " + dest + "  Length : " + length + "  Protocol : " + protocol  ;
	}
	
	
	
}
