package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		//System.out.println(docFile);
		HashMap<String,Occurrence> keywords = new HashMap<String,Occurrence>();
		Scanner sc = new Scanner(new File(docFile));
		//ystem.out.println(sc.toString());
		while (sc.hasNextLine()) {
			
			String[] candidateWords = sc.nextLine().split(" ");
			for(String c : candidateWords) {
				if(this.getKeyword(c) != null) {
					if(keywords.containsKey(getKeyword(c))) {
						//System.out.println("test");
						keywords.get(this.getKeyword(c)).frequency += 1;
					}else {
						keywords.put(this.getKeyword(c), new Occurrence(docFile, 1));
					}
				}
			}
			//System.out.println(sc.nextLine());
			
		}
		//System.out.println(keywords.values().toString());
		//System.out.println(keywords.values())
		Set<String> keys = keywords.keySet();
		Iterator<String> iterator = keys.iterator();
		//System.out.println(keywords.keySet());
		while(iterator.hasNext()) {
			String key = iterator.next();
			//System.out.print(keywords.get(key) + " ");
			//System.out.println(keywords.get(key).toString());
		}
		return keywords;
		
		
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		//return null;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		Set<String> keys = kws.keySet();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()) {
			String next = iterator.next();
			if(keywordsIndex.get(next) != null) {
			//System.out.println(keywordsIndex.get(next));
			keywordsIndex.get(next).add(kws.get(next));
			insertLastOccurrence(keywordsIndex.get(next));
			}else {
				ArrayList<Occurrence> newOne = new ArrayList<Occurrence>();
				newOne.add(kws.get(next));
				keywordsIndex.put(next, newOne);
			}
		}
		//System.out.println(keywordsIndex.keySet());
		
		
		/** COMPLETE THIS METHOD **/
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		String lowerWord = word.toLowerCase();
		boolean isValid = true;
		String noTrailingPunc = "";
		for(int i = lowerWord.length() - 1; i >= 0; i--) {
			if(!(lowerWord.charAt(i) == '.' || lowerWord.charAt(i) == ',' || lowerWord.charAt(i) == '?' || lowerWord.charAt(i) == ':' || word.charAt(i) == ';' || word.charAt(i) == '!')) {
				noTrailingPunc = lowerWord.substring(0, i + 1);
				break;
			}
		}
		if(noTrailingPunc.equals("")) {
			return null;
		}
		for(int i = 0; i < noTrailingPunc.length(); i++) {
			if(!Character.isLetter(noTrailingPunc.charAt(i))) {
				return null;
			}
		}
		if(noiseWords.contains((Object) noTrailingPunc)){
			return null;
		}
		return noTrailingPunc;
		
		/*
		for(int i = 0; i < lowerWord.length(); i++) {
			if(!Character.isLetter(lowerWord.charAt(i))) {
				
				for(int j = i; j < lowerWord.length(); j++) {
					if(lowerWord.charAt(i) == '.' || lowerWord.charAt(i) == ',' || lowerWord.charAt(i) == '?' || lowerWord.charAt(i) == ':' || word.charAt(i) == ';' || word.charAt(i) == '!') {
						
					}
				}
			}
				*/
		}
		
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
	//	return null;
	
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		int leftPoint = 0;
		int rightPoint = occs.size() - 2;
		int indexOfNewElement = occs.size()-1;
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		int midPoint = (rightPoint - leftPoint) / 2;
		indices.add(midPoint);
		
		while(rightPoint > leftPoint) {
			/*
			System.out.println("tf");
			System.out.println(leftPoint);
			System.out.println(rightPoint);
			System.out.println(midPoint);
			*/
			if(occs.get(midPoint).frequency > occs.get(indexOfNewElement).frequency) {
				leftPoint = midPoint + 1;
				midPoint = ((rightPoint - leftPoint) / 2) + leftPoint;
				indices.add(midPoint);
			}else if(occs.get(midPoint).frequency < occs.get(indexOfNewElement).frequency) {
				rightPoint = midPoint - 1;
				midPoint = ((rightPoint - leftPoint) / 2) + leftPoint;
				indices.add(midPoint);
			}else {
				rightPoint = midPoint;
				leftPoint = midPoint;
				break;
			}
		}
		if(occs.get(midPoint).frequency >= occs.get(indexOfNewElement).frequency) {
			Occurrence temp = occs.get(indexOfNewElement);
			for(int i = occs.size() - 1; i > midPoint + 1; i--) {
				occs.set(i, occs.get(i-1));
			}
			occs.set(midPoint + 1, temp);
		}else {
			Occurrence temp = occs.get(indexOfNewElement);
			for(int i = occs.size() - 1; i > midPoint; i--) {
				occs.set(i, occs.get(i-1));
			}
			occs.set(midPoint, temp);
		}
		
		/*
		 for(Occurrence o : occs) {
			System.out.println(o.document);
		}
		*/
		
		return indices;
		
		
		
		
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
	//	return null;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		kw1 = getKeyword(kw1);
		kw2 = getKeyword(kw2);
		//System.out.println(keywordsIndex.keySet());
		ArrayList<String> listOfDocs = new ArrayList<String>();
		Set<String> keys = keywordsIndex.keySet();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()) {
			String thisKey = iterator.next();
			//System.out.println(thisKey);
			if(keywordsIndex.get(thisKey) != null && (thisKey.equals(kw1) || thisKey.equals(kw2))) {
				//System.out.println("here!");
				for(Occurrence o : keywordsIndex.get(thisKey)) {
					boolean alreadyIn = false;
					for(String s : listOfDocs) {
						if(s.equals(o.document)) {
							alreadyIn = true;
						}
					}
					if(!alreadyIn) {
						listOfDocs.add(o.document);
					}
					
					//System.out.println("gimme something");
				}
			}
		}
		if(listOfDocs.size() == 0) {
			//System.out.println("what");
			return null;
		}
		
		ArrayList<Occurrence> occurrencesOf1 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> occurrencesOf2 = new ArrayList<Occurrence>();
		
		Iterator<String> iterator2 = keys.iterator();
		while(iterator2.hasNext()) {
			String thisKey = iterator2.next();
			//System.out.println(thisKey);
			if(thisKey.equals(kw1) || thisKey.equals(kw2)) {
				if(thisKey.equals(kw1)) {
					for(Occurrence o : keywordsIndex.get(thisKey)) {
						occurrencesOf1.add(o);
					}
				}else {
					for(Occurrence o : keywordsIndex.get(thisKey)) {
						occurrencesOf2.add(o);
					}
				}
				
				
				
				
				
				//THIS WAS INCORRECT FIX IT TO DO WHAT THE PROGRAM SHOULD DO, NOT COMBINE FREQUENCIES
				/*
				for(Occurrence o : keywordsIndex.get(thisKey)) {
					//System.out.println(o.document);
					for(Occurrence c : totalOccurrences) {
						//System.out.println("test");
						if(c.document.equals(o.document)) {
							c.frequency += o.frequency;
							break;
						}
					}
				}
				*/
			}
		}
		/*
		for(Occurrence o : occurrencesOf1) {
			System.out.println(o.document);
		}
		System.out.println("Space");
		for(Occurrence o : occurrencesOf2) {
			System.out.println(o.document);
		}
		
		*/
		
		for(int k = 0; k < occurrencesOf1.size(); k++) {
			for(Occurrence n : occurrencesOf2) {
				//System.out.println("up here");
				//System.out.println(occurrencesOf1.get(k).document);
				//System.out.println(n.document);
				if(occurrencesOf1.get(k).document.equals(n.document)) {
					if(occurrencesOf1.get(k).frequency >= n.frequency) {
						//System.out.println("right here");
						//System.out.println(n.document);
						for(int i = 0; i < occurrencesOf2.size(); i++) {
							//System.out.println("iteration");
							//System.out.println(occurrencesOf2.get(i).document);
							//System.out.println(n.document);
							if(occurrencesOf2.get(i) == n) {
								occurrencesOf2.remove(i);
								break;
							}
						}
						break;
					}else {
						for(int i = 0; i < occurrencesOf1.size(); i++) {
							if(occurrencesOf1.get(i) == occurrencesOf1.get(k)) {
								occurrencesOf1.remove(i);
								k--;
								break;
							}
						}
						break;
					}
				}
			}
			//System.out.println("testing");
		}
		/*
		 	for(Occurrence o : occurrencesOf1) {
			System.out.println(o.document);
		}
		System.out.println("Space");
		for(Occurrence o : occurrencesOf2) {
			System.out.println(o.document);
		}
		*/
		//SORT ARRAYS 1 AND 2, COMBINE INTO SORTED AND GET FIRST 5 ELEMENTS AND RETURN THAT
		ArrayList<Occurrence> top5 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> combined = new ArrayList<Occurrence>();
		for(Occurrence o : occurrencesOf1) {
			combined.add(o);
		}
		for(Occurrence o : occurrencesOf2) {
			combined.add(o);
		}
		/*
		System.out.println("Space");
		for(Occurrence o : combined) {
			System.out.println(o.document);
		}
		*/
		int index = 0;
		Occurrence ptr = null;
		for(int j = 0; j < 5; j++) {
			ptr = null;
			index = 0;
			for(int i = 0; i < combined.size(); i++) {
				if(ptr == null) {
					ptr = combined.get(i);
					index = i;
				}else {
					if(ptr.frequency < combined.get(i).frequency) {
						ptr = combined.get(i);
						index = i;
					}
				}
				
			}
			//System.out.println(index);
			if(combined.get(index) != null) {
			top5.add(combined.remove(index));
			if(combined.size() == 0) {
				j = 5;
			}
			}
		}
		/*
		System.out.println("\n");
		for(Occurrence o : top5) {
			System.out.println(o.document);
		}
		*/
		ArrayList<String> top5Strings = new ArrayList<String>();
		for(Occurrence o : top5) {
			top5Strings.add(o.document);
		}
		return top5Strings;
		
		/*
		Iterator<String> iterator2 = keys.iterator();
		while(iterator2.hasNext()) {
			String thisKey = iterator2.next();
			//System.out.println(thisKey);
			if(thisKey.equals(kw1) || thisKey.equals(kw2)) {
				for(Occurrence o : keywordsIndex.get(thisKey)) {
					System.out.println(o.document);
					for(int i = 0; i < listOfDocs.size(); i++) {
						System.out.println("test");
						if(listOfDocs.get(i).equals(o.document)) {
							frequencies.set(i, frequencies.get(i) + o.frequency);
							
							//System.out.println("hey");
							break;
						}
					}
				}
			}
		}
		
		for(Occurrence o : totalOccurrences) {
			System.out.print(o.document + ": " + o.frequency + "\n");
			
		}
		ArrayList<String> top5 = new ArrayList<String>();
		int currentTop = 0;
		int topIndex = 0;
		for(int i = 0; i < 5; i++) {
			currentTop = 0;
			topIndex = 0;
			for(int j = 0; j < totalOccurrences.size(); j++) {
				if(totalOccurrences.get(j).frequency > currentTop) {
					currentTop = totalOccurrences.get(j).frequency;
					topIndex = j;
				}
				top5.add(totalOccurrences.get(j).document);
				totalOccurrences.remove(j);
				if(totalOccurrences.size() == 0) {
					break;
				}
			}
		}
		for(String s : top5) {
			System.out.println(s);
		}
		return top5;
		
		
		/*
		for(int i = 0; i < 5; i++) {
			
		}
		*/
		/** COMPLETE THIS METHOD **/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		//return null;
	
	}
}
