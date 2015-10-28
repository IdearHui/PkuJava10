package binaryTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InorderTra {
	public List<Integer> inorderTraversal(TreeNode root) {
		//��ArrayListģ��ջ��topΪջָ�룬����˳�������
		List<Integer> result = new ArrayList<Integer>();	//����������
        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();	//����һ��ջ
        int top = -1; //ջΪ��
        TreeNode curNode = root;	//��ʼ��ָ����ڵ�
        while(curNode != null || top > -1) {	//ջΪ��ʱ�������
            if(curNode != null) { 	//�ҵ�����Ľڵ㣬���������Ľڵ㶼��ջ
                stack.add(curNode);
                top++;
                curNode = curNode.left;
            }
            else {	//��������������
                curNode = stack.get(top); //ָ��ջ��Ԫ��
                stack.remove(top--); 	//��ջ
                result.add(curNode.val);	//���ʸ��ڵ�
                curNode = curNode.right;	//����������
            }   
        }
        return result;
	}
}
