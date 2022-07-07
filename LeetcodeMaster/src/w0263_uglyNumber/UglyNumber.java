package w0263_uglyNumber;


// https://leetcode.com/problems/ugly-number/description/
/* 定義: 一個 int 如果能被 2, 3, 5 整除，它就是 ugly number
 * 
 */
public class UglyNumber {
	
}
class Solution1 {
	public boolean isUgly (int num) {
		if (num <= 0) return false;
		while (num % 2 == 0) num /= 2;
		while (num % 3 == 0) num /= 3;
		while (num % 5 == 0) num /= 5;
		return num == 1;
	}
}