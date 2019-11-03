import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * BookHashTable.java
 * @author Jake Musleh
 * October 25, 2019
 * Email: jmusleh@wisc.edu
 * COMPSCI 400-001
 * Description: This class implements HashTableADT to create a hash table of KeyBookPair objects. Each Book object has its own unique
 * key, which is used to determine a Book's hash index. When a collision occurs, this program uses quadratic probing to find
 * an open spot in the array. Specifically, this program computes a Book's hash index, then adds squares of the natural numbers, 
 * starting at 1, to the hash index until it finds an open spot in the array.
 * 
 */

/** HashTable implementation that uses:
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

    /** The initial capacity that is used if none is specifed user */
    static final int DEFAULT_CAPACITY = 101;
    
    
    /** The load factor that is used if none is specified by user */
    static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;
    
    private double loadFactorThreshold; //The load factor threshold specified by the user
    private int currentCapacity; //The current capacity of buckets
    private KeyBookPair[] hashTable; //The hash table array
    private int size; //The number of elements in the hash table
   
    /**
     * REQUIRED default no-arg constructor
     * Uses default capacity and sets load factor threshold 
     * for the newly created hash table.
     */
    public BookHashTable() {
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR_THRESHOLD);
    }
    
    /**
     * Creates an empty hash table with the specified capacity 
     * and load factor.
     * @param initialCapacity number of elements table should hold at start.
     * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
     */
    public BookHashTable(int initialCapacity, double loadFactorThreshold) {
    	hashTable = new KeyBookPair[initialCapacity];
    	currentCapacity = initialCapacity;
    	this.loadFactorThreshold = loadFactorThreshold;
    	size = 0;
    }
    
    @Override
    public double getLoadFactorThreshold()
    {
    	return loadFactorThreshold;
    }
    
    @Override
    public int getCapacity()
    {
    	return currentCapacity;
    }
    
    @Override
    public void insert(String key, Book book) throws IllegalNullKeyException, DuplicateKeyException
    {
    	
    	if(key == null)
    	{
    		throw new IllegalNullKeyException();
    	}
    	
    	
    	else if(find(key) != -1)
    	{
    		throw new DuplicateKeyException();
    	}
    	
    	else
    	{
    		
	    	if(loadFactorThreshold <= getLoadFactor())
	    	{
	    		resize();
	    	}
	    	
	    	KeyBookPair entry = new KeyBookPair(key, book);
	    	hashTable[getHashIndex(key)] = entry;
	    	size++;
    	}
    }
    
    @Override
    public boolean remove(String key) throws IllegalNullKeyException
    {
    	if(key == null)
    	{
    		throw new IllegalNullKeyException();
    	}
    	
    	else
    	{
	    	if(find(key) == -1)
	    	{
	    		return false;
	    	}
	    	
	    	else
	    	{
	    		hashTable[find(key)].deleted = true;
	    		//hashTable[find(key)] = new Book("deleted", null, null, null, null, null, null, null);
	    		size--;
	    		return true;
	    	}
    	}
    }
    
   
    @Override
    public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException
    {
    	if(key == null)
    	{
    		throw new IllegalNullKeyException();
    	}
    	
    	else
    	{
	    	if(find(key) == -1)
	    	{
	    		throw new KeyNotFoundException();
	    	}
	    	
	    	else
	    	{
	    		return hashTable[find(key)].book;
	    		//return hashTable[find(key)];
	    	}
    	}
    }
    
    @Override
    public int numKeys()
    {
    	return size;
    }
    
    @Override
    public int getCollisionResolutionScheme()
    {
    	return 2;
    }
    
    /**
     * Gets the index that a new book should be placed at
     * @param key - The Book's key
     * @return - Its index in the hash table
     */
    private int getHashIndex(String key)
    {
    	int index = Math.abs(key.hashCode()) % currentCapacity;
    	if(hashTable[index] == null || hashTable[index].deleted) // hashTable[index].getKey().equals("deleted"))
    	{
    		return index;
    	}
    	
    	else
    	{
	    	boolean notFound = true; //Whether or not an open spot has been found
			int numChecks = 1;
			while(notFound)
			{
				index = (Math.abs(key.hashCode()) + (int)Math.pow(numChecks,  2)) % currentCapacity;
				if(hashTable[index] == null || hashTable[index].deleted)
				{
					notFound = false;
				}
				
				else
				{
					numChecks++;
				}
			}
		return index;
    	}
	    	
	  }
    
    /**
     * Gets the index of a book that already exists in the hash table
     * @param key - The Book's key
     * @return - The Book's index, or -1 if it does not exist
     */
    private int find(String key)
    {
    	
    	boolean exists = true;
		int numChecks = 0;
		while(exists)
		{
			int index = (Math.abs(key.hashCode()) + (int)Math.pow(numChecks,  2)) % currentCapacity;
			if(hashTable[index] == null)
			{
				exists = false;
			}
			else if(hashTable[index].key.equals(key) && !hashTable[index].deleted)
			{
				return index;
			}
			
			else
			{
				numChecks++;
			}
		}
		
		return -1;
    }
    
    /**
     * Computes the current load factor of hashTable
     * @return - The load factor
     */
    private double getLoadFactor()
    {
    	double loadFactor = (double)(size)/hashTable.length;
    	return loadFactor;
    }
    
    //While they are thrown, I do not handle IllegalNullKeyException or DuplicateKeyException because resisze() is only 
    //called with existing hashTable elements
    
    /**
     * Resizes hashTable to an array that is of size 2*currentCapacity + 1
     * @throws IllegalNullKeyException - Thrown when attempting to insert a null key (not handled by this method)
     * @throws DuplicateKeyException - Thrown when attempting to insert a duplicate key (not handled by this method)
     */
    private void resize() throws IllegalNullKeyException, DuplicateKeyException
    {
    	KeyBookPair[] tempReference = hashTable; //Book[] tempReference = hashTable;
    	currentCapacity = currentCapacity * 2 + 1;
    	hashTable = new KeyBookPair[currentCapacity]; 
	    for(int i = 0; i < tempReference.length; i++)
	    {
	    	if(tempReference[i] != null && !tempReference[i].deleted)
	    	{
	    		hashTable[getHashIndex(tempReference[i].key)] = tempReference[i];
	    	}
	    	
    	}
    }
    
    /**
     * A class that stores pairs of Books and their keys
     *
     */
    private class KeyBookPair
    {
    	private String key; //The book's key 
    	private Book book; //The book
    	private boolean deleted; //Whether or not the book has been deleted
    	
    	private KeyBookPair(String key, Book book)
    	{
    		this.key = key;
    		this.book = book;
    		this.deleted = false;
    	}
    }
 }

    
    
    
    
    
    
    
    // TODO: add all unimplemented methods so that the class can compile

