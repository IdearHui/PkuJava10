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
        ListNode curr_node=head;  //head始终是head 不能移动！！
        ListNode next_node=head.next;  
        while(next_node!=null){  //遍历所有节点
            if(curr_node.val==next_node.val){  
            	curr_node.next=next_node.next;//删除重复的节点  
            }  
            else 
            	curr_node=curr_node.next;  
            next_node=next_node.next;  
        }  
        return head;  
    }
}
