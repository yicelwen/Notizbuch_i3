Tables 資料表
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

