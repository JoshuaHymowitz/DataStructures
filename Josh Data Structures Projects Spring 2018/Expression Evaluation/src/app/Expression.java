package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	//System.out.println("test");
    	removeSpaces(expr);
    	//System.out.println("excuse me?");
    	//System.out.println(expr2);
    	for(int i = 0; i < expr.length(); i++) {
    		String currentSubstring = "";
    		//System.out.println("heyo");
    		if(expr.charAt(i) != '+' && expr.charAt(i) != '-' && expr.charAt(i) != '*' && expr.charAt(i) != '/' && expr.charAt(i) != '(' && expr.charAt(i) != ')' && expr.charAt(i) != '[' && expr.charAt(i) != ']' && expr.charAt(i) != '0' && expr.charAt(i) != '1' && expr.charAt(i) != '2' && expr.charAt(i) != '3' && expr.charAt(i) != '4' && expr.charAt(i) != '5' && expr.charAt(i) != '6' && expr.charAt(i) != '7' && expr.charAt(i) != '8' && expr.charAt(i) != '9') {
    			currentSubstring += expr.charAt(i);
    			//System.out.println("heyo");
    			for(int j = i + 1; j != -1; j++) {
    				//System.out.println(expr.charAt(j));
    				if(j < expr.length() && expr.charAt(j) != '+' && expr.charAt(j) != '-' && expr.charAt(j) != '*' && expr.charAt(j) != '/' && expr.charAt(j) != '(' && expr.charAt(j) != ')' && expr.charAt(j) != ']' ) {
    					//System.out.println("?");
    					if(expr.charAt(j) == '[') {
    						i = j;
    						j = -2;
    						boolean addOrNo = true;
    						for(int k = 0; k < arrays.size(); k++) {
    							if(arrays.get(k).name.equals(currentSubstring)) {
    								addOrNo = false;
    								break;
    							}
    						}
    						if(addOrNo) {
    							arrays.add(new Array(currentSubstring));
    						}
    					}else {
    						//System.out.println("test");
    						currentSubstring += expr.charAt(j);
    						//System.out.println(currentSubstring);
    					}
    					
    				}else {
    					//System.out.println("testing");
    					i = j;
    					j = -2;
    					boolean addOrNo = true;
    					for(int k = 0; k < vars.size(); k++) {
    						//System.out.println("test");
    					//	System.out.println(vars.get(k).name);
							if(vars.get(k).name.equals(currentSubstring)) {
								//System.out.println("test");
								
								addOrNo = false;
								break;
							}
    					
    					}
    					if(addOrNo) {
    						vars.add(new Variable(currentSubstring));
    						//System.out.println(currentSubstring);
    					}
    				}
    			
    		}
    			
    	
    		}
    	}
    	traverse(vars);
    	System.out.println("\n\n");
    	traverse(arrays);
    	
    	
    }
    	
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS MEmTHOD **/
    	
    	
    	removeSpaces(expr);
    	ArrayList<String> tokens = new ArrayList<String>();
    	tokens = tokenize(expr);
    	//System.out.println("i wish i was at disneyworld");
    	traverse(tokens);
    	float total =0;
    	total += evaluateToken(expr, 0, expr.length(), vars, arrays);
    	/*
       // System.out.println("test");
    	//traverse(tokens);
    	float total = 0;
    	int numToken = 0;
    	total += evaluateToken(expr2, 0, expr2.length(), vars, arrays);
    	/**
    	for(int i = 0; i < expr.length(); i++) {
    		if(i == 0) {
    			total += evaluateToken(tokens.get(0), vars, arrays);
    			numToken++;
    			continue;
    		}
    		if(expr.charAt(i) != '+' && expr.charAt(i) != '-' && expr.charAt(i) != '*' && expr.charAt(i) != '/') {
    			continue;
    		}else {
    			switch (expr.charAt(i)) {
    				case '+': total += evaluateToken(tokens.get(numToken), vars, arrays); numToken++; continue; 
    				case '-': total -= evaluateToken(tokens.get(numToken), vars, arrays); numToken++; continue; 
    				case '*': total *= evaluateToken(tokens.get(numToken), vars, arrays); numToken++; continue;
    				case '/': total /= evaluateToken(tokens.get(numToken), vars, arrays); numToken++; continue;
    				
    			}
    		}
    	}
    	*/
    	
    	
    	
    	return total;
    }
    /*
    private static float evaluateSubstring(String expr, int firstChar, int secondChar, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	Stack<Integer> indices = new Stack<Integer>();
    	for(int i = 0; i < expr.length(); i++) {
    		
    	}
    }
    */
    private static float evaluateToken(String expr, int firstChar, int lastChar, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	removeSpaces(expr);
    	
    //	System.out.println(firstChar + " " + lastChar);
    //	System.out.println("top of the loop");
    //	for(int i = firstChar; i < lastChar; i++) {
    		//System.out.print(expr.charAt(i));
    		//System.out.println("wat");
    	//}
    	//System.out.println("end of initial loop");
    	boolean isConstant = true;
    	boolean isVariable = true;
    	boolean moreParentheses = true;
    	boolean isSingleElement;
    	boolean isArrayReference = true;
    	String currentSubstring = "";
    	ArrayList<String> tokens = new ArrayList<String>();
    //	System.out.println("heyo");
    	tokens = tokenize(expr.substring(firstChar, lastChar));
    	traverse(tokens);
    	for(int i = 0; i < tokens.size(); i++) {
    		removeSpaces(tokens.get(i));
    	}
    	int numToken = 0;
    	float total = 0;
    	int indexOfLastOperator = 0;
    	numToken = 0;
    	indexOfLastOperator = 0;
    	float arrayIndex = 0;
    	String arrayName = "";
    	/*
    	for(int i = firstChar; i <= lastChar; i++) {
    		if(i == lastChar) {
				currentSubstring += tokens.get(numToken);
				break;
			}
    		if(expr.charAt(i) == '+' || expr.charAt(i) == '-') {
    			
    			currentSubstring += tokens.get(numToken);
    			currentSubstring += expr.charAt(i);
    			numToken++;
    		}
    	}*/
    	//tokens = tokenize(expr);
    	for(int i = 0; i < tokens.size(); i++) {
    		indexOfLastOperator = 0;
    		for(int j = 0; j < tokens.get(i).length(); j++) {
    			if(tokens.get(i).charAt(j) == '+' || tokens.get(i).charAt(j) == '-' || tokens.get(i).charAt(j) == '*' || tokens.get(i).charAt(j) == '/' || tokens.get(i).charAt(j) == '(') {
    				indexOfLastOperator = j;
    			}else if(tokens.get(i).charAt(j) == '[') {
    			//	System.out.println("hello from the other side");
    				for(int k = indexOfLastOperator; k < j; k++) {
    					arrayName += tokens.get(i).charAt(k);
    				}
    				Stack<Character> brackets = new Stack<Character>();
    				for(int k = j; k < tokens.get(i).length(); k++) {
    					if(tokens.get(i).charAt(k) == '[') {
    						brackets.push('[');
    					}else if(tokens.get(i).charAt(k) == ']') {
    						brackets.pop();
    						if(brackets.size() == 0) {
    							arrayIndex = evaluateToken(tokens.get(i), j+1, k, vars, arrays);
    							String substring1 = tokens.get(i).substring(0,j+1);
    							String substring2 = Float.toString(arrayIndex);
    							String substring3 = tokens.get(i).substring(k, tokens.get(i).length());
    							tokens.set(i, substring1+substring2+substring3);
    							//System.out.println("token at i: " + tokens.get(i));
    							j = k;
    							break;
    						}
    					}
    					
    				}
    				 
    			}
    			
    		}
    		
    		//THE CODE ABOVE FINDS ARRAY INDEX, NOW ADD CODE TO GET THE ARRAY NAME THEN REPLACE THE TOKEN CONTAINING THE ARRAY REFERENCE WITH THE ACTUAL EVALUATION OF THE ARRAY INDEXING
    		//tokens.set(i, evaluateToken(token.get(i), 0, ))
    	}
    	
    	for(int i = 0; i < tokens.size(); i++) {
    		if(tokens.get(i).charAt(0) == '('){
    			System.out.println("break?");
    			tokens.set(i, Float.toString(evaluateToken(tokens.get(i), 1, tokens.get(i).length() - 1, vars, arrays)));
    			
    		}
    	}
    	
    	numToken = 0;
    	for(int i = 0; i < tokens.size(); i++) {
    		System.out.println("i: " + i);
    		if(tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
    			switch(tokens.get(i)) {
    			case "*":
    				System.out.println("um");
    			tokens.set(i, Float.toString(evaluateToken(tokens.get(i -1 ), 0, tokens.get(i-1).length(), vars, arrays) * evaluateToken(tokens.get(i+1), 0, tokens.get(i+1).length(), vars, arrays)));
    			System.out.println("hey! listen!");
    			System.out.println(tokens.get(i));
    			tokens.remove(i+1);
    			tokens.remove(i-1);
    			i--; break;
    			case "/":
    				tokens.set(i, Float.toString(evaluateToken(tokens.get(i-1), 0, tokens.get(i-1).length(), vars, arrays) / evaluateToken(tokens.get(i+1), 0, tokens.get(i+1).length(), vars, arrays)));
    				tokens.remove(i+1);
    				tokens.remove(i-1);
    				i--; break;
    			}
    		}
    	}
    	for(int i = 0; i < tokens.size(); i++) {
    		System.out.println("i: " + i);
    		if(tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
    			switch(tokens.get(i)) {
    			case "+":
    				System.out.println("um");
    			tokens.set(i, Float.toString(evaluateToken(tokens.get(i -1 ), 0, tokens.get(i-1).length(), vars, arrays) + evaluateToken(tokens.get(i+1), 0, tokens.get(i+1).length(), vars, arrays)));
    			System.out.println("hey! listen! part2!");
    			System.out.println(tokens.get(i));
    			tokens.remove(i+1);
    			tokens.remove(i-1);
    			i--; break;
    			case "-":
    				if(!(tokens.get(i).length() > 1)) {
    					System.out.println("Over here!");
    					System.out.println(tokens.get(i).length());
    					System.out.println(tokens.get(i));
    					for(int m = 0; m < tokens.size(); m++) {
    						System.out.print(tokens.get(m));
    					}
    					System.out.println("\n");
        				System.out.println("First term: " + Float.toString((evaluateToken(tokens.get(i-1), 0, tokens.get(i-1).length(), vars, arrays))));
        				System.out.println("Second term: " + Float.toString((evaluateToken(tokens.get(i+1), 0, tokens.get(i+1).length(), vars, arrays))));
        				tokens.set(i, Float.toString(evaluateToken(tokens.get(i-1), 0, tokens.get(i-1).length(), vars, arrays) - evaluateToken(tokens.get(i+1), 0, tokens.get(i+1).length(), vars, arrays)));
        				System.out.println("Da ting go skrrraaa" + tokens.get(i));
        				tokens.remove(i+1);
        				tokens.remove(i-1);
        				i--; break;
    				}else {
    					break;
    				}
    				
    			}
    		}
    	}
    	
    	System.out.println("hello");
    	System.out.println(expr);
    	System.out.println(firstChar);
    	System.out.println(lastChar);
    	//System.out.println(Float.parseFloat(expr));
    	//for(int i = 0; i < tokens.size(); i++) {
    		//System.out.println(tokens.get(i));
    	//}
    	numToken = 0;
    	indexOfLastOperator = 0;
    	//System.out.println(currentSubstring);
    	/**for(int i = firstChar; i <= lastChar; i++) {
    		if(i == lastChar) {
				currentSubstring += tokens.get(numToken);
				break;
			}
    		if(expr.charAt(i) == '+' || expr.charAt(i) == '-') {
    			
    			currentSubstring += tokens.get(numToken);
    			currentSubstring += expr.charAt(i);
    			numToken++;
    		}
    	}*/
    	for(int i = 0; i < tokens.get(0).length(); i++) {
    		System.out.println("current Char: " + tokens.get(0).charAt(i));
    		if(tokens.get(0).charAt(i) != '0' && tokens.get(0).charAt(i) != '1' && tokens.get(0).charAt(i) != '2' && tokens.get(0).charAt(i) != '3' && tokens.get(0).charAt(i) != '4' && tokens.get(0).charAt(i) != '5' && tokens.get(0).charAt(i) != '6' && tokens.get(0).charAt(i) != '7' && tokens.get(0).charAt(i) != '8' && tokens.get(0).charAt(i) != '9' && tokens.get(0).charAt(i) != '.' && tokens.get(0).charAt(i) != '[' && (tokens.get(0).charAt(i) != '-'))  {
    			System.out.println("chopped liver??");
    			isConstant = false;
    		}
    	}
    	System.out.println("variable loop:");
    	for(int i = firstChar; i < lastChar; i++) {
    		System.out.println("current char: " + expr.charAt(i));
    		if(expr.charAt(i) == '[')  {
    			System.out.println("didnt mean to make you cry");
    			isVariable = false;
    		}
    	}
    	numToken = 0;
    
    	//for(int i )
    	System.out.println("pre tets");
    	System.out.println(firstChar);
    	System.out.println(lastChar);
    	if(isConstant) {
    		System.out.println("tets");
    		System.out.println(Float.parseFloat(tokens.get(0)));
    		
    		return (Float.parseFloat(tokens.get(0)));
    	}
    	if(isVariable) {
    		//System.out.println("test");
    		
    		for(int i = 0; i < vars.size(); i++) {
    			//System.out.println(expr.substring(firstChar, lastChar));
    			if(vars.get(i).name.equals(expr.substring(firstChar, lastChar))) {
    				//System.out.println(expr.substring(firstChar, lastChar));
    				//System.out.println(vars.get(i));
    				return vars.get(i).value;
    			}
    		}
    	}else {
    		System.out.println("is this the real life?");
    		System.out.println(arrayName);
    		for(int i = 0; i < arrays.size(); i++) {
    			//System.out.println(expr.substring(firstChar, lastChar));
    			if(arrays.get(i).name.equals(arrayName)) {
    				System.out.println("anyway the wind blows");
    				//System.out.println(expr.substring(firstChar, lastChar));
    				//System.out.println(vars.get(i));
    				return arrays.get(i).values[(int) arrayIndex];
    			}
    		}
    		
    	}
    	
    	
    	return total;
    	
    }
    private static ArrayList<String> tokenize(String expr){
    	System.out.println("expresssion: " + expr);
    	expr = removeSpaces(expr);
    	System.out.println("expresssion: " + expr);
    	ArrayList<String> tokens = new ArrayList<String>();
    	for(int i = 0; i < expr.length(); i++) {
    		String currentSubstring = "";
    		System.out.println(expr.charAt(i));
    		if(expr.charAt(i) != '+' && expr.charAt(i) != '-' && expr.charAt(i) != '*' && expr.charAt(i) != '/') {
    			System.out.println("um dafuq");
    			for(int j = i; j != -1; j++) {
        			//currentSubstring += expr.charAt(j);
        			if(j < expr.length() && expr.charAt(j) != '+' && expr.charAt(j) != '-' && expr.charAt(j) != '*' && expr.charAt(j) != '/') {
        				if(expr.charAt(j) == '[') {
        					Stack<Character> brackets = new Stack<Character>();
        					//brackets.push('[');
        					for(int k = j; k != -1; k++) {
        						if(k >= expr.length()) {
        							j = k;
        							k = -2;
        							continue;
        						}
        						currentSubstring += expr.charAt(k);
        						if(expr.charAt(k) == '[') {
        							brackets.push(expr.charAt(k));
        						}else if(expr.charAt(k) == ']') {
        							//System.out.println("hmm");
        							brackets.pop();
        						}
        						System.out.println(brackets.size());
        						if(brackets.size() == 0) {
        							//System.out.println("what");
        						//	System.out.println(currentSubstring);
        							j = k;
        							k = -2;
        							
        						}
        						
        					}
        					//System.out.println("testing");
        					//System.out.println(currentSubstring);
        					tokens.add(currentSubstring);
        					currentSubstring = "";
    						continue;
        					
        				}
        				if(expr.charAt(j) == '(') {
        					Stack<Character> parentheses = new Stack<Character>();
        					
        					for(int k = j; k != -1; k++) {
        						if(k >= expr.length()) {
        							j = k;
        							k = -2;
        							continue;
        						}
        						currentSubstring += expr.charAt(k);
        						
        						if(expr.charAt(k) == '(') {
        							parentheses.push('(');
        						}else if(expr.charAt(k) == ')') {
        							parentheses.pop();
        						}
        						if(parentheses.size() == 0) {
        							j = k;
        							k = -2;
        							
        						}
        					}
        					
        					tokens.add(currentSubstring);
        					currentSubstring = "";
        					continue;
        				}
        				//System.out.println("I think I see the problem");
        				//System.out.println(expr.charAt(j));
        				
        				 currentSubstring += expr.charAt(j);
        			}else if(j < expr.length()){
        				tokens.add(currentSubstring);
        				tokens.add(Character.toString((expr.charAt(j))));
        				//System.out.println("pbandj");
        				i = j;
        				j = -2;
        			}else {
        				tokens.add(currentSubstring);
        				i = j;
        				j = -2;
        			}
        		}
    			//System.out.println("testing2");
    			//tokens.add(currentSubstring);
    			
    			
    		/**}else {
    			//currentSubstring += expr.charAt(i);
    			//tokens.add(currentSubstring);
    			
    		*/}else {
    			System.out.println("This should be an operator " + expr.charAt(i));
    			tokens.add(Character.toString((expr.charAt(i))));
    		}
    		
    	}
    	for(int i = 0; i < tokens.size(); i++) {
    		if(tokens.get(i).equals("")) {
    			tokens.remove(i);
    			i--;
    			System.out.println("doing ya thing");
    		}
    	}
    	
    	for(int i = 0; i < tokens.size(); i++) {
    		if(i == 0) {
    			if(tokens.get(i).equals("-")) {
    				tokens.set(i, "-" + tokens.get(1));
    				tokens.remove(1);
    				continue;
    			}
    		}
    		if(tokens.get(i).equals("-")) {
    			if(tokens.get(i-1).equals("+") || tokens.get(i-1).equals("-") || tokens.get(i-1).equals("*") || tokens.get(i-1).equals("/") || tokens.get(i-1).equals("(") || tokens.get(i-1).equals("[")){
    				tokens.set(i, "-" + tokens.get(i+1));
    				tokens.remove(i+1);
    			}
    		}
    		
    	}
    	System.out.println("tokens: ");
    	traverse(tokens);
    	System.out.println("\n");
    	
    	return tokens;
    	
    }
    private static String removeSpaces(String expr) {
    	expr = expr.replaceAll("\\s+","");
    	System.out.println("Now without spaces (??) " + expr);
    	return expr = expr.replaceAll("\\s+","");
    	
    	
    	}
    private static void traverse(ArrayList list) {
    	for(int i = 0; i < list.size(); i++) {
    		
    		System.out.print(list.get(i) + " ");
    	}
    	System.out.println("\n");
    }
    }

