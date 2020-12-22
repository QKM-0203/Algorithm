/*利用归并排序链表
public ListNode sortList(ListNode head){
        if(head==null||head.next==null){
        return head;
        }
        ListNode midList=MidList(head);
        ListNode right=midList.next;
        midList.next=null;

        ListNode le=sortList(head);
        ListNode ri=sortList(right);
        return Mergesort(le,ri);

//找到中间的那个结点，利用快慢指针法，2倍的关系
private ListNode MidList(ListNode head){
        if(head==null||head.next==null){
        return head;
        }
        ListNode slow,fast;
        slow=head;
        fast=head.next;
        while(fast!=null&&fast.next!=null){
        slow=slow.next;
        fast=fast.next.next;
        }
        return slow;
        }
//合并两个有序的链表
private ListNode Mergesort(ListNode l1,ListNode l2){
        ListNode listnode=new ListNode(-1);
        ListNode p=listnode;
        while(l1!=null&&l2!=null){
        if(l1.val<l2.val){
        listnode.next=l1;
        l1=l1.next;
        }else{
        listnode.next=l2;
        l2=l2.next;
        }
        listnode=listnode.next;
        }
        listnode.next=l1==null?l2:l1;
        return p.next;
        }
        }*/
