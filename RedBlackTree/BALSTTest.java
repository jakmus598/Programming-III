import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

//Needed to test traversal methods
import java.util.List;

/**
 * @author Jake Musleh
 * October 8, 2019
 * COMPSCI 400-001
 * This class implements the BALST interface using red black tree properties 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */

// TODO: Add tests to test the tree is balanced or not

//@SuppressWarnings("rawtypes")
public class BALSTTest {

    BALST<String,String> balst1;	
    BALST<Integer,String> balst2;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        balst1 = createInstance();
	balst2 = createInstance2();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        balst1 = null;
	balst2 = null;
    }

    protected BALST<String, String> createInstance() {
        return new BALST<String,String>();
    }

    protected BALST<Integer,String> createInstance2() {
        return new BALST<Integer,String>();
    }

    /** 
     * Insert three values in sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred.
     */
    @Test
    void testBALST_001_insert_sorted_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("avl insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20)) 
                fail("avl insert to right child of root does not work");
            
            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));

            balst2.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 000: "+e.getMessage() );
        }
    }

    /** 
     * Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_002_insert_reversed_sorted_order_simple() {
    	try
    	{
    		balst2.insert(30, "10");
    		if(!balst2.getKeyAtRoot().equals(30))
    		{
    			fail("The first insert of an avl tree should be at its root");
    		}
    		
    		balst2.insert(20, "15");
    		if(!balst2.getKeyOfLeftChildOf(30).equals(20))
    		{
    			fail("A key that is greater than the root should be in its right subtree");
    		}
    		
    		balst2.insert(10, "5");
    		if(!balst2.getKeyAtRoot().equals(20))
    		{
    			fail("Rebalancing should have caused the root node to have a key of 20");
    		}
    		
    		if(!(balst2.getKeyOfLeftChildOf(20).equals(10)) || !(balst2.getKeyOfRightChildOf(20).equals(30)))
    		{
    			fail("The root does not have the proper left and right subtrees");
    		}
    				
    	}	
    	
    	catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
    	
    	catch(KeyNotFoundException e)
    	{
    		fail("All keys should have been in the tree");
    	}
    }

    /** 
     * Insert three values so that a right-left rotation is
     * needed to fix the balance.
     * 
     * Example: 10-30-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_003_insert_smallest_largest_middle_order_simple() {
    	try
    	{
    		balst2.insert(10, "15");
    		if(!balst2.getKeyAtRoot().equals(10))
    		{
    			fail("The first insert of an avl tree should be at its root");
    		}
    		
    		balst2.insert(30, "10");
    		if(!balst2.getKeyOfRightChildOf(10).equals(30))
    		{
    			fail("A key that is less than the root should be in its left subtree");
    		}
    		
    		balst2.insert(20, "5");
    		if(!balst2.getKeyAtRoot().equals(20))
    		{
    			fail("Rebalancing should have caused the root node to have a key of 20");
    		}
    		
    		if(!(balst2.getKeyOfLeftChildOf(20).equals(10)) || !(balst2.getKeyOfRightChildOf(20).equals(30)))
    		{
    			fail("The root does not have the proper left and right subtrees");
    		}
    				
    	}	
    	
    	catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
    	
    	catch(KeyNotFoundException e)
    	{
    		fail("All keys should have been in the tree");
    	}
    }
    	

    /** 
     * Insert three values so that a left-right rotation is
     * needed to fix the balance.
     * 
     * Example: 30-10-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_004_insert_largest_smallest_middle_order_simple() {
    	try
    	{
    		balst2.insert(30, "15");
    		if(!balst2.getKeyAtRoot().equals(30))
    		{	
    			fail("The first insert of an avl tree should be at its root");
    		}
    			
    		
    		balst2.insert(10, "10");
    		if(!balst2.getKeyOfLeftChildOf(30).equals(10))
    		{
    			fail("A key that is less than the root should be in its left subtree");
    		}
    		
    		balst2.insert(20, "5");
    		if(!balst2.getKeyAtRoot().equals(20))
    		{
    			fail("Rebalancing should have caused the root node to have a key of 20");
    		}
    		
    		if(!(balst2.getKeyOfLeftChildOf(20).equals(10)) || !(balst2.getKeyOfRightChildOf(20).equals(30)))
    		{
    			fail("The root does not have the proper left and right subtrees");
    		}
    				
    	}	
    	
    	catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
    	
    	catch(KeyNotFoundException e)
    	{
    		fail("All keys should have been in the tree");
    	}
    	
    }
        

	@Test 
	void does_rebalancing_work_with_large_sets_of_data()
	{
		try
		{
			//Inserts random nodes
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
			
			//Checks old-school way to see if nodes are rebalanced
			//Note: The left child of the root will not have any grandchildren
			boolean rootIsCorrect = balst2.getKeyAtRoot() == 7;
			if(!rootIsCorrect)
			{
				fail("Root is not correct");
			}
			boolean leftChildOfRoot = balst2.getKeyOfLeftChildOf(7) == 3;
			if(!leftChildOfRoot)
			{
				fail("Left child of root is not correct");
			}
			boolean childrenOfLeftChild = balst2.getKeyOfLeftChildOf(3) == 2 && balst2.getKeyOfRightChildOf(3) == 4;
			if(!childrenOfLeftChild)
			{
				fail("The children of the left child are not correct");
			}
			boolean rightChildOfRoot = balst2.getKeyOfRightChildOf(7) == 25;
			if(!rightChildOfRoot)
			{
				fail("The right child of the root is incorrect");
			}
			boolean childrenOfRightChild = balst2.getKeyOfLeftChildOf(25) == 14 && balst2.getKeyOfRightChildOf(25) == 80;
			if(!childrenOfRightChild)
			{
				fail("The children of the right child are incorrect");
			}
			boolean rightGrandchildrenOfRightChild = balst2.getKeyOfLeftChildOf(14) == 9 && balst2.getKeyOfRightChildOf(14) == 20;
			if(!rightGrandchildrenOfRightChild)
			{
				fail("The right grandchildren of the right child are incorrect");
			}
			boolean leftGrandchildrenOfRightChild = balst2.getKeyOfLeftChildOf(80) == 54 && balst2.getKeyOfRightChildOf(80) == 434;
			if(!leftGrandchildrenOfRightChild)
			{
				fail("The left grandchildren of the right child are incorrect");
			}
			if(!(rootIsCorrect && leftChildOfRoot && childrenOfLeftChild && rightChildOfRoot && childrenOfRightChild &&
				rightGrandchildrenOfRightChild && leftGrandchildrenOfRightChild))
			{
				fail("The tree did not rebalance properly");
			}
		}
			
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
    	
    	catch(KeyNotFoundException e)
    	{
    		fail("All keys should have been in the tree");
    	}
	}
    
	@Test
	void testBALST_005_does_get_height_work()
	{
		try
		{
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
			if(balst2.getHeight() != 4)
			{
				fail("The height of the tree should be 4.");
			}
		}
		
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
	}
	
	@Test
	void testBALST_006_do_duplicates_throw_an_exception()
	{
		try
		{
			balst2.insert(4, "3");
			balst2.insert(4, "2");
			fail("Inserting duplicate keys should throw a DuplicateKeyException");
		}
		catch(DuplicateKeyException e)
		{
			//Nothing to do, test has passed
		}
		catch(IllegalNullKeyException e)
		{
			fail("There should have been a DuplicateKeyException, but instead there was a IllegalNullKeyException");
		}
	}
	
	@Test
	void testBALST_007_does_contains_work()
	{
		try
		{
			balst2.insert(6, "23");
			if(!balst2.contains(6))
			{
				fail("6 was just inserted");
			}
			
			balst2.insert(8, "3");
			balst2.remove(8);
			if(balst2.contains(8))
			{
				fail("8 was just removed");
			}
		}
		catch(Exception e)
		{
			fail("Neither insert() nor contains() should have caused an exception");
		}
	}
	
	@Test
	void testBALST_008_is_in_order_traversal_correct()
	{
		try
		{
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
			List<Integer> inOrderTraversal = balst2.getInOrderTraversal();
			Integer[] correctInOrderTraversal = {2, 3, 4, 7, 9, 14, 20, 25, 54,80, 434};
			for(int i = 0; i < inOrderTraversal.size() - 1; i++)
			{
				if(!inOrderTraversal.get(i).equals(correctInOrderTraversal[i]))
				{
					fail("An in order traversal arranges its elements from smallest to largest");
				}
				
			}
		}
			
			catch(IllegalNullKeyException e)
	    	{
	    		fail("There were no null keys added to the tree");
	    	}
	    	
	    	catch(DuplicateKeyException e)
	    	{
	    		fail("There were no duplicate keys added to the tree");
	    	}
    	
	}
	
	@Test
	void testBALST_009_is_pre_order_traversal_correct()
	{
		try
		{
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
			
			Integer[] correctPreOrder = {7, 3, 2, 4, 25, 14, 9, 20, 80, 54, 434};
			List<Integer> preOrderTraversal = balst2.getPreOrderTraversal();
			for(int i = 0; i < preOrderTraversal.size(); i++)
			{
				//System.out.println(preOrderTraversal.size() + ", " + correctPreOrder.length);
				//System.out.print(preOrderTraversal.get(i) + ", ");
				//System.out.println(correctPreOrder[10] + ", " + preOrderTraversal.get(10));
				if(!preOrderTraversal.get(i).equals(correctPreOrder[i]))
				{
					fail("The nodes are not pre ordered correctly " + i);
				}
				
				
			}
			
			System.out.println();
		}
		
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
	}
	
	@Test
	void testBALST_010_is_post_order_traversal_correct()
	{
		try
		{
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
			
			Integer[] correctPostOrder = {2, 4, 3, 9, 20, 14, 54, 434, 80, 25, 7};
			List<Integer> postOrderTraversal = balst2.getPostOrderTraversal();
			for(int i = 0; i < postOrderTraversal.size(); i++)
			{
				//System.out.print(postOrderTraversal.get(i) + ", ");
				if(!postOrderTraversal.get(i).equals(correctPostOrder[i]))
				{
					fail("The nodes are not post ordered correctly " + i);
				}
				
			}
		}
		
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
	}
	
	@Test
	void testBALST_11_is_level_order_traversal_correct()
	{
		try
		{
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
		
		
			List<Integer> levelOrderTraversal = balst2.getLevelOrderTraversal();
			Integer[] correctLevelOrder = {7, 3, 25, 2, 4, 14, 80, 9, 20, 54, 434};
			for(int i = 0; i < levelOrderTraversal.size(); i++)
			{
				if(!levelOrderTraversal.get(i).equals(correctLevelOrder[i]))
				{
					fail("The nodes are not correctly ordered");
				}
			}
		}
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
	}
	
	
	@Test
	void testBALST_012_does_get_work()
	{
		try
		{
			balst2.insert(6, "90");
			if(!balst2.get(6).equals("90"))
			{
				fail("Get did not return the proper value");
			}
			
			balst2.remove(6);
			String testString = balst2.get(6);
			fail("Getting a deleted key should throw an exception");
		}
		
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
		
		catch(KeyNotFoundException e)
		{
			//Test has passed, nothing left to execute
		}
	}
	
	@Test
	void testBALST_013_does_numKeys_work()
	{
		//Inserts 11 keys
		try
		{
			balst2.insert(7, "0");
			balst2.insert(3, "430");
			balst2.insert(2, "61");
			balst2.insert(4, "43");
			balst2.insert(20, "71");
			balst2.insert(25, "48");
			balst2.insert(434, "43");
			balst2.insert(14, "42");
			balst2.insert(9, "71");
			balst2.insert(54, "63");
			balst2.insert(80, "73");
			
			if(balst2.numKeys() != 11)
			{
				fail("After adding 11 keys, numKeys() should return 11");
			}
		}
		
		catch(IllegalNullKeyException e)
    	{
    		fail("There were no null keys added to the tree");
    	}
    	
    	catch(DuplicateKeyException e)
    	{
    		fail("There were no duplicate keys added to the tree");
    	}
		
	}
	
	@Test
	void testBALST_014_does_removing_a_deleted_key_throw_an_exception()
	{
		try
		{
			balst2.insert(5, "3");
			balst2.remove(5);
			balst2.remove(5);
			fail("Removing a deleted key should throw an exception");
		}
		
		catch(IllegalNullKeyException e)
		{
			fail("While there was supposed to be an exception thrown, it was not this one");
		}
		
		catch(DuplicateKeyException e)
		{
			fail("While there was supposed to be an exception thrown, it was not this one");
		}
		
		catch(KeyNotFoundException e)
		{
			//Correct exception thrown, no more code to execute
		}
	}
		
	
	
	
		
    	
    	
		
		//Inserts same nodes as in test 4 and checks to make sure that balst2 has a height of 4
    
    // TODO: Add your own tests
    
    // Add tests to make sure that rebalancing occurs even if the 
    // tree is larger.   Does it maintain it's balance?
    // Does the height of the tree reflect it's actual height
    // Use the traversal orders to check.
    
    // Can you insert many and still "get" them back out?
    
    // Does delete work?  Does the tree maintain balance when a key is deleted?

}

