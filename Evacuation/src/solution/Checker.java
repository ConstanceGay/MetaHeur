package solution;

import java.io.File;
import java.util.ArrayList;
import java.io.*;

import Graph.Graph;

public class Checker {
	
	public static Solution generate_solution_from_file(String path) {
		String line = null;
		try {
			FileReader fileReader=new FileReader(path);			
			BufferedReader bufferedReader=new BufferedReader(fileReader);
	
			String file_name=bufferedReader.readLine(); 	//id of the safe node
			
			line=bufferedReader.readLine();
			
			int nb_evac_nodes=Integer.parseInt(line.substring(0, (line.length())));
			
			ArrayList<EvacNode> ListEvacNode = new ArrayList<EvacNode>();								//List of nodes to evacuate
			
			for(int i=0;i<nb_evac_nodes;i++) {													//iteration on all the evac_nodes
				line=bufferedReader.readLine();
				
				int espace=line.indexOf(" ");
				int id_node=Integer.parseInt(line.substring(0, (espace) ) );					//id of the node
				line=line.substring((espace+1),(line.length()));
				
				espace=line.indexOf(" ");
				int max_rate=Integer.parseInt(line.substring(0, (espace) ) );					//max_rate of the node
				line=line.substring((espace+1),(line.length()));
				
				espace=line.indexOf(" ");
				int end_date=Integer.parseInt(line.substring(0, (line.length())));
				line=line.substring((espace+1),(line.length()));
				
				ListEvacNode.add(new EvacNode(id_node,max_rate,end_date));			//add node to the list of nodes to evacuate
				//System.out.println("For node n°"+i+" the path is: "+chemin.toString()+"\n");		
			}
			
			boolean isValid;
			String isValidStr=bufferedReader.readLine();
			if (isValidStr.equals("valid")){
				isValid = true;
			} else{
				isValid = false;
			}
			
			line=bufferedReader.readLine();
			int value=Integer.parseInt(line.substring(0, (line.length()) ) );
			
			String method=bufferedReader.readLine();
			String free_space=bufferedReader.readLine();
			
			bufferedReader.close();
			
			return new Solution(file_name,nb_evac_nodes,ListEvacNode,isValid,value,method,free_space);
		}
		catch(IOException e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	public static void main(String arg[]) {
	String filename="exemple2.5";
	String path = new File("src/Examples/"+filename+".txt").getAbsolutePath();
	Solution solution = generate_solution_from_file(path); 
	solution.print_solution();
	}
	
	
}
