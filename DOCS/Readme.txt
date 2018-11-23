BruteForce Algorithm for Solvign TSP
-----------------------------------
Traverses therough the cities from top to bottom recursively
checking all permutations/combinations of paths.
complexity is O(n!)

Since we are solving for a Hamiltonian circuit, 
we need to end each recursive call at the node we started with 

Greedy Algorithm for solving TSP
-------------------------------------
traverse through the cities by starting from the edges with the 
minimum distance and continuing from top down and back to the final point.
Complexity: O(n^2)

Dynamic programming for solving TSP
-------------------------------------
Traverses through the tree from bottom up.
Using Karp algorithm the complexity is O(n^2 * 2^n)
A form of backtracking.