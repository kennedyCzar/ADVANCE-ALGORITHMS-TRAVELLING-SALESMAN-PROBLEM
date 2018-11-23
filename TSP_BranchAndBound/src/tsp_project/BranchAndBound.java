/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class BranchAndBound {
	public static int distance_min = Constants.number_of_cities * Constants.distance_max;
	
	/* This method returns the length of a given path */
	public int pathLength (City[] path) {
		int length = 0;
		int[] tab_of_indexes = new int[path.length];
		for (int i=0; i < path.length; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (path[i].equals(Constants.cities[j])) {
					tab_of_indexes[i] = j;
				}
				else {
					continue;
				}
			}
		}
		for (int i=0; i < Constants.number_of_cities; i++) {
			length += Constants.dis_matrix[tab_of_indexes[i]][tab_of_indexes[i+1]];
		}
		return length;
	}
	
	/* This method computes the length of a path in comparison with the length of a given path */
	public int pathLengthCompare (int distance_min, City[] currentPath) {
		int length = 0;
		int[] tab_of_indexes = new int[currentPath.length];
		for (int i=0; i < currentPath.length; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (currentPath[i].equals(Constants.cities[j])) {
					tab_of_indexes[i] = j;
				}
				else {
					continue;
				}
			}
		}
		for (int i=0; i < Constants.number_of_cities; i++) {
			length += Constants.dis_matrix[tab_of_indexes[i]][tab_of_indexes[i+1]];
			if (length >= distance_min ) {
				return i;
			}
		}
		return -1;
	}
	
	/* This method returns the shortest path (Branch And Bound algorithm) */
	public void branchAndBound (City[][] paths) {
		if ((Constants.starting_point >= 0) && (Constants.starting_point <= Constants.number_of_cities - 1)) {
			/* Table to store the shortest path */
			City[] path = new City[Constants.number_of_cities + 1];
			/* Matrix to store only the paths starting with the starting point */
			City[][] cities = new City[Path.numberOfPossiblePaths() / Constants.number_of_cities][Constants.number_of_cities + 1];
			for (int i=0; i < Path.numberOfPossiblePaths() / Constants.number_of_cities; i++) {
				/* Test to compute only the paths starting with the city : starting_point (index of city) */
				if (Constants.cities[Constants.starting_point].equals(paths[i][0])) {
					for (int j=0; j < Constants.number_of_cities + 1; j++) {
						cities[i][j] = paths[i][j];
					}
				}
			}
			/* Initialization of the first path as the optimal one */
			for (int i=0; i < Constants.number_of_cities + 1; i++) {
				path[i] = cities[0][i];
			}
			if (pathLength(path) < distance_min) {
				distance_min = pathLength(path);
			}
			/* Backtracking */
			City[] path1 = new City[Constants.number_of_cities + 1];
			for (int i=1; i < Path.numberOfPossiblePaths() / Constants.number_of_cities; i++) {
				for (int j=0; j < Constants.number_of_cities + 1; j++) {
					path1[j] = cities[i][j];
				}
				if (pathLengthCompare(distance_min, path1) != -1) {
					if (pathLengthCompare(distance_min, path1) < Constants.number_of_cities - 2) {
						int index = 0;
						for (int k=i+1; k < Path.numberOfPossiblePaths() / Constants.number_of_cities; k++) {
							City[] path2 = new City[Constants.number_of_cities + 1];
							for (int j=0; j < Constants.number_of_cities + 1; j++) {
								path2[j] = cities[k][j];
							}
							if (path2[pathLengthCompare(distance_min, path1)].equals(path1[pathLengthCompare(distance_min, path1)])) {
								index++;
								continue;
							}
							else {
								break;
							}
						}
						i += index;
					}
				}
				else {
					for (int j=0; j < Constants.number_of_cities + 1; j++) {
						path[j] = path1[j];
					}
					distance_min = pathLength(path);
				}
			}
			System.out.println("The shortest path that starts from city " + path[0].getName() + " :");
			for (int i=0; i < Constants.number_of_cities + 1; i++) {
				System.out.print(path[i].getName());
			}
			System.out.println();
			System.out.println("The path length is : " + distance_min);
		}
		else {
			System.out.println("The starting city you gave does not exist");
		}
	}
}
