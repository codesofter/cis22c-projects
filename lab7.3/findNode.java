private BinaryNode<E> _findNode( BinaryNode<E> node, E targetData ){
	if ( node == null ) {
		return null;
	} else {
		if ( e.compareTo(node.getData()) < 0 ){
			node = _findNode(node.getLeftChild(), e);
		} else if ( e.compareTo(node.getData()) > 0 ){
			node = _findNode(node.getRightChild(), e);
		}
		return node;
	}
}