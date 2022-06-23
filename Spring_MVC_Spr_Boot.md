# Spring MVC notes
## 1. spring MVC 架構

+ MVC 代表模型 (Model)，視圖 (View) 跟控制器 (Controller)，是一種軟體設計模式。它將要解決的問題分為 MVC 三部分，彼此獨立，優點為：
    1. 可交由團隊開發，加快開發速度
    2. 可將這三部分交給不同專長的人設計，人盡其才
    3. 抽換任一部分時其它部分不受影響，程式保有最佳的彈性

### 1-1 Spring MVC
+ **M - 模型**
    + 又稱 Domain Object，負責儲存資料的物件

+ **V - 視圖**
    + 交由客戶端裝置來顯示的頁面
    + Controller 取得 Model 產生的資料後，會產交給 View，由它嵌入
    + 可能由 HTML、CSS、jQuery、JavaScript 等技術組成的 UI，組成一個完整頁面，原則上不直接與模型互動，但也有例外 

+ **C - 控制器**
    + 客戶端的請求會先送到控制器，然後由控制器進行與資料庫無關的檢查，如欄位是否空白、檢查碼是否正確等

    + 若有錯誤，交由視圖送回錯誤訊息。如果沒有錯誤，呼叫服務層 (進行企業邏輯的運算，包括與資料庫有關的檢查，如庫存量是否足夠)

    + 然後請 Service 進行數值計算及呼叫 DAO 進行資料庫的存取，並將模型送回的結果轉交給視圖嵌入要送回給客戶端的頁面中

### 1-2 DispatcherServlet 分派器

+ Spring MVC 框架是圍繞在 DispatcherServlet 分派器設計的，分派器設計的，它是請求送到伺服器後第一個接觸的元件。分派器處理所有請求和回應。分派器處理請求的流程如下圖所示：

![image info](./images/spr-mvc-DispatcherServlet.png)

### 1-3 什麼是Spring Web MVC框架
+ Spring Web MVC 是一種基於 Web MVC 設計模式的一種輕量級網路應用系統框架，是目前 Java 語言中最主流的 MVC 框架。此框架提供許多類别與介面，將處理『HTTP 請求』的程式碼依功能來拆解為許多小元件。框架提供基礎功能，程式設計師只需要專注在企業邏輯的編寫，大幅簡化網路應用系統的開發

+ Spring Web MVC 的特質
    1. 能設計出簡潔的後端程式
    2. 可以與其他 Spring 模組（IoC 容器、AOP）無縫地整合在一起
    3. 經由一組功能強大的註釋，讓 POJO 不需實作任何界面就可成為處理請求的控制器 (Controller)
    4. 支持 REST 風格的 URL 請求
    5. 靈活的國際化使用者介面
    6. 提供強大的資料驗證、格式化與數據綁定等功能
    7. 提供一套強大的 JSP 標籤庫，簡化 JSP 網頁開發

+ Spring Web MVC 業界常簡稱為 Spring MVC

## 2. SpringMVC環境建置
### 2-1 Maven Web MVC 的基礎環境建置
###### 需要軟體有 Eclipse(Java EE)、Maven、Tomcat9

#### 2-1-1 在 workspace 內設置 Tomcat
1. 確認目前在 JavaEE 視景 視窗右上角確認，並底下會有 Servers 標籤的視圖
2. Window → Preference → Server → Runtime Environment
3. Add → Apache Tomcat 9.0 → next
4. Browse... 選擇您 tomcat 的位置 → 打勾 Create a new local Server → Finish
     → Apply and Close
5. 找到底下 Server 視圖，右鍵點選剛剛的 tomcat9 open
6. Server Locations 選第二個 Use Tomcat installation
7. Deply path 改成 : webapp
8. Timeouts → Start (in seconds) 改為 1800
9. 記得存檔 (Ctrl + S)
10. 選擇下面視圖內的 Tomcat 右鍵 → Start
11. 去瀏覽器查看 http://localhost:8080 是否有成功啟動

<br/>

