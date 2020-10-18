package structures;

// this class demonstrates using an object's hash code to calculate the 
// index into the array

public class ArrayHashTable<K, V> implements HashTable<K, V> {

  protected static final int defaultCapacity = 10;
  protected static final double defaultLoadFactor = 0.7;
  protected static final String defaultCollisionHandler = "linear";

  protected K[] keyTable;
  protected V[] valueTable;
  protected int capacity;
  protected boolean[] flag;
  protected String collisionHandler;
  protected double loadFactorLimit;
  protected int count;

  /**
   * Default Constructor.
   */
  public ArrayHashTable() {
    this(defaultCapacity, defaultLoadFactor, defaultCollisionHandler);
  }

  /**
   * Default Constructor.
   */
  public ArrayHashTable(String collisionHandler) {
	  this(defaultCapacity, defaultLoadFactor, collisionHandler);
  }

  /**
   * Default Constructor.
   */
  public ArrayHashTable(int capacity, String collisionHandler){
	  this(capacity, defaultLoadFactor, collisionHandler);
  }

  
  /**
   * Constructor.
   */
  public ArrayHashTable(int capacity, double loadFactor, String collisionHandler){
    // TODO 1: complete the constructors
	  this.capacity = capacity;
	  loadFactorLimit = loadFactor;
	  this.collisionHandler = collisionHandler;
	  count = 0;
	  keyTable = (K[]) new Object[capacity];
	  valueTable = (V[]) new Object[capacity];
	  flag = new boolean[capacity];
  }
 
  /** 
  * This method ensures that the inputNum maps to
  * a return value that is in the current array.
  */
  private int getBoundedHash(int inputNum) {
    return Math.abs(inputNum) % this.capacity;
  }

  private int getHash(K key) {
    int index = getBoundedHash(key.hashCode());
    return index;
  }
  
  public K[] getKeyTable() {
    return this.keyTable;
  }
  
  public V[] getValueTable() {
    return this.valueTable;
  }
  
  public boolean[] getFlag() {
    return this.flag;
  }

  /** 
   * Returns the number of elements in the hash table.
   */
  public int size() {
    // TODO 2a: return the size
    return count;
  }

  /**
   * Returns the current maximal number of elements the hash table can save.
   * @return
   */
  public int getCapacity() {
    // TODO 2b: return the capacity
    return capacity;
  }

  /**
   * Returns the name of the collision handler for the current hash table. 
   * @return : name of the collision handler.
   */
  public String getCollisionHandlerName() {
    // TODO 2c: return the name of collision handler
    return collisionHandler;
  }

  /**
   * Enlarges the size of the array and rehash.
   */
  private void resizeArray() {
    // TODO 3: Double the size, then rehash into the new table
	K[] tempKey = keyTable;
	V[] tempValue = valueTable;
	boolean[] tempFlag = flag;
	keyTable = (K[]) new Object[capacity*2];
	valueTable = (V[]) new Object[capacity*2];
	flag = new boolean[capacity*2];
	capacity *= 2;
	
	for (int i = 0; i < tempKey.length; i++) {
		if(tempFlag[i]) {
			put(tempKey[i],tempValue[i]);
			count--;
		}
	}
  }

  /**
  * Calculates the ratio of the size of the data in the table to the capacity.
  */
  private double calcCurrentLoad() {
          return (double) size()/getCapacity();
    // TODO 4: Calculate current load factor and return it 
    // Load factor = size / capacity
  } 

  /**
   * This method calls upon a collision resolution scheme
   * to put this value into the table.
   */
  private int resolveCollision(int index, K key) {
    if (this.collisionHandler.equals("linear")) {
      index = doLinearProbe(index);
    } else if (this.collisionHandler.equals("quadratic")) {
      index = doQuadraticProbe(index);
    } else if (this.collisionHandler.equals("doublehash")) {
      index = doDoubleHash(index, key);
    } else { 
      return -1; 
    }
    return index;
  }

  /** 
   * Put the new value into the hash table. Before adding, 
   * resize the array if the current load factor is larger than loadFactorLimit. 
   * Please use the getHash method provided to get the hash for the array index.
   * Call the searchIndex method to see if a collision occurs. 
   * If the key exists in the hash table, replace the old value with the input value.
   * Call the resolveCollision method if a collision happened.
   */
  public void put(K key, V value) {
    // TODO 5: put the new value into hash table.
	  if (calcCurrentLoad() > loadFactorLimit) {
		  resizeArray();
	  }
	  int index = getHash(key);
	  int curIndex = searchIndex(index,key);
	  if (!flag[index]) {
		  keyTable[index] = key;
	  	  valueTable[index] = value;
	  	  flag[index] = true;
	  	  count++;
	  }
	  else if (curIndex == -1) {
		  index = resolveCollision(index, key);
		  keyTable[index] = key;
	  	  valueTable[index] = value;
	  	  flag[index] = true;
	  	  count++;
	  }
	  else {
		  valueTable[curIndex] = value;
	  	  flag[curIndex] = true;
	  	  count++;
	  }
  }

