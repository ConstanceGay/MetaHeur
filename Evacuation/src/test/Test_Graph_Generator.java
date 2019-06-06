package test;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import exception.GraphGenerationError;
import graph.Graph;

//Test that prints en entire Graph
public class Test_Graph_Generator {
		
	private static ArrayList<String> get_all_graph_from_dir(){
		File folder = new File("../Instances_Int/");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> list = new ArrayList<String>();
			
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
			  list.add(listOfFiles[i].getName());
			} 
		}
		return list;
	}


	public static void main (String arg[]){
		
		ArrayList<String> list_graph = get_all_graph_from_dir();
		ListIterator<String> ite = list_graph.listIterator();
		int nb_errors = 0;
			
		while(ite.hasNext()) {
			String filename = ite.next();
			String path = new File("../Instances_Int/"+filename).getAbsolutePath();
			try {
				Graph graph = Graph.generate_from_file(path); 
				graph.show_graph();
			} catch (GraphGenerationError e) {
				System.out.println("The graph from file "+filename+" wasn't generated "+e.getMessage());
				nb_errors++;
			}
		}
		System.out.println();
		System.out.println("Number of graphs that failed to generate : "+nb_errors);
	}
		
		
}
