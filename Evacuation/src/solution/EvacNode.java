package solution;

public class EvacNode {
	private int id_node;
	private int rate;
	private int start_evac;
	
	
	//Type of node used only in the checker and solution
	public EvacNode(int id_node,int rate, int start_evac) {
		this.id_node=id_node;
		this.rate=rate;
		this.start_evac=start_evac;
	}
	
	
	//GETTER
	
	public int get_id_node() {
		return this.id_node;
	}
	
	public int get_rate() {
		return this.rate;
	}
	
	public int get_start_evac() {
		return this.start_evac;
	}
	
	//SETTER
	
	public void set_id_node(int id) {
		this.id_node=id;
	}
	
	public void set_rate(int rate) {
		this.rate=rate;
	}
	
	public void set_start_evac(int evac) {
		this.start_evac=evac;
	}

	//Tostring
	public String toString() {
		return(this.get_id_node() + " " + this.get_rate() + " " + this.get_start_evac());
	}
}
