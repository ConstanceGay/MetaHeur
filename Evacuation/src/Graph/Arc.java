package Graph;

public class Arc {

	private int id_pere;
	private int id_fils;
	private long duedate;
	private int length;
	private float capacity;
	
	public Arc(int id_pere,int id_fils,long duedate, int length, float capacity) {
		this.id_pere=id_pere;
		this.id_fils=id_fils;
		this.duedate=duedate;
		this.length=length;
		this.capacity=capacity;
	}
	
	public Arc(int id_pere,int id_fils, int length, float capacity) {
		this.id_pere=id_pere;
		this.id_fils=id_fils;
		this.duedate=Integer.MAX_VALUE;
		this.length=length;
		this.capacity=capacity;
	}
	
	//GETTER
	public int get_id_pere() {
		return this.id_pere;
	}
	
	public int get_id_fils() {
		return this.id_fils;
	}
	
	public long get_duedate() {
		return this.duedate;
	}
	
	public int get_length() {
		return this.length;
	}
	
	public float get_capacity() {
		return this.capacity;
	}
	
	
	//SETTER
	public void set_id_pere(int id_pere) {
		this.id_pere=id_pere;
	}
	
	public void set_id_fils(int id_fils) {
		this.id_fils=id_fils;
	}
	
	public void set_duedate(int duedate) {
		this.duedate=duedate;
	}
	
	public void set_length(int length) {
		this.length=length;
	}
	
	public void set_capacity(float capacity) {
		this.capacity=capacity;
	}
	
}
