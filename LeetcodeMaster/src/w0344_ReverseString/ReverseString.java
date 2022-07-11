package w0344_ReverseString;

import java.util.Stack;

public class ReverseString {
}
class SolutionTwoPointers {
	public void reverseString(char[] s) {
		int i = 0;
		int j = s.length - 1;
		
		while(i <= j) {
			char temp = s[i];  // 把前面索引值丟到空箱子
			s[i] = s[j]; // 後面索引值放進前面索引坑
			s[j] = temp; // 空箱內前面索引值放進後面索引坑
			i++;
			j--;
		}
	}
}

class SolutionRecursive {
	public void reverseString(char[] s) {
		int i = 0;
		int j = s.length - 1;
		solve(s, i, j);
	}
	public void solve(char[] s, int i, int j) {
		if (i >= j) return;
		char temp = s[i];
		s[i] = s[j];
		s[j] = temp;
		solve(s, ++i, --j);
	}
}

class SolutionStack {
	public void reverseString(char[] s) {
		Stack<Character> st = new Stack<>();
		String str = new String(s);
		for (int i = 0; i < str.length(); i++) {
			st.push(s[i]);
		}
		char ans[] = new char[s.length];
		int i = 0;
		while (st.size() > 0) {
			s[i++] = st.pop();
		}
		for (int j = 0; j < str.length(); j++) {
			ans[j] = str.charAt(j);
		}
	}
}


