package ANT;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class ANT {
	static int UNIQUE_ID = 0;
	int uid = ++UNIQUE_ID; 
	public double cost = 0; 
	
	public Point2D current_node;
//	public Point2D[] taboo_list = new Point2D[29];
	public ArrayList <Integer> taboo_list = new ArrayList <Integer>();
//	public ArrayList <Integer>solution = new ArrayList<Integer>(Arrays.asList(1, 28, 6, 12, 9, 26, 3, 29, 5, 21, 2, 20, 10, 4, 15, 18, 14, 17, 22, 11, 19, 25, 7, 23, 8, 27, 16, 13, 24));
	public ArrayList <Integer>solution = new ArrayList<Integer>();
	public ant(Point2D point2d) {
		current_node = point2d; 
	}

	public int hashCode(){
		return uid; 
	}
	
}
