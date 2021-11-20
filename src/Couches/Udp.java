package Couches;

import java.util.List;

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

	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	
}
