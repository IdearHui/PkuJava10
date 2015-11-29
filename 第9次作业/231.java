package q_231;

public class Solution {
	//2╣дцщ╠ьсп (x&(x-1))==0
	public boolean isPowerOfTwo(int n) {
		if(n <= 0)
            return false;
        if((n&(n-1))==0)
        	return true;
        return false;
    }
	public static void main(String[] args) {
		Solution s = new Solution();
		System.out.println(s.isPowerOfTwo(5));
	}

}
