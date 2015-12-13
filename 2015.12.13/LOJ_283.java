    public void moveZeroes(int[] nums) 
    {
    	int cnt_zero = 0, l_nums = nums.length, l_tmp = l_nums;
    	for(int i = 0 ; i <= l_nums - 1; i++)
    	{
    		if(nums[i] == 0)
    		{
    			cnt_zero ++;
    		}
    	}
    	for(int i = 0; i <= cnt_zero - 1; i++)
    	{
    		for(int j = 0; j <= l_tmp - 1; j++)
    		{
    			if(nums[j] == 0)
    			{
    				for(int k = j + 1; k <= l_tmp - 1; k++)
    				{
    					nums[k - 1] = nums[k];
    				}
    				l_tmp--;
    				break;
    			}
    		}
    	}
    	for(int i = l_nums - cnt_zero; i <= l_nums - 1; i++)
    	{
    		nums[i] = 0;
    	}
    }