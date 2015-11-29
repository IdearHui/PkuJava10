package q_171;

public class Solution {

	public int titleToNumber(String s) {
		int a = 0;
        for(int i=0;i<s.length();i++){
        	a = a*26 + (s.charAt(i)-'A'+1);
        }
        return a;
    }
	public static void main(String[] args) {
		Solution s = new Solution();
		System.out.println(s.titleToNumber("AZ"));
	}

}
