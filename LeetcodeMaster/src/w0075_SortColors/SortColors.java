package w0075_SortColors;

public class SortColors {
	/* 幫顏色排序: 紅色(0) - 白(1) - 藍(2)
	 * 不可以用 Arrays.sort()
	 */
}
class Solution {
	
	public void sortColors(int[] nums) {
		// 2-pass
		int count0 = 0;
		int count1 = 0;
		int count2 = 0;
		
		// 計數 nums Array 有幾個 0,1,2
		for (int i=0; i < nums.length; i++) {
			if (nums[i] == 0) {count0++;}
			if (nums[i] == 1) {count1++;}
			if (nums[i] == 2) {count2++;}
		}
		
		// 把count0,count1,count2 的次數賦回到 nums Array
		for (int i=0; i < nums.length; i++) {
			if (i < count0) {nums[i] = 0;}
			else if (i < count0 + count1) {nums[i] = 1;}
			else {nums[i] = 2;}
		}
	}
	
}
		