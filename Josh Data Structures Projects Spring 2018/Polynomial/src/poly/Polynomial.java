package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(poly1 == null && poly2 == null) {
			return null;
		}
		if(poly1 == null) {
			return poly2;
		}
		if(poly2 == null) {
			return poly1;
		}
		Node frontOfTuple1 = reverseTuple(getTuple(poly1));
		Node frontOfTuple2 = reverseTuple(getTuple(poly2));
	/**	traverse(poly2);
		traverse(frontOfTuple2);
		traverse(poly1);
		traverse(frontOfTuple1);
		
		*/
		Node ptr = frontOfTuple1;
		Node ptr2 = frontOfTuple2;
		Node ptr3 = null;
		Node frontOfSumTuple = null;
		if(frontOfTuple1.term.degree == frontOfTuple2.term.degree) {
			frontOfSumTuple = new Node(frontOfTuple1.term.coeff + frontOfTuple2.term.coeff, frontOfTuple1.term.degree, null);
			ptr3 = frontOfSumTuple;
		}else {
			if(frontOfTuple1.term.degree > frontOfTuple2.term.degree) {
				int largestDegreeDiff = frontOfTuple1.term.degree - frontOfTuple2.term.degree;
				frontOfSumTuple = new Node(frontOfTuple1.term.coeff, frontOfTuple1.term.degree, null);
				ptr3 = frontOfSumTuple;
				ptr = ptr.next;
				
				largestDegreeDiff -= 1;
				for(int i = largestDegreeDiff; i > 0; i--) {
					ptr3.next = new Node(ptr.term.coeff, ptr.term.degree, null);
					ptr3 = ptr3.next;
				}
				
				
					
					
				//ptr3 = frontOfSumTuple;
			}else {
				int largestDegreeDiff = frontOfTuple2.term.degree - frontOfTuple2.term.degree;
				frontOfSumTuple = new Node(frontOfTuple2.term.coeff, frontOfTuple2.term.degree, null);
				ptr3 = frontOfSumTuple;
				ptr2 = ptr2.next;
				
				largestDegreeDiff -= 1;
				for(int i = largestDegreeDiff; i > 0; i--) {
					ptr3.next = new Node(ptr2.term.coeff, ptr2.term.degree, null);
					ptr3 = ptr3.next;
				}
				//ptr3 = frontOfSumTuple;
			}
		}
		
		if(ptr == null) {
			
			return frontOfSumTuple;
			
		}
		
		//Node ptr3 = frontOfSumTuple;
		while(ptr != null && ptr2 != null) {
			ptr3.next = new Node(ptr.term.coeff + ptr2.term.coeff, ptr.term.degree, null);
			ptr3 = ptr3.next;
			ptr = ptr.next;
			ptr2 = ptr2.next;
			
		}
		while(ptr != null) {
			ptr3.next = new Node(ptr.term.coeff, ptr.term.degree, null);
			ptr3 = ptr3.next;
			ptr = ptr.next;
		}
		while(ptr2 != null) {
			ptr3.next = new Node(ptr.term.coeff, ptr.term.degree, null);
			ptr3 = ptr3.next;
			ptr2 = ptr2.next;
		}
		/**
		while(frontOfSumTuple.term.coeff == 0) {
			if(frontOfSumTuple.next != null) {
				frontOfSumTuple = frontOfSumTuple.next;
				System.out.println("test");
			}else {
				return null;
			}
		}*/
		
		ptr3 = frontOfSumTuple;
		
		while(ptr3 != null) {
			if(ptr3.next != null) {
				if (ptr3.next.term.coeff == 0) {
					ptr3.next = ptr3.next.next;
				
				}
			}
			ptr3 = ptr3.next;
		}
		
		//traverse(frontOfSumTuple);
		//System.out.println("test");
		Node combined = combineLikeTerms(frontOfSumTuple);
		Node noZeros = removeZeros(combined);
		traverse(noZeros);
		System.out.println("^^");
		//traverse(removeZeros(combineLikeTerms(frontOfSumTuple)));
		return noZeros;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(poly1 == null || poly2 == null) {
			return null;
		}
		
		//traverse(poly1);
		Node frontOfTuple1 = poly1;
		Node frontOfTuple2 = poly2;
		Node frontOfProductTuple = null;
		
		Node ptr = frontOfTuple1;
		Node ptr2 = frontOfTuple2;
		//Node ptr3 = frontOfProductTuple;
		
		int sizeOfFirstTuple = 0;
		int sizeOfSecondTuple = 0;
		
		while(ptr != null) {
			ptr = ptr.next;
			sizeOfFirstTuple++;
		}
		while(ptr2 != null) {
			ptr2 = ptr2.next;
			sizeOfSecondTuple++;
		}
		ptr = frontOfTuple1;
		ptr2 = frontOfTuple2;
		
		frontOfProductTuple = new Node((ptr.term.coeff * ptr2.term.coeff), (ptr.term.degree + ptr2.term.degree), null);
		Node ptr3 = frontOfProductTuple;
		traverse(frontOfProductTuple);
		//ptr = ptr.next;
		ptr2 = ptr2.next;
		while(ptr != null) {
			while(ptr2 != null) {
			//	System.out.println("test");
				ptr3.next = new Node((ptr.term.coeff * ptr2.term.coeff), (ptr.term.degree + ptr2.term.degree), null);
				ptr3 = ptr3.next;
				ptr2 = ptr2.next;
				
			}
			ptr2 = frontOfTuple2;
			ptr = ptr.next;
		}
		//traverse(frontOfProductTuple);
		Node combined = frontOfProductTuple;
		combined = combineLikeTerms(combined);
		//traverse(combined);
		//System.out.println(termOfDegree(frontOfProductTuple,0));
		//traverse(combined);
		Node noZeros = removeZeros(combined);
		traverse(noZeros);
		return noZeros;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float total = 0;
		for(Node ptr = poly; ptr != null; ptr = ptr.next) {
			total += (ptr.term.coeff * Math.pow(x, ptr.term.degree));
		}
		
		
		return total;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	
	
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
	
	
	private static Node getTuple(Node poly1) {
		if(poly1 == null) {
			return null;
		}
		int highestDegree = 0;
		Node ptr = poly1;
		while(ptr != null) {
			if(ptr.term.degree > highestDegree) {
				highestDegree = ptr.term.degree;
			}
			ptr = ptr.next;
		}
		ptr = poly1;
		int lowestDegree = highestDegree;
		while(ptr != null) {
			if(ptr.term.degree < lowestDegree) {
				lowestDegree = ptr.term.degree;
			}
			ptr = ptr.next;
		}
		ptr = poly1;
			Node tupleFront = new Node(poly1.term.coeff, lowestDegree, null);
			Node ptr2 = tupleFront;
			//Node ptr = poly1;
			while(ptr != null) {
			if(ptr.next != null) {	
				
				if((ptr.next.term.degree - ptr.term.degree) == 1) {
					ptr2.next = new Node(ptr.next.term.coeff, ptr.next.term.degree, null);
					ptr2 = ptr2.next;
				}else {
					while(ptr2.term.degree < (ptr.next.term.degree - 1)){
						ptr2.next = new Node(0, ptr2.term.degree + 1, null);
						ptr2 = ptr2.next;
					}
					ptr2.next = new Node(ptr.next.term.coeff, ptr.next.term.degree, null);
					ptr2 = ptr2.next;
					
				}
			}
				ptr = ptr.next;
			}
			return tupleFront;
		
		
	}
	private static Node reverseTuple(Node poly1) {
		if(poly1 == null) {
			return null;
		}
		int numElements = 0;
		Node ptr = poly1;
		Node ptr2 = poly1;
		//System.out.println(poly1.term.degree);
		while(ptr2 != null) {
			ptr = ptr2;
			numElements++;
			ptr2 = ptr2.next;
			
		}
		//After this loop ptr should point to the term of highest degree
		
		Node reverseTupleFront = new Node(ptr.term.coeff, ptr.term.degree, null);
		Node ptr3 = reverseTupleFront;
		for(int i = numElements - 1 ; i > 0; i--) {
			ptr2 = poly1;
			//System.out.println(ptr2.term.degree);
			  for(int j = i; j > 1; j--) {
				  ptr2 = ptr2.next;
				 
			  }
			  ptr3.next = new Node(ptr2.term.coeff, ptr2.term.degree, null);
			 // System.out.println(ptr2.term.degree);
			  ptr3 = ptr3.next;
			  
			  
		}
		
		return reverseTupleFront;
	}
	private static void traverse(Node front) {
		Node ptr = front;
		while(ptr != null) {
			System.out.print(ptr.term.coeff + "^" + ptr.term.degree + " -> ");
			ptr = ptr.next;
		}
		System.out.println("\\");
	}
	/*private Node sortPolynomial(Node poly1) {
		ptr = poly1;
	}*/
	
	private static Node combineLikeTerms(Node poly1) {
		Node ptr = poly1;
		Node combinedPolynomial;
		int termOfHighestDegree = 0;
		Node ptr4 = ptr;
		while(ptr != null) {
			if(termOfHighestDegree < ptr.term.degree) {
				termOfHighestDegree = ptr.term.degree;
				ptr4 = ptr;
			}
			ptr = ptr.next;
		}
		ptr = poly1;
		combinedPolynomial = new Node(ptr4.term.coeff, ptr4.term.degree, null);
		Node ptr2 = combinedPolynomial;
		//ptr = ptr.next;
		/*while(ptr != null) {
			if(ptr.term.degree == ptr2.term.degree) {
				ptr2.term.coeff += ptr.term.coeff;
				//System.our.
			}
			ptr = ptr.next;
		}*/
		
		
		//while(ptr != null) {
		for(int i = termOfHighestDegree - 1; i >= 0; i--) {
			
				//System.out.println("test");
				if(termOfDegree(poly1, i)) {
					//System.out.println("test2");
					ptr2.next = new Node(0,i,null);
					ptr2 = ptr2.next;
					//System.out.println(ptr2.term.degree);
					Node ptr3 = poly1;
					while(ptr3 != null) {
						//System.out.println("test3");
						if(ptr3.term.degree == i) {
							ptr2.term.coeff += ptr3.term.coeff;
							//System.out.println(ptr3.term.degree);
							ptr3 = ptr3.next;
							}else {
								ptr3 = ptr3.next;
							}
							
						//ptr3 = ptr3.next;
						//System.out.println("test3");
						
					}
				}
				//System.out.println(ptr.term.degree);				
				//System.out.println("test4");
				
			}
		//ptr = ptr.next;
		//}
		return reverseTuple(combinedPolynomial);
	}
	private static boolean termOfDegree(Node poly1, int degree) {
		Node ptr = poly1;
		while(ptr != null) {
			if(ptr.term.degree == degree) {
				return true;
			}
			ptr = ptr.next;
		}
		return false;
	}
	private static Node removeZeros(Node poly1) {
		Node ptr = poly1;
		boolean foundNonZero = false;
		while(ptr != null) {
			if(ptr.term.coeff != 0) {
				foundNonZero = true;
			}
			ptr = ptr.next;
		}
		if(!foundNonZero) {
			return null;
		}
		ptr = poly1;
		traverse(poly1);
		while(ptr != null && ptr.next != null) {
			
			while(ptr.next != null && ptr.next.term.coeff == 0) {
				
				System.out.println("test");
				if(ptr.next.next != null) {
					ptr.next = ptr.next.next;
					System.out.println(ptr.next.term.degree);
				}else {
					ptr.next = null;
				}
				
				
				
			}
			if(ptr != null) {
				ptr = ptr.next;
			}
			
		}
		if(ptr == poly1) {
			System.out.println("hey");
			return null;
		}
		/**if(ptr.term.coeff == 0) {
			ptr.next = null;
		}*/
		System.out.println("infinite loop?");
		return poly1;
	}
	
}
