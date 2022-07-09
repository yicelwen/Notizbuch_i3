package w0242_validAnagram;

public class ValidAnagram {
	// Tom Marvolo Riddle 🐍 I am Lord Voldemort
	public boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) return false;
		
		char[] c1 = s.toCharArray();
		char[] c2 = t.toCharArray();
		int[] count = new int[26];
		
		for (int i = 0; i < c1.length; i++) {
			count[c1[i] - 'a']++;  // increments the count
			count[c2[i] - 'a']--;  // decrements the count of characters checked in the string
			// 某值-97('a'的ascii碼); b-a = 1  c-a = 2 依此類推
		}
		
		for (int num : count) {
			if (num != 0) return false;
		}
		return true;
	}
	
}
