package w0283_ModeZeros;

public class MoveZeros {
	/* 給定一個數字陣列，把所有 0 值移到尾端，其它數值順序不變 */
	public void moveZeroes(int[] nums) {
		
		int j = 0;
		
		for(int i = 0; i < nums.length; i++) {
			if (nums[i] != 0) {
				int temp = nums[j];
				nums[j] = nums[i];
				nums[i] = temp;
				j++;
			}
		}
	}
}
