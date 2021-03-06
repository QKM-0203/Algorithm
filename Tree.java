import java.util.*;
class LinkNode{
    char date;
    LinkNode lChild;
    LinkNode rChild;
    public LinkNode(char date){
        this.date = date;
    }
}
public class Tree {
    private static String string ;
    private static int i = 0;
  //先序创建树   
 private static LinkNode CreatTree(){
        LinkNode root;
        char ch = string.charAt(i++);
        if (ch == '#'){
            return null;
        } else{
            root = new LinkNode(ch);
            root.lChild = CreatTree();
            root.rChild = CreatTree();
        }
        return root;
    }
    //后序输出
    private static void EndShow(Stack<LinkNode> StackLinkNode,LinkNode root){
           LinkNode pre = null;
        while(root !=null || !StackLinkNode.empty()){
            if (root != null){
                StackLinkNode.push(root);
                root = root.lChild;
            }else {
                LinkNode p = StackLinkNode.peek();
                if(p.rChild == null || p.rChild == pre){
                    LinkNode pop = StackLinkNode.pop();
                    pre = pop;
                    System.out.print(pop.date);
                    root = null;
                }else{
                    root = p.rChild;
                }
            }
        }

    }
     //先序输出
    private static void FirstShow(Stack<LinkNode> StackLinkNode,LinkNode root){
        while(root !=null || !StackLinkNode.empty()){
            if (root != null){
                System.out.print(root.date);
                StackLinkNode.push(root);
                root = root.lChild;
            }else {
                LinkNode pop = StackLinkNode.pop();
                root = pop.rChild;
            }
        }
    }
    //层次遍历
    private static void ArrangementShow(ArrayDeque<LinkNode> DequeLinkNode,LinkNode root){
        DequeLinkNode.add(root);
        while(!DequeLinkNode.isEmpty()){
            LinkNode poll = DequeLinkNode.poll();
            System.out.print(poll.date);
            if(poll.lChild != null){
                DequeLinkNode.add(poll.lChild);
            }
            if(poll.rChild != null){
                DequeLinkNode.add(poll.rChild);
            }
        }

    }
     //中序遍历
        private static void MiddleShow(Stack<LinkNode> StackLinkNode,LinkNode root){
        while(root !=null || !StackLinkNode.empty()){
            if (root != null){
                StackLinkNode.push(root);
                root = root.lChild;
            }else {
                LinkNode pop = StackLinkNode.pop();
                System.out.print(pop.date);
                root = pop.rChild;
            }
        }

    }
    public static void main(String[] args) {
        string = new Scanner(System.in).next();
        Stack<LinkNode> stack = new Stack<>();
        LinkNode linkNode = CreatTree();
        FirstShow(stack, linkNode);
        System.out.println();
        MiddleShow(stack, linkNode);
        System.out.println();
        EndShow(stack, linkNode);
        System.out.println();
        ArrayDeque<LinkNode> linkNodes = new ArrayDeque<>();
        ArrangementShow(linkNodes, linkNode);
    }
    /*
    输入:ABC##DE#G##F###

   先序: ABCDEGF
   中序: CBEGDFA
   后序: CGEFDBA
   层次输出: ABCDEFG
     */
}
