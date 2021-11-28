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
	
	private  List<DnsQuestion> questions;
	private List<DnsRR> answers;
	private List<DnsRR>  authority;
	private List<DnsRR> additionnal;
	
	
	private List<String> enteteDNS;
	private List<String> data;
	
	
	public Dns(Udp udp) {
		this.udp = udp;
		getchamp(udp.getData());
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
		
		for(int j = 0 ; j < Tools.convertHextoDec(numberOfQuestions) ; j++) {
			DnsQuestion q = new DnsQuestion(enteteDNS.subList(i, enteteDNS.size()));
			questions.add(q);
			i += q.getLength();
		}
		
		for(int k = 0 ; k < Tools.convertHextoDec(numbersOfAnswer) ; k++ ) {
			DnsRR asw = new DnsRR(enteteDNS.subList(i, enteteDNS.size()));
			answers.add(asw);
			i+= asw.getLength();
		}
		
		
		for(int k = 0 ; k < Tools.convertHextoDec(numbersOfAnswer) ; k++ ) {
			DnsRR auth = new DnsRR(enteteDNS.subList(i, enteteDNS.size()));
			answers.add(auth);
			i+= auth.getLength();
		}
		
		
		for(int k = 0 ; k < Tools.convertHextoDec(numbersOfAnswer) ; k++ ) {
			DnsRR addi = new DnsRR(enteteDNS.subList(i, enteteDNS.size()));
			answers.add(addi);
			i+= addi.getLength();
		}
		
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
	
	
	
	public static String typeAnalyse(int type) {
		String res; 
		switch (type) {
		case 1: {
			res = "A (Host Adress)";
			break;
		}
		case 2 : 
			res = "NS (Name Server Record)";
			break;
		
		case 5 : 
			res = "CNAME (Canonical Name Record)";
			break;
		case 6 : 
			res = "SOA (Start Of Authority Record)";
			break;
		case 12 :
			res = "PTR (Ressource Record)";
			break;
		case 15 : 
			res = "MX (Mail Exchance Record)";
			break;
		case 16 :
			res = "TXT (Text Record)";
		case 28 : 
			res = "AAA (Host Adress IPv6)";
			break;
		default:
			 res = "Type not treated";
		}
		
		return res;
	}
	
	public static String classAnalyse(int c) {
		String res ; 
		switch (c) {
		case 1:
			res = "Internet (IN)";
			break;
		case 3:
			res = "Chaos (CH)";
			break;
		case 4:
			res = "Hesiod (HS)";
		default:
			res = "unassiagned or reserved";
			break;
		}
		return res;
	}
	
}
