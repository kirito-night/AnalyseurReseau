package Couches;

public class Ethernet implements ICouches{
	private String srcMac;
	private String srcDest;
	private String type;
	public Ethernet(String srcMac, String srcDest, String type) {
		super();
		this.srcMac = srcMac;
		this.srcDest = srcDest;
		this.type = type;
	}
	
	
}
