package w0021_MergeTwoSortedLists;

import Shapes.ListNode;

public class MergeTwoSortedLists {
	/* Linked List | Recursion
	 */
}
/* 迭代 while 迴圈時,因為一次只能看一個 node, 很容易忘記 last 是什麼值
 * 比如；一個 car List,順序 黃、藍、紅。迭代時把黃、藍、紅按迴圈順序指派給 last 變量，
 * 迭代到紅色時,由於 last 變量是紅車, 就無從得知黃車的位置在哪了
 * 但是如果在黃車前面先放台綠車, 就可知黃車位置相當於 greencar.next, 此例 greencar 相當於 prehead
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    	ListNode preHead = new ListNode(0);
    	ListNode last = preHead;
    	
    	while (list1 != null && list2 != null) {
    		if(list1.val > list2.val) {
    			last.next = list2;
    			list2 = list2.next;
    		} else {
    			last.next = list1;
    			list1 = list1.next;
    		}
    		last = last.next;
    	}
    	
    	if(list1 == null) {
    		last.next = list2;
    	} else {
    		last.next = list1;
    	}
    	return preHead.next;
    }
}