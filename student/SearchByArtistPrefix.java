package student;
import java.io.*;
import java.util.*;

import student.Song.CmpArtist;

/*
 * SearchByArtistPrefix.java
 * starting code
 * Boothe 2016
 */
public class SearchByArtistPrefix {

  private Song[] songs;  // The constructor fetches and saves a reference to the song array here
  
  public SearchByArtistPrefix(SongCollection sc) {
    songs = sc.getAllSongs();
  }
  
  private int backwardCmpCnt;
  private int forwardCmpCnt;
  private CmpArtist cmpArtist = new CmpArtist();

  /**
   * find all songs matching artist prefix
   * uses binary search
   * should operate in time log n + k (# matches)
   */
  public Song[] search(String artistPrefix) {

    // arrayList to hold matching songs
    ArrayList<Song> songList = new ArrayList<Song>();

    // create temporary song object to hold artist
    Song artistSong = new Song(artistPrefix,null,null);

    // binary search songs array to find a song given artist  
    int matchIndex = Arrays.binarySearch(songs, artistSong, cmpArtist);

    final int binarySearchCounter = ((CmpCnt)cmpArtist).getCmpCnt();
//    System.out.println("After binarySearch: " + binarySearchCounter);
    
    // get the absolute value of the binary search result,
    // in the event of a incomplete match the negative value
    // returned from binary search is index to start looking
    final int index = Math.abs(matchIndex);

    // after finding a index, start comparing prefixes around index
    // look backward
    findPrefixBackward(index, artistPrefix, songList);
    
    final int backwardSearchCounter = ((CmpCnt)cmpArtist).getCmpCnt() - binarySearchCounter;
//    System.out.println("During backwardSearch: " + backwardSearchCounter);
    
    // look forward
    findPrefixForward(index + 1, artistPrefix, songList);
    
    final int forwardSearchCounter = ((CmpCnt)cmpArtist).getCmpCnt() - binarySearchCounter - backwardSearchCounter;
//    System.out.println("During forwardSearch: " + forwardSearchCounter);
    
//    System.out.println("Total during search: " + (backwardSearchCounter+forwardSearchCounter));
    
    return songList.toArray(new Song[0]);
  }

  // method recursively finds/adds songs in array  
  // matching given prefix at indexes before one provided
  // Parameters:
  // index: where to start looking
  // artistPrefix: prefix to look for
  // songList: arrayList to fill with found songs
  public void findPrefixBackward(int index, final String artistPrefix, ArrayList<Song> songList) {

    // get song at current index
    Song testSong = songs[index];

    // get artist from current song
    String testArtist = testSong.getArtist().toLowerCase();

    
    // check if prefix matches
    cmpArtist.cmpCnt++;
    if (testArtist.startsWith(artistPrefix.toLowerCase())) {

      // if index > 0 look at earlier indexes
      if (index > 0) {
        findPrefixBackward(index - 1, artistPrefix, songList);
      }

    // then add current song to array
    songList.add(testSong);
    }

    return;
  }
  
  // method recursively finds/adds songs in array  
  // matching given prefix at indexes after one provided
  // Parameters:
  // index: where to start looking
  // artistPrefix: prefix to look for
  // songList: arrayList to fill with found songs
  public void findPrefixForward(int index, final String artistPrefix, ArrayList<Song> songList) {

    // get song at current index
    Song testSong = songs[index];

    // get artist from current song
    String testArtist = testSong.getArtist().toLowerCase();

    // check if prefix matches
    cmpArtist.cmpCnt++;
    if (testArtist.startsWith(artistPrefix.toLowerCase())) {
      // current song to array
      songList.add(testSong);

      // then if index < songs.length - 1 look at later indexes
      if (index < songs.length - 1) {
        findPrefixForward(index + 1, artistPrefix, songList);
      }
    }

    return;
  }


  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("usage: prog songfile [search string]");
      return;
    }

    SongCollection sc = new SongCollection(args[0]);
    SearchByArtistPrefix sbap = new SearchByArtistPrefix(sc);

    if (args.length > 1){
      System.out.println("searching for: "+args[1]);
      Song[] byArtistResult = sbap.search(args[1]);

      // show first 10 matches
      // listSongs defaults to returning the first 10
      SongCollection.listSongs(byArtistResult);
    }
  }
}
