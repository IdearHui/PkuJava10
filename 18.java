package loj;
import java.util.List;		//List
import java.util.ArrayList;	//ArrayList
import java.util.Arrays;	//Arrays.sort()

public class LOJ_18 
{
    public List<List<Integer>> fourSum(int[] nums, int target) 
    {
    	List<List<Integer>> ans = new ArrayList<List<Integer>>();	//restore the total result
    	if(nums.length < 4)		//judge if the number is less than 4
    	{
    		return ans;
    	}
    	Arrays.sort(nums);		//sort from small to large
    	int left = -1, middle = -1, right = -1;		//declare three pointers
    	for(int i = 0; i <= nums.length - 4;)
    	{
    		left = i + 1;
    		//right = nums.length - 1;
    		while(left <= nums.length - 3)	//loop condition1
    		{
    			middle = left + 1;
    			right = nums.length - 1;
    			while(middle < right)		//loop condition2
    			{
    				int sum = nums[i] + nums[left] + nums[middle] + nums[right];
    				if(sum == target)
    				{
    					List<Integer> tmp = new ArrayList<Integer>();		//restore the temporary result
    					tmp.add(nums[i]);
    					tmp.add(nums[left]);
    					tmp.add(nums[middle]);
    					tmp.add(nums[right]);
    					ans.add(tmp);
    					middle++;
    					right--;
    					while(middle < right && nums[middle] == nums[middle - 1])	//for the middle repeat digits, pruning2
    					{
    						middle++;
    					}
    					while(middle < right && nums[right] == nums[right + 1])		//for the right repeat digits, pruning3
    					{
    						right--;
    					}
    				}
    				else if(sum < target)
    				{
    					middle ++;
    					while(middle < right && nums[middle] == nums[middle - 1])
    					{
    						middle++;
    					}
    				}
    				else
    				{
    					right--;
    					while(middle < right && nums[right] == nums[right + 1])
    					{
    						right--;
    					}
    				}
    			}
    			left++;
    			while(left < middle && nums[left] == nums[left - 1])		//for the left repeat digits, pruning1
    			{
    				left++;
    			}
    		}
    		i++;
    		while(i <= nums.length - 4 && nums[i] == nums[i - 1])			//for the start repeat digits, pruning0
    		{
    			i++;
    		}
    	}
        return ans;
    }
    
    public static void main(String[] args)
    {
    	int s[] = {1, 0, -1, 0, -2, 2};
    	LOJ_18 l = new LOJ_18();
    	List<List<Integer>> a = l.fourSum(s, 0);
    	for(int i = 0; i <= a.size() - 1; i++)
    	{
    		System.out.println(a.get(i));
    	}
    }
}