#### 2-1-2 跟 Eclipse 說明本地 Maven 位置與其他設定
1. 到 Eclipse 環境內， Window → Perferences → Maven → Installations → Add → Installation home → Directory → 選擇 Maven 的安裝路徑 → 勾選 apache maven x.x.x 的選項 → Apply

2. User Settings → User Settings → Browse → 
    選擇檔案 `C:\DataSource-apache-maven3.x.x\conf\settings.xml` → 會看到下方 Local Repository 指向 `DataSource maven_repository repository`

3. 修改 JRE 版本 點選專案按右鍵 → Build Configure Build Path  → Libraries 標籤，選擇欲更換版本的 JRE System Library → 編輯 → 選擇適合的 JRE 版本 (jdk-11)

4. 點選專案按右鍵 → Project Facet → 選擇 java 11 → Apply

5. 點選專案按右鍵 → Build Configue Build Path → Order and Export 標籤 勾選 Maven Dependency → java 11 JRE → Apply

6. 點選專案按右鍵 Properties → Project Facet → Dynamic Web Module → 選擇 Runtime 標籤並勾選 → Tomcat Server x.x → Apply

7. pom.xml 內加入 dependency lib (從 Maven Repository 官網查看) 設定後存檔，並按下 Alt + f5 按鍵，Update Maven Project 勾選 Force Update of Snapshots/Releases 按下 OK 按鈕更新 Maven Library

<br/>

#### 2-1-3 設定開發環境編碼

1. 以下都在 Window → Preferences 裡面設定

2. Preferences → General → Workspace → 底下 Text file encoding 改為 UTF 8 → Apply

3. Preferences → Web 底下的 CSS Files → encoding 上滑找到 UTF 8 → Apply

4. Preferences → Web 底下的 HTML Files，JSP Files 同上

5. Preferences → JSON → JSON Files → encoding → UTF 8

6. Preferences → General → Content Types → Text → JSP 底下的三個 → Defalut encoding 改為 UTF 8 (這裡打字)

<br/>

#### 2-1-4 正式新建 Maven 的 Web 專案
1. 左邊 Project Explorer 空白處點選右鍵 → New → Project → Maven → Maven Project → Next

2. 打勾 Create a simple projects → Next

3. Group ID: com.sevletjsp → Artifact (你要的專案名稱): jspExercise → Version(不變) → Packaging: war → Finish

4. 建立專案後發現左邊視圖有剛剛我們建的 jspExercise 專案，目前有錯誤是正常的，這時請找到專案的 `pom.xml` 檔案右鍵 → Open with → Maven POM Editor

5. 請在中央視圖底下點選 Overview 標籤 → Properties → Create → Name:maven.compiler.source → Value: 1.8(or 11) → OK → 再一個 Create → Name: maven.compiler.target → Value: 1.8(or 11) → OK 存檔 (Ctrl + S)

6. 這時可以看看底下標籤 pom.xml 多了 
    ```xml
    <properties>
      <maven.compiler.source>11</maven.compiler.source>
      <maven.compiler.target>11</maven.compiler.target>
    </properties>
    ```

7. 上面是我們剛才輸入進去的，當然直接從這邊手寫這段也可以

8. 再加入以下標籤
    ```xml
    <build>
        <plugins>
            <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-war-plugin</artifactId>
               <version>3.3.2</version>
            </plugin>
        </plugins>
    </build>
    ```
9. 更新剛剛我們做的改動讓整個環境知道 : 需要點選專案右鍵 → Maven → Update Project
10. 打勾 Force Update of Snapshops/ Releases
11. 右鍵選專案 → Properties → Project Facets → Dynamic Web Module右邊三角型選4.0 → Java 1.8 → Apply and Close
12. 右鍵選專案 → Java EE Tools → Generate Deployment Descriptor Stub
13. 看專案內 → src →  main → webapp → WEB INF → `web.xml` → 點兩下打開 → 確認下方標籤在 Source
14. 看文件內拉到最右邊看版本會是 version ="2.5" 這不是我們要的，經過facet設定後應該要是4.0
15. 因此，刪除整個 WEB INF，再一次右鍵選專案 → Java EE Tools → Generate Deployment Descriptor Stub
16. 看專案內 → src → main → webapp → WEB INF → `web.xml` → 點兩下打開，應該就是 version= "4.0" 了
17. 設定完成

