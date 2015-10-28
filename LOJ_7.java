// public int reverse(int x) 
    // {
    // 	int sign = 1, ans = 0;
    // 	if(x > 0)
    // 	{
    // 		sign = 1;
    // 	}
    // 	else
    // 	{
    // 		sign = -1;
    // 	}
    // 	String str = String.valueOf(x);
    // 	str = str.replace("-","");
    // 	for(int i = str.length() - 1; i >= 0 ; i--)
    // 	{
    // 		ans = ans * 10 + (str.charAt(i) - '0');
    // 	}
    // 	return sign * ans;
    // }
	
    public int reverse(int x) 
    {
    	long t = x;			//we should use long instead of integer because of the overflow problem
    	if(t > Integer.MAX_VALUE || t < Integer.MIN_VALUE)		//judge if the value of long type is over the range of minimum - maximum or not
    	{
    		return 0;
    	}
    	int sign = 1;
    	long ans = 0;
    	if(t > 0)
    	{
    		sign = 1;
    	}
    	else
    	{
    		sign = -1;
    	}
    	String str = String.valueOf(x);
    	str = str.replace("-","");
    	for(int i = str.length() - 1; i >= 0 ; i--)
    	{
    		ans = ans * 10 + (str.charAt(i) - '0');
    	}
    	if(ans > Integer.MAX_VALUE || ans < Integer.MIN_VALUE)		//after reversing, judge it again
    	{
    		return 0;
    	}
    	return Integer.parseInt(String.valueOf(sign * ans));
    }
    