`Spring Boot EEIT140 Video Day 3 面試準備`

**Java 技術面試問答題:**

+ 物件導向是什麼
+ 定義：封裝、繼承、多型、泛型、抽象類別 vs 介面類別
+ Collection 有哪些
+ 紙筆考試：Main 方法
    + (public static void main(String[] args { })

**SQL 問答題:**

+ Left join vs right join 差異
+ Id 自動產生的方式 (GenerationType.Identity 與 Sequence 差別)
+ 紙筆考試： 3 Tables join

何謂 ACID？

+ 原子性 Atomicity：
    + 同一筆交易不是成功就是失敗(不會扣錢只扣一半, 另一邊沒加到)
+ 一致性 Consistency：
    + 外來鍵增加的資料如果不是另一張表主鍵有的資料，不會讓你加
+ 隔離性 Isolation：
    + 不會兩筆交易同時進去table拿東西
+ 持久性 Durability：
    + commit 完成之後，資料就會存在硬碟上面

+ Index System:
    + 索引是指允許更快地從表中檢索記錄的效能調整方法。索引為每個值建立一個條目，因此檢索資料會更快。

+ Trigger 觸發程序:
    + 

**JavaScript 面試問答題**
+ var, const, let 差別
+ js 拿到日期有哪些方法 (非常多)
+ this 是什麼?
+ Arrow function vs 一般的 function 
+ ES5 vs ES6 差異

**CSS 面試問答題**
+ 垂直置中 (至少準備三種)
+ 水平置中 (至少準備三種)
+ Flex-box 排版 vs Float 排版 vs Grid網格排版方式

**Servlet 面試問題**
+ request ---(中間的過程，會跑過哪些東西。 Filter, listener等等先後順序)--->  response 
+ JSP 會不會使用 EL、JSTL?

**Hibernate 面試問題**
+ 用來解決什麼問題？
+ hibernate 的 Mapping 是什麼？
+ 一對一、一對多、多對多

**Spring 面試問題**
+ 控制反轉 IoC
+ 依賴注入
+ 製作 Bean 的三種方式 (ans.: xml, @Component, @Configuration + @Bean)

**Spring MVC面試問題**
+ 物件 DispatcherServlet 是什麼
+ 控制的機制 Handler Mapping 
+ 何謂 Controller？ 由什麼物件控制？
+ 什麼是 Model and View？ 由什麼物件控制？

**Spring Boot 面試問題**
+ 何謂「約定大於配置」
    + Convention over configuration:
        + 減少軟體開發人員需做決定的數量。開發人員僅需規定應用中不符約定的部分。
+ 簡化了什麼東西？
+ (解決很多環境問題。例：本來要寫 @Configuration，現在只要寫在 application.properties，內建 tomcat)

**Restful**
+ 是一種風格，回傳的是 JSON，前後端分離的狀況下，兩端溝通的橋樑



# 經典 Java 200 題
1. JDK  |  JRE  |  JVM 
    + 概述
        + JDK Java 標準開發包，提供了 **Java 編譯器**、**Java 運行環境**、常用 **Java 類庫**
        + JRE Java 運行環境，用於運行 Java 的字節碼文件。JRE 包括了 JVM 以及 JVM 工作需要的類庫
        + JVM Java 虛擬機，是 JRE 的一部分，是 Java 實現跨平台最核心的部分，負責**運行字節碼**文件

    + 如果要開發 Java 專案就需要 JDK，因為要編譯 Java 源文件
    + 如果只是要運行 Java 字節碼文件(xxx.class)，那只需要 JRE
    + ( __JDK__ ( __JRE__ (__JVM__)))
    + JVM 在運行字節碼時，必須把字節碼解釋為機器指令。不同操作系統上的 JVM 不一樣，因為不同操作系統上的機器指令不同
    + Apache Groovy, Scala, Kotlin 這幾種語言編譯之後是 Java 字節碼，編譯之後也能在 JVM 上運行

2. hashCode() 與 equals() 差異
    + 判斷兩個 User的 hashcode 是否相同，相同的話才會調用 equals 方法來進行比較
    + 需重寫 hashCode() 方法，要跟 equals 方法的邏輯保持一致
    ```Java
    public class Main {
        public static void main(String[] args){
            
            HashMap<User, String> hashMap = new HashMap<>();
            hashMap.put(new User("yicelwen"), "123");

            System.out.println(hashMap.get(new User("yicelwen")));
            // 如果沒有重寫 hashCode 方法，get 方法拿不到回傳值 // null
        }
    }
    ```
    ```Java
    public class User {
        private String name;

        public user(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        /** 得到屬性並進行比較
        **/
        @Override  
        public boolean equals(Object obj) {
            User user = (User) obj;
            return user.getName().equals(this.name);
        }

        /** 邏輯要與 equals 保持一致。關注的屬性都是 name。
        **/
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
    ```

3. String、StringBuffer、StringBuilder 區別
    + String 常量、不可變
        + Strings are constant.
    + StringBuffer 線程安全
        + 類似於 String 但是可以修改
    + StringBuilder 線程不安全
        + 線程不安全 = 沒有同步的
        + 在單線程環境下，StringBuilder效率比較高

4. 泛型中 extends 和 super 
    + <? extends T> 表示包括 T 在內的任何 T 的子類
    + <? super T> 表示包括 T 在內的任何 T 的父類
    ```Java
    public static void main(String[] args) {
        YiwenList<BigDecimal> ywList = new YiwenList();

        ywList.add(new BigDecimal(1));
        ywList.add(new Integer(1));
        // 編譯會檢查增加的內容是否屬於泛型規範的類型
    }
    ```
    ```Java
    public class YiwenList<E extends Number> {
        public void add(E e){
            // ...
        }
    }
    ```

5. == 和 equals 方法區別
    + == : 如果是`基本資料型別`，比較的是值、如果是`參考資料型別`，比較的是記憶體位置/引用地址
    + equals : 要看各類別重寫 equals 方法之後的比較邏輯
        + String 類: 雖然是參考資料型別，但是此類別重寫了 equals 方法，方法內部比較的是字符串中的各個字符是否**全部相等**。
        ```Java
        // String class 底下重寫的 equals 方法
        public class String {
            public boolean equals(Object anObject) {
                if (this == anObject) {
                    return true;
                }
                if(anObject instanceof String) {
                    String anotherString = (String)anObject;
                    int n = value.length;
                    if (n == anotherString.value.length) {
                        char v1[] = value;
                        char v2[] = anotherString.value;
                        int i = 0;
                        while (n-- !=0) {
                            if (v1[i] != v2[i])
                                return false;
                            i++;
                        }
                        return true;
                    }
            }
            return false;
        }
        ```
6.  重載 和 重寫 的區別
    + 重載 Overloading：發生在**同一個類別**中，方法名稱必須相同，參數類型不同。個數不同、順序不同也是重載、方法返回值和訪問修飾子可以不同，發生在編譯時。
    + 重寫 Override：發生在**父子類**中
        + 參數列表必須相同
        + 返回值範圍小於等於父類別
        + 拋出的異常範圍小於等於父類別
        + 訪問修飾符範圍大於等於父類別 (i.e. protected / private)。<br/>
          如果父類別的訪問修飾符是 private，子類別就無法重寫此方法           
        
7. List 和 Set 的區別
    + List：有序，按對象進入的順序保存對象，可重複。允許多個 Null 元素對象。
        + 可以使用 iterator 迭代器取出所有元素，再逐一遍歷
        + 還可以使用 get(int index) 獲取指定下標的元素
    + Set：無序，不可重複，最多允許有一個 Null 元素對象
        + 可以用 iterator 取得並遍歷所有元素，沒有下標訪問

8. ArrayList 和 LinkedList 區別
    + 底層數據：ArrayList 數組  |  LinkedList 鏈表
    + 適用場景：ArrayList - 適合隨機查找 | LinkedList 適合刪除添加
    + LinkedList 除了實現 List，還實現了對列 Queue 介面 (有addFirst()、addLast()、getFirst()、getLast() 等方法)

9. ConcurrentHashMap 的擴容機制
    + JDK 1.7
        + 基於`Segment 分段`實現，每一個 Segment 下面的 HashMap 再擴容
        + 首先，生成新的數組，然後轉移元素到新的數組中
        + HashMap 轉移到新的 HashMap(雙倍擴容) 完成後，該 Segment 再重新指向新的 HashMap
    + JDK 1.8
        + Key-value 組成的 Entry，不再基於 Segment 實現
        + **1.8 支援多線程擴容**，即舊的 Entry 內容轉到雙倍擴容的新 Entry (新的數組) 時，其中幾個元素由某線程負責，另外幾個由其他線程負責轉移元素

10. JDK 1.7 到 JDK 1.8 之間 HashMap 發生了什麼底層變化
    + 1.7 數組 + 鏈表實現 `鏈表在插入值的時候，要先判斷`
        + 使用頭插法
        + Hash 哈希算法比較複雜，存在各種右移與與異或運算，為了提高散列性
    + 1.8 數組 + 鏈表 + **紅黑樹** `目的：提高 HashMap 插入和查詢的整體效率`
        + 使用`尾插法`
        + 因為插入 key-value 時需要判斷鏈表元素個數，所以需要遍歷鏈表，以統計鏈表元素個素
        + 增加紅黑數，可以簡化了 Hash 哈希算法，節省 CPU 資源

11. HashMap 的 put 方法
    + 大致流程原理
        1. 根據 put 進來的 key 通過哈希算法與運算
        2. 判斷數組下標當前位置是否為空，如果是，就將Key-Value封裝成 Entry 對象並放入該位置
            + JDK 1.7 是封裝成 `Entry` 對象、JDK 1.8 是封裝成 `Node` 對象
        3. 如果數組下標位置不為空值，則分版本討論：
            + 1.7:
                + 判斷是否要擴容，不用擴容的話會生成 Entry 對象，並透過頭插法插入當前列表位置
            + 1.8:
                + 判斷當前 Node 是紅黑樹的 node 還是鏈表的 node：
                    + **紅黑樹**：把KV封裝成Node節點，放入紅黑樹<br/>
                         同時判斷當前紅黑樹裡是否已經存在此key，若存在，就更新value
                    + **鏈表**：把KV封裝成Node，透過`尾插法`放進去<br/>
                         遍歷鏈表目的 (1)查看有無相同 key (2)計算有幾個元素 <br/>
                         插入鏈表後，若全部元素如果大於等於 8 個，就會把鏈表轉成紅黑樹
                         最後判斷是否需要擴容
                         
12. 深拷貝 vs 淺拷貝
    + 基本資料型別、實例對象 (user class, orderService) 的引用
        + 淺拷貝：只會拷貝基本數據類型的值以及實例對象的引用地址，不會複製引用地址所指向的對象
            + 即淺拷貝出來的對象，內部類別屬性指向的是同一個對象
        + 深拷貝：既會拷貝基本數據類型的值，也會針對實例對象的引用地址所指向的對象進行複製
            + 深拷貝出來的對象，內部的屬性指向的兩個不同的對象

13. HashMap 的擴容機制
    + 1.7 版
        + 針對數組擴容，鏈表不需要擴容
        + 步驟
            1. 首先，生成新數組 (通常是雙倍 eg. 8 vs 16)
            2. 遍歷老數組中的每個位置上的鏈表上的每個元素
            3. 取每個元素的 key，並基於新數組長度，算出每個元素在新數組中的下標
            4. 將元素添加到新數組中去，完成元素轉移
            5. 所有元素轉移完了之後，將新數組賦值給 HashMap 對象的 table 屬性
    + 1.8 版
        + 步驟
            1. 首先，生成新數組  
            2. 遍歷老數組中的每個位置上的鏈表或者紅黑樹
            3. 如果是鏈表，則直接將鏈表中的每個元素重新計算下標，並添加到新數組中
            4. 如果是紅黑樹，則先遍歷紅黑樹，先計算出紅黑樹中每個元素對應在新數組中的下標位置
                1. 統計每個下標位置的元素個數
                2. 如果該位置下的元素個數超過了 8，則生成一個新的紅黑樹，並將根節點的添加到新數組的對應位置
                3. 如果該位置下的元素沒有超過 8，則生成一個鏈表，並將鏈表的頭節點添加到新數組的對應位置
            5. 所有元素轉移完之後，將新數組賦值給 Hash 對象的 table 屬性
14. 
15. 
18. 
19. 
20. 
21. 
22. 
23. 
23. 