### 2-2 Spring MVC 需要的設定
#### 2-2-1 WEB-INF/web.xml 內的設定(實作)
1. 在 web.xml 內的 \<web-app> 標籤範圍內設定分派器
```xml
 <servlet>
   <servlet-name>dispatcher</servlet-name>
   <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
   <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/spring-mvc-demo-servlet.xml</param-value>
   </init-param>
   <load-on-startup>1</load-on-startup>
 </servlet>
```

2. 設定 url mappings
```xml
<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/<url-pattern>
</servlet-mapping>
```
<br/>

#### 2-2-2 WEB INF/spring-mvc demo servlet.xml 內的設定(實作)
1. Component Scan 的功能
      ```xml
      <context:component-scan base-package="要掃描的起始位置" />
      ```

2. Spring MVC 的格式化、驗證等功能
    ```xml
    <mvc:annotation-driven />
    ```

3. 視圖解析器的功能 (View Resolver)
    ```xml
      <bean
          class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/WEB-INF/view/" />
          <property name="suffix" value=".jsp" />
      </bean>
    ```
+ 只要給 view name 就會找到檔案：`/WEB-INF/view/someview.jsp`

<br/>

### 2-3 SpringMVC 需要的 java 套件
+ Spring MVC 至少需要以下套件的支援：
    + spring webmvc
    + javax.servlet api
    + javax.servlet.jsp api
    + jstl


## 3. Spring Controller and view
### 3-1 Controller 控制器說明
+ Controller 是MVC中的C，常被翻譯為控制器，主要是處理某個 url 的請求
+ 在 Spring MVC 中，在 Class 上方加入 `@Controller` 的註釋就表示本類別是一個控制器
+ 某 url 的請求會在有 `@Controller` 的 Class 內的方法中對應到的 url 字串，呼叫該方法內的程式， 該方法可以回傳 view name 的字串(轉到某頁面)，或是回傳 Java 物件 (會轉成 Restful 的 JSON 字串)

### 3-2 Controller 控制器的實作 
+ **@Controller** 
    + 註釋是 @Component 的一種，為 Class 級別的註釋，表示本類別為控制器
+ **@RequestMapping** 
    + 可以註釋在 class 或方法上方，後可補充字串為請求路徑
    + 若寫在 class 上則表示本類別內的方法中 url 的前綴

