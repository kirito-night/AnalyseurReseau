package testCouches;

import pobj.lecture.FileReader;

import java.util.List;

import Couches.*;
public class TestTrame {
	public static void main(String[] argrs) {
		FileReader f1 = new FileReader("data/data3.txt");
		
		List<Trame> listTrame = Trame.generateListTrame(f1.getMapTrames());
		
		for(Trame t : listTrame){
			System.out.println(Trame.trameToString(t) +"\n");
			
		
			
			String [] result = t.resultattAnalyse();
			for(String s : result) {
				System.out.println(s);
			}
		}
	}
	
}
