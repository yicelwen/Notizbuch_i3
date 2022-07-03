# Tables 資料表
+ SQL 中最核心的單位
    + 商城的資料庫
  ![image info](./images/%E9%9B%BB%E5%95%86%E8%B3%87%E6%96%99%E5%BA%AB.png)

SQL Server 常用的資料型別 Data types:
+ 精確數值 (Exact numerics):
    + INT, BIGINT, SMALLINT, TINYINT, NUMERIC
+ 近似數值 (又稱浮點數, Approximate numerics):
    + FLOAT, REAL
+ 字元字串 (Character strings):
    + CHAR, VARCHAR, TEXT
+ 萬國碼字元字串 (Unicode character strings):
    + NCHAR, NVARCHAR, NTEXT
+ CHAR (N) 
    + 固定長度的非二進位字串，長度不足會往右補空格
    + N 可以指定 1 ~ 8000
+ VARCHAR (N | MAX)
    + 變動長度的非二進位字串，會用單引號包起來 (e.g. 'Jessie')
    + N 可以指定 1 ~ 8000
    + 或使用 max 來表示資料行大小限制最多為 2^31-1 個位元組(2GB)的儲存體
    + e.g. VARCHAR(50) 表示該欄位可接受 50 個字以內的字串
+ NVARCHER (N) (MAX) 變動長度的非二進為字串，Unicode 編碼
    + N 可以指定 1 ~ 4000

#### CHAR v.s. VARCHAR
| Value     | CHAR(4)   | Storage Required | VARCHER(4) | Stroage Required |
|-----------|-----------|------------------|------------|------------------|
|''         | '    '    | 4 bytes          | ''         | 1 byte           |
|'ab'       | **'ab  '**    | 4 bytes          | **'ab'**       | 3 bytes          |
|'abcd'     | 'abcd'    | 4 bytes          | 'abcd'     | 5 bytes          |
|'abcdefgh' | 'abcd'    | 4 bytes          | 'abcd'     | 5 bytes          |

+ char 型別的資料會先給`固定的容器`，若是沒裝滿則會補空格
+ 而 `varchar 會根據裡面裝的內容來調整所需的容量`，實務上來說
    + char 會用在每一筆資料長度都相同的情況
    + varchar 則用在資料長度不盡相同的情況
    + 相同的地方是，這兩者都不能塞入超過預先設定的長度的資料
        + varchar(50) 最多只能給 50 個字元

+ 日期和時間 (Date and Time)
    + DATE, TIME, DATETIME, DATETIME2, SMALLDATETIME
+ 二進位字串 (Binary Strings)
    + BINARY, VARBINARY, IMAGE

#### NVARCHAR 萬國碼字源字串詳細說明
+ NVARCHAR (N | MAX) : 變動長度的非二進位字串，Unicode 編碼
    + n: 可以指定 1 ~ 4000
    + 超過 4000 個字則使用 max，最多佔 2GB 容量
+ 在資料庫中，每個英文字符的儲存空間只需要 1 byte，但若是非英文字及符號，如中/法等非英文字符，則需要 2 byte 來儲存
+ 如果英文與中文同時存在，由於占用空間數不同，容易造成混亂，導致讀取出來的字符串是亂碼，所以有中文的資料，建議使用 NVARCHAR(n)是比較安全的。

+ **BINARY(N)** : 固定長度的二進位資料
    + N 範圍 1 ~ 8000，儲存大小為 N byte
+ **VARBINARY(N | MAX)** : 可變長度的二進位資料
    + N 範圍 1 ~ 8000，儲存大小為 N byte
    + MAX : 超過 8000 則用 MAX，上限為 2^31-1 個位元組(2GB)
+ **IMAGE** : SQL Server 官網不建議再使用，請轉用 VARBINARY(MAX)

+ **INT** : 最常用的整數型別 (4位元組) : -2^31 (-2,147,483,648) 到 2^31-1
        + (2,147,483,647)
+ **TINYINT** : 空間較小的數字 (1位元組)，範圍 0 - 255，例如身高、年齡資料適合使用

+ **BIGINT** : 當 INT 不夠時使用，範圍： -2^63(-9,223,372,036,854,775,808) 到 2^63-1 (9,223,372,036,854,775,807) 

+ **BIT** : 可以是 1, 0, NULL (1位元組)。常用來表示**布林值**
    + 即 1 為 true、0 為 false、NULL 未知

+ **DECIMAL (p, s)** : 浮點數(含有小數點的數)
    + p : 整數與小數位數的總位數 (p 範圍 1 ~ 38)
    + s : 小數位數 (介於 0 ~ p)
        + 例: DECIMAL(5, 2): -999.99 ~ 999.99

+ **float(n)** : 有小數點的數字資料 (非精確儲存)
    + n 可以是 1 ~ 53，預設為 53，表示紀錄到小數點後 53 位
    + 注意 : float 只會使用在當 decimal 的位數 (38) 不夠時才使用
    + float 為非精確數值，因此不能透過 where、>、<、= 等運算子做搜尋

+ **TIME 時間** : 格式為時分秒 'HH:MM:SS'，佔 3 ~ 5 位元組
    + 範例： '10:11:12', '22:50:32'

+ **DATE 日期** : 格式為年月日 'YYYY-MM-DD'，佔 3 位元組
    + 範例： '2021-05-15', '2020-03-20', '2008-09-15'

