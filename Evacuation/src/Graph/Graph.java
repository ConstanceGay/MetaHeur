package Graph;
import java.io.IOException;
import java.util.*;

import javax.xml.crypto.NodeSetData;

import solution.EvacNode;

import java.io.*;

public class Graph {
	
	private int nb_node;						//number of nodes in total
	private int nb_evac_nodes;					//number of nodes to evacuate
	private int safe_node;						//the node that needs to be reached
	private ArrayList<Node> evac_nodes;			//list of nodes to evacuate
	private ArrayList<Node> Nodes;				//list of nodes
	
	public Graph(int safe_node,ArrayList<Node> evac_nodes,ArrayList<Node> Nodes) {
		this.Nodes=Nodes;
		this.safe_node=safe_node;
		this.nb_node=Nodes.size();
		this.nb_evac_nodes=evac_nodes.size();
		this.evac_nodes=evac_nodes;
	}
	
	public Graph() {
		this.Nodes=new ArrayList<Node>();
		this.nb_node=0;
		this.nb_evac_nodes=0;
		this.evac_nodes= new ArrayList<Node>();
		this.safe_node=0;
	}
	
	//GETTER
	
	public ArrayList<Node> get_nodes(){
		return this.Nodes;
	}
	
	public ArrayList<Node> get_evac_nodes(){
		return this.evac_nodes;
	}
	
	public int get_nb_node() {
		return this.nb_node;
	}
	
	public int get_nb_evac_nodes() {
		return this.nb_evac_nodes;
	}
	
	public int get_safe_node() {
		return this.safe_node;
	}
	//return null if node not in graph
	public Node get_node_by_id(int id) {
		Node result=null;
		boolean trouve=false;
		
		ListIterator<Node> ite = this.Nodes.listIterator();
		while (!trouve && ite.hasNext()) {
			Node current_node=ite.next();
			//System.out.println(current_node.get_id());
			if(current_node.get_id()==id) {
				result=current_node;
				trouve=true;
			}
		}
		return result;
		
	}
	//SETTER
	
	public void set_safe_node(int id) {
		this.safe_node=id;
	}

