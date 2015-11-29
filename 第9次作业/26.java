package q_26;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	public int removeDuplicates(int[] nums) {
		int count = 1;//最后得到的数组长度
		if(nums.length == 0)
			return 0;
        for(int i=1;i<nums.length;i++){
        	if(nums[i]!=nums[i-1])
        		nums[count++] = nums[i];//动态改变数组长度
        }
        return count;
    }
	
	public static void main(String args[]){
		Solution s = new Solution();
		int[] n = {1,2,2,4};
		System.out.println(s.removeDuplicates(n));
	}
}
