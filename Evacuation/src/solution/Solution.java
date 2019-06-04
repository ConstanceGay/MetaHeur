package solution;
import java.io.PrintWriter;
import java.util.*;
import Graph.*;

public class Solution {
	private String filename;
	private int nb_evac_node;
	private ArrayList<EvacNode> list_evac_node; 
	private boolean is_valid;
	private int date_end_evac;
	private long calcul_time=0;
	private String method;
	private String free_space;
	
	
	//CONSTRUCTORS
	
	public Solution(String filename,int nb_evac_node,ArrayList<EvacNode> list_evac_node,boolean is_valid,int date_end_evac,String method,String free_space) {
		this.filename=filename;
		this.nb_evac_node=nb_evac_node;
		this.list_evac_node=list_evac_node;
		this.is_valid=is_valid;
		this.date_end_evac=date_end_evac;
		this.method=method;
		this.free_space=free_space;
	}
	
	public Solution(String filename,int nb_evac_node,ArrayList<EvacNode> list_evac_node,boolean is_valid,int date_end_evac,long calcul_time,String method,String free_space) {
		this.filename=filename;
		this.nb_evac_node=nb_evac_node;
		this.list_evac_node=list_evac_node;
		this.is_valid=is_valid;
		this.date_end_evac=date_end_evac;
		this.calcul_time=calcul_time;
		this.method=method;
		this.free_space=free_space;
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
			int time=currentNode.get_arc().get_length()+currentNode.get_population()/rate;		
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
		
		Solution result = new Solution(filename,graph.get_nb_evac_nodes(),ListEvacNodeSolution,false,criticalTime,(System.currentTimeMillis()-SystemTime),"infimum","");
		result.set_validity(Checker.check_solution(result, graph));
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
		result.set_validity(Checker.check_solution(result, graph));
		return result;
	}
	
	//generates a list of solution
	public ArrayList<Solution> generateNeighborhoodRandom(int nb_neighbor,Graph graph, int max_delta_rate, int max_delta_start){
		int i;
		String method="Random with " + nb_neighbor + " neigbhors with parameters max_delta rate : " + max_delta_rate + " max_delta_start : " + max_delta_start;
		ArrayList<Solution> result=new ArrayList<Solution>();
		
		Random Generator = new Random();
		for(i=0;i<nb_neighbor;i++) {
			Solution temp=new Solution(this.get_filename(),this.get_nb_evac_node(),new ArrayList<EvacNode>(), false ,this.get_date_end_evac(),this.get_calcul_time(),method,"");
			
			ArrayList<EvacNode> EvacNodeList = this.list_evac_node;
			ArrayList<EvacNode> ModifiedEvacNodeList = new ArrayList<EvacNode>();
			ListIterator<EvacNode> ite = EvacNodeList.listIterator();
			while(ite.hasNext()) {
				EvacNode currentEvacNodeOrigin = ite.next();
				EvacNode currentEvacNode = new EvacNode(currentEvacNodeOrigin.get_id_node(),currentEvacNodeOrigin.get_rate(),currentEvacNodeOrigin.get_start_evac());
				Node currentNode = graph.get_node_by_id(currentEvacNode.get_id_node());
				//random choice 
				if(Generator.nextBoolean()) {
					//min between : evac_rate+random(0-max_delta) OR max_rate of node 
					currentEvacNode.set_rate(Math.min(currentEvacNode.get_rate()+Generator.nextInt(max_delta_rate),currentNode.get_max_rate()));
				} else {
					//max between:  1 OR evac_rate-random(0-max_delta_rate) 
					currentEvacNode.set_rate(Math.max(1,currentEvacNode.get_rate()-Generator.nextInt(max_delta_rate)));
				}
				
				//another random choice
				if(Generator.nextBoolean()) {
					//augmenter date of start evac de random(0-max_delta_start) 
					currentEvacNode.set_start_evac(currentEvacNode.get_start_evac()+Generator.nextInt(max_delta_start));
				} else {
					//set date of start evac to max of 0 OR date_of_start+random(0,max_delta_start) 
					currentEvacNode.set_start_evac(Math.max(0,currentEvacNode.get_start_evac()-Generator.nextInt(max_delta_start)));
				}
				ModifiedEvacNodeList.add(currentEvacNode);
			}
			temp.set_list_evac_node(ModifiedEvacNodeList);
			temp.compute_end_date_evac(graph);
			temp.set_method(method);
			temp.set_validity(Checker.check_solution(temp, graph));
			if(temp.get_validity()) {
				//System.out.println("CHEF! ON EN A TROUVE UN!");
			}
			result.add(temp);
		}
		return result;
	}
	
