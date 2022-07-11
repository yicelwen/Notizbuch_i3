package w0268_missingNumber;

public class MissingNum {
	
	public int missingNumber(int[] nums) {
		int sum = 0;
		
		// 迭代陣列加總
		for (int num : nums)
			sum += num;
		
		// 底乘高除以二 扣掉 sum，算少了哪個數字
		return (nums.length * (nums.length+1)) / 2 - sum;
	}
}
