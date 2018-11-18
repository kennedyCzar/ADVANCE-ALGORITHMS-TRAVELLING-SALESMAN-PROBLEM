/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public interface Constants {
	static final int number_of_cities = 3;
	static final int distance_min = 50;
	static final int distance_max = 500;
	
	/* Array of cities */
	public static City[] cities = new City[number_of_cities];
	/* Matrix of distances between cities */
	public static int[][] dis_matrix = new int[(int) Math.pow(number_of_cities, 2)][(int) Math.pow(number_of_cities, 2)];
	/* Arrays of candidate paths */
	public static String[] candidate_paths = new String[Path.numberOfPossiblePaths()];
	public static City[][] paths = new City[Path.numberOfPossiblePaths()][Constants.number_of_cities + 1];
}
