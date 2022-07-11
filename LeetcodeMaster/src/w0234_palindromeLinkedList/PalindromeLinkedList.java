package w0234_palindromeLinkedList;

import Shapes.ListNode;

public class PalindromeLinkedList {
	
	public boolean isPalindrome(ListNode head) {
		if (head == null) {
			return true;
		}
		ListNode p1 = head;
		ListNode p2 = head;
		ListNode p3 = p1.next;
		ListNode pre = p1;
		// find middle pointer and reverse head half part
		while (p2.next != null && p2.next.next != null) {
			p2 = p2.next.next;
			pre = p1;
			p1 = p3;
			p3 = p3.next;
			p1.next = pre;
		}
		
		// odd number of elements, need left move p1 one step
		if (p2.next == null) {
			p1 = p1.next;
		} else {
			// even number of element, do nothing
		}
		// compare from middle to head/tail
		while (p3 != null) {
			if (p1.val != p3.val) {
				return false;
			}
			p1 = p1.next;
			p3 = p3.next;
		}
		return true;
	}

}
