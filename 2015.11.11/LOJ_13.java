    public int romanToInt(String s) 
    {
    	Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
    	ht.put("I", 1);
    	ht.put("V", 5);
    	ht.put("X", 10);
    	ht.put("L", 50);
    	ht.put("C", 100);
    	ht.put("D", 500);
    	ht.put("M", 1000);
    	int cnt = s.length(), ans = 0, flag = 1;
    	
    	for(int i = cnt - 1; i >= 1; i-= 1)
    	{
    		int t1 = ht.get(String.valueOf(s.charAt(i - 1))), t2 = ht.get(String.valueOf(s.charAt(i)));
    		ans += flag * t2;
    		if(t1 < t2)
    		{ flag = -1; }
    		else
    		{ flag = 1; }
    	}
    	ans += flag * ht.get(String.valueOf(s.charAt(0)));
    	return ans;
    }