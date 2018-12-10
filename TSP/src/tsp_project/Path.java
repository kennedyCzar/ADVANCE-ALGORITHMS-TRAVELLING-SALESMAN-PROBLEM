/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class Path {
	/* Array of candidate paths */
	private City[] path = new City[Constants.number_of_cities + 1];
	/* Counter needed for permutations */
	static int counter = 0;
	
	public City[] getPath () {
		return this.path;
	}
	
	public void setPath (City[] path) {
		this.path = path;
	}
	
	/* Initialization of the array with the first combination of cities */
	public City[] initialPath () {
		for (int i=0; i < Constants.number_of_cities; i++) {
			this.path[i] = Constants.cities[i];
		}
		this.path[Constants.number_of_cities] = Constants.cities[0];
		return this.path;
	}
	
	/* This method converts a path to a string of indexes of cities */
	public String convertPathToString (City[] cities) {
		String path_string = "";
		
		for (int i=0; i < cities.length; i++) {
			path_string += Integer.toString(i);
		}
		return path_string;
	}
	
	/* This method converts a string to a path */
	public City[][] convertStringToPath (String[] path) {
		for (int i=0; i < numberOfPossiblePaths(); i++) {
			for (int j=0; j < Constants.number_of_cities + 1; j++) {
				Constants.paths[i][j] = Constants.cities[Character.getNumericValue(path[i].charAt(j))];
			}
		}
		return Constants.paths;
	}
	
	/* This method returns the number of possible paths */
	public static int numberOfPossiblePaths () {
		int number_of_possible_paths = 1;
		for(int i=1; i <= Constants.number_of_cities; i++) {    
		      number_of_possible_paths = number_of_possible_paths * i;
		}
		return number_of_possible_paths;
	}
	
	/* These methods return all the possible permutations of the given cities */
	public void cityPermutation(String str) { 
	    permutation("", str); 
	}

	private void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0) {
	    	if (counter < numberOfPossiblePaths()) {
	    		prefix += prefix.substring(0, 1);
	    		Constants.candidate_paths[counter] = prefix;
	    		counter++;
	    	}
	    	else {
	    		counter = 0;
	    	}
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
}
