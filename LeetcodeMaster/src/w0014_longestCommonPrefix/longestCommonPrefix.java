package w0014_longestCommonPrefix;

import java.util.Arrays;

public class longestCommonPrefix {

	/**
	 * Write a function to find the longest common prefix string amongst an array of strings.
	 * If there is no common prefix, return an empty string "".
	 */
	class Solution1{
		public String longestCommonPrefix(String[] strs) {
			if (strs == null || strs.length == 0)  // 如果字串為 null 值或者 字串長度為零
				return "";                        
			
			Arrays.sort(strs); 						  
			String first = strs[0];                // e.g. "f"light
			String last = strs[strs.length - 1];   // e.g. fligh"t"
			int c = 0; 
			while(c < first.length())     
			{
				if (first.charAt(c) == last.charAt(c))
					c++;
				else
					break;
			}
			return c == 0 ? "" : first.substring(0, c);
			
		}
	}
	
	/**
	 * (1) 選 array 中第一個 string (index=0) 作為前綴字
	 * (2) 從陣列第二個(index=1)字母開始遍歷
	 * (3) 使用 indexOf() 方法，確認該前綴字有沒有在 strs[i], 如果有prefix則回傳 0, 沒有的話回傳 -1
	 * (4) 使用 substring() 方法，每次該方法回傳 -1 時，截斷該字串
	 * 
	 * 範例：
	 * strs=["flower", "flow", "flight"]
	 * prefix=flower
	 * index=1
     *     while(strs[index].indexOf(prefix) != 0) means while("flow".indexOf("flower")!=0)
     *     Since flower as a whole is not in flow, it return -1 and  prefix=prefix.substring(0,prefix.length()-1) reduces prefix to "flowe"
     *     Again while(strs[index].indexOf(prefix) != 0) means while("flow".indexOf("flowe")!=0)
     *     Since flowe as a whole is not in flow, it return -1 and  prefix=prefix.substring(0,prefix.length()-1) reduces prefix to "flow"
     *     Again while(strs[index].indexOf(prefix) != 0) means while("flow".indexOf("flow")!=0)
     *     Since flow as a whole is in flow, it returns 0 so now prefix=flow
     * index=2
     *     while(strs[index].indexOf(prefix) != 0) means while("flight".indexOf("flow")!=0)
     *     Since flow as a whole is not in flight, it return -1 and  prefix=prefix.substring(0,prefix.length()-1) reduces prefix to "flo"
     *     Again while(strs[index].indexOf(prefix) != 0) means while("flight".indexOf("flo")!=0)
     *     Since flo as a whole is not in flight, it return -1 and  prefix=prefix.substring(0,prefix.length()-1) reduces prefix to "fl"
     *     Again while(strs[index].indexOf(prefix) != 0) means while("flight".indexOf("fl")!=0)
     *     Since fl as a whole is in flight, it returns 0 so now prefix=fl
     * index=3, for loop terminates and we return prefix which is equal to fl
	 */
	class Solution2 {
		public String longestCommonPrefix(String[] strs) {
			String prefix = strs[0];
			for (int index=1; index<strs.length;index++) {
				while(strs[index].indexOf(prefix) != 0) {
					prefix=prefix.substring(0,prefix.length()-1);
				}
			}
			return prefix;
		}
	}
}

