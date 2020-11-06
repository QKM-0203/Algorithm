/*
输入：ABC##DE#G##F###

输出：
3 2 2
CGF
输出叶子节点数，度为一的，度为二的个数
输出叶子节点
*/
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
typedef struct Tree{
	char date;
	struct Tree* leftchild;
	struct Tree* rightchild;
}*Treenode,Treeinode;
char Pre[100];
int countone = 0;
int countzero = 0;
int counttwo = 0;
Treenode creat(){
	Treenode Tree ;
	char a = getchar();
	if (a == '#'){
		return NULL;
	} else {
	   Tree = (Treenode)malloc(sizeof(Treeinode));
	   Tree->date = a;
	   Tree->leftchild = creat();
	   Tree->rightchild = creat();
	}
	return Tree;
}
void FindOne(Treenode root){
	if (root){
		if((root->leftchild == NULL && root->rightchild != NULL) || (root->leftchild!=NULL && root->rightchild==NULL)){
		countone++;
		} 
		FindOne(root->leftchild);
		FindOne(root->rightchild);
	}
}
void FindZero(Treenode root){
	if(root){
	if (root->leftchild == NULL && root->rightchild == NULL){	
	   	Pre[countzero++]=root->date;
	    }
		FindZero(root->leftchild);
		FindZero(root->rightchild);
    }
}
void FindTwo(Treenode root){
	if(root){
		if (root->leftchild != NULL && root->rightchild != NULL){
		counttwo++;
	    }
		FindTwo(root->leftchild);
		FindTwo(root->rightchild);
	}
}
int main(void){
	Treenode root = creat();
	FindZero(root);
	FindOne(root);
	FindTwo(root);
	printf("%d %d %d\n",countzero,countone,counttwo);
	puts(Pre);
}
