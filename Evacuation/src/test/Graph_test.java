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
			//graph.show_graph();
			
			Solution solution = Checker.generate_solution_from_file("../Evacuation/src/Examples/"+filename+".txt");
			
			Solution infimum=Solution.generate_infimum(graph,filename);
			System.out.println("L'infimum est valide : " + infimum.get_validity());
			System.out.println(("Le temps d'evac de l'infimum est: "+infimum.get_date_end_evac()));
			infimum.write_file_solution("../Evacuation/src/Examples/" + filename+"_infimum.txt");
			
			Solution maximum=Solution.generate_maximum(graph,filename);
			System.out.println("Le maximum est valide : " + maximum.get_validity());
			System.out.println(("Le temps d'evac du maximum est: "+maximum.get_date_end_evac()));
			maximum.write_file_solution("../Evacuation/src/Examples/" + filename+"_maximum.txt");
			
			Solution local_infimum = maximum.recherche_locale_sans_diversification(graph);
			System.out.println("Le local_infimum est valide : " + local_infimum.get_validity());
			System.out.println(("Le temps d'evac du local_infimum est: "+local_infimum.get_date_end_evac()));
			local_infimum.write_file_solution("../Evacuation/src/Examples/"+filename+"_recherchelocale11.txt");
			
		}
		
}
