//	public String reverse(String s)
//	{
//		char[] a = s.toCharArray();
//		for(int i = 0; i <= a.length / 2 - 1; i++)
//		{
//			char t = a[i];
//			a[i] = a[a.length - 1 - i];
//			a[a.length - 1 - i] = t;
//		}
//		//return a.toString(); it is a wrong answer
//		return String.valueOf(a);
//	}
	
    public String reverseWords(String s) 
    {
    	String[] str = s.split(" ");
    	String ans = "";
    	for(int i = 0; i <= str.length / 2 - 1; i++)
    	{
    		String tmps = str[i];
    		str[i] = str[str.length - 1 - i];
    		str[str.length -1 - i] = tmps;
    	}
    	for(int i = 0; i <= str.length - 1; i++)
    	{
    		if(i >= 1 && str[i].length() > 0)		//str[i].length() > 0 It's a important condition
    		{
    			ans += " ";
    		}
    		ans += str[i];
    	}
    	return ans;
    }