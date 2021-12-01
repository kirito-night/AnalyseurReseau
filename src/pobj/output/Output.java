package pobj.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringJoiner;

public class Output {

	
	public static void outputStream(String res , File file) {
		try {
			FileWriter fw = new FileWriter(file);
			
			PrintWriter pw = new PrintWriter(fw, false);
			System.out.println(res);
			pw.print(res);
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("problem d'ouverture du fichier");
		}
	}
	
	
}
