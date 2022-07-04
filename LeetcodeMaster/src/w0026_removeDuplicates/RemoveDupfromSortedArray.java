package w0026_removeDuplicates;

public class RemoveDupfromSortedArray {
	// two-pointer approach 喜蝦糸
	/* 傳入參數一陣列, non-decreasing (索引裡面的數字遞增
	   刪掉重複的數字並回傳此陣列有幾個值
	*/
}

class Solution1 {
    public int removeDuplicates(int[] nums) {
    int i = nums.length > 0 ? 1 : 0;
    for (int n : nums)
        if (n > nums[i-1]){
            nums[i++] = n;}  
    return i;
    }
}

class Solution2 {
    public int removeDuplicates(int[] nums) {
    
        int i = 1; //iterator thru array
        int j = 0; //current index
        for (; i<nums.length; i++) { 
            if (nums[i] != nums[j]) { //new number
                j++; //move current index
                nums[j] = nums[i]; //fill current index with new number
            } 
        }
    return j+1;
   }
}