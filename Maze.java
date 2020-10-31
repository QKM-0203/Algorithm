package com;

import java.util.Scanner;
import java.util.Stack;

public class Maze {
    private static int StartX = 0,StartY = 0;
    private static int EndX = 0,EndY = 0;
    private static int[][] Book = new int[64][64];
    static int[][] maze= {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 1, 1, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    };
    private static Stack<Coordinate>  FindLoad(Stack<Coordinate> stack){
        int temporaryX = Maze.StartX;
        int temporaryY = Maze.StartY;
        Coordinate coordinate = new Coordinate();
        coordinate.x = temporaryX;
        coordinate.y = temporaryY;
        stack.push(coordinate);
        Book[temporaryX][temporaryY]=1;
        while (!stack.isEmpty() && (temporaryX != Maze.EndX || temporaryY != EndY)){
            if (Maze.Book[temporaryX][(temporaryY + 1)] == 0 && Maze.maze[temporaryX][temporaryY + 1] == 0){
                Maze.Book[temporaryX][temporaryY + 1] = 1;
                Coordinate CoordinateRight = new Coordinate();
                CoordinateRight.x = temporaryX;
                CoordinateRight.y = temporaryY + 1;
                stack.push(CoordinateRight);
                temporaryY++;
            } else if (Maze.Book[temporaryX + 1][(temporaryY)] == 0 && Maze.maze[temporaryX + 1][temporaryY] == 0){
                Maze.Book[temporaryX + 1][temporaryY] = 1;
                Coordinate CoordinateDown = new Coordinate();
                CoordinateDown.x = temporaryX + 1;
                CoordinateDown.y = temporaryY;
                stack.push(CoordinateDown);
                temporaryX++;
            } else if (Maze.Book[temporaryX][(temporaryY - 1)] == 0 && Maze.maze[temporaryX][temporaryY - 1] == 0){
                Maze.Book[temporaryX][temporaryY - 1] = 1;
                Coordinate CoordinateLeft = new Coordinate();
                CoordinateLeft.x = temporaryX;
                CoordinateLeft.y = temporaryY - 1;
                stack.push(CoordinateLeft);
                temporaryY--;
            } else if (Maze.Book[temporaryX - 1][(temporaryY)] == 0 && Maze.maze[temporaryX - 1][temporaryY] == 0){
                Maze.Book[temporaryX - 1][temporaryY] = 1;
                Coordinate CoordinateUp = new Coordinate();
                CoordinateUp.x = temporaryX - 1;
                CoordinateUp.y = temporaryY;
                stack.push(CoordinateUp);
                temporaryX--;
            } else {
                stack.pop();
                if(!stack.isEmpty()){
                    temporaryX = stack.peek().x;
                    temporaryY = stack.peek().y;
                }


            }
        }
        return stack;
    }
    private static void ShowLoad(Stack<Coordinate> stack){
        if(stack.isEmpty()) {
            System.out.println("没有通路！");
        } else {
            while (!stack.isEmpty()) {
                Coordinate coordinate = stack.pop();
                Maze.maze[coordinate.x][coordinate.y] = -2;
                //System.out.println('('+(coordinate.x+"")+(coordinate.y+"")+')');
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.printf("%3d", Maze.maze[i][j]);
                }
                System.out.println("");
            }
        }
    }

    public static void main(String[] args) {
        Stack<Coordinate> stack = new Stack<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入起始坐标：");
        Maze.StartX = scanner.nextInt();
        Maze.StartY = scanner.nextInt();
        System.out.println("请输入结束坐标：");
        Maze.EndX = scanner.nextInt();
        Maze.EndY = scanner.nextInt();
        Maze.ShowLoad(Maze.FindLoad(stack));
    }
}
class Coordinate{
     int x = 0;
     int y = 0;
}