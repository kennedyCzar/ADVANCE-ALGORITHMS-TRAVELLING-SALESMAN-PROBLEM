package bruteforceTSP;

import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteForceTSP {
 public static void main(String[] args) {
  String cities = "abc";
  int Nb_cities = cities.length();

  List < String > results = new ArrayList < String > (); // table des permutations (n! permuts)
  BruteForceTSP permutation = new BruteForceTSP();
  double cost = 0.0; // at each permutation
  double minimal_cost=0.0	;
  ArrayList RecordCosts = new ArrayList<>();
  City a = new City("Lyon", 'a');
  City b = new City("Paris", 'b');
  City c = new City("marseille", 'c');
  // City d = new City("casa",'d');

  // all possible  routes (edges)
  Edge e1 = new Edge(a, b, 2);
  Edge e2 = new Edge(a, c, 3);
  //  Edge e3  = new Edge(a,d,5);
  Edge e4 = new Edge(b, a, 9);
  Edge e5 = new Edge(b, c, 4);
  //  Edge e6  = new Edge(b,d,6);
  Edge e7 = new Edge(c, a, 2);
  Edge e8 = new Edge(c, b, 1);
  // Edge e9  = new Edge(c,d,3);
  // Edge e10 = new Edge(d,a,2);
  // Edge e11 = new Edge(d,b,4);
  // Edge e12 = new Edge(d,c,8);



  ArrayList < Edge > edges = new ArrayList < > ();
  //ArrayList ListOfPaths = new ArrayList<>();
  List < Double > distances = new ArrayList < Double > ();

  permutation.permute(cities, 0, Nb_cities - 1, results);

  //  System.out.println(ListOfPaths.get(0));
  //  System.out.println(ListOfPaths.get(0).toString().charAt(strLength));
  System.out.println("possible paths: " + results);
  edges.addAll(Arrays.asList(e1, e2, e4, e5, e7, e8)); //ajouter les edges a une arralyist
  List myparts = DevidePath(results);
  int k = 1;
  for (int i = 0; i < myparts.size(); i++) {
   for (int j = 0; j < edges.size(); j++) {

    if (myparts.get(i).toString().equals(edges.get(j).getA().getLabel() + "" + edges.get(j).getB().getLabel())) {
     cost += edges.get(j).getWeight();
     //System.out.println("edge : "+myparts.get(i)+", weight ->"+edges.get(j).getWeight());

    }


   }
   if (iterate(i, k, Nb_cities, results.size()) == 1) {
	   RecordCosts.add(cost);
       cost = 0.0;
   }
   

  }
  // printing the minimal cost
  for (int i = 0; i < RecordCosts.size()-1; i++) {
	  if ((Double)RecordCosts.get(i) < (Double)RecordCosts.get(i+1)) {
     minimal_cost = (Double)RecordCosts.get(i);
	  }
}
  System.out.println("the minimal cost to reach your destination is : "+ minimal_cost);
 

  
  
  
  
  
  
  
  
  
  // deviding parts manually
  /*System.out.println(results.get(0).charAt(0)+""+results.get(0).charAt(1));
  System.out.println(results.get(0).charAt(1)+""+results.get(0).charAt(2));
  System.out.println(results.get(0).charAt(2)+""+results.get(0).charAt(3));*/
  
  //deviding parts with function
  //System.out.println("possible paths devided to 2: " + DevidePath(results));


  //System.out.println(distances);
 }

 //iterate 
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


 // function to devide paths
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

 /** 
  * permutation function 
  * @param cities string to calculate permutation for 
  * @param l starting index 
  * @param r end index 
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

 /** 
  * Swap Characters at position 
  * @param a string value 
  * @param i position 1 
  * @param j position 2 
  * @return swapped string 
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