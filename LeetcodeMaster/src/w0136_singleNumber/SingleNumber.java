package w0136_singleNumber;

public class SingleNumber {
	/* 給一個數字陣列,幾乎每個值都成對出現,只有單一個數字是自己單獨出現
	 * linear runtime complexity
	 * 
	 * XOR 的三個特性: 
	 * (1) 有互換性 commutative operation (i.e. a xor b 等於 b xor a)
	 * (2) 如果 xor 自己，只會得到 0 (i.e. a xor a = 0)
	 * (3) 零 xor 某值 必定為 某值 (i.e. 0 xor a = a)
	 * 
	 * 也就是說  a xor b xor a = a xor a xor b = 0 xor b = b.
	 */
	public int singleNumber(int[] nums) {
		int result = 0;
		for (int num : nums) {
			result ^= num;
		}
		return result;
	}
}
