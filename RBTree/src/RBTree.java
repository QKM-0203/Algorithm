package RBTree.src;

import AVLTree.src.AVLNode;

public class RBTree<AnyType  extends Comparable<? super AnyType>>{

    private final static String BLACK = "BLACK";
    private final static String RED = "RED";

    private RBNode<AnyType> root = null;


    /**
     *找到插入结点
     * @param key
     */
    public void insert(AnyType key){
        RBNode<AnyType> parent = null;
          //新增的节点都为红色
        RBNode<AnyType> node = new RBNode<AnyType>(RED,key);
        RBNode<AnyType> root = this.root;
          //找到待插入的位置的母亲
        while(root != null){
              parent = root;
              int cmp = root.key.compareTo(key);
              if(cmp > 0){
                  root = root.leftChild;
              }else if(cmp < 0){
                  root = root.rightChild;
              }else{//若key相同，更新key值
                  root.key = key;

              }
        }
          //设置母亲
        node.parent = parent;
          //如果母亲不为空，则设置在母亲的位置
        if(parent != null){
            if(key.compareTo(parent.key) > 0){
                parent.rightChild = node;
            } else{
                parent.leftChild = node;
            }
        } else{//母亲为空，第一个节点，更新根节点，更新颜色
            this.root = node;
            node.setColor(BLACK);
        }
         //判断满足红黑树的性质
        insertFix(node);
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
          //更新孩子和母亲
        setChildAndParent(rbNode,rChild);
        //最后在更新旋转节点的母亲，将旋转节点放在新的节点的左边
        rbNode.parent = rChild;
        rChild.leftChild = rbNode;
    }

    /**
     * 当母亲不为空时，更新母亲和孩子,更新根结点颜色
     * @param rbNode
     * @param rp
     */
    public void setChildAndParent(RBNode<AnyType> rbNode,RBNode<AnyType> rp){
        if(rbNode.parent != null) {
            rp.parent = rbNode.parent;
            if(rbNode == rbNode.parent.leftChild) {
                rbNode.parent.leftChild = rp;
            } else {
                rbNode.parent.rightChild = rp;
            }
        }else {//母亲为空，则将旋转之后的设为根节点
            this.root = rp;
            rp.parent = null;
            rp.setColor(BLACK);
        }
    }

    /**
     * 和左旋差不多
     * @param rbNode
     */
    public void rightRotation(RBNode<AnyType> rbNode){
        RBNode<AnyType> rp = rbNode.leftChild;
        rbNode.leftChild = rp.rightChild;
        if(rp.rightChild != null) {
           rp.rightChild.parent = rbNode;
        }
        setChildAndParent(rbNode,rp);
        rbNode.parent = rp;
        rp.rightChild = rbNode;

    }

    /**
     * 传入该节点，判断母亲的颜色
     * @param rbNode
     */
    private  void insertFix(RBNode<AnyType> rbNode) {
         this.root.setColor(BLACK);
         RBNode<AnyType> parent = rbNode.parent;
            //母亲不为空，同时母亲为红色
         if(parent != null && parent.getColor().equals(RED)){
                 //获取爷爷
             RBNode<AnyType> grandfather = rbNode.parent.parent;
               //母亲是左孩子
             if(parent == grandfather.leftChild){
                     //叔叔为空，或者黑色
                 if(grandfather.rightChild == null || grandfather.rightChild.getColor().equals(BLACK)){
                         //传入节点为母亲的左孩子，都为坐右旋传入爷爷
                     if(rbNode == parent.leftChild){
                         grandfather.setColor(RED);
                         parent.setColor(BLACK);
                         rightRotation(grandfather);
                     } else { //为母亲的右孩子，左旋 传入母亲
                         leftRotation(parent);
                            //将两个红色放在一条线上继续向上在判断，将母亲看成是新插入的节点，继续判断
                         insertFix(parent);
                     }
                 } else { //叔叔存在，同时为黑色，设置爷爷为红色，母亲和叔叔为黑色，将爷爷看成新插入的节点继续判断
                     grandfather.setColor(RED);
                     grandfather.rightChild.setColor(BLACK);
                     parent.setColor(BLACK);
                     insertFix(grandfather);
                 }
             } else{//母亲是右孩子，道理同上，在同一条线上旋转传入爷爷
                 if(grandfather.leftChild == null || grandfather.leftChild.getColor().equals(BLACK)){
                     if(rbNode == parent.rightChild){
                         grandfather.setColor(RED);
                         parent.setColor(BLACK);
                         leftRotation(grandfather);
                     } else{
                         rightRotation(parent);
                         insertFix(parent);
                     }
                 } else{
                     grandfather.setColor(RED);
                     grandfather.leftChild.setColor(BLACK);
                     parent.setColor(BLACK);
                     insertFix(grandfather);
                 }
             }
         }
    }


