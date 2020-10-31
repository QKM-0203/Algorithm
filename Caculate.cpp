#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<math.h>
#define  MAXSIZE  20
char post[100];
int key=0;
char OPE[7] = {'+','-','*','/','^','!','('};
typedef struct charStack{
	char data[MAXSIZE];
	int top;
}Stack;
typedef struct intStack{
	double datanum[MAXSIZE];
	int top;
}Stack_t;
void Init(Stack **ch){  //初始化 
	*ch=(Stack*)malloc(sizeof(Stack));
	(*ch)->top=-1;
   
}
void Init_t(Stack_t **in){
	*in=(Stack_t*)malloc(sizeof(Stack_t));
	(*in)->top=-1;
   
}
bool Push(Stack **ch,char x){	//入栈 
	if((*ch)->top==MAXSIZE-1) return false;
	(*ch)->data[++(*ch)->top]=x;

	return true;
}
bool Push_t(Stack_t **in,double x){	
	if((*in)->top==MAXSIZE-1) return false;
	(*in)->datanum[++(*in)->top]=x;

	return true;
}
bool pop(Stack **ch,char *x){//弹栈 
	if((*ch)->top==-1) return false;
	*x=(*ch)->data[(*ch)->top--];
	return true;
}
bool pop_t(Stack_t **in,double *x){
	if((*in)->top==-1) return false;
	*x=(*in)->datanum[(*in)->top--];
	return true;
}
bool getTop(Stack **ch,char *x){//获取栈顶元素 
	if((*ch)->top==-1) return false;
	*x=(*ch)->data[(*ch)->top];
     return true;
}
bool getTop_t(Stack_t **in,double *x){
	if((*in)->top==-1) return false;
	*x=(*in)->datanum[(*in)->top];
     return true;
}
bool Ismpty(Stack **ch){ //判空 
   
	if((*ch)->top==-1)  {
	  
	   return true;
	   }
	return false;
}
bool Ismpty_t(Stack_t **in){ 
   
	if((*in)->top==-1)  {
	  
	   return true;
	   }
	   
	return false;
}
double jc(double m){
	double sum=1;
	for(double i=2;i<=m;i++){
		sum=sum*i;
	}
	return sum;
}
double Operate(double a,char operate,double b){//二元运算 
	double sum=0;
    switch(operate)
    {
        case '+':sum=a+b; break;
        case '-':sum=a-b; break;
        case '*':sum=a*b; break;
        case '/':sum=a/b; break; 
        case '^':sum=pow(a,b);break;
        case '!':sum=jc(a);break;
    }
    return sum;
}
int getIndex(char ope){
	switch(ope){            //符号对应下标，用来确定优先级 
		case '+':return 0;
		case '-':return 1;
		case '*':return 2;
		case '/':return 3;
		case '^':return 4;
		case '!':return 5;
		case '(':return 6;
	}
}
char getPriority(int x,int y){//判断优先级 
	const char  priority[7][6]={
    {'>','>','<','<','<','<'},

    {'>','>','<','<','<','<'},

    {'>','>','>','>','<','<'},

    {'>','>','>','>','<','<'},

    {'>','>','>','>','>','<'},

    {'>','>','>','>','>','>'},

    {'<','<','<','<','<','<'}
	};
	return priority[x][y];	
}
int judgeope(char c)//判断是否为运算符 
{
	for(int i=0;i<7;i++){
		if(c==OPE[i])
		    return i;
	}
	return -1;
}
void trans(char *exp){
	int i=0;
	Stack *oper;
	Init(&oper);
	char x;
	while(*exp!='\0'){
		if(*exp=='(') { //左括号直接入栈 
		Push(&oper,*exp); 
		exp++;	
		}else if(*exp==')'){// 右括号弹栈直到'(' 
			
			pop(&oper,&x);
			
			while(x!='('){
				post[key++]=x;
				pop(&oper,&x);
			}
			exp++;
		}
		else if(judgeope(*exp)==-1) {
	     	while (*exp >='0' && *exp <='9') //循环判断是否为数字字符，如果是则保存到post，循环判断是因为可能是多位数字
                {
                    post[key++] = *exp;
                    exp++;
                }
                //以#分隔各个数字字符
				post[key++] = '#';//数字字符直接存入post数组 
		}
		
		else if(Ismpty(&oper)==true) { //为空直接字符入栈 
			        Push(&oper,*exp);
			        exp++;
			    }
		else {
				if( getTop(&oper,&x)==true){
					
					if(getPriority(getIndex(x),getIndex(*exp))=='<'){ //栈顶字符优先级小于待插字符，直接入栈 
						Push(&oper,*exp);
						exp++;
					}
					else {
						pop(&oper,&x);       //栈顶字符优先级大于待插字符，弹栈直到小于待插字符或者栈为空 
						post[key++]=x;
						continue;
						
					}
				}
			} 
		}
	if(Ismpty(&oper)==false) {       //将栈中所有字符加到post数组中去 
	    while(Ismpty(&oper)!=true){
	          pop(&oper,&x);
	          post[key++]=x;
		}
    }
    post[key]='\0';//加上字符串结束标志 
}
double Answer(char *a){
    double x,x1;
	Stack_t *inresult;
	Init_t(&inresult);
    while(*a!='\0'){
    	if(judgeope(*a)==-1){//不是运算符，转为数字然后入栈
		    double data=0;
         	while(*a>='0' && *a<='9'){
                data = data*10 + *a-'0';
                a++;
            }
			Push_t(&inresult,data);
		}
		else{
			switch(*a){
				case '!':{
				    pop_t(&inresult,&x);
				  
			    	Push_t(&inresult,Operate(x,'!',1));
			  
				    break;
				}
				case '+':{
					pop_t(&inresult,&x);
					pop_t(&inresult,&x1);
				
			    	Push_t(&inresult,Operate(x1,'+',x));
			 
					break;
				}
				case '-':{
					pop_t(&inresult,&x);
					pop_t(&inresult,&x1);
				
			    	Push_t(&inresult,Operate(x1,'-',x));
			      
					break;
				}
				case '^':{
					pop_t(&inresult,&x);
					pop_t(&inresult,&x1);
			
					Push_t(&inresult,Operate(x1,'^',x));
					break;
				}
				case '/':{
					pop_t(&inresult,&x);
					pop_t(&inresult,&x1);
			
					Push_t(&inresult,Operate(x1,'/',x));
				   
					break;
				}
				case '*':{
					pop_t(&inresult,&x);
					pop_t(&inresult,&x1);
				
					Push_t(&inresult,Operate(x1,'*',x));
			
					break;
				
				}	
			
			}
	
	   }
	    a++;
}
	getTop_t(&inresult,&x);
    return x;
}
int main(void){
	char s[100];
	gets(s);
	trans(s);
	printf("%.2f",Answer(post));
}
