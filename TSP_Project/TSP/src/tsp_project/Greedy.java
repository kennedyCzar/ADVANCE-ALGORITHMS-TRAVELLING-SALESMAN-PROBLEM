package tsp_project;

import java.util.ArrayList;

public class Greedy {

	public Greedy() {}
	public void GreedyAlgorithm (City[][] paths, int starting_point) {
		int min = 99999999;
		int indexOfLastVisitedCity = -1 ;
		int cost = 0 ;
		int storing_sp = starting_point;
		ArrayList<String> path = new ArrayList<>();
		if (starting_point >= 0 && starting_point <= Constants.number_of_cities - 1) {
			// add the starting point to array of path
			path.add(Constants.cities[starting_point].getName());
			Constants.cities[starting_point].isVisited=true;
			
			for (int i=0; i < Constants.number_of_cities; i++) { 
				if (Constants.dis_matrix[starting_point][i]<= min 
						&& Constants.dis_matrix[starting_point][i] !=-1
						&& Constants.cities[i].isVisited == false) {
					min = Constants.dis_matrix[starting_point][i];
					indexOfLastVisitedCity = i;
				}	 
			}
			
			Constants.cities[indexOfLastVisitedCity].isVisited=true;
			path.add(Constants.cities[indexOfLastVisitedCity].getName());

			cost += Constants.dis_matrix[starting_point][indexOfLastVisitedCity];
			// iteration part 
			do {
			min = 9999999;
			ArrayList<Integer> remainingCitiesIndexes = new ArrayList<Integer>();

			for (int i = 0; i < Constants.number_of_cities; i++) {
				
				if (Constants.cities[i].isVisited == false && i != indexOfLastVisitedCity) {
					remainingCitiesIndexes.add(i);	
				}
			}
			
			starting_point = indexOfLastVisitedCity;
			
				for (int j = 0; j < remainingCitiesIndexes.size(); j++) {
					if (Constants.dis_matrix[starting_point][remainingCitiesIndexes.get(j)]<= min 
							&& Constants.cities[remainingCitiesIndexes.get(j)].isVisited== false) {
						min = Constants.dis_matrix[starting_point][remainingCitiesIndexes.get(j)];
						indexOfLastVisitedCity = remainingCitiesIndexes.get(j);
					}	
				}
			Constants.cities[indexOfLastVisitedCity].isVisited=true;
			path.add(Constants.cities[indexOfLastVisitedCity].getName());
			cost += Constants.dis_matrix[starting_point][indexOfLastVisitedCity];

			} while (path.size() != Constants.number_of_cities);
			
			path.add(path.get(0)); // add the source City to complete the cycle after visiting all other cities
			cost += Constants.dis_matrix[indexOfLastVisitedCity][storing_sp];

			// print the results
			
			System.out.println("The result path that starts from city C" + Constants.starting_point + " : ");
			for (int j = 0; j < path.size(); j++) {
				System.out.print(path.get(j) + " ");
			}
			System.out.println();
			System.out.println("The path length is : " + cost);
			
		}
		
	}
}
