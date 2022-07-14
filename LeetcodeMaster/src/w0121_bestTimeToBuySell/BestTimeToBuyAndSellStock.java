package w0121_bestTimeToBuySell;

public class BestTimeToBuyAndSellStock {
/* 給定個陣列，回傳買入買出可以得到的最大價差
 * 如果只賠不賺的話，回傳 0    e.g. [7,6,4,3,1]
 * 注意不能第二天買入第一天賣出 e.g. [7,1,5,3,6,4] 回傳 5
 */
}
// Brute force 暴力法
class Solution1 {
	public int maxProfit(int[] prices) {
		int profit = 0;
		for(int i = 0; i < prices.length; i++) {
			for (int j = i; j >= 0; j--) {
				profit = Math.max(profit,  prices[i]-prices[j]);
			}

		}
		return profit;
	}
}

