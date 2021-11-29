package Couches;

import java.util.List;

import pobj.tools.Tools;

public class Ethernet implements ICouches{
	private String srcMac;
	private String destMac;
	private String type;
	
	private List<String> enteteEth;
	private List<String> data;
	
	public String getSrcMac() {
		return srcMac;
	}

	public void setSrcMac(String srcMac) {
		this.srcMac = srcMac;
	}

	public String getDestMac() {
		return destMac;
	}

	public void setDestMac(String destMac) {
		this.destMac = destMac;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getEnteteEth() {
		return enteteEth;
	}

	public void setEnteteEth(List<String> enteteEth) {
		this.enteteEth = enteteEth;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public Ethernet(String srcMac, String destMac, String type) {
		super();
		this.srcMac = srcMac;
		this.destMac = destMac;
		this.type = type;
	}
	
	public Ethernet(List<String> trame) { 
		
		getChamp(trame);
		
	}
	
	
	public void getChamp(List<String> trame) {
		enteteEth = trame.subList(0, 14);
		data = trame.subList(14, trame.size());
		
		int i = 0;
		destMac = enteteEth.get(i);
		i++;
		
		
		for( ; i < 6 ; i++) {
			destMac= destMac+":" + enteteEth.get(i);
		}
		
		srcMac = enteteEth.get(i);
		i++;
		
		for( ; i < 12 ; i++) {
			srcMac= srcMac+":" + enteteEth.get(i);
		}
		
		type = enteteEth.get(i) +enteteEth.get(++i);
		
		try {
			if(i != 13) { 
				throw new Exception();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		
	}
	
	
	
	public String analyse() {
		StringBuilder sb = new StringBuilder();
		sb.append("Ethernet II : \n\t");
		sb.append("Destination : "+ destMac + "\n\t");
		sb.append("Source : " + srcMac + "\n\t");
		
		switch(Tools.convertHextoDec(type)) {
			case  2048:
				sb.append("Type : IPv4 "+"(0x"+type+")"+ "\n");
				break;
			case 2054 :
				sb.append("Type : ARP "+"(0x"+type+")"+ "\n");
				break;
			case 34525 : 
				sb.append("Type : IPv6 "+"(0x"+type+")"+ "\n");
			default : 
				sb.append("Type : unable analyse type "+"(0x"+type+")"+ "\n");
		}
		
		
	
		return sb.toString();

	}
	
}