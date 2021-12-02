package Couches;

import java.util.List;

import pobj.tools.Tools;

public class DnsAdditionel extends DnsRR {

	public DnsAdditionel(List<String> trame,List<String> enteteDns) throws Exception {
		super(trame, enteteDns);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		/*StringBuilder sb = new StringBuilder();
		sb.append("Additionnel  \n\t\t");
		
		if(Tools.convertHextoDec(name) ==0 ) {
			sb.append("Name : " + "<Root>"+ "\n\t\t");
		}else {
			sb.append("Name : " + ptrName + "\n\t\t");
		}
	
		
		int atype = Tools.convertHextoDec(type);
		
		sb.append("Type : " + Dns.typeAnalyse(atype)+ "(" +atype+ ")\n\t\t" );
		sb.append("Class : (0x" + rClass + ")" +Dns.classAnalyse(Tools.convertHextoDec(rClass)) +"\n\t\t" );
		sb.append("TTL : "+Tools.convertHextoDec(ttl) +"s\n\t\t" );
		sb.append("Data Length : " + rdata.length()+"\n\t");
		
		
		
		
		return sb.toString();*/
		if(Tools.convertHextoDec(type) == 41) {
			StringBuilder sb = new StringBuilder();
			
			
			if(Tools.convertHextoDec(name) ==0 ) {
				sb.append("Name : " + "<Root>"+ "\n\t\t");
			}else {
				sb.append("Name : " + ptrName+ "\n\t\t");
			}
		
			
			int atype = Tools.convertHextoDec(type);
			
			sb.append("Type : " + Dns.typeAnalyse(atype)+ "(" +atype+ ")\n\t\t" );
			sb.append("UDP payload : (0x" + rClass + ")" +Tools.convertHextoDec(rClass) +"\n\t\t" );
			sb.append("Higher bits in extended RCODE : 0x"+ ttl.substring(0, 2) +"\n\t\t" );
			sb.append("EDNS0 version : "+ Tools.convertHextoDec(ttl.substring(2, 4)) +"\n\t\t" );
			sb.append("Z : 0x" + rdLength + "\n\t\t" );
			sb.append("Data Length : " + rdata.size() + "\n\t\t" );
			
			return"Additional\n\t\t"+ sb.toString();
		}
		
		return "Additional\n\t\t" + super.analyse();
	}
}
