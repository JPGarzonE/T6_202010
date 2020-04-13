package test.logic;

import org.junit.Assert;
import org.junit.Test;


import model.data_structures.RedBlackBST;
import model.data_structures.TreeNode;

public class TestModelo {
	
	
	@Test
	public void testMax() {
		RedBlackBST tree = new RedBlackBST<>();
		TreeNode node = new TreeNode<>("a", 1, false, 0);
		Assert.assertEquals(node.getKey(),tree.max());
	}
	@Test
	public void testMin() {
		RedBlackBST tree = new RedBlackBST<>();
		TreeNode node = new TreeNode<>("a", 1, false, 0);
		Assert.assertEquals(node.getKey(),tree.min());
	}
	
	@Test
	public void testSize() {
		RedBlackBST tree = new RedBlackBST<>();
		Assert.assertTrue(tree.size()!=0);
	}
	
}
