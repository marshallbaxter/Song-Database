package student;

import java.io.*;
import java.util.*;

/*
 * SongCollection.java
 * Read the specified data file and build an array of songs.
 * 
 * Starting code by Prof. Boothe 2016
 */
public class SongCollection {

  private Song[] songs;

  public SongCollection(String filename) {

    Scanner fileScanner = null;

    // read in the song file and build the songs array
    try {
      fileScanner = new Scanner(new File(filename));

    }catch (Exception e) {
      System.err.println(filename + " not found");
      return;
    }

    // ArrayList to hold songs while reading
    ArrayList<Song> songList = new ArrayList<Song>();

    // Variables to hold artist & title while reading
    String artist = "";
    String title = "";

    // look at file line by line and pull needed information
    while (fileScanner.hasNext()){

      // get first line from file
      String line = fileScanner.nextLine();

      // Look for artist
      if(line.startsWith("ARTIST")) {

        // extract artist from line
        artist=line.substring(line.indexOf("\"") + 1,line.lastIndexOf("\""));
        continue;
      }

      // look for title
      if(line.startsWith("TITLE")) {

        // extract title from line
        title=line.substring(line.indexOf("\"") + 1,line.lastIndexOf("\""));
        continue;
      }

      // look for lyrics
      else if(line.startsWith("LYRICS")) {
        StringBuilder lyrics = new StringBuilder(line.substring(line.indexOf("\"") + 1));

        // Once first line of lyrics found, grab all lines until closing quote found 
        while(fileScanner.hasNextLine()) {
          line = fileScanner.nextLine();
          if(line.compareTo("\"") == 0) {
            break;
          }
          lyrics.append("\n" + line);
        }

        // Add song to the array
        songList.add(new Song(artist, title, lyrics.toString()));

        // reset Artist and Title fields
        artist = "";
        title = "";
      }
    }

    // sort the songs array
    songs = songList.toArray(new Song[0]);
    Arrays.sort(songs);
  }

  // returns the array of all Songs
  // this is used as the data source for building other data structures
  public Song[] getAllSongs() {
    return songs;
  }


  // method to list number of songs and show artist & title of the first few
  // integer as parameter to control number of songs listed
  public static void listSongs(Song[] songs,int countToPrint) {

    // Check for songs before listing them
    if (songs.length < 1) {
      System.out.println("No results returned.");
      return;
    }

    System.out.print("Total songs = "+ songs.length);
    if(songs.length > 10) {
    	System.out.println(", first 10 matches:");
    }
    else {
    	System.out.println(" :");
    }

    for (int i = 0; i < countToPrint && i < songs.length; i++) {
      System.out.println(songs[i].getArtist() + ", \"" + songs[i].getTitle() + "\"");
    }

  }

  // method override to list number of songs and output the first 10
  // when no arguments are passed
  public static void listSongs(Song[] songs) {
    listSongs(songs, 10);
  }

  // testing method
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("usage: prog songfile");
      return;
    }

    SongCollection sc = new SongCollection(args[0]);

    //show song count and first 10 songs (name & title only, 1 per line)
    listSongs(sc.getAllSongs());


  }
}