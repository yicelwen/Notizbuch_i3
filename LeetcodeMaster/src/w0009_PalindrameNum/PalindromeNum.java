package w0009_PalindrameNum;

public class PalindromeNum {
	/**
	 * 參數 int x，如果 x 是回文的話，回傳 true
	 * 回文定義：從左邊開始讀 vs 從右邊開始讀，得到一樣的值
	 * e.g. input x = 121  |  output: true
	 * e.g#2. input x = -121  |  output: false
	 * 		=> -121 倒著讀是 121-
	 * Hint:
	 *     注意倒轉 int 可能有溢位問題(overflow)
	 * 類似題: # 234 回文鏈表
	 *        # 2217 找固定長度的回文 
	 **/
}

class Solution1 {
    public boolean isPalindrome(int x) {
        String k = String.valueOf(x);
        
        // FOR迴圈：從字串第0個字符開始拜訪, 執行總字串/2次4
        for(int i=0; i<k.length()/2; i++) {
        	
        	// IF判斷：如果字串第i列的值不等於 第(總長度-1-i)，那就回傳 FALSE
        	if (k.charAt(i) != k.charAt(k.length()-1-i)) {
        		return false;
        	}
        }
        return true;
    }
}
    
 class Solution2 {
        public boolean isPalindrome(int x) {
        	// 先排除負數 或者 此數值為10的倍數的情況
        	if(x<0 || (x != 0 && x%10==0))  
        		return false;
        	
        	// 
        	int res = 0;
        		while (x > res) {
        			res = res * 10 + x % 10;
        			x = x/10;
        		}
        	
        	// `x == res/10` 是當 int x 為奇數位數的時候所使用(i.e. 45654)
        	return (x==res || x == res/10);
        }
}