+ **DATETIME 日期加時間** : 格式 'YYYY-MM-DD HH:MM:SS' 需 8 位元組
    + 範圍 : '1753-01-01 00:00:00' ~ '9999-12-31 23:59:59'
    + 範例 : '2008-09-15 10:11:12', '2021-05-15 22:50:32'

+ **DATETIME2(n)** : 日期加時間  格式 YYYY-MM-DD HH:MM:SS
    + 範圍：'0001-01-01 00:00:00' ~ '9999-12-31 23:59:59'
    + N : 可以是 0 ~ 7，儲存秒數的精確度，預設是`3`，指時間的秒可以精確到小數點秒數後 3 位

```SQL
CREATE TABLE MEMBERS (
    member_name nvarchar(50),
    member_age int
)
```
### 查看資料表設計
+ 透過 SSMS : 資料表右鍵 > 設計
+ 透過 SQL 語法，使用 SQL Server 內建函數
    + `exec sp_columns <table_name>`
    + exec 執行  |  sp_columns 是預存程序的名稱

+ 修改資料表欄位設定
    + 修改欄位是風險較大的動作，所以 SSMS 預設會把這項功能鎖起來
    + 通常會完全刪除table再重建，但若想方便此工具修改，則需以下設定
        + 工具 > 選項 > 設計師 > 取消勾選`防止儲存需要資料表重建的變更`   

```SQL
INSERT INTO phone_products(product_name, brand, price)
VALUES ('iPhone 12', 'Apple', 19900),
('Pixel 5', 'Google', 18900),
('Galaxy S21', 'Samsung', 19800);
```

+ 儲存新增查詢 `.SQL` 檔時，右下角存檔記得選萬國碼 UTF-8 (預設是 big5)

```SQL
CREATE TABLE phone_products2(
    product_name varchar(50) not null,
    brand varchar(20) not null,
    price int not null
);
```
#### Null vs Default : Default 說明與設定
+ 當新增資料時，該欄位沒有塞入值，就可以使用 default 設定其預設值
    ```SQL
    CREATE TABLE phone_products3(
        product_name VARCHAR(50) NOT NULL DEFAULT 'unnamed',
        brand VARCHAR(20) NOT NULL DEFAULT 'not sure',
        price INT NOT NULL default ()
    );

    INSERT INTO phone_products3(product_name)
    VALUES ('Flip5');
    
    SELECT * FROM phone_products3;
    ```

#### NULL 和 DEFAULT : 不含 NOT NULL 的 DEFAULT
```SQL
CREATE TABLE phone_products4(
    product_name VARCHAR(50) default 'unnamed',
    brand VARCHAR(20) default 'not sure',
    price INT default ()
);

INSERT INTO phone_products4(product_name, brand)
VALUES (null, 'Apple'), (null, 'Google');
```
*** 
通常設計表格時，該欄需要有 DEFAULT 時，通常會設計成 `NOT NULL`，這邊只是說明 DEFAULT 可以用在允許 NULL 的情況，但**不建議這樣設計**。

```SQL
INSERT INTO phone_products4(brand)
VALUES ('Apple', 'Google')

SELECT * FROM phone_products4;

INSERT INTO phone_products4(product_name, brand)
VALUES(null, 'Apple'), (null, 'Google')

SELECT * FROM phone_products4;
```

#### 主鍵
+ 在商業邏輯中，極可能看到不同件商品但是資料看起來一樣的情況
    + e.g. 使用者若有重複名稱，就無法分辨/缺少識別性
+ 所以需要 ID (primary key) 識別性

```SQL
CREATE TABLE my_products(
    product_id int not null,
    product_name varchar(50),
    price int,
    primary key (product_id)  -- primary key 這兩個字不可以連在一起
)
```
第二種寫法
```SQL
CREATE TABLE my_products(
    product_id int not null primary key, 
    -- 第二種寫法 (也可以寫not null之前，只要在資料型別之後就好)
    product_name varchar(50),
    price int
);
```
#### 產生識別 (IDENTITY) 並自動增加主鍵值
+ 新增資料時，要知道上一個資料主鍵值，才能知道下個資料要給什麼主鍵值，這樣不夠方便。 所以通常資料庫都有自動增加主鍵值的機制 `IDENTITY`
+ IDENTITY 預設值為 1，每次增加 1，寫做 `IDENTITY(1,1)`
+ 可以自訂初始值與累加值，`IDENTITY(101,1)`就是從 101 開始每次 +1 
+ 一個 Table 內只能有一個 IDENTITY 作為識別欄位，這欄位可以是主鍵或 unique 欄位，通常會用在主鍵 `Primary Key` 的位置
+ IDENTITY 約束的欄位可以是整數類型 (INT, TINYINT, SMALLINT, BIGINT)，通常用 `INT`


時間戳記

https://youtu.be/cBily-vKcjU?t=7714




# SQL JOIN
1. Inner join 內部連接 -- 必須指定等值連接的條件，而查詢結果只會返回符合連接條件的資料。
    1. 寫法一
    ```SQL
    SELECT table_column1, table_column2 ...
    FROM table_name1
    INNER JOIN table_name2
    ON table_name1.column_name=table_name2.column_name;
    ```
    2. 寫法二
    ```SQL
    SELECT table_column1, table_column2 ...
    FROM table_name1
    INNER JOIN table_name2
    USING (column_name);
    ```
2. Left (outer) join
3. Right (outer) join
4. Full (outer) join 
5. Cross join 交叉連接
6. Natural join 自然連接