### 壹. Collection | Arrays 的 .stream() 方法
TBC

TBC

TBC

### 貳. {1,1,2,2,3,3,3,3} 印出{1,2,3}  跟這題 leetcode 7-8 分像
https://leetcode.com/submissions/detail/738436001/
```Java
public int removeDuplicates(int[] nums) {
  int i = nums.length > 0 ? 1 : 0;
  for (int n : nums)
    if (n > nums[i-1])
      nums[i++] = n;  // 要略過
  return i;
}
```
### 参. TimeStamp
TBC

TBC

TBC

### 肆. 印出菱形
```Java
int rows = 3;
// upper diamond
for (int i = 1; i < rows; i++) {
  for (int j = 0; j <= rows-i; j++) 
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
```
#### 補充印三角形
```Java
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
```

### 伍. List Map 按順序印出
TBC

TBC

TBC