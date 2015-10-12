public boolean isPalindrome(ListNode head) 
	    {
	    	boolean ans = true;
	    	int n = 0, cnt = 0;
	    	ListNode h = new ListNode(-1);
	    	h.next = head;
	    	ListNode p = h, q = h, r = head, s = head;
	    	while(q.next != null)
	    	{
	    		n++;
	    		q = q.next;
	    	}
	    	if(n <= 1)
	    	{
	    		return true;
	    	}
	    	cnt = n / 2 ;
	        while((cnt--) > 0)
	        {
	        	p = p.next;
	        }
//	        if(n % 2 == 0)			//even number
//	        {
//	        	q = p.next;
//	        }
//	        else							//odd number
//	        {
//	        	q = p.next.next;
//	        }
	        q = p.next;
	        h.next = null;
	        while(s != q)			//last night
	        {
	        	s = s.next;
	        	r.next = h.next;
	        	h.next = r;
	        	r = s;
	        }
	        if(n % 2 == 1)		//odd number
	        {
	        	q = q.next;
	        }
	        while(p != null && q != null)
	        {
	        	if(p.val == q.val)
	        	{
	        		p= p.next;
	        	    q = q.next;
	        	}
	        	else
	        	{
	        		ans = false;
	        		break;
	        	}
	        }
	        return ans;
	    }
