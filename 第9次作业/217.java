package q_217;

import java.util.HashSet;
import java.util.Set;

public class Solution {

	public boolean containsDuplicate(int[] nums) {
		Set<Integer> s = new HashSet<Integer>();  //使用hashset处理
	    for(int i = 0; i < nums.length; i++){  
	        if(!s.contains(nums[i])){  
	            s.add(nums[i]);  
	        } else 
	        	return true;  
	    }  
	    return false;  
    }
	public static void main(String args[]){
		Solution s = new Solution();
		int[] n = {1,2,2,3};
		System.out.println(s.containsDuplicate(n));
	}

}
