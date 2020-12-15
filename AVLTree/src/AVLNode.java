package cn.AVL;

/**
 * AVL结点类
 * @param <AnyType>
 */

public class AVLNode<AnyType> {
        AnyType element;
        AVLNode<AnyType> left;
        AVLNode<AnyType> right;
        int height;

        AVLNode(AnyType element){
            this(element,null,null);
        }
        AVLNode(AnyType element,AVLNode<AnyType> left,AVLNode<AnyType> right){
            this.element = element;
            this.left = left;
            this.right = right;
            height = 0;
        }
}
