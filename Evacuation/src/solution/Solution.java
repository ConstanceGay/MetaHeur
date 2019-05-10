package solution;
import java.util.*;

public class Solution {
	private String filename;
	private int nb_evac_node;
	private ArrayList<EvacNode> list_evac_node; 
	private boolean is_valid;
	private int date_end_evac;
	private String method;
	private String free_space;
	
	public Solution(String filename,int nb_evac_node,ArrayList<EvacNode> list_evac_node,boolean is_valid,int date_end_evac,String method,String free_space) {
		this.filename=filename;
		this.nb_evac_node=nb_evac_node;
		this.list_evac_node=list_evac_node;
		this.is_valid=is_valid;
		this.date_end_evac=date_end_evac;
		this.method=method;
		this.free_space=free_space;
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
}
