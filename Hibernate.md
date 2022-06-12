# Hibernate 資料庫應用程式開發
### Object-Relational Mapping
+ ORM：將物件導向程式中的物件，對應到關聯式資料庫中相關欄位資料的技術，主要使用在 MVC 架構
中的 Model 的 Domain Object 部分(Java Bean)。
+ 資料庫遷移時可以不用重寫 SQL 敘述，由 Hibernate 產生 JDBC 的 Java 程式並執行該資料庫的 SQL Dialect。
+ 實務上，JDBC和ORM技術會同時使用，因過於複雜的 SQL 語法寫原生的效率較佳，也因有些資料庫的 SQL(特殊功能) 無法使用 Hibernate 產生。
+ 一個 Java 物件會對應資料庫一筆資料 row，這個動作稱為 Mapping(對應)。
+ Java程式透過 Hibernate，由 Hibernate 幫我們操作 JDBC ，達到與資料庫互動的功能。(JDBC 效能比 Hibernate 佳)




### 準備 Hibernate 框架需要的組態資訊
可以用多種方式設定資料庫所需的連線資訊(Hibernate Configuration File)設定檔
1. 寫 Java 程式: org.hibernate.cfg.Configuration
2. 使用hibernate.properties設定檔
3. 使用hibernate.cfg.xml設定檔(最常用)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuratoin DTD 3.0//EN"
    "http://www.hibernate.org/dtd/hibernate=configuration-3.0.dtd">
```

```xml
<hibernate-configuration>

  <session-factory>

    <!-- JDBC Database connection settions -->
    <property name="connection.driver_class">com.microsoft.sqlserver.jdbc.SQLServerDriver</property>
    <property name="connection.url">jdbc:sqlserver://localhost:1433;databaseName=資料庫名稱</property>
    <property name="connection.username">使用者帳號</property>
    <property name="connection.password">使用者密碼</property>

    <!--Select our SQL dialect-->
    <property name="dialect">org.hibernate.dialect.SQLServerDialect</property>

    <!--在 console 顯示 Hibernate 執行了什麼 SQL 語法-->
    <property name="show_sql">true</property>

    <!--顯示的 sql 是否要排列整齊-->
    <property name="format_sql">true</property>

    <!--hibernate 內建連線池-->
        <property name="connection.pool_size">2</property>

        <!--後面學到 current session再打開
        <property name="current_session_context_class">thread</property>
        -->
    </session-factory>
  </hibernate-configuration>
```

#### 說明設定檔案裡面的意義，以及常用的屬性設定
+ 設定檔基本語法：`<property name="屬性名稱">屬性值</property>`
+ Hibernate.dialect：
  + 告訴 Hibernate 產生 SQL 語法時，產生適合特定資料庫
  (ex. MySQL, MSSQL) 的 SQL 語法規格
  + 必需實作 org.hibernate.dialect.XXX 該介面的類別全名
+ hibernate.connection.driver_class: 資料庫驅動程式的 Driver Class
+ hibernate.connection.url: 資料庫連線的 URL
+ hibernate.connection.username：資料庫連線使用者帳號
+ hibernate.connection.password：資料庫連線使用者密碼
+ hibernate.connection.datasource：用來設定 JNDI DataSource 
  + 例如：java:/comp/env/xxxxxxx/xxx
+ hibernate.connection.show_sql: 是否在 console 印出 Hibernate 所產生的語法(true/false)
+ hibernate.format_sql: 排版上述印出的 SQL，會換行（可讀性較高）
  + 註: xml 內設定不用寫前面的 hibernate 也可以。
  + ex: `<property name="connection.username">sa</property>`
  + https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#database-diatect

**1.** 新建上課所需的表格table
```SQL
      CREATE DATABASE hibernateDB

      USE hibernateDB

      CREATE TABLE company(
      companyId int primary key NOT NULL,
      companyName nvarchar(50) NOT NULL,
      )
```

**2.** 新建 Java 物件對應表格

**3.** 新建 Table 與 Java 物件之對應設定檔案 (xxx.hbm.xml) (Mapping動作) <br>
   新建 CompanyBean 物件再寫對應的 xxx.hbm.xml
```xml
   public class CompanyBean{
   private int companyId;
   private String companyName;

   //....constructor, getter and setter
   }
```

**4.** 在 hibernate.cfg.xml 內註冊剛剛產生的 hbm 檔案 <br>
   + 用來定義 Hibernate Persisten Class 與資料庫 Table 間的對應關係，可使用多種方式定義： 
     1. 透過 xxx.hbm.xml 檔案設定：與 Persistent Class 放置在相同目錄，<br>
        需要在 hibernate.cfg.xml 檔中設定其位置: <br>
        例如：`<mapping resource="tw/jerryhibernate/model/Users.hbm.xml"/>`
     2. 利用 Hibernate 自訂的 Annotation 語法：直接定義在 Java 程式內，不需要設定檔。
     3. 利用 JPa (Java Persistence API) 規格定義的 Annotation 語法：<br>
        直接定義在 Java 程式內，不需要設定檔。 <br>
        + 註：2. 3. 其實只有些微差異，Hibernate官方建議使用JPA規格來定義，避免因Hibernate版本問題而產生錯誤。
        + example: 透過 xxx.hbm.xml 檔案設定
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
  <class name="對應之Java物件" table="對應之表格">
    <id name="Java物件內ID屬性名稱" type="Hibernate對應型別">
      <column name="表格內欄位名稱" />
      <generator class="給予ID的方式" />
    </id>
    <property name="Java物件內一般屬性名稱" type="Hibernate對應型別">
      <column name="表格內欄位名稱" />
    </property>
  </class>
</hibernate-mapping>
```

#### 常用的 Hibernate 對應型別(Hibernate TypeRegistry key)
|ANSI SQL Type    | Hibernate TypeRegistry key(s) | Java Type           |
|-----------------|-------------------------------|---------------------|
|VARCHAR,NVARCHAR |string, java.lang.String       | java.lang.String    |
|INTEGER, INT     | integer,int,java.lang.Integer | int,java.langInteger|
|TIME             | time,java.sql.Time            | java.sql.Time       |
|TIMESTAMP        |timestamp,java.sql.Timestamp,<br>java.util.Date    | java.util.Date      |
|BLOB             |blob, java.sql.Blob            | java.sql.Blob       |

+ 其他 Type 詳情請見 Hibernate 官方文件：User Guide > 2.Domain Model > 2.3 Basic Type
+ docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#basic

+ 進行 O/R Mapping所需的映射資訊
+ example. 透過 xxx.hbm.xml 檔案設定

`<generator class="給予ID的方式"/>`
+ 給予 ID 的方式有下列 
  1. assigned: 自己編寫程式給予id
  2. identity: 採用資料庫提供的 Primary 生成機制 
     + 例如-在SQL Server內產生Table時有設定 identity(1,1)
  3. sequence: 採用資料庫提供的 Sequence 機制(Oracle與PostgreSQL常用)
     + 例如-在SQL Server內CREATE SEQUENCE, 可在資料庫>可程式性>順序
  4. foreign:使用外來鍵當作本表格主鍵，搭配`<one-to-many class="ClassName"/>`使用

