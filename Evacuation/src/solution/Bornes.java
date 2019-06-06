package solution;

import java.util.ArrayList;
import java.util.ListIterator;

import graph.Graph;
import graph.Node;

public class Bornes {
	
	private Solution infimum;
	private Solution maximum;
	
	public Bornes (Graph graph,String filename) {
		this.infimum = generate_infimum(graph,filename);
		this.maximum = generate_maximum(graph,filename);
	}
	
	//Function to generate an infimum (borne inférieure)
	public static Solution generate_infimum(Graph graph,String filename) {
		long SystemTime = System.currentTimeMillis();
			
		ArrayList<Node> ListEvacNodeGraph=graph.get_evac_nodes();
		ArrayList<EvacNode> ListEvacNodeSolution= new ArrayList<EvacNode>(); 
			
		ListIterator<Node> ite = ListEvacNodeGraph.listIterator();
		//Each node will start the evacuation at time 0 
		//end of the evacuation will be the critical time between all evacuation nodes
		int criticalTime=0;
		while(ite.hasNext()) {
			Node currentNode=ite.next();
			int rate=Math.min(currentNode.get_arc().get_capacity(), currentNode.get_max_rate());	//evacuation rate is the min between arc capacity and max evacuation rate
			ListEvacNodeSolution.add(new EvacNode(currentNode.get_id(),rate,0));
				
			//total evacuation time for each evac node
			int time=currentNode.get_population()/rate;	
			if(currentNode.get_population()%rate!=0) {										
				time++;
			}
				
			//Add the time it takes to cross each arc on the evacuation path
			ArrayList<Integer> evacPath=currentNode.get_evac_path();
			ListIterator<Integer> itePath=evacPath.listIterator();
			while(itePath.hasNext()) {
				int currentid=itePath.next();
				if(currentid != graph.get_safe_node()) {
					time+=graph.get_node_by_id(currentid).get_arc().get_length();
				}
			}
			if(time>criticalTime) {
				criticalTime=time;				//stores the total evacuation time
			}
		}		
		Solution result = new Solution(filename,graph.get_nb_evac_nodes(),ListEvacNodeSolution,false,criticalTime,System.currentTimeMillis()-SystemTime,"infimum","");
		result.set_validity(Checker.check_solution(result, graph).get_validity());
		return result;
	}
		
		
	//Function to generate a maximum (borne supérieure)
	public static Solution generate_maximum(Graph graph,String filename) {
		long SystemTime = System.currentTimeMillis();
			
		ArrayList<Node> ListEvacNodeGraph = graph.get_evac_nodes();
		ArrayList<EvacNode> ListEvacNodeSolution = new ArrayList<EvacNode>();
			
		ListIterator<Node> ite = ListEvacNodeGraph.listIterator();
		//each node will evacuated when the preceding evacuation is done
		//the end of the evacuation will be when the last node is completely evacuated
		int time = 0;
		while(ite.hasNext()) {
			Node currentNode = ite.next();
			//this time the rate is the minimum between arc capacity and max evacuation rate
			int rate=Math.min(currentNode.get_arc().get_capacity(), currentNode.get_max_rate());
			ListEvacNodeSolution.add(new EvacNode(currentNode.get_id(),rate,time));
				
			time += currentNode.get_population()/rate;
			if(currentNode.get_population()%rate != 0) {
				time++;
			}
				
			ArrayList<Integer> evacPath = currentNode.get_evac_path();
			ListIterator<Integer> iteEvacPath = evacPath.listIterator();
			while (iteEvacPath.hasNext()) {
				int currentEvacNode = iteEvacPath.next();
				if(currentEvacNode != graph.get_safe_node()) {
					time += graph.get_node_by_id(currentEvacNode).get_arc().get_length();
				}
			}
		}
		Solution result = new Solution(filename,graph.get_nb_evac_nodes(),ListEvacNodeSolution,false,time,(System.currentTimeMillis()-SystemTime),"maximum","");
		result.set_validity(Checker.check_solution(result, graph).get_validity());
		return result;
	}
	
	//SETTER
	public void set_infimum(Solution inf) {
		this.infimum = inf;
	}
	
	public void set_maximum(Solution max) {
		this.maximum = max;
	}
	
	//GETTER
	public Solution get_infimum() {
		return this.infimum;
	}
		
	public Solution get_maximum() {
		return this.maximum;
	}
		
}
