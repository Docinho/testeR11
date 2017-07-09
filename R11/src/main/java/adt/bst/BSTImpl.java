package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(BSTNode<T> node) {
		int retorno = -1;
		if (!node.isEmpty())
			retorno = 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));

		return retorno;
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(element, this.root);
	}

	private BSTNode<T> search(T element, BSTNode<T> node) {
		if (node == null)
			return new BSTNode<T>();
		if (node.isEmpty() || node.getData().equals(element))
			return node;
		if (node.getData().compareTo(element) < 0)
			return search(element, (BSTNode<T>) node.getRight());
		return search(element, (BSTNode<T>) node.getLeft());
	}

	@Override
	public void insert(T element) {
		if (element != null)
			insert(element, this.root);
	}

	private void insert(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (node.getData().compareTo(element) < 0) {
				insert(element, (BSTNode<T>) node.getRight());
			} else if (node.getData().compareTo(element) > 0) {
				insert(element, (BSTNode<T>) node.getLeft());
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(this.root);
	}

	protected BSTNode<T> maximum(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return null;
		} else if (node.getRight().isEmpty()) {
			return node;
		} else {
			return maximum((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(this.root);
	}

	protected BSTNode<T> minimum(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return null;
		} else if (node.getLeft().isEmpty()) {
			return node;
		} else {
			return minimum((BSTNode<T>) node.getLeft());
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
			while (parent != null && !parent.isEmpty() && parent.getData().compareTo(node.getData()) < 0) {
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
			while (parent != null && !parent.isEmpty() && parent.getData().compareTo(node.getData()) > 0) {
				parent = (BSTNode<T>) parent.getParent();
			}
			return parent;
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> node = search(element);
			if (!node.isEmpty())
				remove(node);
		}
	}

	private void remove(BSTNode<T> node) {
		if (this.root.getData().equals(node.getData())) {

			BSTNode<T> auxNode = sucessor(node.getData());
			if (auxNode == null)
				auxNode = predecessor(node.getData());

			if (auxNode == null) {
				node.setData(null);
			} else {
				T auxData = (T) node.getData();
				node.setData(auxNode.getData());
				auxNode.setData(auxData);
				remove(auxNode);
			}

		} else {
			if (node.isLeaf())
				node.setData(null);
			else if (node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
				node.getRight().setParent(node.getParent());
				if (node.getData().equals(node.getParent().getRight().getData()))
					node.getParent().setRight(node.getRight());
				else
					node.getParent().setLeft(node.getRight());
			} else if (!node.getLeft().isEmpty() && node.getRight().isEmpty()) {
				node.getLeft().setParent(node.getParent());
				if (node.getParent().getLeft().getData().equals(node.getData()))
					node.getParent().setLeft(node.getLeft());
				else
					node.getParent().setRight(node.getLeft());
			} else {
				BSTNode<T> sucessorNode = sucessor((T) node);
				T auxData = (T) node.getData();
				node.setData(sucessorNode.getData());
				sucessorNode.setData(auxData);
				remove(sucessorNode);
			}
		}
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		preOrder(getRoot(), array, 0);
		return array;
	}

	private int preOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			array[index++] = node.getData();
			index = preOrder((BSTNode<T>) node.getLeft(), array, index);
			index = preOrder((BSTNode<T>) node.getRight(), array, index);
		}
		return index;
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[this.size()];
		order(getRoot(), array, 0);
		return array;
	}

	private int order(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			index = order((BSTNode<T>) node.getLeft(), array, index);
			array[index++] = node.getData();
			index = order((BSTNode<T>) node.getRight(), array, index);
		}
		return index;
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		postOrder(getRoot(), array, 0);
		return array;
	}

	private int postOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			index = postOrder((BSTNode<T>) node.getLeft(), array, index);
			index = postOrder((BSTNode<T>) node.getRight(), array, index);
			array[index++] = node.getData();
		}
		return index;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}
}