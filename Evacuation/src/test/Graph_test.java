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
			
			boolean valid = Checker.check_solution(solution, graph);
			
			System.out.println(valid);
			
			valid= Checker.check_solution(Solution.generate_infimum(graph), graph);
			
			System.out.println("L'infimum est :" +valid);
			
			valid= Checker.check_solution(Solution.generate_maximum(graph), graph);
			
			System.out.println(valid);
		
		}
		
}
