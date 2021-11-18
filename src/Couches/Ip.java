package Couches;

import java.util.List;

public class Ip implements ICouches {
	private String version;
	private String ihl;
	private String tos;
	private String totalLength;
	private String Identifer;
	private String r;
	private String df;
	private String mf;
	private String fragmentOffset;
	private String ttl;
	private String protocol;
	private String headerChecksum;
	private String srcIpAdress;
	private String destIpAdress;
	private String option;
	private String padding;
	
	private List<String> enteteIP ; 
	private List<String> data;
	
	public Ip(List<String> trame) {
		
		
	}
	public void getchamp(List<String> trame) {
		String tmp = trame.get(0);
		version = tmp.substring(0, 1);
	}
}
