package Graph;

public class Node {

	private int id;
	private Arc arc_evac;
	private int population;
	private int max_rate;
	
	//CONSTRUCTORS
	
	public Node(int id,Arc arc_evac,int population, int max_rate) {
		this.id=id;
		this.arc_evac=arc_evac;
		this.population=population;
		this.max_rate=max_rate;
	}
	
	public Node(int id,Arc arc_evac) {
		this.id=id;
		this.arc_evac=arc_evac;
		this.population=0;
		this.max_rate=0;
	}
	
	//GETTER
	public int get_id() {
		return this.id;
	}
	
	public Arc get_arc() {
		return this.arc_evac;
	}
	
	//SETTER
	public void set_id(int id) {
		this.id=id;
	}
	
	public void set_arc(Arc arc) {
		this.arc_evac=arc;
	}

}