  /** T
   * This method calls upon a collision resolution scheme
   * to search for an index where the value can be inserted into the table.
   */
  private int searchIndex(int index, K value) {
    if (this.collisionHandler.equals("linear")) {
      index = doLinearSearch(index, value);
    } else if (this.collisionHandler.equals("quadratic")) {
      index = doQuadraticSearch(index, value);
    } else if (this.collisionHandler.equals("doublehash")) {
      index = doSecondHashSearch(index, value);
    }
    return index;
  }

  /**
   * Removes the target value from the hash table and return the value.
   * Throws ElementNotFoundException if the target does not exist in the table.
   * Calls searchIndex to get the index in case a collision occurred when
   * the value was put into the table.
   */
  public V remove(K target)throws ElementNotFoundException {
    //TODO 6: remove the target value from hash table and return value
	  int index = getHash(target); 
	  int curIndex = searchIndex(index, target);
	  if(keyTable[index].equals(target) && flag[index]) {
		  flag[index] = false;
		  count--;
		  return valueTable[index];
	  }
	  else if (curIndex != -1 && flag[curIndex] && keyTable[curIndex].equals(target)) {
		  flag[curIndex] = false;
		  count--;
		  return  valueTable[curIndex];
	  }
	  else {
		  throw new ElementNotFoundException("Element not found.");
	  }
  }

  /**
   * Returns the value if it exists in the table.
   * Returns null if the target does not exist in the table.
   * Calls searchIndex to get the index in case a collision occurred when
   * the value was put into the table.
   */
  public V get(K target) {
    //TODO 7: return target if it exist in the table
	  int index = searchIndex(getHash(target), target);
	  if (index != -1) {
		  return valueTable[index];
	  }
	return null;
  }

  /** TODO 8: Complete linearProbe, quadraticProbe, and doubleHash
  * Start at index and search linearly (with probe length = 1) until an open spot
  * is found. A spot will be found because the table 
  * is never more full than the Load Factor, assuming it's <1.0
  */
  private int doLinearProbe(int index) { 
	  while (flag[index]) { 
		  index = (index+1) % capacity;
	  }
	  return index;
  }
 
  /**
  * Start at index and search quadratically until an open spot is found
  * A spot will be found because the table 
  * is never more full than the Load Factor, assuming it's <1.0
  */
  private int doQuadraticProbe(int index) {
	  int result = -1;
	  int i = 1;
	  int start = index;
	  while (flag[index] && i < capacity) { 
		  index = start + (i*i) % capacity;
		  i++;
	  }
	  result = index;
	  return result;
  }


  /**
  * If the first hash resulted in a collision, use a second hash
  * as the probe length until a space is found. 
  * The second hash value is computed by length of value.hashCode()
  */
  private int doDoubleHash(int index, K key) {
	 int curIndex = key.hashCode();
	 int probe = Integer.toString(curIndex).length();
	  while (flag[index]) { 
		  index = (index+probe) % capacity;
	  }
	  return index;
  }  

  /**
  * TODO 9: Complete linearSearch, quadraticSearch, and doubleHashSearch.
  * Start at startIndex and search linearly until the target
  * is found. Return -1 if not found.
  */
  private int doLinearSearch(int startIndex, K target) {
	  int index = startIndex; 
	  int start = index;
	  while(keyTable[index] != null) {
		  if(keyTable[index].equals(target) && flag[index]) {
			  return index;
		  }	
		  index = (index+1) % capacity;
		  if (start == index) {
			  return -1;
		  }
	  }
	  return -1;
  }

  /**
  * Start at startIndex and search quadratically until the target
  * is found. Return -1 if not found.
  */
  private int doQuadraticSearch(int startIndex, K target) {
	  int index = startIndex; 
	  int start = index;
	  int iterations = 0;
	  int i = 1;
	  while(keyTable[index] != null) {
		  if(keyTable[index].equals(target) && flag[index]) {
			  return index;
		  }	
		  index = start + (i*i) % capacity;
		  i++;
		  iterations++;
		  if (iterations > capacity) {
			  return -1;
		  }
	  }
	  return -1;
  }

  private int doSecondHashSearch(int startIndex, K target) {
	  int index = startIndex; 
	  int start = index;
      int probe = Integer.toString(target.hashCode()).length();
	  while(keyTable[index] != null) {
		  if(keyTable[index].equals(target) && flag[index]) {
			  return index;
		  }	
		  index = (index+probe) % capacity;
		  if (index == start) {
		  	return -1;
		  }
	  }
	  return -1;
  }

  /**
   * Return all available keys in hash table as an array.
   */
  public K[] keys() {
    // TODO 10: return all available keys in hash table
	K[] tempKey = (K[]) new Object[count];
	int j = 0;
	for(int i = 0; i< capacity; i++) {
		if (flag[i]) {
			tempKey[j] = keyTable[i];
			j++;
		}
	}
    return tempKey;
  }
}
