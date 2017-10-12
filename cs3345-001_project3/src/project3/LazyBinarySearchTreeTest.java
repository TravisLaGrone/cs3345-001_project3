package project3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@Tag(LazyBinarySearchTreeTest.INSERT_TAG)
@TestInstance(Lifecycle.PER_CLASS)
class LazyBinarySearchTreeTest
{
	static final String INSERT_TAG = "insert";
	static final String DELETE_TAG = "delete";
	static final String CONTAINS_TAG = "contains";
	static final String MIN_TAG = "findMin";
	static final String MAX_TAG = "findMax";
	static final String HEIGHT_TAG = "heightWithDeleted";
	static final String SIZE_TAG = "sizeWithDeleted";
	static final String PRINT_TAG = "printAllWithDeleted";
	static final List<String> TAGS;
	
	static {
		TAGS = Collections.unmodifiableList(Arrays.asList(
				INSERT_TAG,
				DELETE_TAG,
				CONTAINS_TAG,
				MIN_TAG,
				MAX_TAG,
				HEIGHT_TAG,
				SIZE_TAG,
				PRINT_TAG
		));
	}
	
	LazyBinarySearchTree tree;
	
	/**
	 * To get access to the data written by {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)}
	 * to the proxy object {@link LazyBinarySearchTreeTest#ps}.
	 * <p>
	 * A {@link ByteArrayOutputStream} need only be instantiated once across all tests since it cannot be
	 * corrupted by IOExceptions, closing it has no effect, and the underlying byte array buffer may be
	 * cleared and reused simply by invoking {@link ByteArrayOutputStream#reset()}.
	 */
	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	/**
	 * To serve as the argument for the <code>out</code> parameter of
	 * {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)}.
	 */
	PrintStream ps;
	
	@BeforeEach
	void setUp()
	{
		tree = new LazyBinarySearchTree();
		baos.reset();
		ps = new PrintStream(baos, true);
	}