	//A utiliser si la solution de départ est valide. Peut trouver des solutions plus optis mais moins efficace que d'autres recherches
	public Solution recherche_locale_sans_diversification(Graph graph) {
		int max_delta_rate=10;
		int max_delta_start=10;
		int size_neighborhood=50;
		int size_compteur=20;
		int nb_iteration = 20;
		
		Solution result=new Solution(this.get_filename(),this.get_nb_evac_node(),(ArrayList<EvacNode>) this.get_list_evac_node().clone(), false ,this.get_date_end_evac(),this.get_calcul_time(),method,"");
		
		
		int z=result.get_date_end_evac();
		
		boolean done=false;
		int compteur=0;
		
		for(int i=0; i<nb_iteration; i++) {
			//choix solution
			//System.out.println("Debut boucle avec comme objectif de faire mieux que : " + z);
			ArrayList<Solution> neighborhood= new ArrayList<Solution>();
			
			//generate a certain number of solutions
			neighborhood = result.generateNeighborhoodRandom(size_neighborhood, graph,max_delta_rate,max_delta_start);
			
			ListIterator<Solution> ite = neighborhood.listIterator();
			while(ite.hasNext()) {
				Solution temp=ite.next();
				//temp.print_solution();
				int heuristique= temp.get_date_end_evac();
				
				//put a true solution above a false one
				if(!result.is_valid && temp.is_valid ) {
					System.out.println(result.is_valid +" "+temp.is_valid);
					result=temp;
					z=heuristique;
				//don't try to compare a true solution with a false one
				}else if( !(result.is_valid && !temp.is_valid) && (heuristique < z) ) {
					result=temp;
					z=heuristique;
				}				
			}
			
		}		
		return result;
	}
	
	public Solution recherche_locale_avec_diversification(Graph graph) {
		int max_delta_rate=10;
		int max_delta_start=10;
		int size_neighborhood=50;
		int size_compteur=20;
		int nb_iteration = 20;
		
		Random Generator = new Random();
		
		Solution result=new Solution(this.get_filename(),this.get_nb_evac_node(),(ArrayList<EvacNode>) this.get_list_evac_node().clone(), false ,this.get_date_end_evac(),this.get_calcul_time(),method,"");
		
		int z=result.get_date_end_evac();
		
		boolean done=false;
		int compteur=0;
		
		for(int i=0; i<nb_iteration; i++) {
			//choix solution
			ArrayList<Solution> neighborhood= new ArrayList<Solution>();
			
			//generate a certain number of solutions
			neighborhood = result.generateNeighborhoodRandom(size_neighborhood, graph,max_delta_rate,max_delta_start);
			
			ListIterator<Solution> ite = neighborhood.listIterator();
			while(ite.hasNext()) {
				Solution temp=ite.next();
				//temp.print_solution();
				int heuristique= temp.get_date_end_evac();
				
				//put a true solution above a false one
				if(!result.is_valid && temp.is_valid ) {
					System.out.println(result.is_valid +" "+temp.is_valid);
					result=temp;
					z=heuristique;
				//don't try to compare a true solution with a false one
				}else if( !(result.is_valid && !temp.is_valid) && (heuristique < z) ) {
					result=temp;
					z=heuristique;
				}				
			}
			
		}		
		return result;
	}
	
