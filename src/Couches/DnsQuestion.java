package Couches;

import static org.junit.Assert.assertEquals;

import java.util.List;

import pobj.tools.Tools;

public class DnsQuestion implements ICouches{
	private String qName; 
	private String qType;
	private String qClass;
	private int length;
	public DnsQuestion (List<String> trame) throws Exception {
		getChamp(trame);
	}
	private void getChamp(List<String> trame) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		/*while(Tools.convertHextoDec(trame.get(i)) != 0 && i < trame.size()) {
			sb.append(trame.get(i));
			i++;
		}*/
		//sb.append(trame.get(i++));
		//qName = sb.toString();
		qName = Dns.domainNameRead(trame);
		
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
	
	
}
