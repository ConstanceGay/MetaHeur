package test;

import java.io.File;
import exception.GraphGenerationError;

import Graph.Graph;
import solution.Checker;
import solution.Solution;

public class Recherche_locale_test {
	public static void main(String args[]) {
		String filename="sparse_10_30_3_1_I";
		String path = new File("../Instances_Int/"+filename+".full").getAbsolutePath();   
	//	String filename="example2.5";
	//	String path = new File("src/Examples/"+filename+".full").getAbsolutePath(); 
		
		try {
			Graph graph = Graph.generate_from_file(path); 
			graph.show_graph();
			graph.show_all_nodes();
			
			//Solution solution = Checker.generate_solution_from_file("../Evacuation/src/Examples/FerrySpare1.txt");
			//System.out.println("Cette solution est : "+Checker.check_solution(solution, graph).get_validity());
			
			Solution infimum=Solution.generate_infimum(graph,filename);
			System.out.println("L'infimum est valide : " + infimum.get_validity());
			System.out.println(("Le temps d'evac de l'infimum est: "+infimum.get_date_end_evac()));
			//infimum.write_file_solution("../Evacuation/src/Examples/" + filename+"_infimum.txt");
			
			Solution maximum=Solution.generate_maximum(graph,filename);
			System.out.println("Le maximum est valide : " + maximum.get_validity());
			System.out.println(("Le temps d'evac du maximum est: "+maximum.get_date_end_evac()));
			System.out.println("Son problème est : "+Checker.check_solution(maximum, graph).get_reason());
			maximum.write_file_solution("../Evacuation/src/Examples/" + filename+"_maximum.txt");
			
			Solution local_infimum = maximum.recherche_locale(graph);
			System.out.println("Le local_infimum est valide : " + local_infimum.get_validity());
			System.out.println(("Le temps d'evac du local_infimum est: "+local_infimum.get_date_end_evac()));
			//local_infimum.write_file_solution("../Evacuation/src/Examples/"+filename+"_recherchelocale_sans_div_1.txt");
			
			/*
			Solution local_infimum2 = maximum.recherche_locale_avec_diversification(graph);
			System.out.println("Le local_infimum est valide : " + local_infimum2.get_validity());
			System.out.println(("Le temps d'evac du local_infimum est: "+local_infimum2.get_date_end_evac()));
			//local_infimum2.write_file_solution("../Evacuation/src/Examples/"+filename+"_recherchelocale_avec_div_1.txt");
			*/
		}catch (GraphGenerationError e) {
			System.out.println("Le graph n'a pas pu être généré car "+e.getMessage());
		}
		
	}
}
