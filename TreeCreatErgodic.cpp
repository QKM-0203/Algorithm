#include<stdio.h>
#include<string.h>
#include<stdlib.h>
typedef struct Tree{
	char date;
	struct Tree* leftchild;
	struct Tree* rightchild;
}*Treenode,Treeinode;
void Print(Treenode node){
	printf("%c",node->date);
}
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
void FirstFind(Treenode root){
	if (root){
		Print(root);
		
		FirstFind(root->leftchild);
		
		FirstFind(root->rightchild);
	}
}
void MiddleFind(Treenode root){
	if (root){	
	    MiddleFind(root->leftchild);
		
		Print(root);
	
		MiddleFind(root->rightchild);
	}
}
void EndFind(Treenode root){
	if (root){
		EndFind(root->leftchild);
		
		EndFind(root->rightchild);
		
		Print(root);
	}
}
int main(void){
	Treenode root = creat();
	FirstFind(root);
	printf("\n");
	MiddleFind(root);
	printf("\n");
	EndFind(root);
}
