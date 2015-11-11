package binaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class minDepth {	
	//采用BFS的思想，层次遍历，当在某层找到叶子节点时，则当前层数为最小深度，结束遍历
	public int minDepth(TreeNode root) {		
		if (root == null) {			
			return 0;		
		} //空树深度为0
		Queue<TreeNode>currentLevel = new LinkedList<TreeNode>();	//创建一个队列存放这一层的节点
		Queue<TreeNode>nextLevel = new LinkedList<TreeNode>();	//创建一个队列存放当前节点的下一层
		int level = 1;	//只有根节点的话，深度最小为1
		currentLevel.add(root);		//根节点入队列
		while (true) {	//一直遍历
			TreeNode node = currentLevel.poll();	//获取队列首元素并让其出队列
			if (node.left == null && node.right == null) {	//左右孩子为空，则为叶子节点	
				return level;	//找到叶子节点了，直接返回当前深度
			}
			if (node.left != null) {	//左孩子不空，不是叶子节点，往下
				nextLevel.add(node.left);			
			}
			if (node.right != null) {	//右孩子不空，不是叶子节点，往下
				nextLevel.add(node.right);
			}
			if (currentLevel.isEmpty()) {	//这一层遍历完了，交换，往下一层继续遍历，层数加1
				Queue<TreeNode>temp = currentLevel;
				currentLevel = nextLevel;
				nextLevel = temp;
				level++;
			}		
		}	
	}
}
