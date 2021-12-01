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
			DnsAnswer asw = new DnsAnswer(enteteDNS.subList(i, enteteDNS.size()), enteteDNS);
			answers.add(asw);
			i+= asw.getLength();
		}
		
		
		
		for(int k = 0 ; k < Tools.convertHextoDec(numberOfAuthority) ; k++ ) {
			DnsAuthority auth = new DnsAuthority(enteteDNS.subList(i, enteteDNS.size()), enteteDNS);
			authority.add(auth);
			i+= auth.getLength();
		}
		
		/*
		for(int k = 0 ; k < Tools.convertHextoDec(numberOfAdditionnal) ; k++ ) {
			DnsAdditionel addi = new DnsAdditionel(enteteDNS.subList(i, enteteDNS.size()), enteteDNS);
			additionnal.add(addi);
			i+= addi.getLength();
		}*/
		
		
		
	}
	
	
	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Domain Name System : \n\t");
		sb.append("Transaction ID : 0x" + identification+ "\n\t");
		sb.append("Flags : 0x" + flags + "\n\t\t");
		sb.append(flagAnalyse());
		sb.append("Questions : " + Tools.convertHextoDec(numberOfQuestions) + "\n\t" );
		sb.append("AAnswer RRs : " + Tools.convertHextoDec(numbersOfAnswer) + "\n\t" );
		sb.append("Authority RRs : " + Tools.convertHextoDec(numberOfAuthority) + "\n\t" );
		sb.append("Additional RRs : " + Tools.convertHextoDec(numberOfAdditionnal) + "\n\t" );
		for(DnsQuestion q : questions) {
			sb.append(q.analyse());
		}
		for(DnsAnswer r : answers) {
			sb.append(r.analyse());
		}
		for(DnsAuthority a : authority) {
			sb.append(a.analyse());
		}

		
		return sb.toString();
	}
	private String flagAnalyse() {
		StringBuilder sb = new StringBuilder();
		String binFlag = Tools.convertHextoBin(flags);
		String qr = binFlag.substring(0, 1);
		String opCode = binFlag.substring(1,5);
		String aa = binFlag.substring(5,6);
		String tc = binFlag.substring(6,7);
		String rd = binFlag.substring(7,8);
		String ra = binFlag.substring(8,9);
		String z = binFlag.substring(9,10);
		String ad = binFlag.substring(10,11);
		String cf = binFlag.substring(11,12);
		String rCode = binFlag.substring(12,16);
		
		
		switch (Tools.convertHextoDec(qr)) {
		case 0 :
			sb.append(qr +"... .... .... .... = Response : message is a querie\n\t\t");
			break;
		case 1:
			sb.append(qr +"... .... .... .... = Response : message is a response\n\t\t");
			break;

		default:
			sb.append("problem of qr(flag) section\n\t\t");
			break;
		}
		
		switch (Tools.convertHextoDec(opCode)) {
		case 0 :
			sb.append(".000 0... .... .... = OpCode : Standard Querie(0)\n\t\t");
			break;
		case 1 :
			sb.append(".000 1... .... .... = OpCode :Inverse Querie(1)\n\t\t");
			break;
		case 2 : 
			sb.append(".001 0... .... .... = OpCode :Server Status Request(2)\n\t\t");
			break;
		default:
			sb.append("problem of opcode(flag) section\n\t\t");
			break;
		}
		
		switch (Tools.convertHextoDec(aa)) {
		case 0:
			sb.append(".... .0.. .... .... = Authorative : Server is not an authorative for domain \n\t\t");
			break;
		case 1:
			sb.append(".... .1.. .... .... = Authorative : Server is an authorative for domain \n\t\t");
			break;
		default:
			sb.append("problem of aa(flag) section\n\t\t");
			break;
		}
		
		switch (Tools.convertHextoDec(tc)) {
		case 0:
			sb.append(".... ..0. .... .... = Truncated : message is not truncated \n\t\t");
			break;
		case 1 :
			sb.append(".... ..1. .... .... = Truncated : message is truncated \n\t\t");
			break;
		default:
			sb.append("problem of tc(flag) section\n\t\t");
			break;
		}
		
		switch (Tools.convertHextoDec(rd)) {
		case 1:
			sb.append(".... ...1 .... .... = Recursion desired : do query recursively \n\t\t");
			break;
		case 0:
			sb.append(".... ...0 .... .... = Recursion not desired : don't do query recursively \n\t\t");
			break;
		default:
			sb.append("problem of rd(flag) section\n\t\t");
			break;
		}
		
		switch (Tools.convertHextoDec(ra)) {
		case 0:
			sb.append(".... .... 0... .... = Recursion Not Available : do query recursively \n\t\t");
			break;
		case 1 : 
			sb.append(".... .... 1... .... = Recursion Available :Server can do query recursively \n\t\t");
			break;
		default:
			sb.append("problem of ra(flag) section\n\t\t");
			break;
		}
		
		sb.append(".... .... .0.. .... = Z : reserved (0)");
		
		switch (Tools.convertHextoDec(ad)) {
		case 0:
			sb.append(".... .... ..0. .... = Non-Authenticated by the server  \n\t\t");
			break;
		case 1 : 
			sb.append(".... .... ..1. .... = Authenticated by the server \n\t\t");
			break;
		default:
			break;
		}
		
		switch (Tools.convertHextoDec(cf)) {
		case 0:
			sb.append(".... .... ...0 .... = Non-Authenticated data : Unacceptable  \n\t\t");
			break;
		case 1 :
			sb.append(".... .... ...1 .... = Authenticated data : Acceptable n\t\t");
			break;
		default:
			break;
		}
		
		switch (Tools.convertHextoDec(rCode)) {
		case 0:
			sb.append(".... .... .... 0000 = Reply error : No error(0) \n\t");
			break;

		default:
			sb.append(".... .... .... 0000 = Reply error :  Error("+ Tools.convertHextoDec(rCode)+") \n\t");
			break;
		}
		
		return sb.toString();
	}
	
	public static String domainNameRead(List<String> name) throws Exception {
		List<String> list = new ArrayList<>();
		int i = 0;
		while(Tools.convertHextoDec(name.get(i)) != 0 && i < name.size()) {
			int tmpLen = Tools.convertHextoDec(name.get(i++));
			tmpLen += i;
			/*
			try {
				if(tmpLen > name.size()) {
					throw new Exception("pb de taille de String : " + tmpLen + " > " + name.size() + "hexa : " + name.get(--i) + "\n trame : " + name.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}*/
			
			
			
			String str = String.join("", name.subList(i, tmpLen));
			String elem = Tools.hexToASCII(str);
			list.add(elem);
			i = tmpLen;
			
		}
		
		String res = String.join(".", list);
		
		return res ;
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
