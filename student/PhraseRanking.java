package student;

import java.util.*;

public class PhraseRanking {


  // takes in 2 strings, the lyrics and the lyrics phrase being looked for. 
  // returns the ranking of the lyrics with respect to a search for lyricsPhrase
  static int rankPhrase(String songLyrics, String lyricSearchPhrase) {

    // make searches not case sensitive 
    songLyrics = songLyrics.toLowerCase();
    lyricSearchPhrase = lyricSearchPhrase.toLowerCase();

    // Integer to store the best matched value
    int bestMatch = Integer.MAX_VALUE;

    // Array of words from the searched Phrase
    ArrayList<String> searchPhrase = new ArrayList<>(); 

    // find words by looking for alpha chars
    // int to track start of word, int to track current positon
    // add to array when non alpha word is reached
    int startOfWord = 0;
    boolean trackingWord = false;
    for(int i = 0; i < lyricSearchPhrase.length(); i++) {
      char currentChar = lyricSearchPhrase.charAt(i);

      if (!trackingWord & Character.isLetter(currentChar)) {
        trackingWord = true;
        startOfWord = i;
      }

      else if(trackingWord & !Character.isLetter(currentChar)) {
        searchPhrase.add(lyricSearchPhrase.substring(startOfWord, i));
        trackingWord = false;
      }

      else if(i == lyricSearchPhrase.length() - 1) {
        searchPhrase.add(lyricSearchPhrase.substring(startOfWord));
        break;
      }


    }


    // outerloop, track start of possible match
    int startOfMatch = 0;

    // goes false if can't find searched words in the inner loop
    // we can stop looking
    boolean wordsCanMatch = true;

    while ( startOfMatch < songLyrics.length()) {

      // find the first word
      startOfMatch = songLyrics.indexOf(searchPhrase.get(0), startOfMatch);

      // if the first word is not found, break match not possible
      if (startOfMatch == -1) {
        break;
      }

      // check that letters before and after match are not letters
      // exact word match (love does not match glove)

      // char just before matched word
      char before = '#';
      if(startOfMatch > 0) {
        before = songLyrics.charAt(startOfMatch-1);
      }
      // char just after matched word
      char after = '#';
      if(startOfMatch + searchPhrase.get(0).length() < songLyrics.length()) {
        after = songLyrics.charAt(startOfMatch + searchPhrase.get(0).length());
      }

      //
      if (Character.isLetter(before)||Character.isLetter(after)) {
        startOfMatch++;
      }
      else {
        // valid match for first word found, look for next
        int wordsMatched = 1;
        int endOfMatch = startOfMatch + searchPhrase.get(0).length() - 1;

        // while loop until searched for words are matched in order


        while (wordsMatched < searchPhrase.size()) {

          // find next word,
          endOfMatch = songLyrics.indexOf(searchPhrase.get(wordsMatched), endOfMatch);

          if (endOfMatch == -1 ) {
            break;
          }

          // check before & after for letters
          before = songLyrics.charAt(endOfMatch-1);

          // char just after matched word

          after ='#';
          if (endOfMatch + searchPhrase.get(wordsMatched).length() < songLyrics.length()) {
            after = songLyrics.charAt(endOfMatch + searchPhrase.get(wordsMatched).length());
          }

          if (Character.isLetter(before)||Character.isLetter(after)) {       
            endOfMatch++;
          }
          else {
            wordsMatched++;
            endOfMatch++;
          }       
        }


        if (wordsMatched == searchPhrase.size()) {
          // full match, compare to others
          int matchLength = endOfMatch - 1 - startOfMatch + searchPhrase.get(wordsMatched-1).length();

          if (matchLength < bestMatch) {
            bestMatch = matchLength;
          }

          // ideal match found, stop looking
          if (bestMatch == lyricSearchPhrase.length()) {
            return bestMatch;
          }

        }
        startOfMatch++;
      }

    }

    if (bestMatch == Integer.MAX_VALUE) {
      bestMatch = -1;
    }

    return bestMatch;

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

    SearchByLyricsWords sblw = new SearchByLyricsWords(sc);

    // get array of songs that contain the searched phrase
    // no reason to look at songs without all the words
    Song[] songsToSearch = sblw.search(args[1]);

    //		System.out.println(args[1]);

    for (int i = 0; i < songsToSearch.length; i++) {

      int ranking = PhraseRanking.rankPhrase(songsToSearch[i].getLyrics(),args[1]);
      if (ranking > 0) {
        System.out.println("" + ranking + " " + songsToSearch[i].toString());
      }

    }
  }

}
