package binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreorderTra {
	public List<Integer> preorderTraversal(TreeNode root) {
		//用ArrayList模拟栈，top为栈指针，访问顺序根左右
		List<Integer> result = new ArrayList<Integer>();	//保存输出结果
        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();	//建立一个栈
        int top = -1; //栈为空
        if(root == null) return result; //树为空，返回空
        TreeNode curNode = root;	//初始化指向根节点
        while(true) {
        	if(curNode != null) {
        		result.add(curNode.val);	//访问根节点
        	}
            if(curNode.right != null) { 	//右孩子不空则进栈
                stack.add(curNode.right);
                top++;
            }
            if(curNode.left != null) {	//左孩子不空则进栈
            	stack.add(curNode.left);
                top++;
            }
            if(top > -1) { //栈内还有元素，栈顶元素出栈
            	curNode = stack.get(top);
            	stack.remove(top--);
            }
            else break; //栈内元素空了，遍历结束
        }
        return result;
	}
}
