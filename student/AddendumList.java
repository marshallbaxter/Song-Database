package student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * AddendumList.java
 * Marshall Baxter
 * 
 * Initial starting code by Prof. Boothe Sep. 2019
 *
 * To an external user and AddendumList appears as a single sorted list ordered
 * by the comparator. Duplicates are allowed, and new items with duplicate keys
 * are added after any matching items.
 * 
 * Internally, at its simplest, an AddendumList is one big sorted array, but
 * additions are added to a small secondary (addendum) array. Searching first
 * checks the big array, and if a match is not found it then checks the
 * addendum array. Searching is fast because it can use binary search, and
 * adding is fast because adds are added into the small addendum array.
 * 
 * In fact there can be multiple levels of addendum arrays of exponentially
 * decreasing sizes. Searching works it's way through all of them. 
 * 
 * All additions are to an array of minimum size. When the minimum sized array
 * becomes full, it is possibly merged with the preceding array. Merging occurs
 * when the preceding array is of equal or greater size. This merging might
 * then continue up the chain to the top.
 * 
 * After a merger there will be no minimum sized array. A new one is created
 * upon the next addition.
 *  
 * The implementation the AddendumList is stored internally as an array of
 * arrays.
 * 
 * The top level array (called level 1) contains references to the 2nd level
 * arrays.
 * 
 * NOTE: normally fields, internal nested classes and non API methods should
 *       all be private, however they have been made public so that the tester
 *       can access them.
 */
@SuppressWarnings("unchecked")  // added to suppress warnings about all the
// type casting of Object arrays
public class AddendumList<E> implements Iterable<E> {
  private static final int L1_STARTING_SIZE = 4;
  private static final int L2_MINIMUM_SIZE = 4;   
  public int size;             // total number of elements stored
  public Object[] l1array;     // really is an array of L2Array, but the 
  // compiler won't let me cast to that
  public int l1numUsed;
  private Comparator<E> comp;

  // create an empty list
  // Always has at least 1 second level array even if empty. It makes the
  // code easier. 
  // (DONE)
  public AddendumList(Comparator<E> c){
    size = 0;
    // the l1array is really an array of L2Array's, but for backwards
    // compatibility reasons Java does not allow array declarations
    // involving generic types. Instead we must declare it as just an array
    // of Objects.
    l1array = new Object[L1_STARTING_SIZE]; // because no generic arrays
    l1array[0] = new L2Array(L2_MINIMUM_SIZE);    // first 2nd level array
    l1numUsed = 1;
    comp = c;
  }

  // nested class for 2nd level arrays
  // (DONE)
  public class L2Array {
    public E[] items;  
    public int numUsed;

    public L2Array(int capacity) {
      // an L2Array is really an array of the generic type E elements,
      // but you can't create an array of a generic type, so it is created
      // as an array of Objects and then cast to and array of E
      items = (E[])new Object[capacity];
      numUsed = 0;
    }
  }

  //total size (number of entries) in the entire data structure
  // (DONE)
  public int size(){
    return size;
  }

  // null out all references so that the garbage collector can recycle them
  // but keep the (now empty) l1array 
  // (DONE)
  public void clear(){
    size = 0;
    Arrays.fill(l1array, null);  // clear l1 array
    l1array[0] = new L2Array(L2_MINIMUM_SIZE);    // first 2nd level array
    l1numUsed = 1;
  }


  // Find the index of the 1st matching entry in the specified array.
  // If the item is not found, it returns a negative value
  // (-insertion point -1). 
  // The insertion point is the point at which the key would be inserted into
  // the array, which may be an unused slot at the end of the array
  public int findFirstInArray(E item, L2Array a){

    // start with binary search
    int foundIndex = Arrays.binarySearch(a.items, 0,a.numUsed, item, comp);

    // if binary search finds something, walk back to find first one
    while(foundIndex > 0) {
      E earlierArrayItem = a.items[foundIndex-1];
      if (comp.compare(item, earlierArrayItem) == 0) {
        foundIndex--;
      }
      else {
        break;
      }       

    }

    // If the item is not found, return a negative value
    // (-insertion point -1) from binary Search
    return foundIndex;
  }

  /**
   * check if list contains a match
   * searches all levels, largest levels first
   */
  public boolean contains(E item){
    // call findFirstInArray for each L2 array in order
    // starting at the largest (index 0) if a integer
    // >= 0 is returned (indicating a match), 
    // then contains will return true, otherwise contains 
    // will return false

    for(int i = 0;i < l1numUsed; i++){
      if(findFirstInArray(item, (L2Array)l1array[i])>=0) {
        return true;
      }
    }

    return false;  // never found a match
  }

