package student;

import java.util.PriorityQueue;

public class SearchByLyricsPhrase {

  //Fields: 
  
  private SearchByLyricsWords sblw;

  // Constructor
  public SearchByLyricsPhrase(SongCollection sc) {

    // build the SongCollection
    this.sblw = new SearchByLyricsWords(sc);

  }

  // search method, can limit number of results by passing positive 
  // passing "0" will return all valid matches
  public Song[] search(String lyricsPhrase, int numResults) {

    // PriorityQueue to hold ranked songs
    PriorityQueue<RankedSong> rankedSongs = new PriorityQueue<RankedSong>();

    // get smaller array of only songs that contain all words in phrase
    Song[] containsAllWords = sblw.search(lyricsPhrase);

    // get rank for each song in smaller array and add to PriorityQueue
    for(Song currentSong: containsAllWords) {
      // get ranking for each song 
      int ranking = PhraseRanking.rankPhrase(currentSong.getLyrics(),lyricsPhrase);

      // if the ranking is greater than zero, add song to PriorityQueue with ranking value
      if(ranking > 0) {
        currentSong.rankedValue = ranking;
        rankedSongs.add(new RankedSong(ranking, currentSong));
      }
    }

    // Build array to return, limited in length by lesser of numResults requested
    // or size of the PrioityQueue
    if (numResults == 0) {
      numResults = rankedSongs.size();
    }
    
//    System.out.println("searching for: " + lyricsPhrase);
//    System.out.println("Total songs = " + numResults + " :");
    
    Song[] output = new Song[numResults];

    for(int i = 0; i < numResults; i++) {
//      System.out.println(rankedSongs.peek().toString());
      output[i] = rankedSongs.poll().getSong();
    }
    
    return output;
  }

  // search method without limiting number of results
  public Song[] search(String lyricsPhrase) {
    return search(lyricsPhrase, 0);
  }


  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("usage: prog songfile [search string]");
      return;
    }

    if (args.length < 2) {
      System.err.println("usage: Add search args");
      return;
    }

    SongCollection sc = new SongCollection(args[0]);

    SearchByLyricsPhrase sblp = new SearchByLyricsPhrase(sc);

    Song[] results = sblp.search(args[1]);

    System.out.println("Total songs = " + results.length + ", first 10 matches:");
    System.out.println("rank artist title");
    for (int i = 0; i < 10; i++) {
     System.out.println(results[i].rankedValue + " " + results[i].toString());
    }

  }

}
