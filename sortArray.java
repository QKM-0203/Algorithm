package com.fly.game;

public class sortArray {
    /*冒泡排序
    public static void main(String[] args) {
        int[] num={22,13,4,5,23,6,7};
        for (int i = 0; i < num.length-1; i++) {
            Boolean flag=true;
            for (int i1 = 0; i1 < num.length-1-i; i1++) {
                if(num[i1]>num[i1+1]){
                    int temp=num[i1];
                    num[i1]=num[i1+1];
                    num[i1+1]=temp;
                    flag=false;
                }
            }
            if(flag) break;
        }

        for (int i : num) {
            System.out.print(i+" ");
        }
    }*/
    /*插入排序
    public static void main(String[] args) {
        int[] num={22,13,4,5,23,6,7};
        int i1=0;
        for (int i = 1; i < num.length; i++) {
            int temp=num[i];
            for ( i1 = i-1; i1 >=0; i1--) {
                if(num[i1]>temp){
                    num[i1+1]=num[i1];
                }
                else break;
            }
            num[i1+1]=temp;
        }
        for (int i : num) {
            System.out.print(i+" ");
        }
    }*/
    /*选择排序
    public static void main(String[] args) {
        int[] num={22,13,4,5,23,6,7};
        int i1=0;
        for (int i = 0; i < num.length-1; i++) {
            int temp=i;
            for ( i1 = i+1; i1 <num.length; i1++) {
                if(num[i1]<num[temp]) {
                    temp = i1;
                }
            }
            if(temp!=i){
                int flag=num[i];
                num[i]=num[temp];
                num[temp]=flag;
            }
        }
        for (int i : num) {
            System.out.print(i+" ");
        }
    }*/
    /*希尔排序
    public static void main(String[] args) {
        int[] num = {22, 13, 4, 5, 23, 6, 7,0,9};
        int i1 = 0;
        int k = num.length / 2;
        while (k > 0) {
            for (int i = k; i < num.length; i=i+k) {
                int temp = num[i];
                for (i1 = i-k; i1 >=0; i1=i1-k) {
                    if (num[i1] >temp) {
                        num[i1+k]=num[i1];
                    }
                    else break;
                }
                num[i1+k]=temp;

            }
            k=k/2;
        }
            for (int i : num) {
                System.out.print(i + " ");
            }
        }*/
    /*归并排序
    private static void sort(int WaitSort[],int start,int mid,int end){
        int[] result=new int[10];
        int mnd=mid+1,n=start,m=start;
        while(start<=mid && mnd<=end){
             if(WaitSort[start]>WaitSort[mnd]){
                 result[n++]=WaitSort[mnd++];
             }
             else result[n++]=WaitSort[start++];
        }
        if(start <= mid){
            System.arraycopy(WaitSort,start,result,n,mid-start+1);
        } else if(mnd <= end){
            System.arraycopy(WaitSort,mnd,result,n,end-mnd+1);
        }

        /*
        while(start<=mid){
            result[n++]=WaitSort[start++];
        }
        while(mnd<=end){
            result[n++]=WaitSort[mnd++];
        }*/
       /* start=m;
        for(int i=start;i<=end;i++){
            WaitSort[i]=result[i];
          // System.out.print(result[i]+" ");
        }

    }
    private static void fen(int WaitFen[],int start,int end){
        if(start<end){
            int mid=(start+end)/2;
            fen(WaitFen,start,mid);
            fen(WaitFen,mid+1,end);
            sort(WaitFen,start,mid,end);
        }
    }

    public static void main(String[] args) {
        int[] num = {22, 13, 4, 5, 23, 6, 7,0,9};
        fen(num,0,8);
        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i]+" ");
        }
    }*/
    /*快排交换法
     private static int PriSort(int[] a, int begin, int end) {
         int key = a[begin];
         int k = begin;
         while(begin < end){
             while(begin <= end && a[end] > key){
             end--;
             }

             while(begin < end && a[begin] <= key){
             begin++;
             }

             if(begin < end ){
                 int temp = a[begin];
                 a[begin] = a[end];
                 a[end] = temp;
             }
         }
         a[k] = a[begin];
         a[begin] = key;
         return begin;
     }
    private static void sort(int[] a,int begin,int end){
        if(begin < end){
            int mid = PriSort(a,begin,end);
            sort(a,begin,mid-1);
            sort(a,mid+1,end);
        }
    }



    public static void main(String[] args) {
        int[] num = {22, 13, 4, -1, 233, 6, 7, 0, 9};
        sort(num, 0, 8);
        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i] + " ");
        }
    }*/
    /*基数排序按照最大数位数取。稳定性好，以空间换时间
     private static void BucketSort(int[] num) {
         int[][] PositiveBucket = new int[10][num.length];
         int[][] negativeBucket = new int[10][num.length];
         int bucketElementNCounts[] = new int[10];
         int bucketElementPCounts[] = new int[10];
         int max = num[0];
         for (int i = 0;i < num.length;i++){
             if (max < Math.abs(num[i])) max = Math.abs(num[i]);
         }
         int length = (max + "").length();
         for (int k = 0,n = 1; k < length; k++,n = n * 10) {
             for (int i = 0; i < num.length; i++) {

                 int digitValue = num[i] / n % 10;
                 if (digitValue < 0 || (num[i] < 0 && digitValue == 0))
                     negativeBucket[Math.abs(digitValue)][bucketElementNCounts[Math.abs(digitValue)]++]
                         = Math.abs(num[i]);
                 else PositiveBucket[digitValue][bucketElementPCounts[digitValue]++] = num[i];
             }
             int index = 0;
             if(k == length-1) {
                 for (int m = 9; m >= 0; m--) {
                     if (bucketElementNCounts[m] != 0) {
                         for (int j = bucketElementNCounts[m] - 1; j >= 0; j--) {
                             num[index++] = negativeBucket[m][j] * (-1);
                         }
                     }
                     bucketElementNCounts[m] = 0;
                 }
             } else{
                 for (int m = 0; m < 10; m++) {
                     if (bucketElementNCounts[m] != 0) {
                         for (int j = 0 ; j < bucketElementNCounts[m]  ; j++) {
                             num[index++] = negativeBucket[m][j] * (-1);
                         }
                     }
                     bucketElementNCounts[m] = 0;
                 }
             }
             for (int m = 0; m < 10; m++) {
                 if (bucketElementPCounts[m] != 0) {
                     for (int j = 0; j < bucketElementPCounts[m]; j++) {
                         num[index++] = PositiveBucket[m][j];
                     }
                 }
                 bucketElementPCounts[m] = 0;
             }

         }
     }
    public static void main(String[] args) {
        int[] num = {22, 1321, -4, -89, -23, -6, -78223, 0, 9};
        BucketSort(num);
       for (int i = 0; i < num.length; i++) {
           System.out.print(num[i] + " ");
        }
    }*/

}

