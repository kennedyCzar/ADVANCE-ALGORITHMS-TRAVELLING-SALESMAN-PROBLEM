/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *	
 */
public class City {
	private char name;
	
	public City (char name) {
		this.name = name;
	}
	
	/* This method returns the name of a city */
	public char getName () {
		return this.name;
	}
	
	/* This method generates a random value for distance between cities */
	public int distance (City city) {
		int dis = (int) ((Math.random() * ((Constants.distance_max - Constants.distance_min) + 1)) + Constants.distance_min);
		return dis;
	}
}
