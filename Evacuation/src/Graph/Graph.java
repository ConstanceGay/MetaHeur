package Graph;
import java.util.*;

public class Graph {
	
	private int nb_node;
	private ArrayList<Node> Nodes;
	
	public Graph(ArrayList<Node> Nodes) {
		this.Nodes=Nodes;
		this.nb_node=Nodes.size();
	}
	
	public Graph() {
		this.Nodes=new ArrayList<Node>();
		this.nb_node=0;
	}
	
	//GETTER
	
	public ArrayList<Node> get_nodes(){
		return this.Nodes;
	}
	
	public int get_size() {
		return this.nb_node;
	}
	
}
