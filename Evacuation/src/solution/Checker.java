package solution;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;
import java.io.*;
import Graph.*;

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
	
	public static boolean check_solution(Solution solution, Graph graph) {
		boolean valid = true;
		int nb_nodes = graph.get_nb_node();
		int date_end_evac=solution.get_date_end_evac();
		
		int[][] matrix= new int[date_end_evac][nb_nodes+1];
		
		ArrayList<EvacNode> ListEvacNode=solution.get_list_evac_node();
		ListIterator<EvacNode> iteEvacNode = ListEvacNode.listIterator();
		
		
		while(valid && iteEvacNode.hasNext()) {
			EvacNode currentEvacNode=iteEvacNode.next();			
			Node currentNode=graph.get_node_by_id(currentEvacNode.get_id_node());
			
			//System.out.println("Id of EvacNode : " + currentEvacNode.get_id_node());
			//System.out.println(currentNode);
			
			int time=currentEvacNode.get_start_evac();
			int rate=currentEvacNode.get_rate(); //check rate OK
			//check rate
			int delta= currentNode.get_population()/currentEvacNode.get_rate();
			int rest = currentNode.get_population() % currentEvacNode.get_rate();		
			
			int arcLength= currentNode.get_arc().get_length();
			
			//first iteration
			if(rest==0) {
				valid= valid && time+delta < currentNode.get_arc().get_duedate();
			}
			else{
				valid= valid && time+delta+1 < currentNode.get_arc().get_duedate();
			}		
			
			int j;
			for(j=time; j<time+delta;j++) {
				matrix[j][currentNode.get_id()]+=rate;
				valid= valid && matrix[j][currentNode.get_id()]<=currentNode.get_arc().get_capacity();
			}
			matrix[j][currentNode.get_id()]+=rest;
			valid=valid && matrix[j][currentNode.get_id()]<currentNode.get_arc().get_capacity(); //check arc capacity
			//end first iteration
			
			ArrayList<Integer> EvacPath=currentNode.get_evac_path();
			ListIterator<Integer> iteEvacPath=EvacPath.listIterator();
			
			time += arcLength;
			if(rest!=0)
				time++;
			
			int currentNodePathid=-1;
			while (valid && iteEvacPath.hasNext()) {
				currentNodePathid=iteEvacPath.next();
					if(currentNodePathid != graph.get_safe_node()) {
						Node currentNodePath=graph.get_node_by_id(currentNodePathid);
						
						arcLength=currentNodePath.get_arc().get_length();
						
						//check duedate
						if(rest==0) {
							valid= valid && time+delta < currentNodePath.get_arc().get_duedate();
						}
						else{
							valid= valid && time+delta+1 < currentNodePath.get_arc().get_duedate();
						}
						
						
						int i;
						for(i=time; i<time+delta;i++) {
							matrix[i][currentNodePathid]+=rate;
							valid= valid && matrix[i][currentNodePathid]<=currentNodePath.get_arc().get_capacity();
						}
						matrix[i][currentNodePathid]+=rest;
						valid=valid && matrix[i][currentNodePathid]<currentNodePath.get_arc().get_capacity(); //check arc capacity
						
						//update time
						time += arcLength;
						if(rest!=0) {
							time++;
						}
					}
					else {
						valid=false;
					}
			}
			valid= valid && currentNodePathid == graph.get_safe_node();
			
		}
		return valid;
	}
	
	public static void main(String arg[]) {
	String filename="example2.5";
	String path = new File("src/Examples/"+filename+".txt").getAbsolutePath();
	Solution solution = generate_solution_from_file(path); 
	solution.print_solution();
	}
	
	
}
