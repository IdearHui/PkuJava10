    public String intToRoman(int num) 
    {
    	String[][] roman = 		//Different bits represent different weights in Roman characters
    		{{"","I","II","III","IV","V","VI","VII","VIII","IX"},
    		 {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"},
    		 {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"},
    		 {"","M","MM","MMM"},
    		};
    	String ans = "";
    	int i,j,n;
    	for(i = 10000, j = 0;j < 4;i /= 10, j++)
    	{
    		n = (num % i) / (i / 10);		//get the most significant digit
    		ans += roman[3 - j][n];		    //add the corresponding Roman characters
    	}
    	return ans;
    }