package Shapes;

public class Diamond {
	public static void main(String[] args) {
		int rows = 3;
		// upper diamond
		for (int i = 1; i < rows; i++) {
			for (int j = 0; j <= rows-i; j++) {
				System.out.print(" ");
			}
			for (int k = 1; k <= 2*i-1; k++) {
				System.out.print("*");
			}
			System.out.println();
		}
		// lower diamond
		for (int i = rows; i > 0; i--) {
			for (int j=0; j <= rows-i; j++) {
				System.out.print(" ");
			}
			for (int k = 1; k <= 2*i-1; k++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

}
