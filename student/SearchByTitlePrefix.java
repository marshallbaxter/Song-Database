package student;

import java.util.ArrayList;

import student.Song.CmpTitle;

public class SearchByTitlePrefix {

  // Comparator object
  private CmpTitle cmpTitle = new CmpTitle();

  // AddendumList to store songs
  private AddendumList<Song> songs = new AddendumList<Song>(cmpTitle);

  // constructor that takes in a SongCollection and uses the data 
  // from it to build an AddendumList of songs ordered by title
  public SearchByTitlePrefix(SongCollection sc) {
    // Get the array of songs from the SongCollection
     Song[] allSongs = sc.getAllSongs();
    
    // add each song to AddendumList, sorting by title
     for(int i = 0; i < allSongs.length; i++) {
       songs.add(allSongs[i]);
     }
     System.out.println("Comparisons to build AddendumList: " + cmpTitle.cmpCnt);

  }

  // search() method that takes a title prefix (such as “Angel”) 
  // and returns an array containing all of the matching songs.
  // This will use the subList() and toArray() methods
  public Song[] search(String titlePrefix) {
    cmpTitle.cmpCnt = 0;

    // create temporary song object to hold title
    Song fromElement = new Song(null, titlePrefix, null);
    
    // temporary song object to hold next successive string 
    // that doesn’t match the prefix. This is obtained by incrementing 
    // the last character,“Angel” -> “Angem” 
    int prefixLength = titlePrefix.length();
    String allButLast = titlePrefix.substring(0, prefixLength - 1);
    String nextString = allButLast + (char)(titlePrefix.charAt(prefixLength-1)+1);
        
    Song toElement = new Song(null, nextString, null);
    
    // use the two song objects to get a subLst of matching songs
    AddendumList<Song> foundTitles = songs.subList(fromElement, toElement);
    
    System.out.println("Comparisons to search AddendumList for "+ titlePrefix +": " + cmpTitle.cmpCnt);
    
    // return the array of songs
    return foundTitles.toArray(new Song[foundTitles.size]);
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("usage: prog songfile [search string]");
      return;
    }

    SongCollection sc = new SongCollection(args[0]);
    SearchByTitlePrefix sbtp = new SearchByTitlePrefix(sc);

    if (args.length > 1){
      System.out.println("searching for: "+args[1]);
      Song[] byTitleResult = sbtp.search(args[1]);

      // show first 10 matches
      // listSongs defaults to returning the first 10
      SongCollection.listSongs(byTitleResult);
    }

  }

}
