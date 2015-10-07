public class Solution {
    public int myAtoi(String str){
        if(str == null || str.equals("") || str.equals("null"))//若字符串为空 则返回0
        	return 0;
        else{
        	
        	str = str.trim();//去掉字符串前面的空格
        	/*考虑以下几种情况：
        	 * 1.字符串里面数字前面有正负号，则要保留正负号
        	 * 2.字符串数字中间有非数字的字符 则不计字符及其之后的任何字符及数字
        	 * 3.字符串中字符是否溢出 溢出则返回最大(小)范围
        	 */
        	char sig = '+';//数字前面的符号
        	double value = 0; //字符串中的数值
        	for(int i=0;i<str.length();i++){
//        		if(str.charAt(0) == '-')
//        			sig = '-';
//        		else if(str.charAt(0) == '+')
//        			sig = '+';
        		//判断是否有正负号
        		if(str.charAt(i) == '-' || str.charAt(i) == '+'){
        			if(i == 0){
        				if(str.charAt(i) == '-')
        					sig = '-';
        			}else
        				return 0;
        		}
        		
        		else{
        			//System.out.println((str.charAt(i)>='0' && str.charAt(i)<='9')?"aa":"bb");
	        		//若是数字 则记下 不是则break
	        		if(str.charAt(i)>='0' && str.charAt(i)<='9')
	        			value = value*10 +str.charAt(i) - '0';
	        		else
	        			break;
        		}
        	}
        	
        	//填上正负号 ＋不输出 -则加在数字
        	if(sig == '-'){
        		value = -value;
//        		return value;
        	}
        	//处理溢出的情况
        	if(value > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
        	else if(value < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;
        	else
        		return (int)value;
        }
    }    
}