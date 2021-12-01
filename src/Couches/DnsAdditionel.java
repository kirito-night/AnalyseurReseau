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
		StringBuilder sb = new StringBuilder();
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
		
		
		
		
		return sb.toString();
	}
}
