package Couches;

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
	private String r;
	private String df;
	private String mf;
	private String fragmentOffset;
	private String ttl;        //Time tio live
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
	
	public Ip(List<String> trame) {
		
		
	}
	public void getchamp(List<String> trame) {
		String tmp = trame.get(0);
		version = tmp.substring(0, 1);
		ihl = tmp.substring(1, 2);
		if(Tools.convertHextoDec(ihl) == 5) {
			enteteIP = trame.subList(0, 20);
			partieFixe();
		}
		if(Tools.convertHextoDec(tmp) == 15) {
			enteteIP = trame.subList(0, 60);
			partieFixe();
			optionIP();
		}
		
	}
	
	private void partieFixe() {
		int i = 1;
		tos = enteteIP.get(i++);
		this.totalLength = enteteIP.get(i++) + enteteIP.get(i++);
		this.identifer = enteteIP.get(i++) + enteteIP.get(i++);
		String tmp = enteteIP.get(i++) + enteteIP.get(i++);
		String binTmp = Tools.convertHextoBin(tmp); 
		fragmentOffset = binTmp.substring(3);
		this.r = binTmp.substring(0, 1);
		this.df= binTmp.substring(1, 2);
		this.mf = binTmp.substring(2, 3);
		this.ttl = enteteIP.get(i++);
		this.protocol = enteteIP.get(i++);
		this.headerChecksum  = enteteIP.get(i++) + enteteIP.get(i++);
		this.srcIpAdress = Tools.convertHextoDec( enteteIP.get(i++)) + "." +Tools.convertHextoDec( enteteIP.get(i++))  + "." + Tools.convertHextoDec( enteteIP.get(i++)) + "." + Tools.convertHextoDec( enteteIP.get(i++)) ;
		this.destIpAdress =  Tools.convertHextoDec( enteteIP.get(i++)) + "." +Tools.convertHextoDec( enteteIP.get(i++))  + "." + Tools.convertHextoDec( enteteIP.get(i++)) + "." + Tools.convertHextoDec( enteteIP.get(i++)) ;
		
		
		
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
	}
	
	public String analyse() {
		StringBuilder sb = new StringBuilder();
		sb.append("Internet Protocol Version 4 : \n\tVersion : ");
		sb.append(version + "\n\tHearder Length : ");
		sb.append(ihl + "\n\tTotal Length : ");
		sb.append(totalLength + "\n\tIdentification : ");
		sb.append(identifer + "\n\tFlags : ");
		
		if(df=="1") {
			sb.append("Don't fragment\n\t");
		}
		if(mf=="1") {
			sb.append("More fragment\n\t");
		}
		sb.append(fragmentOffset + "\n\tTime to live : ");
		sb.append(ttl+"\n\tProtocol : ");
		if(Tools.convertHextoDec(protocol)="6") {
			sb.append("TCP (6)");
		}
		
		sb.append()
	}
	
}