#### 3-2-1 Controller 使用方式
```Java
@Controller
public class FirstController {

    @RequestMapping("/")
    public String goSomewhere() {
        return "test1";
    }
}
```
+ 上圖為一個控制器，其中`goSomewhere()`方法上的 `@RequestMapping` 為請求的路徑，預設為 Get 請求
+ 回傳的字串為某頁面的名稱，上圖會轉到頁面 `/WEB INF/view/test1.jsp` 頁面
+ 因前面有設定頁面的前綴 (/WEB INF/view) 和後綴 (. 了，所以可以省略，只寫頁面名稱就好
+ Controller 內的方法名稱可自訂，可以辨別意思為主，沒有特別規定


#### 3-2-2 其他請求編寫方式
+ 若是其他請求，則括號內的url要寫出value，才可以加入第二個參數 (Request 參數)

    ```java
    @RequestMapping(value="/showform",method=RequestMethod.GET)
    public String showForm() {
        return "student_form";
    }
    ```
+ RequestMethod 可以為：Get、Post、Put、Delete，分別表示本 url 為 Get 請求或 Post 請求或其他上述 http 請求



#### 3-2-3 http 請求的簡化註解
+ 請求的種類可簡化為 
    + @GetMapping("請求路徑")
    + @PostMapping("請求路徑")


### 3-3 搭配 form 來轉頁
+ form 的 action 字串 url 會根據 Controller 內的字串，執行該 Controller 內的程式
    + jsp 頁面：
        ```xml
        <form action="processForm" method="get">
        <input type="text" name="studentname" placeholder="請輸入姓名" />
        <input type="submit" />
        </form>
        ```
    + 對應的 Controller：
        ```Java
        @RequestMapping("/processForm")
        public String formAction() {
            return "student";
        }
        ```
    + 在 jsp 內使用 param 物件 + form 的 name 屬性可以讀到該 form 的值
        ```jsp
        Student Name: ${param.studentname}
        ```

### 3-4 前端 link 標籤 超連結 連結到 controller 字串的方式
+ 直接編寫在 href 的值內就會連結到該 controller
    ```xml
    <a href="showform">show form page</a>
    ```
+ 如果 first Controller 有寫 `@Controller + @RequestMapping("/student")` 下方的方法就是巢狀請求

## 4. Spring model
![image info](./images/spr-mvc-DispatcherServlet.png)

+ Spring 的 Model 可以裝任何的物件
+ Model 會在 Controller 內使用
+ 可以裝 String, objects, 從資料庫拿到的物件等
+ 可以放多個物件
+ jsp 可以透過 model 拿到資料


#### 4-1-1 編寫有 Model 的 controller
+ 需寫在 Controller 方法內的括弧號內
+ 放物件則使用 Model 內的 addAttribute() 方法，裡面有兩個參數，第一個是物件的名字 (字串)，另一個是物件
    ```Java
    @GetMapping("/processForm2")
    public String demoModel(HttpServletRequest request, Model model){
        String theName = request.getParameter("studentname");
        String result = "Hi" + theName + "!!";
        model.addAttribute("message", result);
        return "student";
    }
    ```

#### 4-2-2 在 jsp 內拿到 model 資料的方式
    直接使用 ${attribute 的 key}
    student2_model.jsp 內範例程式：
    回傳訊息：${message}

## 5.http 請求參數 request params
### 5-1 用 @RequestParam 綁定請求參數
+ 除了上一節使用原生 Servlet 的 HttpServletRequest 的 getParameter 方式取得請求的參數以外，Spring MVC 也提供 `@RequestParam` 的方式拿到請求參數，寫起來較為精簡方便，
寫在 Controller 方法參數內：

    ```Java
    @GetMapping("/processForm3")
    public String demoModel(@RequestParam("studentname") String theName, Model model){
    // 下方就可以用 theName 直接拿到請求的參數
    return "student";
    }
    ```
+ 使用 `@RequestParam` 重點：
    1. Spring 會讀取請求參數內對應的值：studentname
    2. 並且把該值綁定在theName 變數

### 5-2 Controller 層級的 RequestMapping
若RequestMapping 的註釋寫在 Controller 上面，表示本 Controller 以下的方法請求路徑前
都會加上 Controller RequestMapping 註釋的字串：

第一個方法的路徑變為：member/show
第二個方法的路徑變為：member/add

此種方式指定 RequestMapping 可以幫助 url 路徑的分類，使用上或開發上有些時候較為方便。

```Java
@Controller
@RequestMapping("member")
public class ControllerLevelMapping{

    @RequestMapping("/show")
    public String showMember(){
        return "somepage";
    }
    @RequestMapping("/add")
    public String addMember() {
        return "somepage2";
    }
}
```

## 6.Spring Form Tags
### 6-1 Spring MVC 的 Form 標籤
+ Spring MVC 的 Form 標籤可以直接綁定表格內的資料
+ 自動把值放入 Java Bean 或 Spring Bean
+ Spring MVC 的 Form 標籤會自動產生一些 html 標籤，節省開發時間

 
### 6-2 🎇🎇常見的Form 標籤 🎇🎇
+ 其它 Form 標籤：

    | Spring form 區塊 | form:form       |
    |------------------|-----------------|
    | 可輸入字串       | form:input       |
    | 可輸入多行字串   | form:textarea    |
    | 多項勾選功能     | form:checkbox    |
    | 單項勾選         | form:radiobutton |
    | 下拉式表單       | form:select      |

### 6-3 Spring MVC form tag 範例
+ 加入 form 標籤須先在 jsp 上放加入依賴

    ```xml
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    ```

![image info](./images/spr-mvc-form-ex-car.png)

+ Controller 內 Model 參數的寫法：
    + Model 為在 Controller 與 View 之間傳遞的物件
    + 把 Car 物件加到 model 內，需使用 model 的 `addAttribute()` 方法
    + model的 `addAttribute()` 可以放兩個參數，第一個為要放物件的自訂名稱，第二個可以是任何 Java 物件
        ```JAVA
        @GetMapping("/showCarForm")
        public String showCarForm(Model model) {
          model.addAttribute("carBean", new Car());
          return "car-form";
        }
        ```

### 6-3 Spring MVC form tag 範例
+ 在JSP 的Form 表單中如何讀取 Model 的資料:
```XML
  <form:form action="carAction" modelAttribute="carBean" method="post">
    brand: <form:input path="brand"/>
  <br/>
    color: <form:input path="color"/>
  <br/>
```
```Java
// Carcontroller.java 內的 showCarForm 方法
  @GetMapping("/showCarForm")    
  public String showCarForm (Model model){
    model.addAttribute("carBean", new Car());
    return "car-form";
  }
  ```
+ 使用spring 的 \<form:form> 在 form 內使用 `modelAttribute` 代表 model 帶過來的物件，搭配底下 path 代表物件內的屬性

+ 參考 `@ModelAttribute 2 種用法與解釋`
    + https://b0444135.medium.com/springmvc-modelattribute-2%E7%A8%AE%E7%94%A8%E6%B3%95%E8%88%87%E8%A7%A3%E9%87%8B-modelattribute-used-on-method-and-parameter-db70284c3344


### 6-3 Spring MVC form tag 範例
↠ 在 Form 表單中如何拿到 Model 內物件的值
   <form:form action="carAction" modelAttribute="carBean" method="post">
      brand: <form:input path="brand"/>
      <br/>
      color: <form:input path="color"/>
      <br/>
↠ 以上述範例為例，若是取得 modelAttribute 的資料
↠ 其中 path 內的屬性等於使用 model 物件內 Car 中的 getBrand()，getColor()等方法
↠ 送出有 modelAttribute 的 Form 表單
↠ 以上述範例送出的狀況，若是 送出(submit) 的話等於使用model物件內Car中的
   setBrand(), setColor()等方法

↠ 送出有modelAttribute 的 Controller 處理方式:
↠ 在處理 Request 的方法內加入 @ModelAttribute 的參數，並加上送物件的屬性名稱
   @PostMapping("/carAction")
   public String formAction(@ModelAttribute("carBean") Car car, Model model) {
       System.out.println("car brand: " + car.getBrand());
       return "car-response";
   }
↠ @ModelAttribute：綁定 Form 的表單在物件上
↠ 送出後的下一頁 jsp 如何取值：
↠ 使用 ${ModelAttribute 物件名稱.屬性名稱}
   <h2> car: </h2>
   <br/>
   ${carBean.brand} and ${carBean.color}

#### 6-3-1 範例實作步驟
(1) 創建裝資料的物件 Java Bean
(2) 創建 Controller
(3) 創建 HTML form
(4) 把 form 加入 spring form tag
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
(5) 測試回應

### 6-4 Spring Form 下拉選單
下拉選單用法：
↠ 用 <form:select path="OOO" > 搭配 <form:option value="xxx" label="xxx"/>
↠ 其中 path="OOO" 為某一個Java屬性 (根據ModelAttribute的物件)
    form:option value="xxx" 則是要給Java該屬性的值
↠ 需在 Car 物件加入 country 屬性和 Getter, Setter
↠ 下拉選單(版本一)用法：
   <form:select path="country">    //country 要與 Car.java內的private String屬性名稱一致
       <form:option value="DE" label="Germany"/>
       <form:option value="JP" label="Japan"/>
       <form:option value="KR" label="Korea"/>
       <form:option value="US" label="US"/>
   </form:select>
   ↠ value 為真實的值，label 為顯示給前端頁面看的值
   ↠ 送出(Submit)後，Spring 會執行setCountry(...)
   ↠ 寫完之後，送出資料做一個response 測試

↠ 下拉選單(版本二)用法：
   ↠ 若值是從Java 物件內取出的寫法
   ↠ 先加入含有資料的建構子
   public Car( ) {    //要先建一個 private LinkedHashMap<Str, Str> countryOptions;
       countryOptions = new LinkedHashMap<>();
                         //key, value/label
       countryOption.put("DE","DE");
       countryOption.put("JP","JP");
       countryOption.put("KR","KR");
       countryOption.put("US","US");
   }

   ↠ 若值是從 Java 物件內取出，jsp內拿到值的寫法 jsp 內拿到值的寫法
   ↠ 編寫完成後，測試 response 頁面是否可以拿到值
      <!--表單版本2-->
      <form:select path="country">
          <form:options items="${carBean.countryOptions}"/>
      </form:select>

### 6-5 Spring Form 的單選選項 | Radio Button
↠ Spring Form 單選選項的編寫方式
     車種：
     <form:radiobutton path="carType" value="電動車"/>電動車
     <form:radiobutton path="carType" value="汽油車"/>汽油車

↠ 到 Car 物件增加 carType 屬性，getter、setter
↠ 表單送出(Submit)時，Spring會執行setCarType(...)

### 6-6 Spring Form 多選項的 checkbox
Spring Form 多選項 checkbox 編寫方式
  內容設備：
  <form:checkbox path="addition" value="天窗" />天窗
  <form:checkbox path="addition" value="尾翼" />尾翼
  <form:checkbox path="addition" value="倒車雷達" />倒車雷達
    ↠ 到 Car 物件增加 addition 屬性， getter, setter
    ↠ 因可能會有多項，所以要增加陣列的屬性
    ↠ private String[] addition;
    ↠ 表單送出 (Submit) 時，Spring 會執行 setAddition(...)
    ↠ jsp 加入 jstl 的 tag 引入 jstl ，方便顯示 List 的資料    //遍歷、迭代
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


## 7. 轉換為Java設定的Spring專案 (no XML)

### 7-1 基本設定
↠ 增加以下Annotation：為 SpringMVC 專案最少需要的設定
↠ 新增 package com.jerrymvc.springdemo.config 然後加 Class WebAppconfig 實作
   WebMvcConfigurer
    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackages="com.jerrymvc.springdemo")
    public class WebAppConfig implements WebMvcConfigurer {

    上方設定等於原本XML裡面的
     <context:component-scan base-package="com.jerrymvc.springdemo"/>

#### 7-1-1 設定靜態資源的位置
原本 xml
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

轉為 Java 設定
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
設定靜態資源的位置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/view/css/");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("/WEB-INF/view/image/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/view/js/");
    }

