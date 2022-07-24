## 0. H2 DB

## 1. Restful API

## 2. Cache
+ cache last time 60 seconds
+ 如果有 cache 則回傳 cache, 沒cache的話, 重新查詢後更新 cache 並回傳
+ edit 方法完成更新 cache
+ remove/delete方法完成時清除 cache

### `@CacheConfig`
+ `@CacheConfig` 是類級別的註解，統一該類的所有緩存可以前綴
```Java
@CacheConfig(cacheNames = {"user"})
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Cacheable(key="#id")
    public User findUserById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    
}
```
+ 以上代碼，代表了該類別所有緩存可以都是"user::"為前綴

### `@Cacheable`
+ `@Cacheable`是方法級別的註解，用於將方法的結果緩存起來

## 3. Transactional propagation
+ update two tables at a time, rollback in case of any exception
+ update two tables at a time , any exception wouldn't effect the operation done earlier

## 4. AOP
+ LoggerAspect `@Before`
+ LoggerAspect `@After`
+ LoggerAspect `@Around`
+ Logger.info start-finish time to complete each method in controller

## 5. Security
+ Need to enter key(Authentication) + value(Crypted_password) to access
+ `String` to Base64
+ `byte[]` to Base64
+ Decrypt Base64 and print out lengh of the byte[]
+ Commonly used encrypt/decrypt methods in business application and the Algorithms covered

## 6. Scheduler 
+ To calculate how many books stored in Book table and printed result on the console.

## 7. Request Validator
+ All params are mandatory/required to fill in, except for the Updating method.
+ `@Validator` and RegexExConst
    + [a-zA-Z0-9]{0,100}
    + [a-zA-Z]{0,50}
    + yyyyMMdd versus yyyy-MM-dd PATTERN

## 8.  sortbyorder
+ To sort by ORDER of book length, author AtoZ, date newToOld

## 9. Lambda 
+ concept, how to use
+ pros/benefits
+ under what conditions one can use the Lambda 
+ some sample code for comparision 
+ Lambda 表達式-簡化介面實現
    + 

## 10. Stream
+ from Collection to Stream

stream 實現對集合迭代器的增強（過濾、排序、統計分組）或者大批量的數據操作
提高可讀性以及效率: 與 Lambda 一起使用

```java
public class AppleServer {
    private static List<Apple> appleStore = new ArrayList<Apple>();

    static {
        appleStore.add(new Apple(1, "red", 500, "Fuji"));
        appleStore.add(new Apple(2, "red", 400, "Fuji"));       
        appleStore.add(new Apple(3, "green", 300, "Aomori"));
        appleStore.add(new Apple(4, "green", 200, "Washington"));
        appleStore.add(new Apple(5, "green", 100, "NewYork"));
    }
    

    // find red apple
    public void filterForLoop(){
        List<Apple> redApple = new ArrayList<Apple>();
        for (Apple apple : appleStore) {
            if (apple.getColor().equals("red")){
                redApple.add(apple);
            }
            // if - determine weight
            // if - determine origin
        }
    }

    // find red apple using stream and lambda
    public void filterLambda(){
        List<Apple> redApple = appleStore.stream()
                .filter(a -> a.getColor().equals("red"))
                .filter(a -> a.getWeight()>300)
                .filter(a -> a.getOrigin().equals("Aomori"))
                .collect(Collectors.toList());
    }

    // use predicate as parameter for filtering
    public void filterLambda(Predicate<? super Apple> pr){
        List<Apple> list = appleStore.stream()
                .filter(pr)
                .collect(Collectors.toList());
    }

    // find average weight of each color
    @Test
    public void test1() {
        // #1 group by color
        Map<String, List<Apple>> maps = new HashMap<>();
        for (Apple apple : appleStore) {
            List<Apple> apples = maps.computeIfAbsent(apple.getColor(),
                    key -> new ArrayList<>());
            // 如果該顏色對應的 key 不存在,就創建一個
            // IDEA 一段code.var - 相當於 Eclipse shift + alt + L 指派變數名
            // IDEA 一個變數.for - 快速生成一個 for 迭代迴圈
            apples.add(apple);
        }
        // #2 get average weight of each color
        for (Map.Entry<String, List<Apple>> entry : maps.entrySet()) {
            int weights = 0;
            for (Apple apple : entry.getValue()) {
                weights += apple.getWeight();
            }
            System.out.println(String.format("顏色%s 平均重量%s", weights / entry.getValue().size()));
            // IDEA 一段code.sout - syso + ALT + / 不過 IDEA 先寫要印出的內容
            // "(一段String)".format - 生成String.format(expr)
        }
    }

    // find average weight of each color USE STREAM API
    @Test
    public void test2() {
        // Map<String, List<Apple>> maps = appleStore.stream().collect(Collectors.groupingBy(a -> a.getColor()));
        appleStore.stream()
                .collect(Collectors.groupingBy(a -> a.getColor(),       // 基於顏色統計分組
                         Collectors.averagingInt(a -> a.getWeight())))  // 根據統計分組求出重量平均值
                .forEach((k,v)-> System.out.println(k+":"+v));          // 印出來
    }

    public static void main(String[] args) {
        new AppleServer().filterLambda(a->a.getColor().equals("red")&&a.getWeight()>300);
        // a-> 的 a 即為lambda，只是個代號，可用 b,c,d...代替
    }
}
```
```java
public class Apple {
    private int id;
    private String color;
    private int weight;
    private String origin;
}
```


## 11. GitLab
+ To understand how the reasons to use `rebase`
+ `cherry pick`
+ To use `no fast-forward merge` to merge git_Sample into my own branch and then solve merging conflicts
+ To compare fast-forward merge and no fast-forward merge

