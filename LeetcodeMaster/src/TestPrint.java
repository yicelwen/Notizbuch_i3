
public class TestPrint {
	public static void main(String[] args) {

		for (int i = 0; i <= 4; i++) {
			// 先列印“-”
			for (int k = 1; k <= 4 - i; k++) {
				System.out.print(" ");
			}

			// 再列印“*”
			for (int j = 1; j <= i + 1; j++) {
				System.out.print("* ");// 列印“*”+空格
			}
			System.out.println(" ");// 每列印完一行後，換行

		}

		for (int i = 0; i <= 3; i++) {
			// 先列印“-”
			for (int k = 1; k <= i + 1; k++) {
				System.out.print(" ");
			}
			// 再列印“*”
			for (int j = 1; j <= 4 - i; j++) {
				System.out.print("* ");// 列印“*”+空格
			}
			System.out.println(" ");// 每列印完一行後，換行

		}
	}
}