package nimGame;

public class Solution {
	/**
	 * 基本策略：
	 * 	首先我们考察最后两步：要想最后我赢，那么最后剩下的该我选的一定是不大于3个石头
	 *  如果最后对方选的时候，剩下4个，那么我必赢
	 *  所以，总共的石子数一定是4x+k (x为整数，k∈{1，2，3})
	 *  故，总数若是4的倍数，则我必输，总数不是4的倍数，我必赢！
	 * @param n
	 * @return
	 */
	public boolean canWinNim(int n) {
        if(n%4 != 0)
            return true;
        return false;
    }
	
	public static void main(String args[]){
		Solution s = new Solution();
		System.out.println(s.canWinNim(100));
	}
}
