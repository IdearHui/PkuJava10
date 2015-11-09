package removeDuplicates;

public class Solution {

	public class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}
	
	public ListNode deleteDuplicates(ListNode head) {
        if(head==null||head.next==null)  
            return head;  
        ListNode curr_node=head;  //headʼ����head �����ƶ�����
        ListNode next_node=head.next;  
        while(next_node!=null){  //�������нڵ�
            if(curr_node.val==next_node.val){  
            	curr_node.next=next_node.next;//ɾ���ظ��Ľڵ�  
            }  
            else 
            	curr_node=curr_node.next;  
            next_node=next_node.next;  
        }  
        return head;  
    }
}
