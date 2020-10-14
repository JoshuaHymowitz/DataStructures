package lse;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class LSETester {

	public static void main(String[] args) throws FileNotFoundException {
		LittleSearchEngine tester = new LittleSearchEngine();
		tester.makeIndex("docs.txt", "noisewords.txt");
		tester.top5search("blurp", "blap");
		
		
		//tester.loadKeywordsFromDocument("RapEm.txt");
		
		/*
		String string1 = "distance..";
		String string2 = "equi-distant";
		String string3 = "Rabbit";
		String string4 = "Through";
		String string5 = "we're";
		String string6 = "World..";
		String string7 = "World!?";
		String string8 = "What,ever";
		String string9 = "whatever-..";
		String string10 = "whatever..";
		
		System.out.println(tester.getKeyword(string1));
		System.out.println(tester.getKeyword(string2));
		System.out.println(tester.getKeyword(string5));
		System.out.println(tester.getKeyword(string7));
		System.out.println(tester.getKeyword(string10));
		
		
		Occurrence song1 = new Occurrence("All the Stars", 291);
		Occurrence song2 = new Occurrence("Pray For Me", 171);
		Occurrence song3 = new Occurrence("King's Dead", 113);
		Occurrence song4 = new Occurrence("HUMBLE.", 783);
		Occurrence song5 = new Occurrence("LOVE.", 328);
		Occurrence song6 = new Occurrence("Stronger", 397);
		Occurrence song7 = new Occurrence("FourFiveSeconds", 443);
		Occurrence song8 = new Occurrence("Ni**as in Paris", 404);
		Occurrence song9 = new Occurrence("POWER", 288);
		Occurrence song10 = new Occurrence("Runaway", 81);
		
		ArrayList<Occurrence> album = new ArrayList<Occurrence>();
		//album.add(song4);
		album.add(song7);
		album.add(song8);
		album.add(song5);
		album.add(song1);
		album.add(song9);
		album.add(song2);
		album.add(song3);
		album.add(song6);
		album.add(song10);
		album.add(song4);
		
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		indices = tester.insertLastOccurrence(album);
		for(int n : indices) {
			System.out.println(n);
		}
		*/
	

	}

}
