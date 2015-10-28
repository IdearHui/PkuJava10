public class Solution {
    public int addDigits(int num) {
        int sum = 0;
        if(num / 10 == 0)
            return num;
        else{
            while(num!=0){//求每位之和
                sum += num%10;
                num /= 10;
            }
        }
        //采用递归的方式，把每次求得的和再进行同样的方式计算，直到得到个位数
        return addDigits(sum);
    }
} 