/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class BruteForce {
	public static int distance_min = City.numberOfCities() * Constants.distance_max;
	
	/* This method returns the length of a given path */
	public int pathLength (City[] path) {
		int length = 0;
		int[] tab_of_indexes = new int[path.length];
		for (int i=0; i < path.length; i++) {
			for (int j=0; j < City.numberOfCities(); j++) {
				if (path[i].equals(Constants.cities[j])) {
					tab_of_indexes[i] = j;
				}
				else {
					continue;
				}
			}
		}
		for (int i=0; i < City.numberOfCities(); i++) {
			length += Constants.dis_matrix[tab_of_indexes[i]][tab_of_indexes[i+1]];
		}
		return length;
	}
	
	/* This method returns the shortest path (Brute-Force algorithm) */
	public void bruteForce (City[][] paths) {
		if ((Constants.starting_point >= 0) && (Constants.starting_point <= City.numberOfCities() - 1)) {
			/* Paths counter */
			int index = 1;
			City[] path = new City[City.numberOfCities() + 1];
			City[] final_path = new City[City.numberOfCities() + 1];
			for (int i=0; i < Path.numberOfPossiblePaths(); i++) {
				/* Test to compute only the paths starting with the city : starting_point (index of city) */
				if (Constants.cities[Constants.starting_point].equals(paths[i][0])) {
					System.out.print("Path " + index + " : ");
					for (int j=0; j < City.numberOfCities() + 1; j++) {
						path[j] = paths[i][j];
						System.out.print(path[j].getName() + " ");
					}
					System.out.println();
					System.out.println("Length of path " + index + " : " + pathLength(path));
					index++;
					
					if (pathLength(path) < distance_min) {
						distance_min = pathLength(path);
						for (int j=0; j < City.numberOfCities() + 1; j++) {
							final_path[j] = path[j];
						}
					}
					System.out.println();
				}
			}
			System.out.println("The shortest path that starts from city " + path[0].getName() + " :");
			for (int i=0; i < City.numberOfCities() + 1; i++) {
				System.out.print(final_path[i].getName() + " ");
			}
			System.out.println();
			System.out.println("The path length is : " + distance_min);
		}
		else {
			System.out.println("The starting city you gave does not exist");
		}
	}
}
