package nimGame;

public class Solution {
	/**
	 * �������ԣ�
	 * 	�������ǿ������������Ҫ�������Ӯ����ô���ʣ�µĸ���ѡ��һ���ǲ�����3��ʯͷ
	 *  ������Է�ѡ��ʱ��ʣ��4������ô�ұ�Ӯ
	 *  ���ԣ��ܹ���ʯ����һ����4x+k (xΪ������k��{1��2��3})
	 *  �ʣ���������4�ı��������ұ��䣬��������4�ı������ұ�Ӯ��
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