    /**
     * 删除操作
     * @param key
     */
    public void delete(RBNode<AnyType> root,AnyType key) {
        RBNode<AnyType> root1 = root;
        while (root1 != null) {
            int cmp = key.compareTo(root1.key);
            if (cmp > 0) {
                root1 = root1.rightChild;

            } else if (cmp < 0) {
                root1 = root1.leftChild;

            } else {
                if (root1.leftChild == null && root1.rightChild == null) {
                    //叶子为红
                    if (root1.getColor().equals(RED)) {
                        if (root1.parent != null) {//可能删除
                            if (root1.parent.leftChild == root1) {
                                root1.parent.leftChild = null;
                            } else {
                                root1.parent.rightChild = null;
                            }

                        } else {
                            this.root = null;
                        }
                    } else {
                        deleteFix(root1,root1.key);
                    }
                } else if (root1.leftChild != null && root1.rightChild != null) { //待删除双儿子
                    RBNode<AnyType> replaceNode = this.findMin(root1.rightChild);
                    root1.key = replaceNode.key;
                    delete(root1.rightChild,root1.key);
                } else {
                    if(root1.leftChild != null) {
                        root1.key = root1.leftChild.key;
                        delete(root1.leftChild,root1.key);
                    } else {
                        root1.key = root1.rightChild.key;
                        delete(root1.rightChild,root1.key);
                    }
                }
                return ;
            }
            if (root1 == null) {
                System.out.println("没有该节点,删除失败");
                break;
            }
        }
    }

