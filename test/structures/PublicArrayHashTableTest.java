package structures;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PublicArrayHashTableTest {

  @Test (timeout = 1000)
  public void testDefaultConstructor() throws Exception  {
    ArrayHashTable<String, Integer> q = new ArrayHashTable<String, Integer>();
    assertEquals(0, q.size());
    assertEquals(10, q.getCapacity());
  }

  @Test (timeout = 1000)
  public void testConstructor() throws Exception  {
    ArrayHashTable<String, Integer> q = new ArrayHashTable<String, Integer>(100, "linear");
    assertEquals(0, q.size());
    assertEquals(100, q.getCapacity());
    assertTrue(q.getCollisionHandlerName().equals("linear"));
  }

  @Test (timeout = 1000)
  public void testPutAndGet() throws Exception  {
    ArrayHashTable<Integer, String> q = new ArrayHashTable<Integer, String>();
    assertEquals(0, q.size());
    q.put(1, "apple");
    q.put(10, "pencil");
    assertTrue(q.get(1).equals("apple"));
    q.put(3, "pineapple");
    assertEquals(10, q.getCapacity());
    q.put(2, "pen");
    assertEquals(4, q.size());
    assertTrue(q.get(3).equals("pineapple"));

  }

  @Test (timeout = 1000)
  public void testresizeArray() throws Exception  {
    ArrayHashTable<Integer, String> q = new ArrayHashTable<Integer, String>(3, "linear");
    assertEquals(0, q.size());
    assertEquals(3, q.getCapacity());
    q.put(1, "apple");
    q.put(2, "pen");
    q.put(3, "pineapple");
    assertEquals(3, q.size());
    assertEquals(3, q.getCapacity());
    q.put(10, "pencil");
    assertEquals(4, q.size());
    assertEquals(6, q.getCapacity());
  }

  @Test (timeout = 1000)
  public void testRemove() throws Exception  {
    ArrayHashTable<Integer, String> q = new ArrayHashTable<Integer, String>(100, "doublehash");
    q.put(112, "Apple");
    q.put(11, "Pen");
    assertEquals(q.size(), 2);
    q.put(1114, "Dog");
    assertTrue(q.get(1114).equals("Dog"));
    assertEquals(q.size(), 3);
    q.remove(11);
    assertEquals(q.size(), 2);
    assertTrue(q.get(11) == null);
  }

  @Test (timeout = 1000)
  public void testLinearProb() throws Exception  {
    ArrayHashTable<Integer, String> q = new ArrayHashTable<Integer, String>(200, "linear");

    q.put(111, "Apple");
    q.put(311, "Pen");
    Object[] keyTable = q.keyTable;
    Object[] valueTable = q.valueTable;
    Integer k = (Integer) keyTable[111];
    String v = (String) valueTable[112];
    assertTrue(k.equals(111));
    assertTrue(v.equals("Pen"));
    q.put(1114, "Dog");
    q.put(914, "Cat");
    q.put(713, "Bird");
    q.put(313, "Grape");
    keyTable = q.keyTable;
    valueTable = q.valueTable;
    k = (Integer) keyTable[115];
    v = (String) valueTable[116];
    assertTrue(k.equals(914));
    assertTrue(v.equals("Grape"));
  }

  @Test (timeout = 1000)
  public void testQuadraticProbe() throws Exception  {
    ArrayHashTable<Integer, String> q = new ArrayHashTable<Integer, String>(200, "quadratic");	
    q.put(111, "Apple");
    q.put(311, "Pen");
    q.put(711, "Bird");
    q.put(1114, "Dog");
    q.put(914, "Cat");
    q.put(313, "Grape");
    Object[] keyTable = q.keyTable;
    Object[] valueTable = q.valueTable;

    Integer k = (Integer) keyTable[111];
    String v = (String) valueTable[111];
    assertTrue(k.equals(111));
    assertTrue(v.equals("Apple"));
    k = (Integer) keyTable[112];
    v = (String) valueTable[112];
    assertTrue(k.equals(311));
    assertTrue(v.equals("Pen"));
    k = (Integer) keyTable[113];
    v = (String) valueTable[113];
    assertTrue(k.equals(313));
    assertTrue(v.equals("Grape"));
    k = (Integer) keyTable[114];
    v = (String) valueTable[114];
    assertTrue(k.equals(1114));
    assertTrue(v.equals("Dog"));
    k = (Integer) keyTable[115];
    v = (String) valueTable[115];
    assertTrue(k.equals(711));
    assertTrue(v.equals("Bird"));
    k = (Integer) keyTable[118];
    v = (String) valueTable[118];
    assertTrue(k.equals(914));
    assertTrue(v.equals("Cat"));
  }

  @Test (timeout = 1000)
  public void testDoubleHash() throws Exception  {
    ArrayHashTable<Integer, String> q = new ArrayHashTable<Integer, String>(100, "doublehash");	
    q.put(111, "Apple");
    q.put(311, "Pen");
    q.put(711, "Bird");
    q.put(11111, "Dog");
    q.put(14, "Cat");
    q.put(313, "Grape");
    Object[] keyTable = q.keyTable;
    Object[] valueTable = q.valueTable;

    Integer k = (Integer) keyTable[11];
    String v = (String) valueTable[11];
    assertTrue(k.equals(111));
    assertTrue(v.equals("Apple"));
    k = (Integer) keyTable[14];
    v = (String) valueTable[14];
    assertTrue(k.equals(311));
    assertTrue(v.equals("Pen"));
    k = (Integer) keyTable[13];
    v = (String) valueTable[13];
    assertTrue(k.equals(313));
    assertTrue(v.equals("Grape"));
    k = (Integer) keyTable[16];
    v = (String) valueTable[16];
    assertTrue(k.equals(11111));
    assertTrue(v.equals("Dog"));
    k = (Integer) keyTable[17];
    v = (String) valueTable[17];
    assertTrue(k.equals(711));
    assertTrue(v.equals("Bird"));
    k = (Integer) keyTable[18];
    v = (String) valueTable[18];
    assertTrue(k.equals(14));
    assertTrue(v.equals("Cat"));
  }
}
