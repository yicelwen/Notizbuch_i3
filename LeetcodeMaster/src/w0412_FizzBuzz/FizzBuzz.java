package w0412_FizzBuzz;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
	/* 給一個 int n，要回傳一個串列["1",...,"n"]:
	 *   如果輪到可以被 3 跟 5 整除，改成 "FizzBuzz"
	 *   如果可以被 3 整除，改成 "Fizz"
	 *   如果可以被 5 整除，改成 "Buzz"
	 *   如果沒有 3,5 此兩因數，就 answer[i]="i"
	 */
	public List<String> fizzBuzz(int n){
		List<String> answer = new ArrayList<>();
		
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0) {
				if (i % 5 == 0)
					answer.add("FizzBuzz");
				else
					answer.add("Fizz");
			}
			else if (i % 5 == 0)
				answer.add("Buzz");
			else
				answer.add(String.valueOf(i));
		}
		return answer;
	}
}