	//GENERATOR
	public static Graph generate_from_file(String path) {
		String line = null;
		try {
			FileReader fileReader=new FileReader(path);			
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			
			line=bufferedReader.readLine();														//skip the first line
			line=bufferedReader.readLine();				
			
			int espace=line.indexOf(" ");
			int nb_evac_nodes=Integer.parseInt(line.substring(0, espace ) );					//number of nodes to evacuate
			int id_safe_node=Integer.parseInt(line.substring( (espace+1), (line.length()) ) ); 	//id of the safe node
			
			ArrayList<Node> ListNodeEvac = new ArrayList<Node>();								//List of nodes to evacuate
			ArrayList<Node> ListNodeFinal = new ArrayList<Node>();								//List of all the nodes in the graph
			
			ListNodeFinal.add(new Node(id_safe_node,null));										//add the safe node to the list
			
			for(int i=0;i<nb_evac_nodes;i++) {													//iteration on all the evac_nodes
				line=bufferedReader.readLine();
				
				espace=line.indexOf(" ");
				int id_node=Integer.parseInt(line.substring(0, (espace) ) );					//id of the node
				line=line.substring((espace+1),(line.length()));
				
				espace=line.indexOf(" ");
				int population=Integer.parseInt(line.substring(0, (espace) ) );					//population of the node
				line=line.substring((espace+1),(line.length()));
				
				espace=line.indexOf(" ");
				int max_rate=Integer.parseInt(line.substring(0, (espace) ) );					//max_rate of the node
				line=line.substring((espace+1),(line.length()));
				
				espace=line.indexOf(" ");
				int k=Integer.parseInt(line.substring(0, (espace) ) );							//k (number of nodes on evac path) of the node
				line=line.substring((espace+1),(line.length()));
				
				ArrayList<Integer> chemin = new ArrayList<Integer>();							//evacuation path of the node				
				chemin.add(id_node);
				for(int j=0;j<k;j++) {															//parse the evacuation path of the node
					int id_nextnode;
					if (j==(k-1)) {																//end of line
						id_nextnode=Integer.parseInt(line.substring(0, line.length() ) );			
					} else {
						espace=line.indexOf(" ");
						id_nextnode=Integer.parseInt(line.substring(0, (espace) ) );
						line=line.substring((espace+1),(line.length()));
					}
					chemin.add(id_nextnode);
				}
				ListNodeEvac.add(new Node(id_node,null,population,max_rate,chemin));			//add node to the list of nodes to evacuate		
			}
			bufferedReader.close();
			
			//GETTING THE ARCS ON THE EVACUATION PATHS
			ListIterator<Node> ite_list_evac = ListNodeEvac.listIterator();
			
			while (ite_list_evac.hasNext()) {													//loop on all the evac nodes
				Node cur_evac_node = ite_list_evac.next();
				ArrayList<Integer> current_path= cur_evac_node.get_evac_path();					//get the evacuation path of the current node
				
				for (int j=0;j<current_path.size()-1;j++) {										//loop on all the nodes of the evacuation path
					fileReader=new FileReader(path);
					bufferedReader=new BufferedReader(fileReader);
																						
					for(int h=0;h <= nb_evac_nodes+4;h++) {
						line=bufferedReader.readLine();											//skip all the lines before the list of arcs
					}
					
					boolean trouve=false;
					while(!trouve && line!=null) {												//loop on all the lines looking for the current node + the one after
						espace=line.indexOf(" ");
						int node1=Integer.parseInt(line.substring(0, (espace) ) );				//get the first node
						line=line.substring((espace+1),(line.length()));
						
						espace=line.indexOf(" ");
						int node2=Integer.parseInt(line.substring(0, (espace) ) );				//get the second node
						line=line.substring((espace+1),(line.length()));
						
						if(node1==current_path.get(j) && node2==current_path.get(j+1)) {		//if they match the pair on the evac path:
							trouve=true;
							espace=line.indexOf(" ");
							long due_date=Long.parseLong(line.substring(0, (espace) ) );		//retrieve due date of arc
							line=line.substring((espace+1),(line.length()));
							
							espace=line.indexOf(" ");
							int length=Integer.parseInt(line.substring(0, (espace) ) );			//retrieve length of arc
							line=line.substring((espace+1),(line.length()));
							
							float capacity=Float.parseFloat(line.substring(0, (espace) ) );		//retrieve capacity of arc
							line=line.substring(0,(line.length()));								//End of line
							
							Arc new_arc=new Arc(node1,node2,due_date,length,capacity);
							
							ListIterator<Node> iteNode = ListNodeFinal.listIterator();
							boolean is_in_list=false;
							while(iteNode.hasNext() && !is_in_list) {							//checks if node is already in the final list, so as not to add it again
								Node cur_node = iteNode.next();
								if(cur_node.get_id()==node1) {
									is_in_list=true;
								}
							}
										
							if(node1==cur_evac_node.get_id()) {									//checks if the node is an evacuation node
									cur_evac_node.set_arc(new_arc);								//add the arc to the current evac_node (it'll change in both lists)
									if(!is_in_list) {
										ListNodeFinal.add(cur_evac_node);
									}
								
							}else {
								if(!is_in_list) {												//To not add it to the list again
									Node new_node = new Node(node1, new_arc);					//if not, a new node is created
									ListNodeFinal.add(new_node);								//and added to the list of final nodes
								}
							}
						}
						line=bufferedReader.readLine();
					}
					bufferedReader.close();
				}
			}			
			Collections.sort(ListNodeFinal, new Comparator<Node>(){								//puts list in order
				@Override
				public int compare(Node arg0, Node arg1) {
					return Integer.compare(arg0.get_id(), arg1.get_id());
					
				}
	        });
			
			return new Graph(id_safe_node,ListNodeEvac,ListNodeFinal);							//a graph is created and returned
		}
		catch(IOException e) {
			System.out.println(e.toString());
			return null;
		}
	}
	public void show_graph() {
		System.out.println("EVACUATION INFORMATION\n");
		System.out.println("Number of nodes to evacuate: "+this.get_nb_evac_nodes()+"\n");
		System.out.println("Exit (safe node): "+this.get_safe_node());
		System.out.println("-------------------------------------");
		System.out.println("List of nodes to evacuate");
		ListIterator<Node> ite = this.get_evac_nodes().listIterator();				
		while(ite.hasNext()) {
			Node cur_node = ite.next();
			System.out.println("ID: "+cur_node.get_id()+" || Population: "+cur_node.get_population()+" || Max evacuation rate: "+cur_node.get_max_rate());
			System.out.println("Evacuation path: "+cur_node.get_evac_path().toString());
			System.out.println();
		}
		
		System.out.println("-------------------------------------");
		System.out.println("COMPLETE LIST OF NODES");
		System.out.println();
		ListIterator<Node> ite2 = this.Nodes.listIterator();				
		while(ite2.hasNext()) {
			Node cur_node = ite2.next();
			System.out.print("ID: "+cur_node.get_id());
			if (cur_node.get_id()==(this.safe_node)) {
				System.out.print(" !SAFE NODE!");
			}else {
				System.out.print(" || Arc to Node: "+cur_node.get_arc().get_id_fils());
			}
			System.out.println();
		}
		
		System.out.println("-------------------------------------");
		System.out.println("LIST OF EVACUATION ARCS");
		System.out.println();
		ListIterator<Node> ite3 = this.Nodes.listIterator();				
		while(ite3.hasNext()) {
			Node cur_node = ite3.next();
			if(cur_node.get_id()!=this.safe_node) {
				System.out.println("ID Origin: "+cur_node.get_id()+" || ID Destination: "+cur_node.get_arc().get_id_fils());
				System.out.println("Capacity: "+cur_node.get_arc().get_capacity()+" || Duedate: "+cur_node.get_arc().get_duedate()+" || Length: "+cur_node.get_arc().get_length());
				System.out.println();
			}
		}
		System.out.println("-------------------------------------");
		System.out.println("GRAPH INFORMATION\n");
		System.out.println("Total number of nodes: "+this.get_nb_node());
	}
}