#### 7-1-2 設定 DispatcherServlet
只須寫一個 Class 並繼承AbstractAnnotationConfigDispatcherServletInitializer(AACD)物件，
就會自動跑出 DispatcherServlet 相關的設定：
AbstractAnnotationConfigDispatcherServletInitializer 在初初始化過程實際就是
原 web.xml 中建立 ContextLoaderListener 與 DispatcherServlet 的過程。
   @Override
   protected Class<>[] getRootConfigClasses() {
       return null;
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
       return new Class[] {WebAppConfig.class};
   }

   @Override
   protected String[] getServletMappings() {
       return new String[] {"/"};
   }

   @Override
   protected Class<?>[] getRootConfigClasses() {
       return null;
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
       return new Class[] { WebAppConfig.class };
   }

   @Override
   protected String[] getServletMappings() {
       return new String[] {"/"};
   }

分別是
(1) getRootConfigClasses：讀取使用者自訂需掃描的 Bean 設定的 Class
(2) getServletConfigClasses：讀取 Servlet 相關的設定
(3) getServletMappings：Controller 映射的位置(通常為 /)

以上Annotations取代原本的 xml 設定
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/spring-mvc-demo-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
<servlet-mapping>

### 7-2 刪除在 xml 檔案內設定 Spring 相關的程式
    (1) 刪除 webxml 內相關 Spring 的設定
    (2) 直接刪除 spring mvc demo servlet.xml 檔案
    (3) 進行測試


