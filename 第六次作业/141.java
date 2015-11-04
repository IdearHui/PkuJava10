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
			//��p1����һ���ڵ㡢p2�ĺ������ڵ㶼�ǿգ���p1��һ����p2������
			if(p1.next != null && p2.next != null && p2.next.next != null){
				p1 = p1.next;
				p2 = p2.next.next;
			}
			else
				return false;
		}while(p1 != p2);//������ʹ��do...whileѭ����Ϊ��ʼ��Ҫ����һ�����ܽ����жϣ����������ѭ����ԶҲ����ȥ
		if(p1 == p2)
			return true;
		return false;
	}
}
