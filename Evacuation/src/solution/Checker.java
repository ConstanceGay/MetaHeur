package solution;

import java.util.ArrayList;
import java.util.ListIterator;
import graph.*;

public class Checker {
	
	public static ArrayList<Integer> create_int_list(ArrayList<Node> list) {
		ArrayList<Integer> int_list = new ArrayList<Integer>();
		ListIterator<Node> ite = list.listIterator();
		while(ite.hasNext()) {	
			Node cur=ite.next();
			int_list.add(cur.get_id());
		}
		return int_list;
	}
	
	public static Checker_message check_solution(Solution solution, Graph graph) {
		//The solution is valid unless there is a proof against
		boolean valid = true;	
		String reason = " ";
		int noeud = -1;
		
		ArrayList<Integer> list = create_int_list(graph.get_nodes());
		
		//this matrix represent the flow entering an arc at a specific time
		int[][] matrix= new int[solution.get_date_end_evac()][graph.get_nb_node()+1];
		
		//We will iterate on all evac node checking the validity
		ArrayList<EvacNode> ListEvacNode=solution.get_list_evac_node();
		ListIterator<EvacNode> iteEvacNode = ListEvacNode.listIterator();
		while(valid && iteEvacNode.hasNext()) {
			EvacNode currentEvacNode=iteEvacNode.next();			
			//System.out.println("Checker : on passe sur le noeud d'evac : "+currentEvacNode.get_id_node());
			Node currentNode=graph.get_node_by_id(currentEvacNode.get_id_node());
			
			int time=currentEvacNode.get_start_evac();
			int rate=currentEvacNode.get_rate(); 
			
			//System.out.println("Checker : son rate est : "+rate+" et il commence l'evac � : "+time);
			
			//nb_packets is the number of subdivided groups of the evacuating population that are equals to the rate
			int nb_packets= currentNode.get_population()/currentEvacNode.get_rate();
			//the number of persons in the last packet
			int last_packet = currentNode.get_population() % currentEvacNode.get_rate();		
	
			//System.out.println("Checker : On aura "+nb_packets+" paquets et le dernier paquets aura "+last_packet+" personnes.");
			
			//We will check all the evacuation path through this iterator
			ArrayList<Integer> EvacPath=currentNode.get_evac_path();
			ListIterator<Integer> iteEvacPath=EvacPath.listIterator();
			int currentNodePathid=-1;
			while (valid && iteEvacPath.hasNext()) {
				currentNodePathid=iteEvacPath.next();
					//if this is not the last node of the evacuation path, we check the validity of the arc
					if(currentNodePathid != graph.get_safe_node()) {
						//System.out.println("Checker : ce n'est pas le safe node");
						Node currentNodePath=graph.get_node_by_id(currentNodePathid);
						//System.out.println("Checker : son max_rate est : "+currentNodePath.get_max_rate());
						int arcLength=currentNodePath.get_arc().get_length();
						//System.out.println("Checker : son arc de sortie a pour longueur "+arcLength+" sa duedate est "+currentNodePath.get_arc().get_duedate());
						
						//Checking that nobody enter an arc after the duedate
						if(last_packet==0) {
							valid= valid && time+nb_packets <= currentNodePath.get_arc().get_duedate();
						}
						else{
							valid= valid &&  time +nb_packets+1 <= currentNodePath.get_arc().get_duedate();
						}
						if(!valid) {
							reason = "duedate";
							noeud = currentNodePath.get_id(); 
						}
						//System.out.println("Checker : personne n'entre dans l'arc apr�s sa duedate : "+valid);
						
						
						//Adding this flow of persons in the accurate arc at the accurate time in the matrix
						if(valid) {
							int i;
							for(i=time; i<time+nb_packets;i++) {
								//System.out.println("Checker : on acc�de � la colonne "+list.indexOf(currentNodePathid)+" de la matrice et ligne "+i);
								matrix[i][list.indexOf(currentNodePathid)]+=rate;
								//Checking the arc overflow
								valid= valid && matrix[i][list.indexOf(currentNodePathid)]<=currentNodePath.get_arc().get_capacity();
							}
							matrix[i][list.indexOf(currentNodePathid)]+=last_packet;
							valid=valid && matrix[i][list.indexOf(currentNodePathid)]<currentNodePath.get_arc().get_capacity(); //check arc capacity
							
							//update time for the next arc
							time += arcLength;
							if(!valid) {
								reason = "overflow";
								noeud = currentNodePath.get_id();
							}
						}
					}
					//If this is the safe node, the evacuation is wrong if this is not the last node, ie there is a next node in the evacuation path
					else {
						valid=valid && !iteEvacPath.hasNext();
					}
			}
		}
		ArrayList<Integer> pb_list = null;
		
		//on cherche les noeuds d'evac concern�s pas le probl�me
 		if (noeud != -1) {
 			pb_list = new ArrayList<Integer>();
 			ListIterator<Node> iteEvac=graph.get_evac_nodes().listIterator();
			while (iteEvac.hasNext()) {
				Node currentNode=iteEvac.next();
				if(currentNode.get_evac_path().contains(noeud)) {
					pb_list.add(currentNode.get_id());
				}
			}
		} 		
		return new Checker_message(valid,reason,pb_list);
	}
	
}
