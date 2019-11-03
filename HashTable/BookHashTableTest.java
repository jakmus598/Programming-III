/**
 * October 25, 2019
 * Filename:   BookHashTableTest.java
 * Project:    p3a
 * Authors:    Debra Deppeler (deppeler@cs.wisc.edu)
 * 			   Jake Musleh (jmusleh@wisc.edu)
 * Description: A test class for BookHashTable
 * 
 * */

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** 
 * Test HashTable class implementation to ensure that required 
 * functionality works for all cases.
 */
public class BookHashTableTest {

    // Default name of books data file
    public static final String BOOKS = "books_clean.csv";

    // Empty hash tables that can be used by tests
    static BookHashTable bookObject;
    static ArrayList<Book> bookTable;

    static final int INIT_CAPACITY = 2;
    static final double LOAD_FACTOR_THRESHOLD = 0.49;
       
    static Random RNG = new Random(0);  // seeded to make results repeatable (deterministic)

    /** Create a large array of keys and matching values for use in any test */
    @BeforeAll
    public static void beforeClass() throws Exception{
        bookTable = BookParser.parse(BOOKS);
    }
    
    /** Initialize empty hash table to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
        // TODO: change HashTable for final solution
         bookObject = new BookHashTable(INIT_CAPACITY,LOAD_FACTOR_THRESHOLD);
    }

    /** Not much to do, just make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        bookObject = null;
    }

    private void insertMany(ArrayList<Book> bookTable) 
        throws IllegalNullKeyException, DuplicateKeyException {
        for (int i=0; i < bookTable.size(); i++ ) {
            bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
        }
    }

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_collision_scheme() {
        if (bookObject == null)
        	fail("Gg");
    	int scheme = bookObject.getCollisionResolutionScheme();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }


    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_IsEmpty() {
        //"size with 0 entries:"
        assertEquals(0, bookObject.numKeys());
    }

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is not empty after adding one (key,book) pair
     * @throws DuplicateKeyException 
     * @throws IllegalNullKeyException 
     */
    @Test
    public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        String expected = ""+1; 
        //"size with one entry:"
        assertEquals(expected, ""+bookObject.numKeys());
    }
    
    /** IMPLEMENTED AS EXAMPLE FOR YOU 
    * Test if the hash table  will be resized after adding two (key,book) pairs
    * given the load factor is 0.49 and initial capacity to be 2.
    */
    
    @Test 
    public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    	int cap1 = bookObject.getCapacity(); 
    	bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    	int cap2 = bookObject.getCapacity();
    	
        //"size with one entry:"
        assertTrue(cap2 > cap1 & cap1 ==2);
    }
    
    /**
     * Tests whether an exception is thrown when attempting to insert duplicate keys
     * @throws IllegalNullKeyException
     * @throws DuplicateKeyException
     */
    
    @Test
    public void test003_InsertDuplicateKeys() throws IllegalNullKeyException, DuplicateKeyException
    {
    	try
    	{
    		bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    		bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    		fail("Insertion of duplicate keys should have thrown an exception");
    	}
    	catch(DuplicateKeyException e)
    	{
    		//Test has passed, nothing to execute
    	}
    	catch(Exception e)
    	{
    		fail("Wrong type of exception was thrown");
    	}
    
    }
    
    /**
     * Checks to see if the number of keys is correctly updated after several insertions and removals
     * @throws IllegalNullKeyException
     * @throws DuplicateKeyException
     */
    
	@Test
	public void test004_CheckNumKeys() throws IllegalNullKeyException, DuplicateKeyException
	{
		//The position of the one hundreth valid entry that can be inserted into the table without an IndexOutOfBoundsException
		
		try
		{
			for(int i = 0; i < bookTable.size(); i++)
			{
				bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
			}
			
			//30 was chosen as an arbitrary number
			for(int i = 0; i < 30; i++)
			{
				bookObject.remove(bookTable.get(i).getKey());
			}
			
			if(bookObject.numKeys() != bookTable.size() - 30)
			{
				fail("bookObject has a current size of " + bookObject.numKeys() + " when it should have a size of " + (bookTable.size() - 30) );
			}
		}
		catch(Exception e)
		{
			fail("An exception has been thrown when none were expected");
		}
	}
	
	
	/**
	 * Checks if the hash table correctly updates its capacity when necessary
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test005_DoesCapacityUpdateCorrectly() throws IllegalNullKeyException, DuplicateKeyException
	{
		//Checks that the table is at correct capacity at all times
		try
		{
			for(int i = 0; i < bookTable.size(); i++)
			{
				int correctCapacity = bookObject.getCapacity();
				if(bookObject.getLoadFactorThreshold() <= (double)(bookObject.numKeys())/bookObject.getCapacity()) //Should resizing occur?
				{
					correctCapacity = bookObject.getCapacity()*2 + 1;
				}
				bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
				if(correctCapacity != bookObject.getCapacity())
				{
					fail("The hash table does not update its capacity correctly");
				}
			}
		}
		
		catch(Exception e)
		{
			fail("Exception thrown when none were expected");
		}
	}
	
	
	/**
	 * Checks to see if removal returns false when attempting to remove a nonexistent key
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test006_RemovalOfANonexistentKey() throws IllegalNullKeyException, DuplicateKeyException
	{
		try
		{
			bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
			bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
			if(bookObject.remove(bookTable.get(2).getKey()))
			{
				fail("Removal of a key that does not exist should return false");
			}
		}
		
		catch(Exception e)
		{
			fail("There was an exception thrown when there were none expected");
		}
	}
	
	/**
	 * Checks to see if inserting and removing null keys throws the proper IllegalNullKeyException
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test007_TestInsertAndRemovalOfNullKeys() throws IllegalNullKeyException, DuplicateKeyException
	{
		try
		{
			bookObject.insert(null, null);
			fail("Inserting a null key should have thrown an exception");
		}
		catch(IllegalNullKeyException e)
		{
			try
			{
				bookObject.remove(null);
				fail("Removing a null key should have thrown an exception");
			}
			catch(IllegalNullKeyException k)
			{
				//Test has passed, no more code to execute
			}
		}	
	}
	
	
	/**
	 * Checks to ensure that get returns the correct Book associated with a given key
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 * @throws KeyNotFoundException
	 */
	@Test
	public void test008_TestGet() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException
	{
		
		try
		{
			for(int i = 0; i < bookTable.size(); i++)
			{
				bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
			}
			
			for(int i = 0; i < bookTable.size(); i++)
			{
				if(!bookObject.get(bookTable.get(i).getKey()).toString().equals(bookTable.get(i).toString()))
				{
					fail("Get method did not return the correct Book object");
				}
			}
		}
				
		catch (Exception e)
			{
				fail("There was an exception thrown when none were expected");
			}
			
	}
	
	/**
	 * Checks to ensure that getting an already deleted Book returns false
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 * @throws KeyNotFoundException
	 */
	@Test
	public void test009_GetARemovedKey() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException
	{
		try
		{
			bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
			bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
			boolean wasKeyFound = bookObject.remove(bookTable.get(0).getKey());
			if(!wasKeyFound)
			{
				fail("Removal of an existing key should return true");
			}
		}
		catch(Exception e)
		{
			fail("Exceptionm thrown when there were none expected");
		}
		
		try
		{
			bookObject.get(bookTable.get(0).getKey());
			fail("Getting a key that has been deleted should throw a KeyNotFoundException");
		}
		
		catch(KeyNotFoundException e)
		{
			//Test has passed, no more code to execute
		}
		
		catch(Exception e)
		{
			fail("Wrong type of exception thrown");
		}
		
	}
	
	/**
	 * Checks to ensure that removing an already deleted key returns false
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test010_RemovalOfAnAlreadyDeletedKey() throws IllegalNullKeyException, DuplicateKeyException
	{
		try
		{
			bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
			boolean wasRemoveSuccessful = bookObject.remove(bookTable.get(0).getKey());
			if(!wasRemoveSuccessful)
			{
				fail("Removal of an existing key should return true");
			}
			
			boolean wasSecondRemoveSuccessful = bookObject.remove(bookTable.get(0).getKey());
			
			if(wasSecondRemoveSuccessful)
			{
				fail("Removal of a nonexistent key should return false");
			}
		}
		catch(Exception e)
		{
			fail("No exception should be thrown yet");
		}
		
	}
	
	/**
	 * Checks to ensure that a large number of elements, 1000 specifically, can be entered correctly
	 * Note: Since bookTable does not contain one thousand entries, this method generates its own Book objects
	 * @throws IllegalNullKeyException
	 * @throws DuplicateKeyException
	 * @throws KeyNotFoundException
	 */
	@Test
	public void test011_InsertionOfOneThousandElements() throws IllegalNullKeyException, DuplicateKeyException, KeyNotFoundException
	{
		try
		{
			for(int i = 0; i < 1000; i++)
			{
				bookObject.insert("" + i, new Book("" + i, null, null, null, null, null, null, null));
			}
			
			for(int i = 0; i < 1000; i++)
			{
				if(!bookObject.get("" + i).toString().equals(new Book("" + i, null, null, null, null, null, null, null).toString()))
				{
					fail("All elements should exist in the hash table");
				}
			}
		}
		
		catch(Exception e)
		{
			fail("No exception should be thrown");
		}
		
	}
	
	/**
	 * Tests to ensure that the correct load factor threshold is stored by bookObject
	 */
	@Test
	public void testLoadFactorThreshold()
	{
		if(bookObject.getLoadFactorThreshold() != LOAD_FACTOR_THRESHOLD)
		{
			fail("bookObject should have a load factor threshold of " + LOAD_FACTOR_THRESHOLD);
		}
	}
			
}

    
    

