package linkedListCycle;

public class Solution {
	class ListNode{
		int val;
		ListNode next;
		ListNode(int x){
			val = x;
			next = null;
		}
	}
	
	public boolean hasCycle(ListNode head){
		if(head == null)
			return false;
		ListNode p1 = head;
		ListNode p2 = head;
		
		do{
			//若p1的下一个节点、p2的后两个节点都非空，则p1走一步，p2走两步
			if(p1.next != null && p2.next != null && p2.next.next != null){
				p1 = p1.next;
				p2 = p2.next.next;
			}
			else
				return false;
		}while(p1 != p2);//在这里使用do...while循环因为初始就要先走一步才能进入判断，否则这里的循环永远也不进去
		if(p1 == p2)
			return true;
		return false;
	}
}
