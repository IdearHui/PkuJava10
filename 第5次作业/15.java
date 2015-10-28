package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> list = new ArrayList<List<Integer>>();
    	Arrays.sort(nums);//对数组nums中的元素排序
    	/**
    	 * 首先，对数组排序
    	 * 然后，三个数相加，第一个数固定，第二个数从左至右遍历，第三个数从右至左遍历
    	 * 若三数相加大于0 则第三个数左移，若和小于零 则第二个数右移
    	 * 保证第二个数的下标始终小于第三个数下标
    	 */
    	int len = nums.length -1;
    	for(int i=0;i<nums.length-1 && i<len;i++){
    		int j = i+1;
    		len = nums.length-1;
    		while((i==0 || nums[i]!= nums[i-1])&&j<len){
    			if(nums[i]+nums[j]+nums[len]>0){
    				len--;
    			}
    			if(nums[i]+nums[j]+nums[len]<0)
    				j++;
    			if(nums[i]+nums[j]+nums[len]==0){
    				if(!list.contains(Arrays.asList(nums[i],nums[j],nums[len]))&&(j!=len))
    					list.add(Arrays.asList(nums[i],nums[j],nums[len]));
    				j++;
    				len--;
    			}
    		}
    	}
    	return list;
    }
}
