package diamond;

public class TestPrint {
	public static void main(String[] args) {
		
		int range = 3;
		
		for (int i = 0; i < range; i++) {
			String space = "";
			String star = "";
			for (int j = 0; j < range-i-1 ; j++) space += " ";
			for (int j = 0; j <i*2+1; j++) star += "*";
		    System.out.println(space+star);
		}
		
		for (int i=1; i<range; i++) {
			String space ="";
			String star = "";
			for(int j = 0; j<i; j++) space += " ";
			for(int j = 0; j<range*2-1-i*2 ; j++)  star += "*";
			System.out.println(space+star);
		}
		
	}
}