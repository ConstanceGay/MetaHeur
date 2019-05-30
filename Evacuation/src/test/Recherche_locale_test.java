package test;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import Graph.Graph;
import solution.Checker;
import solution.Solution;

public class Recherche_locale_test {
	public static void main(String args[]) {
	String filename="example2.5";
	String path = new File("../Evacuation/src/Examples/"+filename+".full").getAbsolutePath();
	Graph graph = Graph.generate_from_file(path); 
		
	Solution maximum=Solution.generate_maximum(graph, filename);
	Solution local_infimum=maximum.recherche_locale(graph);
	local_infimum.write_file_solution("../Evacuation/src/Examples/" + filename + "_recherchelocale10.txt");
	
	}
}
