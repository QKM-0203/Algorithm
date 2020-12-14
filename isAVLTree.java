class TreeNode{
TreeNode left;
TreeNode right;
}
public class isAVLTree{
    //是否是平衡树
    public boolean isBalanced(TreeNode root) {
        return height(root) == -1?false:true;
    }
    public int height(TreeNode root){
        //叶子节点高度为0时;
        if(root == null){
            return 0;
        }else{
            //获取左子树的高度
            int leftHeight =  height(root.left);
            //如果左子树的高度为-1则直接返回一定不是AVLTree
            if(leftHeight == -1) return -1;
            //同上
            int rightHeight =  height(root.right);
            if(rightHeight == -1) return -1;
//如果左右子树的高度差小于等于1则返回该节点的高度，否则直接返回-1不是AVLTree
            return Math.abs(leftHeight-rightHeight)<=1?Math.max(leftHeight,rightHeight)+1:-1;
        }

    }

    public static void main(String[] args) {

    }

}
