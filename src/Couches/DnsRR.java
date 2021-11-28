package Couches;

import java.util.List;

import pobj.tools.Tools;

public class DnsRR implements ICouches {
	private String name; 
	private String type;
	private String rClass;
	private String ttl;
	private String rdLength;
	private String rdata;
	
	
	
	private int length;
	
	public DnsRR(List<String> trame) {
		int i = 0;
		name = trame.get(i) + trame.get(i++);
		type = trame.get(i) + trame.get(i++);
		rClass = trame.get(i) + trame.get(i++);
		ttl = trame.get(i) + trame.get(i++) + trame.get(i) + trame.get(i++);
		rdLength = trame.get(i) + trame.get(i++);
		int len = Tools.convertHextoDec(rdLength);
		rdata =String.join("", trame.subList(i, len));
	}

	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
