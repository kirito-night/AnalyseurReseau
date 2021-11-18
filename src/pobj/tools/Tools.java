package pobj.tools;

public class Tools {
	public static int convertHextoDec(String hex) {
		return Integer.parseInt(hex, 16);
	}
	
	public static String convertDectoHex(int dec) {
		return Integer.toHexString(dec);
	}
	
	public static int convertBintoDec(String bin) {
		return Integer.parseInt(bin, 2);
	}
	
	public static String convertDectoBin(int dec) {
		return Integer.toBinaryString(dec);
	}
	
	
	public static String convertHextoBin(String hex) {
		return convertDectoBin(convertHextoDec(hex));
	}
	
	public static String converBintoHex(String bin) {
		return convertDectoHex(convertBintoDec(bin));
	}
	
	public static void main(String[] args){
		System.out.println(convertHextoDec("F"));
		System.out.println(convertHextoBin("ff"));
		System.out.println(convertBintoDec("11111111"));
	}
}
