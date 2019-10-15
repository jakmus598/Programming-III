import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class DataStructureADTTest<T extends DataStructureADT<String,String>> {
	
	private T dataStructureInstance;
	
	protected abstract T createInstance();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		dataStructureInstance = createInstance();
	}

	@AfterEach
	void tearDown() throws Exception {
		dataStructureInstance = null;
	}

	
	@Test
	void test00_empty_ds_size() {
		if (dataStructureInstance.size() != 0)
		fail("data structure should be empty, with size=0, but size="+dataStructureInstance.size());
	}
	
	@Test
	void test01_after_insert_one_size_is_one() {
		dataStructureInstance.insert("Key", "Value");
		if(dataStructureInstance.size() != 1)
		{
			fail("Data structure should have a size = 1, but instead size = " + 
		    dataStructureInstance.size());
		}
	}
	
	@Test
	void test02_after_insert_one_remove_one_size_is_zero() {
		dataStructureInstance.insert("Key", "Value");
		dataStructureInstance.remove("Key");
		if(dataStructureInstance.size() != 0)
		{
			fail("After removal, size of data structure should be 0, but instead size = " +
		    dataStructureInstance.size());
		}
		
	}
	
	@Test
	void test03_duplicate_exception_is_thrown() {
		try
		{
			dataStructureInstance.insert("A", "B");
			dataStructureInstance.insert("E", "F");
			dataStructureInstance.insert("G", "Q");
			dataStructureInstance.insert("A", "X");
			fail("Inserting a duplicate key should have produced a runtime exception");
		}
		catch(RuntimeException e)
		{
			//Test has passed, no further code needed
		}
		catch(Exception e)
		{
			fail("The wrong type of exception has been thrown");
		}
	}
	
	@Test
	void test04_remove_returns_false_when_key_not_present() {
		dataStructureInstance.insert("17", "32");
		dataStructureInstance.insert("41", "79");
		boolean removalSuccessful = dataStructureInstance.remove("34");
		if(removalSuccessful)
		{
			fail("Removal of a key that does not exist in dataStructureInstance should have returned false");
		}
	}
	
	@Test
	void test05_ds_can_store_at_least_500_entries() {
		try
		{
			int size = dataStructureInstance.size();
			for(int i = 0; i < 500; i++)
			{
				String key = "key #: " + i; //Prevents duplicate keys
				dataStructureInstance.insert(key, "testValue");
			}
			
		}
		catch(Exception e)
		{
			fail("dataStructureInstance should be able to store at least 500 entries");
		}
		
		if(dataStructureInstance.size() < 500)
		{
			fail("dataStructureInstance should be able to store at least 500 entries");
		}
		
	}
	
	@Test
	void test06_contains_with_a_null_key()
	{
		try
		{
			boolean isNullTrue = dataStructureInstance.contains(null);
			if(isNullTrue)
			{
				fail("A null key should return false");
			}
		}
		catch(NullPointerException e)
		{
			fail("A null key should return false and not throw an exception");
		}
		catch(Exception e)
		{
			fail("A null key should return false and not throw an exception");
		}
	}
	
	@Test
	void test07_does_size_update_properly_after_many_insertions()
	{
		try
		{
			for(int i = 0; i < 100; i++)
			{
				dataStructureInstance.insert("key #: " + i, "value");
			}
		}
		catch(Exception e)
		{
			fail("dataStructureInstance should be able to store at least 500 entries");
		}
		
		if(dataStructureInstance.size() != 100)
		{
			fail("The size of the data structure should be 100, but instead it is " + dataStructureInstance.size());
		}
	}
	
	@Test
	void test08_readding_a_key_that_was_removed()
	{
		try
		{
			dataStructureInstance.insert("A", "1");
			dataStructureInstance.remove("A");
			dataStructureInstance.insert("A", "1");
		}
		
		catch(Exception e)
		{
			fail("Adding a removed key should not throw an exception");
		}
	}
	
	@Test
	void test09_remove_with_a_null_key()
	{
		try
		{
			dataStructureInstance.remove(null);
			fail("Removal of a null key should throw an IllegalArgumentException");
		}
		
		catch(IllegalArgumentException e)
		{
			//Test has passed, no further code needed
		}
		
		catch(Exception e)
		{
			fail("Removal of a null key should throw an IllegalArgumentException");
		}
	}
	
	@Test
	void test10_does_get_change_size()
	{
		dataStructureInstance.insert("A", "1");
		dataStructureInstance.insert("B", "2");
		dataStructureInstance.get("A");
		if(dataStructureInstance.size() != 2)
		{
			fail("Calling get() should not change the size of dataStructureInstance");
		}
	}
	
	
}

	
	
	// TODO: implement tests 01 - 04

	// test01_after_insert_one_size_is_one
	
	// test02_after_insert_one_remove_one_size_is_0
	
	// test03_duplicate_exception_is_thrown
	
	// test04_remove_returns_false_when_key_not_present

	
	// TODO: add tests to ensure that you can detect implementation that fail
	
	// Tip: consider different numbers of inserts and removes and how different combinations of insert and removes



