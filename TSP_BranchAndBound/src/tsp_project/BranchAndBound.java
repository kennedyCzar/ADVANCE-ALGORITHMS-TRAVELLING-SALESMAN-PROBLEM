/**
 * 
 */
package tsp_project;

import java.util.ArrayList;
import java.util.List;

/**
 * @author walid
 *
 */
public class BranchAndBound {
	public static int reduction_cost;

	/* This method does the reduction process on the given matrix */
	public int[][] matrixReduction (int[][] dis_matrix) {
		/* Table of minimum values of rows */
		int[] min_rows = new int[Constants.number_of_cities];
		/* Table of minimum values of columns */
		int[] min_columns = new int[Constants.number_of_cities];
		/* Matrix of reduction */
		int[][] matrix = new int[Constants.number_of_cities][Constants.number_of_cities];
		/* Cost of reduction on rows */
		int cost_rows = 0;
		/* Cost of reduction on columns */
		int cost_columns = 0;

		/* Reduction on rows */
		for (int i=0; i < Constants.number_of_cities; i++) {
			int min = Constants.distance_max + 1;
			int index = 0;
			for (int j = 0; j < Constants.number_of_cities; j++) {
				if (dis_matrix[i][j] < min && dis_matrix[i][j] != -1) {
					min = dis_matrix[i][j];
				} else if (dis_matrix[i][j] == -1) {
					index++;
				}
			}
			if (index == Constants.number_of_cities) {
				min_rows[i] = 0;
			} else {
				min_rows[i] = min;
			}
		}
		for (int i=0; i < Constants.number_of_cities; i++) {
			cost_rows += min_rows[i];
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (dis_matrix[i][j] != -1 && min_rows[i] != 0) {
					matrix[i][j] = dis_matrix[i][j] - min_rows[i];
				} else {
					matrix[i][j] = dis_matrix[i][j];
				}
			}
		}
		/* Reduction on columns */
		for (int i=0; i < Constants.number_of_cities; i++) {
			int min = Constants.distance_max + 1;
			int index = 0;
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (matrix[j][i] < min && matrix[j][i] != -1) {
					min = matrix[j][i];
				} else if (matrix[j][i] == -1) {
					min = 0;
				}
			}
			if (index == Constants.number_of_cities) {
				min_columns[i] = 0;
			} else {
				min_columns[i] = min;
			}
		}
		for (int i=0; i < Constants.number_of_cities; i++) {
			cost_columns += min_columns[i];
			for (int j=0; j < Constants.number_of_cities; j++) {
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
		int nodes_nbr = 1 + (Path.numberOfPossiblePaths() / Constants.number_of_cities);
		int tree_level = Constants.number_of_cities - 1;
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
		for (int i=0; i < Constants.number_of_cities; i++) {
			Constants.nodes[i] = Constants.cities[i];
		}
		
		/* Last 2 levels of tree */
		for (int i=Constants.number_of_cities - 1; i > Constants.number_of_cities - 3; i--) {
				for (int j = index + (Path.numberOfPossiblePaths() / Constants.number_of_cities) - 1; j >= index; j--) {
				Constants.nodes[index1] = Constants.paths[j][i];
				index1--;
			}
		}
		/* Remaining levels */
		for (int i=Constants.number_of_cities - 3; i > 1; i--) {
			if (step <= Path.numberOfPossiblePaths() / Constants.number_of_cities) {
				for (int j = index + (Path.numberOfPossiblePaths() / Constants.number_of_cities) - 1; j >= index; j -= step) {
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
		int nb1 = Constants.number_of_cities - 2;
		/* Content to be stored in the table origin */
		int nb2 = 1;
		/* Where to start filling table origin */
		int index2 = Constants.number_of_cities;
		/* To keep track of the number of the nodes at every itteration */
		int index4 = 1;
		while (nb1 > 1) {
			/* Number of nodes depending on the tree level */
			int index3 = index2 - index4; // nb dial les noeuds 3la hsab niveau fl'arbre
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

	/* This method returns the shortest path given a starting point */
	public void branchAndBound () {
		/* We will store the matrices of reduction in an Arraylist of matrices */
		List<int[][]> matrices = new ArrayList<int[][]>(nodesNumber());
		/* Operations will be made on this matrix */
		int[][] m1 = new int[Constants.number_of_cities][Constants.number_of_cities];
		/* Table used to store the visited cities that represent the final path */
		int[] path = new int[nodesNumber()];
		/* Current parent node */
		int current_parent_node = 0;
		/* Used to determine the minimum cost */
		int min_cost = 999999999;
		/* Used to keep track of current minimum cost node */
		int current_min_cost = 0;
		/* Used to keep track of current node with minimum cost */
		int current_min_node = 0;
		/* Used to know if computation is finished or not */
		int end = 0;

		/* Initializing the list of matrices */
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				m1[i][j] = 0;
			}
		}
		for (int i=0; i < nodesNumber(); i++) {
			matrices.add(i, m1);
		}

		/* Initializing visited array (only first city is visited */
		visitedInit();
		
		/* Initializing cost array */
		costInit();

		/* Initializing final visited cities table */
		path[0] = 1;
		for (int i=1; i < nodesNumber(); i++) {
			path[i] = 0;
		}

		/* Computing the first matrix of reduction */
		matrices.add(0, matrixReduction(Constants.dis_matrix));

		/* Initializing cost array */
		Constants.node_cost[0] = reduction_cost;

		/* Computing the remaining matrices of reduction */
		while (end == 0) {
			/* For all children nodes */
			for (int i=1; i < nodesNumber(); i++) {
				if (Constants.origin[i] == current_parent_node) {
					/* m1 = matrices.get(current_parent_node); */
					for (int j=0; j < Constants.number_of_cities; j++) {
						for (int k=0; k < Constants.number_of_cities; k++) {
							m1[j][k] = matrices.get(current_parent_node)[j][k];
						}
					}
					int index1 = 0;
					int index2 = 0;
					for (int j=0; j < Constants.number_of_cities; j++) {
						for (int k=0; k < Constants.number_of_cities; k++) {
							if (Constants.cities[k].equals(Constants.nodes[current_parent_node])) {
								index1 = k;
							}
							if (Constants.cities[k].equals(Constants.nodes[i])) {
								index2 = k;
							}
						}
						m1[index1][j] = -1;
						m1[j][index2] = -1;
						m1[index2][Constants.starting_point] = -1;
					}

					/* Computing the cost of node */
					matrices.add(i, matrixReduction(m1));
					Constants.node_cost[i] = reduction_cost + matrices.get(current_parent_node)[index1][index2] + Constants.node_cost[current_parent_node];
				}
			}
			/* Updating min_cost when leaf is reached */
			/* Testing if the index of current node is an index of a leaf node */
			if (current_parent_node >= nodesNumber() - (Path.numberOfPossiblePaths() / Constants.number_of_cities)) {
				min_cost = Constants.node_cost[current_parent_node];
			}
			
			/* Determine the next parent node */
			current_min_cost = min_cost;
			for (int i=1; i < nodesNumber(); i++) {
				/* The actual node is not a leaf */
				if (min_cost == 999999999) {
					/* Unvisited nodes with known costs */
					if (Constants.visited[i] != 1 && Constants.visited[i] != 2 && Constants.node_cost[i] != -1) {
						if (Constants.node_cost[i] < current_min_cost) {
							current_min_cost = Constants.node_cost[i];
							current_min_node = i;
						}
					}
				}
				/* We have reached a leaf */
				else {
					/* If visited[i]=2 it means that the node i is eliminated */
					if (Constants.visited[i] != 1 && Constants.visited[i] != 2 && Constants.node_cost[i] != -1) {
						/* We eliminate nodes with costs higher than min_cost */
						if (Constants.node_cost[i] > min_cost) {
							Constants.visited[i] = 2;
						} 
						else {
							/* We take the path with the minimum cost between all paths with cost less than min_cost */
							if (Constants.node_cost[i] < current_min_cost) {
								current_min_cost = Constants.node_cost[i];
								current_min_node = i;
							}
						}
					}
				}
			}
			/* We have reached a leaf of minimum cost path */
			if (current_min_cost == Constants.node_cost[current_parent_node] && min_cost != 999999999) {
				break;
			}
			
			min_cost = 999999999;
			/* Updating visited nodes table */
			Constants.visited[current_min_node] = 1;
			path[current_min_node] = 1;
			
			/* Updating final path table */
			/* If the parent of the min cost node sound is not the current parent node */
			if (Constants.origin[current_min_node] != current_parent_node) {
				/* We set the table cells to 0 except for the first one which represents the root of tree */
				for (int j=1; j < nodesNumber(); j++) {
					path[j] = 0;
				}
				path[current_min_node] = 1;
				
				/* We mark all the parent of the current min node as visited */
				int index = Constants.origin[current_min_node];
				while (index > 0) {
					path[index] = 1;
					index = Constants.origin[index];
				}
			}
			
			/* Setting next parent node as minimum cost current node */
			current_parent_node = current_min_node;
		}
		
		/* Printing the shortest path */
		System.out.println("The shortest path that starts from city " + Constants.nodes[0].getName() + " :");
		for (int i=0; i < nodesNumber(); i++) {
			if (path[i] == 1) {
				System.out.print(Constants.nodes[i].getName() + " ");
			}
		}
		System.out.println(Constants.nodes[0].getName());
		System.out.println("The path length is : " + Constants.node_cost[current_min_node]);
	}
}
