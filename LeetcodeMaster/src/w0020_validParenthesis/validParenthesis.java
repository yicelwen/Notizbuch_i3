package w0020_validParenthesis;

import java.util.Stack;
import java.util.regex.Pattern;

public class validParenthesis {
	/**
	 * 判斷參數 string 裡面的小/中/大括號是否有效。因素：
	 *   1. opening 跟 closing 需要為同一種括號
	 *   2. 順序要正確: 先開的最後關
	 * 提示二: 可以在遇到成對的括號後把它 remove 掉，縮減 expression 大小
	 * 提示三：Stack 堆疊資料結構 可以幫助我們使用遞迴的方式(從外部到裡面) 處理問題
	 */
}

	
	class Solution1 {
		// 此方法無法找出向這樣的括號組 [([([([([()])])])])]
		public boolean isValid(String s) {
			int length;
			do {
				length = s.length();
				s = s.replace("()", "").replace("{}", "").replace("[]", "");
			} while (length != s.length());
			return s.isEmpty();
		}
		
		// 改用 Pattern 的 matcher 方法
		private static final Pattern PARENS = Pattern.compile("\\(\\)|\\[\\]|\\{\\}");
		
		public boolean isValid2 (String s) {
			int length;
			do {
				length = s.length();
				s = PARENS.matcher(s).replaceAll("");
			} while (length != s.length());
			
			return s.isEmpty();
		}
	}
	
	class Solution2 {
		public boolean isValid(String s) {
			Stack<Character> stack = new Stack<>();
			for (char c : s.toCharArray()) {   // 遍歷 s 字串陣列
				if (c == '(' || c == '[' || c == '{') 
					stack.push(c);        // 如果找到 ( 或者 [ 或者 { 的話, 把它放到 stack 堆疊裡面
				else {
					if (stack.isEmpty())  // 如果此堆疊沒有任何括號, 回傳 false/無效括號
						return false;
					if (c == ')') {       // 如果有遍歷到 closing 小括號卻沒有opening, 也回傳 false
						if ('(' != stack.pop())                 // pop方法相當於 peek and remove
							return false;
					} else if (c == ']') {
						if ('[' != stack.pop())
							return false;
					} else if (c == '}') {
						if ('{' != stack.pop())
							return false;
					}
				}
			}
			return stack.isEmpty();  
			// 有丟括號到堆疊的話就會是true, 
			// 有抓到 closing parenthesis 卻沒有 opening 的話就會是 false
		}
	}
	
