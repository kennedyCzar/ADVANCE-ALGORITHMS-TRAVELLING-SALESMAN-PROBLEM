package bruteforceTSP;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteForceTSP {
 public static void main(String[] args) {

  List<String> myparts;
  int k = 1;
  int optimalPath_index =0;
  String RealPath =""; // path converted from labels to real names
  String cities ="" ;
  int Nb_cities ;
  List < String > results = new ArrayList < String > (); // table des permutations (n! permutation in total)
  ArrayList < Edge > edges = new ArrayList < > ();
  ArrayList < City > citiesArray = new ArrayList < > ();
  ArrayList<Double> RecordCosts = new ArrayList<Double> ();


  BruteForceTSP permutation = new BruteForceTSP();
  double cost = 0.0; // at each permutation
  double minimal_cost=99999;
  City a = new City("Lyon", 'a',citiesArray);
  City b = new City("Paris", 'b',citiesArray);
  City c = new City("Marseille", 'c',citiesArray);
  City d = new City("casa",'d',citiesArray);
  City e = new City("Lille", 'e',citiesArray);
  City f = new City("Le-Havre", 'f',citiesArray);
  City g = new City("Larache", 'g',citiesArray);
  City h = new City("Tangier",'h',citiesArray);
  
  for (int i = 0; i < citiesArray.size(); i++) {
	  cities += citiesArray.get(i).getLabel();
}
   Nb_cities = cities.length();
  // all possible  routes (edges)
  Edge e1  = new Edge(a,b,22,edges);
  Edge e2  = new Edge(a,c,13,edges);
  Edge e3  = new Edge(a,d,51,edges);
  Edge e13 = new Edge(a,e,2,edges);
  Edge e14 = new Edge(a,f,3,edges);
  Edge e15 = new Edge(a,g,5,edges);
  Edge e16 = new Edge(a,h,9,edges);
  
  Edge e4  = new Edge(b,a,49,edges);
  Edge e5  = new Edge(b,c,44,edges);
  Edge e6  = new Edge(b,d,26,edges);
  Edge e17 = new Edge(b,e,4,edges);
  Edge e18 = new Edge(b,f,6,edges);
  Edge e19 = new Edge(b,g,2,edges);
  Edge e20 = new Edge(b,h,1,edges);
  
  Edge e7  = new Edge(c,a,12,edges);
  Edge e8  = new Edge(c,b,11,edges);
  Edge e9  = new Edge(c,d,32,edges);
  Edge e21 = new Edge(c,e,31,edges);
  Edge e22 = new Edge(c,f,22,edges);
  Edge e23 = new Edge(c,g,74,edges);
  Edge e24 = new Edge(c,h,48,edges);
  
  Edge e10 = new Edge(d,a,24,edges);
  Edge e11 = new Edge(d,b,54,edges);
  Edge e12 = new Edge(d,c,87,edges);
  Edge e25 = new Edge(d,e,44,edges);
  Edge e26 = new Edge(d,f,65,edges);
  Edge e27 = new Edge(d,g,2.36,edges);
  Edge e28 = new Edge(d,h,51,edges);
  /*
  Edge e29 = new Edge(e,a,4,edges);
  Edge e30 = new Edge(e,b,6,edges);
  Edge e31 = new Edge(e,c,22,edges);
  Edge e32 = new Edge(e,d,41,edges);
  Edge e33 = new Edge(e,f,47,edges);
  Edge e34 = new Edge(e,g,62,edges);
  Edge e35 = new Edge(e,h,45,edges);
  
  Edge e36 = new Edge(f,a,2.8,edges);
  Edge e37 = new Edge(f,b,4,edges);
  Edge e38 = new Edge(f,c,456,edges);
  Edge e39 = new Edge(f,d,72,edges);
  Edge e40 = new Edge(f,e,51,edges);
  Edge e41 = new Edge(f,g,554,edges);
  Edge e42 = new Edge(f,h,6,edges);
  
  Edge e43 = new Edge(g,a,1, edges);
  Edge e44 = new Edge(g,b,4, edges);
  Edge e45 = new Edge(g,c,6, edges);
  Edge e46 = new Edge(g,d,52,edges);
  Edge e47 = new Edge(g,e,71,edges);
  Edge e48 = new Edge(g,f,44,edges);
  Edge e49 = new Edge(g,h,26,edges);
  
  Edge e50 = new Edge(h,a,41,edges);
  Edge e51 = new Edge(h,b,4, edges);
  Edge e52 = new Edge(h,c,16,edges);
  Edge e53 = new Edge(h,d,24,edges);
  Edge e54 = new Edge(h,e,15,edges);
  Edge e55 = new Edge(h,f,63,edges);
  Edge e56 = new Edge(h,g,14,edges);*/
  
      double begin = System.currentTimeMillis();

   permutation.permute(cities, 0, Nb_cities - 1, results);

  System.out.println("possible paths: " + results);
  
   myparts = DevidePath(results);
  
  for (int i = 0; i < myparts.size(); i++) {
   for (int j = 0; j < edges.size(); j++) {

    if (myparts.get(i).toString().equals(edges.get(j).getA().getLabel() + "" + edges.get(j).getB().getLabel())) {
     cost += edges.get(j).getWeight();
     //System.out.println("edge : "+myparts.get(i)+", weight ->"+edges.get(j).getWeight());

    }
   }
   
   if (iterate(i, k, Nb_cities, results.size()) == 1) {
	   RecordCosts.add(cost);
       cost = 0.0; // after computing the cost of each path, turn cost to 0
   }
   

  }
  System.out.println("costs for each path :"+ RecordCosts);
  // printing the minimal cost
  for (int i = 0; i < RecordCosts.size()-1; i++) {
		  if ((Double)RecordCosts.get(i) < minimal_cost ) {
			  optimalPath_index = i ;
			  minimal_cost = (Double)RecordCosts.get(i);
		  }
	
}

	  for (int i = 0; i < results.get(optimalPath_index).length(); i++) {
		  for (int j = 0; j < citiesArray.size(); j++) {
			if (results.get(optimalPath_index).charAt(i) == citiesArray.get(j).getLabel()) {
				RealPath+= " "+citiesArray.get(j).getName();
			}
		 }
		  
	   }
	  System.out.println("the minimal cost to reach your destination is : "+ minimal_cost);
	  System.out.println("the optimal path is : "+ RealPath);
	  double end = System.currentTimeMillis();
	  	System.out.print("the time taken in (s):  ");
	  	System.out.print((end-begin)/1000);	  	System.out.print("s");

 }

 
 


 
 
 
 /*  iterate function used to
  *  iterate on the values of i
  *  we record and re-initialise the cost the cost 
  *  whenever i is in an index multiplying nb_cities ( number of cities )
  */
 public static int iterate(int i, int k, int Nb_cities, int results_size) {
  int OnOff;
  if (i > 1 && i + 1 == (Nb_cities) * k) {
   OnOff = 1;
  } else {
   OnOff = 0; // doesn't work using normal recursion => manual recursion -_-
   do {
    k++;
    if (i > 1 && i + 1 == ((Nb_cities) * k)) {
     OnOff = 1;
    }
   } while (k != results_size);
  }
  return OnOff;

 }


 /* function to devide paths 
  * for example : the result of applying the function 
  * to a path as 'abdca' would be: [ab,bd,dc,ca]
  */
 
 public static List < String > DevidePath(List < String > results) {
  List < String > saveParts = new ArrayList < String > ();
  for (String string: results) {
   for (int i = 0; i < string.length() - 1; i++) {
    String str = string.charAt(i) + "" + string.charAt(i + 1);
    saveParts.add(str);
   }
  }
  return saveParts;
 }

 /* 
  * permutation function: gives all possible permutation for a given string
  * cities string to calculate permutation for 
  * l starting index 
  * r end index 
  */
 private int permute(String cities, int l, int r, List < String > results) {

  if (l == r) {
   if (cities.charAt(0) == 'a') {
    results.add(cities + 'a');
   }
  } else {
   for (int i = l; i <= r; i++) {
    cities = swap(cities, l, i);
    permute(cities, l + 1, r, results);
    cities = swap(cities, l, i);

   }
  }
  return 0;
 }

 /* 
  * Swap Characters at position : swap positions
  *  a string value 
  *  i position 1 
  *  j position 2 
  * return swapped string 
  */
 public String swap(String a, int i, int j) {
  char temp;
  char[] charArray = a.toCharArray();
  temp = charArray[i];
  charArray[i] = charArray[j];
  charArray[j] = temp;
  return String.valueOf(charArray);
 }

}