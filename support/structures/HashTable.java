package structures;


public interface HashTable <K, V> {
 
  /* Adds the argument to the hash table. The argument must implement the hashCode method and the equals method.
   * Duplicate values are not stored, so if the value already exists in the hash table it will not be added.
   * A NullPointerException is thrown if an argument is null. 
  */ 
  public void put(K key, V value);
  

  /* Given a key, return the corresponding value in the hash table. */
  public V get(K key);
     
  /* Given a key, remove the key-value pair from the hash table and return the value */
  public V remove(K key) throws ElementNotFoundException; 
  
  /* Return number of elements in the hash table. */
  public int size();
  
  /* Return the keys in the hash table as an array. */
  public K[] keys();
   
}
