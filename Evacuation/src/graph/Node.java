package graph;

import java.util.ArrayList;

public class Node {

	private int id;
	private Arc arc_evac;
	private int population;
	private int max_rate;
	private ArrayList<Integer> evac_path;
	
	//CONSTRUCTORS
	public Node(int id,Arc arc_evac,int population, int max_rate, ArrayList<Integer> evac_path) {
		this.id=id;
		this.arc_evac=arc_evac;
		this.population=population;
		this.max_rate=max_rate;
		this.evac_path=evac_path;
	}
	
	public Node(int id,Arc arc_evac) {
		this.id=id;
		this.arc_evac=arc_evac;
		this.population=0;
		this.max_rate=0;
		this.evac_path=null;
	}
	
	//GETTER
	public int get_id() {
		return this.id;
	}
	
	public Arc get_arc() {
		return this.arc_evac;
	}
	
	public int get_population() {
		return this.population;
	}
	
	public int get_max_rate() {
		return this.max_rate;
	}
	
	public ArrayList<Integer> get_evac_path() {
		return this.evac_path;
	}
	
	//SETTER
	public void set_id(int id) {
		this.id=id;
	}
	
	public void set_arc(Arc arc) {
		this.arc_evac=arc;
	}

}
