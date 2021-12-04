package Couches;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import pobj.tools.Tools;

public class Ip implements ICouches {

	/**
	 * Version est la version du IP
	 * ihl est le Header Length
	 * tos est le Type of service
	 * totalLength est le longueur total du IP (nombre de bits)
	 * 
	 */
	
	private String version;
	private String ihl;         
	private String tos;         
	private String totalLength;
	private String identifer;
	private String flags;
	private String r;
	private String df;
	private String mf;
	private String fragmentOffset;
	private String ttl;        //Time to live

	private String protocol;
	private String headerChecksum;
	private String srcIpAdress;
	private String destIpAdress;
	private String optionType;
	private String optionLength;
	private String optionPointer;
	private List<String> listeIPpasser;
	private String padding;
	
	private List<String> enteteIP ; 
	private List<String> data;
	
	public Ip(List<String> trame) throws Exception {
		
		getchamp(trame);
	}
	public void getchamp(List<String> trame) throws Exception {
		String tmp = trame.get(0);
		
		version = tmp.substring(0, 1);
		ihl = tmp.substring(1, 2);
		if(Tools.convertHextoDec(ihl) == 5) {
			enteteIP = trame.subList(0, 20);
			data = trame.subList(20, trame.size());
			partieFixe();
			return;
		}
		if(Tools.convertHextoDec(ihl) == 15) {
			
			enteteIP = trame.subList(0, 60);
			data = trame.subList(60, trame.size());
			partieFixe();
			optionIP();
			return; 
		}
		
		throw new Exception("problem of ihl, size not 5 or 15");
	}
	
	private void partieFixe() {
		int i = 1;
		tos = enteteIP.get(i++);
		this.totalLength = enteteIP.get(i++) + enteteIP.get(i++);
		this.identifer = enteteIP.get(i++) + enteteIP.get(i++);
		flags= enteteIP.get(i++) + enteteIP.get(i++);
		String binTmp = Tools.convertHextoBin(flags); 
		fragmentOffset = binTmp.substring(3);
		this.r = binTmp.substring(0, 1);
		this.df= binTmp.substring(1, 2);
		this.mf = binTmp.substring(2, 3);
		this.ttl = enteteIP.get(i++);
		this.protocol = enteteIP.get(i++);
		this.headerChecksum  = enteteIP.get(i++) + enteteIP.get(i++);
		this.srcIpAdress = Tools.convertHextoDec( enteteIP.get(i++)) + "." +Tools.convertHextoDec( enteteIP.get(i++))  + "." + Tools.convertHextoDec( enteteIP.get(i++)) + "." + Tools.convertHextoDec( enteteIP.get(i++)) ;
		this.destIpAdress =  Tools.convertHextoDec( enteteIP.get(i++)) + "." +Tools.convertHextoDec( enteteIP.get(i++))  + "." + Tools.convertHextoDec( enteteIP.get(i++)) + "." + Tools.convertHextoDec( enteteIP.get(i++)) ;
		
		assertEquals(i, 20);
		
		
		
	}
	
	private void optionIP(){
		int i = 20;
		optionType = enteteIP.get(i++);
		optionLength = enteteIP.get(i++);
		optionPointer = enteteIP.get(i++);
		int  length = Tools.convertHextoDec(optionLength);
		listeIPpasser = new ArrayList<>();
		while(i< 20 +length) {
			String ipPasser =  Tools.convertHextoDec( enteteIP.get(i++)) + "." +Tools.convertHextoDec( enteteIP.get(i++))  + "." + Tools.convertHextoDec( enteteIP.get(i++)) + "." + Tools.convertHextoDec( enteteIP.get(i++));
			listeIPpasser.add(ipPasser);
		}
		padding = enteteIP.get(i++);
		for(; i< 60 ; i++) {
			padding = padding +" "+ enteteIP.get(i);
		}
		assertEquals(i, 60);
	}
	
	public String getProtocol() {
		return protocol;
	}
	private void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String analyse() {
		StringBuilder sb = analysePartieFixe();
		if(enteteIP.size() == 60) {
			sb.append("type option : ");
				switch(Tools.convertHextoDec(optionType)) {
					case 0:
						sb.append("End of Options List (0)\n\t");
						//End of list
						break;
					case 1:
						sb.append("No Operation (1)\n\t");
						//no operation 
						break;
					case 7 :
						sb.append("Recourd Route (1)\n\t");
						//recourd route
						break;
					case 68 :
						sb.append("Time Stamp (68)\n\t");
						//Time Stamp
						break;
					case 131 :
						sb.append("Loose Routing (131)\n\t");
						//Loose Routing
						break;
					case 137 :
						sb.append("Strict Routing (137)\n\t");
						//Strict Routing
						break;
					default:
						sb.append("unable to analyse option type");
				}
				sb.append("Option Length : "+Tools.convertHextoDec(optionLength)+ "\n\t");
				sb.append("Option Pointeur : "+optionPointer+"\n\t");
				for(String s:listeIPpasser) {
					sb.append("\t"+s+"\n\t");
				}
				sb.append("Padding : "+padding+"\n");
			
		}
		return sb.toString();
	}
	private StringBuilder analysePartieFixe() {
		StringBuilder sb = new StringBuilder();
		sb.append("Internet Protocol Version 4 : \n\t");
		sb.append("Version : " + version + "\n\t");
		sb.append("Header Length : " + ihl + "\n\t");
		sb.append("Type of service : 0x" + tos+"\n\t");
		sb.append("Total Length : "+Tools.convertHextoDec(totalLength) + "\n\t");
		sb.append("Identification : 0x" + identifer + " ("+ Tools.convertHextoDec(identifer)+")\n\t");
		
		sb.append("Flags : 0x" + flags + "\n\t\t");
		sb.append("r : "+r+" Reserved\n\t\t");
		if(Tools.convertHextoDec(df)==1) {
			sb.append("df : "+df+" Don't fragment\n\t\t");
		}
		if(Tools.convertHextoDec(df)==0) {
			sb.append("df : "+df+" Could fragment\n\t\t");
		}
		if(Tools.convertHextoDec(mf)==1) {
			sb.append("mf : "+mf+" More fragment\n\t");
		}
		if(Tools.convertHextoDec(mf) ==0) {
			sb.append("mf : "+mf+" No More fragment\n\t");
		}
		sb.append("Fragment Offset :"+fragmentOffset + "\n\t");
		sb.append("Time to live : "+ Tools.convertHextoDec(ttl)+"\n\t");
		if(Tools.convertHextoDec(protocol)==1) {
			sb.append("Protocol : ICMP (1)\n\t");
		}
		if(Tools.convertHextoDec(protocol)==6) {
			sb.append("Protocol : TCP (6)\n\t");
		}
		if(Tools.convertHextoDec(protocol)==17) {
			sb.append("Protocol : UDP (17)\n\t");
		}
		sb.append("Header checksum : 0x" + headerChecksum+   " ("+ Tools.convertHextoDec(headerChecksum)+")\n\t");
		sb.append("Source : " + srcIpAdress+"\n\t");
		sb.append("Destination : "+ destIpAdress+"\n\t");
		return sb;
	}
	
	
	public String getSrcIpAdress() {
		return srcIpAdress;
	}
	public void setSrcIpAdress(String srcIpAdress) {
		this.srcIpAdress = srcIpAdress;
	}
	public String getDestIpAdress() {
		return destIpAdress;
	}
	public void setDestIpAdress(String destIpAdress) {
		this.destIpAdress = destIpAdress;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
}
