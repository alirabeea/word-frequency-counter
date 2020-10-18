package wordfrequencycounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import structures.ArrayHashTable;



public class WordFrequencyCounter {
	private static ArrayHashTable<String, Integer> wordTable;

  /**
   * Constructor.
   */
  public WordFrequencyCounter() {
    // TODO
	  wordTable = new ArrayHashTable<String, Integer>();
  }

  /**
   * Load file and pass each word in the file to the addword method.
   * @param filename : name of file.
   * @throws IOException : IOException.
   */
  public void loadFile(String filename) throws IOException {
    FileReader fr = new FileReader(filename);
    BufferedReader br = new BufferedReader(fr);
    String line;
    while ((line = br.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line);
      while (st.hasMoreTokens()) {
        String temp = st.nextToken();
        addWord(temp);
      }
    }
    br.close();
  }

  /**
   * Return the number of words in the file.
   * @param word : word.
   * @return : the number of word in the file.
   */
  public int countWord(String word) {
    // TODO
	  Integer result = wordTable.get(word);
	  if(result == null) {
		  result = 0;
	  }
    return result;
  }

  /**
   * Update word frequency table.
   */
  static void addWord(String word) {
    // TODO
	  Integer count = wordTable.get(word);
	  if (count == null) {
		  wordTable.put(word, 1);
	  }
	  else {
		  wordTable.put(word, count + 1); 
	  }
  }
}
