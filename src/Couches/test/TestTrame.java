package Couches.test;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import Couches.Trame;
import pobj.lecture.FileReader;
import pobj.output.Output;
public class TestTrame {
	public static void main(String[] argrs)  {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("entrer le path de votre fichier .txt");
			String fileName = scan.nextLine();
			FileReader f1 = new FileReader(fileName);
			
			if(f1 == null) {
				throw new Exception("pb ouverture");
			}
			
			List<Trame> listTrame = Trame.generateListTrame(f1.getMapTrames());
			StringBuilder sb = new StringBuilder();
			
			
			for(Trame t : listTrame){
				//System.out.println(Trame.trameToString(t) +"\n");
				
			
				
				String [] result = t.resultattAnalyse();
				/*for(String s : result) {
					System.out.print(s);
				}*/
				
				
				String res = String.join("", result);
				sb.append(res);
				//Output.output(result, file);
				
				
			
			}
			
			System.out.println(sb.toString());
			System.out.println("voulez vous sauvegarder ? tapez  Y pour oui, N pour non (si vous tapez autre chose que Y (Maj) cela serait considerer comme un non");
			if(scan.nextLine().equals("Y")) {
				System.out.println("veuillez entrer le nom du fichier que vous voulez sauvegarder");
				String saveName = scan.nextLine();
				File file = new File(saveName);
				Output.outputStream(sb.toString(), file);
			}
			
			
			
			System.out.println("program terminer");
			scan.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("probleme d'ouverture de fichier");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
	}
	
}
