import java.util.HashSet;
public class Solution {
    public boolean isHappy(int n) {
//		int a = 0;
		int a;
		if(n == 1)
			return true;
		else{
//			int num = String.valueOf(n).length();
//			for(int i=0;i<num;i++){//将整数转化为字符串，遍历其每一位
//				//System.out.println(n);
//				/*if(n/10 !=0){
//					a = n/10;
//					a *= a;//得到a²
//				}else{
//					b = n%10;
//					b*=b;//得到b²
//				}
//				b = n % 10;
//				b *= b; //得到b²*/
//				int a = 0;
//				while(n != 0){
//					a += (n%10)*(n%10);
////					if(n/10 != 0)
//						n /= 10;
////					else
////						continue;
//				}
//				//System.out.println(i);
//				n = a;//将得到的平方和重新赋给n
//				if(a == 1)
//					break;
//				else{
//					if(i != num)
//						num+=1;
//					else
//						break;
//				}
//			}
//			if(n == 1)
//				return true;
//			else
//				return false;
			// 用于保存中间出现的结果
	        HashSet<Integer> set = new HashSet<>(32);
	        // n不为1，并且n的值不能重复出现，否则会死循环
	        while (n != 1 && !set.contains(n)) {
	            set.add(n);
	            a = 0;   //每次重新把a赋值给n后  a都要清零！！！
	            while (n > 0) {
	                a += (n % 10)*(n % 10);
	                n /= 10;
	            }
	            n = a;//将得到的平方和重新赋给n
	        }
	        return n == 1;
		}
	}
}