## 8. 在 SpringMVC 中加入 Hibernate
### 8-1 先載入資料庫和連線池需要的依賴程式
↠ 請到Maven 的pom.xml 加入
```xml
   <dependency>
   <groupId>com.microsoft.sqlserver</groupId>
   <artifactId>mssql-jdbc</artifactId>
   <version>9.4.1.jre11</version>
   </dependency>

   <dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-core</artifactId>
   <version>5.6.7.Final</version>
   </dependency>

   <dependency>
   <groupId>com.zaxxer</groupId>
   <artifactId>HikariCP</artifactId>
   <version>5.0.1</version>
   </dependency>

   <dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-orm</artifactId>
   <version>${spring.version}</version>
   </dependency>

   <dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-tx</artifactId>
   <version>${spring.version}</version>
   </dependency>
```

### 8-2 加入 Hibernate、連線池、資料庫的設定
```java
public Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", org.hibernate.dialect.SQLServer2016Dialect.class);
    properties.put("hibernate.show_sql", Boolean.TRUE);
    properties.put("hibernate.format_sql", Boolean.TRUE);
    properties.put("hibernate.hbm2ddl.auto", "update");
    return properties;
}
  
@Configuration
@EnableTransactionManagement
public class RootAppConfig {   //從Hibernate-web-teacher直接拉AppConfig,改名成RootAppConfig
    @Bean                       // ①Sessionfactory改成ToScan(URL)  ②datasource更改DB名稱
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan("com.ywmvc.springdemo.model");
        return sessionFactory;
    }

@Bean
public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager txManager = new HibernateTransactionManager();
    txManager.setSessionFactory(sessionFactory().getObject());
    return txManager;
}

public HikariDataSource datasource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=SpringMvcDB");
    config.setUsername("thermos");
    config.setPassword("12345678");
    config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    config.setAutoCommit(false);
    config.setMaximumPoolSize(5);
    HikariDataSource ds = new HikariDataSource(config);
    return ds;
}
```

