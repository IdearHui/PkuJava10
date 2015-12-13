import java.util.Hashtable;

public class Solution 
{
    public boolean containsDuplicate(int[] nums) 
    {
        Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
        boolean flag = false;
        
        for(int i = 0; i <= nums.length - 1; i++)
        {
        	Integer temp = ht.get(nums[i]);
        	if(temp == null)
        	{
        		ht.put(nums[i], i);
        	}
        	else
        	{
        		flag = true;
        		break;
        	}
        }
        return flag;
    }
}