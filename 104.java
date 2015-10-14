package loj;

public class LOJ_104 
{
	public static  class TreeNode
	{
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
	
    public int maxDepth(TreeNode root) 
    {
    	if(root == null)
    	{
    		return 0;
    	}
    	int leftDepth = 0, rightDepth = 0;
        if(root.left == null && root.right == null)
        {
        	return 1;
        }
        if(root.left != null)
        {
        	 leftDepth = maxDepth(root.left);
        }
        else
        {
        	leftDepth = 0;
        }
        if(root.right != null)
        {
        	rightDepth = maxDepth(root.right);
        }
        else
        {
        	rightDepth = 0;
        }
        return leftDepth > rightDepth? leftDepth + 1: rightDepth + 1;
    }
    
    public static void main(String[] args)
    {
    	LOJ_104 l = new LOJ_104();
    	TreeNode tn = new TreeNode(1), ln = tn, rn = tn;
    	
    	ln.left = new TreeNode(2);
    	ln = ln.left;
    	rn.right = new TreeNode(3);
    	rn = rn.right;
    	
    	ln.left = new TreeNode(4);
    	ln.right = new TreeNode(5);
    	ln = ln.left;
    	
    	ln.left = new TreeNode(6);
    	ln = ln.left;
    	
    	System.out.println(l.maxDepth(tn));
    }
}
