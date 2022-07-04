package w0066_plusOne;

public class PlusOne {
	/* 一個數字陣列, digit[i] 即為第 i 十進致的值，順序是從左至右、大到小
	 * 這個陣列不包含任何 0。幫此 digit 加一後回傳陣列
	 */
}

// Incomplete idea
class MyDumbSolution {
    public int[] plusOne(int[] digits) {
        // 找到此 digit 陣列
    	int lt = (digits.length)-1;
    	if (digits[lt] >= 0 && digits[lt] < 9) {
    		digits[lt] += 1; 
    		return digits;
    	} else {
    		digits[lt] = 0;
    		digits[lt-1] += 1; // 這裡會產生 OutofBounds Runtime Error
    		return digits;     // e.g. 十位數為 9 的狀況
    	}
    }
}

class Solution1 {
    public int[] plusOne(int[] digits) {
    	int carry = 1;
    	for (int i = digits.length-1; i>=0; i--) {
    		digits[i]+= carry; 
    		if (digits [i] <= 9) // 不用進制的話, early return
    			return digits;
    		digits[i] = 0; // 要進制的話, 先將0指派給個位數, 再往前推一個迴圈判斷是否<9
    	}
    	int[] ret = new int[digits.length+1]; 
    	ret[0] = 1;
    	return ret;
    	/* 如果此 large integer 是 9XXX,
    	 * 則進位為 1XXXX
    	 */
    }
}