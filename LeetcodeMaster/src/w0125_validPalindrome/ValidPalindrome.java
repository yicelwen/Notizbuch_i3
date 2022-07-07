package w0125_validPalindrome;

public class ValidPalindrome {
	/* Palindrome 定義: 全部小寫, 只有英文數字(不能有符號), 從前面讀跟從後面讀一樣
	 */
}

class Solution1 {
    public boolean isPalindrome(String s) {
        if (s.isEmpty()) {
        	return true; // 如果為空字串 直接回傳true
        }
        int head = 0;
        int tail = s.length()-1; 
        char cHead; // default u0000
        char cTail;
        
        while (head <= tail) {
        	cHead = s.charAt(head);
        	cTail = s.charAt(tail);
        	// 若首字不是英數字, 則往下個索引字符迴圈
        	if (!Character.isLetterOrDigit(cHead)) {
        		head++;
        	// 若最後一字元不是英數字, 則往前一個索引字符迴圈
        	} else if (!Character.isLetterOrDigit(cTail)) {
        		tail--;
        	} else {
        		// 如果不等於,判定此 string 並非回文
        		if (Character.toLowerCase(cHead) != Character.toLowerCase(cTail)) {
        			return false;
        		}
        		head++;
        		tail--;
        	}
        }
        return true;
    }
}

class Solution2 {
	public boolean isPalindrome(String s) {
				// [^  ] 取反值: A-Z a-Z 0-9 以外的所有字，都抽掉; 改小寫
		String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
		// StringBuffer: 類似String，但可以被修改，執行緒安全的
		String rev = new StringBuffer(actual).reverse().toString();
		// 若 rev 值等於 actual，回傳 true
		return actual.equals(rev);
	}
}




