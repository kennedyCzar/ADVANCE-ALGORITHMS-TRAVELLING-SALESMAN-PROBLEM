package bruteforceTSP;

import java.util.ArrayList;

public class Edge {
	 private City a, b;
	 private double weight;

	public Edge(City a,City b, double weight,ArrayList<Edge> arr) {
		this.a = a;
		this.b = b;
		this.weight= weight;
		arr.add(this);
	}
 
	public City getA() {
		return a;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}



	public City getB() {
		return b;
	}
    
}














