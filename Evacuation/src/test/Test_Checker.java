package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import exception.GraphGenerationError;
import graph.Graph;
import solution.Checker;
import solution.Solution;

public class Test_Checker {
	
	private static ArrayList<String> get_all_solution_from_dir(){
		File folder = new File("../Solutions/");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
			  list.add(listOfFiles[i].getName());
			} 
		}
		return list;
	}
	
	public static void main (String arg[]){
		//Retrieve all the solutions
		ArrayList<String> list_sol = get_all_solution_from_dir();
		ListIterator<String> ite = list_sol.listIterator();
		
		while(ite.hasNext()) {
			try {
			String filename = ite.next();
			FileReader fileReader=new FileReader("../Solutions/"+filename);			
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			String graph_file_name=bufferedReader.readLine();
			
			String path = new File("../Instances_Int/"+graph_file_name+".full").getAbsolutePath();  
			Graph graph = Graph.generate_from_file(path); 
			Solution solution = Checker.generate_solution_from_file("../Solutions/"+filename);
			System.out.println("-------------------------------------");
			System.out.println("Testing solution "+filename+" graph generated from file "+graph_file_name);
			System.out.println("Solution is "+Checker.check_solution(solution, graph).get_validity());
			bufferedReader.close();
			}catch (GraphGenerationError e) {
				System.out.println("The graph hasn't been generated because "+e.getMessage());
			}catch (FileNotFoundException e) {
				System.out.println("File wasn't found "+e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}	
}
