import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 *可以实现txt文件的压缩与解压缩，可以是中文可以是英文，输入绝对路径就可以
 * 压缩会产生一个zip.txt压缩文件，配置文件是config.txt(存的是哈夫曼编码hashmap)，code.txt存的是01码，
 * 解压之后会生成decode.txt
 */
class HuffNode implements Comparable<HuffNode>{
    String data;
    Integer weight;
    HuffNode LChild;
    HuffNode RChild;
    HuffNode Parent;

    //重写该接口，这样每次取数都是排好序的
    @Override
    public int compareTo(HuffNode o) {
        return this.weight - o.weight;
    }
    public HuffNode(String data, int weight) {
        this.data = data;
        this.weight = weight;
    }

}

public class HuffMan {
    private static final String ZERO = "0";//编码的左为0

    private static final String ONE = "1"; //编码右为1

    private static HuffNode root;   //哈夫曼树的根节点

    private static Map<String, String> LeaveMapAsc;//哈夫曼树的编码对应的叶子

    private static Map<String, String> LeaveMapAsc1;//哈夫曼树的叶子对应的编码

    private static Map<String, Integer> LeaveMapWeight; //哈夫曼树的叶子对应的权重

    private static ArrayList<HuffNode> Leave;    //存储叶子节点

    private static String lastByte = "";

    /**
     * 创建哈夫曼树
     */
    private static void creatHuffman() throws IOException {
        //利用优先队列排好序，实现Comparable接口
        PriorityQueue<HuffNode> priQueue = new PriorityQueue<>();

        LeaveMapWeight.forEach((character, integer) -> {

                    HuffNode huffNode = new HuffNode(character, integer);

                    priQueue.add(huffNode);

                    Leave.add(huffNode);
                }
        );

        int QueueLen = priQueue.size();//获得叶子的个数，也就是优先队列的大小
        int i = 0;
        //总共有2*叶子数-1，所以循环叶子数-1次;
        while (i < QueueLen - 1) {
            HuffNode pollNode1 = priQueue.poll();//已经是排好序的因为实现了Comparable接口，直接弹出两个最小的数
            HuffNode pollNode2 = priQueue.poll();
            //双亲的data没有影响，所以以'0'来存
            HuffNode huffNode = new HuffNode("0", pollNode1.weight + pollNode2.weight);
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
     * 给每个字符进行编码，将编码后的hashmap写入配置文件.config.txt中去
     *
     * @param Leave
     */

    public static void EncodingChar(ArrayList<HuffNode> Leave) throws IOException {
        StringBuilder string = new StringBuilder();
        LeaveMapAsc = new HashMap<String, String>();
        LeaveMapAsc1 = new HashMap<>();
        //编码是从叶子出发，最后逆置存集合中去
        for (int i = 0; i < Leave.size(); i++) {
            HuffNode huffNode = Leave.get(i);
            HuffNode hu = huffNode;
            while (huffNode.Parent != null) {
                if (huffNode == huffNode.Parent.LChild) {
                    string.append(ZERO);
                } else {
                    string.append(ONE);
                }
                huffNode = huffNode.Parent;
            }
            string.reverse();
            LeaveMapAsc.put(string.toString(), hu.data);
            LeaveMapAsc1.put(hu.data, string.toString());
            string.delete(0, string.length());
        }

    }

    public static HashMap<String,String> returnMap(String config) throws IOException, ClassNotFoundException {
        if(!new File(config).exists()){
            return null;
        }
        HashMap<String,String> LeaveMapAscOB = null;
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(config));
        return LeaveMapAscOB = (HashMap<String, String>) objectInputStream.readObject();
    }

    /**
     *根据01串获取字符
     * @param
     */

    private static String getChar(String str01,String Filename) throws IOException, ClassNotFoundException {
        String subFilename = Filename.substring(0, Filename.indexOf("."));
        String  config = null;
        config = subFilename + ".config" + ".txt";
        File file = new File(config);
        HashMap<String,String> LeaveMapAscOB = null;
        if( !file.exists()){
            System.out.println("配置文件不存在");
            return null;
        }else{
            LeaveMapAscOB = returnMap(config);
        }
        StringBuilder last = new StringBuilder(str01).append(LeaveMapAscOB.get("last"));
        StringBuilder stringBuilder = new StringBuilder();
        String temp = "";
        for (int i = 0; i < last.length(); i++){
            temp += last.charAt(i);
            String character = LeaveMapAscOB.get(temp);
            if(character != null){
                stringBuilder.append(character);
                temp = "";
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 读文件的内容返回字符串
     * @param
     * @throws IOException
     */
    public static String Read(String  name) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(name));
        int line = 0;
        byte[] By = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();

        while((line = bufferedInputStream.read(By))!= -1){
            stringBuilder.append(new String(By,0,line));
        }
        bufferedInputStream.close();
        return stringBuilder.toString();
    }


    public static void WriteObject(String configFilename) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(configFilename));
        LeaveMapAsc.put("last",lastByte);
        objectOutputStream.writeObject(LeaveMapAsc);
    }
    /**
     * 读文件的内容返回字节数组
     * @param
     * @throws IOException
     */
    public static byte[] Read1(String filename) throws IOException {
        if(!new File(filename).exists()){
            System.out.println("待解压文件不存在。。");
            return null;
        }
        BufferedInputStream bufferedInputStream1 = new BufferedInputStream(new FileInputStream(filename));
        byte[] dest = null;//最后在内存中形成的字节数组(目的地)
        ByteArrayOutputStream Boas = new ByteArrayOutputStream();//此流是程序到新文件的输出流
        byte[] flush = new byte[1024 * 10];//设置缓冲，这样便于传输，大大提高传输效率
        int len = -1;//设置每次传输的个数,若没有缓冲的数据大，则返回剩下的数据，没有数据返回-1
        while ((len = bufferedInputStream1.read(flush)) != -1) {
            Boas.write(flush, 0, len);//每次读取len长度数据后，将其写出
        }
        Boas.flush();//刷新管道数据
        dest = Boas.toByteArray();//最终获得的字节数组
        bufferedInputStream1.close();
        return dest;//返回Boas在内存中所形成的字节数组

    }

