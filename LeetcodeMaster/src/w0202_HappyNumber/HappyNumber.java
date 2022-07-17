package w0202_HappyNumber;

public class HappyNumber {

}

class Solution {
	public boolean isHappy(int n) {
		int s = n;  // slow
		int f = n;  // fast
		do {
			s = compute(s); // slow computes only once
			f = compute(compute(f)); // fast computes two times
			
			if (s == 1) return true; // if we found 1 then it's HAPPY
		} while (s!=f);
		 // else at some point they will meet in the cycle
		
		return false; // it's a cycle (NOT HAPPY)
	}
	
	public int compute (int n) {
		int s = 0;
		while (n != 0) {
			s += (n%10)*(n%10);
			n = n/10;
		}
		return s;
	}
}