Example: 透過 xxx.hbm.xml 檔案設定
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
  "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
  <class name="tw.hibernate.model.CompanyBean" table="company">
    <id name="companyId" type="java.lang.Integer">
      <column name="companyID"/>
      <generator class="assigned"/>
    </id>
    <property name="companyName" type="java.lang.String">
      <column name="companyName" sql-type="nvarchar(60)" />
    </property>
  </class>
</hibernate-mapping>
```
註: 若欄位是字串 (String) 時 sql-type 沒有指定，則會是 varchar(255)

記得回到 hibernate.cfg.xml 註冊對應關係
`<mapping resource="tw/jerryhibernate/model/CompanyBean.hbm.xml"/>`

這樣在 Hibernate 啟動時，hibernate會先去讀取hibernate.cfg.xml，並透過`<mapping/>`
去檢查 Java 物件與資料庫內表格對應關係。<br>
若有問題，會拋出有關 SessionFactory 的錯誤訊息。


#### 與 Hibernate 框架運作有關的進階資訊
自動新建或更新資料庫內的 Table (補充選用，不一定要使用)
`<property name="hbm2ddl.auto">update</property>`

+ **create:** 每次啟動Hibernate，新建SessionFactory物件時，都會刪除現有的表格，然後根據
  Java Entity 類別內或類別對應的 Mapping(xxx.hbm.xml)重新產生表格，原有的資料庫會消失。
  通常用在系統要初始化時。

+ **create-drop:** 新建 SessionFactory 物件時，根據類別的映射資訊重新產生表格，關閉
  SessionFactory時，自對刪除表格。

+ **update:** 新建 SessionFactory 物件時，若表格不存在就新建表格；如果表格存在，根據類別的
  映射資訊自動更新表格結構，如果 Entity Class 內有新的屬性(or getter/setter)，
  Hibernate會自動插入新的欄位，即使表格結構改變，表格內原有的資料仍存在而不會刪除他們
  (不會刪除表格中已有的行與列)。測試常用，不適合正式上線產品(相容性問題)。

+ **validate:** 新建 SessionFactory物件時，根據Entity Class內的註釋或mapping設定檔
  (xxx.hbm.xml)驗證表格結構，只會和資料庫中的表格進行比較，不會創新表格，若表格結構不同，
  將會丟出例外，通常不做此設定，因為功能不能相容所有資料庫。

+ 若沒設定 hbm2ddl.auto 屬性，則不作上述任何動作。

#### Hibernate 讀取設定檔案的程式
讀取 hibernate 的設定檔案程式片段:
```
StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
SessionFactory factory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
```
註: 若configure()內沒有寫，會直接尋找src資料夾內的hibernate.cfg.xml(預設)，<br>
因此，若您的hibernate.cfg.xml檔名有特殊需求，則需要改成您要的名稱。

**5.** 用 Hibernate 程式新建資料來測試 

   1. Example 1 - 增加一筆公司 (DemoCompanyBeanActionEx1.java)
         ```
         StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
         SessionFactory factory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
         Session session = factory.openSession();
         session.beginTransaction();

         CompanyBean cpyBean = new CompanyBean(1001, "Google");
         session.save(cpyBean);

         session.getTransaction().commit();
         session.close();
         factory.close();
         ```
   2. Example 2 - 增加一筆公司 (DemoCompanyBeanActionEx2.java)
       ```
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        SessionFactory factory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        Session session = factory.openSession();
        session.beginTransaction();

        CompanyBean cpyBean = new CompanyBean(1002,"Facebook");
        Serializable identifier = session.save(cpyBean); //拿到identifier(該物件對應資料的ID)
        System.out.println("identifier:" + identifier);

        session.getTransaction().commit();
        session.close();
        factory.close();
        ```

(3) 實作一個
```SQL
USE hibernateDB
CREATE TABLE myHouse(
houseId int primary key NOT NULL,
houseName nvarchar(50)
)
```
新增一筆資料 houseName = TaipeiHouse <br/>
提示: <br/>
1. 新建所需之表格(Table)
2. 新建Java物件對應之表格 
3. 新建Table與Java物件之對應設定檔案(xxx.hbm.xml)
4. 在 hibernate.cfg.xml 內註冊剛剛產生的 hbm檔案 
5. 用 Hibernate 程式新建物件，達到新增資料的效果


### Hibernate 提供的註釋 Annotation
3-1 編寫註釋的位置與優缺點
直接在 Persistant Class 內編寫。
**優點**：不用 xml 檔案做設定，直接寫在 Persistant Class，一目瞭然。
程式讀取 xml 比較消耗資源，且不會經由編譯器檢查錯誤，出錯時比較難發現錯誤的地方，
因此除錯 (debug) 時間較長。

Annotate Java Class
Entity class 實體類別
↠ Java Class 對應到資料庫內某資料表與欄位
↠ 這種 class 通常是一個 POJO (plain old java object) object)，
只有屬性跟方法的簡單 java 類別，也就是 Java Bean
↠ 必須要有不帶參數的建構子 (public or protected)
↠ 通常會有 Getter/ Setter 方法
↠ 若該實體類別要在 detached 時使用，則必須實作 Serializable 介面
↠ 資料庫 Table 若有雙主鍵，也要實作 Serializable 介面
↠ 物件寫出時， 也要實作 Serializable 介面
↠ Entity 可以都先實作 Serializable 介面 ，不會有副作用
↠ 其他細節請見 Hibernate 官方文件 POJO Models 部分
(2.5 Entity Types > POJO Models)

POJO-Models文件:
docs.jboss.org/hibernate/orm/5.5/userguide/html_single/Hibernate_User_Guide.html#entity-pojo

Mapping 的兩種方式
(1) 使用 xxx.hbm.xml 設定檔：與 Persistent Class 放置在相同目錄，需要在
hibernate.cfg.xml 檔案中使用 <mapping> 標籤定義設定檔名稱與位置。

(2) Java Annotation (比較新):
可使用 Hibernate 的 Persist API 或是 JPA(Java Persistence API) 定義的
Annotation 語法。 (Hibernate 官方建議使用 JPA)


3-2 @Entity, @Id, @Table, @Transient, @GeneratedValue, @Column

↠ JPA Annotation 的語法：
@Entity    -宣告在Java類別名稱上方，註明此類別是persistent class且有對應的table。
@Table     -宣告在Java類別名稱上方，註明此Persistent Class對應資料庫內的Table名稱。
@Id        -宣告在屬性 (Field Access)，註明此屬性對應的 Primary Key 欄位。
@Id        -宣告在getter方法(Property Access)，註明此方法可以取得的Primary Key欄位值。
@Column    -宣告在屬性或是 getter 方法，註明對應的欄位名稱(name="column-name")。
@Transient -要求Hibernate讓此屬性不對應Table欄位，
因為Hibernate會自動將屬性對應到Table 欄位。

↠ 注意 1 ：只有對應 Primary Key 欄位的屬性必須使用 @Id 指定，其他屬性可以由 Hibernate
自動對應到 Table 欄位。
也就是說 @Column 可以省略，會由 屬性名稱 自動對應到 欄位名稱 ，若找不到對應欄位會拋
出例外。但不建議省略，寫清楚比較好。

↠ 注意 2 : Table 裡面給的 name 最好要小寫，例如 : @Table(name = "book") 。

↠ JPA Annotation 的語法：
當 @Id 使用 Field Access(定義在屬性)，所有其他設定都要使用Field Access;
反之，如果 @Id 使用 Property Access(定義在getter方法)，所有其他設定都要使用Property
Access。
↠ 混用會產生無法預期的錯誤，因此請避免混用。
- @GeneratedValue：設定對應Primary Key欄位的屬性的資料產生方式，
  等於 xxx.hbm.xml 設定的 <generator> 標籤
  語法：@GeneratedValue(strategy=GenerationType.IDENTITY)
- strategy 選項合法值必須是 JPA 規格定義的 Enum(列舉)型別
  javax.persistence.GenerationType內有：IDENTITY，SEQUENCE，TABLE，AUTO

@Id: GenerationType值的種類：java.persistence.GenerationType
↠ GenerationType.IDENTITY:
Primary Key欄位值由資料庫內資料表Identity自動產生，回傳型別是 long, short 或 int。

↠ GenerationType.SEQUENCE:
Primary Key欄位由資料庫內sequence產生。適用於有支援Sequence的資料庫(例：SQL Server,
Oracle, PostgreSQL) SQL Server是在以下位置設定：資料庫->可程式性->順序

↠ GenerationType.TABLE:
Primary Key欄位值由特定Table產生，是一種模擬的SEQUENCE，因為較耗資源，比較少使用

↠ GenerationType.AUTO(預設值):
Hibernate系統根據底層資料庫，自動決定要使用IDENTITY, SEQUENCE, TABLE。
建議不要使用AUTO，因為指定上面三種設定會比較明確，比較不會發生不可預期的錯誤。

JPA Annotation(Primary Key):欄位值由IDENTITY自動產生
CREATE TABLE SomeTable(
sometableid int primary key identity(1,1),
..
);     對應

@Entity
@Table(name="the_table")
public class SomeTable{
@Id
@Column(name="table_id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Integer sometableid;
// ... 其他屬性對應資料表的欄位及其getter and setter方法
}

3-3 組態檔中說明永續類別的位置
在hibernate.cfg.xml內:
<hibernate-configuration>
<session-factory>
// …
<mapping class="...model.yourClassName"/>
<session-factory>
<hibernate-configuration>

實作範例
CREATE TABLE Department(
deptid int primary key not null identity(1,1),
deptname nvarchar(50)
);

Example(persistence class: Department.java):
@Entity
@Table(name= "department")
public class Department{
@Id
@Column(name="deptid")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int deptid;

@Column(name = "deptname")
private String deptname;

public int getDeptid(){
return deptid;
}

public void setDeptid(int deptid){
this.deptid = deptid;
}
public String getDeptname(){
return deptname;
}
public void setDeptname(String deptname){
this.deptname = deptname;
}

範例(記得在 hibernate.cfg.xml內加入)
<hibernate-configuration>
<session-factory>
//...
<mapping class="...model.Department"/>
</session-factory>
</hibernate-configuration>

新增一筆資料 (DemoDepartmentActionEx1.java)
StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
SessionFactory factory = new MetadataSource(serviceRegistry).buildMetadata().buildSessionFactory();

Session session = factory.openSession();
session.beginTransaction();

Department depart1 = new Department();
depart1.setDeptname("RD");
session.save(depart1);

session.getTransaction().commit();
session.close();
factory.close();


### SessionFactory 介面
4-1 介紹 SessionFactory 的功能
(1) SessionFactory：用來表示 1 個連線的資料庫
↠ 定義在 org.hibernate Package
↠ SessionFactory 物件封裝了 Hibernate 執行環境：
1 個資料庫對應 1 個 hibernate.cfg.xml 設定檔，
1 個 hibernate.cfg.xml 設定檔對應一個 SessionFactory 物件。
↠ SessionFactory 是一個重量級物件：
產生SessionFactory物件需要讀取hibernate.cfg.xml設定檔，也非常消耗資源。
(2) 建立 SessionFactory 物件非常消耗系統資源與時間，因此建立此物件時要有詳細的規劃。
(3) Java 應用程式內建立：
↠ 要在應用程式開始讀取資料庫之前建立 SessionFactory 物件。
可以考慮使用靜態區塊來建立 SessionFactory 物件。
↠ 可使用輔助類別(HibernateUtils.java)的static方法取得SessionFactory物件。
(4) Web 應用程式：
↠ 可經由 Web 應用系統的 ServletContextListener 介面，在系統初始化的時候建立。
↠ 經由適當的組態設定， Web 應用系統可在初始化時，自動產生此物件。

4-2 設計能夠產生 SessionFactory 的程式
SesstionFactory Singleton Design Pattern的建立方式 (HibernateUtil.java)：
↠ 在 Java 類別內宣告 private static 方法產生 SessionFactory 物件。
↠ 在 Java 類別內宣告 SessionFactory 型別的 private static 屬性，
並呼叫上述方法初始化這個 static 屬性。
↠ 為 SessionFactory 型別屬性宣告 public static 的 getter 方法，
方便應用程式的其他地方透過此方法取得 SessionFactory 物件。
↠ 最後規定整個應用程式必須透過 getter 方法取得 SessionFactory ，這樣就可以保證整個
應用程式只使用 1 個 SessionFactory 物件。
↠ static: 類別載入時產生物件， new 時不會被覆蓋，只會產生一次。

public class HibernateUtil{
private static final SessionFactory factory = createSessionFactory();

private static SessionFactory createSessionFactory(){
StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
SessionFactory factory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
return factory;
}

public static SessionFactory getSessionFactory(){
return factory;
}

public static void closeSessionFactory(){
if(factory != null){
factory.close();
}
}

4-3 建立 SessionFactory 的時機
原本
StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
SessionFactory factory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

現在只要
SessionFactory factory = HibernateUtil.getSessionFactory();

### Session 介面
5-1 如何得到Session物件
↠ Hibernate 程式每次存取資料庫之前必須先取得一個 Session 物件，
此物件配置一個可以連上資料庫的實體連線。
↠ 程式必須由 Session 物件開啟交易 (beginTransaction())，才能進行表格的增刪改查。
Session 介面提供許多方法來儲存 查詢 修改 刪除物件所對應的表格紀錄。
↠ 程式可以透過 SessionFactory 物件的 openSession() 與 getCurrentSession()
來取得Session 物件。

5-2 SessionFactory提供的openSession()與getCurrentSession()之比較
session.openSession()
↠ 每次呼叫SessionFactory介面的openSession()方法都會得到一個全新的Session物件
↠ 當程式不需要存取資料庫時，必須執行session.close()方法來關閉session物件
↠ 不需要做任何組態檔設定就可以使用openSession()方法
↠ 可由下方之程式片段看出每次執行openSession()方法都會建立一個全新的Session物件
SessionFactory factory = HibernateUtil.getSessionFactory();
Session session1 = factory.openSession();
Session session2 = factory.oopenSession();
System.out.println(session1);
System.out.println(session2);
session1.close(); //關閉 session
session2.close();

session.getCurrentSession()
↠ 當存取資料庫的程式第一次執行SessionFactory介面的getCurrentSession()方法時
會開啟一個全新的Session物件，然後此Session物件就會綁定在 Hibernate控管的環境內。
↠ 接下來程式每次執行getCurrentSession()方法會得到同一個Session物件。由於交易都需要
在同一個Session物件中進行，因此當程式的交易橫跨多個DAO類別時一定要以此方法來取得
Session物件。
↠ 當程式執行交易的commit()或rollback()方法讓交易結束後，會自動關閉Session物件。
↠ hibernate.cfg.xml 內必須設置
<property name="current_session_context_class">thread</property>
否則程式會丟出No CurrentSessionContext configured的錯誤訊息

用法整理：
(1) getCurrentSession()——
在 hibernate.cfg.xml 中若有設定
<property name="current_session_context_class">thread</preperty>
則可使用Session session = factory.getCurrentSession(); 取得Session物件。
commit 或 rollback 就會自己關閉 session ，因此 session.close() 不需要再寫，
只需要編寫開啟、關閉 Transaction 物件。

(2) 若無設定上述，則必須使用openSession()開啟session，且需要自己寫開啟並關閉
session。在session關閉前，也需要編寫開啟、關閉Transaction物件。


5-3 Session物件的關閉
↠ 在openSession()情況下，當程式不再需要存取資料庫時，一定要執行Session介面的close()
方法關閉Session物件。
↠ 在getCurrentSession()情況下，交易的commit()或rollback()會自動關閉Session物件。
↠ 關閉 Session 物件並不一定等於關閉資料庫的連線，如果 Hibernate 是在提供連線池的
環境下執行，Session的close()方法會將連線物件java.sql.Connection還回給連線池，
而不會關閉它。


### Transaction 介面
6-1 如何取得 Transaction 物件
↠ Transaction 介面是 Hibernate 提供的資料庫交易介面，它覆蓋不同廠商資料庫間交易的機制，
提供一致的程式編寫方式。
↠ 一個交易中對多個表格的異動 (CRUD) 一定要在同一個 session 下進行。
↠ 在 Hibernate 程式中，所有存取資料庫的方法都必須在交易內進行。
即要先開啟Transaction，才能進行資料庫的資料存取。如果成功存取，進行交易的commit()；
如果存取資料失敗，則進行交易的 rollback()。

Transaction 交易建立方式：
(1) 透過 Session 物件啟動 transaction:
Transaction tx = session.beginTransaction();
(2) 透過 Session 物件取得現存的 Transaction 物件：
Transaction tx = session.getTransaction();
(3) 透過 Transaction 物件結束 transaction：
tx.commit();
tx.rollback();


6-2 Hibernate 對交易的支援

Session與Transaction交易建立方式：
(1) 透過SessionFactory的openSession()方法必須編寫程式產生與關閉Session物件。
Session session = factory.openSession();
try{
session.beginTransaction();
//...對資料庫存取程式
session.getTransaction().commit();
}catch(Exception e){
session.getTransaction().rollback();  //任何錯誤都要 rollback
throw e;
}finally{
session.close();
}


(2) 透過SessionFactory的getCurrentSession()方法，
當交易commit()或rollback()會自動關閉 Session，因此不用編寫關閉Session的程式。
Session物件的產生與thread結合，不須要編寫程式碼產生與關閉Session物件。
注意：getCurrentSession()不用自己寫session.close()
因為 commit() 或 rollback() 會做。
Session session = factory.getCurrentSession();
try{
session.beginTransaction();
//...對資料庫存取程式
session.getTransaction().commit();
}catch(Exception e){
session.getTransaction().rollback();  //任何錯誤都要 rollback
throw e;
}

6-3 交易在 Hibernate 應用程式中的重要性
Transaction Interface(介面):
用來表示Database Transaction(交易)
↠ 本身為Not Thread Safe物件：
必須確保1個Transaction只能讓1個thread使用，
讓多個thread共用會產生程式錯誤。
↠ 由Session物件控制：
Transaction必須屬於某個Session，雖然1個Session可以啟動多個Transaction，
但是同一時間最多只能有1個沒有commit()的Transaction。


### 編寫Hibernate程式的通則
7-1 一個完整的 Hibernate 程式
包含以下Java檔與組態設定檔案：
xxxBean.java
xxxBeanDAO.java
xxxBean.hbm.xml (或使用 Annotation Mapping)
hibernate.cfg.xml

7-2 編寫以 Hibernate 技術來存取資料庫的 Dao 類別
DAO是Data Access Object實作存取資料庫的程式。
DAO可以是JDBC程式或是Hibernate程式。
Hibernate技術的DAO內不應該有取得及關閉Session物件，
也不會有啟動與關閉Transaction的動作，
以確保不同的Session與Transaction設計時可以使用。
7-3 Dao 類別在 MVC 架構中扮演的角色


### 永續類別 (Persistence Class)
8-1 將永續設計為 POJO 類別
Entity class 實體類別
↠ Java Class 對應到資料庫內某資料表與欄位。
↠ 這種 class 通常是一個 POJO (plain old java object) object)，
只有屬性跟方法的簡單 java 類別，也就是 Java Bean 。
↠ 必須要有不帶參數的建構子 (public or protected)。
↠ 通常會有 Getter/ Setter 方法。
↠ 若該實體類別要在 detached 時使用，則必須實作 Serializable 介面。
↠ 資料庫 Table 若有雙主鍵，也要實作 Serializable 介面。
↠ 物件寫出時，也要實作 Serializable 介面。
↠ 因此， Entity 可以都先實作 Serializable 介面，不會有副作用。

8-2 永續類別之物件的生命週期


8-3 觸發物件在生命週期改變狀態的相關方法
Session API 轉換物件狀態的方法：
↠ 由Transient到Persistent可用 - save(), saveOrUpdate(), persist(), merge()
↠ 由Detached到Persistent可用 - update(), saveOrUpdate(), merge()
↠ 注意：merge()會回傳新的永續物件，原始物件不改變
另外, updated(), saveOrUpdate(), merge() 如果不需要讓物件橫跨多個
Session(又稱Persistent Context)，就不需要使用。


### 永續類別之狀態
9-1 Transient(臨時)狀態
Transient (臨時) 狀態：物件剛剛產生 (new) 還沒有跟 Persistent Context 結合的
狀態，也就是 還沒 session.save() / persist() / saveOrUpdate() 的時候，在這
個狀態下物件 尚未 與資料庫發生任何的關係，因此也不會對應資料庫中的資料。

9-2 Persistent(永續)狀態
當臨時物件經由Session物件的 save() / persist() / saveOrUpdate()方法儲存到表格內，
或經由 Session 物件的get() 或 load()方法由表格讀出的物件，這兩類物件將會存放在Session
的快取(緩衝區)內，受到 Hibernate 的監控，我們稱這樣的物件為永續物件。
只要Session物件沒有關閉，永續物件的屬性如果有任何改變，在之後 commit()時也會呼叫flush()
方法，更新資料庫的資料表內與永續物件對應的資料。

9-3 Detached(分離)狀態與Removed(移除)
Detached(分離)狀態：
曾經的 Persistent 物件，在經過session.close()之後，由於Session已經關閉，目前沒有跟 Persistent Context 結合，在這個狀態下物件狀態的改變不會對資料庫資料造成任何影響。

Removed(移除)狀態：
當 Session 呼叫session.remove()或session.delete()時，Persistent物件對應的資料
已經失效便進入 Removed 狀態，該物件會交由Garbage Collection (GC)進行資源回收。


### Session 介面儲存物件
10-1 編寫 JUnit 程式來測試 Session 介面提供的方法
↠ 單元測試 (Unit Testing)：以程式碼的最小單位進行測試，保護程式邏輯不會在系統維護的
過程中遭到破壞，也進一步確保維護中的程式碼品質。
↠ 需經驗豐富的程式設計師來決定如何編寫單元測試，效率比較高。
↠ Java 生態系通常使用 JUnit 套件 來進行單元測試。

↠ 載入套件 (JUnit5)
<dependency>
<groupId>org.junit.jupiter</groupId>
<artifactId>junit-jupiter</artifactId>
<version>5.7.0</version>
</dependency>

↠ 常用標註
@Test: 宣告一個方法。
@Test
public void testCreate() {
}
@Test
public void testUpdate() {
}
@BeforeAll: 在所有方法執行以前先執行該方法。
@BeforeAll
public static void setup(){
sessionFactory = HibernateUtil.getSessionFactory();
System.out.println("SessionFactory created");
}
@BeforeEach: 在每一個方法執行以前，都先執行此方法。
@BeforeEach
public static void openSession(){
session = sessionFactory.openSession();
System.out.println("Session created");
}
@AfterEach: 在每一個方法執行以後，執行此方法。
@AfterEach
public static void closeSession(){
if(session != null)session.close();
System.out.println("Session closed\n");
}
@AfterAll: 所有方法執行完畢後，執行此方法。
@AfterAll
public static void tearDown() {
if (sessionFactory != null) sessionFactory.close();
System.out.println("SessionFactory destroyed");
}


10-2 Session 的 save() 方法 ***
↠ session.save()會將臨時(Transient)物件儲存到表格內成為永續物件。
如果資料庫內使用自增Primary Key機制，則Hibernate會透過JDBC立即發送SQL指令，
將物件對應的資料寫入表格。
↠ 如果是程式給的Primary Key，則會等到commit()後才會將物件代表的資料寫進資料庫。
↠ session.save()建議在交易內執行。
↠ 傳回值：物件的ID(OID)，也是該資料的ID，為Serializable型別。

10-3 Session 的 persist() 方法
↠ session.persist() 將一個臨時物件轉變為永續物件。此方法必須在交易內執行。
↠ persist() 儲存物件(參數內物件)時不會產生物件 ID(OID)
↠ persist() 也不能儲存有 OID 的物件，如果有 OID 則會產生以下錯誤
PersistentObjectException: detached entity passed to persist
↠ 之前提到的session.save()物件可以有OID 。
↠ persist()沒有回傳值。


### Session 介面讀取物件
11-1 依主鍵查詢： Session 的 get() 方法與 load() 方法的使用時機與差異
Session的get()與load()都可以依主鍵(pk)讀取表格內的資料，拿到1筆永續物件，但是採用策略不同
Member member = session.get(Member.class,pk);
Member member = session.load(Member.class,pk);
//Member.class：第一個參數表示要讀取之永續物件的類別
//pk：第二個參數表示該物件的主鍵值

↠ load()方法用在讀取的永續物件是真實存在的，此方法會回傳一個代理(proxy)物件，
其內只有id屬性(欄位)有值，而沒有真正去讀取資料庫內的資料。如果後來發現永續物件不存在，
程式會丟出 ObjectNotFoundException。

↠ load()方法會延遲讀取(lazy loading,該機制後面詳細說明)永續物件，直到程式需要使用永
續物件的屬性時 (ex: 執行永續物件的 getXXX() or toString()) 才真正對資料庫下達SQL敘述
進行讀取資料，此中機制稱為Lazy-Loading，該機制後面詳細說明。

↠ get()方法會直接讀取資料庫內的資料，傳回要讀取的永續物件，若該資料不存在，則回傳null。


11-2 flush() 方法、 delete() 方法、 update() 方法、 saveOrUpdate 方法
↠ flush():
同步目前永續物件內容與資料庫資料。
以下狀況Hibernate會預先自動執行flush()方法
1. 當交易實行 commit() 以前
2. 當執行查詢語句以前

↠ delete():
用來刪除資料時使用   //執行 delete() 或是不動作
session.delete(companyBean);

↠ update(): 分離(Detached)轉成永續(Persistent)狀態
Session session1 = sessionFactory.openSession();
CompanyBean bean = session1.get(CompanyBean.class, 1001);

    session1.close();   //此時bean物件變成分離狀態(Detached)
    
    bean.setCompanyName("iii");   //只修改物件屬性，資料庫內資料不會改變

    //關閉 session 時 bean 會變為 Detached 狀態，
    //可以用session.update()方法回到Persistent狀態，才可以存取，如下程式碼：

    Session session2 = sessionFactory.openSession();
    Transaction tx = session2.beginTransaction();
    
    session2.update(bean);
    tx.commit();
    session2.close();


↠ saveOrUpdate():
Transient, Detached 轉換成 Persistent
↠ 如果方法參數本身已經是 Persistent ，則不做任何事情。
↠ 如果方法參數沒有設定 PK 值或是利用 PK 無法從資料庫載入物件，則會執行 save() 方法。
↠ 如果不是上述狀況，則執行 update 方法。
flush()方法：強制將緩衝區中暫存的資料送至資料庫執行處理。

11-3 merge() 方法、 clear() 方法、 evict() 方法、 close() 方法
↠ merge()方法


↠ clear()方法
↠ 清除所有session內的永續物件。在Session快取中受到Hibernate監控的所有物件都不再
與該session發生關聯，也就是原來的永續物件都轉變為分離物件 (Detached)。
↠ 使用此方法時， Hbernate 不會對資料庫發出任何 SQL 敘述。
↠ 請在交易內使用此方法。

↠ evict()方法
從 session 清除一個物件（參數內物件）。

↠ close()方法
session.close()：關閉session，釋放資源。


### 編寫Dao類別
12-1 設計 Dao 介面與實作 Dao 介面的類別
DAO(Data Access Object)建立方式：
↠ 使用Hibernate技術的DAO：使用Session API的save(), delete(), get()等方法來處理資料庫
內的資料。
↠ Hibernate技術的DAO內不應該編寫建立Session與關閉 Session 的動作，也不會有啟動與關閉
Transaction物件，以確保不同 Session 與 Transaction 設計時可以使用相同DAO。

設計DAO介面：
public interface CompanyDAOInterface{
CompanyBean insert(CompanyBean comBean);
CompanyBean select(int comId);
List<CompanyBean> selectAll();
CompanyBean updateOne(int comId, String comName);
boolean deleteOne(int comId);    
}
(CompanyDAO.java) 設定與取得 Session ，但不負責開啟與關閉 Session:
private Session session;

    //建構子，方便其他物件呼叫它
    public CompanyDAO(Session session){
        this.session = session;
    }
    public Session getSessiont(){
        return session;
    }

12-2 Dao 類別的新增、刪除、修改、查詢
↠ 透過 Session 執行新增（已有該筆就不新增）
public CompanyBean insert(CompanyBean cBean){
CompanyBean companyBean=session.get(CompanyBean.class, cBean.getCompanyId());
if(companyBean == null){
session.save(cBean);
return cBean;
}
}

↠ 透過 Session 執行查詢（Select）
(1) 查詢Company table內是否有該筆companyId的資料，如果沒有則回傳 null
public CompanyBean select(int comId){
return session.get(CompanyBean.class, comId);
}

(2) 查詢多筆資料
public List<CompanyBean> selectAll(){
Query<CompanyBean> query=session.createQuery("from CompanyBean", CompanyBean.class);
return query.list();
}

↠ 透過 Session 執行修改（updateOne）
有該筆資料就進行修改，沒有就回傳null。
註：更新資料不需要session.save()或session.update()，因為它是從資料庫取出，
本身就已經是永續物件（Persistent Objects）
public CompanyBean updateOne(int comId, String comName){
CompanyBean comBean = session.get(CompanyBean.class, comId);
if(comBean != null){
comBean.setCompanyName(comName);
}
return comBean;
}

↠ 透過 Session 執行刪除（deleteOne）
public boolean deleteOne(int comId){
CompanyBean comBean = session.get(CompanyBean.class, comId);
if(comBean != null){
session.delete(comBean);
return true;
}
return false;
}

12-3 Dao 類別的使用 openSession() 與 getCurrentSession() 的差別
↠ openSession()：透過編寫程式產生Session物件，1 個Session可以跨越多個Transaction。
↠ getCurrentSession()：Session物件產生與thread結合，1個Session只能有1個Transaction。


### Service類別
13-1 設計 Service 介面與實作 Service 介面的類別
透過設計Service，可以將 DAO 變為更獨立的物件。若要存取資料，
須透過Service間接取得 DAO，可以提供較彈性的應用程式架構。
Service 類別建立的方式：
(1) 建立全域DAO的屬性變數
(2) 透過Service的建構子初始化DAO物件，當Service物件實體建立時，DAO物件實體也會跟著建立

public interface CompanyServiceInterface{
public CompanyBean select(int comId);
public List<CompanyBean> selectAll();
public CompanyBean insert(CompanyBean comBean);
public CompanyBean updateOne(int comId, String comName);
public boolean delete(int comId);
}

private CompanyDAO comDAO;
public CompanyService(Session session){
comDAO = new CompanyDAO(session);
}

13-2 Service 類別採用委任設計模式將新增、刪除、修改、查詢的工作委託給 Dao 類別來完成
Select
public CompanyBean select(int comId){
CompanyBean theCom = comDAO.select(comId);
return theCom;
}
public List<CompanyBean> selectAll(){
List<CompanyBean> theComs = comDAO.selectAll();
return theComs;
}

Insert 和 updateOne
public CompanyBean insert(CompanyBean comBean){
CompanyBean oneCom = comDAO.insert(comBean);
return oneCom;
}
public CompanyBean updateOne(int comId, String comName){
CompanyBean oneCom = comDAO.updateOne(comId, comBean);
return oneCom;
}

Delete
public boolean delete(int comId){
boolean boo = comDAO.deleteOne(comId);
return boo;
}

13-3 在 Service 類別內的方法定義交易


### 延遲載入
14-1 延遲載入的定義與成因
當程式使用 session.load() 方法載入某個物件或讀取之物件含有 One-Many / Many-Many的
集合成員時，Hibernate不會立即發出SELECT敘述讀取物件或集合成員，而會回傳一個或多個代理
物件(Proxy)，Proxy裡面的屬性值都是null。之後程式需要代理物件的屬性值時(ex: getName())
，Hibernate才會發出SQL SELECT敘述讀取資料庫內對應的資料，這時才組合為一個物件或集合物件。

在JSP程式透過EL來取出代理物件的屬性時，如果Session已經關閉，無法與資料庫保持連線，因此
程式發出的SELECT也無法到達資料庫，就會發生例外：
org.hibernate.LazyInitalizationException: could not initialize proxy no Session

14-2 各種解決延遲載入的作法
避免 org.hibernate.LazyInitalizationException 發生的方式：
(1)若是用註釋設定物件表格應對關係：
透過註釋說明該類別的集合成員不採用延遲加載的方式，亦即要立即載入
@OneToMany(fetch=FetchType.EAGER)
private Set<StockTransaction> stockTrans;

註1: @OneToMany和@ManyToMany預設為(fetch=FetchType.LAZY)
註2: 若是用 xxx.hbm.xml 設定物件表格應對關係，則要設定lazy="false":
<class...lazy="false">
//主鍵與其他欄位設定
</class>

(2)在Session關閉之前呼叫 org.hibernate.Hibernate 的initialize()方法
強制將資料載入Proxy物件。
eg. Hibernate.initialize(friends);

(3)呼叫Persistence Class物件的getter方法，將資料全部抓取完畢之後再關閉Session
(比較推薦的方法)。


14-3 經由自行定義的 Filter 來解決延遲載入
對於Web應用程式，可以用Filter處理Session產生及Transaction的開始與結束。
(1) 符合 MVC 設計模式常見的流程
ⅰ. View顯示畫面供使用者填入資料，並送到 Controller
ⅱ. Controller負責接收資料、驗證資訊、轉換資料、呼叫Model、根據Model執行結果導向View。
ⅲ. View負責顯示Controller準備好的資料。

(2) 根據上述流程，Hibernate程式(屬於Model)與讀取Model資料顯示畫面的程式(屬於View)
不在相同位置。

(3) 由於View在讀取資料時已經脫離Model範圍(因為Session已關閉)，此時抓取Persistent
Class 資料就可能碰到 Lazy Initialization 問題。

(4) 解決 Web 應用程式過早關閉 Session 物件，造成 Lazy Initialization 問題的方式
讓 Session 保持開啟直到 View 執行結束

(5) 新建一個Filter，在它的doFilter()方法內呼叫SessionFactory的getCurrentSession()
取出Session物件、接著啟動交易(beginTransaction())，呼叫chain.doFilter(req,resp)
由被監控的資源開始執行其應有的工作 (例如多個表格資料新增、刪除、修改、查詢)，
等到該資源執行完所有的資料庫存取後再度回到Filter，如果一切正常，執行交易的Commit()，
若執行被監控的資源時拋出任何例外，執行交易的 rollback() 。

(6) 修改Service類別：將所有Service類別內呼叫Transaction之commit()，rollback()
敘述都移除，不要在Service類別內關閉Session。

(7) 提醒：以getCurrentSession()方法得到的Session物件會在交易commit()或rollback()
時自動關閉，因此不用執行 Session.close() 方法。

### Hibernate關聯 (Association)
之前的課程內容著重於一個資料表，而在現實的情況資料表(Table)間是有關聯關係的，以下課程就說明
Table 關聯關係在 Hibernate 如何實作。
常見3種：one-to-one、one-to-many/many-to-one、many-to-many

15-1 關連的類型 , @OneToOne, @OneToMany,@ManyToOne,@ManyToMany
一對一關係： 「教師」「教師詳情」(instructor<=>instructorDetail)
一對多關係： 「客戶」「客戶訂單」一個客戶有許多訂單
(一對多，多對一 差別只在觀點不同)
多對多關係： 「朋友」「群組」一個朋友屬於多個群組，一個群組內也有多個朋友

15-2 單向關連與雙向關連的差別
↠ 單向關聯 (Uni Directional Association)：
在具有關聯的一對 Entity 中，只有一個 Entity 儲存另一個 Entity 的物件參考，
稱為單向關聯，意即只能有一方找到另一方。

↠ 雙向關聯 (Bi Directional Association)：
如果雙方都存有對方的物件參考，稱為雙向關聯，意即彼此都能找到對方。

15-3 一對一關連
create table instructorDetail(
id int not null primary key identity(101,1),
email nvarcher(100) not null,
phone nvarcher(50) not null
)

create table instructor(
id int not null primary key identity(1,1),
instructorName nvarchar(50) not null,
fk_instructorDetail_id int references instructorDetail(id
)

先介紹單向一對一
@Entity
@Table(name = "instructorDetail")
public class InstructorDetail{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    //getter and setter ...

@Entity
@Table(name = "instructor")
public class Instructor{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private int id;

    @Column(name = "instructorName")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_instructorDetail_id")
    private InstructorDetail instructorDetail;
    //getter and setter ...

說明：
(1) @OneToOne(cascade=CascadeType.ALL) 一對一且另一方一起動作
(2) CascadeType有DETACH, MERGE, PERSIST, REMOVE
全部都需要的話就使用 CascadeType.ALL
(3) @JoinColumn 表示在本表格的外鍵欄位

一對一關聯(單向)
如何執行單向一對一關聯？
(1) 兩邊都實體化(new)其物件並設定其值
(2) 由設定 @OneToOne 的那一邊 set 另一邊物件
(3) session.save()主要方，就會儲存兩邊的物件，達到關聯效果


一對一關聯(雙向)
(1) 兩邊都可以互相找到對方
(2) 可由 Instructor Detail 找到 Instructor
(3) 不用變更資料庫表格設計，只需要在Java程式改變即可

在InstructorDetail做以下修改:
(1) 在全域變數的地方加入 Instructor 去找到它
(2) 加上 getter/setter
(3) 加上 @OneToOne 標註






@Entity
@Table(name = "instructorDetail")
public class InstructorDetail{
//...
@OneToOne(mappedBy = "")
private Instructor instructor;
//...

重點說明 mappedBy = "instructorDetail":
↠ 去 Instructor 類別內找到 instructorDetail 屬性
↠ 使用 Instructor 類別裡面的 @JoinColumn 的資訊幫助建立關聯
↠ mappedBy 不可以與 @JoinColumn 同時出現，必須在對面類別


### Hibernate關聯的其他類型

16-1 一對多關連


使用@OneToMany 來設定一對多關係 (One To Many Relationship)
↠ cascade: 設定自己的資料改變時對方的動作，例如：本類別(Entity)內資料刪除，
關聯的對方 Entity 也透過 Hibernate 執行進行資料的刪除，則須設定
cascade = {CascadeType.REMOVE}
↠ mappedBy: 對方類別內設定關聯的Java屬性名稱，
不可與@JoinColumn，@JoinTable同時出現及使用
↠ 使用 @OneToMany(mappedBy="...") 則不可使用 @JoinColumn或是 @JoinTable

16-2 多對一關連
使用 @ManyToOne 來設定多對一關係 (Many To One Relationship)
通常與 @JoinColumn(name=...) 使用，設定其外來鍵 (foreign key(

Example
create table bookUsers(
id int primary key identity(1,1) not null,
username nvarchar(50),
)

create table books(
id int primary key identity(1,1) not null,
booktitle nvarchar(50),
publicYear nvarchar(50),
fk_user_id int references bookUsers(id)
)

一對多(一方Entity)
@Entity
@Table(name="bookUsers")
public class BookUsers{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
private int id;

    @Column(name="username")
    private String username;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="bookuser",cascade=CascadeType.ALL)
    private set<Books> books = new LinkedHashSet<Books>();

多對一(多方Entity)
@Entity
@Table(name="books")
public class Books{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private int id;

    private String booktitle;

    private String publicYear;

    @Column(name = "fk_user_id")
    @Transient
    private int userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_id")
    private BookUsers bookuser;

說明：
↠ @Transient表示請Hibernate該欄位不由本類別給予，因為是外來鍵
↠ @JoinColumn(name="fk_user_id")表示本類別透過哪個欄位(外來鍵)拿到對方物件
↠ @ManyToOne的FetchType預設本來就是EAGER，所以可省略該設定
↠ 記得回到 hibernate.cfg.xml 註冊
<hibernate-configuration>
<session-factory>
...
<mapping class="tw.hibernatedemo.model.BookUsers">
<mapping class="tw.hibernatedemo.model.Books">
...
</session-factory>
</hibernate-configuration>

16-3 多對多關連
Example: 朋友與群組的多對多關係，一個群組可以存多個朋友，朋友也可以屬於多個群組。












create table friends(
frends_id int not null primary key identity(1,1),
frendName nvarchar(50) not null
)
GO

create table groups(
groups_id int not null primary key identity(1,1),
groupName nvarchar(50) not null
)
GO

create table friend_group(
fk_friend_id int not null references friends(friends_id),
fk_group_id int not null references groups(groups_id),
)

範例資料
insert into friends (friendName) values
('Tom'),('Mary'),('Tina')
GO
insert into groups (groupName) values ('high school'),('game'),('work')
GO

insert into friend_group(fk_friend_id, fk_group_id) values
(1,1),(1,2),(2,3),
(3,1),(3,2),(3,3)
GO

使用 @ManyToMany 來設定多對多關係(Many To Many Relationship)
↠ cascade: 設定自己的資料改變時對方的動作，例如本類別(Entity)內資料刪除，
關聯的對方Entity也透過Hibernate執行進行資料的刪除，
則須設定 cascade={CascadeType.REMOVE}
↠ mappedBy: 對方類別內設定關聯的Java 屬性名稱，不可與 @JoinColumn,
@JoinTable 同時
使用 @ManyToMany(mappedBy="...") 則不可使用 @JoinColumn 或是 @JoinTable

@JoinTable: 用來設定Many-to-Many關係的中介Table，有以下屬性設定
↠ name: 資料庫中介 Table 之名稱
↠ joinColumns: 設定目前Table與中介Table相關聯的欄位名稱，也就是目前Table的
Primary Key欄位、中介Table的Foreign Key欄位，使用JoinColumn(name=..)語法設定
↠ inversionJoinColumns: 設定中介Table與對方Table的Primary Key欄位，使用
JoinColumn(name=...) 語法設定

MyGroup 物件：
@Entity
@Table(name = "groups")
public class MyGroup{
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="groups_id")
private Integer groupId;

    @Column(name="groupName")
    private String groupName;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="friend_group",
      joinColumns={@JoinColumn(name="fk_group_id", referencedColumnName="groups_id")},
      inverseJoinColumns={@JoinColumn(name="fk_friend_id",referencedColumnName="friends_id")})
    private Set<Friend> friends = new HashSet<Friend>();
//...constructor, getter and setter

Friend 物件：
@Entity
@Table(name = "friends")
public class Friend{
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="friends_id")
private Integer friendId;

    @Column(name="friendName")
    private String friendName;

    @ManyToMany(mappedBy="friends")
    private Set<MyGroup> groups = new HashSet<MyGroup>();
//...constructor, getter and setter

記得回到 hibernate.cfg.xml 註冊
<hibernate-configuration>
<session-factory>
...
<mapping class="tw.hibernatedemo.model.MyGroup"/>
<mapping class="tw.hibernatedemo.model.Friend"/>
...
</session-factory>
</hibernate-configuration>


### Hibernate Query Language (HQL)
17-1 HQL 與 SQL 的異同
↠ Hibernate Query Language是一種功能強大，語法類似SQL的查詢語言。
↠ 查詢語言操作的對象是類別與類別的性質(屬性、方法)，而非表格語表格的欄位。
↠ Hibernate會將HQL轉為表格語欄位的操作，進行資料庫內資料表的CRUD等功能。
↠ HQL語言不分大小寫，但是Java類別名稱與屬性名稱有區分大小寫，而其他關鍵字不區分大小寫。
↠ HQL是Hibernate官方推薦的查詢語言。
↠ HQL不支援Insert語法。

17-2 Query 介面
↠ Hibernate的HQL都必須經由Query介面提供的方法來執行。
↠ Session介面的createQuery(String HQL)可產生Query物件。
↠ Query介面版本差異
Hibernate 5.1 -> org.hibernate.Query(舊...淘汰中)
Hibernate 5.2 -> org.hibernate.query.Query
↠ 舊的Query介面於5.2起作廢，計畫於Hibernate 6.0移除舊的Query介面。

17-3 使用 HQL 的步驟
(1)產生Query物件：
由Session物件的 createQuery() 來產生 Query 物件
Query query = session.createQuery(hql);
(2)前置作業：替HQL參數賦值，設定讀取物件的範圍：
query.setParameter("參數名稱1",參數值1)
.setParameter("參數名稱2",參數值2)
...
.setFirstResult(5) //由第六筆開始讀
.setMaxResult(3)   //最多讀三筆
(3)執行 HQL
query.getResultList() 或
query.getSingleResult() 或
query.executeUpdate()

↠ getResultList():
傳回 java.util.List<R> 的物件，用來查詢 0 或多筆物件。
↠ getSingleResult():
傳回單一物件，通常用來查詢正好為 1 筆資料的物件，傳回 0 筆或多筆會丟出例外 :
0 筆丟出 java.persistence.NoResultException
多筆丟出 org.hibernate.NonUniqueResultException
↠ setParameter("參數名稱1",參數值1): 設定 HQL 內的參數
在 HQL 內可定義多個以冒號 (:) 開頭的變數，這些變數都要用不同的
setParameter(" 參數名稱 ", 參數值 ) 來設定該值
↠ executeUpdate(): 執行 UPDATE/DELETE 的HQL敘述
↠ setFirstResult(): 說明由第幾筆物件開始讀取(從0開始)
↠ setMaxResults(): 要讀多少筆物件


### Query介面常用的方法
18-1 HQL 查詢傳回值的型態
↠ 查詢類別：
ex: "from Department as dept" 小寫 dept 為別名 (as 可省略)
傳回值的型態為 List<Employee>

↠ 查詢單一性質：
ex1: "select dept.deptname from Department as dept" -> 傳回值的型態為 List<String>
ex2: "select dept.depid from Department as dept" -> 傳回值的型態為 List<Integer>

18-2 Named Paramters Query
命名參數:
String hql = "from Department where deptid=:myid and deptname=:myname";

query.setParameter("myid", 3);
query.setParameter("myname", "SALES");

Department myDept = query.uniqueResult();
if(myDept!=null){
System.out.println(myDept.getDeptid()+":"+myDept.getDeptname());
}


String hql = "from Department where deptid=:myid and deptname=:myname";
Query<Department> query=session.createQuery(hql, Department.class);

    query.setParameter("myid", 3);
    query.setParameter("myname", "SALES");

    Department myDept = query.uniqueResult();
    if(myDept!=null){
      System.out.println(myDept.getDeptid() +":"+ myDept.getDeptname());
    }


18-3 HQL 查詢子句
FROM子句   
查詢類別
String hql = "from Department as dept";

SELECT子句  
查詢單一性值，傳回值為 List<?>
"SELECT e.name FROM Employee e"

WHERE子句 - 自訂條件
↠ hql = "FROM Employee e WHERE e.name = 'Jerry' and e.depId = 1 ";
說明：從 Employee 物件中找 name 為 Jerry 且 depId=1 的資料

↠ hql = "FROM Employee e WHERE e.salary >= 30000 ";
說明：從 Employee 物件中找 salary 大於 30000 的資料。

↠ hql = "FROM Employee e WHERE e.salary >= 30000 and e.name like '黃%'";
說明：從 Employee 物件中找 salary 大於 30000 且 name 開頭為'黃'的資料