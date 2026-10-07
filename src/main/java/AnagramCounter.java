import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Counts unique anagram roots found in a text file.
 *
 * <p>An anagram root of a word is the word sorted by characters; for
 * example, the anagram root of "mango" is "agmno". This class parses a
 * file line by line, sorts every word into its anagram root, and stores
 * the roots in a {@link HashTable}, skipping duplicates.</p>
 */
public class AnagramCounter {

    /**
     * Creates a new {@code AnagramCounter} instance; all functionality is
     * provided statically through
     * {@link #countUniqueAnagramRoots(String)}.
     *
     * Time Complexity: O(1).
     * Space Complexity: O(1).
     */
    public AnagramCounter() {}

    /**
     * Reads the file at the given path line by line and counts how many
     * unique anagram roots appear in it.
     *
     * <p>Anything other than alpha-numeric characters
     * ({@code [a-zA-Z0-9]}) is treated as a word delimiter. Each word is
     * sorted by character using the built-in sorting API, and the sorted
     * word is inserted into the hash table; duplicate roots are skipped.</p>
     *
     * Time Complexity: Average O(w*klogk) where w is the number of words
     * and k is the maximum word length; worst case O(w*(klogk + n)) where
     * n is the number of unique anagram roots when all roots collide into
     * one chain.
     * Space Complexity: O(n + k) where n is the number of unique anagram
     * roots stored in the table and k is the maximum word length.
     *
     * @param filePath the path to the text file to parse
     * @return the number of unique anagram roots, i.e., the number of
     *         elements in the hash table
     */
    public static int countUniqueAnagramRoots(String filePath) {
        HashTable table = new HashTable();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[^a-zA-Z0-9]+");
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    char[] chars = word.toCharArray();
                    Arrays.sort(chars);
                    table.insert(new String(chars));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
        return table.size();
    }
}
