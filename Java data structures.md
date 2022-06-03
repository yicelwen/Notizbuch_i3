### Array - a list of bucket where you define the size
+  Fixed in size
+  Fast for data retreivals
+  Compact memory usage is size is known
+  Deleting operation is very hard
   need to shuffle others forward
+  2D arrays
```
String[] color = new String[5];
colors[0] = "purple";
colors[1] = "blue";

System.out.println(Arrays.toString(colors));
System.out.println(colors[0]);
System.out.println(colors[1]);
System.out.println(colors[2]);
for [int i = 0; i < colors.length; i++) {
	System.out.println(colors[i]);
}

for (int i = colors.length -1; i >= 0; i--) {
	System.out.println(colors[i]);
}

for (String color : colors) {
	System.out.println(color);
}

```
0,0		0,1		0,2

1,0		1,1		1,2

2,0		2,1		2,2
```
char[][] board = new char[3][3];
for(int i=0; i<3; i++){
	for (int j=0; j<3; j++{
	board[i][j] = '-';
	}
}
//使用 nested loop 以外的方式填入 '-' 符號
char[][] boardTwo = new char[]p[ {
	new char[] {'-','-','-'},
	new char[] {'-','-','-'},
	new char[] {'-','-','-'}
}
```
[[-,-,-],[-,-,-],[-,-,-]]

### List
+ An ordered collection aka sequence.
+ Allow duplicates
+ Not fixed in size like arrays
+ Fast for data retrivals
+ Various implementation
	+ ArrayList
	+ Stack
	+ Vector
	+ Others
```
List<String> colors = new ArrayList<>();
colors.add("blue");
colors.add("purple");
	System.out.println(colors.size());
System.out.println(colors.contains("blue"));  // true
System.out.println(colors.contains("pink"));  // false
// colors.add(1);
// colors.add(new Object());

for (String color : colors) {
	System.out.println(color);
}

colors.forEach(System.out::println);

for(int i = 0; i < colors.size(); i++) {

}

```
1. 使用 for : loop
2. 使用 forEach
colors.forEach(System.out::println);
3. 使用傳統 for i loop

List<E>  Generics
ArrayList : one of the implementations of List interface
預設有 10 個空位

List<E> = new ArrayList<E>();
前面通常寫list inferace  =  後面寫實作的類別(eg. LinkedList, ArrayList,etc)
90% 都是用 ArrayList

```
List<String> colorsUn = List.of(
	"hola",
	"hallo",
	"konichiwa"
}
// 這是不能增加修改的 list (immutable)
```

### Stack
+ The stack class represent a LAST-IN-FIRST-OUT(LIFO) stack of objects.
+ It extends class *Vector* with five operations that allow a vector to be treated as a stack.
+ The usual *push* and *pop* operations are provided,
	as well as a method to *peek* at the top item on the stack,
	a method to test for whether the stack is *empty*, and a method
	to *search* the stack for an item and and discover how far it is from the top.
```
Stack<Integer> stack = new Stack<>();
stack.push(1);
stack.push(2);
stack.push(3);
System.out.println(stack.peek());	// give you an element on the top of the stack (3)
System.out.println(stack.size());	// 3
System.out.println(stack.pop());	// not only gives you .peek element, but also removes it
System.out.println(stack.empty());	// return boolean value 
System.out.println(stack.search(3));

// List 的 add 方法相當於 Stack 的 push 方法
```

### Queue
FIRST-IN-FIRST-OUT (FIFO)
+ A collection designed for holding elements prior to processing.
	tail <===> head
```
public static void main(String[] args){
	Queue<Person> supermarket = new LinkedList<Person>();
	supermarket.add(new Person("Alex, 21));
	supermarket.add(new Person("Mariam, 40));
	supermarket.add(new Person("Ali, 33));
	
	supermarket.size());	// 3
	supermarket.peek());  //Alex
	supermarket.poll());  //Alex (poll==peek+remove; 如果無值則回傳null
	supermarket.size());	// 2
	supermarket.peek());  //Mariam

}
static record Person(String name, int age){}

```
LinkedList
null<--(head)node 1==next==> node 2 ==next==> node 3 ==next==> (tail)node 4-->null
				   <=prev===		<=prev===		 <=prev=== 


```
public static void main(String[] args){
	LinkedList<Person> linkedList = new LinkedList<>();
	linkedList.add(new Person("Alex", 21));
	linkedList.add(new Person("Benji", 29));
	linkedList.
	ListIterator<Person> personListIterator = linkedList.listIterator();
	while (personListIterator.hasNext()) {
		System.out.println(personListIterator.next());
	}	// loop 

	while (personListIterator.hasPrevious()) {
		System.out.println(personListIterator.previous());
	}	// reverse loop

}
static record Person(String name, int age){}
```

### Set
+ A collection that contains no duplicate elements. (不可重複)
+ More formally, sets contain no pair of elements e1 and e2 such that e1.equals(e2), and at most one null element.
+ As implied by its name, this interface models the mathematical set abstraction.
	+ HashSet
	+ TreeSet
	+ EnumSet
	+ et cetera....

```
public static void main(String[] args){
	Set<Ball> balls = new HashSet<>();
	balls.add(new Ball("blue"));
	balls.add(new Ball("blue"));	   //
	balls.add(new Ball("yellow"));
	balls.add(new Ball("red"));
	balls.remove(new Ball("red"));
	balls.forEach(System.out::println);  
		// print out not-quaranteed order
	System.out.println(balls.size());  // 3. as duplicate blues not approved
}

record Ball(String color){}
```

### Map
+ HashTable: Null not allowed, synchronized
+ HashMap: Null allowed
    + LinkedHashMap: Null Allowed, doubly linkedlisted based impl, slow
+ SortedMap
    + NavigableMap
    + TreeMap: Sorted, null not allowed
1. A map cnanot contain duplicate keys.
2. Each key can map to at most one value.

1:Value		2:Value		3:Value		KN:VN