package student;

public class RankedSong implements Comparable<RankedSong>{
  
  // Fields:
  private int rankVal;  
  private Song rankedSong;
  
  // Constructor 
  public RankedSong(int rankVal, Song song) {
    this.rankedSong = song;
    this.rankVal = rankVal;
    
  }
  
  // Getters
  public int getRankVal(){
    return rankVal;
  }
  
  public Song getSong() {
    return rankedSong;
  }
  
  // toString
  // format: rank artist, "title"
  // 13 Aerosmith, "Beyond Beautiful"
  public String toString() {
    return "" + rankVal + " " + rankedSong.getArtist() + ", \"" + rankedSong.getTitle() + "\"";
    
  }
  
  // TODO compareTo
  // Primary key: rank value
  // Secondary key: Song's artist
  // tertiary key: Song's title
  public int compareTo(RankedSong rankedSong) {
    
    // start by comparing rank values
    int compare = this.rankVal - rankedSong.rankVal;
    
    // if rank values match, compare artists
    if (compare == 0) {
      compare = this.getSong().getArtist().compareToIgnoreCase(rankedSong.getSong().getArtist());
    }
    
    // if rank values & artists match, compare title
    if (compare == 0) {
      compare = this.getSong().getTitle().compareToIgnoreCase(rankedSong.getSong().getTitle());
    }
    
    return compare;
  }

  public static void main(String[] args) {
    
    RankedSong song1 = new RankedSong(13, new Song("Aerosmith", "Beyond Beautiful",null));
    RankedSong song2 = new RankedSong(13, new Song("Beatles, The", "She Loves You",null));
    RankedSong song3 = new RankedSong(13, new Song("Beatles, The", "That Means A Lot",null));
    RankedSong song4 = new RankedSong(20, new Song("Pink Floyd", "Apples And Oranges",null));
    
    
    System.out.println(song1.compareTo(song1));
    System.out.println(song1.compareTo(song2));
    System.out.println(song1.compareTo(song3));
    System.out.println(song1.compareTo(song4));
    
    System.out.println(song1.compareTo(song1));
    System.out.println(song2.compareTo(song1));
    System.out.println(song3.compareTo(song1));
    System.out.println(song4.compareTo(song1));

  }

}
