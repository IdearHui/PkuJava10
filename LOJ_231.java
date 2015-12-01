public class Solution 
{
    public boolean isPowerOfTwo(int n) 
    {
        //It is a important judge
    	if(n <= 0)
    	{
    		return false;
    	}
    	
    	boolean flag = true;
    	
        while(n > 1)
        {
        	if(n % 2 == 0)
        	{
        		n /= 2;
        	}
        	else
        	{
        		flag = false;
        		break;
        	}
        }
        
        return flag;
    }
}