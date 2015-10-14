package loj;

public class LOJ_237 
{
	public static class ListNode
	{
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}
	
    public void deleteNode(ListNode node) 
    {
    	if(node == null)			//the node is empty
    	{
    		return;
    	}
        ListNode p = node, q = p.next;
        if(q == null)					//the node is the tail node
        {
        	return;
        }
        p.val = q.val;
        p.next = q.next;
    }
    
    public static void main(String[] args)
    {
    	LOJ_237 l = new LOJ_237();
    	ListNode h = new ListNode(1), p = h, q =null;
    	
    	p.next = new ListNode(2);
    	p = p.next;
    	p.next = new ListNode(3);
    	p = p.next;
    	q = p;
    	p.next = new ListNode(4);
    	p = p.next;
    	p.next = new ListNode(5);
    	p = p.next;
    	p.next = null;
    	
    	l.deleteNode(q);
        //System.out.println(h);			//loj.LOJ_237$ListNode@a57993
    	while(h != null)
    	{
    		System.out.println(h.val);
    		h = h.next;
    	}
    }
}
