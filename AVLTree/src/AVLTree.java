package AVLTree.src;



public class AVLTree<AnyType  extends Comparable<? super AnyType>>{

   /**
    * 定义的高度差最大值
    */

   private static final int MAX_HEIGHT_DIFFERENCE = 1;

   /**
    * 根结点
    */

   private AVLNode<AnyType> root = null;


   /**
    * LL型
    * @param avlNode
    * @return
    */

   private AVLNode<AnyType> leftLeftRotation(AVLNode<AnyType> avlNode ){
         AVLNode<AnyType> left = avlNode.left;
         avlNode.left = left.right;
         left.right = avlNode;


      avlNode.height = Math.max(height(avlNode.left),height(avlNode.right)) + 1;
      left.height = Math.max(avlNode.height,height(left.left)) + 1;
      return left;
   }




   /**
    * RR型
    * @param avlNode
    * @return
    */

   private AVLNode<AnyType> rightRightRotation(AVLNode<AnyType> avlNode ) {
      AVLNode<AnyType> right = avlNode.right;
      avlNode.right = right.left;
      right.left = avlNode;

      avlNode.height = Math.max(height(avlNode.left), height(avlNode.right)) + 1;
      right.height = Math.max(avlNode.height, height(right.right)) + 1;
      return right;
   }




   /**
    * RL型   ---  先LL在RR
    * @param avlNode
    * @return
    */

   private AVLNode<AnyType> rightLeftRotation(AVLNode<AnyType> avlNode ){
      avlNode.right =  leftLeftRotation(avlNode.right);
      return rightRightRotation(avlNode);
   }



   /**
    * LR型   ---  在RR在LL
    * @param avlNode
    * @return
    */

   private AVLNode<AnyType> leftRightRotation(AVLNode<AnyType> avlNode ){
      avlNode.left = rightRightRotation(avlNode.left);
      return leftLeftRotation(avlNode);
   }



   /**
    * 插入结点的操作,插入一个节点就要判断
    * @param avlNode
    * @return
    */

   private AVLNode<AnyType> insertNode(AVLNode<AnyType> avlNode,AnyType key){
      if (avlNode == null){
         avlNode = new AVLNode<AnyType>(key,null,null);
      } else{
         int cmpResult = key.compareTo(avlNode.element);
         if (cmpResult > 0){
            avlNode.right = insertNode(avlNode.right,key);
         } else if(cmpResult < 0){
            avlNode.left = insertNode(avlNode.left,key);
         } else{
            System.out.println("该结点已存在，不再添加！");
         }
      }
      avlNode.height = Math.max(height(avlNode.left),height(avlNode.right)) + 1;
      //每次插入一个节点判断所有节点是否平衡
      return isBalanced(avlNode);
   }


   /**
    * 删除节点
    * @param avlNode
    * @param key
    * @return
    */
   private AVLNode<AnyType> deleteNode(AVLNode<AnyType> avlNode,AnyType key){
      //删除叶子时，直接返回空
      if(avlNode == null){
         return null;
      }
         if(key.compareTo(avlNode.element) > 0){
           avlNode.right = deleteNode(avlNode.right,key);
         } else if(key.compareTo(avlNode.element) < 0){
            avlNode.left = deleteNode(avlNode.left,key);
         } else if (avlNode.left != null && avlNode.right != null){
            //把右子树的最小的赋值给当前结点的数据域
              avlNode.element = findMin(avlNode.right).element;
              //以当前结点的右子树为根结点来删除右子树中最小的
              avlNode.right = deleteNode(avlNode.right,avlNode.element);
         } else {
              avlNode = avlNode.left != null ? avlNode.left : avlNode.right;
         }
         return isBalanced(avlNode);
      }


   /**
    * 判断是否平衡，不平衡进行旋转，返回平衡后的结点
    * @param avlNode
    * @return
    */
   private AVLNode<AnyType> isBalanced(AVLNode<AnyType> avlNode){
      if(avlNode == null){
         return null;
      }
      //右子树高
      if (height(avlNode.right) - height(avlNode.left) >  MAX_HEIGHT_DIFFERENCE ){
         //结点右边的右边的高度比右边左边的高度高，则为RR型
         if (height(avlNode.right.right) >= height(avlNode.right.left)) {

            avlNode = rightRightRotation(avlNode);

         } else {
            //反之，则为RL型
            avlNode = rightLeftRotation(avlNode);
         }
         //左子树高
      } else if(height(avlNode.left) - height(avlNode.right) >  MAX_HEIGHT_DIFFERENCE ){
         //结点左边的左边的高度比左边右边的高度高，则为LL情况
         if (height(avlNode.left.left) >= height(avlNode.left.right)){

            avlNode = leftLeftRotation(avlNode);
         } else{
            //在左子树的右边，LR情况
            avlNode = leftRightRotation(avlNode);//在左子树的右边，lr情况
         }

      }
      return avlNode;
   }


   /**
    * 查找该结点是否存在
    * @param avlNode
    * @param key
    * @return
    */
   private AVLNode<AnyType> isFind(AVLNode<AnyType> avlNode,AnyType key){
      if(avlNode == null){
         System.out.println("没有找到该结点，删除失败！");
         return null;
      }
      if(key.compareTo(avlNode.element) > 0){
         avlNode.left = isFind(avlNode.right,key);
      } else if(key.compareTo(avlNode.element) < 0){
         avlNode.right = isFind(avlNode.left,key);
      } else if (key == avlNode.element){
        avlNode =  deleteNode(this.root,key);
      }
      return avlNode;
   }


   /**
    * 找最小结点
    * @param root
    * @return
    */

   private AVLNode<AnyType> findMin(AVLNode<AnyType> root) {
       while(root.left != null) {
          root = root.left;
       }
       return root;
   }



   /**
    * 找最大结点
    * @param root
    * @return
    */

   private AVLNode<AnyType> findMax(AVLNode<AnyType> root) {
      while(root.right != null) {
         root = root.right;
      }
      return root;
   }



   /**
    * 每插入一个结点更新一次根结点
    * @param key
    */
   private void insert(AnyType key){
      this.root = insertNode(this.root,key);
   }


   /**
    * 每删除一个结点更新一次根结点
    * @param key
    */
   private void delete(AnyType key){
      this.root = isFind(this.root,key);
   }


   /**
    * 计算高度的，每次插入时就直接更新所有的节点高度、
    * 如果是叶子节点就返回-1
    * @param avlNode
    * @return
    */
   private int height(AVLNode<AnyType> avlNode){
      if(avlNode != null){
         return avlNode.height;
      }
      return -1;
   }

   /**
    * 中序遍历输出
    * @param root
    */
   private  void Middle(AVLNode<AnyType> root){

      if(root != null){

         Middle(root.left);
         AnyType element = root.element;
         Student s =(Student)element;
         System.out.println(s.age);
         Middle(root.right);
      }
   }
   public static void main(String[] args) {
      AVLTree<Student> integerAVLTree = new AVLTree<>();
      integerAVLTree.insert(new Student(79,"qkm"));
      integerAVLTree.insert(new Student(8,"qkm"));
      integerAVLTree.insert(new Student(54,"qkm"));
      integerAVLTree.Middle(integerAVLTree.root);

   }
}
