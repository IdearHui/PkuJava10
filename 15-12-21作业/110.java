	public int DFS(TreeNode root) 
	{
		if (root == null) 
		{
			return 0;
		}
		int l = DFS(root.left);
		int r = DFS(root.right);
		if(l == -1 || r == -1 || Math.abs(l - r) > 1)
		{
			return -1;
		}
		else
		{
			return l > r ? l + 1 : r + 1;
		}
	}

	public boolean isBalanced(TreeNode root) 
	{
		if(DFS(root) >= 0)
		{
			return true;
		}
		return false;
	}