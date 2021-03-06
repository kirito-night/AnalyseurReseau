package Couches;

import java.util.ArrayList;
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
			ptrName = findName(trame);
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
				/*int ptrCname = 0;
				while (ptrCname  < rdata.size()) {
					sb.append("Cannonical Name : " + this.findName(rdata)+"\n\t");
				}*/
				
				sb.append("Cannonical Name : " + this.findName(rdata)+"\n\t");
				break;
			case 4  :
				//mail Exchange
				int pref = Tools.convertHextoDec(rdata.get(0)+rdata.get(1));
				sb.append(" Mail Exchange , Preferences : "+ pref+" Exchange :" +this.findName(rdata.subList(2, rdata.size()))+"\n\t");
				break;
			case 5 : 
				//authoritative name server
				sb.append("Authoritative Name Server : " + this.findName(rdata)+"\n\t");
				break;
			case 6 : 
				int i = 0;
				
				
				String pNameServer = findName(rdata);
				
				while(Tools.convertHextoDec(rdata.get(i)) != 0 && Tools.convertBintoDec( Tools.convertHextoBin(rdata.get(i)).substring(0, 2)) != Tools.convertBintoDec("11")  && i < rdata.size()) {
					i++;
				}
				
				
				if(Tools.convertHextoDec(rdata.get(i) ) == 0) {
					i++;
				}else {
					i+=2;
				}
				
				if(i>= rdata.size()) {
					sb.append("Error : No '00' byte separating Primary Name server and Responsible authority mail box \n\t");
					break;
				}
				
				
				String aMailBox = findName(rdata);
				while(Tools.convertHextoDec(rdata.get(i)) != 0 && Tools.convertBintoDec( Tools.convertHextoBin(rdata.get(i)).substring(0, 2)) != Tools.convertBintoDec("11")  && i < rdata.size()) {
					i++;
				}
				
				
				if(Tools.convertHextoDec(rdata.get(i) ) == 0) {
					i++;
				}else {
					i+=2;
				}
				if(i>= rdata.size()) {
					sb.append("Error : No '00' byte separating Primary Name server and Responsible authority mail box \n\t");
					break;
				}
				try {
					String serial = rdata.get(i++) + rdata.get(i++)+  rdata.get(i++) + rdata.get(i++);
					String refresh = rdata.get(i++) + rdata.get(i++)+  rdata.get(i++) + rdata.get(i++);
					String retry = rdata.get(i++) + rdata.get(i++)+  rdata.get(i++) + rdata.get(i++);
					String expire = rdata.get(i++) + rdata.get(i++)+  rdata.get(i++) + rdata.get(i++);
					String minimum = rdata.get(i++) + rdata.get(i++)+  rdata.get(i++) + rdata.get(i++);
					
					
					sb.append("Primary name server :"+pNameServer +  "\n\t\t");
					
					sb.append("Responsible authority's mailbox  :"+aMailBox +  "\n\t\t");
					sb.append("Serial Number  :"+Tools.convertHextoDec(serial) +  "\n\t\t");
					sb.append("Refresh Interval  :"+Tools.convertHextoDec(refresh) +  "\n\t\t");
					sb.append("Retry Interval :"+Tools.convertHextoDec(retry) +  "\n\t\t");
					sb.append("Expire limit  :"+Tools.convertHextoDec(expire) +  "\n\t\t");
					sb.append("Minimum TTL   :"+Tools.convertHextoDec(minimum) +  "s\n\t");
				}catch (Exception e) {
					// TODO: handle exception
					sb.append("problem of reading SOA \n\t");
				}
				
				
				break;
			default:
				sb.append("rData :  format not treated (only treated for A , AAAA, NS , CNAME, MX  )\n\t");
				break;
			}
		}
		
		
		return sb.toString();
	}
	
	
	public String findName() throws Exception {
		
		String binName = Tools.convertHextoBin(name);
		
		String offset  =binName.substring(2,binName.length());
		int ptr = Tools.convertBintoDec(offset);
		return this.findName(enteteDns.subList(ptr, enteteDns.size()));
		
		
		
		
	}
	
	public String findName(List<String> name) {
		
		
		
		List<String> list = new ArrayList<>();
		int i = 0;
		while(Tools.convertHextoDec(name.get(i)) != 0 && i < name.size()) {
			String tmp = name.get(i);
			String binTmp = Tools.convertHextoBin(tmp);
			String pref = binTmp.substring(0,2);
			if(Tools.convertBintoDec(pref) == 3 ) {
				i++;
				tmp +=name.get(i);
				binTmp = Tools.convertHextoBin(tmp);
				String off =  binTmp.substring(2,binTmp.length()) ;
				int ptr = Tools.convertBintoDec(off);
				String pointedName = this.findName(enteteDns.subList(ptr, enteteDns.size()));
				list.add(pointedName);
				break;
			}
			
			int tmpLen = Tools.convertHextoDec(name.get(i++));
			
			
			tmpLen += i;
			
			
			if(tmpLen > name.size()) {
				return "problem of domainn name read";
			}
			
			
			
			
			String str = String.join("", name.subList(i, tmpLen));
			String elem = Tools.hexToASCII(str);
			list.add(elem);
			i = tmpLen;
			
		}
		
		String res = String.join(".", list);
		
		return res ;
		
		
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
