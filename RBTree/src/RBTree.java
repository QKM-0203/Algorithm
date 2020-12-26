package RBTree.src;

public class RBTree<AnyType  extends Comparable<? super AnyType>>{

    private final static String BLACK = "BLACK";
    private final static String RED = "RED";

    private RBNode<AnyType> root = null;

    /**
     *
     * @param key
     */
    public void insert(AnyType key){
        RBNode<AnyType> parent = null;
        RBNode<AnyType> node = new RBNode<AnyType>(RED,key);
        RBNode<AnyType> root = this.root;
        while(root != null){
              parent = root;
              int cmp = root.key.compareTo(key);
              if(cmp > 0){
                  root = root.leftChild;
              }else if(cmp < 0){
                  root = root.rightChild;
              }else{
                  root.key = key;
                  return;
              }
        }
        node.parent = parent;
        if(parent != null){
            if(key.compareTo(parent.key) > 0){
                parent.rightChild = node;
            } else{
                parent.leftChild = node;
            }
        } else{
            this.root = node;
            this.root.setColor(BLACK);
        }
        isBalanced(node);
        return;
    }

    /**
     * 左旋
     * 首先获取右孩子，将右孩子的左孩子放到旋转节点的右边
     * 在判断右孩子的左孩子为空吗，不为空设置母亲，在判断旋转节点有没有母亲没有母亲更新根节点，有母亲要设置母亲和儿子
     * 最后在更新旋转节点的母亲，将旋转节点放在新的节点的左边
     * @param rbNode
     * @return
     */
    public void leftRotation(RBNode<AnyType> rbNode){
        //首先获取右孩子，将右孩子的左孩子放到旋转节点的右边
        RBNode<AnyType> rChild = rbNode.rightChild;
        rbNode.rightChild = rChild.leftChild;

        //在判断右孩子的左孩子为空吗，不为空设置母亲，在判断旋转节点有没有母亲没有母亲更新根节点，有母亲要设置母亲和儿子
        if(rChild.leftChild != null) {
            rChild.leftChild.parent = rbNode;
        }
        if(rbNode.parent != null) {
            rChild.parent = rbNode.parent;
            if(rbNode == rbNode.parent.leftChild) {
                rbNode.parent.leftChild = rChild;
            } else {
                rbNode.parent.rightChild = rChild;
            }
        }else {
            this.root = rChild;
            //新更节点的母亲设置为空
            this.root.parent = null;
        }
        //最后在更新旋转节点的母亲，将旋转节点放在新的节点的左边
        rbNode.parent = rChild;
        rChild.leftChild = rbNode;
    }
    public void rightRotation(RBNode<AnyType> rbNode){
        RBNode<AnyType> rp = rbNode.leftChild;
        rbNode.leftChild = rp.rightChild;
        if(rp.rightChild != null) {
           rp.rightChild.parent = rbNode;
        }
        if(rbNode.parent != null) {
            rp.parent = rbNode.parent;
            if(rbNode == rbNode.parent.leftChild) {
                rbNode.parent.leftChild = rp;
            } else {
                rbNode.parent.rightChild = rp;
            }
        }else {
            this.root = rp;
            this.root.parent = null;
        }
        rbNode.parent = rp;
        rp.rightChild = rbNode;

    }
    private  void isBalanced(RBNode<AnyType> rbNode) {
         this.root.setColor(BLACK);
         RBNode<AnyType> parent = rbNode.parent;
         if(parent != null && parent.getColor().equals(RED)){
             RBNode<AnyType> grandfather = rbNode.parent.parent;
               //母亲是左孩子
             if(parent == grandfather.leftChild){
                 if(grandfather.rightChild == null || grandfather.rightChild.getColor().equals(BLACK)){
                     if(rbNode == parent.leftChild){
                         grandfather.setColor(RED);
                         parent.setColor(BLACK);
                         rightRotation(grandfather);
                     }
                     if(parent.rightChild == rbNode){
                         leftRotation(parent);
                         isBalanced(parent);
                     }
                 } else {
                     grandfather.setColor(RED);
                     grandfather.rightChild.setColor(BLACK);
                     parent.setColor(BLACK);
                     isBalanced(grandfather);
                     //return ;
                 }
             } else{//母亲是右孩子
                 if(grandfather.leftChild == null || grandfather.leftChild.getColor().equals(BLACK)){
                     if(rbNode == parent.rightChild){
                         grandfather.setColor(RED);
                         parent.setColor(BLACK);
                         leftRotation(grandfather);
                     }
                     if(parent.leftChild == rbNode){
                         rightRotation(parent);
                         isBalanced(parent);
                     }
                 } else{
                     grandfather.setColor(RED);
                     grandfather.leftChild.setColor(BLACK);
                     parent.setColor(BLACK);
                     isBalanced(grandfather);
                     //return ;
                 }
             }
         }
    }
    private  void Middle(RBNode<AnyType> root){

        if(root != null){

            Middle(root.leftChild);
            System.out.println(root.key + root.color);
            Middle(root.rightChild);
        }
    }

    public static void main(String[] args) {
        RBTree<Integer> integerRBTree = new RBTree<>();
        integerRBTree.insert(12);
        integerRBTree.insert(1);

        integerRBTree.insert(9);

       integerRBTree.insert(2);
       integerRBTree.insert(0);
       integerRBTree.insert(11);
       integerRBTree.insert(7);
        integerRBTree.Middle(integerRBTree.root);
    }
}
