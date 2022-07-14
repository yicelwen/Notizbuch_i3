package w0160_IntersectionOfTwoLinkedLists;

import Shapes.ListNode;

public class IntersectionOfTwoLinketLists {
	/* 指定的參數:
	 *   intersectVal - 兩個鏈結串列交集時，該節點的數值；如果沒有交集則回傳 0
	 *   listA - 第一個鏈結串列
	 *   listB - 第二個鏈結串列
	 *   skipA - 1st 鏈結串列抵達 intersectVal 之前要跳過的幾個節點
	 *   skipB - 2nd 鏈結串列抵達 intersectVal 之前要跳過的幾個節點
	 */
}

class Solution {
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
	    //boundary check
	    if(headA == null || headB == null) return null;
	    
	    ListNode a = headA;
	    ListNode b = headB;
	    
	    //if a & b have different len, then we will stop the loop after second iteration
	    while( a != b){
	    	//for the end of first iteration, we just reset the pointer to the head of another linkedlist
	        a = a == null? headB : a.next;
	        b = b == null? headA : b.next;    
	    }
	    
	    return a;
	}
}
