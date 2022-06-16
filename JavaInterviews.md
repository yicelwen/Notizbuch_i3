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
+ 簡化了什麼東西？
+ (解決很多環境問題。例：本來要寫 @Configuration，現在只要寫在 application.properties，內建 tomcat)

**Restful**
+ 是一種風格，回傳的是 JSON，前後端分離的狀況下，兩端溝通的橋樑


