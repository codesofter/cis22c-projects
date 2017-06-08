//  THIS IS WHAT I CALLED Right Rotation at k2
protected AVL_Node<E> rotateWithLeftChild( 
   AVL_Node<E> k2 )
{
   AVL_Node<E> k1 = k2.getLeftChild();
   k2.setLeftChild(k1.getRightChild());
   k1.setRightChild(k2);
   k2.setHeight( Math.max( heightOf(k2.getLeftChild()),  heightOf(k2.getRightChild()) ) + 1 );
   k1.setHeight( Math.max( heightOf(k1.getLeftChild()),  k2.getHeight() ) + 1 );
   return k1;
}

//  What I called Left Rotation at k2
protected AVL_Node<E> rotateWithRightChild( 
   AVL_Node<E> k2 )
{
   AVL_Node<E> k1 = k2.getRightChild());
   k2.setRightChild(k1.getLeftChild());
   k1.setLeftChild(k2);
   k2.setHeight( Math.max( heightOf(k2.getLeftChild()),  heightOf(k2.getRightChild()) ) + 1 );
   k1.setHeight( Math.max( heightOf(k1.getRightChild()),  k2.getHeight() ) + 1 );
   return k1;
}

/*
      leftRightRotate (left rotate then right rotate at k3)
*/
protected AVL_Node<E> doubleWithLeftChild(AVL_Node<E> k3){
   /* do a single "left rotation" passing k3's left child, then return the result of calling rotateWithLeftChild passing the parameter, remembering that the rotateWithRightChild does a "left rotation" and vice versa */
   AVL_Node<E> kL = k3.getLeftChild();
   AVL_Node<E> rotationResult;

   // left rotation at k3
   rotationResult = rotateWithRightChild(kL);

   // right rotation at k3
   rotationResult = rotateWithLeftChild(rotationResult);

   return rotationResult;
}

/*
      rightLeftRotate (right rotate then left rotate at k3)
*/
protected AVL_Node<E> doubleWithRightChild(AVL_Node<E> k3){
   /* do a single "right rotation" passing k3's right child, then return the result of calling rotateWithRightChild passing the parameter, remembering that the rotateWithLeftChild does a "right rotation" and vice versa*/
   AVL_Node<E> kR = k3.getRightChild();
   AVL_Node<E> rotationResult;

   // right rotation at k3
   rotationResult = rotateWithLeftChild(kR);

   // left rotation at k3
   rotationResult = rotateWithRightChild(rotationResult);

   return rotationResult;
}