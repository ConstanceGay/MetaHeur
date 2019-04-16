package Graph;

public class Arc {

	private int Node_pere;
	private int Node_fils;
	private int duedate;
	private int length;
	private int capacity;
	
	public Arc(int Node_pere,int Node_fils,int duedate, int length, int capacity) {
		this.Node_pere=Node_pere;
		this.Node_fils=Node_fils;
		this.duedate=duedate;
		this.length=length;
		this.capacity=capacity;
	}
	
	public Arc(int Node_pere,int Node_fils, int length, int capacity) {
		this.Node_pere=Node_pere;
		this.Node_fils=Node_fils;
		this.duedate=Integer.MAX_VALUE;
		this.length=length;
		this.capacity=capacity;
	}
	
	//GETTER
	public int get_id_pere() {
		return this.Node_pere;
	}
	
	public int get_id_fils() {
		return this.Node_fils;
	}
	
	public int get_duedate() {
		return this.duedate;
	}
	
	public int get_length() {
		return this.length;
	}
	
	public int get_capacity() {
		return this.capacity;
	}
	
	
	//SETTER
	public void set_id_pere(int Node_pere) {
		this.Node_pere=Node_pere;
	}
	
	public void set_id_fils(int Node_fils) {
		this.Node_fils=Node_fils;
	}
	
	public void set_duedate(int duedate) {
		this.duedate=duedate;
	}
	
	public void set_length(int length) {
		this.length=length;
	}
	
	public void set_capacity(int capacity) {
		this.capacity=capacity;
	}
	
}
