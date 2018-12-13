/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class AddAndRemoveEdges {
	double path_length = 0;
	
	/* This method computes the lower bound */
	public static double lowerBound (int[][] matrix) {
		/* Matrix to work on */
		int[][] m = new int[Main.number_of_cities][Main.number_of_cities];
		double lower_bound = 0;
		int index = 0;
		
		/* Copying data of given matrix to the new matrix */
		for (int i=0; i < Main.number_of_cities; i++) {
			for (int j=0; j < Main.number_of_cities; j++) {
				m[i][j] = matrix[i][j];
			}
		}
		
		/* We store in this array the minimum distance of every row of the matrix */
		for (int i=0; i < Main.number_of_cities; i++) {
			int min = 999999999;
			for (int j=0; j < Main.number_of_cities; j++) {
				/* If an edge has been added (-2 in the matrix) we should consider it in computing the lower bound */
				if (m[i][j] == -2) {
					min = Constants.dis_matrix[i][j];
					index = j;
					break;
				}
				if (m[i][j] < min && m[i][j] != -1 && m[i][j] != -2) {
					min = m[i][j];
					index = j;
				}
			}
			m[i][index] = -1;
			lower_bound += min;
		}
		
		/* Second minimum distance of every row */
		for (int i=0; i < Main.number_of_cities; i++) {
			int min = 999999999;
			for (int j=0; j < Main.number_of_cities; j++) {
				if (m[i][j] == -2) {
					min = Constants.dis_matrix[i][j];
					break;
				}
				if (m[i][j] < min && m[i][j] != -1 && m[i][j] != -2) {
					min = m[i][j];
				}
			}
			lower_bound += min;
		}
		return (lower_bound / 2);
	}

	/* This method returns the number of edges */
	public static int edgesNumber () {
		return ((Main.number_of_cities - 1) * Main.number_of_cities);
	}

	/* This method initializes the table of final edges */
	public void finalEdgesInit () {
		for (int i=0; i < Main.number_of_cities; i++) {
			for (int j=0; j < Main.number_of_cities; j++) {
				Constants.final_edges[i][j] = -1;
			}
		}
	}

	/* This method returns the shortest path given a starting point */
	public void addAndRemoveEdges () {
		int starting_vertex = 0;
		/* Number of added edges */
		int nb_added;
		/* Number of removed edges */
		int nb_removed;
		int end = 0;
		/* Matrix from which we will compute the lower bounds */
		int[][] matrix = new int[Main.number_of_cities][Main.number_of_cities];
		
		/* Initializing the matrix of final edges */
		finalEdgesInit();
		
		/* We repeat the operation until visiting all edges */
		while (end == 0) {
			/* We keep going for every starting vertex until we remove or add 2 edges */
			int i = starting_vertex + 1;
			while (i < Main.number_of_cities) {
				/* We remove every edge that has been added or removed from the matrix */
				for (int j=0; j < Main.number_of_cities; j++) {
					for (int k=0; k < Main.number_of_cities; k++) {
						matrix[j][k] = Constants.dis_matrix[j][k];
						/* If edge is already added or removed */
						if (Constants.final_edges[j][k] == 1) {
							matrix[j][k] = -2;
						}
						if (Constants.final_edges[j][k] == 2) {
							matrix[j][k] = -1;
						}
					}
				}
				
				nb_added = 0;
				nb_removed = 0;
				for (int j=0; j < Main.number_of_cities; j++) {
					if (Constants.final_edges[starting_vertex][j] != -1) {
						if (Constants.final_edges[starting_vertex][j] == 1) {
							nb_added++;
						}
						if (Constants.final_edges[starting_vertex][j] == 2) {
							nb_removed++;
						}
					}
				}
				
				int nb_added1 = 0;
				int nb_removed1 = 0;
				for (int j=0; j < Main.number_of_cities; j++) {
					if (Constants.final_edges[i][j] != -1) {
						if (Constants.final_edges[i][j] == 1) {
							nb_added1++;
						}
						if (Constants.final_edges[i][j] == 2) {
							nb_removed1++;
						}
					}
				}
				
				int nb_added2 = 0;
				int nb_removed2 = 0;
				for (int j=0; j < Main.number_of_cities; j++) {
					if (Constants.final_edges[j][i] != -1) {
						if (Constants.final_edges[j][i] == 1) {
							nb_added2++;
						}
						if (Constants.final_edges[j][i] == 2) {
							nb_removed2++;
						}
					}
				}
				
				double lb1 = -1;
				double lb2 = -1;
				
				if (nb_added < 2 && nb_added1 < 2 && nb_added2 < 2 && matrix[starting_vertex][i] != -1 && i != starting_vertex)	{
					if (nb_removed < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed1 < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed2 < (edgesNumber() / Main.number_of_cities) - 2 && i != starting_vertex && Constants.final_edges[starting_vertex][i] != -2) {
						int index = -1;
						int nb=0;
						if (starting_vertex > 0) {
							for (int j=starting_vertex + 1; j < Main.number_of_cities; j++) {
								for (int k=0; k < Main.number_of_cities; k++) {
									if (Constants.final_edges[j][k] == 1) {
										nb++;
									}
								}
								if (nb == 0) {
									index = j;
									break;
								}
							}
						}
						if (index == -1) {
							/* Add current edge from matrix to compute the lower bound */
							matrix[starting_vertex][i] = -2;
							/* Since we have a symmetric matrix we add also the equivalent edge */
							matrix[i][starting_vertex] = -2;
						}
						else {
							while (index < Main.number_of_cities) {
								int nb_added3 = 0;
								for (int j=0; j < Main.number_of_cities; j++) {
									if (Constants.final_edges[j][index] != -1) {
										if (Constants.final_edges[j][index] == 1) {
											nb_added3++;
										}
									}
								}
								if (nb_added3 < 2) {
									/* Add current edge from matrix to compute the lower bound */
									matrix[starting_vertex][index] = -2;
									/* Since we have a symmetric matrix we add also the equivalent edge */
									matrix[index][starting_vertex] = -2;
									break;
								}
								else {
									index++;
								}
							}
						}
						/* Computing the lower bound before removing the edge */
						lb1 = lowerBound(matrix);
					}
					
					
				}
				
				
					
					
				if (nb_removed < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed1 < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed2 < (edgesNumber() / Main.number_of_cities) - 2 && i != starting_vertex && Constants.final_edges[starting_vertex][i] != -2) {
					if (nb_added < 2 && nb_added1 < 2 && nb_added2 < 2 && matrix[starting_vertex][i] != -1 && i != starting_vertex)	{			
						/* Remove current edge from matrix to compute the lower bound*/
						matrix[starting_vertex][i] = -1;
						/* Since we have a symmetric matrix we remove also the equivalent edge */
						matrix[i][starting_vertex] = -1;
						/* Computing the lower bound after removing the edge */
						lb2 = lowerBound(matrix);
					}
				}
				
				/* Test if we should add the edge or remove it */
				if (i != starting_vertex && (lb1 != -1 || lb2 != -1)) {
					if (lb1 <= lb2) {
						if (lb1 > lowerBound(Constants.dis_matrix) && lb1 != -1 && nb_added < 2 && nb_added1 < 2 && nb_added2 < 2) {
							Constants.final_edges[starting_vertex][i] = 1;
							Constants.final_edges[i][starting_vertex] = 1;
						}
						else if (lb2 > lowerBound(Constants.dis_matrix) && lb2 != -1 && nb_removed < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed1 < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed2 < (edgesNumber() / Main.number_of_cities) - 2 && i != starting_vertex && Constants.final_edges[starting_vertex][i] != -2) {
							Constants.final_edges[starting_vertex][i] = 2;
							Constants.final_edges[i][starting_vertex] = 2;
						}
					}
					else if (lb2 <= lb1) {
						if (lb2 > lowerBound(Constants.dis_matrix) && lb2 != -1 && nb_removed < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed1 < (edgesNumber() / Main.number_of_cities) - 2 && nb_removed2 < (edgesNumber() / Main.number_of_cities) - 2 && i != starting_vertex && Constants.final_edges[starting_vertex][i] != -2) {
							Constants.final_edges[starting_vertex][i] = 2;
							Constants.final_edges[i][starting_vertex] = 2;
						}
						else if (lb1 > lowerBound(Constants.dis_matrix) && lb1 != -1 && nb_added < 2 && nb_added1 < 2 && nb_added2 < 2) {
							Constants.final_edges[starting_vertex][i] = 1;
							Constants.final_edges[i][starting_vertex] = 1;
						}
					}
				}
				
				nb_added = 0;
				nb_removed = 0;
				for (int j=0; j < Main.number_of_cities; j++) {
					if (Constants.final_edges[starting_vertex][j] != -1) {
						if (Constants.final_edges[starting_vertex][j] == 1) {
							nb_added++;
						}
						if (Constants.final_edges[starting_vertex][j] == 2) {
							nb_removed++;
						}
					}
				}
				
				i++;
				if ((nb_added == 2 && nb_removed != (edgesNumber() / Main.number_of_cities) - 2) || (nb_added != 2 && nb_removed == (edgesNumber() / Main.number_of_cities) - 2)) {
					if (nb_added == 2 && nb_removed != (edgesNumber() / Main.number_of_cities) - 2) {
						for (int j=0; j < Main.number_of_cities; j++) {
							if (Constants.final_edges[starting_vertex][j] != 1 && Constants.final_edges[j][starting_vertex] != 1 && starting_vertex != j) {
								Constants.final_edges[starting_vertex][j] = 2;
								Constants.final_edges[j][starting_vertex] = 2;
							}
						}
					}
					else if (nb_added != 2 && nb_removed == (edgesNumber() / Main.number_of_cities) - 2) {
						for (int j=0; j < Main.number_of_cities; j++) {
							if (Constants.final_edges[starting_vertex][j] != 2 && Constants.final_edges[j][starting_vertex] != 2 && starting_vertex != j) {
								Constants.final_edges[starting_vertex][j] = 1;
								Constants.final_edges[j][starting_vertex] = 1;
							}
						}
					}
				}
				
				nb_added = 0;
				nb_removed = 0;
				for (int j=0; j < Main.number_of_cities; j++) {
					if (Constants.final_edges[starting_vertex][j] != -1) {
						if (Constants.final_edges[starting_vertex][j] == 1) {
							nb_added++;
						}
						if (Constants.final_edges[starting_vertex][j] == 2) {
							nb_removed++;
						}
					}
				}
				
				if (nb_added == 2 && nb_removed == (edgesNumber() / Main.number_of_cities) - 2) {
					int[] indexes = new int[2];
					int k = 0;
					for (int j=0; j < Main.number_of_cities; j++) {
						if (Constants.final_edges[starting_vertex][j] == 1) {
							indexes[k] = j;
							k++;
						}
					}
					Constants.final_edges[indexes[0]][indexes[1]] = 2;
					Constants.final_edges[indexes[1]][indexes[0]] = 2;
					
					
					starting_vertex++;
					i = starting_vertex + 1;
				}
				if (starting_vertex == Main.number_of_cities - 1) {
					path_length = lowerBound(matrix);
				}
				
				if (starting_vertex >= Main.number_of_cities - 1) {
					end = 1;
					break;
				}
			}
		}
		
		int[][] edges = new int[2][Main.number_of_cities];
		int index = 0;
		for (int i=0; i < Main.number_of_cities; i++) {
			for (int j=0; j < Main.number_of_cities; j++) {
				if (Constants.final_edges[i][j] == 1) {
					edges[0][index] = i;
					edges[1][index] = j;
					index++;
					Constants.final_edges[j][i] = -1;
				}
			}
		}
		System.out.println("The shortest path is : ");
		System.out.print("C" + edges[0][0] + " C" + edges[1][0]);
		int current_vertex = edges[1][0];
		edges[0][0] = -1;
		edges[1][0] = -1;
		for (int j=0; j < 2; j++) {
			for (int i=0; i < Main.number_of_cities; i++) {
				if (edges[j][i] != -1) {
					if (edges[j][i] == current_vertex) {
						if (j == 0) {
							System.out.print(" C" + edges[1][i]);
							current_vertex = edges[1][i];
							edges[j][i] = -1;
							edges[1][i] = -1;
							i = 0;
							j = 0;
						}
						else if (j == 1) {
							System.out.print(" C" + edges[0][i]);
							current_vertex = edges[0][i];
							edges[j][i] = -1;
							edges[0][i] = -1;
							i = 0;
							j = 0;
						}
					}
				}
			}
		}
		System.out.println();
		System.out.println("Length of path : " + path_length);
	}
}
