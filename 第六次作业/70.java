package climbStairs;

public class Solution {
	public int climbStairs(int n){
		if(n==0 || n==1 || n==2)//��ʼ�������
			return n;
		int[] stair = new int[n+1];//����һ���������ڴ��¥����
		stair[0] = 0;
		stair[1] = 1;
		stair[2] = 2;//��ʼֵ
		
		for(int i=3;i<n+1;i++){
			stair[n] = stair[n-1]+stair[n-2];
			//���һ��Ҫô��ֻʣһ��̨�ף���ֻ��Ҫһ����Ҫô������̨�ף�����Ҫ����
		}
		return stair[n];//���õݹ�ķ�ʽ���
	}
}
