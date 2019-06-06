# MetaHeuristics 4IR project

## Authors 
- Laura BOUZEREAU
- Constance GAY

## Files
- **Instances_Int** folder contains instances of graphs to solve (.full format)
- **Solutions** folder contains the solutions our program generates (.text format)
- **Evacuation** folder contains our solution
- **Sujet** file contains the context and specifications of the project
- **Report** file contains our report on how we tried to solve the project

## Functions implementing the specifications

### Data Format
Our way of representing a graph is stored in **Evacuation/src/graph**. There you can find the objects *Graph*,*Arc* and *Node*.

### Reading instances and reading/writing solutions
- The object *Graph* in **Evacuation/src/graph** contains a method **generate_from_file(path)** to generate a graph from a .full file whose path is provided. The method **show_graph()** that will print all of the characteristics of a graph. There is also a method called **show_all_nodes()** that will print a list of all the nodes of the graph along with their characteristics if they are evacuation nodes.

- The object *Solution* in **Evacuation/src/solution** contains a method **generate_solution_from_file(path)** to generate a graph from a .txt file whose path is provided. It also contains a method **print_solution()** that will print all of the characteristics of a graph. The method **write_file_solution()** writes a solution into a .txt file.

**_Test :_** Running the file *Test_Graph_Generator* in **Evacuation/src/solution** will print all of the graphs from the files in **Instances_Int** along with the number of them whom ran into a problem during their extraction. 

### Solution Checker
The static method **check_solution(solution,graph)** in the objet *Checker* in **Evacuation/src/solution** returns an object *Checker_message* from the same package. *Checker_message* is made up of three things: the validity of the solution, the reason for non-validity and a list of evacuation nodes causing the problem.

**_Test :_** Running the file *Test_Checker* in **Evacuation/src/solution** will check all of the solutions from the files in **Solutions**  and print their filename and validity and if they are not valid the reason for that will be printed.

### Local Search

- **_Bornes:_** The object *Bornes* in **Evacuation/src/solution**. Its constructor takes a filename and a path and uses the static methods **generate_infimum** and **generate_maximum** generate the Solutions it gives its attributes **infimum** and **maximum**.

- **_Neighborhoods:_** We made three neighborhood generation functions. Both of them are in the object *Solution* in **Evacuation/src/solution**
  - **Random neighborhood generation:** the function **generateNeighborhoodRandom(...)** generates a neighborhood of a certain size but randomly changing the rates and start_evac_dates of the evac nodes.
  - **Neighborhood changing rates:** the function **generate_Neighborhood_RATE(...)** generates a neighborhood by lowering the evacuation rates some evac nodes (chosen randomly) more and more.
  - **Neighborhood changing dates of start:** the function **generate_Neighborhood_DATE(...)** generates a neighborhood by lowering the start_evac_date some evac nodes (chosen randomly) more and more.

- **_Local Searches:_** We made three local search functions. Both of them are in the object *Solution* in **Evacuation/src/solution**
  - **Random local search:** the function **recherche_locale_sans_diversification()** takes a solution and generates neighborhoods using **generateNeighborhoodRandom(...)** with a constant delta. It then compares the date of the end of evacuation of all those neighborhoods and takes the lowest one (putting priority on valid solutions). It does this for a certain number of iterations (can be changed in code).
  
  - **Random local search with diversification:** the function **recherche_locale_avec_diversification()** takes a solution and generates neighborhoods using **generateNeighborhoodRandom(...)** with random deltas for rate and date. It then compares the date of the end of evacuation of all those neighborhoods and takes the lowest one (putting priority on valid solutions). It does this for a certain number of iterations (can be changed in code).
  
  - **Random local according to validity:** the function **local_search** takes a solution, checks it and if the reason for non-validity is overflow, generates a neighborhood using **generate_Neighborhood_RATE(...)**. If the reason is duedate however it generates the neighborhood using **generate_Neighborhood_RATE(...)**. It then compares the date of the end of evacuation of all those neighborhoods and takes the lowest one (putting priority on valid solutions). It then checks the best solution and loops. It does this for a certain number of iterations (can be changed in code).
  
**_Test :_** Running the file *Test_Local_Search* in **Evacuation/src/solution** will take the instance whose name is written on line 12 from file **Instance_Int** and generate and print its infimum,maximum, result of all the types of local searches along with their validity and the value of their date of end of evacuation.
