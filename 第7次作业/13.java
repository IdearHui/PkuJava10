package romanToInt;

public class Solution {
	public int romanToInt(String s) {
        char[] c = s.toCharArray();//�����ַ���ת��Ϊchar���鴦��
        /**
         * �ֳ�7�����ۣ�
         * 1����ĸM��ͷ�ģ�ǧλ��
         * 2����ĸC��ͷ���У�100��200��300��400��900
         * 3����ĸD��ͷ���У�500��600��700��800
         * 4����ĸX��ͷ���У�10��20��30��40��90
         * 5����ĸL��ͷ���У�50��60��70��80
         * 6����ĸI��ͷ�ģ�1��2��3��4��9
         * 7����ĸV��ͷ�ģ�5��6��7��8
         */
        int num = 0;//���Ҫ���������
        for(int i=0;i<c.length;i++){
        	switch(c[i]){
                case 'M': 
                	num += 1000; 
                	break;
                case 'D': 
                	num += 500; 
                	break;
                case 'C': //C��ͷҪ���Ǻ����Ƿ���D��M  ���ǣ���Ϊ��ǰ��ӹ�һ�Σ���Ҫ��������ֵ
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
                case 'X': //X��ͷҪ���Ǻ����Ƿ���L��C  ���ǣ���Ϊ��ǰ��ӹ�һ�Σ���Ҫ��������ֵ
                	if(i<c.length-1){
	                    if(c[i+1] == 'L' || c[i+1] == 'C')
	                        num -= 10;	
	                    else
	                        num += 10;
                	}else
                        num += 10;
                    break;
                case 'V': num += 5; break;
                case 'I': //I��ͷҪ���Ǻ����Ƿ���V ���ǣ���Ϊ��ǰ��ӹ�һ�Σ���Ҫ��������ֵ
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
