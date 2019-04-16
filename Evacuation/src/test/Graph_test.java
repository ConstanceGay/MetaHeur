package test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ListIterator;

import Graph.Graph;
import Graph.Node;


public class Graph_test {
		@SuppressWarnings("unused")
		public static void main (String arg[]){
			String filename="sparse_10_30_3_10";
			String path = new File("../Instances/"+filename+".full").getAbsolutePath();
			Graph graph = Graph.generate_from_file(path); 
			graph.show_graph();
			
			String newpath = new File("src/Examples/"+filename+".txt").getAbsolutePath();
			File file = new File(newpath);
			try {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(filename.getBytes());
				String line = graph.get_nb_evac_nodes()+"\n";
				fos.write(line.getBytes());
				
				ListIterator<Node> ite = graph.get_evac_nodes().listIterator();				
				while(ite.hasNext()) {
					Node cur_node = ite.next();
					line = cur_node.get_id()+" "+cur_node.get_max_rate()+" "+0+"\n";	//TODO
					fos.write(line.getBytes());
				}
				line="invalid\n";														//TODO
				fos.write(line.getBytes());
				
				line="Valeur fonction obj\n";											//TODO
				fos.write(line.getBytes());
				
				line="temps de calcul\n";												//TODO
				fos.write(line.getBytes());
				
				line="methode de calcul\n";												//TODO
				fos.write(line.getBytes());
				
				line="Champs liiiiiibre\n";												//TODO
				fos.write(line.getBytes());
				
				fos.flush();
				fos.close();
			}catch(Exception e) {
				System.out.println(e.toString());
			}
		}
}