記得把該 Java 加到此設定中 --
```Java
@Override
protected Class<?>[] getRootConfigClasses() {
    return new Class[] {RootAppConfig.class};
}
```

## 9. SpringMVC的三層式架構

### 9-1 網路應用程式的三層式架構

三層式架構主要使用在應用程式後端場景，不只Java的程式有這種結構，其他程式語言也有。將不同功能的
程式拆分開，提高可維護性。
每個層次都有不同的職責，而不是將所有的程式碼都寫在同一個分類。
三層式架構為以下三層：
↠ 表現層：也就是Controller，應該負責接收前端的http request，並請Service處理，最後將資料
   做http response。回應的範疇包括狀態碼(status code)、標頭(header)與主體(body)等。
↠ 業務邏輯層：又稱作Service，會被Controller呼叫。它負責根據請求來進行資料處理，並回傳結果。
      也可能被其他 Service 呼叫。
↠ 資料持久層：擔任與資料庫溝通的媒介，會被 Service 呼叫。就是 DAO(Data Access Object) 。

對不同層次賦予各自的職責，可以達到分工，而相同的程式碼也能方便地重複利用。當程式專案的規模
變大後，便可感受其較高的維護性。


### 9-2 SpringMVC 對三層式架構的支援：

在 SpringMVC 中可以使用 @Service 表示業務邏輯層，@Repository 表示資料持久層，
@Controller 表示表現層：

以下註釋都是 Class 層級的註釋，需標註在 Class 上方
  ↠ @Service業務邏輯層：編寫業務邏輯的程式
  ↠ @Repositpry資料持久層：編寫DAO程式，在註解了@Repository的類上，如果資料庫操作中拋出了
      異常，拋出的是翻譯後的spring專屬資料庫Exception，是可以跨資料庫的一種Exception。
  ↠ @Controller資料表現層：負責接收http request和回傳http response，並決定回傳某頁面或
      回傳 JSON 或其他格式。



(1) 只有一兩個的時候，用 @RequestParam
(2) 多個，用 @ModelAttribute

Bean Spring 控管物件
 (1) XML
 (2) Component Annotatoin
 (3) Configuration

 <br/>

 # Spring Boot notes

