package w0326_powerOfThree;

public class PowerOfThree {
	/* Power of three 代表提供的 int n 是 三的 x 次方
	 * 
	 */
}

// Loop Iteration
class Solution1 {
    public boolean isPowerOfThree(int n) {
        // 判斷 int n 是否為0或負數
    	if (n < 1) {
        	return false;
        }
        
    	// 如果 n 可以被 3 整除，則 n = n/3
        while (n % 3 == 0) {
        	n /= 3;
        }
        
        return n == 1;
    }
}