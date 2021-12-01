package testCouches;
import java.io.File;
import java.io.IOException;
import java.util.List;

import Couches.Trame;
import pobj.lecture.FileReader;
import pobj.output.Output;
public class TestTrame {
	public static void main(String[] argrs) throws Exception {
		FileReader f1 = new FileReader("data/dns.txt");
		
		List<Trame> listTrame = Trame.generateListTrame(f1.getMapTrames());
		StringBuilder sb = new StringBuilder();
		File file = new File("data/output1.txt");
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
		Output.outputStream(sb.toString(), file);
	}
	
}
