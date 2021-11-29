package Couches;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
	private List<DnsAnswer> answers;
	private List<DnsAuthority>  authority;
	private List<DnsAdditionel> additionnal;
	
	
	private List<String> enteteDNS;
	private List<String> data;
	
	
	public Dns(Udp udp) throws Exception {
		this.udp = udp;
		getchamp(udp.getData());
	}
	public void getchamp(List<String> trame) throws Exception {
		// TODO Auto-generated method stub
		/*if(enteteDNS.size() < Tools.convertHextoDec(udp.getLength()) - 8){
			throw new Exception("enteDNS " + enteteDNS.size() + " < " +  Tools.convertHextoDec(udp.getLength()));
		}*/
		
		enteteDNS = trame.subList(0, Tools.convertHextoDec(udp.getLength())- 8);
		data = trame.subList(Tools.convertHextoDec(udp.getLength())-8, trame.size());
		int i =0; 
		identification = enteteDNS.get(i++) + enteteDNS.get(i++); 
		flags = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numberOfQuestions = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numbersOfAnswer = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numberOfAuthority = enteteDNS.get(i++) + enteteDNS.get(i++); 
		numberOfAdditionnal = enteteDNS.get(i++) + enteteDNS.get(i++); 
		questions = new ArrayList<>();
		
		answers = new ArrayList<>();
		authority = new ArrayList<>();
		additionnal = new ArrayList<>();
		
		for(int j = 0 ; j < Tools.convertHextoDec(numberOfQuestions) ; j++) {
			DnsQuestion q = new DnsQuestion(enteteDNS.subList(i, enteteDNS.size()));
			questions.add(q);
			i += q.getLength();
		}
		
		int  nAnswer = Tools.convertHextoDec(numbersOfAnswer);
		for(int k = 0 ; k <  nAnswer; k++ ) {
			DnsAnswer asw = new DnsAnswer(enteteDNS.subList(i, enteteDNS.size()));
			answers.add(asw);
			i+= asw.getLength();
		}
		
		
		for(int k = 0 ; k < Tools.convertHextoDec(numberOfAuthority) ; k++ ) {
			DnsAuthority auth = new DnsAuthority(enteteDNS.subList(i, enteteDNS.size()));
			authority.add(auth);
			i+= auth.getLength();
		}
		
		
		for(int k = 0 ; k < Tools.convertHextoDec(numberOfAdditionnal) ; k++ ) {
			DnsAdditionel addi = new DnsAdditionel(enteteDNS.subList(i, enteteDNS.size()));
			additionnal.add(addi);
			i+= addi.getLength();
		}
		
		
		
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
		for(DnsQuestion q : questions) {
			sb.append(q.analyse());
		}
		for(DnsRR r : answers) {
			sb.append(r.analyse());
		}

		
		return sb.toString();
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
		case 41:
			res = "OPT";
		
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
	
	public static int rrData(int  oType , int oClass) {
		int res;
		
		switch(oClass) {
		case 1 : 
			switch (oType) {
			case 1: 
				//class IN et Type A 
				res = 1;
			break;
			case 28:
				//class IN type AAAA
				res = 2;
			
			default :
				res = 0; 
				break;
			}
				
			break;
		
		
		default:
			res = 0; 
			
		
		}
		return res;
	}
	
	
	
}
