package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
private ArrayList<String> tokenize(){
		ArrayList<String> tokens = new ArrayList<String>();
		//System.out.println(sc.nextLine());
		while(sc.hasNextLine()) {
			tokens.add(sc.nextLine());
		}
		return tokens;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		ArrayList<String> tokens = this.tokenize();
		//System.out.println("RIP Aliyah");
		//traverse(tokens);
		
		
		this.root = new TagNode("html", null, null);
		//System.out.println("\n\n");
		
		build(root, 0, tokens);
		//System.out.println("pray for me");
		print();
		
	}
	private static int indexOfNextSib(int level, ArrayList<String>tokens) {
		Stack<Character> sibs = new Stack<Character>();
		int j = level;
		if(tokens.get(j).charAt(0) != '<' && sibs.size() == 0) {
			if(tokens.get(j+1).length() >= 2 && tokens.get(j + 1).substring(0,2).equals("</")) {
				return 0;
			}
			return j + 1;
		}
		while(true) {
			j++;
			
			if(j >= tokens.size()) {
				return 0;
			}
			if(tokens.get(j).length() >=2 && tokens.get(j).charAt(0) == '<' && tokens.get(j).charAt(1) != '/'){
				sibs.push('<');
			}else if(tokens.get(j).length() >=2 && tokens.get(j).substring(0,2).equals("</")) {
				try {
					sibs.pop();
					
				}catch(NoSuchElementException e) {
					j++;
					break;
				}
			
		}
		}
		//System.out.println(level);
		if(j >= tokens.size()) {
			return 0;
		}
		if(tokens.get(j).length() >=2 && tokens.get(j).substring(0,2).equals("</")) {
			return 0;
		}
		return j;
	}
	/*
	private static int getNumSiblings(int level, ArrayList<String> tokens) {
		Stack<Character> siblings = new Stack<Character>();
		Stack<Character> siblings2 = new Stack<Character>();
		int j = level;
		int numSiblings = 0;
		while(true) {
			j++;
			if(j >= tokens.size()) {
				return 0;
				
			}
			if(tokens.get(j).length() >=2 && tokens.get(j).charAt(0) == '<' && tokens.get(j).charAt(1) != '/'){
				siblings.push('<');
			}else if(tokens.get(j).length() >=2 && tokens.get(j).substring(0,2).equals("</")) {
				try {
					siblings.pop();
					
				}catch(NoSuchElementException e) {
					j++;
					break;
				}
			
		}
		}
		System.out.println("Second sibling");
		System.out.println(tokens.get(j));
		
		while(true) {
			j++;
			if(j >= tokens.size()) {
				break;
			}
			if(tokens.get(j).length() >=2 && tokens.get(j).charAt(0) == '<' && tokens.get(j).charAt(1) != '/'){
				siblings.push('<');
			}else if(tokens.get(j).length() >=2 && tokens.get(j).substring(0,2).equals("</")) {
				try {
					siblings.pop();
					if(siblings.size() == 0) {
						numSiblings++;
						System.out.println(tokens.get(j));
						System.out.println("tit");
					}
				}catch(NoSuchElementException e) {
					break;
				}
			}else if(tokens.get(j).charAt(0) != '<' && siblings.size() == 0) {
				numSiblings++;
				System.out.println(tokens.get(j));
			}
		}
		return numSiblings;
	}*/
	private void build(TagNode root, int level, ArrayList<String> tokens) {
		int i = level + 1;
		if(i < tokens.size()) {
			
		//System.out.println(tokens.get(i));
		if((tokens.get(i).length() >= 2 && !tokens.get(i).substring(0,2).equals("</")) || tokens.get(i).charAt(0) != '<'){
		
				//System.out.println("passed the if!");
				TagNode child = new TagNode(tokens.get(i), null, null);
				if(child.tag.charAt(0) == '<') {
					child.tag = child.tag.substring(1, child.tag.length() - 1);
				}
				if(tokens.get(i-1).charAt(0) == '<') {
					
				
				root.firstChild = child;
				
				this.build(child, i, tokens);
				}
				print();
			
				//System.out.println("Whys you lyin");
				//System.out.println(level);
				//System.out.println(tokens.get(level));
				if(level < tokens.size()) {
				int z = indexOfNextSib(level, tokens);
				TagNode ptr;
				if(z > 0) {
				TagNode sibling = new TagNode(tokens.get(z), null, null);
				root.sibling = sibling;
				ptr = sibling;
				if(sibling.tag.charAt(0) == '<') {
					sibling.tag = sibling.tag.substring(1, sibling.tag.length() - 1);
				}
				
				
				while(z > 0) {
					
					
					
					//System.out.println("this thing");
					//System.out.println(ptr.tag);
					this.build(ptr, z, tokens);
					print();
					ptr = ptr.sibling;
					//System.out.println("Z before and after");
					//System.out.println(z);
					//System.out.println(tokens.get(z));
					z = indexOfNextSib(z, tokens);
					//System.out.println(z);
					//System.out.println(tokens.get(z));
				}
			}}
		}}
		
			
		
		
		
		
		
	}

	
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		replaceTag(root, 1, oldTag, newTag);
		print();
	}
	private void replaceTag(TagNode root, int level, String oldTag, String newTag) {
		for(TagNode ptr = root; ptr != null; ptr = ptr.sibling) {
		
			if(ptr.tag.equals(oldTag)) {
				ptr.tag = (newTag);
			}else if(ptr.tag.equals(oldTag)){
				ptr.tag = (newTag);
			}
			if (ptr.firstChild != null) {
				replaceTag(ptr.firstChild, level+1, oldTag, newTag);
			}
		}
	}
		/*
		private void print(TagNode root, int level) {
			for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
				for (int i=0; i < level-1; i++) {
					System.out.print("      ");
				};
				if (root != this.root) {
					System.out.print("|---- ");
				} else {
					System.out.print("      ");
				}
				System.out.println(ptr.tag);
				if (ptr.firstChild != null) {
					print(ptr.firstChild, level+1);
				}
			}
		}
		*/
		
		
		
		/*
		ArrayList<String> tokens = new ArrayList<String>(this.tokenize());
		while(sc.hasNextLine()) {
			if(sc.nextLine().length() >= 3 && sc.nextLine().equals("<" + oldTag + ">")) {
				sc.nextLine() = ("<" + newTag + ">");
			}else if(tokens.get(i).length() >= 3 && tokens.get(i).equals("</" + oldTag + ">")){
				tokens.set(i, "</" + newTag + ">");
			}
		}
		//this.build();
		 
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		boldRow(root, 1, row);
		print();
		
	}
	private void boldRow(TagNode root, int level, int row) {
		/*
		 * 
		 * 
		 * for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
		 * 
		 */
		
		for(TagNode ptr = root; ptr != null; ptr = ptr.sibling) {
			if (ptr.tag.equals("table")){
				TagNode ptr2 = ptr.firstChild;
				int currentRow = 1;
				while(ptr2 != null) {
					if (currentRow == row) {
						break;
					}
					ptr2 = ptr2.sibling;
					currentRow++;
				}
				//ptr2 is pointing to the tr tag
				if(ptr2 != null) {
				ptr2 = ptr2.firstChild;
				TagNode ptr3 = ptr2.firstChild;
				//ptr2 is now pointing to the first td
				//ptr3 is now pointing to the data of the first td
			//	int currentRow = 1;
				while(ptr2 != null) {
					//System.out.println("top of the loop");
					//System.out.println(ptr2.tag);
					ptr2.firstChild = new TagNode("b", null, null);
					//System.out.println(ptr2.firstChild.tag);
					ptr2.firstChild.firstChild = ptr3;
					
					ptr2 = ptr2.sibling;
					//System.out.println(ptr2.tag);
					if(ptr2 != null) {
					ptr3 = ptr2.firstChild;
					//System.out.println(ptr3.tag);
					}
				}
				
				break;
				
			}
			}
			
			
			
			if(ptr.firstChild != null) {
				boldRow(ptr.firstChild, level+1, row);
			}
		}
		
		
		
		
	}
	
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		removeTag(root, 1, tag);
	
	}
	private void removeTag(TagNode root, int level, String tag) {
		if(tag.equals("b") || tag.equals("em") || tag.equals("p")) {
		for(TagNode ptr = root; ptr != null; ptr = ptr.sibling) {
			if(ptr.sibling != null && ptr.sibling.tag.equals(tag)) {
				TagNode ptr3 = null;
				if(ptr.sibling.sibling != null) {
					ptr3 = ptr.sibling.sibling;
				}
				if(ptr.sibling.firstChild != null)
				{
				ptr.sibling = ptr.sibling.firstChild;
				//System.out.println("OVER HERE");
			//	System.out.println(ptr.tag);
			//	System.out.println(ptr.sibling.tag);
				print();
				if(ptr.sibling.firstChild != null) {
					TagNode ptr2 = ptr.sibling.firstChild;

					while(ptr2.sibling != null) {
						ptr2 = ptr2.sibling;
					}
				
				if(ptr3 != null) {
					ptr2.sibling = ptr3;
				}
				}else {
					ptr.sibling.sibling = ptr3;
				}
				}else {
					ptr.sibling = ptr3;
				}
			}
			print();
			if (ptr.firstChild != null) {
				if(ptr.firstChild.tag.equals(tag)) {
					//System.out.println("Something is up!");
					//System.out.println(ptr.tag);
					//System.out.println(ptr.firstChild.tag);
					TagNode ptr4 = ptr.firstChild;
					TagNode ptr6 = ptr4.sibling;
					ptr.firstChild = ptr4.firstChild;
					TagNode ptr5 = ptr.firstChild;
					while(ptr5.sibling != null) {
						ptr5 = ptr5.sibling;
					}
					ptr5.sibling = ptr6;
					
			}
				removeTag(ptr.firstChild, level+1, tag);
		}
			
				
				
		}
		}else {
			
			for(TagNode ptr = root; ptr != null; ptr = ptr.sibling) {
				//System.out.println("Here?");
				if(ptr.sibling != null && ptr.sibling.tag.equals(tag)) {
					
					TagNode ptr7 = null;
					if(ptr.sibling.sibling != null) {
						ptr7 = ptr.sibling.sibling;
					}
					TagNode ptr8 = ptr.sibling.firstChild;
					ptr.sibling = ptr8;
					ptr.sibling.tag = "p";
					//ptr7 = ptr8;
					while(ptr8.sibling != null) {
						ptr8.sibling.tag = "p";
						ptr8 = ptr8.sibling;
						//System.out.println("here?");
						//ptr7.sibling = ptr8.sibling;
					}
					ptr8.sibling = ptr7;
					
					
					/*
					TagNode ptr3 = null;
					if(ptr.sibling.sibling != null) {
						ptr3 = ptr.sibling.sibling;
					}
					if(ptr.sibling.firstChild != null)
					{
					ptr.sibling = ptr.sibling.firstChild;
					System.out.println("OVER HERE");
					System.out.println(ptr.tag);
					System.out.println(ptr.sibling.tag);
					print();
					if(ptr.sibling.firstChild != null) {
						TagNode ptr2 = ptr.sibling.firstChild;

						while(ptr2.sibling != null) {
							ptr2 = ptr2.sibling;
						}
					
					if(ptr3 != null) {
						ptr3.tag = "p";
						ptr2.sibling = ptr3;
					}
					}else {
						ptr3.tag = "p";
						ptr.sibling.sibling = ptr3;
					}
					}else {
						ptr3.tag = "p";
						ptr.sibling = ptr3;
					}
					*/
				}
				print();
				if (ptr.firstChild != null) {
					if(ptr.firstChild.tag.equals(tag)) {
						//System.out.println("Something is up!");
						//System.out.println(ptr.tag);
						//System.out.println(ptr.firstChild.tag);
						TagNode ptr4 = ptr.firstChild;
						TagNode ptr6 = ptr4.sibling;
						ptr.firstChild = ptr4.firstChild;
						TagNode ptr5 = ptr.firstChild;
						ptr5.tag = "p";
						while(ptr5.sibling != null) {
						//	System.out.println("down here?");
							ptr5 = ptr5.sibling;
							ptr5.tag = "p";
						}
						ptr5.sibling = ptr6;
						
				}
					removeTag(ptr.firstChild, level+1, tag);
			}
			}
			
			
			
			
		}
		
		
		
		print();}
			//}
	
		
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		addTag(root, 1, word, tag, 0);
		print();
	}
	private void addTag(TagNode root, int level, String word, String tag, int stopper ) {
		
		final int thing = 0;
		for(TagNode ptr = root; ptr != null; ptr = ptr.sibling) {
			System.out.println("WHAA");
			//System.out.println(ptr.tag);
			if(ptr.firstChild != null) {
			//System.out.println(ptr.firstChild.tag);
			}
			if(!ptr.tag.equals(tag)) {
			if(ptr.tag.toLowerCase().indexOf(word.toLowerCase()) != -1) {
				System.out.println("HELLO");
				System.out.println(ptr.tag);
				int i = ptr.tag.toLowerCase().indexOf(word.toLowerCase());
				int j = i + word.length();
				System.out.println(i);
				System.out.println(j);
				//i is the index of the first letter of the word, j is the index of the first character after the word ends
				if((i == 0 || (i > 0 && ptr.tag.charAt(i-1) == ' ')) && ((j == ptr.tag.length() || (ptr.tag.charAt(j) == '.' || ptr.tag.charAt(j) == '!' || ptr.tag.charAt(j) == ',' || ptr.tag.charAt(j) == '?' || ptr.tag.charAt(j) == ';' || ptr.tag.charAt(j) == ':')) && ( j+1 >= ptr.tag.length() ||(ptr.tag.charAt(j+1) != '.' || ptr.tag.charAt(j+1) != '!' || ptr.tag.charAt(j+1) != ',' || ptr.tag.charAt(j+1) != '?' || ptr.tag.charAt(j+1) != ';' || ptr.tag.charAt(j+1) != ':'))|| ptr.tag.charAt(j) == ' ' )) {
					
					System.out.println("HELLO WORLD");
					String substring1 = ptr.tag.substring(0, i);
					String substring2;
					String substring3;
					if(j < ptr.tag.length() && ptr.tag.charAt(j) != ' ') {
						substring2 = ptr.tag.substring(i, j+1);
						substring3 = ptr.tag.substring(j+1, ptr.tag.length());
					}else {
						substring2 = ptr.tag.substring(i, j);
						substring3 = ptr.tag.substring(j, ptr.tag.length());
					}
					System.out.println(substring1);
					System.out.println(substring2);
					System.out.println(substring3);
					
					//String substring2 = ptr.tag.substring(i, j);
					
					TagNode ptr2 = ptr;
					if(substring1.equals("")) {
						ptr.tag = tag;
						ptr.firstChild = new TagNode(substring2, null, null);
						if(!substring3.equals("")) {
							ptr2 = ptr.sibling;
							ptr.sibling = new TagNode(substring3, null, null);
							ptr.sibling.sibling = ptr2;
							ptr = ptr.sibling;
							System.out.println("HEYO");
						}
					}else {
						ptr.tag = substring1;
						ptr.sibling = new TagNode(tag, null, null);
						ptr.sibling.firstChild = new TagNode(substring2, null, null);
						if(!substring3.equals("")) {
							ptr.sibling.sibling = new TagNode(substring3, null, null);
							ptr = ptr.sibling;
						}
						ptr = ptr.sibling;
					}
					
				}
			}
			
			}
			
			
			System.out.println("lemme see");
			System.out.println(ptr.tag);
			//ptr = ptr.sibling;
			if(ptr.firstChild != null && !ptr.tag.equals(tag)) {
				System.out.println("HI");
				addTag(ptr.firstChild, level+1, word, tag, stopper + 1);
			}
			//System.out.println("Lemme see");
			
			
		}
		
		
		
		
		
		
		
		
		/*
		 * 
		 * 
		 * for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
		 * 
		 */
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
	public static void traverse(ArrayList list) {
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