    /**
     * 平衡修复
     * @param rbNode
     * @param Key
     */
        public void deleteFix (RBNode < AnyType > rbNode, AnyType Key){
            if (rbNode.parent == null) {
                rbNode.setColor(BLACK);
                return;
            }
            String s = null;
            if (rbNode == rbNode.parent.leftChild) {
                s = "0";
            } else {
                s = "1";
            }
            if (s.equals("0")) { //待删节点在左边
                RBNode<AnyType> brother = rbNode.parent.rightChild;
                   //兄弟为红色
                if (brother.getColor().equals(RED)) {
                    rbNode.parent.setColor(RED);
                    brother.setColor(BLACK);
                    leftRotation(rbNode.parent);
                    deleteFix(rbNode, Key);
                } else { //兄弟为黑色
                    //兄弟的两个孩子为空或者都是黑色
                    if ((brother.leftChild == null && brother.rightChild == null) || (brother.leftChild != null && brother.rightChild != null
                            && brother.leftChild.getColor().equals(BLACK
                    ) && brother.rightChild.getColor().equals(BLACK))) {
                        brother.setColor(RED);
                        //兄弟的两个孩子为空或者都是黑色，父亲为红色
                        if (brother.parent.getColor().equals(RED)) {
                            brother.parent.setColor(BLACK);
                            brother.setColor(RED);
                            //针对向上调整不再删除的情况，下面的同这一条
                            if(rbNode.key == Key){
                                rbNode.parent.leftChild = null;
                            }

                        } else { //兄弟的两个孩子为空或者都是黑色，父亲为黑色
                            if(rbNode.key == Key) {
                                rbNode.parent.leftChild = null;
                            }
                            brother.setColor(RED);
                            deleteFix(brother.parent, Key);
                        }
                        //远侄子为红色
                    } else if (brother.rightChild != null && brother.rightChild.getColor().equals(RED)) {
                        String color = brother.parent.getColor();
                        brother.parent.setColor(BLACK);
                        brother.setColor(color);
                        leftRotation(brother.parent);
                        brother.rightChild.setColor(BLACK);
                        if(rbNode.key == Key) {
                            rbNode.parent.leftChild = null;
                        }
                        //近侄子为红色
                    } else if (brother.leftChild != null && brother.leftChild.getColor().equals(RED)) {
                        String color = brother.leftChild.getColor();
                        brother.leftChild.setColor(brother.getColor());
                        brother.setColor(color);
                        rightRotation(brother);
                        deleteFix(rbNode, Key);
                    }
                }
            } else { //待删结点在右边
                RBNode<AnyType> brother = rbNode.parent.leftChild;
                //兄弟为红色
                if (brother.getColor().equals(RED)) {
                    rbNode.parent.setColor(RED);
                    brother.setColor(BLACK);
                    rightRotation(rbNode.parent);
                    deleteFix(rbNode, Key);
                } else { //兄弟为黑色
                       //兄弟为黑色，两个侄子都是黑或者都是空
                    if ((brother.leftChild == null && brother.rightChild == null) || (brother.leftChild != null && brother.rightChild != null
                            && brother.leftChild.getColor().equals(BLACK
                    ) && brother.rightChild.getColor().equals(BLACK))) {
                        //兄弟为黑色，两个侄子都是黑或者都是空，母亲为红色
                        if (rbNode.parent.getColor().equals(RED)) {
                            rbNode.parent.setColor(BLACK);
                            brother.setColor(RED);

                            if(rbNode.key == Key) {
                                rbNode.parent.rightChild = null;
                            }
                        } else { //兄弟为黑色，两个侄子都是黑或者都是空，母亲为黑色
                            if(rbNode.key == Key) {
                                rbNode.parent.rightChild = null;
                            }
                            brother.setColor(RED);
                            deleteFix(brother.parent, Key);
                        }
                        //远侄子为红色
                    } else if (brother.leftChild != null && brother.leftChild.getColor().equals(RED)) {
                        String color = brother.parent.getColor();
                        brother.parent.setColor(BLACK);
                        brother.setColor(color);
                        rightRotation(brother.parent);
                        brother.leftChild.setColor(BLACK);
                        rbNode.parent.rightChild = null;
                        //近侄子为红色
                    } else if (brother.rightChild != null && brother.rightChild.getColor().equals(RED)) {
                        String color = brother.rightChild.getColor();
                        brother.rightChild.setColor(brother.getColor());
                        brother.setColor(color);
                        leftRotation(brother);
                        deleteFix(rbNode, Key);
                    }
                }
            }
        }



    /**
     * 找最小结点
     * @param root
     * @return
     */
    private RBNode<AnyType> findMin(RBNode<AnyType> root){
        while(root.leftChild != null) {
            root = root.leftChild;
        }
        return root;
    }

    /**
     * 中序打印
     * @param root
     */
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
        integerRBTree.insert(19);
        integerRBTree.insert(4);
        integerRBTree.insert(15);
        integerRBTree.insert(18);
        integerRBTree.insert(5);
        integerRBTree.insert(14);
        integerRBTree.insert(13);
        integerRBTree.insert(10);
        integerRBTree.insert(16);
        integerRBTree.insert(6);
        integerRBTree.insert(3);
        integerRBTree.insert(8);
        integerRBTree.insert(17);
        integerRBTree.Middle(integerRBTree.root);
        System.out.println();
        integerRBTree.delete(integerRBTree.root, 12);
        integerRBTree.delete(integerRBTree.root,1);
        integerRBTree.delete(integerRBTree.root,9);
        integerRBTree.delete(integerRBTree.root,2);
        integerRBTree.delete(integerRBTree.root,0);
        integerRBTree.delete(integerRBTree.root,11);
        integerRBTree.delete(integerRBTree.root,7);
        integerRBTree.delete(integerRBTree.root,19);
        integerRBTree.delete(integerRBTree.root,4);
        integerRBTree.delete(integerRBTree.root,15);
        integerRBTree.delete(integerRBTree.root,18);
        integerRBTree.delete(integerRBTree.root,5);
        integerRBTree.delete(integerRBTree.root,14);
        integerRBTree.delete(integerRBTree.root,13);
        integerRBTree.delete(integerRBTree.root,10);
        integerRBTree.delete(integerRBTree.root,16);
        integerRBTree.delete(integerRBTree.root,6);
        integerRBTree.delete(integerRBTree.root,3);
        integerRBTree.delete(integerRBTree.root,8);
        integerRBTree.Middle(integerRBTree.root);
    }
}
