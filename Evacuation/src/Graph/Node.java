package Graph;

public class Node {

	private int id;
	private Arc arc_evac;
	
	public Node(int id,Arc arc_evac) {
		this.id=id;
		this.arc_evac=arc_evac;
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
