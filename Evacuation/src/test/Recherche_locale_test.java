package test;

import java.io.File;

import Graph.Graph;
import solution.Solution;

public class Recherche_locale_test {
	public static void main(String args[]) {
		String filename="sparse_10_30_3_1";
		String path = new File("../Instances/"+filename+".full").getAbsolutePath();
		Graph graph = Graph.generate_from_file(path); 
		
		//Solution solution = Checker.generate_solution_from_file("../Evacuation/src/Examples/"+filename+".txt");
		
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
		local_infimum.write_file_solution("../Evacuation/src/Examples/"+filename+"_recherchelocale_sans_div_1.txt");
		
		Solution local_infimum2 = maximum.recherche_locale_avec_diversification(graph);
		System.out.println("Le local_infimum est valide : " + local_infimum2.get_validity());
		System.out.println(("Le temps d'evac du local_infimum est: "+local_infimum2.get_date_end_evac()));
		local_infimum.write_file_solution("../Evacuation/src/Examples/"+filename+"_recherchelocale_avec_div_1.txt");
	}
}
