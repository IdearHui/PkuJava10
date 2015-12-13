public class Solution 
{
    public int maxProfit(int[] prices) 
    {
    	if(prices.length == 0 || prices == null)
    	{
    		return 0;
    	}
//This is a wrong method, because I didn't consider that buying is always earlier than selling or on the same day. 
//    	int max = prices[0], min = prices[0];
//    	for(int i = 1; i <= prices.length - 1; i++)
//    	{
//    		if(prices[i] > max)
//    		{
//    			max = prices[i];
//    		}
//    		if(prices[i] < min)
//    		{
//    			min = prices[i];
//    		}
//    	}
//    	return max - min;
        //Dynamic Programming
    	int min = prices[0], ans = 0;
    	for(int i = 1; i <= prices.length - 1; i++)
    	{
    		if(prices[i] < min)
    		{
    			min = prices[i];
    		}
    		if(prices[i] - min > ans)
    		{
    			ans = prices[i] - min;
    		}
    	}
    	return ans;
    }
}