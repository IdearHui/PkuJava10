public class Solution {
    /* �㷨������
	 * ���÷��η�������������kΪ�ָ��ֳ�����������
	 * �ֱ�����߲�ȡͬ���ķ�ʽ�����������ת��Ȼ������ý���ٽ��кϲ���ת�õ����ս��
	 */
	public void rotate(int[] nums,int k){
		int len = nums.length;
		k %= len;//��֤k��lenС
		if(len > 0 && k > 0){//���õݹ�ķ�ʽ��ÿ����������зֱ���ת
			nums = turnArray(nums,0,len-k-1);
			nums = turnArray(nums,len-k,len-1);
			nums = turnArray(nums,0,len-1);
		}
	}
	
	public int[] turnArray(int[] arr,int start,int end){//�ݹ麯��
//		for(int i=0;i<=end;i++){
//			for(int j=end;j>=0;j--){
		int i = start,j = end;
		while(i<=end && j>=start){//������˫��ѭ����˫��ѭ������Զ�ά���飬�˴���һά����
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