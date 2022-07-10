package w0350_intersectionOfTwoArrII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntersectionOfTwoArrII {
	
	/* 給定兩個數字陣列, 回傳有交集的數字
	 * 不指定回傳數字陣列的順序
	 * 
	 */
	public int[] intersect(int[] nums1, int[] nums2) {
		// 幫兩個陣列從小到大排序
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		// 使用 two-pointer 比對
		int top = 0;
		int bottom = 0;
		List<Integer> h = new ArrayList<>();
		
		while(true) {
			// 設定跳出迴圈條件: top變數>nums1陣列長度 或 bottom變數>nums2陣列長度
			if (top >= nums1.length || bottom >= nums2.length) {
				break;
			}
			// 上下值相等時, 把值寫進 h arraylist
			if (nums1[top] == nums2[bottom]) {
				h.add(nums1[top]);
				top ++;
				bottom ++;
			}else if (nums1[top] < nums2[bottom]) {
				top ++;
				/* 若nums1當前索引對應的值小於 nums2索引對應的值
				   則增加nums1[top] 的索引值 */
			}else if (nums1[top] > nums2[bottom]) {
				bottom ++;
				/* 反之則增加 nums2[bottom] 的索引值 */
			}
		}
		// 建一個數字陣列 g，長度同數字串列 h
		int[] g = new int[h.size()];	
		for (int i = 0; i < h.size(); i++) {
			g[i] = h.get(i);
		}
		return g;
	}
}

