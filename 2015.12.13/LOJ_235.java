	
	public TreeNode[] postorderTraversal(TreeNode root, TreeNode t)
	{
		if(root == null)
		{
			return null;
		}
		TreeNode p = root;
		TreeNode[] Stack1 = new TreeNode[10000];
		int flag = 0, top = -1;
		int[] Stack2 = new int[10000];
		
		while(p != null || top != -1)
		{
			while(p != null)
			{
				Stack1[++top] = p;
				Stack2[top] = 0;
				p = p.left;
			}
			p = Stack1[top];
			flag = Stack2[top--];
			if(flag == 0)
			{
				Stack1[++top] = p;
				Stack2[top] = 1;
				p = p.right;
			}
			else
			{
				if(p == t)
				{
					//return Stack1;
					break;
				}
				p = null;
			}
		}
		return Stack1;
	}
	
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) 
    {
        TreeNode[] s1 = postorderTraversal(root, p);
        TreeNode[] s2 = postorderTraversal(root, q);
        TreeNode ans = null;
        
        int l_p = 1, l_q = 1;
        
        for(int i = 0; s1[i] != p; i++)
        {
        	l_p++;
        }
        for(int i = 0; s2[i] != q; i++)
        {
        	l_q++;
        }
        ok:
        for(int i = l_p - 1; i >= 0 ; i--)
        {
        	for(int j = l_q - 1; j >= 0; j--)
        	{
        		if(s1[i].val == s2[j].val)
        		{
        			ans = s1[i];
        			break ok;
        		}
        	}
        }
        return ans;
    }