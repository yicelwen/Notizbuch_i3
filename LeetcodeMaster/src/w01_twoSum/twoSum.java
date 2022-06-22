package w01_twoSum;

import java.util.HashMap;
import java.util.Map;

public class twoSum {

	/**
	 * 
	 */
	class Solution1 {
	    public int[] twoSum(int[] nums, int target) {
	        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	        for (int i = 0; i < nums.length; map.put(nums[i], i++))
	            if (map.containsKey(target - nums[i]))
	                return new int[] {map.get(target - nums[i]), i};
	        return new int[] {0, 0};
	    }
	}
	
	/**
	 * 1. 將 array `例如 nums = [2,7,11,15]` 存入 hashMap 的 key 值
	 * 
	 */
	class Solution2 {
	public int[] twoSum2(int[] num, int target) {
	    int[] result = new int[2];
	    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	    for (int i = 0; i < num.length; i++) {
	    	// 如果此 hashmap 包含
	        if (map.containsKey(target - num[i])) {
	            result[0] = i;
	            result[1] = map.get(target - num[i]); // get 方法返回 hashmap 的 value
	            return result;
	        }
	        // refer to note#1
	        map.put(num[i], i);
	    }
	    return result;
	}

}
}
