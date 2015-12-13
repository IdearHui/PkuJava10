    public TreeNode invertTree(TreeNode root) 
    {
        if(root == null)
        {
        	return root;
        }
        else
        {
        	if(root.left != null || root.right != null)
        	{
            	TreeNode tmp = root.left;
            	root.left = root.right;
            	root.right = tmp;
            	if(root.left != null)
            	{
            		invertTree(root.left); 
            	}
            	if(root.right != null)
            	{
            		invertTree(root.right);
            	}
        	}
        	return root;
        }
    }