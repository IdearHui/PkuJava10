public class Solution {
    public String addBinary(String a, String b) {
		int length_a = a.length()-1;//字符串a的末位
		int length_b = b.length()-1;//字符串b的末位
		int temp_in = 0;//进位
		
		int maxLength = Math.max(a.length(), b.length());//两个数组中长度最大数组的长度
		char[] cc = new char[maxLength+1];//结果数组，如果运算结果的最高位比最长数组最高位大，要考虑增加数组长度
		
		StringBuilder buildera = new StringBuilder(maxLength);//新创建一个字符串，用于扩充短的字符串a
		StringBuilder builderb = new StringBuilder(maxLength);//新创建一个字符串，用于扩充短的字符串b
		if(length_a > length_b){
			for(int i=0;i<(length_a-length_b);i++){
				builderb.append("0");
			}
//			builderb = builderb.append(b);
		}else{
			for(int i=0;i<(length_b-length_a);i++){
				buildera.append("0");
			}
//			buildera = buildera.append(a);
		}
		buildera = buildera.append(a);
		builderb = builderb.append(b);
		//把aa、bb两个字符串转换成char数组
		char[] aa = buildera.toString().toCharArray();
		char[] bb = builderb.toString().toCharArray();
		
		for(int i=0;i<maxLength;i++){
//			int aint = aa[i] - '0';
//			int bint = bb[i] - '0';
			int cint = (aa[maxLength-i - 1] - '0') + (bb[maxLength-i - 1] - '0') + temp_in;//两个数末位以及进位相加
//			System.out.println(cint);
			if(cint == 0){
				cc[maxLength-i] = '0';
				temp_in = 0;
			}else if(cint == 1){
				cc[maxLength-i] = '1';
				temp_in = 0;
			}else if((cint == 2) && (temp_in == 0)){
				cc[maxLength-i] = '0';
				temp_in = 1;
			}else if((cint == 2) && (temp_in == 1)){
				cc[maxLength-i] = '0';
			}else if(cint == 3){
				cc[maxLength-i] = '1';
				temp_in = 1;
			}
		}	
		String cString;
		if(temp_in == 1){
			cc[0] = '1';
			cString = String.copyValueOf(cc);//将数组转换成字符串
		}
		else{
			char[] ccc = new char[maxLength];
			for(int i=0;i<maxLength;i++){
				ccc[i]=cc[i+1];
			}
			cString = String.copyValueOf(ccc);//将数组转换成字符串
		}
		
//		System.out.println(cString);
		return cString;
	}
}