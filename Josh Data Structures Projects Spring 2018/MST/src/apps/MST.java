package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		//System.out.println("uh");
	
		/* COMPLETE THIS METHOD */
		
		PartialTreeList list = new PartialTreeList();
		//graph.print();
		for(Vertex v : graph.vertices) {
			PartialTree tree = new PartialTree(v);
			//Vertex ptr = v;
			
			Vertex ptr2 = v;
			
			Vertex.Neighbor ptr = v.neighbors;
			//tree.getArcs().insert();
			//System.out.println("hey");
			//System.out.println(tree.getArcs());
			while(ptr != null) {
				/*
				System.out.println("HEY LISTEN");
				System.out.println(v.toString());
				System.out.println(ptr.vertex.toString());
				System.out.println(ptr.vertex.getRoot().toString());
				*/
				tree.getArcs().insert(new PartialTree.Arc(v, ptr.vertex, ptr.weight));
				ptr = ptr.next;
				
			}
			//System.out.println(tree.toString());
			list.append(tree);
			
		}
		
		return list;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		//System.out.println(ptlist.size());
		ArrayList<PartialTree.Arc> theList = new ArrayList<PartialTree.Arc>();
		while(ptlist.size() > 1) {
			PartialTree first = ptlist.remove();
			//System.out.println("top o the loop 2 ya");
			Iterator<PartialTree> iter10 = ptlist.iterator();
			while(iter10.hasNext()) {
				System.out.println(iter10.next().toString());
			}
			/*
			System.out.println("\n");
			System.out.println(first.toString());
			*/
			ptlist.append(first);//WHY IS THIS ADDING THE WRONG THING AHH
			//System.out.println(first.getRoot().getRoot().toString());
			//ptlist.append(first);
			/*
			System.out.println("what we just removed");
			System.out.println(first.toString());
			System.out.println(theList.toString());
			*/
			PartialTree.Arc arc1 = first.getArcs().deleteMin();
			/*
			Iterator<PartialTree> iter4 = ptlist.iterator();
			
			while(iter4.hasNext()) {
				System.out.println(iter4.next().toString());
			}
			System.out.println("\n");
			*/
			
			while(arc1.v1.getRoot().equals(arc1.v2.getRoot())){
				//System.out.println("hello");
				arc1 = first.getArcs().deleteMin();
				//arc1 = first.getArcs().getMin();
			}
			/*
			System.out.println("this is important");
			System.out.println(arc1.v2.toString());
			System.out.println(arc1.v2.getRoot().toString());
		
			System.out.println("\n");
			*/
			theList.add(arc1);
			//System.out.println(theList.toString());
			PartialTree temp = ptlist.removeTreeContaining(arc1.v2);
			/*
			System.out.println(temp.toString());
			System.out.println("\n");
			
			Iterator<PartialTree> iter8 = ptlist.iterator();
			while(iter8.hasNext()) {
				System.out.println(iter8.next().getRoot().toString());
			}
			System.out.println("\n");
			*/
			first.merge(temp);
			
			//ptlist.append(first);//ASK WHY APPEND ADDS B NOT A
			/*
			System.out.println("The first time, this should say A");
			System.out.println(first.toString());
			System.out.println(temp.getRoot().getRoot().toString());
			System.out.println(temp.toString());
			System.out.println("\n");
			Iterator<PartialTree> iter6 = ptlist.iterator();
			while(iter6.hasNext()) {
				System.out.println(iter6.next().getRoot().toString());
			}
			System.out.println("\n");
			
			System.out.println("\n");
			System.out.println("roots");
			System.out.println(first.toString());
			System.out.println(arc1.v2.getRoot().toString());
			System.out.println(first.getRoot().toString());
			System.out.println("\n");
			
			Iterator<PartialTree> iter2 = ptlist.iterator();
			while(iter2.hasNext()) {
				System.out.println(iter2.next().getRoot().toString());
			}
			System.out.println("\n");
			
		
			Iterator<PartialTree> iter = ptlist.iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next().toString());
			}
			System.out.println("\n");
			*/
		}
		return theList;
		
	 
	
		
		/* COMPLETE THIS METHOD */

		//return null;
	}
}
