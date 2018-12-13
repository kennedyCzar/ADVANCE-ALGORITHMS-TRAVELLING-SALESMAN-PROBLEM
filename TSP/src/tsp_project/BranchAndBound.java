/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class BranchAndBound {
	public static int reduction_cost;
	public static int distance_min = Constants.number_of_cities * Constants.distance_max;

	/* This method does the reduction process on the given matrix */
	public int[][] matrixReduction (int[][] dis_matrix) {
		/* Table of minimum values of rows */
		int[] min_rows = new int[Main.number_of_cities];
		/* Table of minimum values of columns */
		int[] min_columns = new int[Main.number_of_cities];
		/* Matrix of reduction */
		int[][] matrix = new int[Main.number_of_cities][Main.number_of_cities];
		/* Cost of reduction on rows */
		int cost_rows = 0;
		/* Cost of reduction on columns */
		int cost_columns = 0;

		/* Reduction on rows */
		for (int i=0; i < Main.number_of_cities; i++) {
			int min = Constants.distance_max + 1;
			int index = 0;
			for (int j = 0; j < Main.number_of_cities; j++) {
				if (dis_matrix[i][j] < min && dis_matrix[i][j] != -1) {
					min = dis_matrix[i][j];
				} else if (dis_matrix[i][j] == -1) {
					index++;
				}
			}
			if (index == Main.number_of_cities) {
				min_rows[i] = 0;
			} else {
				min_rows[i] = min;
			}
		}
		for (int i=0; i < Main.number_of_cities; i++) {
			cost_rows += min_rows[i];
			for (int j=0; j < Main.number_of_cities; j++) {
				if (dis_matrix[i][j] != -1 && min_rows[i] != 0) {
					matrix[i][j] = dis_matrix[i][j] - min_rows[i];
				} else {
					matrix[i][j] = dis_matrix[i][j];
				}
			}
		}
		/* Reduction on columns */
		for (int i=0; i < Main.number_of_cities; i++) {
			int min = Constants.distance_max + 1;
			int index = 0;
			for (int j=0; j < Main.number_of_cities; j++) {
				if (matrix[j][i] < min && matrix[j][i] != -1) {
					min = matrix[j][i];
				} else if (matrix[j][i] == -1) {
					min = 0;
				}
			}
			if (index == Main.number_of_cities) {
				min_columns[i] = 0;
			} else {
				min_columns[i] = min;
			}
		}
		for (int i=0; i < Main.number_of_cities; i++) {
			cost_columns += min_columns[i];
			for (int j=0; j < Main.number_of_cities; j++) {
				if (matrix[j][i] != -1 && min_columns[i] != 0) {
					matrix[j][i] -= min_columns[i];
				}
			}
		}
		/* Cost of reduction */
		reduction_cost = cost_rows + cost_columns;
		return matrix;
	}

	/* This method returns the number of nodes */
	public static int nodesNumber () {
		int nodes_nbr = 1 + (Path.numberOfPossiblePaths() / Main.number_of_cities);
		int tree_level = Main.number_of_cities - 1;
		int val = 1;
		
		while (tree_level > 1) {
			val = val * tree_level;
			nodes_nbr += val;
			tree_level--;
		}
		return nodes_nbr;
	}

	/* This method initializes the table of visited nodes */
	public void visitedInit () {
		for (int i=0; i < nodesNumber(); i++) {
			/* Cities which are not yet visited */
			if (i != Constants.starting_point) {
				Constants.visited[i] = -1;
			}
			/* Cities which are already visited */
			else {
				Constants.visited[i] = 1;
			}
		}
	}
	
	/* This method initializes the table of nodes costs */
	public void costInit () {
		for (int i=0; i < nodesNumber(); i++) {
			Constants.node_cost[i] = -1;
		}
	}
	
	/* This method initializes the nodes of the tree */
	public void initializeNodes () {
		/* Determine the cities that start with starting point */
		int index = 0;
		for (int i=0; i < Path.numberOfPossiblePaths(); i++) {
			if (Constants.cities[Constants.starting_point].equals(Constants.paths[i][0])) {
				index = i;
				break;
			} else {
				continue;
			}
		}
		
		int index1 = nodesNumber() - 1;
		int step = 2;
		int step1 = 1;
		/* First 2 levels of tree */
		for (int i=0; i < Main.number_of_cities; i++) {
			Constants.nodes[i] = Constants.cities[i];
		}
		
		/* Last 2 levels of tree */
		for (int i=Main.number_of_cities - 1; i > Main.number_of_cities - 3; i--) {
				for (int j = index + (Path.numberOfPossiblePaths() / Main.number_of_cities) - 1; j >= index; j--) {
				Constants.nodes[index1] = Constants.paths[j][i];
				index1--;
			}
		}
		/* Remaining levels */
		for (int i=Main.number_of_cities - 3; i > 1; i--) {
			if (step <= Path.numberOfPossiblePaths() / Main.number_of_cities) {
				for (int j = index + (Path.numberOfPossiblePaths() / Main.number_of_cities) - 1; j >= index; j -= step) {
					Constants.nodes[index1] = Constants.paths[j][i];
					index1--;
				}
			}
			else {
				break;
			}
			step1++;
			step *= step1 + 1;
		}
		
		/* Filling the table origin (nodes parents) */
		/* Number of children of a node */
		int nb1 = Main.number_of_cities - 2;
		/* Content to be stored in the table origin */
		int nb2 = 1;
		/* Where to start filling table origin */
		int index2 = Main.number_of_cities;
		/* To keep track of the number of the nodes at every itteration */
		int index4 = 1;
		while (nb1 > 1) {
			/* Number of nodes depending on the tree level */
			int index3 = index2 - index4;
			/* Value of last number of nodes */
			index4 = index2;
			while (index3 > 0) {
				int i;
				for (i=index2; i < index2 + nb1; i++) {
					Constants.origin[i] = nb2;
				}
				nb2++;
				index3--;
				index2 = i;
			}
			nb1--;
		}
		if (nb1 == 1) {
			for (int i=index2; i < Constants.origin.length; i++) {
				Constants.origin[i] = nb2;
				nb2++;
			}
		}
	}
	
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
				System.out.print(path[i].getName() + " ");
			}
			System.out.println();
			System.out.println("The path length is : " + distance_min);
		}
		else {
			System.out.println("The starting city you gave does not exist");
		}
	}
}
