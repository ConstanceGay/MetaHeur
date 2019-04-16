package Graph;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class Graph {
	
	private int nb_node;
	private int safe_node;
	private ArrayList<Node> Nodes;
	
	public Graph(ArrayList<Node> Nodes,int safe_node) {
		this.Nodes=Nodes;
		this.nb_node=Nodes.size();
		this.safe_node=safe_node;
	}
	
	public Graph() {
		this.Nodes=new ArrayList<Node>();
		this.nb_node=0;
		this.safe_node=0;
	}
	
	//GETTER
	
	public ArrayList<Node> get_nodes(){
		return this.Nodes;
	}
	
	public int get_size() {
		return this.nb_node;
	}
	
	//SETTER
	
	public void set_safe_node(int id) {
		this.safe_node=id;
	}

	
	
	//GENERATOR
	public Graph generate_from_file(String path) {
		String line = null;
		try {
			FileReader fileReader=new FileReader(path);			
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			
			line=bufferedReader.readLine();
			line=bufferedReader.readLine();
			
			int espace=line.indexOf(" ");
			int nb_nodes=Integer.parseInt(line.substring(0, (espace-1) ) );
			int id_safe_node=Integer.parseInt(line.substring( (espace+1), (line.length()-1) ) ); 
			
			ArrayList<Node> ListNodeDepart = new ArrayList<Node>();
			
			ArrayList<Node> ListNodeFinal = new ArrayList<Node>();
			
			ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
			
			for(int i=0;i<nb_nodes;i++) {
				line=bufferedReader.readLine();
				
				espace=line.indexOf(" ");
				int id_node=Integer.parseInt(line.substring(0, (espace-1) ) );
				line=line.substring((espace+1),(line.length()-1));
				
				
				espace=line.indexOf(" ");
				int population=Integer.parseInt(line.substring(0, (espace-1) ) );
				line=line.substring((espace+1),(line.length()-1));
				
				espace=line.indexOf(" ");
				int max_rate=Integer.parseInt(line.substring(0, (espace-1) ) );
				line=line.substring((espace+1),(line.length()-1));
				
				espace=line.indexOf(" ");
				int k=Integer.parseInt(line.substring(0, (espace-1) ) );
				line=line.substring((espace+1),(line.length()-1));
				
				ArrayList<Integer> chemin = new ArrayList<Integer>();
				chemin.add(id_node);
				
				ListNodeDepart.add(new Node(id_node,null,population,max_rate));
				
				for(int j=0;j<k;j++) {
					espace=line.indexOf(" ");
					int id_nextnode=Integer.parseInt(line.substring(0, (espace-1) ) );
					line=line.substring((espace+1),(line.length()-1));
					
					chemin.add(id_nextnode);
				}
				
				paths.add(chemin);
			}
			
			bufferedReader.close();
			
			for (int i=0;i<nb_nodes;i++) {
				ArrayList<Integer> current_path= paths.get(i);
				for (int j=0;j<current_path.size()-1;j++) {
					bufferedReader=new BufferedReader(fileReader);
					
					int h=0;
					while(h <= nb_nodes+4) {
						line=bufferedReader.readLine();
						h++;
					}
					
					boolean trouve=false;
					while(!trouve && line!=null) {
						espace=line.indexOf(" ");
						int node1=Integer.parseInt(line.substring(0, (espace-1) ) );
						line=line.substring((espace+1),(line.length()-1));
						
						espace=line.indexOf(" ");
						int node2=Integer.parseInt(line.substring(0, (espace-1) ) );
						line=line.substring((espace+1),(line.length()-1));
						
						if(node1==current_path.get(j) && node2==current_path.get(j+1)) {
							trouve=true;
							espace=line.indexOf(" ");
							int due_date=Integer.parseInt(line.substring(0, (espace-1) ) );
							line=line.substring((espace+1),(line.length()-1));
							
							espace=line.indexOf(" ");
							int length=Integer.parseInt(line.substring(0, (espace-1) ) );
							line=line.substring((espace+1),(line.length()-1));
							
							espace=line.indexOf(" ");
							int capacity=Integer.parseInt(line.substring(0, (espace-1) ) );
							line=line.substring((espace+1),(line.length()-1));
							
							Arc new_arc=new Arc(node1,node2,due_date,length,capacity);
							
							ListIterator<Node> ite = ListNodeDepart.listIterator();
							boolean trouve_noeud=false;
							while(ite.hasNext() && trouve_noeud) {
								Node cur_node = ite.next();
								if (cur_node.get_id()==node1) {
									cur_node.set_arc(new_arc);
									ListNodeFinal.add(cur_node);
									trouve_noeud=true;
								}
							}
						}
						
					}
					
					
					bufferedReader.close();
				}
			}
			return new Graph(ListNodeFinal,safe_node);
		}
		catch(IOException e) {
			System.out.println(e.toString());
			return null;
		}
	}
}
