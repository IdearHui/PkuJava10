	//Time Limit Exceeded
//    public int trailingZeroes(int n) 
//    {
//    	//exceptional case: 
//    	if(n == 0)
//    	{
//    		return 0;
//    	}
//    	if(n < 0)
//    	{
//    		n = -n;
//    	}
//    	//counters for divisor 5
//    	int cnt_5 = 0;
//    	Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
//    	//input 5, 10, 15, 20, ... into the hash table
//    	for(int i = 5; i <= n; i += 5)
//    	{
//    		ht.put(i, i);
//    	}
//    	//traverse the hash table
//    	Set<Integer> keys = ht.keySet();
//    	for(Integer key: keys)
//    	{
//    		int tmp = ht.get(key);
//    		while(tmp % 5 == 0)
//    		{
//    			tmp /= 5;
//    			cnt_5++;
//    		}
//    	}
//    	//return the result
//    	return cnt_5;
//    }
    
	public int trailingZeroes(int n) 
	{
		//exceptional case: 
		if(n == 0)
		{
			return 0;
		}
		if(n < 0)
		{
			n = -n;
		}
		//define the answer variable
		int ans = 0;
		//count the number of the divisor 5
		while(n >= 5)
		{
			ans += n / 5;
			n /= 5;
		}
		//return the answer
		return ans;
	}