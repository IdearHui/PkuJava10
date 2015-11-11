package loj;
import java.util.List;			//List
import java.util.ArrayList;		//ArrayList
import java.util.Arrays;		//Arrays.sort()

public class LOJ_15 
{
    public List<List<Integer>> threeSum(int[] nums) 
    {
    	List<List<Integer>> ans = new ArrayList<List<Integer>>();	//restore the total result
    	if(nums.length < 3)				//judge if the number is less than 3
    	{
    		return ans;
    	}
    	int left = -1, right = -1;		//declare two pointers left and right
    	Arrays.sort(nums);
    	for(int i = 0; i <= nums.length - 1;)
    	{
    		left = i + 1;
    		right = nums.length - 1;
    		while(left < right)			//loop condition
    		{
    			int sum = nums[i] + nums[left] + nums[right];
    			if(sum == 0)
    			{
    				ArrayList<Integer> tmp = new ArrayList<Integer>();		//restore the temporary result
    				tmp.add(nums[i]);
    				tmp.add(nums[left]);
    				tmp.add(nums[right]);
    				ans.add(tmp);
    				left++;
    				right--;
    				while(left < right && nums[left] == nums[left - 1])		//for the left repeat digits, pruning1
    				{
    					left++;
    				}
    				while(left < right && nums[right] == nums[right + 1])   //for the right repeat digits, pruning2
    				{
    					right--;
    				}
    			}
    			else if(sum < 0)
    			{
    				left++;
    				while(left < right && nums[left] == nums[left - 1])     //for the left repeat digits, pruning1
    				{
    					left++;
    				}
    			}
    			else
    			{
    				right--;
    				while(left < right && nums[right] == nums[right + 1])	//for the right repeat digits, pruning2
    				{
    					right--;
    				}
    			}
    		}
    		i++;
    		while(i <= nums.length - 1 && nums[i] == nums[i - 1])			//for the right repeat digits, pruning3
    		{
    			i++;
    		}
    	}
        return ans;
    }
    
    public static void main(String[] args)
    {
    	int s[] = {-1,0,1,2,-1,-4};
    	LOJ_15 l = new LOJ_15();
    	List<List<Integer>> a = l.threeSum(s);
    	for(int i = 0; i <= a.size() - 1; i++)
    	{
    		System.out.println(a.get(i));
    	}
    }
}
