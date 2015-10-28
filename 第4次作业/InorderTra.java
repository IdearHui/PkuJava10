package binaryTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InorderTra {
	public List<Integer> inorderTraversal(TreeNode root) {
		//用ArrayList模拟栈，top为栈指针，访问顺序左根右
		List<Integer> result = new ArrayList<Integer>();	//保存输出结果
        ArrayList<TreeNode> stack = new ArrayList<TreeNode>();	//建立一个栈
        int top = -1; //栈为空
        TreeNode curNode = root;	//初始化指向根节点
        while(curNode != null || top > -1) {	//栈为空时遍历完毕
            if(curNode != null) { 	//找到最左的节点，并将遇到的节点都入栈
                stack.add(curNode);
                top++;
                curNode = curNode.left;
            }
            else {	//继续遍历右子数
                curNode = stack.get(top); //指向栈顶元素
                stack.remove(top--); 	//退栈
                result.add(curNode.val);	//访问根节点
                curNode = curNode.right;	//遍历右子树
            }   
        }
        return result;
	}
}
