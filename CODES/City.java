package bruteforceTSP;

import java.util.ArrayList;

public class City {

	private String name;
	private char label; // to differentiate between source and other cities
	 public boolean isVisited = false;

	public City(String name, char label,ArrayList<City> arr) {
		this.name = name;
		this.label = label;
		arr.add(this);

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getLabel() {
		return label;
	}
	public void setLabel(char label) {
		this.label = label;
	}
	
	public boolean isVisited() {
	
		return isVisited;
	}
	public String toString() {
		return this.getName();
	}
	
	
}











