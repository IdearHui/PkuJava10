    public boolean isAnagram(String s, String t) 
    {
    	if(s == null && t == null)
    	{
    		return true;
    	}
    	else if(s == null && t != null)
    	{
    		return false;
    	}
    	else if(s != null && t == null)
    	{
    		return false;
    	}
    	else
    	{
        	char[] a = s.toCharArray();
        	char[] b = t.toCharArray();
            Arrays.sort(a);
            Arrays.sort(b);
            //String s1 = a.toString();
            //String t1 = b.toString();
            String s1 = String.valueOf(a);
            String t1 = String.valueOf(b);
            if(s1.equals(t1))
            {
            	return true;
            }
            else
            {
            	return false;
            }
    	}
    }