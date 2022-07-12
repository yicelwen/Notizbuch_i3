package w0387_firstUniCharInAString;

public class FirstUniCharInAString {
	// 找字串中只出現過一次字母的索引位置
}

class Solution {
	public int firstUniqChar(String s) {
		
		for (char c : s.toCharArray()) {
			// 如果首次出現的索引等於最後一次出現的索引,就回傳該索引位置
			int index = s.indexOf(c);			
			int lastIndex = s.lastIndexOf(c);
			if(index == lastIndex)
				return index;
		}
		return -1;
	}
}
