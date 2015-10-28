package binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreorderTra {
	public List<Integer> preorderTraversal(TreeNode root) {
		//��ArrayListģ��ջ��topΪջָ�룬����˳�������
		List<Integer> result = new ArrayList<Integer>();	//����������
        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();	//����һ��ջ
        int top = -1; //ջΪ��
        if(root == null) return result; //��Ϊ�գ����ؿ�
        TreeNode curNode = root;	//��ʼ��ָ����ڵ�
        while(true) {
        	if(curNode != null) {
        		result.add(curNode.val);	//���ʸ��ڵ�
        	}
            if(curNode.right != null) { 	//�Һ��Ӳ������ջ
                stack.add(curNode.right);
                top++;
            }
            if(curNode.left != null) {	//���Ӳ������ջ
            	stack.add(curNode.left);
                top++;
            }
            if(top > -1) { //ջ�ڻ���Ԫ�أ�ջ��Ԫ�س�ջ
            	curNode = stack.get(top);
            	stack.remove(top--);
            }
            else break; //ջ��Ԫ�ؿ��ˣ���������
        }
        return result;
	}
}
