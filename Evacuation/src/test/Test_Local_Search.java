package test;

import java.io.File;
import exception.GraphGenerationError;
import graph.Graph;
import solution.Bornes;
import solution.Checker;
import solution.Solution;

public class Test_Local_Search {
	public static void main(String args[]) {
		String filename="sparse_10_30_3_1_I";
		String path = new File("../Instances_Int/"+filename+".full").getAbsolutePath();   
		
		try {
			Graph graph = Graph.generate_from_file(path); 
			graph.show_graph();
			//graph.show_all_nodes();
			
			System.out.println("-------------------------------------");
			System.out.println("BORNES");
			Bornes bornes = new Bornes(graph,filename);
			Solution infimum = bornes.get_infimum();
			System.out.println("The infimum is valid : " + infimum.get_validity());
			System.out.println(("The evac time of the infimum is: "+infimum.get_date_end_evac()));
			infimum.write_file_solution("../Solutions/" + filename+"_infimum.txt");
			
			Solution maximum=bornes.get_maximum();
			System.out.println("The maximum is valid : " + maximum.get_validity());
			System.out.println(("The evac time of the maximum : "+maximum.get_date_end_evac()));
			System.out.println("The problem is : "+Checker.check_solution(maximum, graph).get_reason());
			maximum.write_file_solution("../Solutions/" + filename+"_maximum.txt");
			
			//TEST OF VALIDITY THEN OPTIMALITY LOCAL SEARCH
			System.out.println("-------------------------------------");
			System.out.println("VALIDITY THEN OPTIMALITY LOCAL SEARCH");
			Solution local_infimum = maximum.recherche_locale(graph);
			System.out.println("The local_infimum is valid : " + local_infimum.get_validity());
			System.out.println(("The evac time of the local infimum is : "+local_infimum.get_date_end_evac()));
			local_infimum.write_file_solution("../Solutions/"+filename+"_recherchelocale.txt");
			
			//TEST OF RANDOM LOCAL SEARCH
			System.out.println("-------------------------------------");
			System.out.println("RANDOM LOCAL SEARCH");
			Solution local_infimum2 = maximum.recherche_locale_sans_diversification(graph);
			System.out.println("The local_infimum is valid : " + local_infimum2.get_validity());
			System.out.println(("The evac time of the local infimum is : "+local_infimum2.get_date_end_evac()));
			local_infimum2.write_file_solution("../Solutions/"+filename+"_recherchelocale_sans_div.txt");
			
			//TEST OF RANDOM LOCAL SEARCH WITHOUT DIVERSIFICATION
			System.out.println("-------------------------------------");
			System.out.println("RANDOM LOCAL SEARCH WITH DIVERSIFICATION");
			Solution local_infimum3 = maximum.recherche_locale_avec_diversification(graph);
			System.out.println("The local_infimum is valid : " + local_infimum3.get_validity());
			System.out.println(("The evac time of the local infimum is : "+local_infimum3.get_date_end_evac()));
			local_infimum2.write_file_solution("../Solutions/"+filename+"_recherchelocale_avec_div.txt");

		}catch (GraphGenerationError e) {
			System.out.println("The graph wasn't generated because : "+e.getMessage());
		}
		
	}
}
