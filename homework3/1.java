public class Solution {
    public int[] twoSum(int[] nums, int target) {
		int[] index = new int[2];//定义一个数组 用于存放返回的两个数字
		for(int i=0;i<nums.length;i++){
			int j=i+1;//保证每次j都从i后面一位开始
			for(;j<nums.length;j++){
				if(nums[i]+nums[j] == target){
					index[0] = i+1;
					index[1] = j+1;
					break;
				}
			}
			//每进行完一轮后 第二个数的下标不为0 则说明已经找到该数对 则停止循环
			if(index[1] != 0)
				break;
		}
		return index;
	}
}