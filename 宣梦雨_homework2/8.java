public class Solution {
    public int myAtoi(String str){
        if(str == null || str.equals("") || str.equals("null"))//���ַ���Ϊ�� �򷵻�0
        	return 0;
        else{
        	
        	str = str.trim();//ȥ���ַ���ǰ��Ŀո�
        	/*�������¼��������
        	 * 1.�ַ�����������ǰ���������ţ���Ҫ����������
        	 * 2.�ַ��������м��з����ֵ��ַ� �򲻼��ַ�����֮����κ��ַ�������
        	 * 3.�ַ������ַ��Ƿ���� ����򷵻����(С)��Χ
        	 */
        	char sig = '+';//����ǰ��ķ���
        	double value = 0; //�ַ����е���ֵ
        	for(int i=0;i<str.length();i++){
//        		if(str.charAt(0) == '-')
//        			sig = '-';
//        		else if(str.charAt(0) == '+')
//        			sig = '+';
        		//�ж��Ƿ���������
        		if(str.charAt(i) == '-' || str.charAt(i) == '+'){
        			if(i == 0){
        				if(str.charAt(i) == '-')
        					sig = '-';
        			}else
        				return 0;
        		}
        		
        		else{
        			//System.out.println((str.charAt(i)>='0' && str.charAt(i)<='9')?"aa":"bb");
	        		//�������� ����� ������break
	        		if(str.charAt(i)>='0' && str.charAt(i)<='9')
	        			value = value*10 +str.charAt(i) - '0';
	        		else
	        			break;
        		}
        	}
        	
        	//���������� ������� -���������
        	if(sig == '-'){
        		value = -value;
//        		return value;
        	}
        	//������������
        	if(value > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
        	else if(value < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;
        	else
        		return (int)value;
        }
    }    
}