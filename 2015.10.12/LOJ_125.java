public boolean judge(char c)
	{
		return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
	}
	
    public boolean isPalindrome(String s) 
    {
    	if(s == null || s.length() == 0)
    	{
    		return true;
    	}
    	boolean ans = true;
        s = s.toLowerCase();
//        String str = "";
//        for(int i = 0; i <= s.length() - 1; i++)		//in this way we'll get the result "Time Limit Exceeded"
//        {
//        	if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
//        	{
//        		str += s.charAt(i);
//        	}
//        }
        int p = 0, q = s.length() - 1;
        while(p < q)
        {
        	while(!judge(s.charAt(p)) && p < q)
        	{ p++; }
        	while(!judge(s.charAt(q)) && p < q)
        	{ q--; }
        	if(p < q)
        	{
            	if(s.charAt(p) == s.charAt(q))
            	{
            		p++;
            		q--;
            	}
            	else
            	{
            		ans = false;
            		break;
            	}
        	}
        }
        return ans;
    }
