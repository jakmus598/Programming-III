/**
 * @author Jake Musleh
 * Email: jmusleh@wisc.edu
 * September 15, 2019
 * COMP SCI 400-001
 * This program implements DataStructureADT. It stores entries that are key, value pairs. The entries
 * are organized in descending order by the return value of compareTo such that a call to compareTo
 * will always return a positive number if it is called by the last entry.
 *
 */

public class DS_My implements DataStructureADT {

	private int size; //The current number of entries stored
	private Pair[] keyValuePairs; //The array that stores the entries
	private final int CAPACITY = 1000; //The initial length of the array referred to by keyValuePairs
	
    //Creates a new instance of DS_My and initializes its fields
    public DS_My() {
        size = 0;
        keyValuePairs = new Pair[CAPACITY];
    }

    @Override
    public void insert(Comparable k, Object v) {
    	if(k == null)
    	{
    		throw new IllegalArgumentException("Null key");
    	}
    	
    	else
    	{
    		//Looks through all the elements in the array to see where specified key belongs
    		for(int i = 0; i <= size; i++)
    		{
    			if(i == size)
    			{
    				//If a key is greater than all the keys in the array, it goes at the end
    					keyValuePairs[size] = new Pair(k, v);
    					size++;
    					break;
    			}
    			
    			//Compares to see if key to insert comes before or after the current key being visited
    			int comparasion = keyValuePairs[i].getKey().compareTo(k);
    			
    			if(comparasion < 0)
    			{
    				//Shifts all entries following key to insert to the right
    				for(int j = size; j > i; j--)
    				{
    					keyValuePairs[j] = keyValuePairs[j - 1];
    				}
    				keyValuePairs[i] = new Pair(k, v);
    				size++;
    				break;
    			}
    			else if(comparasion == 0)
    			{
    				throw new RuntimeException("Duplicate key");
    			}
    			else
    			{
    				//If comparasion > 0, the loop continues to execute
    			}
    		}
    	}
    }
    
    
    @Override
    public boolean remove(Comparable k) {
    	if(k == null)
    	{
    		//Cannot remove a null key
    		throw new IllegalArgumentException("Null key");
    	}
    	else
    	{
    		int position = getPosition(k);
    		//Checks to see that key exists in keyValuePairs
    		if(position != -1)
    		{
    			//Shifts entries to the left
    			for(int i = position; i < size; i++)
    			{
    				keyValuePairs[i] = keyValuePairs[i + 1];
    			}
    			size--;
    			return true;
    		}
    		
    		else
    		{
    			return false;
    		}
    	}
    }

    @Override
    public boolean contains(Comparable k) {
    	if(k == null)
    	{
    		//Cannot have a null key
    		return false;
    	}
    	return getPosition(k) != -1; 
    }

    @Override
    public Object get(Comparable k) {
    	if(k == null)
    	{
    		//Cannot have a null key
    		throw new IllegalArgumentException("Null key");
    	}
    	
    	//Checks to see if key exists in keyValuePairs
    	if(getPosition(k) != -1)
    	{
    		return keyValuePairs[getPosition(k)].getValue();
    	}
    	else
    	{
    		return null;
    	}
    }

    @Override
    public int size() {
    	return size;
    }
    
    //Gets the index of a given key in keyValuePairs
    //Returns the index if key is found, -1 if it is not
    private int getPosition(Comparable k)
    {
    	for(int i = 0; i < size; i++)
		{
			if(keyValuePairs[i].getKey().compareTo(k) == 0)
			{
				return i;
			}
		}
    	return -1; //If key is not found
    }    
    
    
//A class to represent a single key/value entry
//The key must be Comparable
private class Pair
{
	private Comparable key; //The key
	private Object value; //The value
	
	//Creates a new instance of pair and initializes its fields
	private Pair(Comparable key, Object value)
	{
		this.key = key;
		this.value = value;
	}
	
	//Gets the value of this pair
	private Object getValue()
	{
		return value;
	}
	
	//Gets the key of this pair
	private Comparable getKey()
	{
		return key;
	}
}

}