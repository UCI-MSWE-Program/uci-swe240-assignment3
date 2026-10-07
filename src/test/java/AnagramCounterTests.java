import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link AnagramCounter}.
 *
 * <p>Verifies anagram-root counting, delimiter handling, and the full
 * pride-and-prejudice.txt novel.</p>
 */
public class AnagramCounterTests {

    /**
     * Resolves a classpath resource to an absolute file path.
     *
     * @param name the resource name, e.g. "pride-and-prejudice.txt"
     * @return the absolute path of the resource
     */
    private static String resourcePath(String name) {
        try {
            return Path.of(Objects.requireNonNull(
                    AnagramCounterTests.class.getClassLoader().getResource(name),
                    "resource not found: " + name).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------------------------------------
    // Edge cases
    // ------------------------------------------------------------------

    @Test
    public void testCountUniqueAnagramRoots_EmptyFile_IsZero() throws IOException {
        Path empty = Files.createTempFile("empty", ".txt");
        try {
            assertEquals(0, AnagramCounter.countUniqueAnagramRoots(empty.toString()));
        } finally {
            Files.deleteIfExists(empty);
        }
    }

    @Test
    public void testCountUniqueAnagramRoots_SingleWord_IsOne() throws IOException {
        Path file = Files.createTempFile("one", ".txt");
        try {
            Files.writeString(file, "hello");
            assertEquals(1, AnagramCounter.countUniqueAnagramRoots(file.toString()));
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testCountUniqueAnagramRoots_PunctuationAndNewlines_AreDelimiters() throws IOException {
        Path file = Files.createTempFile("punct", ".txt");
        try {
            Files.writeString(file, "mango!\n\tgonma, \"mango\". ...");
            assertEquals(1, AnagramCounter.countUniqueAnagramRoots(file.toString()));
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testCountUniqueAnagramRoots_CaseSensitive_CountsBothRoots() throws IOException {
        Path file = Files.createTempFile("case", ".txt");
        try {
            Files.writeString(file, "The the");
            // "The" -> "Teh" and "the" -> "eht" are distinct roots
            assertEquals(2, AnagramCounter.countUniqueAnagramRoots(file.toString()));
        } finally {
            Files.deleteIfExists(file);
        }
    }

    // ------------------------------------------------------------------
    // Full novel
    // ------------------------------------------------------------------

    @Test
    public void testCountUniqueAnagramRoots_PrideAndPrejudice() {
        int roots = AnagramCounter.countUniqueAnagramRoots(
                resourcePath("pride-and-prejudice.txt"));
        assertEquals(6954, roots);
        assertTrue(roots > 0, "the novel should contain at least one anagram root");
    }
}