    /**
     * 将01串变成字节写进压缩文件中去
     * @param
     * @throws IOException
     */
    public static void CompressWrite(String str,String compressName) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(compressName));
        int len = str.length() / 8;
        if (str.length() % 8 != 0) {
            // 编码长度不能被8整除，保存多余的不足8位的部分
            lastByte = str.substring(len * 8);
        }
        // 压缩存储的byte数组
        byte[] huffmanCode = new byte[len];
        for (int i = 0; i < huffmanCode.length; i++) {
            huffmanCode[i] = (byte) Integer.parseInt(str.substring(i * 8, i * 8 + 8), 2);
        }
        System.out.printf("压缩后的字节 %d\n", "".equals(lastByte) ? len : len + 1);
        bufferedOutputStream.write(huffmanCode);
        bufferedOutputStream.close();

    }
    private static void Write(String str01,String filename) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename));
        bufferedOutputStream.write(str01.getBytes());
        bufferedOutputStream.close();
    }


    /**
     * 解压缩写入文件中去
     * @param UncompressName
     * @param compressContent
     * @throws IOException
     */
    public static int UncompressWrite(byte[] compressContent,String UncompressName) throws IOException, ClassNotFoundException {
        //转换成01串
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < compressContent.length; i++){
            int temp = (int)compressContent[i];
            // 补高位，和256（1 0000 0000）进行按位或
            temp |= 256;
            String str = Integer.toBinaryString(temp);

            String substring = str.substring(str.length() - 8);
            stringBuilder.append(substring);
        }
        //根据01串获取对应的字符写入解压文件中去。
        String aChar = getChar(stringBuilder.toString(),UncompressName);
        if(aChar == null){
            return 0;
        }

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(UncompressName));
        bufferedOutputStream.write(aChar.getBytes());
        bufferedOutputStream.close();
        return 1;
    }



    /**
     * 获取文件中每个字符出现的次数，存到对应的权值HashMap中去
     * @param read
     * @param LeaveMapWeight
     */
    public static void getCharSum(String read,HashMap<String,Integer> LeaveMapWeight){
        for (int i = 0; i < read.length(); i++){
            Character character = read.charAt(i);
            if(LeaveMapWeight.get(character+"") == null){
                LeaveMapWeight.put(character+"",1);
            } else{
                Integer integer = LeaveMapWeight.get(character+"");
                integer++;
                LeaveMapWeight.put(character+"",integer);
            }
        }
    }
    /**
     * 获取文件的01串,在调用写进压缩文件的方法
     * @param read
     */
    public static String  getStr01(String read,String compressFilename) throws IOException {
        //获取文件对应的01串
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < read.length(); i++){
            stringBuilder.append(LeaveMapAsc1.get(read.charAt(i)+""));
        }
        CompressWrite(stringBuilder.toString(),compressFilename);
        return stringBuilder.toString();
    }

    private static void show() throws IOException, ClassNotFoundException {
        System.out.println("\t\t\t\t\t\t\t\t\t\t--------------------哈夫曼编译器----------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t----------------1--压缩文件---------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t----------------2--解压文件---------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t----------------3--编码方案---------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t\t----------------0--退出------------------------------");
        Scanner scanner = new Scanner(System.in);
        int key;
        while(true) {
            key = scanner.nextInt();
            if (key == 1) {
                //读入要压缩的文件名称
                System.out.println("请输入要压缩的文件绝对路径：");
                String codeFilename = scanner.next();
                //为了获取配置文件的名称
                String subFilename = codeFilename.substring(0, codeFilename.lastIndexOf("."));
                System.out.println("正在压缩。。。。。");
                long l = System.currentTimeMillis();
                //读要压缩的文件内容
                String read = null;
                try {
                    read = Read(codeFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("源文件的字节：" + read.getBytes().length);
                //获取内容中每个字符出现的频率
                getCharSum(read, (HashMap<String, Integer>) LeaveMapWeight);
                //构建哈夫曼树
                creatHuffman();
                //获取每个字符的01串，存入HashMap中
                EncodingChar(Leave);
                //获取文件的01串
                String str01 = getStr01(read, subFilename + ".zip" + ".txt");
                Write(str01,subFilename + ".code" + ".txt");
                WriteObject(subFilename + ".config" +".txt");
                long l1 = System.currentTimeMillis();
                System.out.print("压缩完成。。。。。共耗时:");
                System.out.println(l1-l+"毫秒");
                double t = 0;
                double lag =  100*((t = (str01.length()%8) == 0 ? str01.length()/8:(str01.length()/8)+1)/read.getBytes().length);
                DecimalFormat decimalFormat = new DecimalFormat(".##");
                System.out.println("压缩比为："+decimalFormat.format(lag)+"%");
            } else if (key == 2) {
                //解压
                System.out.println("请输入要解压的文件的绝对路径：");
                String decodeFilename = scanner.next();

                String subFilename1 = decodeFilename.substring(0, decodeFilename.indexOf("."));
                System.out.println("正在解压。。。。。");

                long l = System.currentTimeMillis();
                //读压缩文件的内容
                byte[] readCompress = Read1(decodeFilename);
                if(readCompress != null){

                    int i = 0;
                    try {
                        i = UncompressWrite(readCompress, subFilename1 + ".decode.txt");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(i == 1){
                        long l1 = System.currentTimeMillis();
                        System.out.print("解压完成。。。。。共耗时:");
                        System.out.println(l1-l+"毫秒");
                    }
                }


            } else if(key == 3) {
                System.out.println("请输入你要查看的绝对路径文件名");
                String name = scanner.next();
                HashMap<String, String> Map = returnMap(name.substring(0,name.indexOf("."))+".config"+".txt");
                if(Map == null){
                    System.out.println("配置文件不存在，不能读取编码方案");

                }else{
                    Map.forEach((key1, values) -> System.out.println(values + " :" + key1));
                }

            }
            if(key == 0){
                break;
            }
        }

    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LeaveMapWeight = new HashMap<>();
        Leave = new ArrayList<>();
        show();
    }
}
