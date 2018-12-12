package tsp_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author walid
 *
 */
public class City {
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

}