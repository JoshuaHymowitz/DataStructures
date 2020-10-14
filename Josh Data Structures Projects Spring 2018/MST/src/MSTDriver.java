import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import structures.Vertex;
import structures.Graph;
import apps.PartialTreeList;
import apps.PartialTree;
import apps.MST;



public class MSTDriver {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//Scanner sc = new Scanner(new File("graph1.txt"));
		/*
		Graph graph = new Graph("graph2.txt");
		PartialTreeList ptl = MST.initialize(graph);
		Iterator<PartialTree> iter = ptl.iterator();
		   while (iter.hasNext()) {
		       PartialTree pt = iter.next();
		       System.out.println(pt.toString());
		      
		   }
		System.out.println("\n");
		System.out.println(ptl.remove().toString());
		System.out.println(ptl.remove().toString());
		System.out.println("\n");
		//PartialTreeList ptl2 = MST.initialize(graph);
		Iterator<PartialTree> iter2 = ptl.iterator();
		   while (iter2.hasNext()) {
		       PartialTree pt = iter2.next();
		       System.out.println(pt.toString());
		      
		   }
		   
		   System.out.println("??");
		   Graph graph2 = new Graph("graph3.txt");
			PartialTreeList ptl3 = MST.initialize(graph2);
			Iterator<PartialTree> iter3 = ptl3.iterator();
			   while (iter3.hasNext()) {
			       PartialTree pt = iter3.next();
			       System.out.println(pt.toString());
			      
			   }
			   //System.out.println(ptl3.remove().toString());
	//	throws IOException();
		
			 System.out.println("hol up");  
		Iterator<PartialTree> iter4 = ptl.iterator();
			while(iter4.hasNext()) {
				PartialTree pt = iter4.next();
				if(iter4.hasNext()) {
					pt.merge(iter4.next());
				}
				
				System.out.println(pt.toString());
				System.out.println(pt.getRoot().parent);
				Vertex ptr = pt.getRoot().parent;
				/*do {
					System.out.println(ptr.name);
					System.out.println(ptr.parent.name);
					ptr = ptr.getRoot();
					System.out.println("hey");
				}while(ptr != pt.getRoot());
				
			}
			 //ptl.removeTreeContaining(vertex)
			 System.out.println("hol up"); 
			 Iterator<PartialTree> iter5 = ptl.iterator();
			 while(iter5.hasNext()) {
					PartialTree pt = iter5.next();
					System.out.println(ptl.removeTreeContaining(pt.getRoot()).toString());
					break;
			 					
					
					
			 }
			 Iterator<PartialTree> iter6 = ptl.iterator();
			 while(iter6.hasNext()) {
					PartialTree pt = iter6.next();
					
					System.out.println(pt.toString());
					break;
			 					
					
					
			 }
			 
			 System.out.println("---------");
			 Graph graph2 = new Graph("graph1.txt");
			 PartialTreeList ptl2 = MST.initialize(graph2);
			 Iterator<PartialTree> iter7 = ptl2.iterator();
			 while(iter7.hasNext()) {
				 PartialTree pt = iter7.next();
				 System.out.println(pt.toString());
			 }
			 
			 System.out.println(MST.execute(ptl2).toString());
			 */
		
		
		Graph graph2 = new Graph("graph1.txt");
		PartialTreeList ptl2 = MST.initialize(graph2);
		Iterator<PartialTree> iter = ptl2.iterator();
		while(iter.hasNext()) {
			PartialTree pt = iter.next();
			System.out.println(pt.toString());
		}
		System.out.println("---------");
		while(ptl2.size() > 1) {
			System.out.println("---------");
		Vertex v = null;
		Iterator<PartialTree> iter2 = ptl2.iterator();
		while(iter2.hasNext()) {
			PartialTree pt = iter2.next();
			if(iter2.hasNext()) {
				PartialTree p2 = ptl2.removeTreeContaining(iter2.next().getRoot());
				v = p2.getRoot();
				pt.merge(p2);
			}
		}
		
		Iterator<PartialTree> iter3 = ptl2.iterator();
		while(iter3.hasNext()) {
			PartialTree pt = iter3.next();
			System.out.println(pt.toString());
		}
		System.out.println(v.toString());
		
		ptl2.removeTreeContaining(v);
		Iterator<PartialTree> iter4 = ptl2.iterator();
		while(iter4.hasNext()) {
			PartialTree pt = iter4.next();
			System.out.println(pt.toString());
		}
		
		}
		System.out.println("------");
		Graph graph3 = new Graph("graph4.txt");
		PartialTreeList ptl3 = MST.initialize(graph3);
		Iterator<PartialTree> iter5 = ptl3.iterator();
		while(iter5.hasNext()) {
			PartialTree pt = iter5.next();
			System.out.println(pt.toString());
		}

		System.out.println(MST.execute(ptl3).toString());
		
	}

}
