package w0002_addTwoNums;

import Shapes.ListNode;

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

// 此人 /hi-malik/ 解題極為詳細 *thumbs-up*
class Solution3 {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode dummy = new ListNode(0); // new 一個 dummy list
		ListNode curr = dummy;  // 初始化一個 pointer
		int carry = 0; // 初始化 carry 的值為 0
		// 除非 l1 或者 l2 變成 null 值或者兩者都變成 null值，while loop 會一值執行下去。但carry有值為==1
		// 會把它加到 list 中
		while(l1 != null || l2 != null || carry == 1) {
			int sum = 0;  // 初始化 sum
			if (l1 != null) {  // 將 l1 加到 sum 中, l1 移動到下一個
				sum += l1.val;
				l1 = l1.next;
			}
			if (l2 != null) {  // 將 l2 加到 sum, l2 移動到 list 下個值
				sum += l2.val;
				l2 = l2.next;
			}
			sum += carry; // 如果 carry 有值的話，把它加到 sum
			carry = sum/10; // 將 sum 除以 10 取得 carry 
			ListNode node = new ListNode(sum % 10); // 將 sum 除以10 求餘數，餘數也會變成新節點，加入 list 中
			curr.next = node; // 如果有 node 值的話，curr 會改指向到該節點
			curr = curr.next; // 每次都更新 curr
		}
		return dummy.next; // 回傳 dummy.next
	}
}

	
	
	
	
	
	
	
	
	
	
	
	

