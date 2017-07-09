package adt.bst;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import adt.bst.extended.SortComparatorBSTImpl;

public class MaisTeste {
	
	private SortComparatorBSTImpl<Integer> tree;
	
	@Before
	public void setUp() throws Exception {
		tree = new SortComparatorBSTImpl<>((o1, o2) -> o1 - o2);
	}

	@Test
	public void test() {
		tree.insert(72);
		tree.insert(20);
		tree.insert(88);
		tree.insert(12);
		tree.insert(38);
		tree.insert(69);
		tree.insert(9);		
		tree.insert(10);
		tree.insert(60);
		tree.insert(59);
		tree.insert(87);
		Integer[] array = new Integer[] {9, 10, 12, 20, 38, 59, 60, 69, 72, 87, 88};
		assertArrayEquals(array, tree.order());
		array = new Integer[] {88, 87, 72, 69, 60, 59, 38, 20, 12, 10, 9};
		assertArrayEquals(array, tree.reverseOrder());

	}

}