	//GETTER
	
	public String get_filename() {
		return this.filename;
	}
	
	public int get_nb_evac_node() {
		return this.nb_evac_node;
	}
	
	public ArrayList<EvacNode> get_list_evac_node(){
		return this.list_evac_node;
	}
	
	public boolean get_validity() {
		return this.is_valid;
	}
	
	public int get_date_end_evac() {
		return this.date_end_evac;
	}
	
	public String get_method() {
		return this.method;
	}
	
	public String get_free_space() {
		return this.free_space;
	}
	
	public long get_calcul_time() {
		return this.calcul_time;
	}
	
	//SETTER
	
	public void set_filename(String filename) {
		this.filename=filename;
	}
	
	public void set_nb_evac_node(int nb) {
		this.nb_evac_node=nb;
	}
	
	public void set_list_evac_node(ArrayList<EvacNode> list) {
		this.list_evac_node=list;
	}
	
	public void set_validity(boolean is_valid) {
		this.is_valid=is_valid;
	}
	
	public void set_end_date_evac(int date) {
		this.date_end_evac=date;
	}
	
	public void set_method(String method) {
		this.method=method;
	}
	
	public void set_free_space(String comment) {
		this.free_space=comment;
	}
	
	public void compute_end_date_evac(Graph graph) {
		ListIterator<EvacNode> ite = this.get_list_evac_node().listIterator();
		int criticalTime=0;
		
		while(ite.hasNext()) {
			EvacNode currentEvacNode=ite.next();
			Node currentNode=graph.get_node_by_id(currentEvacNode.get_id_node());
			
			int rate=currentEvacNode.get_rate();
			int time=currentEvacNode.get_start_evac(); //debut de l'évacuation
			//calcul of total evacuation time for each evac node			
			time+=currentNode.get_population()/rate; //temps de retard du à la division de la population
			if(currentNode.get_population()%rate!=0) {
				time++; //dernier paquet 
			}
			ArrayList<Integer> evacPath=currentNode.get_evac_path();
			ListIterator<Integer> itePath=evacPath.listIterator();
			
			while(itePath.hasNext()) {
				int currentid=itePath.next();
				if(currentid != graph.get_safe_node()) { //pas d'arc si safe node : chemin terminé
					time+=graph.get_node_by_id(currentid).get_arc().get_length();
				}
			}
			if(time>criticalTime) {
				criticalTime=time;
			}
		}
		this.date_end_evac=criticalTime;
	}
	
	// PRINTER & WRITER 
	public void print_solution() {
		System.out.println(this.filename);
		System.out.println(this.nb_evac_node);
		
		ListIterator<EvacNode> ite = this.list_evac_node.listIterator();
		while(ite.hasNext()) {
			EvacNode currentNode=ite.next();
			System.out.println("idnode:" + currentNode.get_id_node() + " rate : " + currentNode.get_rate() + " date start evac : " + currentNode.get_start_evac());
		}
		System.out.println(this.is_valid);
		System.out.println(this.date_end_evac);
		System.out.println(this.method);
		System.out.println(this.free_space);
	}
	
	public void write_file_solution(String path) {
		try {
			PrintWriter writer = new PrintWriter(path,"UTF-8");
			writer.println(this.filename);
			writer.println(this.get_nb_evac_node());
			ListIterator<EvacNode> ite = this.get_list_evac_node().listIterator();
			while(ite.hasNext()) {
				writer.println(ite.next().toString());
			}
			if(this.is_valid) {
				writer.println("valid");
			}else {
				writer.println("invalid");
			}
			writer.println(this.date_end_evac);
			writer.println(this.calcul_time);
			writer.println(this.method);
			writer.println(this.free_space);
			writer.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
