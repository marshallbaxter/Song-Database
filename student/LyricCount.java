package student;

public class LyricCount implements Comparable<LyricCount>{
	
	// fields: 
	// word (string): the common word that a collection of songs share
	private String word;
	
	// songCount (integer): number of songs that share the word
	private int songCount;
	
	//  constructor, accept word and songCount and set the fields
	public LyricCount(String word, int songCount) {
		this.word = word;
		this.songCount = songCount;
	}
	
	// getters
	public String getWord(){
		return word;
	}
	
	public int getSongCount() {
		return songCount;
	}
	
	// toString
	public String toString() {
		return "" + songCount + " songs contain the word " + word;
	}

	// the default comparison of LyricCount
    // * primary key: songCount, secondary key: word
	@Override
	public int compareTo(LyricCount counter) {

		int compare = this.getSongCount() - counter.getSongCount();
		
		if (compare == 0) {
			compare = this.getWord().compareToIgnoreCase(counter.getWord());
		}
		
		return compare;
	}
	
	public static void main(String[] args) {
		// testing
		LyricCount l1 = new LyricCount("word 1", 10);
		LyricCount l2 = new LyricCount("word 2", 20);
		
		System.out.println("Lyric1 vs Lyric2 = " + l1.compareTo(l2));
		System.out.println("Lyric1 vs Lyric1 = " + l1.compareTo(l1));
		System.out.println("Lyric2 vs Lyric1 = " + l2.compareTo(l1));


		

	}

}
