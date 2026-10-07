import java.util.LinkedList;

/**
 * A hash table for strings built from scratch using separate chaining.
 *
 * <p>Each slot in the fixed-size bucket array holds a {@link LinkedList} of
 * strings. Collisions are resolved by appending to the chain at the hashed
 * index. Duplicate keys are skipped on insert, so the table behaves like a
 * set of unique keys.</p>
 */
public class HashTable {

    /** The fixed-size bucket array. */
    private final LinkedList<String>[] buckets;

    /** The number of unique keys stored in the table. */
    private int count;

    /**
     * Creates a hash table with the default capacity of 10657 buckets.
     *
     * <p>10657 is a prime number. This helps prevent hash collisions: with
     * the hash function h = h*31 + c, collisions depend on the modulus, and
     * a prime number means hash values share no factors with it so keys
     * spread more evenly.</p>
     *
     * Time Complexity: O(1) (constant number of buckets).
     * Space Complexity: O(1) (constant number of buckets).
     */
    public HashTable() {
        this(10657);
    }

    /**
     * Creates a hash table with the given bucket-array capacity.
     *
     * Time Complexity: O(n) where n is the given capacity.
     * Space Complexity: O(n) where n is the given capacity.
     *
     * @param capacity the number of buckets in the table
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        buckets = (LinkedList<String>[]) new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        count = 0;
    }

    /**
     * Hashes the given string to an index in the bucket array.
     *
     * <p>Uses a polynomial rolling hash where each character multiplies the
     * running total by 31 before adding the character value. The result is
     * reduced modulo the table capacity, so the returned index is always a
     * valid bucket position.</p>
     *
     * Time Complexity: O(m) where m is the length of the string.
     * Space Complexity: O(1).
     *
     * @param x the string to hash
     * @return a valid index in the bucket array
     */
    public int hash(String x) {
        int h = 0;
        for (int i = 0; i < x.length(); i++) {
            h = h * 31 + x.charAt(i);
        }
        // The % operator follows the sign of the dividend, while Math.floorMod() follows the sign of the divisor.
        // We know that bucket.length is always positive but h might be negative due to buffer overflow.
        return Math.floorMod(h, buckets.length);
    }

    /**
     * Inserts the given string at the index returned by {@link #hash}.
     *
     * <p>If the string is already present in its chain, the insertion is
     * skipped, so each key is stored only once.</p>
     *
     * Time Complexity: Average O(m) where m is the length of the string;
     * worst case O(n + m) where n is the number of keys in the table when
     * all keys collide into one chain.
     * Space Complexity: O(1) beyond the table's own storage.
     *
     * @param x the string to insert
     */
    public void insert(String x) {
        LinkedList<String> chain = buckets[hash(x)];
        if (!chain.contains(x)) {
            chain.add(x);
            count++;
        }
    }

    /**
     * Returns the number of unique keys in the table.
     *
     * Time Complexity: O(1).
     * Space Complexity: O(1).
     *
     * @return the number of elements stored
     */
    public int size() {
        return count;
    }
}
