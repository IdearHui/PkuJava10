    public ListNode deleteDuplicates(ListNode head) 
    {
    	if(head == null)
    	{
    		return null;
    	}
        ListNode p = head, q = head.next;
        while(p != null && q != null)
        {
        	if(p.val == q.val)
        	{
        		p.next = q.next;
        		q = p;
        		if(q != null)
        		{
        			q = q.next;
        		}
        	}
        	else
        	{
        		p = p.next;
        		q = q.next;
        	}
        }
        return head;
    }