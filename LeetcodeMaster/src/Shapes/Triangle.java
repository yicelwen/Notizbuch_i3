package Shapes;

public class Triangle {
	public static void main(String[] args) {
		int rows = 8;
        for (int i = 1; i <= rows; i++) {
            for (int j = rows; j >= i; j--) {  // 印出左半三角形旁邊的空格
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {  // 印出三角形左半邊
                System.out.print("*");
            } 
            for (int j = 1; j < i ; j++) {  // 印出右半邊
                System.out.print("*");       
            }
            System.out.println();
        }
	}
}
