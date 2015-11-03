package majorityElement;

import java.util.Arrays;

public class Solution {
	public int majorityElement(int[] nums){
		int len = nums.length;
		Arrays.sort(nums);
		return nums[len/2];//根据定理，有序数组的中位数，一定是出现频率最高
	}
}
