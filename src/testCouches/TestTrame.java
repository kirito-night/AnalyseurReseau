package testCouches;
import java.io.File;
import java.util.List;

import Couches.Trame;
import pobj.lecture.FileReader;
import pobj.output.Output;
public class TestTrame {
	public static void main(String[] argrs) {
		FileReader f1 = new FileReader("data/data4.txt");
		
		List<Trame> listTrame = Trame.generateListTrame(f1.getMapTrames());
		
		for(Trame t : listTrame){
			System.out.println(Trame.trameToString(t) +"\n");
			
		
			
			String [] result = t.resultattAnalyse();
			for(String s : result) {
				System.out.println(s);
			}
			//File file = new File("data/output.txt");
			//Output.output(result, file);
		
		}
	}
	
}