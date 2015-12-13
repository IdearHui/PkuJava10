public class Solution 
{
    public int titleToNumber(String s) 
    {
    	if(s.length() == 0 || s == null)
    	{
    		return 0;
    	}
    	
    	int len = s.length(), weight = 1, ans = 0;
    	//reverse()
        StringBuffer sb = new StringBuffer(s);
        s = sb.reverse().toString();
        ans += s.charAt(0) - 'A' + 1;
        //add the digit * weight
        for(int i = 1; i <= len - 1; i++)
        {
        	weight *= 26;
        	ans += (s.charAt(i) - 'A' + 1) * weight;
        }
        return ans;
    }
}