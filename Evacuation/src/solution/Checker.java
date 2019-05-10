package solution;

import java.io.File;
import Graph.Graph;

public class Checker {
	
	public void main() {
	String filename="exemple2.5.txt";
	String path = new File("../Instances/"+filename+".full").getAbsolutePath();
	Graph graph = Graph.generate_from_file(path); 
	}
}
