package wordfrequencycounter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import wordfrequencycounter.WordFrequencyCounter;


public class PublicWordFrequencyCounterTest {
  @Rule
  public Timeout timeout = new Timeout(10L, TimeUnit.SECONDS);

  File file;
  WordFrequencyCounter wordCounter;


  /**
   * Before each test, this method sets up the files.
   * this method throws IOException.
   */
  @Before
  public void before() throws IOException {
    file = new File("tempfile.tmp");
    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
    bw.write("This test is not the 1st test \n");
    bw.close();
  }

  @After
  public void after() {
    file.delete();
  }

  @Test
  public void testSingleFile() throws Exception {
    wordCounter = new WordFrequencyCounter();
    wordCounter.loadFile("tempfile.tmp");
    assertEquals(1, wordCounter.countWord("the"));
    assertEquals(2, wordCounter.countWord("test"));
    assertEquals(0, wordCounter.countWord("first"));
  }

}
