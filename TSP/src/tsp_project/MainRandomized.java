/**
 * 
 */
package tsp_project;

/**
 * @author walid
 *
 */
public class MainRandomized {
	public static void main (String[] args) {
		/* Setting the array of cities by giving each city a name */
		int nb = 0;
		for(int i=0; i < Constants.number_of_cities; i++) {
			String city_name = "C" + nb;
			nb++;
			Constants.cities[i] = new City(city_name);
		}
		
		/* Distances matrix initialization */
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				Constants.dis_matrix[i][j] = -1;
			}
		}
		
		/* Setting the matrix of distances by giving random distances between cities */
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				if (i == j) {
					continue;
				}
				else if (Constants.dis_matrix[i][j] != -1) {
					continue;
				}
				/* Symetric matrix of distances */
//				else {
//					Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
//					Constants.dis_matrix[j][i] = Constants.dis_matrix[i][j];
//				}
				/* Non symmetric matrix of distances */
				else {
					Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
				}
			}
		}
		
		/* Printing the matrix of distances */
		System.out.println("Matrix representing the cities and the distances between them :");
		String str = "|\t";
		String str2 = "\t";
		for(int k=0; k < Constants.number_of_cities; k++) {
			str2 += " " + Constants.cities[k].getName() + "\t";
		}
		System.out.println(str2);
		
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
		        str += Constants.dis_matrix[i][j] + "\t";
			}
			System.out.print(Constants.cities[i].getName());
			System.out.println(str + "|");
	        str = "|\t";
		}
		System.out.println();
		
		/* Computing the candidate paths */
		Path path = new Path();
		path.cityPermutation(path.convertPathToString(Constants.cities));
		path.convertStringToPath(Constants.candidate_paths);
		
		/* Branch and Bound Algo */
		Randomized a = new Randomized();
		a.initializeNodes();
		a.randomized();
	}
}
