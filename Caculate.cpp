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
void Init(Stack **ch){  //��ʼ�� 
	*ch=(Stack*)malloc(sizeof(Stack));
	(*ch)->top=-1;
   
}
void Init_t(Stack_t **in){
	*in=(Stack_t*)malloc(sizeof(Stack_t));
	(*in)->top=-1;
   
}
bool Push(Stack **ch,char x){	//��ջ 
	if((*ch)->top==MAXSIZE-1) return false;
	(*ch)->data[++(*ch)->top]=x;

	return true;
}
bool Push_t(Stack_t **in,double x){	
	if((*in)->top==MAXSIZE-1) return false;
	(*in)->datanum[++(*in)->top]=x;

	return true;
}
bool pop(Stack **ch,char *x){//��ջ 
	if((*ch)->top==-1) return false;
	*x=(*ch)->data[(*ch)->top--];
	return true;
}
bool pop_t(Stack_t **in,double *x){
	if((*in)->top==-1) return false;
	*x=(*in)->datanum[(*in)->top--];
	return true;
}
bool getTop(Stack **ch,char *x){//��ȡջ��Ԫ�� 
	if((*ch)->top==-1) return false;
	*x=(*ch)->data[(*ch)->top];
     return true;
}
bool getTop_t(Stack_t **in,double *x){
	if((*in)->top==-1) return false;
	*x=(*in)->datanum[(*in)->top];
     return true;
}
bool Ismpty(Stack **ch){ //�п� 
   
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
double Operate(double a,char operate,double b){//��Ԫ���� 
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
	switch(ope){            //���Ŷ�Ӧ�±꣬����ȷ�����ȼ� 
		case '+':return 0;
		case '-':return 1;
		case '*':return 2;
		case '/':return 3;
		case '^':return 4;
		case '!':return 5;
		case '(':return 6;
	}
}
char getPriority(int x,int y){//�ж����ȼ� 
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
int judgeope(char c)//�ж��Ƿ�Ϊ����� 
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
		if(*exp=='(') { //������ֱ����ջ 
		Push(&oper,*exp); 
		exp++;	
		}else if(*exp==')'){// �����ŵ�ջֱ��'(' 
			
			pop(&oper,&x);
			
			while(x!='('){
				post[key++]=x;
				pop(&oper,&x);
			}
			exp++;
		}
		else if(judgeope(*exp)==-1) {
	     	while (*exp >='0' && *exp <='9') //ѭ���ж��Ƿ�Ϊ�����ַ���������򱣴浽post��ѭ���ж�����Ϊ�����Ƕ�λ����
                {
                    post[key++] = *exp;
                    exp++;
                }
                //��#�ָ����������ַ�
				post[key++] = '#';//�����ַ�ֱ�Ӵ���post���� 
		}
		
		else if(Ismpty(&oper)==true) { //Ϊ��ֱ���ַ���ջ 
			        Push(&oper,*exp);
			        exp++;
			    }
		else {
				if( getTop(&oper,&x)==true){
					
					if(getPriority(getIndex(x),getIndex(*exp))=='<'){ //ջ���ַ����ȼ�С�ڴ����ַ���ֱ����ջ 
						Push(&oper,*exp);
						exp++;
					}
					else {
						pop(&oper,&x);       //ջ���ַ����ȼ����ڴ����ַ�����ջֱ��С�ڴ����ַ�����ջΪ�� 
						post[key++]=x;
						continue;
						
					}
				}
			} 
		}
	if(Ismpty(&oper)==false) {       //��ջ�������ַ��ӵ�post������ȥ 
	    while(Ismpty(&oper)!=true){
	          pop(&oper,&x);
	          post[key++]=x;
		}
    }
    post[key]='\0';//�����ַ���������־ 
}
double Answer(char *a){
    double x,x1;
	Stack_t *inresult;
	Init_t(&inresult);
    while(*a!='\0'){
    	if(judgeope(*a)==-1){//�����������תΪ����Ȼ����ջ
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
