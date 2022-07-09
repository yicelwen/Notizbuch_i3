package w0088_mergeSorted;

public class MergeSortedArray {
	/*
	 * https://leetcode.com/problems/merge-sorted-array/ 兩個數字陣列，把它依照大小順序合併成一個陣列，存在
	 * nums1 因此 nums1 陣列大小為 m + n
	 */

	public void merge(int[] nums1, int m, int[] nums2, int n) {
		int p1 = m - 1;  // 第一個陣列結束的索引值
		int p2 = n - 1;  // 第二個陣列結束的索引值
		int i = m + n - 1;  // 合併後陣列的長度
		
		while (p2 >= 0) { 
			if (p1 >= 0 && nums1[p1] > nums2[p2]) {
				nums1[i--] = nums1[p1--];   
				// 如果陣列一最後一個值>陣列二最後一個值，則把值賦給後面的索引陣列 (從後面開始寫不會覆蓋到)
			} else {
				nums1[i--] = nums2[p2--];  
				// 否則就把陣列二最後個值寫到陣列一裡面，再-1往前面一個索引的值比較
			}
		}
	}
}