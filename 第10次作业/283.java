package moveZero;

public class Solution {
	
	public void moveZeroes(int[] nums) {
		int newIndex = 0;  
        
        for (int i = 0; i < nums.length ; i++) {  
            if (nums[i] != 0) {  
                nums[newIndex++] = nums[i];  
            }  
        }  
        for (; newIndex < nums.length; newIndex++) {  
            nums[newIndex] = 0;  
        }  
    }
}
