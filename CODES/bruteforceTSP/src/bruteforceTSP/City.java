package bruteforceTSP;

public class City {

	private String name;
	private char label; // to differentiate between source and other cities
	private double weight;
	public City(String name, char label) {
		this.name = name;
		this.label = label;

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
	
	/*
	public void setWeight_from_to(City city,double weight) {
		city.weight = city.weight-this.weight;
	};
	
	
	public double getWeight_from_to(City city) { // return weight between From_city and To_city
		return city.weight - this.weight ;
	};*/
}











