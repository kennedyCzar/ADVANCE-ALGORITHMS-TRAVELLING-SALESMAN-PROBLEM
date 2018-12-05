package ACO;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class ACO {
	public static Random ran = new Random();
	public static Point2D[] coordinates;
	public static double alpha = 1;
	public static double beta = 1; 
	public static double[][] phermone;
	public static double update_factor = 0.96; 
	public static ArrayList <Integer>global_solution = new ArrayList<Integer>();
	public static double global_best_cost =  999999999; 
	public static ArrayList <Integer>iteration_best_solution = new ArrayList<Integer>();

	public static double xxxxxxxxxiteration_best_cost = 999999999;

	public static double uuuuuuuuuuuuuuuuuuuiteration_best_cost = 999999999;

	
	
	public static void main(String[] args) {
    // input the cordinates of the city here
		double[] x_coord = new double[]{1150,630,40,750,750,1030,1650,1490,790,710,840,1170,
				970,510,750,1280,230,460,1040,590,830,490,1840,1260,1280,
				490,1460,1260,360};
		double[] y_coord = new double[]{1760,1660,2090,1100,2030,2070,650,1630,2260,1310,550,
				2300,1340,700,900,1200,590,860,950,1390,1770,500,1240,1500,
				790,2130,1420,1910,1980}; 
		
		//Point2D distance returns the Euclidean distance, it is built into JAVA. 
		coordinates = new Point2D[29];
		for(int i = 0; i< 29; i++){
				coordinates[i] = new Point2D.Double(x_coord[i], y_coord[i]);
//				System.out.println(coordinates[i]);
		}
		
		//Initializations
		//Phermone table, Phermone[0][1] gives you the Phermone value between city #0 and city #1.
		phermone = new double [29][29];
		int num_ants = 30;
		double initial_phermone_level = 100;
		int num_iterations = 10;
		
		//Initialize phermone levels to be same everywhere
		for(int i = 0; i<29; i++){
			for(int j = 0; j<29; j++){
				if(i == j){
					phermone[i][j] = 0;
				}else{
					phermone[i][j] = initial_phermone_level;
				}
			}
		}
		
		while(num_iterations != 0){
			for(int i = 0; i< num_ants; i++){
				int index = ran.nextInt(29);
				ANT temp_ant = new ANT(coordinates[index]);
				generate_solution(temp_ant, index);

				//Evaporation of phermone
				evaporatePhermone();
				
				if(temp_ant.cost< iteration_best_cost){
					iteration_best_cost = temp_ant.cost;
					iteration_best_solution = temp_ant.solution;
				}
				System.out.println("Iteration best cost: " + temp_ant.cost);
				System.out.println("Iteration best solution: " + temp_ant.solution);
			}
			num_iterations--;
		}
		
		if(iteration_best_cost<global_best_cost){
			global_best_cost = iteration_best_cost;
			global_solution = iteration_best_solution;
		}
		
		System.out.println("Global best cost: " + global_best_cost);
		System.out.println("Global best solution: " + global_solution);

	}
		

	private static void evaporatePhermone() {
		
		for (int i = 0; i<29; i++){
			for(int j = 0; j<29;j++){
				phermone[i][j] = (phermone [i][j])*update_factor;
			}
		}
	}

	private static void generate_solution(ANT temp_ant, int index) {
		double best_prob = 0.0;
		double t;
		double d;
		double prob; 
		double total = 0;
		int best_city = 0;
		boolean cities_left_to_visit = false;
		
		temp_ant.taboo_list.add(index);
		temp_ant.solution.add(index+1);
	
		
		while(temp_ant.solution.size() != 29){
			for(int i = 0; i< 29; i++){
				if(is_taboo(i, temp_ant)){
					continue;
				}else{
					d = temp_ant.current_node.distance(coordinates[i]);
					t = phermone[index][i];
					double tot = t/d;
					total = total + tot;
				}
			}
			
			for(int i = 0; i< 29; i++){
				if(is_taboo(i, temp_ant)){
					continue;
				}else{
					cities_left_to_visit = true; 
					d = temp_ant.current_node.distance(coordinates[i]);
					t = phermone[index][i];
					double temp_prob = ((t/d)/total);
					if(temp_prob>best_prob){
						best_city = i;
						best_prob = temp_prob;
					}
				}
			}
			
			if(cities_left_to_visit){
				//Visit the city found
				
				if(ran.nextDouble() > best_prob){
					temp_ant.solution.add(best_city+1);
					temp_ant.current_node = coordinates[best_city];
					temp_ant.taboo_list.add(best_city);
				}else{
					while(true){
						int rando = ran.nextInt(29);
						if(is_taboo(rando, temp_ant )){
							continue;
						}else{
							temp_ant.solution.add(rando+1);
							temp_ant.current_node = coordinates[rando];
							temp_ant.taboo_list.add(rando);
							break;
						}
					}
				}
				best_prob =0;
				best_city = 0;
				cities_left_to_visit = false;
			}
		}
		//Online Delayed Update Rule
		update(temp_ant);
		
		//Evaporation of phermone
		evaporatePhermone();

	}

	private static void update(ANT temp_ant) {
		for(int i = 0; i< temp_ant.solution.size(); i++){
			if(i != (temp_ant.solution.size()-1)){
				temp_ant.cost = temp_ant.cost + coordinates[temp_ant.solution.get(i)-1].distance(coordinates[temp_ant.solution.get(i+1)-1]);
				double delta = (1200/temp_ant.cost);
				phermone[i][i+1] = phermone[i][i+1] + delta;  
			}else{
				temp_ant.cost = temp_ant.cost + coordinates[temp_ant.solution.get(i)-1].distance(coordinates[temp_ant.solution.get(0)-1]);
				double delta = (1200/temp_ant.cost);
				phermone[i][0] = phermone[i][0] + delta;
			}
		}
		
	}

	private static boolean is_taboo(int j, ANT temp_ant) {
		for (int i = 0; i < temp_ant.taboo_list.size(); i++){
			if(j == temp_ant.taboo_list.get(i)){
				return true;
			}
		}
		return false; 
	}
	
}
