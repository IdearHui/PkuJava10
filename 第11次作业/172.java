public int trailingZeroes(int n) {
	if(n == 0){
		return 0;
	}
	if(n < 0){
		n = -n;
	}
	int ans = 0;
	while(n >= 5){
		ans += n / 5;
		n /= 5;
	}
	return ans;
}