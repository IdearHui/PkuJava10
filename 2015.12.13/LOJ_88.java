    public void merge(int[] nums1, int m, int[] nums2, int n) 
    {
    	int l_1 = m, pre = 0;
    	
        for(int i = 0; i <= n - 1; i++)
        {
        	boolean flag = false;
        	for(int j = pre; j <= l_1 - 1; j++)
        	{
        		if(nums1[j] > nums2[i])
        		{
        			for(int k = l_1 - 1; k >= j ; k--)
        			{
        				nums1[k + 1] = nums1[k];
        			}
        			nums1[j] = nums2[i];
        			flag = true;
        			pre = j;
        			break;
        		}
        	}
        	if(!flag)
        	{
        		nums1[l_1] = nums2[i];
        		pre = l_1;
        	}
        	l_1++;
        }
    }