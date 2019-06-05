package solution;
import java.util.ArrayList;

//class to give the reason of a validity failure along with the evac nodes that need to be changed
public class Checker_message {
	private boolean validity;
	private String reason;
	private ArrayList<Integer> list_nodes_to_change;
	
	public Checker_message (boolean val, String reason, ArrayList<Integer> list) {
		this.validity = val;
		this.reason = reason;
		this.list_nodes_to_change = list;
	}
	
	//GETTER
	public boolean get_validity () {
		return this.validity;
	}
	
	public String get_reason () {
		return this.reason;
	}
	
	public ArrayList<Integer> get_list_nodes () {
		return this.list_nodes_to_change;
	}
	
	//SETTER
	public void set_validity (boolean valid) {
		this.validity = valid;
	}
	
	public void get_reason (String reason) {
		this.reason = reason;
	}
	
	public void get_list_nodes (ArrayList<Integer> list) {
		this.list_nodes_to_change = list;
	}

}
