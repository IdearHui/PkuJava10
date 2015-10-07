public class Solution {
    /* 算法剖析：
	 * 采用分治法，将该数组以k为分隔分成左右两部分
	 * 分别对两边采取同样的方式尽心数组的旋转，然后把所得结果再进行合并旋转得到最终结果
	 */
	public void rotate(int[] nums,int k){
		int len = nums.length;
		k %= len;//保证k比len小
		if(len > 0 && k > 0){//采用递归的方式对每部分数组进行分别旋转
			nums = turnArray(nums,0,len-k-1);
			nums = turnArray(nums,len-k,len-1);
			nums = turnArray(nums,0,len-1);
		}
	}
	
	public int[] turnArray(int[] arr,int start,int end){//递归函数
//		for(int i=0;i<=end;i++){
//			for(int j=end;j>=0;j--){
		int i = start,j = end;
		while(i<=end && j>=start){//不能用双层循环，双层循环是针对二维数组，此处是一维数组
			if(i >= j)
				break;
			else{
				int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
			i++;
			j--;
		}
//			}
//		}
		return arr;
	}
}