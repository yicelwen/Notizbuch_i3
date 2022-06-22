package w02_addTwoNums;

public class addTwoNums {
	/**
	 * 兩個非空值的鏈表 linked list, 包含兩個存相反的正整數 (即 [2,4,3] 相當於 342; [5,6,4]=465)
	 * 需要回傳一個 鏈表, 值為兩數相加, 也要存相反 (即 342+465=807, 回傳[7,0,8])
	 * 
	 * 限制: 兩個鏈表的值介於 1~100
	 * 每個節點 node 的值 0 <= node.val <= 9
	 * 最前面的數字不會是 0 ? 
	 * (it's guaranteed that the list represents a number that does not have leading zeros.)
	 * 
	 */
}

class Solution1 {
    public ListNode addTwoNumbers (ListNode l1, ListNode l2) {
    	ListNode p = new ListNode(1);
    	ListNode q = new ListNode(2);
    	int flag = 0;
    	p.next = l1;
    	q.next = l2;
    	// 掃描到短的結束後退出
    	while (p.next != null && q.next != null) {
    		int sum = p.next.val + q.next.val + flag;
    		p.next.val = q.next.val = sum % 10;  // 求 sum 除以10 的餘數
    		flag = sum / 10;
    		p = p.next;
    		q = q.next;
    	}
    	// 鏈接長短鏈表
    	if (p.next == null)
    		p.next = q.next;
    	q.next = p.next;
    	
    	// 掃描接下去的進位情況
    	while (p.next != null && flag != 0) { 
    		int sum = p.next.val + flag;
    		p.next.val = sum % 10;
    		flag = sum / 10;
    		p = p.next;
    	}
    	if (flag != 0)
    		p.next = new ListNode(1);
    	return l1;
    }
    
    
}

class Solution2 {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int carry = 0;
		ListNode p, dummy = new ListNode(0);
		p = dummy;
		while (l1 != null || l2 != null || carry != 0) {
			if (l1 != null) {
				carry += l1.val;  // carry = carry + l1 ListNode 的值
				l1 = l1.next; // 將鏈表的下一個值指派給 l1
			}
			if (l2 != null) {
				carry += l2.val;
				l2 = l2.next;
			}
			p.next = new ListNode (carry % 10);
			carry /= 10; 
			p = p.next;
		}
		return dummy.next;
	}
}

	
	
	
	
	
	
	
	
	
	
	
	

