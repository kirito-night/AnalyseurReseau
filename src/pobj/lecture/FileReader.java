package pobj.lecture;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pobj.tools.Tools;


public class FileReader {
	private String filename;
	private String[] tramesBrut;
	private String fullTrames;
	
	/**
	 * mapTrames a est une Map qui contient en cle le numero de Trame, commencant de 1 , et en valeur une liste des String qui sont des octets
	 * a manipuler avec un boucle for(Map.Entry<Integer, List<String> entry : mapTrames.entrySet()  ou bien un boucle for commment de 1 en faisant des mapTrame.get(i)
	 */
	private  Map<Integer, List<String>> mapTrames;
	
	private File file ; 
	
	public FileReader (String filename) {
		this.filename = filename;
		mapTrames = new HashMap<>();
		file = new File(filename);
		fullTrames = read();
		tramesBrut = read().split("0000");
		
		mapTrames = tramesFilter();
		mapTrames.remove(0);
	}
	
	
	
	
	
	
	public String getFullTrames() {
		return fullTrames;
	}






	public void setFullTrames(String fullTrames) {
		this.fullTrames = fullTrames;
	}

	private boolean validateOffset(String off) {
		if(off.length() != 4 ) {
			return false;
		}
		try {
			Tools.convertHextoDec(off);
			
		}catch(Exception e) {
			return false;
		}
		return true;
	}





	private String read() {
		StringBuilder fullFile = new StringBuilder();
		String currentLine ; 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new java.io.FileReader(file));
			while((currentLine = br.readLine()) != null) {
				String offset = currentLine.split(" ")[0];
				if(!validateOffset(offset)) {
					continue; 
				}
				fullFile.append(currentLine);
				fullFile.append(" \n");
			}
			
		}catch(IOException e) {
			e.getStackTrace();
		}
		return fullFile.toString();
		
	}
	private Map<Integer, List<String>> tramesFilter() {
		Map<Integer, List<String>> res = new HashMap<>();
		
		for(int i = 0 ; i < tramesBrut.length ; i++) {
			String currentTrame = tramesBrut[i];
			String [] tmp = currentTrame.split(" ");
			List<String> listCurrentTrame = new  ArrayList<>();
			for(int j = 0 ;  j < tmp.length ; j++) {
				if(isOctet(tmp[j])) {
					listCurrentTrame.add(tmp[j]);
				}
			}
			
			res.put(i, listCurrentTrame);
			
		}
		
		return res;
	}
	
	
	
	
	public String[] getTramesBrut() {
		return tramesBrut;
	}





	public void setTramesBrut(String[] tramesBrut) {
		this.tramesBrut = tramesBrut;
	}





	public Map<Integer, List<String>> getMapTrames() {
		return mapTrames;
	}





	public void setMapTrames(Map<Integer, List<String>> mapTrames) {
		this.mapTrames = mapTrames;
	}





	public File getFile() {
		return file;
	}





	public void setFile(File file) {
		this.file = file;
	}





	private boolean isOctet(String str) {
        if (str.length() != 2) return false;
        try {
            int oct = Tools.convertHextoDec(str);
            if(oct > 255 || oct < 0) {
            	return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }





	public String getFilename() {
		return filename;
	}





	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
}
