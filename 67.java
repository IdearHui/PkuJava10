public class Solution {
    public String addBinary(String a, String b) {
		int length_a = a.length()-1;//�ַ���a��ĩλ
		int length_b = b.length()-1;//�ַ���b��ĩλ
		int temp_in = 0;//��λ
		
		int maxLength = Math.max(a.length(), b.length());//���������г����������ĳ���
		char[] cc = new char[maxLength+1];//������飬��������������λ����������λ��Ҫ�����������鳤��
		
		StringBuilder buildera = new StringBuilder(maxLength);//�´���һ���ַ�������������̵��ַ���a
		StringBuilder builderb = new StringBuilder(maxLength);//�´���һ���ַ�������������̵��ַ���b
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
		//��aa��bb�����ַ���ת����char����
		char[] aa = buildera.toString().toCharArray();
		char[] bb = builderb.toString().toCharArray();
		
		for(int i=0;i<maxLength;i++){
//			int aint = aa[i] - '0';
//			int bint = bb[i] - '0';
			int cint = (aa[maxLength-i - 1] - '0') + (bb[maxLength-i - 1] - '0') + temp_in;//������ĩλ�Լ���λ���
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
			cString = String.copyValueOf(cc);//������ת�����ַ���
		}
		else{
			char[] ccc = new char[maxLength];
			for(int i=0;i<maxLength;i++){
				ccc[i]=cc[i+1];
			}
			cString = String.copyValueOf(ccc);//������ת�����ַ���
		}
		
//		System.out.println(cString);
		return cString;
	}
}