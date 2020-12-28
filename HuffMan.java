import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class HuffNode implements Comparable<HuffNode>{
    Character data;
    Integer weight;
    HuffNode LChild;
    HuffNode RChild;
    HuffNode Parent;

    //重写该接口，这样每次取数都是排好序的
    @Override
    public int compareTo(HuffNode o) {
       return this.weight - o.weight;
    }
    public HuffNode(char data, int weight) {
        this.data = data;
        this.weight = weight;
    }
}
public class HuffMan{
    private static final char ZERO = '0';//编码的左为0

    private static final char ONE = '1'; //编码右为1

    private static HuffNode root;   //哈夫曼树的根节点

    private static Map<Character,String> LeaveMapAsc;//哈夫曼树的叶子对应的编码

    private static Map<Character,Integer> LeaveMapWeight; //哈夫曼树的叶子对应的权重

    private static ArrayList<HuffNode> Leave  ;    //存储叶子节点

    /**
     *
     * 创建哈夫曼树
     */
    private static void creatHuffman(){
        //利用优先队列排好序，实现Comparable接口
        PriorityQueue<HuffNode> priQueue = new PriorityQueue<>();

        LeaveMapWeight.forEach((character,integer)-> {

            HuffNode huffNode = new HuffNode(character, integer);

            priQueue.add(huffNode);

            Leave.add(huffNode);
            }
        );
        int QueueLen = priQueue.size();//获得叶子的个数，也就是优先队列的大小
        int i = 0;
        //总共有2*叶子数-1，所以循环叶子数-1次;
        while(i < QueueLen - 1 ) {
            HuffNode pollNode1 = priQueue.poll();//已经是排好序的因为实现了Comparable接口，直接弹出两个最小的数
            HuffNode pollNode2 = priQueue.poll();
            //双亲的data没有影响，所以以'0'来存
            HuffNode huffNode = new HuffNode('0', pollNode1.weight + pollNode2.weight);
            //改孩子
            huffNode.LChild = pollNode1;
            huffNode.RChild = pollNode2;
            //改双亲
            pollNode1.Parent = huffNode;
            pollNode2.Parent = huffNode;
            //把双亲加到优先队列里
            priQueue.add(huffNode);
            i++;
        }
        //队列最后就是根
        root = priQueue.poll();
    }

    /**
     * 给每个字符进行编码
     * @param Leave
     */

    public static void Encoding(ArrayList<HuffNode> Leave){
        int b = 65;//表示A-F字符
        StringBuilder string = new StringBuilder();
        LeaveMapAsc = new HashMap<>();
        //编码是从叶子出发，最后逆置存集合中去
        for (int i1 = 0; i1 < Leave.size(); i1++) {
            HuffNode huffNode = Leave.get(i1);
            while(huffNode != root){
                if (huffNode.Parent != null && huffNode == huffNode.Parent.LChild){
                    string.append(ZERO);
                } else{
                    string.append(ONE);
                }
                huffNode = huffNode.Parent;
            }
            string.reverse();
            LeaveMapAsc.put((char)b,string.toString());
            string.delete(0,string.length());
            b = b + 1;
        }
    }


    /**
     *译码，利用队列，先进先出，判断完后就直接弹出去，找到既没有左子树有没有右子树的就直接输出，并且再次从根开始找
     * @param str1
     */

    private static void Decoding(String str1){
        ArrayDeque<Character> characters = new ArrayDeque<>();
        for(int i = 0; i < str1.length(); i++){
            characters.add(str1.charAt(i));
        }
        System.out.println("");
        HuffNode PreRoot = root;
        while(!characters.isEmpty()){
            Character poll = characters.poll();
            if(poll == '0') root = root.LChild;
            if(poll == '1') root = root.RChild;
            if(root.LChild == null && root.RChild == null ){
                System.out.print(root.data);
                root = PreRoot;
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeaveMapWeight = new HashMap<>();
        Leave   =  new ArrayList<>();
        int a = 65;
        for (int i = 0; i < 6; i++) {
            LeaveMapWeight.put((char)a,scanner.nextInt());
            a = a + 1;
        }
        String str = scanner.next();
        String str1 = scanner.next();
        creatHuffman();
        Encoding(Leave);
        LeaveMapAsc.forEach(new BiConsumer<Character, String>() {
            @Override
            public void accept(Character character, String s) {
                System.out.print(character+":" + s);
                if(character != 'F') System.out.println("");
            }
        });
        Decoding(str1);
    }
}
