	int cnt1 = 0, cnt2 = 0;
	int[] a = new int[10000], b = new int[10000];
	//root->left->right
	public void DFS1(TreeNode root)
	{
		if(root != null)
		{
			a[cnt1++] = root.val;
			DFS1(root.left);
			DFS1(root.right);
		}
		else
		{
			a[cnt1++] = -1;
		}
	}
	//root->right->left
	public void DFS2(TreeNode root)
	{
		if(root != null)
		{
			b[cnt2++] = root.val;
			DFS2(root.right);
			DFS2(root.left);
		}
		else
		{
			b[cnt2++] = -1;
		}
	}
	
    public boolean isSymmetric(TreeNode root) 
    {
    	boolean flag = true;
    	//exceptional case: 
        if(root == null)
        {
        	return true;
        }
        //DFS
        DFS1(root);
        DFS2(root);
        //judge if array a is equal to array b
        for(int i = 0; i <= cnt1 - 1; i++)
        {
        	if(a[i] != b[i])
        	{
        		flag = false;
        		break;
        	}
        }
        //return the result
        return flag;
    }