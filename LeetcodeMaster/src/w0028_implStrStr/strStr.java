package w0028_implStrStr;

public class strStr {
	/* 兩個字串，變數名分別為 needle，haystack
	 * 如果 needle 在 haystack 字串中，回傳陣列索引位置
	 * 如果 needle 不是 haystack 字串一部分，回傳 -1
	 * 如果 needle 是空字串，回傳 0
	 * 
	 * C : strstr()  |  Java : indexOf()
	 */
}

class Solution1 {
    public int strStr(String haystack, String needle) {
        int hayl = haystack.length();
        int neel = needle.length();
        if (hayl < neel) {
        	return -1;
        } else if (neel == 0) {
        	return 0;
        }
        int threshold = hayl - neel;
        for (int i = 0; i <= threshold; ++i) {
        	if (haystack.substring(i,i+neel).equals(needle)) {
        		return i;
        	}
        }
        return -1;
    }
}

/* KMP字串尋找演算法: 可以在一個字串 S 內尋找一個字 W 的出現位置
 * 一個詞在不匹配時，本身就包含足夠資訊來確定下個匹配可能的開始位置
 */
class Solution2 {
	private int[] failureFunction(char[] str) {
		int[] f = new int[str.length+1];
		for (int i = 2; i < f.length; i++) {
			int j = f[i-1];
			while (j > 0 && str[j] != str[i-1]) j = f[j];
			if (j > 0 || str[j] == str[i-1]) f[i] = j+1;
		}
		return f;
	}
	public int strStr(String haystack, String needle) {
		if (needle.length() == 0) return 0;
		if (needle.length() <= haystack.length()) {
			int[] f = failureFunction(needle.toCharArray());
			int i = 0;
			int j = 0;
			while (i < haystack.length()) {
				if (haystack.charAt(i) == needle.charAt(j)) {
					i++;
					j++;
					if (j == needle.length()) return i-j;
				} else if (j > 0) j = f[j];
				else i++;
			}
		}
		return -1;
	}
}

class Solution3 {
	public int strStr(String haystack, String needle) {
		for (int i = 0; ; i++) {
			for (int j = 0; ; j++) {
		      if (j == needle.length()) return i;
		      if (i + j == haystack.length()) return -1;
		      if (needle.charAt(j) != haystack.charAt(i + j)) break;
		    }
		  }
	}
}