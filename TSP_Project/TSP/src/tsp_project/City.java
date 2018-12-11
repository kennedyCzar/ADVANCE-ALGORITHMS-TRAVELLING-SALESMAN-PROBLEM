/**
 * 
 */
package tsp_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author walid
 *
 */
public class City {
	static int number_of_cities = numberOfCities();
	private static Scanner sc;
	static int database = Constants.use_database;
	private String name;
	public boolean isVisited = false;
	
	public City (String name) {
		this.name = name;
	}
	
	/* This method returns the name of a city */
	public String getName () {
		return this.name;
	}
	
	/* This method generates a random value for distance between cities */
	public int distance (City city) {
		return (int) ((Math.random() * ((Constants.distance_max - Constants.distance_min) + 1)) + Constants.distance_min);
	}
	
	/* This method returns the number of cities */
	public static int numberOfCities () {
		int nb = 0;
		if (database == 1) {
			try {
				File f = new File(Constants.database);
				sc = new Scanner(f);
				
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] details = line.split("\\s+");
					nb = details.length;
				}
			}
			catch (FileNotFoundException e) {
				System.out.println("The file was not found");
				database = 0;
	            e.printStackTrace();
			}
		}
		else {
			nb = Constants.number_of_cities;
		}
		return nb;
	}
}
