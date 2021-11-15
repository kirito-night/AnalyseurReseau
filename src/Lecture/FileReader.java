package Lecture;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class FileReader {
	private String filename;
	private Map<Integer, List<String>> trames;
	
	
	private void read() {
		String currentLine;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new java.io.FileReader(filename));
			
			
		}catch(IOException e) {
			e.getStackTrace();
		}
		
	}
	
}
