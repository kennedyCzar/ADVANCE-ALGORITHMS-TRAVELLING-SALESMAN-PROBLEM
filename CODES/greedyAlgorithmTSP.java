package bruteforceTSP;
import java.util.ArrayList;

public class greedyAlgorithmTSP {

	public static void main(String[] args) {
		  
		
		
		  double min = 999999; 
		  int indexOfMinimalEdge = 0;
		  double cost = 0.0;
		  ArrayList < City > Path = new ArrayList <City> ();
		  ArrayList < Edge > edges = new ArrayList <Edge> ();
		  ArrayList < City > citiesArray = new ArrayList <City> ();
		  ArrayList < Edge > ArrayOfEdgesFromCurrentCity = new ArrayList<Edge>() ;

		  City a = new City("Lyon", 'a',citiesArray);
		  City b = new City("Paris", 'b',citiesArray);
		  City c = new City("Marseille", 'c',citiesArray);
		  City d = new City("casa",'d',citiesArray);
		 
		  // supposing that the weight of X -> Y is not same as Y -> X
		  // so we should have as input: possible edges + weights of each edge
		  
		  Edge e1  = new Edge(a,b,7,edges);
		  Edge e2  = new Edge(a,c,13,edges);
		  Edge e3  = new Edge(a,d,51,edges);
		  
		  Edge e4  = new Edge(b,a,2,edges);
		  Edge e5  = new Edge(b,c,12,edges);
		  Edge e6  = new Edge(b,d,6,edges);
	
		  Edge e7  = new Edge(c,a,12,edges);
		  Edge e8  = new Edge(c,b,43,edges);
		  Edge e9  = new Edge(c,d,31,edges);
		
		  Edge e10 = new Edge(d,a,24,edges);
		  Edge e11 = new Edge(d,b,54,edges);
		  Edge e12 = new Edge(d,c,87,edges);
	      double begin = System.currentTimeMillis();

		  // the salesman begin from 'a' so it is implicitly visited
		  a.isVisited  = true;
		  Path.add(a);
		  for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getA().getLabel()== 'a') {
				ArrayOfEdgesFromCurrentCity.add(edges.get(i));
			}
		}
		  //System.out.println((ArrayOfEdgesFromCurrentCity.toString())); 
		  
		  // delete all the edges with destination city 'B' is visited
		  for (int i = 0; i < ArrayOfEdgesFromCurrentCity.size(); i++) {
				if (ArrayOfEdgesFromCurrentCity.get(i).getB().isVisited == true) {
					ArrayOfEdgesFromCurrentCity.remove(i);
					}
			}

		  // select and store the edge having the minimal weight
		  for (int i = 0; i <ArrayOfEdgesFromCurrentCity.size(); i++) {
			if (ArrayOfEdgesFromCurrentCity.get(i).getWeight() <= min) {
				indexOfMinimalEdge = i;
				min = ArrayOfEdgesFromCurrentCity.get(i).getWeight();
			}
		}
		  cost += min ;
		  ArrayOfEdgesFromCurrentCity.get(indexOfMinimalEdge).getB().isVisited = true;
		  Path.add(ArrayOfEdgesFromCurrentCity.get(indexOfMinimalEdge).getB());
		  // iterate 
		  iterate(Path,edges,citiesArray,cost);
		    double end = System.currentTimeMillis();
		  	System.out.print("the time taken in (s):  "); 
		  	System.out.print((end-begin)/1000);	  	System.out.print("s");// calculate and display the time taken
	
	}
	
	public static void iterate(ArrayList<City> Path,ArrayList<Edge> edges,ArrayList<City> cities,double minimal_cost) {
		do {
	    ArrayList<Edge> arrCities = new ArrayList<Edge>();
		double min = 999999;
		int indexOfMinimalEdge = 0;
		// step 1
		 for (int i = 0; i < edges.size(); i++) {
				if (edges.get(i).getA().getLabel() == Path.get(Path.size()-1).getLabel()) {
					arrCities.add(edges.get(i));
				}
			}
		 // step 2
		 for (int i = 0; i < arrCities.size(); i++) {
				if (arrCities.get(i).getB().isVisited == true) {
					arrCities.remove(i);
				/*	System.out.println("removed / visited A: "+ arrCities.get(i).getA().getLabel());
					System.out.println("removed / visited B: "+ arrCities.get(i).getB().getLabel());*/

					}
				//System.out.println("cities not visited"+ arrCities.toString());
			}
		 // step 3
		 for (int i = 0; i <arrCities.size(); i++) {
				if (arrCities.get(i).getWeight() <= min && arrCities.get(i).getB().isVisited != true) {
					indexOfMinimalEdge = i;
					min = arrCities.get(i).getWeight();
				}
			}
		      minimal_cost += min;
			  Path.add(arrCities.get(indexOfMinimalEdge).getB());
			  arrCities.get(indexOfMinimalEdge).getB().isVisited = true;
			  //System.out.println("ssss"+Path.toString());
			  
		} while (Path.size() != cities.size());

		   Path.add(Path.get(0)); // add the source City to complete the cycle after visiting all other cities
		  System.out.println("the minimal cost to reach your destination is : "+ minimal_cost);
		  System.out.println("the optimal path is : "+ Path.toString());
	}
}
