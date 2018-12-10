package tsp_project;
//A Java program for Prim's Minimum Spanning Tree (MST) algorithm. 
//The program is for adjacency matrix representation of the graph 

import java.util.*; 

class MinimunSpanningTree 
{ 
	// Number of vertices in the graph 
	private static final int V =  Constants.number_of_cities; 

	// A utility function to find the vertex with minimum key 
	// value, from the set of vertices not yet included in MST 
	int minKey(int key[], Boolean mstSet[]) 
	{ 
		// Initialize min value 
		int min = Integer.MAX_VALUE, min_index=-1; 

		for (int v = 0; v < V; v++) 
			if (mstSet[v] == false && key[v] < min) 
			{ 
				min = key[v]; 
				min_index = v; 
			} 

		return min_index; 
	} 

	// A utility function to print the constructed MST stored in 
	// parent[] 
	void printMST(int parent[], int n, int graph[][]) 
	{ 
		System.out.println("Edges \tWeights"); 
		for (int i = 1; i < V; i++) 
			System.out.println(Constants.cities[parent[i]].getName()+" - "+ Constants.cities[i].getName()+"\t"+ 
							graph[parent[i]][i]); 
	} 

	// Function to construct and print MST for a graph represented 
	// using adjacency matrix representation 
	void primMST(int graph[][], int starting_point) 
	{ 
		// Array to store constructed MST 
		int parent[] = new int[V]; 

		// Key values used to pick minimum weight edge in cut 
		int key[] = new int [V]; 

		// To represent set of vertices not yet included in MST 
		Boolean mstSet[] = new Boolean[V]; 

		// Initialize all keys as INFINITE 
		for (int i = 0; i < V; i++) 
		{ 
			key[i] = Integer.MAX_VALUE; 
			mstSet[i] = false; 
		} 

		// Always include first 1st vertex in MST. 
		key[0] = 0;	 // Make key 0 so that this vertex is 
						// picked as first vertex 
		parent[starting_point] = -1; // First node is always root of MST 

		// The MST will have V vertices 
		for (int count = 0; count < V-1; count++) 
		{ 
			// Pick the minimum key vertex from the set of vertices 
			// not yet included in MST 
			int u = minKey(key, mstSet); 

			// Add the picked vertex to the MST Set 
			mstSet[u] = true; 

			// Update key value and parent index of the adjacent 
			// vertices of the picked vertex. Consider only those 
			// vertices which are not yet included in MST 
			for (int v = 0; v < V; v++) 

				// graph[u][v] is non zero only for adjacent vertices of m 
				// mstSet[v] is false for vertices not yet included in MST 
				// Update the key only if graph[u][v] is smaller than key[v] 
				if (graph[u][v]!=0 && mstSet[v] == false && 
					graph[u][v] < key[v]) 
				{ 
					parent[v] = u; 
					key[v] = graph[u][v]; 
				} 
		} 

		// print the constructed MST 
		printMST(parent, V, graph); 
		ArrayList<String> arrOfPath = new ArrayList<>();
		ArrayList<String> paTh = new ArrayList<>();
		ArrayList<Integer> weights = new ArrayList<>();
		int cost = 0 ;

		for (int i = 1; i < parent.length; i++) {
			arrOfPath.add(Constants.cities[parent[i]].getName());
			arrOfPath.add(Constants.cities[i].getName());

		}
		for (int i = 1; i < parent.length; i++) {
			weights.add(graph[parent[i]][i]);
		}
		
		for (int i = 0; i < arrOfPath.size(); i++) {
			if (arrOfPath.get(i) == Constants.cities[starting_point].getName()) {
				paTh.add(arrOfPath.get(i));
				paTh.add(arrOfPath.get(i+1));
				//System.out.println("weight "+weights.get((int)i/2).intValue());
				cost += weights.get((int)i/2).intValue();
				break;
			}
			
		}
		for (int i = 0; i < arrOfPath.size(); i++) {
				if (!paTh.contains(arrOfPath.get(i))) {
					paTh.add(arrOfPath.get(i));
					
					for (int j = 0; j < Constants.number_of_cities; j++) {
						for (int j2 = 0; j2 < mstSet.length; j2++) {
							if (paTh.get(paTh.size()-2) == Constants.cities[j].getName() 
									&& paTh.get(paTh.size()-1) == Constants.cities[j2].getName() ) {
								cost += Constants.dis_matrix[j][j2]; 

							}
						}
						
					}
				}
			
		}
		paTh.add(Constants.cities[starting_point].getName());
		for (int i = 0; i < Constants.number_of_cities; i++) {
			if (Constants.cities[i].getName() == paTh.get(paTh.size()-1)) {
				cost +=  Constants.dis_matrix[i][starting_point];

			}
		}
		System.out.println();
		System.out.println("The path is (may be not the optimal one) : "+paTh);
		System.out.println("The cost of this path is : "+cost);
	} 

} 
