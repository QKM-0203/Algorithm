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

    private static void deleteTree(TreeNode root, int numDelete) {
        if (root.leftTree == null && root.rightTree == null) {
            if(root.parentTree.rightTree == root){
                root.parentTree.rightTree = null;
            } else{
                root.parentTree.leftTree = null;
            }
        } else if (root.rightTree != null && root.leftTree != null) {
            TreeNode preRootR = root.rightTree;
            while (preRootR.leftTree != null) {
                preRootR = preRootR.leftTree;
            }
            int t = root.date;
            root.date = preRootR.date;
            preRootR.date = t;
            deleteTree(preRootR,numDelete);
        } else {
            if (root.leftTree == null) {
                if(root.parentTree.rightTree == root){
                    root.parentTree.rightTree = root.rightTree;
                }else{
                    root.parentTree.leftTree = root.rightTree;
                }

            } else {
                if(root.parentTree.leftTree == root){
                    root.parentTree.leftTree = root.leftTree;
                }else{
                    root.parentTree.rightTree = root.leftTree;
                }


            }
            root = null;
        }
    }

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
