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
public class Main {
    public static void outSum(Character Char){
        if(outSum.get(Char) == null){
            outSum.put(Char,1);
        }else{
            outSum.put(Char,outSum.get(Char)+1);
        }

    }
    public static void inSum(Character Char){
        if(inSum.get(Char) == null){
            inSum.put(Char,1);
        }else{
            inSum.put(Char,inSum.get(Char)+1);
        }
    }

    //创建邻接表
    private static void CreatPicture(char char0,char char1){
        PointNode Node = new PointNode(char1 - 65);
        if(ArrPoint[char0 - 65].next == null){
            ArrPoint[char0 - 65].next = Node;
        } else{
            PointNode pre = ArrPoint[char0 - 65].next;
            while(pre.next != null){
                pre = pre.next;
            }
            pre.next = Node;
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
            if(book[p.index] == 0){
                book[p.index] = 1;
                System.out.print((char)(p.index + 65));
                key++;
                DFS(p.index);
            }
            p = p.next;
        }

    }
    private static void BFS(int index){
        PointNode p = ArrPoint[index].next;
        PointNode p1 = null;
        if(key == 5){
            return ;
        }
        //存在邻接边
        while(p != null){
            if(book[p.index] == 0){
                book[p.index] = 1;
                System.out.print((char)(p.index + 65));
                key++;
            }
            p1 = p;
            p = p.next;
        }
        DFS(p1.index);
    }

    private static ArrayPoint[] ArrPoint = new ArrayPoint[21];
    private static HashMap<Character,Integer> outSum = new HashMap<>();
    private static HashMap<Character,Integer> inSum = new HashMap<>();
    private static int key = 1;
    private static int[] book = new int[10];
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int sumPoint = scan.nextInt();
        int connectPoint = scan.nextInt();
        String str = scan.next();
        char[] chars = str.toCharArray();

        for(int i = 0; i < sumPoint; i++){
            ArrPoint[i] = new ArrayPoint(chars[i]);
        }
        for (int i = 0; i < connectPoint; i++){
            String PreConnect = scan.next();
            char char0 = PreConnect.charAt(0);
            char char1 = PreConnect.charAt(1);
            CreatPicture(char0,char1);
            outSum(char0);
            inSum(char1);
        }
        Set<Character> outset = outSum.keySet();
        Iterator<Character> ito = outset.iterator();
        Set<Character> inset = inSum.keySet();
        Iterator<Character> iti = inset.iterator();
        for(Character Char : outset ){
            System.out.print(Char+" "+outSum.get(Char)+" ");
            System.out.println(inSum.get(Char));
        }
        System.out.print("A");
        book[0] = 1;
        DFS('A' - 65);
        for(int i = 0; i < sumPoint; i++){
            book[i] = 0;
        }
        key = 1;
        System.out.print("\nA");
        BFS('A' - 65);
    }
}


