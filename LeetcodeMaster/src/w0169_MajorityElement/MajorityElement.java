package w0169_MajorityElement;

public class MajorityElement {
/* 給定一個 int[] nums，Array 大小為 n，找出陣列中出現次數 > (n/2) 的元素
 * 
 * Approachs:
 * 1. Brute force
 * 2. Hashmap
 * 3. Sorting
 * 4. Randomization
 * 5. Divide and conquer
 * 6. Boyer-moore voting algorithm
 * 
 * https://leetcode.com/problems/majority-element/solution/
 */
}
class Solution {
	public int majorityElement(int[] nums) {
		int majorityCount = nums.length/2;
		
		for (int num : nums) {
			int count = 0;
			for (int elem : nums) {
				if (elem == num) {
					count += 1;
				}
			}
			
			if (count > majorityCount) {
				return num;
			}
		}
		
		return -1;
	}
}
