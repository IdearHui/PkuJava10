package majorityElement;

import java.util.Arrays;

public class Solution {
	public int majorityElement(int[] nums){
		int len = nums.length;
		Arrays.sort(nums);
		return nums[len/2];//���ݶ��������������λ����һ���ǳ���Ƶ�����
	}
}
