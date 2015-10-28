package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> list = new ArrayList<List<Integer>>();
    	Arrays.sort(nums);//������nums�е�Ԫ������
    	/**
    	 * ���ȣ�����������
    	 * Ȼ����������ӣ���һ�����̶����ڶ������������ұ������������������������
    	 * ��������Ӵ���0 ������������ƣ�����С���� ��ڶ���������
    	 * ��֤�ڶ��������±�ʼ��С�ڵ��������±�
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
