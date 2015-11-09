package romanToInt;

public class Solution {
	public int romanToInt(String s) {
        char[] c = s.toCharArray();//将该字符串转化为char数组处理
        /**
         * 分成7类讨论：
         * 1，字母M开头的，千位数
         * 2，字母C开头的有：100，200，300，400，900
         * 3，字母D开头的有：500，600，700，800
         * 4，字母X开头的有：10，20，30，40，90
         * 5，字母L开头的有：50，60，70，80
         * 6，字母I开头的：1，2，3，4，9
         * 7，字母V开头的：5，6，7，8
         */
        int num = 0;//最后要输出的数字
        for(int i=0;i<c.length;i++){
        	switch(c[i]){
                case 'M': 
                	num += 1000; 
                	break;
                case 'D': 
                	num += 500; 
                	break;
                case 'C': //C开头要考虑后面是否是D或M  若是，因为在前面加过一次，则要减掉部分值
                	if(i<c.length-1){
	                    if(c[i+1] == 'D' || c[i+1] == 'M')
	                        num -= 100;
	                    else
	                        num += 100;
                	}else
                        num += 100;
                    break;
                case 'L': 
                	num += 50; 
                	break;
                case 'X': //X开头要考虑后面是否是L或C  若是，因为在前面加过一次，则要减掉部分值
                	if(i<c.length-1){
	                    if(c[i+1] == 'L' || c[i+1] == 'C')
	                        num -= 10;	
	                    else
	                        num += 10;
                	}else
                        num += 10;
                    break;
                case 'V': num += 5; break;
                case 'I': //I开头要考虑后面是否是V 若是，因为在前面加过一次，则要减掉部分值
                	if(i<c.length-1){
	                    if(c[i+1] == 'V' || c[i+1] == 'X')
	                        num--;	
	                    else
	                        num ++;
                	}else
                        num ++;
                    break;
            }
        }
        return num;
    }
	public static void main(String args[]){
		Solution s = new Solution();
		System.out.println(s.romanToInt("DCXXI"));
	}
}
