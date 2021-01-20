import java.util.Arrays;
public class Kmp {
    public static String str1 = "abcadabcab";
    public static String str2 = "abcab";
    public static int[] getNext(){
        int[] next = new int[]{0,0,0,0,0};
        int j = 1;
        for(int i = 0;i < str2.length(); i++){
            if(str2.charAt(i) == str2.charAt(j)){
                j++;
            }else {
                while ((j > 0) && (str2.charAt(i) != str2.charAt(j))) {
                    j = next[j-1];
                }
            }
            next[i] = j;
        }
        System.out.println("next数组为:"+Arrays.toString(next));
        return next;
    }
    public static int isFind(int[] next){
        int j = 0;
        int i = 0;
        for(i = 0; i < str1.length();i++){
            while (j > 0 && (str1.charAt(i)) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if(j == str2.length()-1){
                return i-j;
            }
            if(str1.charAt(i) == str2.charAt(j)){
                j++;
            }

        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println("配点的下标为:"+isFind(getNext()));

    }
}
