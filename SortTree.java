import java.util.Scanner;
//二叉排序树
class TreeNode{
    int date;
    TreeNode leftTree;
    TreeNode rightTree;
    TreeNode parentTree;
    public TreeNode(int date){
        this.date = date;
    }
}
public class SortTree {
    private static TreeNode root = null;
    //首先查找删除的结点是否存在
    private static void deleteBST(TreeNode root, int numDelete) {
        if (root == null) {
            System.out.println("没有该结点，删除失败!");

        } else if (root.date > numDelete) {
            deleteBST(root.leftTree, numDelete);
        } else if (root.date < numDelete) {
            deleteBST(root.rightTree, numDelete);
        } else {
            deleteTree(root, numDelete);
        }
    }
     //若存在则删除该结点
    private static void deleteTree(TreeNode root, int numDelete) {
    //为叶子结点直接删除
        if (root.leftTree == null && root.rightTree == null) {
            if(root.parentTree.rightTree == root){
                root.parentTree.rightTree = null;
            } else{
                root.parentTree.leftTree = null;
            }
            //有左右子树则找到右子树中最小的，或者左子树中最大的，我找的是右子树最小的
        } else if (root.rightTree != null && root.leftTree != null) {
            TreeNode preRootR = root.rightTree;
            while (preRootR.leftTree != null) {
                preRootR = preRootR.leftTree;
            }
            int t = root.date;
            root.date = preRootR.date;
            preRootR.date = t;
            deleteTree(preRootR,numDelete);
            //有一个子树不为空
        } else {
            //右子树不为空
            if (root.leftTree == null) {
            //判断该节点是右子树吗，因为要改双亲节点的孩子域
                if(root.parentTree.rightTree == root){
                    root.parentTree.rightTree = root.rightTree;
                }else{
                    root.parentTree.leftTree = root.rightTree;
                }
            //左子树不为空
             }else {
             //判断是不是左子树
                if(root.parentTree.leftTree == root){
                    root.parentTree.leftTree = root.leftTree;
                }else{
                    root.parentTree.rightTree = root.leftTree;
                }


            }
            
            root = null;
        }
    }
     //创建二叉排序树，同时也是插入操作
    private static TreeNode insertTree(TreeNode root,int date){
        if(root == null){
            root = new TreeNode(date);
            root.leftTree = null;
            root.rightTree = null;
        } else if(root.date > date){
            root.leftTree = insertTree(root.leftTree,date);
            root.leftTree.parentTree = root;
        } else if(root.date < date){
            root.rightTree= insertTree(root.rightTree,date);
            root.rightTree.parentTree  = root;
        }else{
            System.out.println("该值已存在不再插入！");
        }
        return root;
    }
    //中序遍历，有序的
    private static void Middle(TreeNode root){

        if(root != null){
            Middle(root.leftTree);

            if(root.date != 65535){
                System.out.print(root.date+ " ");
            }

            Middle(root.rightTree);
        }
    }
    public static void main(String[] args) {
        int[] TreeNode = new int[]{2,12,4,7,22,65,-1,0};
        for (int i : TreeNode) {
            root = insertTree(root,i);
        }
        Scanner scanner = new Scanner(System.in);
        int numInsert = scanner.nextInt();
        insertTree(root,numInsert);
        Middle(root);
        int numDelete = scanner.nextInt();
        deleteBST(root,numDelete);
        Middle(root);
    }
}
