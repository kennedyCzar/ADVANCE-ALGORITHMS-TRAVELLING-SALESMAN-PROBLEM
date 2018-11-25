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
public class AddRemoveEdges {
	public static int reduction_cost;
	
	/* This method does the reduction process on the given matrix */
	public int[][]matrixReduction (int[][] dis_matrix) {
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
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (dis_matrix[i][j] < min && dis_matrix[i][j] != -1) {
					min = dis_matrix[i][j];
				}
				else if (dis_matrix[i][j] == -1) {
					index++;
				}
			}
			if (index == Constants.number_of_cities) {
				min_rows[i] = 0;
			}
			else {
				min_rows[i] = min;
			}
		}
		for (int i=0; i < Constants.number_of_cities; i++) {
			cost_rows += min_rows[i];
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (dis_matrix[i][j] != -1 && min_rows[i] != 0) {
					matrix[i][j] = dis_matrix[i][j] - min_rows[i];
				}
				else {
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
				}
				else if (matrix[j][i] == -1) {
					min = 0;
				}
			}
			if (index == Constants.number_of_cities) {
				min_columns[i] = 0;
			}
			else {
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
		if (Constants.number_of_cities == 3) {
			return 5;
		}
		int nodes_nbr = (Constants.number_of_cities - 1) * (Constants.number_of_cities - 2);
		
		for (int i=Constants.number_of_cities - 3; i > 1; i--) {
			nodes_nbr += nodes_nbr * i;
		}
		nodes_nbr += Constants.number_of_cities + (Path.numberOfPossiblePaths() / Constants.number_of_cities);
		return nodes_nbr;
	}
	
	/* This method return the number of parent nodes (except the first one) */
	public static int parentNodesNumber () {
		int parent_nodes_nbr = nodesNumber() - (Path.numberOfPossiblePaths() / Constants.number_of_cities) - 1;
		return parent_nodes_nbr;
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
	
	/* This method initializes the nodes of the tree */
	public void initializeNodes () {
		for (int i=0; i < Constants.number_of_cities; i++) {
			Constants.nodes[i] = Constants.cities[i];
			Constants.origin[i] = 0;
		}
		/* Determine the cities that start with starting point */
		int index = 0;
		for (int i=0; i < Path.numberOfPossiblePaths(); i++) {
			if (Constants.cities[Constants.starting_point].equals(Constants.paths[i][0])) {
				index = i;
				break;
			}
			else {
				continue;
			}
		}
		int k = Constants.number_of_cities;
		for (int i=2; i < Constants.number_of_cities; i++) {
			for (int j=index; j < index + (Path.numberOfPossiblePaths() / Constants.number_of_cities) && k < Constants.nodes.length; j++) {
				Constants.nodes[k] = Constants.paths[j][i];
				k++;
			}
		}
		/* Origin of the nodes */
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
	public void addRemoveEdges () {
		/* We will store the matrices of reduction in an Arraylist of matrices */
		List<int[][]> matrices = new ArrayList<int[][]>(nodesNumber());
		/* Operations will be made on these matrices */
		int[][] m1 = new int[Constants.number_of_cities][Constants.number_of_cities];
		/* Table to store the final path */
		City[] final_path = new City[Constants.number_of_cities + 1];
		/* Current parent node */
		int current_parent_node = 0;
		/* Used to determine the minimum cost */
		int min_cost = 999999999;
		/* Used to keep track of current node with minimum cost */
		int current_min_node = 0;
		/* Number of level of the tree */
		int levels = Constants.number_of_cities - 1;
		
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
		
		/* Computing the first matrix of reduction */
		matrices.add(0, matrixReduction(Constants.dis_matrix));
		
		/* Initializing cost array */
		Constants.node_cost[0] = reduction_cost;
		
		/* Computing the remaining matrices of reduction */
		while (levels != 0) {
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
					
					matrices.add(i, matrixReduction(m1));
					Constants.node_cost[i] = reduction_cost + matrices.get(current_parent_node)[index1][index2] + Constants.node_cost[current_parent_node];
					if (Constants.node_cost[i] < min_cost) {
						min_cost = Constants.node_cost[i];
						current_min_node = i;
					}
				}
			}
			current_parent_node = current_min_node;
			Constants.visited[current_min_node] = 1;
			min_cost = 999999999;
			levels--;
		}
		
		/* Printing the shortest path */
		System.out.println("The shortest path that starts from city " + Constants.nodes[0].getName() + " :");
		for (int i=0; i < nodesNumber(); i++) {
			if (Constants.visited[i] == 1) {
				System.out.print(Constants.nodes[i].getName());
			}
		}
		System.out.println(Constants.nodes[0].getName());
		System.out.println("The path length is : " + Constants.node_cost[current_min_node]);
	}
}