  // find the index of the insertion point of this item, which is
  // the index after any smaller or matching entries
  // this might be an unused slot at the end of the array
  // note: this will only be used on the last (very small) addendum array
  //       before this is called, the caller should make sure that there is
  //       space to add an item
  public int findIndexAfter(E item, L2Array a){

    // start with binary search
    int foundIndex = Arrays.binarySearch(a.items, 0,a.numUsed, item, comp);

    // if item is not found, return insertion point from binary search
    if (foundIndex < 0) {
      return Math.abs(foundIndex) - 1;
    }

    // otherwise, find last matching entry matching item and return
    // the next index
    // test for null elements, comparator can't handle them
    while(a.items[foundIndex] != null && comp.compare(item, a.items[foundIndex]) == 0) {
      foundIndex++;
    }

    return foundIndex;
  }

  /** 
   * Add this new item after any other matching items.
   * 
   * Always adds to the last (and minimum sized) addendum array. 
   * 
   * If the last array is full or not minimum sized, it first performs
   * any required merging and then makes a new minimum size array
   * for adding into.
   * 
   * Use findIndexAfter() to find the insertion position.
   * Use merge1Level() for merging.
   * 
   * Remember to increment numUsed for the L2Array inserted into, and
   * increment size for the whole data structure.
   */
  public boolean add(E item){
    // TO DO

    // Check if last array has space for an item
    L2Array lastArray = (AddendumList<E>.L2Array) l1array[l1numUsed - 1];

    //Just check if the last element is null, means there is space for more
    if (lastArray.items[L2_MINIMUM_SIZE-1] == null) {

      // if there is space, find insert point
      int insertPoint = findIndexAfter(item, lastArray);

      // shuffle later elements to make room
      // loop from end to insert point 
      for (int i = lastArray.numUsed; i > insertPoint; i--) {
        lastArray.items[i] = lastArray.items[i-1];
      }

      // insert item and update array numUsed and overall size
      lastArray.items[insertPoint] = item;
      lastArray.numUsed++;
      size++;

      return true;  
    }

    // if last array is full, check if arrays need to be merged
    // merge arrays as required

    // while loop if there are at least 2 arrays in use
    while (l1numUsed >= 2) {
      // grab arrays to compare
      L2Array upperArray = (AddendumList<E>.L2Array) l1array[l1numUsed - 2];
      L2Array bottomArray = (AddendumList<E>.L2Array) l1array[l1numUsed -1];

      // Check if arrays need to be merged
      if(bottomArray.numUsed >= upperArray.numUsed) {
        merge1Level();
      }
      else {
        // If arrays don't need to be merged stop checking
        break;
      }
    }

    // check if l1array is full
    // grow l1array by 4
    if (l1array.length == l1numUsed) {     
      l1array = Arrays.copyOf(l1array, l1array.length + 4);
    }

    // create minimum size addendum array    
    // add item to the new array    
    L2Array newL2Array = new L2Array(L2_MINIMUM_SIZE);
    newL2Array.items[0] = item;
    newL2Array.numUsed++;
    size++;

    l1array[l1numUsed] = newL2Array;
    l1numUsed++;

    return true;
  }

  // Merge the last two levels.
  // If there are matching items, those from the earlier array go first in
  // the merged array.
  public void merge1Level() {

    //check that there are at least two l2arrays in use
    if (l1numUsed < 2) {
      return;
    }

    // get references to last two levels
    L2Array upperArray = (AddendumList<E>.L2Array) l1array[l1numUsed - 2];
    L2Array bottomArray = (AddendumList<E>.L2Array) l1array[l1numUsed -1]; 


    // make new array to merge into
    // length is sum of the number of elements in the other arrays
    final int newArraySize = upperArray.numUsed + bottomArray.numUsed;
    L2Array newArray = new L2Array(newArraySize);

    // variables for current indexes of merge points
    // for first array, second array, and new array
    int upperArrayPos = 0;
    int bottomArrayPos = 0;


    // while loop until new array is full
    while (newArray.numUsed < newArraySize) {

      // if upper array is out of elements,
      // end of array or current element is null
      // finish filling with bottom array
      if(upperArrayPos == upperArray.numUsed || upperArray.items[upperArrayPos] == null) {
        newArray.items[newArray.numUsed] = bottomArray.items[bottomArrayPos]; 

        bottomArrayPos++;
        newArray.numUsed++;
        continue; 

      }

      // if bottom array is out of elements,
      // end of array or current element is null
      // finish filling with upper array
      if(bottomArrayPos == bottomArray.numUsed || bottomArray.items[bottomArrayPos] == null) {
        newArray.items[newArray.numUsed] = upperArray.items[upperArrayPos]; 

        upperArrayPos++;
        newArray.numUsed++;
        continue;

      }

      // otherwise compare current elements of first and second
      // arrays and insert into new array, give priority to 
      // elements of first array

      E firstItem = upperArray.items[upperArrayPos];
      E secondItem = bottomArray.items[bottomArrayPos];

      if(comp.compare(firstItem, secondItem)<= 0) {
        newArray.items[newArray.numUsed] = upperArray.items[upperArrayPos]; 

        upperArrayPos++;
        newArray.numUsed++;
      }
      else {
        newArray.items[newArray.numUsed] = bottomArray.items[bottomArrayPos]; 

        bottomArrayPos++;
        newArray.numUsed++;

      }
    }

    // update array layout
    // remove last array (update number of arrays)
    l1array[l1numUsed - 1] = null;
    // set what is now the last array to be the new array
    l1array[l1numUsed - 2] = newArray;
    l1numUsed--;

  }

