package Couches;

import java.util.List;

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
			case 61:
				sb.append("Option : (61) Client Identifier\n\t");
				sb.append("length : " +  Tools.convertHextoDec(length)+"\n\t");
				sb.append("Hardware type : " +  Tools.convertHextoDec(length)+"\n\t"); // fonction hardware type a faire
				
				break;
			case 53 :
				sb.append("Option : (53) DHCP Message Type\n\t");
				sb.append("length : " +  Tools.convertHextoDec(length)+"\n\t");
				
				switch (Tools.convertHextoDec(option.get(0))){
				case 1: 
					sb.append("DHCP : Discosver (1)\n" );
					break;
				case 2 :
					sb.append("DHCP : Offer (2)\n" );
					break;
				case 3 :
					sb.append("DHCP : Request (3)\n" );
					break;
				case 4 :
					sb.append("DHCP : Decline (4)\n" );
					
					break;
				case 5 :
					sb.append("DHCP : Ack (5)\n" );
					break; 
				case 6 :
					sb.append("DHCP : Nak (6)\n" );
					break;
				case 7 :
					sb.append("DHCP : Release (7)\n" );
					break;
				case 8:
					sb.append("DHCP : Inform (8)\n" );
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + option.get(0));
				}
				
				break;
			case 255:
				sb.append("Option (255) End \n\t Option End : 255");
				break;
			default :
				sb.append("unable analyse option : " +  Tools.convertHextoDec(tag));
		}
		
		return sb.toString();
	}

	@Override
	public String toString() {
		return "OptionDHCP [tag=" + tag + ", length=" + length + ", option=" + option + "]";
	}
	
	
}
