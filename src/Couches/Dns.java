package Couches;

import static org.junit.Assert.assertEquals;

import java.util.List;

import pobj.tools.Tools;

public class Dns implements ICouches {
	private Udp udp;
	
	private String identification; //2 octet 
	private String flags; // 2 octet 
	private String  numberOfQuestions; 
	private String  numbersOfAnswer;
	private String numberOfAuthority;
	private String numberOfAdditionnal;
	
	private  List<String> questions;
	private List<String> answers;
	private List<String>  authority;
	private List<String> additionnal;
	
	
	private List<String> enteteDNS;
	private List<String> data;
	
	
	public Dns(List<String> trame , Udp udp) {
		this.udp = udp;
		getchamp(trame);
	}
	public void getchamp(List<String> trame) {
		// TODO Auto-generated method stub
		enteteDNS = trame.subList(0, Tools.convertHextoDec(udp.getLength()));
		data = trame.subList(Tools.convertHextoDec(udp.getLength()), trame.size());
		int i =0; 
		identification = enteteDNS.get(i++) + enteteDNS.get(i++); 
		flags = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numberOfQuestions = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numbersOfAnswer = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numberOfAuthority = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numberOfAdditionnal = enteteDNS.get(i++) + enteteDNS.get(i++); 
		assertEquals(12, i);
		
	}
	
	
	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Domain Name System : \n\t");
		sb.append("Transaction ID : " + identification+ "\n\t");
		sb.append("Flags : 0x" + flags + "\n\t");
		sb.append("Questions : " + Tools.convertHextoDec(numberOfQuestions) + "\n\t" );
		sb.append("AAnswer RRs : " + Tools.convertHextoDec(numbersOfAnswer) + "\n\t" );
		sb.append("Authority RRs : " + Tools.convertHextoDec(numberOfAuthority) + "\n\t" );
		sb.append("Additional RRs : " + Tools.convertHextoDec(numberOfAdditionnal) + "\n\t" );
		sb.append("Queries\n\t" );
		
		return null;
	}
}
