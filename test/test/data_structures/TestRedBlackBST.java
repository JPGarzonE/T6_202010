package test.data_structures;

import static org.junit.Assert.*;

import org.junit.Assert;

import model.data_structures.RedBlackBST;
import model.data_structures.TreeNode;
import model.logic.Modelo;

import org.junit.Test;

public class TestRedBlackBST{


	@Test
	public void testPut() {
		
	}
	
	@Test
	public void testGet() {
		
	}

	@Test
	public void testSize() {
		RedBlackBST tree = new RedBlackBST<>();
		Assert.assertTrue(tree.size()!=0);
	}
	
	@Test
	public void testIsEmpty() {
		RedBlackBST tree = new RedBlackBST<>();
		Assert.assertTrue(tree.size()==0);
	}
}