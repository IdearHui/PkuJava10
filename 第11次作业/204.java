public int countPrimes(int n){
	n--;
	if(n <= 1){
		return 0;
	}
	
	boolean[] isprime = new boolean[n + 1];
	int cnt_prime = 0;
	for(int i = 0; i <= n; i++){
		isprime[i] = true;
	}
	isprime[0] = false;
	isprime[1] = false;
	for(int i = 2; i<= n; i++){
		if(isprime[i]){
			for(int j = i + i; j <= n; j += i){
				isprime[j] = false;
			}
		}
	}
	for(int i = 0; i <= n; i++){
		if(isprime[i]){
			cnt_prime++;
		}
	}
	return cnt_prime;
}