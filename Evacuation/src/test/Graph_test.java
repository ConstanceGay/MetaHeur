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
			String filename="sparse_10_30_3_1";
			String path = new File("../Instances/"+filename+".full").getAbsolutePath();
			Graph graph = Graph.generate_from_file(path); 
			graph.show_graph();
		}
		
}
