public class Solution {
    public int addDigits(int num) {
        int sum = 0;
        if(num / 10 == 0)
            return num;
        else{
            while(num!=0){//��ÿλ֮��
                sum += num%10;
                num /= 10;
            }
        }
        //���õݹ�ķ�ʽ����ÿ����õĺ��ٽ���ͬ���ķ�ʽ���㣬ֱ���õ���λ��
        return addDigits(sum);
    }
} 