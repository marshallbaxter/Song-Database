package student;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class SearchByLyricsWords {

	// Create a TreeMap of Strings to TreeSets of Songs
	private Map<String, TreeSet<Song>> lyricWords = new TreeMap<>();

	// create list of common words
	String[] commonWords = {"the", "of", "and", "a", "to", "in", "is", "you",
			"that", "it", "he", "for", "was", "on", "are", "as","with", "his", "they",
			"at", "be", "this", "from", "I", "have", "or", "by", "one", "had", "not",
			"but", "what", "all", "were", "when", "we", "there", "can", "an", "your",
			"which", "their", "if", "do", "will", "each", "how", "them", "then", "she",
			"many", "some", "so", "these", "would", "into", "has", "more", "her", "two",
			"him", "see", "could", "no", "make", "than", "been", "its", "now", "my",
			"made", "did", "get", "our", "me", "too"};


	public SearchByLyricsWords(SongCollection sc) {

		// Get the array of songs from the SongCollection
		Song[] allSongs = sc.getAllSongs();


		// For every unique word in every song, add that word and song to the map.
		// The words will be the keys for the map, and the values in the map will 
		// be TreeSets of all the songs containing that word

		for(int i = 0; i < allSongs.length; i++) {

			// get array of individual words in lyrics
			String lyrics =allSongs[i].getLyrics();
			String[] splitLyrics = lyrics.split("[^a-zA-Z]+");

			// for each word in array of lyrics
			for(int j = 0; j < splitLyrics.length; j++) {

				// convert to lower case
				String word = splitLyrics[j].toLowerCase();

				// check if common word or single letter
				if(Arrays.asList(commonWords).contains(word) || word.length() <= 1) {
					continue;
				}

				// check if word is already in the set
				TreeSet<Song> songSet = lyricWords.get(word);
				if(songSet != null) {
					// add song to corresponding TreeSet
					songSet.add(allSongs[i]);
					continue;
				}
				else {
					// make new treeSet containing the song 
					TreeSet<Song> newSongSet = new TreeSet<Song>();
					newSongSet.add(allSongs[i]);
					// add treeSet to TreeMap for corresponding word
					lyricWords.put(word, newSongSet);
				}
			}
		}
		return;
	}
	public void statistics() throws IOException {

		// Print the number of keys (K) in the map
		System.out.println("Number of keys (K) in map: " + lyricWords.size());

		//Count number of  Song references stored in all the sets
		Collection<TreeSet<Song>> lyricWordsCollection = lyricWords.values();

		int songRefCount = 0;

		for(TreeSet<Song> songs:lyricWordsCollection) {
			songRefCount += songs.size();
		}

		System.out.println("Number of Song references stored (S): " + songRefCount);

		// output keys to file, for debugging
//		FileWriter fileWriter = new FileWriter("output.txt");
//		PrintWriter printWriter = new PrintWriter(fileWriter);   
//		Set<String> keys = lyricWords.keySet();
//		for(String element : keys) {
//			printWriter.println(element);
//		} 
//		printWriter.close();
	}

	public String[] topWords(int numWords) {
		
		// Hold the word/lyricCount pairs
		ArrayList<LyricCount> topWords = new ArrayList<>();
		
		String[] output = new String[numWords];
		
		// Get the word/lyricCount pairs from the lyricWords tree set
		for(Entry<String, TreeSet<Song>> word:lyricWords.entrySet()) {
			topWords.add(new LyricCount(word.getKey(),word.getValue().size()));		
		}
		
		// Sort array
		Collections.sort(topWords);
		
		// get numWords biggest words from the topWords array and
		// put in output String array
		for( int i = 0; i < numWords; i++) {
			int topWordIndex = topWords.size()- 1 - i;
			
			output[i] = topWords.get(topWordIndex).getWord();
		}
		
		return output;
	}

	// method to return the top 10 words, calls topWords(10)
	public String[] topWords() {
		return topWords(10);
	}

	public Song[] search(String lyrics) {
		

		// TreeSet for songs that include lyrics searched for
		TreeSet<Song> foundSongs = new TreeSet<>();

		// process string, ignore common words, ignore punctuation and single letters,  
		String[] splitLyrics = lyrics.split("[^a-zA-Z]+");

		// process each word in the search (for loop)
		for(int i = 0; i < splitLyrics.length; i++) {

			// check that word has at least 2 letters
			if (splitLyrics[i].length() < 2) {
				continue;
			}

			// convert all words to lowercase
			splitLyrics[i] = splitLyrics[i].toLowerCase();

			// remove any common words (continue to next word)
			if(Arrays.asList(commonWords).contains(splitLyrics[i])){
				continue;
			}

			// check if word is in the set
			if(lyricWords.containsKey(splitLyrics[i])) {

				// if first lyric, add matching songs to foundSongs      
				if (foundSongs.isEmpty()) {
					foundSongs.addAll(lyricWords.get(splitLyrics[i]));
				}

				// else, remove songs that don't contain the word
				else {   
					// lookup each word in lyricWords map and remove matching songs from foundSongs TreeSet
					TreeSet<Song>songs = lyricWords.get(splitLyrics[i]);
					foundSongs.retainAll(songs);
					
					// if all songs have been removed stop checking
					if(foundSongs.isEmpty()) {
						break;
					}
					
				}
				
			}   
		}

		// return an array of the songs that include all the words
		return  foundSongs.toArray(new Song[0]);
	}


	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.err.println("usage: prog songfile [search string]");
			return;
		}

		if (args.length < 2) {
			System.err.println("usage: Add search args");
			return;
		}

		SongCollection sc = new SongCollection(args[0]);
		SearchByLyricsWords sblw = new SearchByLyricsWords(sc);
//		sblw.statistics();
		
		
		System.out.println("Search: " + args[1]);
		SongCollection.listSongs(sblw.search(args[1]));

		// Add Extra analysis for top ten words
		if(Arrays.asList(args).contains("-top10words")) {
			System.out.println("Extra analysis -top10words:");
			String[] top10words = sblw.topWords();
			for(int i = 0; i < 10; i++) {
				System.out.println(top10words[i]);
			}
		}

	}

}
