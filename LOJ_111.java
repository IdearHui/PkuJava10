package binaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class minDepth {	
	//����BFS��˼�룬��α���������ĳ���ҵ�Ҷ�ӽڵ�ʱ����ǰ����Ϊ��С��ȣ���������
	public int minDepth(TreeNode root) {		
		if (root == null) {			
			return 0;		
		} //�������Ϊ0
		Queue<TreeNode>currentLevel = new LinkedList<TreeNode>();	//����һ�����д����һ��Ľڵ�
		Queue<TreeNode>nextLevel = new LinkedList<TreeNode>();	//����һ�����д�ŵ�ǰ�ڵ����һ��
		int level = 1;	//ֻ�и��ڵ�Ļ��������СΪ1
		currentLevel.add(root);		//���ڵ������
		while (true) {	//һֱ����
			TreeNode node = currentLevel.poll();	//��ȡ������Ԫ�ز����������
			if (node.left == null && node.right == null) {	//���Һ���Ϊ�գ���ΪҶ�ӽڵ�	
				return level;	//�ҵ�Ҷ�ӽڵ��ˣ�ֱ�ӷ��ص�ǰ���
			}
			if (node.left != null) {	//���Ӳ��գ�����Ҷ�ӽڵ㣬����
				nextLevel.add(node.left);			
			}
			if (node.right != null) {	//�Һ��Ӳ��գ�����Ҷ�ӽڵ㣬����
				nextLevel.add(node.right);
			}
			if (currentLevel.isEmpty()) {	//��һ��������ˣ�����������һ�����������������1
				Queue<TreeNode>temp = currentLevel;
				currentLevel = nextLevel;
				nextLevel = temp;
				level++;
			}		
		}	
	}
}
