package Couches;

import java.util.List;

import pobj.tools.Tools;

public class Dhcp implements ICouches {
	private Udp udp;
	private String opcode;
	private String  hardwareType;
	private String hardwareAdressLength;
	private String hops;
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
	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Dnymamic Host Configuration : \n\t");
		sb.append("Messafe type : ");
		if(Tools.convertHextoDec(opcode)==Tools.convertHextoDec("01")) {
			sb.append("Boot Request (1)\n\t");
		}
		if(Tools.convertHextoDec(opcode)==Tools.convertHextoDec("02")) {
			sb.append("Boot Reply (2)\n\t");
		}
		sb.append("Hardware type : ");
		switch(Tools.convertHextoDec(hardwareType)) {
		case 1:
			sb.append("Ethernet (0x");
			break;
			
		case 6:
			sb.append("IEEE 802 Networks (0x");
			break;
			
		case 7 : 
			sb.append("ARCNET (0x");
			break;
			
		case 11 :
			sb.append("LocalTalk (0x");
			break;
			
		case 12:
			sb.append("LocalNet (0x");
			break;
			
			
		case 14 :
			sb.append("SMDS (0x");
			break;
			
		case 15:
			sb.append("Frame Relay (0x");
			break;
			
		case 16 :
			sb.append("Asynchronous Transfer Mode (0x");
			break;
			
		case 17 :sb.append("HDLC (0x");
			break;
			
		case 18:sb.append("Fibre Channel (0x");
			break;
			
		case 19 :
			sb.append("Asynchronous Transfer Mode (0x");
			break;
			
	    case 20 :
	    	sb.append("Serial Line (0x");
			break;
			
		default:
			sb.append("unable to analyse option type");
	}
		sb.append(hardwareType+")\n\t");
		sb.append("Hardware address length : "+Tools.convertHextoDec(hardwareAdressLength)+"\n\t");
		sb.append("Hops : "+Tools.convertHextoDec(hops)+"\n\t");
		sb.append("Transaction ID : 0x"+xid+"\n\t");
		sb.append("Seconds elapsed : "+Tools.convertHextoDec(sec)+"\n\t");
		sb.append("Bootp flags : 0x"+flags);
		String binFlags = Tools.convertHextoBin(flags);
		String b = binFlags.substring(0, 1);
		if(Tools.convertBintoDec(b) == 0 ) {
			sb.append("(Unicast)\n\t");
		}
		if(Tools.convertBintoDec(b) == 1) {
			sb.append("(Broadcast)\n\t");
		}
		sb.append("Client IP address : "+xid+"\n\t");
		return sb.toString();
	}
	
	
}