  // Merge all levels
  // This is used: by iterator(), toArray() and subList()
  // This makes these easy to implement, and the full merge time would
  // likely be required for these operations anyway.
  // Note: the merging will likely cause the size of the array to no longer
  //       be a power of two.
  private void mergeAllLevels() {

    // While loop until there is one array level remaining
    while(l1numUsed >= 2) {

      // call merge1level
      merge1Level();
    }
  }

  /**
   * copy the contents of the AddendumList into the specified array
   * @param a - an array of the actual type and of the correct size
   * @return the filled in array
   */
  public E[] toArray(E[] a){

    // Merge AddendumList to single level array
    mergeAllLevels();

    // Check that specified array of actual type and correct size
    if(a.length >= size) {

      // Grab reference to top and now only level2 array
      L2Array l2array = (AddendumList<E>.L2Array) l1array[0];

      // Copy array to a
      System.arraycopy(l2array.items, 0, a, 0, size);
    }

    return a;
  }

  /**
   * Returns a new independent AddendumList whose elements range 
   * from fromElement(inclusive) to toElement(exclusive).
   * The original list retains its original contents.
   * @param fromElement
   * @param toElement
   * @return the sublist
   */
  public AddendumList<E> subList(E fromElement, E toElement){

    // Merge AddendumList to single level array
    mergeAllLevels(); 

    // Grab reference to top and now only level2 array
    L2Array l2array = (AddendumList<E>.L2Array) l1array[0];

    // find index of fromElement
    int startIndex = findFirstInArray(fromElement, l2array);

    // find index of toElement
    int endIndex = findFirstInArray(toElement, l2array);

    int listLength;
    // find length of the subList
    if (startIndex < 0) {
      startIndex = Math.abs(startIndex) - 1;
    }

    if (endIndex < 0) {
      endIndex = Math.abs(endIndex) - 1;
    }

    listLength = endIndex - startIndex;

    // Create level 2 array to hold the elements from 
    // fromElement(inclusive) to toElement(exclusive)
    L2Array subList = new L2Array(listLength);

    System.arraycopy(l2array.items, startIndex, subList.items, 0, listLength);
    subList.numUsed = listLength;


    // make new AddendumList with the range of elements found
    AddendumList<E> newList = new AddendumList<E>(comp);
    newList.l1array[0]= subList;
    newList.size = listLength;

    return newList;
  }

  /**
   * Returns an iterator for this list.
   * This method just merges the items into a single array and creates an
   * instance of the inner Itr() class
   * (DONE)   
   */
  public Iterator<E> iterator() {
    mergeAllLevels();
    return new Itr();
  }

  /**
   * Iterator 
   */
  private class Itr implements Iterator<E> {
    int index;

    /*
     * initialize iterator to the start of list
     */
    Itr(){
      index = 0;
    }

    /**
     * check if more items
     */
    public boolean hasNext() {

      // reference to the l2 array, for readability
      L2Array l2array = (AddendumList<E>.L2Array) l1array[0];

      // check if the current index is less than the number used
      if(index < l2array.numUsed){
        return true;
      }

      return false;
    }

    /**
     * return item and move to next
     * throws NoSuchElementException if off end of list
     */
    // check if the current index is less than the number used
    public E next() {
      L2Array l2array = (AddendumList<E>.L2Array) l1array[0];
      if(index < l2array.numUsed){

        // return item and increment index
        return l2array.items[index++];
      }

      // off the end of array throw exception
      throw new NoSuchElementException();
    }

    /**
     * Remove is not implemented. Just use this code.
     * (DONE)
     */
    public void remove() {
      throw new UnsupportedOperationException();	
    }
  }
}
