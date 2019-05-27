package test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ListIterator;

import Graph.Graph;
import Graph.Node;
import solution.*;


public class Graph_test {
		@SuppressWarnings("unused")
		public static void main (String arg[]){
			String filename="example2.5";
			String path = new File("../Evacuation/src/Examples/"+filename+".full").getAbsolutePath();
			Graph graph = Graph.generate_from_file(path); 
			graph.show_graph();
			
			Solution solution = Checker.generate_solution_from_file("../Evacuation/src/Examples/"+filename+".txt");
			
			Solution infimum=Solution.generate_infimum(graph,filename);
			System.out.println("L'infimum est valide : " + infimum.get_validity());
			infimum.write_file_solution("../Evacuation/src/Examples/" + filename+"_infimum.txt");
			
			Solution maximum=Solution.generate_maximum(graph,filename);
			System.out.println("Le maximum est valide : " + maximum.get_validity());
			maximum.write_file_solution("../Evacuation/src/Examples/" + filename+"_maximum.txt");
		
		}
		
}
