package testCouches;
import java.io.File;
import java.io.IOException;
import java.util.List;

import Couches.Trame;
import pobj.lecture.FileReader;
import pobj.output.Output;
public class TestTrame {
	public static void main(String[] argrs) throws Exception {
		FileReader f1 = new FileReader("data/ipv4_with_option.txt");
		
		List<Trame> listTrame = Trame.generateListTrame(f1.getMapTrames());
		
		for(Trame t : listTrame){
			//System.out.println(Trame.trameToString(t) +"\n");
			
		
			
			String [] result = t.resultattAnalyse();
			/*for(String s : result) {
				System.out.print(s);
			}*/
			File file = new File("data/output1.txt");
			
			Output.output(result, file);
			
		
		}
	}
	
}
