package Couches;

import java.util.List;

import javax.tools.Tool;

import pobj.tools.Tools;

public class OptionDHCP implements ICouches{
	private String tag;
	private String length;
	private List<String> option;
	
	public OptionDHCP(String tag, String length, List<String> option ) {
		this.tag = tag;
		this.length = length;
		this.option = option;
	}
	
	public OptionDHCP(String tag) {
		this.tag = tag;
		
	}

	@Override
	public String analyse() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		switch(Tools.convertHextoDec(tag)) {
			case 0 :
				break;
				
			case 1 :
				sb.append("Option : (1) Subnet Mask\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String sad = String.join(".", option);
				sb.append("Subnet Mask : " + sad + "\n\t");
				break;
			case 12 : 
				sb.append("Option : (12)  Host Name Option \n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String hName = String.join(" ",option);
				sb.append("Host Name Option : " + Tools.hexToASCII(hName)+"\n\t");
				break;
				
			case 19 :
				sb.append("Option : (19) IP Forwarding Enable/Disable Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				if(Tools.convertHextoDec(option.get(0))==1) {
					sb.append("Enable IP forwarding\n\t");
				}
				if(Tools.convertHextoDec(option.get(0))==0) {
					sb.append("Disable IP forwarding\n\t");
				}
				break;
				
			case 24:
				sb.append("Option : (24) Path MTU Aging Timeout Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String t = String.join("", option);
				sb.append("Timeout : (" + Tools.convertHextoDec(t) + " s)\n\t");
				break;
				
			case 29:
				sb.append("Option : (29) Perform Mask Discovery Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				if(Tools.convertHextoDec(option.get(0))==1) {
					sb.append("The client should perform mask discovery\n\t");
				}
				if(Tools.convertHextoDec(option.get(0))==0) {
					sb.append("The client should not perform mask discovery\n\t");
				}
				break;
				
			case 30:
				sb.append("Option : (30) Mask Supplier Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				if(Tools.convertHextoDec(option.get(0))==1) {
					sb.append("The client should respond\n\t");
				}
				if(Tools.convertHextoDec(option.get(0))==0) {
					sb.append("The client should not respond\n\t");
				}
				break;
			
			case 31:
				sb.append("Option : (31) Perform Router Discovery Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				if(Tools.convertHextoDec(option.get(0))==1) {
					sb.append("The client should perform router discovery\n\t");
				}
				if(Tools.convertHextoDec(option.get(0))==0) {
					sb.append("The client should not perform router discovery\n\t");
				}
				break;
				
			case 32:
				sb.append("Option : (32) Router Solicitation Address Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String ad = String.join(".", option);
				sb.append("Address : " + ad + "\n\t");
				break;
				
			case 38:
				sb.append("Option : (38) TCP Keepalive Interval Option\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String tm = String.join("", option);
				sb.append("Time : (" + Tools.convertHextoDec(tm) + " s)\n\t");
				break;
			
			
			case 50 :
				//discover
				//discover request
				sb.append("Option : (50) Ip Address Lease Time \n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String rIp = String.join(".",option);
				sb.append("Server Ip : " + rIp +"\n\t");
				break;
			case 51 :
				//discover request
				sb.append("Option : (51) Ip Address Lease Time \n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String leasteTime = String.join("",option);
				sb.append("Lease Time  : " + Tools.convertHextoDec(leasteTime)+"\n\t");
				break;
				
			case 53 :
				sb.append("Option : (53) DHCP Message Type\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				
				switch (Tools.convertHextoDec(option.get(0))){
				case 1: 
					sb.append("DHCP : Discosver (1)" +"\n\t" );
					break;
				case 2 :
					sb.append("DHCP : Offer (2)"+"\n\t");
					break;
				case 3 :
					sb.append("DHCP : Request (3)"+"\n\t" );
					break;
				case 4 :
					sb.append("DHCP : Decline (4)" +"\n\t");
					
					break;
				case 5 :
					sb.append("DHCP : Ack (5)"+"\n\t" );
					break; 
				case 6 :
					sb.append("DHCP : Nak (6)"+"\n\t" );
					break;
				case 7 :
					sb.append("DHCP : Release (7)" +"\n\t");
					break;
				case 8:
					sb.append("DHCP : Inform (8)" +"\n\t");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + option.get(0)+"\n\t");
				}
				
				break;
				
			case 54 : 
				sb.append("Option : (54) Server Identifier\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String ipServer = String.join(".",option);
				sb.append("Server Ip : " + ipServer+"\n\t");
				break;
			
			case 55 : 
				
				sb.append("Option : (55) Parameter Request List \n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length));
				for(String s : option) {
					sb.append("\n\t\t"+"Parameter Request List Item : (" + Tools.convertHextoDec(s)+")");
				}
				sb.append("\n\t");
				break;
				
			case 56 :
				
				sb.append("Option : (56) Message\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String mess = String.join("", option);
				sb.append("Message  : " + Tools.hexToASCII(mess)+"\n\t");
				
			case 57 :
				//discover
				sb.append("Option : (57) Maximum DHCP Message Size\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String l = String.join("", option);
				sb.append("Mmaximum length DHCP message  : " + Tools.convertHextoDec(l)+"\n\t");
				
				break; 
				
			case 58:
				sb.append("Option : (58) Renewal Time Value\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String tmp = String.join("", option);
				sb.append("Renewal Time Value : (" + Tools.convertHextoDec(tmp) + " s)\n\t");
				break;
				
			case 59:
				sb.append("Option : (59) Rebinding Time Value\n\t\t");
				sb.append("Length : " +  Tools.convertHextoDec(length)+"\n\t\t");
				String v = String.join("", option);
				sb.append("Rebinding Time Value : (" + Tools.convertHextoDec(v) + " s)\n\t");
				break;
			
			case 61:
				sb.append("Option : (61) Client Identifier\n\t\t");
				int len = Tools.convertHextoDec(length);
				sb.append("Length : " + len  +"\n\t\t");
				String hardwareType  = option.get(0);
				sb.append("Hardware type : " ); 
				extracted(sb, hardwareType);
				String cIdent = String.join(":", option.subList(1, len));
				sb.append(cIdent+"\n\t");
				
				
				// fonction hardware type a faire
				
				break;
			
			case 255:
				sb.append("Option (255) End \n\t\t Option End : 255"+"\n\t");
				break;
			default :
				sb.append("unable analyse option : " +  Tools.convertHextoDec(tag)+"\n\t");
		}
		
		return sb.toString();
	}
	
	private void extracted(StringBuilder sb, String hardwareType) {
		switch(Tools.convertHextoDec(hardwareType)) {
		case 1:
			sb.append("Ethernet (0x");
			break;
			
		case 6:
			sb.append("IEEE 802 Networks (0x");
			break;
			
		case 7 : 
			sb.append("ARCNET (0x");
			break;
			
		case 11 :
			sb.append("LocalTalk (0x");
			break;
			
		case 12:
			sb.append("LocalNet (0x");
			break;
			
			
		case 14 :
			sb.append("SMDS (0x");
			break;
			
		case 15:
			sb.append("Frame Relay (0x");
			break;
			
		case 16 :
			sb.append("Asynchronous Transfer Mode (0x");
			break;
			
		case 17 :sb.append("HDLC (0x");
			break;
			
		case 18:sb.append("Fibre Channel (0x");
			break;
			
		case 19 :
			sb.append("Asynchronous Transfer Mode (0x");
			break;
			
		case 20 :
			sb.append("Serial Line (0x");
			break;
			
		default:
			sb.append("unable to analyse option type");
}
		sb.append(hardwareType+")\n\t\t");
	}

	@Override
	public String toString() {
		return "OptionDHCP [tag=" + tag + ", length=" + length + ", option=" + option + "]";
	}
	
	
}
