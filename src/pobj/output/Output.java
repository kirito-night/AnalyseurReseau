package pobj.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class Output {
	public static void output(String[] content , File file) {
	
		
		/*StringJoiner sj = new StringJoiner("");
		for(String s : content) {
			sj.add(s);
		}*/
		
		//String res = sj.toString();
		
		
		
		
		try {
			/*
			FileWriter ftmp = new FileWriter(file, false);
			BufferedWriter btmp = new BufferedWriter(ftmp);
			btmp.write("");
			btmp.close();*/

			String res = String.join("",content);
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			//bw.write("");
			/*for(String res : content) {
				bw.write(res);
				
			}*/
			bw.write(res);
			System.out.println(res);
			//bw.write(String.join("\n\n", content));
			
			//bw.write("");
			//bw.write(res);
			
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
