package solution;
import Graph.*;

public class EvacNode {
	private Node node;
	private int rate;
	private int start_evac;
	
	public EvacNode(Node node,int rate, int start_evac) {
		this.node=node;
		this.rate=rate;
		this.start_evac=start_evac;
	}
	
	
	//GETTER
	
	public Node get_Node() {
		return this.node;
	}
	
	public int get_rate() {
		return this.rate;
	}
	
	public int get_start_evac() {
		return this.start_evac;
	}
	
	//SETTER
	
	public void set_Node(Node node) {
		this.node=node;
	}
	
	public void set_rate(int rate) {
		this.rate=rate;
	}
	
	public void set_start_evac(int evac) {
		this.start_evac=evac;
	}
	
}
