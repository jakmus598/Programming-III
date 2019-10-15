import java.util.List;
import java.util.LinkedList;


/**
 * @author Jake Musleh
 * October 8, 2019
 * COMPSCI 400-001
 * This class implements the BALST interface using red black tree properties 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

	private RBNode<K, V> root; //The root of the tree

	private int numKeys; //The number of total keys in the tree

	public BALST() 
	{
		root = null;
		numKeys = 0;
	}

	@Override
	public K getKeyAtRoot() {
		if(root != null)
		{
			return root.key;
		}
		return null;
	}

	@Override
	public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null)
		{
			throw new IllegalNullKeyException();
		}
		
		//Searches to locate the desired parent node by its key, then gets its left child
		RBNode<K, V> nodeWithKey = findNodeByKey(key, root);
		if(nodeWithKey == null)
		{
			throw new KeyNotFoundException();
		}
		else
		{
			K leftKey = findNodeByKey(key, root).left.key;
			return leftKey;
		}
	}

	@Override
	public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null)
		{
			throw new IllegalNullKeyException();
		}
		
		//Searches to locate the desired parent node by its key, then gets its right child
		RBNode<K, V> nodeWithKey = findNodeByKey(key, root);
		
		if(nodeWithKey == null)
		{
			throw new KeyNotFoundException();
		}
		
		else
		{
			K rightKey = findNodeByKey(key, root).right.key;
			return rightKey;
		}
	}
	

	@Override
	public int getHeight() {
		return getHeight(root);
	
	}

	@Override
	public List<K> getInOrderTraversal() {
		List<K> keyList = new LinkedList();
		inOrderTraversal(keyList, root);
		return keyList;
		
		//return inOrderTraversal(keyList, root);
	}

	@Override
	public List<K> getPreOrderTraversal() {
		List<K> keyList = new LinkedList();
		preOrderTraversal(keyList, root);
		return keyList;
	}

	@Override
	public List<K> getPostOrderTraversal() {
		List<K> keyList = new LinkedList();
		postOrderTraversal(keyList, root);
		return keyList;
	}

	@Override
	public List<K> getLevelOrderTraversal() {
		List<K> keyList = new LinkedList(); //A list of all the keys
		List<RBNode<K, V>> nodeList = new LinkedList(); //A list of all the nodes
		nodeList.add(root);
		keyList.add(root.key);
		return levelOrderTraversal(keyList, nodeList);
	}

	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if(key == null)
		{
			throw new IllegalNullKeyException();
		}
		
		root = insert(key, value, root);
	}

	@Override
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null)
		{
			throw new IllegalNullKeyException();
		}
		
		RBNode<K, V> nodeOfKey = findNodeByKey(key, root);
		if(nodeOfKey == null || nodeOfKey.isDeleted)
		{
			throw new KeyNotFoundException();
		}
		
		else
		{
			nodeOfKey.isDeleted = true;
			numKeys--;
			return true;
		}
		
	}

	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null)
		{
			throw new IllegalNullKeyException();
		}
		RBNode<K, V> nodeToRetrieve = findNodeByKey(key, root);
		if(nodeToRetrieve == null)
		{
			throw new KeyNotFoundException();
		}
		
		else
		{
			return nodeToRetrieve.value;
		}
	}

	@Override
	public boolean contains(K key) throws IllegalNullKeyException {
		if(key == null)
		{
			throw new IllegalNullKeyException();
		}
		
		RBNode<K, V> desiredNode = findNodeByKey(key, root);
		
		return desiredNode != null && !desiredNode.isDeleted;
	}

	@Override
	public int numKeys()
	{
		return numKeys;
	}

	@Override
	public void print() 
	{
		printSubtree(root);
	}
	
	
	//Prints the subtree starting at the given root
	private void printSubtree(RBNode<K, V> root) {
		if (root == null) 
		{
			//Nothing to execute
		}

		List<RBNode<K, V>> nodeList = new LinkedList<RBNode<K, V>>(); //Stores the nodes that need to be printed
		nodeList.add(root); //Adds the root node to the list of nodes to be printed

		int height = getHeight(root); //Initializes the variable that will be used to determine how many levels are left to print

		//While there are still levels of the tree to be printed
		for(int i = 0; i < getHeight(root); i++ ) 
		{
			int numNodes = nodeList.size(); //Total number of nodes to print
			if (numNodes == 0)
			{
				break;
			}

			//Prints the head of the node list along with the proper number of spaces before and after
			while (numNodes > 0) {
				
				RBNode<K, V> nodeToAdd = nodeList.get(0); 
				String totalSpaces = "";
				for (int j = 0; j < (Math.pow(2, height) - 1); j++) 
				{
					totalSpaces += " ";
				}
				
				System.out.print(totalSpaces);
				if(nodeToAdd == null)
				{
					System.out.print(" ");
				}
				else
				{
					System.out.print(nodeToAdd.key);
				}
				System.out.print(totalSpaces);
				
				nodeList.remove(nodeToAdd);

				if (nodeToAdd != null)
				{
					if (nodeToAdd.left != null) 
					{
						nodeList.add(nodeToAdd.left);
					} 
					else 
					{
						nodeList.add(null);
					}
					
					if (nodeToAdd.right != null)
					{
						nodeList.add(nodeToAdd.right);
					} 
					else 
					{
						nodeList.add(null);
					}
				}
				numNodes--;
			}
			System.out.println();
			System.out.println();
			height--;
		}
	}
	
	//Returns a list whose nodes are in level order
	private List<K> levelOrderTraversal(List<K> list, List<RBNode<K, V>> nodeList)
	{
		//int startingPosition = list.get(list.size() - Math.pow(2, levelNumber - 1));
		for(int i = 0; i < getHeight(); i++)
		{
			int startingPosition = list.size() - (int)(Math.pow(2,  i - 1));
			for(int j = startingPosition; j < nodeList.size(); j++)
			{
				if(nodeList.get(j).left != null)
				{
					list.add(nodeList.get(j).left.key);
					nodeList.add(nodeList.get(j).left);
				}
				if(nodeList.get(j).right != null)
				{
					list.add(nodeList.get(j).right.key);
					nodeList.add(nodeList.get(j).right);			
				}
			}
			
		}
		return list;
	}
	
	//Returns a list whose nodes aren't preordered
	private void preOrderTraversal(List<K> keyList, RBNode<K, V> startingNode)
	{
		if(startingNode == null)
		{
			//No code to execute
		}
		
		else
		{
			if(!startingNode.isDeleted)
			{
				keyList.add(startingNode.key);
			}
			
			if(startingNode.left != null && !startingNode.left.isDeleted)
			{
				preOrderTraversal(keyList, startingNode.left);
			}
			
			if(startingNode.right != null && !startingNode.right.isDeleted)
			{
				preOrderTraversal(keyList, startingNode.right);
			}
		}
	}
	
	//Organizes nodes in post order
	private void postOrderTraversal(List<K> keyList, RBNode<K, V> startingNode)
	{
		if(startingNode == null)
		{
			//No code to execute
		}
		
		else
		{
			if(startingNode.left != null)
			{
				postOrderTraversal(keyList, startingNode.left);
			}
			
			if(startingNode.right != null)
			{
				postOrderTraversal(keyList, startingNode.right);
			}
			
			if(!startingNode.isDeleted)
			{
				keyList.add(startingNode.key);
			}
		}
	}
	
	//Returns list with nodes organized in order
	private void inOrderTraversal(List<K> keyList, RBNode<K, V> startingNode)
	{
		if(startingNode == null)
		{
			//No code to execute
		}
		
		else
		{
			if(startingNode.left != null)
			{
				inOrderTraversal(keyList, startingNode.left);
			}
			
			if(!startingNode.isDeleted)
			{
				keyList.add(startingNode.key);
			}
			
			if(startingNode.right != null)
			{
				inOrderTraversal(keyList, startingNode.right);
			}
				
			}
		}
	
	
	//Gets the height of the tree originating at the given node
	private int getHeight(RBNode<K, V> root)
	{
		if(root == null)
		{
			return 0;
		}
		else if(root.left == null && root.right == null)
		{
			return 1;
		}
		
		else if(root.left != null && root.right != null)
		{
			return 1 + Math.max(getHeight(root.left), getHeight(root.right));
		}
		
		else if(root.left != null)
		{
			return 1 + getHeight(root.left);
		}
		
		else
		{
			return 1 + getHeight(root.right);
		}
		
	}
	
	//Colors the grandparent red (unless it is the root) and its children black
	private RBNode<K, V> recolor(RBNode<K, V> grandparent)
	{
		grandparent.left.color = 'b';
		grandparent.right.color = 'b';
		if(root.key.compareTo(grandparent.key) != 0)
		{
			grandparent.color = 'r';
		}
		
		return grandparent;
	}
	
	//Restructures a group of three nodes, returning the root of the trinode restructure
	private RBNode<K, V> triNodeRestructure(RBNode<K, V> child, RBNode<K, V> parent, RBNode<K, V> grandparent)
	{
		if(child == null || parent == null || grandparent == null)
		{
			return grandparent;
		}
		
		if(grandparent.left != null && parent.key.compareTo(grandparent.left.key) == 0)
		{
			//If both parent and child are left nodes
			if(parent.left != null && child.key.compareTo(parent.left.key) == 0)
			{
				grandparent = rotateRight(grandparent);
			}
			
			else
			{
				grandparent.left = rotateLeft(parent);
				grandparent = rotateRight(grandparent);
			}
			
		}
		
		else if(grandparent.right != null && parent.key.compareTo(grandparent.right.key) == 0)
		{
			if(parent.left != null && child.key.compareTo(parent.left.key) == 0)
			{
				grandparent.right = rotateRight(parent);
				grandparent = rotateLeft(grandparent);
			}
			
			//If both parent and child are right nodes
			else
			{
				grandparent = rotateLeft(grandparent);
			}
		}
		
		else
		{
			return null;
		}
		
		grandparent.color = 'b';
		if(grandparent.left != null)
		{
			grandparent.left.color = 'r';
		}
		if(grandparent.right != null)
		{
			grandparent.right.color = 'r';
		}
		return grandparent;
	}
	
	
	//Checks if a parent and its child are both red
	private boolean redPropertyViolation(RBNode<K, V> parent, RBNode<K, V> child)
	{
		if(parent == null || child == null)
		{
			return false;
		}
		return parent.color == 'r' && child.color == 'r';
	}
	
	//Gets the balance factor of a node by subtracting the height of its left subtree from the height of its right
	private int getBalanceFactor(RBNode<K, V> node)
	{
		return getHeight(node.left) - getHeight(node.right); 
	}
	
	//Performs a right rotation at the given node
	private RBNode<K, V> rotateRight(RBNode<K, V> rotationNode)
	{
		RBNode<K, V> newNode = rotationNode.left;
		rotationNode.left = newNode.right;
		newNode.right = rotationNode;
		return newNode;
	}
	
	//Performs a left rotation at the given node
	private RBNode<K, V> rotateLeft(RBNode<K, V> rotationNode)
	{
		RBNode<K, V> newNode = rotationNode.right;
		rotationNode.right = newNode.left;
		newNode.left = rotationNode;
		return newNode;
	}
	
	//Returns the node associated with the specified key
	//Returns null if the key could not be found or is marked as deleted
	private RBNode<K, V> findNodeByKey(K key, RBNode<K, V> nodeToCompare)
	{
		if(key == null)
		{
			return null;
		}
		
		if(nodeToCompare == null)
		{
			return null;
		}
		
		else if(key.compareTo(nodeToCompare.key) == 0)
		{
			if(nodeToCompare.isDeleted)
			{
				return null;
			}
			return nodeToCompare;
		}
		
		else if(key.compareTo(nodeToCompare.key) < 0)
		{
			return findNodeByKey(key, nodeToCompare.left);
		}
		else
		{
			return findNodeByKey(key, nodeToCompare.right);
		}
	}
	
	//Inserts a new node
	private RBNode<K, V> insert(K key, V value, RBNode<K, V> nodeToCompare) throws DuplicateKeyException
	{
		if(nodeToCompare == null)
		{
			numKeys++;
			if(root == null)
			{
				return new RBNode(key, value, 'b', null, null);
			}
			else
			{
				return new RBNode(key, value, 'r', null, null);
			}
			
			
		}
		else if(key.compareTo(nodeToCompare.key) == 0)
		{
			throw new DuplicateKeyException();
		}
		
		else if(key.compareTo(nodeToCompare.key) < 0)
		{
			nodeToCompare.left = insert(key, value, nodeToCompare.left);
			if(redPropertyViolation(nodeToCompare.left, nodeToCompare.left.left))
			{
				if(nodeToCompare.right != null && nodeToCompare.right.color == 'r')
				{
					nodeToCompare = recolor(nodeToCompare);
				}
				else
				{
					nodeToCompare = triNodeRestructure(nodeToCompare.left.left, nodeToCompare.left, nodeToCompare);
				}
				
			}
			
			if(redPropertyViolation(nodeToCompare.left, nodeToCompare.left.right))
			{
				if(nodeToCompare.right != null && nodeToCompare.right.color == 'r')
				{
					nodeToCompare = recolor(nodeToCompare);
				}
				else
				{
					nodeToCompare = triNodeRestructure(nodeToCompare.left.right, nodeToCompare.left, nodeToCompare);
				}
			}
			
			return nodeToCompare;
			
		}
		else
		{
			nodeToCompare.right = insert(key, value, nodeToCompare.right);
			if(redPropertyViolation(nodeToCompare.right, nodeToCompare.right.left))
			{
				if(nodeToCompare.left != null && nodeToCompare.left.color == 'r')
				{
					nodeToCompare = recolor(nodeToCompare);
				}
				else
				{
					nodeToCompare = triNodeRestructure(nodeToCompare.right.left, nodeToCompare.right, nodeToCompare);
				}
			}
			
			else if(redPropertyViolation(nodeToCompare.right, nodeToCompare.right.right))
			{
				if(nodeToCompare.left != null && nodeToCompare.left.color == 'r')
				{
					nodeToCompare = recolor(nodeToCompare);
				}
				else
				{
					nodeToCompare = triNodeRestructure(nodeToCompare.right.right, nodeToCompare.right, nodeToCompare);
				}
				
			}
			
			return nodeToCompare;
			
		}
	}
	
	//Class the defines an RBNode
	private class RBNode<K extends Comparable<K>, V>
	{
		private K key; //The node's key
		private V value; //The node's value
		private char color; //The node's color
		private RBNode<K, V> left; //The node's left child
		private RBNode<K, V> right; //Right child
		private boolean isDeleted; //Whether or not the node has been marked deleted
		
		private RBNode(K key, V value, char color, RBNode<K, V> left, RBNode<K, V> right)
		{
			this.key = key;
			this.value = value;
			this.left = left;
			this.color = color;
			this.right = right;
			this.isDeleted = false;
		}
		
		private RBNode(K key, V value)
		{
			this.key = key;
			this.value = value;
			this.color = 'r';
			this.left = null;
			this.right = null;
			this.isDeleted = false;
		}
		
	}
}
	
	

