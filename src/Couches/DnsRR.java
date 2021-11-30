package Couches;

import java.util.List;

import pobj.tools.Tools;

public class DnsRR implements ICouches {
	String name; 
	String type;
	String rClass;
	String ttl;
	String rdLength;
	String rdata;
	List<String> enteteDns;
	
	String ptrName;
	
	private int length;
	
	public DnsRR(List<String> trame, List<String> enteteDns) throws Exception {
		this.enteteDns = enteteDns;
		int i = 0;
		if(Tools.convertHextoDec(trame.get(i)) ==0 ) {
			name = trame.get(i++);
		}else {
			name = trame.get(i++) + trame.get(i++);
		}
		
		
		type = trame.get(i++) + trame.get(i++);
		rClass = trame.get(i++) + trame.get(i++);
		ttl = trame.get(i++) + trame.get(i++) + trame.get(i++) + trame.get(i++);
		rdLength = trame.get(i++) + trame.get(i++);
		
		int len = Tools.convertHextoDec(rdLength);
		len = i + len ;
		
		if(trame.size() < len) {
			throw new Exception(trame.size() + " < " +len + "dns option data size ");
		}
		rdata =String.join("", trame.subList(i, len));
		
		length = len;
		
		ptrName = findName();
	}

	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Answers  \n\t\t");
		
		if(Tools.convertHextoDec(name) ==0 ) {
			sb.append("Name : " + "<Root>"+ "\n\t\t");
		}else {
			sb.append("Name : " + ptrName+ "\n\t\t");
		}
	
		
		int atype = Tools.convertHextoDec(type);
		
		sb.append("Type : " + Dns.typeAnalyse(atype)+ "(" +atype+ ")\n\t\t" );
		sb.append("Class : (0x" + rClass + ")" +Dns.classAnalyse(Tools.convertHextoDec(rClass)) +"\n\t\t" );
		sb.append("TTL : "+Tools.convertHextoDec(ttl) +"s\n\t\t" );
		sb.append("Data Length : " + rdata.length());
		
		
		
		
		return sb.toString();
	}
	
	
	public String findName() {
		String binName = Tools.convertHextoBin(name);
		String offset  =binName.substring(2,binName.length());
		int ptr = Tools.convertBintoDec(offset);
		return Dns.domainNameRead(enteteDns.subList(ptr, enteteDns.size()));
		
		
		
		
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getrClass() {
		return rClass;
	}

	public void setrClass(String rClass) {
		this.rClass = rClass;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public String getRdLength() {
		return rdLength;
	}

	public void setRdLength(String rdLength) {
		this.rdLength = rdLength;
	}

	public String getRdata() {
		return rdata;
	}

	public void setRdata(String rdata) {
		this.rdata = rdata;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
