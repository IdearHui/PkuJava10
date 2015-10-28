package leetcode.minDepth;

import java.util.ArrayList;
import java.util.List;

public class Solution {

	public class TreeNode {
	    int val;
	    TreeNode left;
	    TreeNode right;
	    TreeNode(int x) { val = x; }
	}
	public int minDepth(TreeNode root) {
        if(root==null)
            return 0;
        List<TreeNode> list = new ArrayList<>();
        int depth = 1;//���������
        
        list.add(root);
        while(list.size()!=0){//�ǿ���
        	List<TreeNode> lista = new ArrayList<>();//���ĳһ�����֧���ӽڵ�
            for(TreeNode t:list){
                if(t.left != null)
                	lista.add(t.left);
                if(t.right != null)
                	lista.add(t.right);
                if(t.left == null && t.right == null)
                	return depth;
            }
            depth++;//ÿ����һ�� ���+1
            list = lista;
        }
        return 0;
    }
}
