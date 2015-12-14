package validAnagram;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Solution {

	public boolean isAnagram(String s, String t) {
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        
        Arrays.sort(ss);
        Arrays.sort(tt);
        
        return String.valueOf(ss).equals(String.valueOf(tt));
    }
	public static void main(String[] args) {
		Solution s = new Solution();
		System.out.println(s.isAnagram("idearhui", "huidear"));
	}

}
