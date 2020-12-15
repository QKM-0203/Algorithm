import java.util.Scanner;
//二叉排序树
public class BSTTree<AnyType>{
        private static class TreeNode<AnyType>{
        int date;
        TreeNode<AnyType> leftTree;
        TreeNode<AnyType> rightTree;
        public TreeNode(int date){
            this(date,null,null);
        }
        public TreeNode(int date, TreeNode<AnyType> left , TreeNode<AnyType> rightTree){
            this.date = date;
            this.leftTree = null;
            this.rightTree = null;
        }
    }
    private   TreeNode<AnyType> root = null;

    private  TreeNode<AnyType> deleteBST(TreeNode<AnyType> root, int numDelete) {
        if (root == null) {
            System.out.println("没有该结点，删除失败!");
            return null;

        } else if (root.date > numDelete) {
            root.leftTree = deleteBST(root.leftTree, numDelete);
        } else if (root.date < numDelete) {
            root.rightTree = deleteBST(root.rightTree, numDelete);
        } else {
            root = deleteTree(root, numDelete);
        }
        return root;
    }

    private  TreeNode<AnyType> deleteTree(TreeNode<AnyType> root, int numDelete) {
        if(root == null){
            return null;
        }
     if (root.rightTree != null && root.leftTree != null) {
            TreeNode<AnyType> preRootR = root.rightTree;
            //找到右子树中最小的
            while (preRootR.leftTree != null) {
                preRootR = preRootR.leftTree;
            }
            //找到之后赋值
            root.date = preRootR.date;
            //删除掉右子树中最小的数
            root.rightTree = deleteTree(root.rightTree,root.date);
        } else {
           root = root.rightTree != null ? root.rightTree : root.leftTree;
        }
        return root;
    }

    private  TreeNode<AnyType> insertTree(TreeNode<AnyType> root,int date){
        if(root == null){

            root = new TreeNode<AnyType>(date);
        } else if(root.date > date){

            root.leftTree = insertTree(root.leftTree,date);

        } else if(root.date < date){

            root.rightTree= insertTree(root.rightTree,date);
        }else{

            System.out.println("该值已存在不再插入！");
        }
        return root;
    }
    private void Middle(TreeNode<AnyType> root){

        if(root != null){

            Middle(root.leftTree);


            System.out.print(root.date+ " ");


            Middle(root.rightTree);
        }
    }
    public static void main(String[] args) {
        BSTTree<Integer> integerBSTTree = new BSTTree<>();
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,1);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,4);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,13);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,-4);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,0);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,33);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,22);
        integerBSTTree.root = integerBSTTree.insertTree(integerBSTTree.root,11);
        Scanner scanner = new Scanner(System.in);
        int numInsert = scanner.nextInt();
        integerBSTTree.insertTree(integerBSTTree.root,numInsert);
        integerBSTTree.Middle(integerBSTTree.root);
        int numDelete = scanner.nextInt();
        //更新root
        integerBSTTree.root = integerBSTTree.deleteBST(integerBSTTree.root,numDelete);
        integerBSTTree.Middle(integerBSTTree.root);
    }
}

