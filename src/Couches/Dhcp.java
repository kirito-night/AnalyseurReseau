package Couches;

import java.util.List;

import pobj.tools.Tools;

public class Dhcp implements ICouches {
	private Udp udp;
	private String opcode;
	private String  hardwareType;
	private String hardwareAdressLength;
	private String Hops;
	private String xid;
	private String sec;
	private String flags;
	private String ciaddr; // client ip adress
	private String yiaddr; // your ip adress
	private String siaddr; //server Ip adress
	private String giaddr; //gateway Ip adress
	
	private String chaddr; // client hardware addresses 16 octet
	private String serverName ; //serverName 64 octet
	private String bootFileName; // 128 octet; 
	private String MagicCookie;  // un octet;
	private List<String> options; 
	
	private List<String> enteteDHCP ; 
	
	private List<String> data;
	
	public Dhcp(List<String> trame, Udp udp) {
		this.udp = udp;
		getChamp(trame);
	}
	
	public Udp getUdp() {
		return udp;
	}

	public void setUdp(Udp udp) {
		this.udp = udp;
	}

	public void getChamp(List<String> trame) {
		enteteDHCP = trame.subList(0, Tools.convertHextoDec(udp.getLength()));
		data =trame.subList(Tools.convertHextoDec(udp.getLength()), trame.size() );
		
		int i = 0;
		
	}
	
	
}
