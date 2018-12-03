/**
 * 
 */
package tsp_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.stream.events.StartDocument;

/**
 * @author walid
 *
 */
public class Randomized {
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
	public void randomized () {
		/* Current parent node */
		int current_parent_node = 0;
		int path_cost = 0;
		
		/* Initializing visited array (only first city is visited */
		visitedInit();
		
		int end = 0;
		do {
			/* List to store the child nodes of current parent nodes */
			List<Integer> child_nodes = new ArrayList<Integer>();
			/* Counting the number of child nodes of current parent node */
			for (int i=1; i < nodesNumber(); i++) {
				if (Constants.origin[i] == current_parent_node) {
					child_nodes.add(i);
				}
			}
			/* Picking randomly a child node from the table */
			int random = new Random().nextInt(child_nodes.size());
			
			/* Transforming nodes to cities to compute path length */
			int city1 = 0;
			int city2 = 0;
			for (int i=0; i < Constants.number_of_cities; i++) {
				if (Constants.cities[i] == Constants.nodes[current_parent_node]) {
					city1 = i;
				}
				if (Constants.cities[i] == Constants.nodes[child_nodes.get(random)]) {
					city2 = i;
				}
			}
			path_cost += Constants.dis_matrix[city1][city2];
			Constants.visited[child_nodes.get(random)] = 1;
			current_parent_node = child_nodes.get(random);
			
			/* If we reach the leaf of the tree computation is done */
			if (current_parent_node >= nodesNumber() - (Path.numberOfPossiblePaths() / Constants.number_of_cities)) {
				end = 1;
			}
		}
		while (end != 1);
		
		/* Printing the shortest path */
		System.out.println("The randomized path that starts from city " + Constants.nodes[0].getName() + " :");
		for (int i=0; i < nodesNumber(); i++) {
			if (Constants.visited[i] == 1) {
				System.out.print(Constants.nodes[i].getName());
			}
		}
		System.out.println(Constants.nodes[0].getName());
		System.out.println("The path length is : " + path_cost);
	}
}
