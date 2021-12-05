package pobj.tools;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

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
		 	hex = hex.replaceAll("0", "0000");
	        hex = hex.replaceAll("1", "0001");
	        hex = hex.replaceAll("2", "0010");
	        hex = hex.replaceAll("3", "0011");
	        hex = hex.replaceAll("4", "0100");
	        hex = hex.replaceAll("5", "0101");
	        hex = hex.replaceAll("6", "0110");
	        hex = hex.replaceAll("7", "0111");
	        hex = hex.replaceAll("8", "1000");
	        hex = hex.replaceAll("9", "1001");
	        hex = hex.replaceAll("A", "1010");
	        hex = hex.replaceAll("B", "1011");
	        hex = hex.replaceAll("C", "1100");
	        hex = hex.replaceAll("D", "1101");
	        hex = hex.replaceAll("E", "1110");
	        hex = hex.replaceAll("F", "1111");
	        hex = hex.replaceAll("a", "1010");
	        hex = hex.replaceAll("b", "1011");
	        hex = hex.replaceAll("c", "1100");
	        hex = hex.replaceAll("d", "1101");
	        hex = hex.replaceAll("e", "1110");
	        hex = hex.replaceAll("f", "1111");
	        
	        return hex;
	}
	
	
	public static String hexToASCII(String hex1)
    {
        // initialize the ASCII code string as empty.
		String hex = hex1.replaceAll(" ", "");
        String ascii = "";
 
        for (int i = 0; i < hex.length(); i += 2) {
 
            // extract two characters from hex string
            String part = hex.substring(i, i + 2);
 
            // change it into base 16 and typecast as the character
            char ch = (char)Integer.parseInt(part, 16);
 
            // add this char to final ASCII string
            ascii = ascii + ch;
        }
 
        return ascii;
    }
	
	public static String converBintoHex(String bin) {
		return convertDectoHex(convertBintoDec(bin));
	}
	
	
	
	public static void main(String[] args){
		System.out.println(convertHextoDec("86DD"));
		System.out.println(convertBintoDec("11"));
		/*
		System.out.println(convertHextoBin("0000"));
		System.out.println(convertHextoBin("aaaa"));
		System.out.println(convertBintoDec("11111111"));
		System.out.println(hexToASCII("630063"));
		assertEquals("\0\0", hexToASCII("0000"));*/
		
	}
}
