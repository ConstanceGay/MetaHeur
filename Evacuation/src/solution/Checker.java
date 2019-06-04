package solution;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	
	public static ArrayList<Integer> create_int_list(ArrayList<Node> list) {
		ArrayList<Integer> int_list = new ArrayList<Integer>();
		ListIterator<Node> ite = list.listIterator();
		while(ite.hasNext()) {	
			Node cur=ite.next();
			int_list.add(cur.get_id());
		}
		return int_list;
	}
	
	public static boolean check_solution(Solution solution, Graph graph) {
		//The solution is valid unless there is a proof against
		boolean valid = true;		
		ArrayList<Integer> list = create_int_list(graph.get_nodes());
		//this matrix represent the flow entering an arc at a specific time
		int[][] matrix= new int[solution.get_date_end_evac()][graph.get_nb_node()+1];
		
		//We will iterate on all evac node checking the validity
		ArrayList<EvacNode> ListEvacNode=solution.get_list_evac_node();
		ListIterator<EvacNode> iteEvacNode = ListEvacNode.listIterator();
		while(valid && iteEvacNode.hasNext()) {
			
			EvacNode currentEvacNode=iteEvacNode.next();			
			Node currentNode=graph.get_node_by_id(currentEvacNode.get_id_node());
			
			int time=currentEvacNode.get_start_evac();
			int rate=currentEvacNode.get_rate(); 
			
			//nb_packets is the number of subdivided groups of the evacuating population that are equals to the rate
			int nb_packets= currentNode.get_population()/currentEvacNode.get_rate();
			//the number of persons in the last packet
			int last_packet = currentNode.get_population() % currentEvacNode.get_rate();		
			
			int arcLength= currentNode.get_arc().get_length();
	
			//We will check all the evacuation path through this iterator
			ArrayList<Integer> EvacPath=currentNode.get_evac_path();
			ListIterator<Integer> iteEvacPath=EvacPath.listIterator();
			int currentNodePathid=-1;
			while (valid && iteEvacPath.hasNext()) {
				currentNodePathid=iteEvacPath.next();
					//if this is not the last node of the evacuation path, we check the validity of the arc
					if(currentNodePathid != graph.get_safe_node()) {
						
						Node currentNodePath=graph.get_node_by_id(currentNodePathid);
						arcLength=currentNodePath.get_arc().get_length();
						
						//Checking that nobody enter an arc after the duedate
						if(last_packet==0) {
							valid= valid && time+nb_packets <= currentNodePath.get_arc().get_duedate();
						}
						else{
							valid= valid && time+nb_packets+1 <= currentNodePath.get_arc().get_duedate();
						}
						
						//Adding this flow of persons in the accurate arc at the accurate time in the matrix
						if(valid) {
							int i;
							for(i=time; i<time+nb_packets;i++) {
								matrix[i][list.indexOf(currentNodePathid)]+=rate;
								//Checking the arc overflow
								valid= valid && matrix[i][list.indexOf(currentNodePathid)]<=currentNodePath.get_arc().get_capacity();
							}
							matrix[i][list.indexOf(currentNodePathid)]+=last_packet;
							valid=valid && matrix[i][list.indexOf(currentNodePathid)]<currentNodePath.get_arc().get_capacity(); //check arc capacity
							
							//update time for the next arc
							time += arcLength;
							if(last_packet!=0) {
								time++;
							}
						}
					}
					
					//If this is the safe node, the evacuation is wrong if this is not the last node, ie there is a next node in the evacuation path
					else {
						valid=valid && !iteEvacPath.hasNext();
					}
			}
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
