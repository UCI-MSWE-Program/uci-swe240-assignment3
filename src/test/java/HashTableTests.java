import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link HashTable}.
 *
 * <p>Verifies hashing bounds, insertion, duplicate skipping, collision
 * handling through separate chaining, and size tracking.</p>
 */
public class HashTableTests {

    // ------------------------------------------------------------------
    // hash()
    // ------------------------------------------------------------------

    @Test
    public void testHash_IndexAlwaysInRange() {
        HashTable table = new HashTable(37);
        String[] keys = {"", "a", "mango", "Pride and Prejudice", "word123", "zzzzzzzz"};
        for (String key : keys) {
            int index = table.hash(key);
            assertTrue(index >= 0 && index < 37,
                    "hash(\"" + key + "\") = " + index + " is out of range");
        }
    }

    @Test
    public void testHash_SameKey_ReturnsSameIndex() {
        HashTable table = new HashTable(10);
        assertEquals(table.hash("mango"), table.hash("mango"));
    }

    // ------------------------------------------------------------------
    // insert() and size()
    // ------------------------------------------------------------------

    @Test
    public void testSize_NewTable_IsZero() {
        assertEquals(0, new HashTable().size());
    }

    @Test
    public void testInsert_NewKey_IncrementsSize() {
        HashTable table = new HashTable();
        table.insert("mango");
        assertEquals(1, table.size());
        table.insert("apple");
        assertEquals(2, table.size());
    }

    @Test
    public void testInsert_DuplicateKey_Skipped() {
        HashTable table = new HashTable();
        table.insert("mango");
        table.insert("mango");
        table.insert("mango");
        assertEquals(1, table.size());
    }

    @Test
    public void testInsert_DistinctKeys_AllCounted() {
        HashTable table = new HashTable(5);
        table.insert("a");
        table.insert("b");
        table.insert("c");
        table.insert("d");
        table.insert("e");
        assertEquals(5, table.size());
    }

    @Test
    public void testInsert_ManyCollidingKeys_DistinctCountPreserved() {
        HashTable table = new HashTable(11);
        String[] keys = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh"};
        for (String key : keys) {
            table.insert(key);
        }
        assertEquals(keys.length, table.size());
        for (String key : keys) {
            table.insert(key);
        }
        assertEquals(keys.length, table.size());
    }

    @Test
    public void testInsert_CaseSensitive_DistinctKeys() {
        HashTable table = new HashTable();
        table.insert("The");
        table.insert("the");
        assertEquals(2, table.size());
    }

    @Test
    public void testInsert_EmptyString_CountedOnce() {
        HashTable table = new HashTable();
        table.insert("");
        table.insert("");
        assertEquals(1, table.size());
    }
}
