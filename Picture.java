import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
class PointNode{
    int index;
    PointNode next;
    public PointNode(int index){
        this.index = index;
        next = null;
    }
}

class ArrayPoint{
    char point;
    PointNode next;
    public ArrayPoint(char chars){
        point = chars;
        next = null;
    }
}
class Degree{
    public int inDegree;
    public int outDegree;
    public char  ch;

    public Degree(int inDegree, int outDegree, char ch) {
        this.inDegree = inDegree;
        this.outDegree = outDegree;
        this.ch = ch;
    }
}
public class Main {
    //计算出度
    public static void findOutDegree(){
        for(int i = 0; i < 27; i++){
            if(ArrPoint[i] != null){
                PointNode p = ArrPoint[i].next;
                     while(p != null) {
                         degree[i].outDegree++;
                         p = p.next;
                     }
            }
        }
    }
    //创建邻接表，并且邻接表是有序的
    private static void CreatPicture(char char0,char char1){
        PointNode Node = new PointNode(char1 - 65);
        if(ArrPoint[char0 - 65].next == null){
            ArrPoint[char0 - 65].next = Node;
        } else{
            PointNode pre = ArrPoint[char0 - 65].next;
            PointNode pre1 = pre;
            if((char)(pre.index+65) > char1){
                ArrPoint[char0 - 65].next = Node;
                Node.next = pre;
                return ;
            }
            PointNode p1 = pre;
            pre = pre.next;

            while(pre!= null){
                if((char)(pre.index+65) > char1){
                    p1.next = Node;
                    Node.next = pre;
                    return ;
                }
                p1 = pre;
                pre = pre.next;
            }
           while(pre1.next != null){
               pre1 = pre1.next;
           }
           pre1.next = Node;
        }

    }
    //递归深度搜索
    private static void DFS(int index){
        PointNode p = ArrPoint[index].next;
        if(key == 5){
            return ;
        }
        //存在邻接边
        while(p != null){
            if(Book[p.index] == 0){
                Book[p.index] = 1;
                //System.out.print((char)(p.index + 65));
                key++;
                DFS(p.index);
            }
            p = p.next;
        }

    }
    //广搜
    private static void BFS(int index){
        PointNode p = ArrPoint[index].next;
        PointNode p1 = null;
        if(key == 5){
            return ;
        }
        //存在邻接边
        while(p != null){
            if(Book[p.index] == 0){
                Book[p.index] = 1;
                //System.out.print((char)(p.index + 65));
                key++;
            }
            p1 = p;
            p = p.next;
        }
        if(p1 != null){
            DFS(p1.index);
        }
        return ;

    }

    public static ArrayPoint[] ArrPoint = new ArrayPoint[27];
    public static Degree[] degree = new Degree[27];
    public static int key = 1;
    public static int[] Book = new int[27];
    public static int sumPoint;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
         sumPoint = scan.nextInt();
        int connectPoint = scan.nextInt();
        String str = scan.next();
        char[] chars = str.toCharArray();

        for(int i = 0; i < sumPoint; i++){
            ArrPoint[chars[i] - 65] = new ArrayPoint(chars[i]);
            degree[chars[i] - 65] = new Degree(0,0,chars[i]);
        }
        for (int i = 0; i < connectPoint; i++){
            String PreConnect = scan.next();
            char char0 = PreConnect.charAt(0);
            char char1 = PreConnect.charAt(1);
            CreatPicture(char0,char1);
        }
       //计算入度
       for(int i = 0; i < chars.length; i++) {
           for (int j = 0; j < 27; j++) {
               if (ArrPoint[j] != null) {
                   PointNode p = ArrPoint[j].next;
                   while (p != null) {
                       if((char)(p.index+65) == chars[i]){
                           degree[chars[i] - 65].inDegree++;
                       }
                       p = p.next;
                   }
               }
           }
       }
        findOutDegree();
        for (int i = 0; i < 27; i++) {
            if(degree[i] != null){
                System.out.println(degree[i].ch + " "+degree[i].outDegree + " " + degree[i].inDegree);
            }

        }
        System.out.print("A");
        Book[0] = 1;
        DFS('A' - 65);
        for(int i = 0; i < sumPoint; i++){
            Book[i] = 0;
        }
        key = 1;
        System.out.print("\nA");
        BFS('A' - 65);
        //求联通分支的个数
        int sum = 0;
         for(int i = 0; i < sumPoint; i++){
             if(Book[chars[i] - 65] == 0 ){
                 BFS(chars[i] - 65);
                 sum++;
             }
         }
        System.out.println(sum);
    }
}


