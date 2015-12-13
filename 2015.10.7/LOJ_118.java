    public List<List<Integer>> generate(int numRows) 
    {
    	List<List<Integer>> l = new ArrayList<List<Integer>>();
    	List<Integer> l1 = new ArrayList<Integer>();
    	List<Integer> l2 = new ArrayList<Integer>();
    	l1.add(1);
    	l2.add(1);
    	l2.add(1);
    	if(numRows < 1)
    	{
    		return l;		//return null; is a wrong answer
    	}
    	l.add(l1);
    	if(numRows == 1)
    	{
    		return l;
    	}
    	l.add(l2);
    	if(numRows == 2)
    	{
    		return l;
    	}
    	for(int i = 2; i <= numRows - 1; i++)
    	{
    		List<Integer> tmpl = new ArrayList<Integer>();
    		tmpl.add(1);
    		for(int j = 0; j <= i - 2; j++)
    		{
    			tmpl.add(l.get(i - 1).get(j) + l.get(i - 1).get(j + 1));
    		}
    		tmpl.add(1);
    		l.add(tmpl);
    	}
    	return l;
    }