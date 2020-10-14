package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    //	System.out.println("messing");
    //	System.out.println(tree.toString());
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
    	//System.out.println(rear.tree.toString());
    		
    		/* COMPLETE THIS METHOD */
    	
    	if(rear == null) {
    		return null;
    	}
    	
    	if(rear.next == rear) {
    		Node ptr = rear;
    		rear = null;
    		//System.out.println(ptr.tree.toString());
    		//System.out.println("hey");
    		this.size--;
    		return ptr.tree;
    	}
    	Node ptr = rear.next;
    	rear.next = rear.next.next;
    	this.size--;
    	return ptr.tree;
    	
    		//return null;
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    		/* COMPLETE THIS METHOD */
    	
    	
    	
    	
    	Vertex rootOfVertex = vertex.getRoot();
    	//System.out.println("the vertex");
    	//System.out.println(vertex.toString());
    	Node ptr = rear.next;
    	Node prev = rear;
    	//System.out.println("hey");
    	//System.out.println(rootOfVertex.toString());
    	if(size == 1) {
    		PartialTree ret = ptr.tree;
    		this.remove();
    		return ret;
    	}
    	do {
    		//System.out.println("flirty");
    		//System.out.println(ptr.tree.toString());
    		if(ptr.tree.getRoot().equals(rootOfVertex)) {
    			//System.out.println("true");
    			if(prev == rear) {
    				//System.out.println("no");
    				if(rear.next == rear) {
    					rear = null;
    					this.size--;
    					return null;
    				}else {
    					prev.next = ptr.next;
    					this.size--;
        				return ptr.tree;
    					
    					//rear = rear.next;
    					//prev.next = rear;
    					//rear.next = 
    				}
    				//rear = null;
    				
    			}else {
    				prev.next = ptr.next;
    				this.size--;
    				Iterator<PartialTree> iter7 = this.iterator();
    				 while(iter7.hasNext()) {
    					 PartialTree pt = iter7.next();
    					// System.out.println(pt.toString());
    				 }
    				return ptr.tree;
    			}
    		}
    		prev = ptr;
    		ptr = ptr.next;
    	}while(ptr != rear.next);
    	
    		//THE DO WHILE LOOP TRAVERSES THE LIST, THEN WE NEED A NESTED LOOP TO TRAVERSE EACH PARTIAL TREE IN THE LIST TO FIND THE VERTEX
    		//for(Vertex ptr2 = ptr.root; )
    		//Vertex ptr2 = ptr.tree.root;
    	
    	 //Iterator<PartialTree> iter = this.iterator();
    	  
    		   
    		   /*
    	       PartialTree pt = iter.next();
    	       Vertex ptr = pt.getRoot();
    	       do {
    	    	   if(ptr.equals(vertex)){
    	    		   PartialTree temp = pt;
    	    		   pt = rear.next.tree;
    	    		   rear.next.tree = temp;
    	    		   return remove();
    	    		   
    	    		   
    	    	   }else {
    	    		   ptr = ptr.getRoot();
    	    	   }
    	    	   
    	       }while(ptr != pt.getRoot());
    	       */
    	       
    	   
    	   throw new NoSuchElementException();
    	   
    	   
    	   //return null;
    		
    	
     }
    
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}


