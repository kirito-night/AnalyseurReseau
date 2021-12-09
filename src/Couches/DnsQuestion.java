package Couches;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import pobj.tools.Tools;

public class DnsQuestion implements ICouches{
	private String qName; 
	private String qType;
	private String qClass;
	private int length;
	List<String> enteteDns;
	public DnsQuestion (List<String> trame ,  List<String>	enteteDns) throws Exception {
		this.enteteDns = 	enteteDns;
		getChamp(trame);
	}
	private void getChamp(List<String> trame) throws Exception {
		//StringBuilder sb = new StringBuilder();
		
		int i = 0;
		/*while(Tools.convertHextoDec(trame.get(i)) != 0 && i < trame.size()) {
			sb.append(trame.get(i));
			i++;
		}*/
		//sb.append(trame.get(i++));
		//qName = sb.toString();
		qName = this.findName(trame);
		
		while(Tools.convertHextoDec(trame.get(i)) != 0 && i < trame.size()) {
			i++;
		}
		assertEquals(0, Tools.convertHextoDec(trame.get(i)));
		i++;
		
		qType = trame.get(i++) + trame.get(i++);
		qClass =  trame.get(i++) + trame.get(i++);
		length = i;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Queries \n\t\t");
		
		sb.append("Name : " +qName+ "\n\t\t");
		sb.append("[Name Length : " + qName.length()+ "]\n\t\t");
		
		
		

		sb.append("[Label count : "+ qName.split("\\.").length +"] \n\t\t" ); // a voir si c'est 3 ou non 
		int type = Tools.convertHextoDec(qType);
		
		sb.append("Type : " + Dns.typeAnalyse(type)+ "(" +type+ ")\n\t\t" );
		sb.append("Class : (0x" + qClass + ")" +Dns.classAnalyse(Tools.convertHextoDec(qClass)) +"\n\t" );
		
		return sb.toString();
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
	
	
}
