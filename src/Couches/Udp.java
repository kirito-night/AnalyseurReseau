package Couches;

import java.util.List;

import pobj.tools.Tools;

public class Udp implements ICouches{
	private String srcPort;
	private String destPort;
	private String length;
	private String checksum;
	private List<String> enteteUDP;
	private List<String> data;
	
	public Udp(List<String> trame ){
		getChamp(trame);
	}
	
	public void getChamp(List<String> trame) {
		int i = 0;
		enteteUDP = trame.subList(0, 8);
		data = trame.subList(8, trame.size());
		
		srcPort = enteteUDP.get(i++) +enteteUDP.get(i++);
		destPort = enteteUDP.get(i++) +enteteUDP.get(i++);
		length = enteteUDP.get(i++) +enteteUDP.get(i++);
		checksum = enteteUDP.get(i++) +enteteUDP.get(i++);
	}
	
	
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	public List<String> getEnteteUDP() {
		return enteteUDP;
	}

	public void setEnteteUDP(List<String> enteteUDP) {
		this.enteteUDP = enteteUDP;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("User Datagram Protocol : \n\t");
		sb.append("Source Port : "+Tools.convertHextoDec(srcPort)+"\n\t");
		sb.append("Destination Port : "+Tools.convertHextoDec(destPort)+"\n\t");
		sb.append("Length : "+Tools.convertHextoDec(length)+"\n\t");
		sb.append("Checksum : 0x"+checksum +"\n");
		return sb.toString();
	}
	
	
}
