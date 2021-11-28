package Couches;

import java.util.List;

import pobj.tools.Tools;

public class DnsQuestion implements ICouches{
	private String qName; 
	private String qType;
	private String qClass;
	private int length;
	public DnsQuestion (List<String> trame) {
		getChamp(trame);
	}
	private void getChamp(List<String> trame) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while(Tools.convertHextoDec(trame.get(i)) != 0 && i < trame.size()) {
			sb.append(trame.get(i));
			i++;
		}
		sb.append(trame.get(i++));
		qName = sb.toString();
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
		
		sb.append("Name : " + Tools.hexToASCII(qName)+ "\n\t\t");
		sb.append("[Name Length : " + qName.length()+ "]\n\t\t");
		sb.append("[Label count : 3 ?] \n\t\t" ); // a voir si c'est 3 ou non 
		int type = Tools.convertHextoDec(qType);
		sb.append("Type : " + Dns.typeAnalyse(type)+ "(" +type+ ")\n\t\t" );
		sb.append("Class : (0x" + qClass + ")" +Dns.classAnalyse(Tools.convertHextoDec(qClass)) +"\n\t\t" );
		return sb.toString();
	}
	
	
}
