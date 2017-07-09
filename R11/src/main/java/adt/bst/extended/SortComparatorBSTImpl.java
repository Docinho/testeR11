package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em
 * suas funcionalidades e possui um metodo de ordenar um array dado como
 * parametro, retornando o resultado do percurso desejado que produz o array
 * ordenado.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;

	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		while (!this.root.isEmpty()) {
			this.remove(this.root.getData());
		}

		for (int i = 0; i < array.length; i++) {
			this.insert(array[i]);
		}

		return this.order();
	}

	@Override
	public T[] reverseOrder() {
		T[] array = (T[]) new Comparable[size()];
		reverseOrder(array, root, 0);
		return array;
	}

	private int reverseOrder(T[] array, BSTNode<T> node, int index) {
		if (node.getRight() != null && !node.getRight().isEmpty()) {
			index = reverseOrder(array, (BSTNode<T>) node.getRight(), index);
			array[index++] = node.getData();
		} else {
			array[index++] = node.getData();
		}
		if (node.getLeft() != null && !node.getLeft().isEmpty())
			index = reverseOrder(array, (BSTNode<T>) node.getLeft(), index);

		return index;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(element, root);
	}

	public BSTNode<T> search(T element, BSTNode<T> node) {
		if (node == null)
			return new BSTNode<T>();
		if (node.isEmpty() || getComparator().compare(node.getData(), element) == 0)
			return node;
		if (getComparator().compare(node.getData(), element) < 0)
			return search(element, (BSTNode<T>) node.getRight());
		return search(element, (BSTNode<T>) node.getLeft());
	}

	@Override
	public void insert(T element) {
		if (element != null)
			insert(element, root);
	}

	private void insert(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (getComparator().compare(node.getData(), element) < 0) {
				insert(element, (BSTNode<T>) node.getRight());
			} else if (getComparator().compare(node.getData(), element) > 0) {
				insert(element, (BSTNode<T>) node.getLeft());
			}
		}

	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);
		if (node == null || node.isEmpty()) {
			return null;
		}
		if (!node.getRight().isEmpty()) {
			return minimum((BSTNode<T>) node.getRight());
		} else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			while (parent != null && !parent.isEmpty()
					&& getComparator().compare(parent.getData(), node.getData()) < 0) {
				parent = (BSTNode<T>) parent.getParent();
			}
			return parent;
		}

	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = search(element);
		if (node == null || node.isEmpty()) {
			return null;
		}

		if (!node.getLeft().isEmpty()) {
			return maximum((BSTNode<T>) node.getLeft());
		} else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			while (parent != null && !parent.isEmpty()
					&& getComparator().compare(parent.getData(), node.getData()) > 0) {
				parent = (BSTNode<T>) parent.getParent();
			}
			return parent;
		}
	}
}
