import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
class Direction{
    int x;
    int y;
    int wayOutNum;
}

public class ChessBoard {
    /*
     * 马踏棋盘问题：(贪婪法求解)
     * 棋盘有64个位置，“日”字走法，刚好走满整个棋盘
     */

    //下一个走法的方向类
        static final int[] dx = { -2, -1, 1, 2, 2, 1, -1, -2 }; // x方向的增量
        static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 }; // y方向的增量
        static final int N = 8;
        static int[][] chessboard = new int[N][N]; // 棋盘

        /**
         *
         * @param nami
         * @param x,y为棋子的位置
         * @return 如果棋子的位置不合法，则返回一个大于8的数。
         * 否则返回棋子的下个出路的个数
         */
        static int wayOut(int x, int y){
            int count = 0;
            int tx, ty, i;
            //判断是否超出棋盘边界，该位置是否已经下过
            if(x<0 || x>7 || y<0 || y>7 || chessboard[x][y]!=0){
                return 9;
            }
            for(i=0; i<N; i++){
                tx = x+dx[i];
                ty = y+dy[i];
                //如果棋子的下个出路可行，则出路数自加一次
                if(tx>-1 && tx<8 && ty>-1 && ty<8 && chessboard[tx][ty]==0)
                    count++;
            }
            return count;
        }

        /**
         * 按照棋子的下个出路的个数从低到高排序
         * @param next 棋子的八个位置的数组
         */
        static void sort(Direction[] next){
            int i, j, index;
            Direction temp = null;
            //这里用的选择排序
            for(i=0; i<N; i++){
                index = i;
                for(j=i+1; j<N; j++){
                    if(next[index].wayOutNum > next[j].wayOutNum)
                        index = j;
                }
                if(i != index){
                    temp = next[i];
                    next[i] = next[index];
                    next[index] = temp;
                }
            }
        }

        static void Move(int x, int y, int step){
            int i, j;
            int tx, ty;
            //如果step==64，则说明每个棋格都走到了，现在只需要打印结果就完了
            if(step == N*N){
                for(i=0; i<N; i++){
                    for(j=0; j<N; j++){
                        System.out.printf("%3d", chessboard[i][j]);
                    }
                    System.out.println();
                }
                System.exit(0);
            }

            //下一个棋子的N个位置的数组
            Direction[] next = new Direction[N];

            for(i=0; i<N; i++){
                Direction temp = new Direction();
                temp.x = x+dx[i];
                temp.y = y+dy[i];
                next[i] = temp;
                //循环得到下个棋子N处位置的下个出路的个数
                next[i].wayOutNum = wayOut(temp.x, temp.y);
            }

            //配合贪婪算法，按下个棋子的下个出路数排序后，next[0]就是下个出路数最少的那个
            sort(next);

            for(i=0; i<N; i++){
                tx = next[i].x;
                ty = next[i].y;
                chessboard[tx][ty] = step;
                Move(tx, ty, step+1);
            /*如果上面Move()往下一步走不通，则回溯到这里
            重置chessboard[tx][ty]为0，接着i++,又循环...... */
                chessboard[tx][ty] = 0;
            }
        }

        public static void main(String[] args) {
            int i, j;
            //初始化棋盘
            for(i=0; i<8; i++){
                for(j=0; j<8; j++){
                    chessboard[i][j] = 0;
                }
            }
            System.out.println("请输入棋子开始位置（0-7）：");
            Scanner sc = new Scanner(System.in);
            int x = sc.nextInt();
            int y = sc.nextInt();
            //第一步不用比较，赋值第一步
            chessboard[x][y] = 1;
            Move(x, y, 2);
        }
}
