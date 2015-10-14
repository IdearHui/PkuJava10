public class Solution {
    public int lengthOfLastWord(String s) {
		char[] c = s.toCharArray();
		int count = 0;
		for(int i=s.length()-1;i>=0;i--){
			if((c[i] >= 65 && c[i] <= 90) || (c[i] >= 97 && c[i] <= 122)){
				count++;
				if(i != 0){
					if(c[i-1]==32)
						break;
				}
				else if(i==0)
					break;
			}
		}
		return count;
	}
}