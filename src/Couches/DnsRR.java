package Couches;

import java.util.List;

import pobj.tools.Tools;

public class DnsRR implements ICouches {
	String name; 
	String type;
	String rClass;
	String ttl;
	String rdLength;
	List<String> rdata;
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
		rdata =trame.subList(i, len);
		
		length = len;
		
		
		if(Tools.convertHextoDec(name) ==0 ) {
			ptrName = "<Root>";
		}else {
			ptrName = findName();
		}
		
	}

	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		
		if(Tools.convertHextoDec(name) ==0 ) {
			sb.append("Name : " + "<Root>"+ "\n\t\t");
		}else {
			sb.append("Name : " + ptrName+ "\n\t\t");
		}
	
		
		int atype = Tools.convertHextoDec(type);
		
		sb.append("Type : " + Dns.typeAnalyse(atype)+ "(" +atype+ ")\n\t\t" );
		sb.append("Class : (0x" + rClass + ")" +Dns.classAnalyse(Tools.convertHextoDec(rClass)) +"\n\t\t" );
		sb.append("TTL : "+Tools.convertHextoDec(ttl) +"s\n\t\t" );
		sb.append("Data Length : " + rdata.size() + "\n\t\t" );
		
		if(rdata.size() == 0){
			sb.append("\n\t");
		}else {
			int rdataFormat = Dns.rrData(Tools.convertHextoDec(type), Tools.convertHextoDec(rClass));
			switch (rdataFormat) {
			case 1:
				//class IN et TYPE A 
				if(rdata.size() == 4) {
					String addr = Tools.convertHextoDec(rdata.get(0))+ "."+ Tools.convertHextoDec(rdata.get(1))+ "." + Tools.convertHextoDec(rdata.get(2))+ "." +Tools.convertHextoDec(rdata.get(3));
					
					sb.append("Address : "+  addr + "\n\t");
				}else {
					sb.append("data length problem \n\t");
				}
				break;
			case 2 :
				///type AAAA
				if(rdata.size() == 16) {
					sb.append("AAAA Address : " + rdata.get(0)+rdata.get(1)+":"+rdata.get(2)+rdata.get(3)+"::"+rdata.get(12)+rdata.get(13)+":"+ rdata.get(14) + rdata.get(15) + "\n\t") ;
				}else {
					sb.append("data length problem \n\t");
				}
				break ;
			case 3:
				//Cname
				sb.append("Cannonical Name: " + Dns.domainNameRead(rdata)+"\n\t");
				break;
			case 4  :
				//mail Exchange
				int pref = Tools.convertHextoDec(rdata.get(0)+rdata.get(1));
				sb.append(" Mail Exchange , Preferences : "+ pref+" Exchange :" + Dns.domainNameRead(rdata.subList(2, rdata.size()))+"\n\t");
				break;
			case 5 : 
				//authoritative name server
				sb.append("Authoritative Name Server : " + Dns.domainNameRead(rdata)+"\n\t");
				break;
			//case 6 : 
				//SOA   start of a zone of authority
			//	break;
			default:
				sb.append("rData format not treated\n\t");
				break;
			}
		}
		
		
		return sb.toString();
	}
	
	
	public String findName() throws Exception {
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

	public List<String> getRdata() {
		return rdata;
	}

	public void setRdata(List<String> rdata) {
		this.rdata = rdata;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
