package w0141_LinkedListCycle;

import Shapes.ListNode;

public class LinkedListCycle {
	/**
	 * Definition for singly-linked list.
	 * class ListNode {
	 *     int val;
	 *     ListNode next;
	 *     ListNode(int x) {
	 *         val = x;
	 *         next = null;
	 *     }
	 * }
	 */
}

class Solution {
	public boolean hasCycle(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			// slow 一次 while loop 只往前移一格
			// fast 一次前移兩格, 如果週期的話, 會遇到 slow==fast
			// Floyd's tortoise and hare algorithm
			
			if (slow == fast)
				return true;
		}
		
		return false;
	}
}