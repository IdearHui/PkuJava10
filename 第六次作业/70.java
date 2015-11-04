package climbStairs;

public class Solution {
	public int climbStairs(int n){
		if(n==0 || n==1 || n==2)//初始极端情况
			return n;
		int[] stair = new int[n+1];//创建一个数组用于存放楼梯数
		stair[0] = 0;
		stair[1] = 1;
		stair[2] = 2;//初始值
		
		for(int i=3;i<n+1;i++){
			stair[n] = stair[n-1]+stair[n-2];
			//最后一步要么是只剩一级台阶，则只需要一步，要么是两级台阶，则需要两步
		}
		return stair[n];//采用递归的方式求解
	}
}
