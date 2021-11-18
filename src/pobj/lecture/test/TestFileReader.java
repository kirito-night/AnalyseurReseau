package pobj.lecture.test;

import java.util.List;
import java.util.Map;

import pobj.lecture.*;
	public class TestFileReader {
		public static void main(String[] argrs) {
			FileReader f1 = new FileReader("data/data1.txt");
			
			String fullTrame = f1.getFullTrames();
			//System.out.println(fullTrame);
			/*for(int i = 0 ; i < trameBrut.length ; i++) {
				System.out.println(trameBrut[i]);
			}*/
			
			
			Map<Integer, List<String>> trame = f1.getMapTrames();
			//System.out.println(trame.get(0));
			
			for(Map.Entry<Integer, List<String>> entry : trame.entrySet()) {
				List<String> currentTrame = entry.getValue();
				System.out.println(currentTrame.get(0));
				/*for(String s : currentTrame) {
					System.out.print(s + " ");
				}*/
				System.out.println();
				//System.out.println(entry.getValue());
			}
		}
}
