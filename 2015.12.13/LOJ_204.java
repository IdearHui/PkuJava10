	//Time Limit Exceeded
//	public boolean isPrime(int n)
//	{
//		boolean flag = true;
//		for(int i = 2; i <= Math.sqrt(n); i++)
//		{
//			if(n % i == 0)
//			{
//				flag = false;
//				break;
//			}
//		}
//		return flag;
//	}
//	
//    public int countPrimes(int n) 
//    {
//    	int cnt_prime = 0;
//    	for(int i = 2; i <= n; i++)
//    	{
//    		if(isPrime(i))
//    		{
//    			cnt_prime++;
//    		}
//    	}
//    	return cnt_prime;
//    }
    
    //sieve of Eratosthenes
	public int countPrimes(int n)
	{
		//less than n
		n--;
		//exceptional case:  
		if(n <= 1)
		{
			return 0;
		}
		
		boolean[] isprime = new boolean[n + 1];
		int cnt_prime = 0;
		//initialization
		for(int i = 0; i <= n; i++)
		{
			isprime[i] = true;
		}
		isprime[0] = false;
		isprime[1] = false;
		//make the prime table
		for(int i = 2; i<= n; i++)
		{
			if(isprime[i])
			{
				for(int j = i + i; j <= n; j += i)
				{
					isprime[j] = false;
				}
			}
		}
		//count the prime
		for(int i = 0; i <= n; i++)
		{
			if(isprime[i])
			{
				cnt_prime++;
			}
		}
		return cnt_prime;
	}