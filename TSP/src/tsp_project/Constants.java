/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public interface Constants {
	static final int number_of_cities = 10;
	static final int distance_min = 50;
	static final int distance_max = 500;
	static final int starting_point = 0;
	static final int use_database = 0;
	
	/* Database file */
	public static final String database = "src/database.txt";
	
	/* This array is used to store the final edges */
	public static int[][] final_edges = new int[Main.number_of_cities][Main.number_of_cities];
	
	/* We store in this matrix the cities left to accomplish the path */
	public static int[] visited = new int[BranchAndBound.nodesNumber()];
	/*  We store in this matrix the cost of each node */
	public static int[] node_cost = new int[BranchAndBound.nodesNumber()];
	/* We store in this array the tree nodes (cities) */
	public static City[] nodes = new City[BranchAndBound.nodesNumber()];
	/* We store in this array the parent nodes of nodes in the array node_cost */
	public static int[] origin = new int[nodes.length];
	
	/* Array of cities */
	public static City[] cities = new City[Main.number_of_cities];
	/* Matrix of distances between cities */
	public static int[][] dis_matrix = new int[Main.number_of_cities][Main.number_of_cities];
	/* Arrays of candidate paths */
	public static String[] candidate_paths = new String[Path.numberOfPossiblePaths()];
	public static City[][] paths = new City[Path.numberOfPossiblePaths()][Main.number_of_cities + 1];
}
