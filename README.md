# Assignment 3: Hash Table and Anagram Counter

Java implementation of the two tasks for SWE 240 Assignment 3: a hash
table built from scratch with separate chaining, and an anagram-root
counter for Pride and Prejudice built on that hash table. Each task
includes JUnit 5 sample test cases. No built-in `Hash` or `Dictionary`
APIs are used.

[TOC]

## Task 1: Implement a hash table from scratch

Implement a hash data structure without using built-in `Hash` or
`Dictionary` APIs. Arrays, lists, or a custom linked list are allowed
as backing storage. The `Hash` class must provide the following
functions and fields:

```java
class Hash {
    HashTable;  // a fixed-size array or list, one-dimensional or
                // two-dimensional.
    hash(x);    // returns an integer index in the hashtable for
                // string x. Must have a collision-resolution
                // mechanism.
    insert(x);  // inserts string x in the HashTable at the index
                // returned by hash(x).
    size();     // returns the size of the elements, i.e., the number
                // of keys.
}
```

Implementation: [src/main/java/HashTable.java](src/main/java/HashTable.java)
uses separate chaining: a fixed-size bucket array of `LinkedList`s and
a polynomial rolling hash (`h = h * 31 + c`) reduced with
`Math.floorMod`. Duplicate keys are skipped, so the table stores each
key once. The default capacity is the prime number 10657.

### Task 1 sample test cases

`src/test/java/HashTableTests.java` validates:

*   `hash()` always returns an index within the bucket array, even for
    the empty string.
*   `hash()` returns the same index for the same key.
*   `size()` starts at 0 and increments per inserted key.
*   Duplicate inserts of the same key are skipped.
*   Distinct keys are all counted, including keys that collide into the
    same bucket.
*   Keys are case-sensitive: `"The"` and `"the"` are distinct.
*   The empty string is counted once.

## Task 2: Count unique anagram roots in Pride and Prejudice

Read and parse each word from the file `pride-and-prejudice.txt`. The
file is very large, so read the text line by line instead of all at
once. To split a line into words, treat anything other than
alpha-numeric characters (i.e., `[a-zA-Z0-9]`) as delimiters, for
example `\n`, `\t`, `,`, `.`, and so on.

An anagram is a word or phrase formed by rearranging the letters of a
different word or phrase, typically using all the original letters
exactly once. For example, `mango` and `gonma` are anagrams containing
the same characters. An anagram-root word is a word derived by sorting
the word by characters; for example, the anagram root of `mango` is
`agmno`. Write a function that finds how many unique anagram-root words
there are in the novel.

```text
Input: "mango" and "gonma"
Anagram root: "agmno" (both words)
```

Follow these steps to find the number of unique anagram roots:

1.  While parsing each word from the file, sort the word by character.
    Any built-in sorting API may be used.
1.  Insert the sorted word into the hashtable from Task 1. If the word
    is already present in the hashtable, skip it.
1.  Once all words have been sorted and inserted (or skipped), call the
    `size()` function of the `Hash` class.

Implementation:
[src/main/java/AnagramCounter.java](src/main/java/AnagramCounter.java)
reads the file line by line with a `BufferedReader`, splits each line
on `[^a-zA-Z0-9]+`, sorts every word's characters with
`Arrays.sort()`, and inserts the sorted root into the `HashTable`. The
novel contains 6,954 unique anagram roots.

### Task 2 sample test cases

`src/test/java/AnagramCounterTests.java` validates:

*   An empty file contains 0 unique anagram roots.
*   A single-word file contains 1 unique anagram root.
*   Punctuation, whitespace, and newlines are treated as delimiters:
    `"mango!"`, `"gonma"`, and `"mango"` share one root.
*   Roots are case-sensitive: `"The"` and `"the"` produce distinct
    roots.
*   The full novel `pride-and-prejudice.txt` contains 6,954 unique
    anagram roots.

## Build and test

Requires JDK 25 or newer. Build and run the tests with the Gradle
wrapper:

```shell
./gradlew build
./gradlew test
```

## Project layout

```text
src/main/java/HashTable.java
src/main/java/AnagramCounter.java
src/test/java/HashTableTests.java
src/test/java/AnagramCounterTests.java
src/test/resources/pride-and-prejudice.txt
```
