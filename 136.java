package loj;
import java.util.Arrays;

public class LOJ_136 
{
    public int singleNumber(int[] nums) 
    {
    	int len = nums.length, ans = -1;
    	if(len == 0)
    	{
    		return 0;
    	}
    	else if(len == 1)
    	{
    		return nums[0];
    	}
    	else
    	{
        	//Arrays.sort(nums, 0, nums.length - 1);
    		Arrays.sort(nums);
        	for(int i = 0; i <= len - 1;)
        	{
        		if(i + 1 <= len - 1 && nums[i] == nums[i + 1])
        		{
        			i += 2;
        		}
        		else
        		{
        			ans = nums[i];
        			break;
        		}
        	}
        	return ans;
    	}
    }
    
    public static void main(String[] args)
    {
    	//Test
    	//System.out.println("111");
    	//{1, 1, 2, 2, 3, 3, 4, 5, 5};
    	//{1, 5, 3, 2, 4, 3, 2, 1, 5};
    	//{2,2,1};
    	int[] a = {17, 12, 5, -6, 12, 4, 17, -5, 2, -3, 2, 4, 5, 16, -3, -4, 15, 15, -4, -5, -6};
    	LOJ_136 l = new LOJ_136();
    	System.out.println(l.singleNumber(a));
    }
}