	@Tag(INSERT_TAG)
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class InvalidInputTest
	{
		static final int MIN_OF_LOWER_INVALID = Integer.MIN_VALUE;
		static final int MID_OF_LOWER_INVALID = -50;
		static final int MAX_OF_LOWER_INVALID = 0;
		
		static final int MIN_OF_VALID = 1;
		static final int MID_OF_VALID = 50;
		static final int MAX_OF_VALID = 99;
		
		static final int MIN_OF_UPPER_INVALID = 100;
		static final int MID_OF_UPPER_INVALID = 150;
		static final int MAX_OF_UPPER_INVALID = Integer.MAX_VALUE;
		
		final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;  // can't be declared static because runtime evaluation in non-static nested class
		
		@Tag(INSERT_TAG)
		@Test
		void testInsert()
		{
			assertAll("invalid input",
					() -> assertAll(
							() -> assertThrows(IAE, () -> tree.insert(MID_OF_LOWER_INVALID), "input: " + Integer.toString(MID_OF_LOWER_INVALID)),
							() -> assertThrows(IAE, () -> tree.insert(MID_OF_UPPER_INVALID), "input: " + Integer.toString(MID_OF_UPPER_INVALID))
					),
//					() -> assertAll(
//							() -> assertThrows(IAE, () -> tree.insert(MIN_OF_LOWER_INVALID)),
//							() -> assertThrows(IAE, () -> tree.insert(MAX_OF_UPPER_INVALID))
//					),
					() -> assertAll(
							() -> assertThrows(IAE, () -> tree.insert(MAX_OF_LOWER_INVALID), "input: " + Integer.toString(MAX_OF_LOWER_INVALID)),
							() -> assertThrows(IAE, () -> tree.insert(MIN_OF_UPPER_INVALID), "input: " + Integer.toString(MIN_OF_UPPER_INVALID))
					)
			);
		}
		
		@Tag(DELETE_TAG)
		@Test
		void testDelete()
		{
			assertAll("invalid input",
					() -> assertAll(
							() -> assertThrows(IAE, () -> tree.delete(MID_OF_LOWER_INVALID), "input: " + Integer.toString(MID_OF_LOWER_INVALID)),
							() -> assertThrows(IAE, () -> tree.delete(MID_OF_UPPER_INVALID), "input: " + Integer.toString(MID_OF_UPPER_INVALID))
					),
//					() -> assertAll(
//							() -> assertThrows(IAE, () -> tree.delete(MIN_OF_LOWER_INVALID)),
//							() -> assertThrows(IAE, () -> tree.delete(MAX_OF_UPPER_INVALID))
//					),
					() -> assertAll(
							() -> assertThrows(IAE, () -> tree.delete(MAX_OF_LOWER_INVALID), "input: " + Integer.toString(MAX_OF_LOWER_INVALID)),
							() -> assertThrows(IAE, () -> tree.delete(MIN_OF_UPPER_INVALID), "input: " + Integer.toString(MIN_OF_UPPER_INVALID))
					)
			);
		}
		
		@Tag(CONTAINS_TAG)
		@Test
		void testContains()
		{
			assertAll("invalid input",
					() -> assertAll(
							() -> assertThrows(IAE, () -> tree.contains(MID_OF_LOWER_INVALID), "input: " + Integer.toString(MID_OF_LOWER_INVALID)),
							() -> assertThrows(IAE, () -> tree.contains(MID_OF_UPPER_INVALID), "input: " + Integer.toString(MID_OF_UPPER_INVALID))
					),
//					() -> assertAll(
//							() -> assertThrows(IAE, () -> tree.contains(MIN_OF_LOWER_INVALID)),
//							() -> assertThrows(IAE, () -> tree.contains(MAX_OF_UPPER_INVALID))
//					),
					() -> assertAll(
							() -> assertThrows(IAE, () -> tree.contains(MAX_OF_LOWER_INVALID), "input: " + Integer.toString(MAX_OF_LOWER_INVALID)),
							() -> assertThrows(IAE, () -> tree.contains(MIN_OF_UPPER_INVALID), "input: " + Integer.toString(MIN_OF_UPPER_INVALID))
					)
			);
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class EmptyTreeTest
	{
		static final int KEY = 50;
		
		@Tag(INSERT_TAG)
		@Test
		void testInsertRoot()
		{
			boolean insertReturnValue = tree.insert(KEY);
			assertAll("insert result",
					() -> assertTrue(insertReturnValue),
					() -> assertEquals(Integer.toString(KEY), getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(DELETE_TAG)
		@Test
		void testDelete()
		{
			boolean deleteReturnValue = tree.delete(KEY);
			assertAll("delete result",
					() -> assertFalse(deleteReturnValue),
					() -> assertEquals("", getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(CONTAINS_TAG)
		@Test
		void testContains()
		{
			boolean containsReturnValue = tree.contains(KEY);
			assertFalse(containsReturnValue);
		}
		
		@Tag(MIN_TAG)
		@Test
		void testFindMin()
		{
			int findMinReturnValue = tree.findMin();
			assertEquals(-1, findMinReturnValue, "-1 is defined to be the default value");
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMax()
		{
			int findMaxReturnValue = tree.findMax();
			assertEquals(-1, findMaxReturnValue, "-1 is defined to be the default value");
		}
		
		@Tag(HEIGHT_TAG)
		@Test
		@Disabled("forgot to specify the default value for an empty tree in the project instructions")
		void testHeight()
		{
			int heightReturnValue = tree.heightWithDeleted();
			assertEquals(-1, heightReturnValue, "-1 is defined to be the default value");
		}
		
		@Tag(SIZE_TAG)
		@Test
		void testSize()
		{
			int sizeReturnValue = tree.sizeWithDeleted();
			assertEquals(0, sizeReturnValue);
		}
		
		@Tag(PRINT_TAG)
		@Test
		void testPrintAll()
		{
			String printAllNormalizedReturnValue = getNormalizedPrintAllOutput(tree, ps);  // calls tree.printAllWithDeleted(ps)
			assertEquals("", printAllNormalizedReturnValue);
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class SingletonTreeTest
	{
		static final int SINGLETON_KEY = 50;
		static final int LEFT_KEY = 40;
		static final int RIGHT_KEY = 60;
		static final int ABSENT_KEY = 13;
		
		@BeforeEach
		void setUp()
		{
			tree.insert(SINGLETON_KEY);
		}
		
		@Tag(INSERT_TAG)
		@Test
		@DisplayName("insert a non-duplicate value \"to the left\" of an extant node")
		void testInsertAbsentLeft()
		{
			String expectedPrintAllOutput =
					String.join(" ", Integer.toString(LEFT_KEY), Integer.toString(SINGLETON_KEY));
			
			boolean insertReturnValue = tree.insert(LEFT_KEY);
			assertAll("insert result",
					() -> assertTrue(insertReturnValue),
					() -> assertEquals(expectedPrintAllOutput, getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(INSERT_TAG)
		@Test
		@DisplayName("insert a non-duplicate value \"to the right\" of an extant node")
		void testInsertAbsentRight()
		{
			String expectedPrintAllOutput =
					String.join(" ", Integer.toString(SINGLETON_KEY), Integer.toString(RIGHT_KEY));
			
			boolean insertReturnValue = tree.insert(RIGHT_KEY);
			assertAll("insert result",
					() -> assertTrue(insertReturnValue),
					() -> assertEquals(expectedPrintAllOutput, getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(INSERT_TAG)
		@Test
		@DisplayName("insert a duplicate value of an element that is present and non-deleted")
		void testInsertPresent()
		{
			boolean insertReturnValue = tree.insert(SINGLETON_KEY);
			assertAll("insert result",
					() -> assertFalse(insertReturnValue),
					() -> assertEquals(Integer.toString(SINGLETON_KEY), getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(INSERT_TAG)
		@Test
		@DisplayName("insert a duplicate value of an element that is present but already deleted")
		void testInsertDeleted()
		{
			tree.delete(SINGLETON_KEY);
			boolean insertReturnValue = tree.insert(SINGLETON_KEY);
			assertAll("insert result",
					() -> assertTrue(insertReturnValue),
					() -> assertEquals(Integer.toString(SINGLETON_KEY), getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(DELETE_TAG)
		@Test
		@DisplayName("delete an element not in the tree")
		void testDeleteAbsent()
		{
			boolean deleteReturnValue = tree.delete(ABSENT_KEY);
			assertAll("delete result",
					() -> assertFalse(deleteReturnValue),
					() -> assertEquals(Integer.toString(SINGLETON_KEY), getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(DELETE_TAG)
		@Test
		@DisplayName("delete an element that is present and non-deleted")
		void testDeletePresent()
		{
			boolean deleteReturnValue = tree.delete(SINGLETON_KEY);
			assertAll("delete result",
					() -> assertTrue(deleteReturnValue),
					() -> assertEquals("*" + Integer.toString(SINGLETON_KEY), getNormalizedPrintAllOutput(tree,ps))
			);
		}
		
		@Tag(DELETE_TAG)
		@Test
		@DisplayName("delete an element that is present but already deleted")
		void testDeleteDeleted()
		{
			tree.delete(SINGLETON_KEY);
			boolean deleteReturnValue = tree.delete(SINGLETON_KEY);
			assertAll("delete result",
					() -> assertFalse(deleteReturnValue),
					() -> assertEquals("*" + Integer.toString(SINGLETON_KEY), getNormalizedPrintAllOutput(tree,ps))
			);
		}

		@Tag(CONTAINS_TAG)
		@Test
		@DisplayName("check if the tree contains an element that it does not")
		void testContainsAbsent()
		{
			assertFalse(tree.contains(ABSENT_KEY));
		}
		
		@Tag(CONTAINS_TAG)
		@Test
		@DisplayName("check if the tree contains a non-deleted element that it does")
		void testContainsPresent()
		{
			assertTrue(tree.contains(SINGLETON_KEY));
		}
		
		@Tag(CONTAINS_TAG)
		@Test
		@DisplayName("check if the tree contains an element that it does, but which has been deleted")
		void testContainsDeleted()
		{
			tree.delete(SINGLETON_KEY);
			assertFalse(tree.contains(SINGLETON_KEY));
		}
		
		@Tag(MIN_TAG)
		@Test
		void testFindMin()
		{
			assertEquals(SINGLETON_KEY, tree.findMin());
		}
		
		@Test
		void testFindMinWhereAllDeleted()
		{
			tree.delete(SINGLETON_KEY);
			assertEquals(-1, tree.findMin(), "-1 is defined to be the default return value");
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMax()
		{
			assertEquals(SINGLETON_KEY, tree.findMax());
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMaxWhereAllDeleted()
		{
			tree.delete(SINGLETON_KEY);
			assertEquals(-1, tree.findMax(), "-1 is defined to be the default return value");
		}
		
		@Tag(HEIGHT_TAG)
		@Test
		void testHeightWhereNoneDeleted()
		{
			int heightReturnValue = tree.heightWithDeleted();
			assertEquals(0, heightReturnValue);
		}
		
		@Tag(HEIGHT_TAG)
		@Test
		void testHeightWhereAllDeleted()
		{
			tree.delete(SINGLETON_KEY);
			int heightReturnValue = tree.sizeWithDeleted();
			assertEquals(0, heightReturnValue);
		}
		
		@Tag(SIZE_TAG)
		@Test
		void testSizeWhereNoneDeleted()
		{
			int sizeReturnValue = tree.sizeWithDeleted();
			assertEquals(1, sizeReturnValue);
		}
		
		@Tag(SIZE_TAG)
		@Test
		void testSizeWhereAllDeleted()
		{
			tree.delete(SINGLETON_KEY);
			int sizeReturnValue = tree.sizeWithDeleted();
			assertEquals(1, sizeReturnValue);
		}
		
		@Tag(PRINT_TAG)
		@Test
		void testPrintAllWhereNoneDeleted()
		{
			String printAllNormalizedReturnValue = getNormalizedPrintAllOutput(tree, ps);  // calls tree.printAllWithDeleted(ps)
			assertEquals(Integer.toString(SINGLETON_KEY), printAllNormalizedReturnValue);
		}
		
		@Tag(PRINT_TAG)
		@Test
		void testPrintAllWhereAllDeleted()
		{
			tree.delete(SINGLETON_KEY);
			String printAllNormalizedReturnValue = getNormalizedPrintAllOutput(tree, ps);  // calls tree.printAllWithDeleted(ps)
			assertEquals("*" + Integer.toString(SINGLETON_KEY), printAllNormalizedReturnValue);
		}
		
	}
	
	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class HeightTwoTreeTest
	{
		/**
		 * An ordered set of three keys that will result in a "zig-zag" tree if inserted in order.  A
		 * "zig-zag" tree is a tree whose structure is <code>root => leftChild => rightChild</code>.
		 */
		final List<Integer> ZIG_ZAG_KEYS = Collections.unmodifiableList(
				Arrays.asList(50, 30, 40));
		
		/**
		 * An ordered set of three keys that will result in a "zag-zig" tree if inserted in order.  A
		 * "zag-zig" tree is a tree whose structure is <code>root => rightChild => leftChild</code>.
		 */
		final List<Integer> ZAG_ZIG_KEYS = Collections.unmodifiableList(
				Arrays.asList(50, 70, 60));
		
		/**
		 * An ordered set of keys that will result in a perfect tree of height two if inserted in order.
		 * Is a super set of both ZIG_ZAG_KEYS and ZAG_ZIG_KEYS.
		 */
		final List<Integer> PERFECT_KEYS = Collections.unmodifiableList(
				Arrays.asList(50, 30, 40, 20, 70, 60, 80));
		
		@Tag(INSERT_TAG)
		@Test
		void testInsertKeysOfZigZagTree()
		{
			ZIG_ZAG_KEYS.forEach(k -> tree.insert(k));
			assertEquals( simulatePrintAll(ZIG_ZAG_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(INSERT_TAG)
		@Test
		void testInsertKeysOfZagZigTree()
		{
			ZAG_ZIG_KEYS.forEach(k -> tree.insert(k));
			assertEquals( simulatePrintAll(ZAG_ZIG_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(INSERT_TAG)
		@Test
		void testInsertKeysOfPerfectTree()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			assertEquals( simulatePrintAll(PERFECT_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(DELETE_TAG)
		@Test
		void testDeleteZigZagFromPerfectTree()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			ZIG_ZAG_KEYS.forEach(k -> tree.delete(k));
			assertEquals( simulatePrintAllWithDeleted(PERFECT_KEYS, ZIG_ZAG_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(DELETE_TAG)
		@Test
		void testDeleteZagZigFromPerfectTree()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			ZAG_ZIG_KEYS.forEach(k -> tree.delete(k));
			assertEquals( simulatePrintAllWithDeleted(PERFECT_KEYS, ZAG_ZIG_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(DELETE_TAG)
		@Test
		void testDeletePerfectTreeStartingAtRoot()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			PERFECT_KEYS.forEach(k -> tree.delete(k));
			assertEquals( simulatePrintAllWithDeleted(PERFECT_KEYS, PERFECT_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(DELETE_TAG)
		@Test
		void testDeletePerfectTreeStartingAtLeaves()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			reversed(PERFECT_KEYS).forEach(k -> tree.delete(k));
			assertEquals( simulatePrintAllWithDeleted(PERFECT_KEYS, PERFECT_KEYS), getNormalizedPrintAllOutput(tree,ps) );
		}
		
		@Tag(MIN_TAG)
		@Test
		void testFindMinOfZigZagTree()
		{
			ZIG_ZAG_KEYS.forEach(k -> tree.insert(k));
			assertEquals( (int) min(ZIG_ZAG_KEYS), tree.findMin() );
		}
		
		@Tag(MIN_TAG)
		@Test
		void testFindMinOfPerfectTree()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			assertEquals( (int) min(PERFECT_KEYS), tree.findMin() );
		}
		
		@Tag(MIN_TAG)
		@Test
		void testFindMinOfPerfectTreeWithDeletedZigZag()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			ZIG_ZAG_KEYS.forEach(k -> tree.delete(k));
			assertEquals( (int) min(PERFECT_KEYS), tree.findMin() );
		}
		
		@Tag(MIN_TAG)
		@Test
		void testFindMinOfPerfectTreeWithDeletedMin()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			tree.delete(min(PERFECT_KEYS));
			assertEquals( (int) secondMin(PERFECT_KEYS), tree.findMin() );
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMaxOfZagZigTree()
		{
			ZAG_ZIG_KEYS.forEach(k -> tree.insert(k));
			assertEquals( (int) max(ZAG_ZIG_KEYS), tree.findMax() );
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMaxOfPerfectTree()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			assertEquals( (int) max(PERFECT_KEYS), tree.findMax() );
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMaxOfPerfectTreeWithDeletedZagZig()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			ZAG_ZIG_KEYS.forEach(k -> tree.delete(k));
			assertEquals( (int) max(PERFECT_KEYS), tree.findMax() );
		}
		
		@Tag(MAX_TAG)
		@Test
		void testFindMaxOfPerfectTreeWithDeletedMax()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			tree.delete(max(PERFECT_KEYS));
			assertEquals( (int) secondMax(PERFECT_KEYS), tree.findMax() );
		}
		
		@Tag(HEIGHT_TAG)
		@Test
		void testHeightWithDeletedWhereDeletionsStartAtRoot()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			for (int key : PERFECT_KEYS) {
				tree.delete(key);
				assertEquals( 2, tree.heightWithDeleted() );
			}
		}
		
		@Tag(HEIGHT_TAG)
		@Test
		void testHeightWithDeletedWhereDeletionsStartAtLeaves()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			for (int key : reversed(PERFECT_KEYS)) {
				tree.delete(key);
				assertEquals( 2, tree.heightWithDeleted() );
			}
		}
		
		@Tag(SIZE_TAG)
		@Test
		void testSizeWithDeletedWhereDeletionsStartAtRoot()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			for (int key : PERFECT_KEYS) {
				tree.delete(key);
				assertEquals( PERFECT_KEYS.size(), tree.sizeWithDeleted() );
			}
		}
		
		@Tag(SIZE_TAG)
		@Test
		void testSizeWithDeletedWhereDeletionsStartAtLeaves()
		{
			PERFECT_KEYS.forEach(k -> tree.insert(k));
			for (int key : reversed(PERFECT_KEYS)) {
				tree.delete(key);
				assertEquals( PERFECT_KEYS.size(), tree.sizeWithDeleted() );
			}
		}
		
	}
	
	
	///////////////
	// UTILITIES //
	///////////////
	
	/**
	 * Invokes {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)} on the <code>tree</code>
	 * parameter and then returns the output string from the most recent successful invocation of said method.
	 * 
	 * @param tree the <code>LazyBinarySearchTree</code> on which to invoke <code>printAllWithDeleted</code>
	 * @param ps the <code>PrintStream</code> to serve as the argument for the <code>out</code> parameter of
	 * {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)}
	 * @return the output of the most recent successful <code>tree.printAllWithDeleted</code> invocation
	 */
	String getPrintAllOutput(LazyBinarySearchTree tree, PrintStream ps)
	{
		tree.printAllWithDeleted(ps);
		return baos.toString();
	}
	
	/**
	 * Invokes {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)} on the <code>tree</code>
	 * parameter and then returns the normalized output string from the most recent successful invocation
	 * of said method.
	 * 
	 * @param tree the <code>LazyBinarySearchTree</code> on which to invoke <code>printAllWithDeleted</code>
	 * @param ps the <code>PrintStream</code> to serve as the argument for the <code>out</code> parameter of
	 * {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)}
	 * @return the normalized output of the most recent successful <code>tree.printAllWithDeleted</code> invocation
	 */
	String getNormalizedPrintAllOutput(LazyBinarySearchTree tree, PrintStream ps)
	{
		tree.printAllWithDeleted(ps);
		return normalizedPrintAllOutput();
	}
	
	/**
	 * Attempts to normalize the output of {@link LazyBinarySearchTree#printAllWithDeleted(PrintStream)}
	 * according to the Project 3 specifications.  Dependent upon the {@link ContainsTest#baos} field to
	 * which the output is expected to be written by way of the intermediate {@link ContainsTest#ps} field.
	 * <p>
	 * The purpose of this method is to be able to verify the state of the tree using the student's
	 * {@link LazyBinarySearchTree#printAllWithDeleted} method without having a test fail due to simple
	 * formatting issues.  However, direct testing of the <code>printAllWithDeleted</code> method itself
	 * should not use this utility, as formatting would actually be part of the tested behavior in that case.
	 * 
	 * @return the normalized output of the most recent successful <code>tree.printAllWithDeleted</code> invocation
	 */
	String normalizedPrintAllOutput()
	{
		return baos.toString()
				.replaceAll("\\s*\\*", " *")  // ensure exactly one space preceding any asterisk
				.replaceAll("\\*\\s+", "*")  // ensure no whitespace immediately following any asterisk
				.replaceAll("\\s{2,}", " ")  // reduce extents of whitespace to exactly one space
				.replaceAll("[\\p{Space}&&[^ ]]", " ")  // replace any remaining non-space whitespace characters with spaces
				.trim();  // remove leading and trailing whitespace
	}
	
	static String simulatePrintAll(List<Integer> keys)
	{
		return keys.stream()
				.sorted()
				.map(i -> i.toString())
				.collect(Collectors.joining(" "));
	}
	
	static String simulatePrintAllWithDeleted(List<Integer> allKeys, List<Integer> deletedKeys)
	{
		return allKeys.stream()
				.sorted()
				.map(i -> deletedKeys.contains(i) ? "*" + i.toString() : i.toString())  // inefficient contains algo, but doesn't matter
				.collect(Collectors.joining(" "));
	}

	static <T extends Comparable<T>> T min(List<T> list)
	{
		return list.stream().min(Comparator.naturalOrder()).get();
	}
	
	static <T extends Comparable<T>> T max(List<T> list)
	{
		return list.stream().max(Comparator.naturalOrder()).get();
	}
	
	static <T extends Comparable<T>> T secondMin(List<T> list)
	{
		return list.stream().sorted().skip(1).findFirst().get();  // inefficient algo, but doesn't matter
	}
	
	static <T extends Comparable<T>> T secondMax(List<T> list)
	{
		return list.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();  // inefficient algo, but doesn't matter
	}

	static <T> List<T> reversed(List<T> list)
	{
		List<T> copy = new ArrayList<>(list);
		Collections.reverse(copy);
		return copy;
	}
	
}
