package w0206_ReverseLinkedList;

import Shapes.ListNode;

public class ReverseLinkedList {
	/* 給定一個單向鏈表, 回傳順序顛倒的鏈表 
	 * 可用 iteratively 或 recursively */
}
class Solution {
	public ListNode reverseList(ListNode head) {
		if (head == null) return head;
		ListNode next = head.next;
		head.next = null;
		
		return recursive(head,next);
	}
	
	private ListNode recursive (ListNode head, ListNode next) {
		if (next == null) return head;
		ListNode temp = next.next;
		next.next = head;
		return recursive(next,temp);
	}
}
