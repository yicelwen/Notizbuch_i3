# Servlet JSP ... 一些概念

## 1. 簡介 web 應用程式
+ URL 主要目的是以文字方式來說明網際網路上的資源如何取得。
+ URN 代表某個資源獨一無二的名稱。
+ URL 或 URN 目的都是用來識別某個資源，後來的標準制定了 URI，
而 URL 與 URN 成為 URI 的子集。

+ HTTP 是基於請求/回應的通訊協定，瀏覽器對 Web 網站發出一個取得資源的請求，Web 網站將要求的資源回應給瀏覽器，**沒請求就不會有回應**。

+ 在 HTTP 協定下，Web 是個健忘的傢伙，Web 回應瀏覽器之後，
就**不會記得**瀏覽器的資訊，更**不會去維護**與瀏覽器有關的**狀態**，因此HTTP又稱為*無狀態的通訊協定*。

+ **請求參數**是在 URI 之後跟隨一個問號(?)，然後是請求參數名稱與請求參數值中間以等號 (=) 表示成對關係。若有多個請求參數，則以 & 字元連接。

+ Get 與 Post 在使用時除了 URI 的資料長度限制、是否在網址列上出現請求參數等表面上的功能差異之外，事實上在 HTTP 最初設計中，該選擇使用 GET 或 POST，可根據其是否為安全或等冪操作來決定。
    #### Get 應用於安全、等冪操作的請求，而 POST 用於非等冪操作的請求

+ 在 URI 的規範中定義了一些保留字元，像是 :、/、?、&、=、@、% 等字元。在 URI 中都有它的作用，如果要在請求參數上表達 URI 中的保留字元，必須在 % 字元之後以 16 進位數值表示方式，來表示該字元的 8 個位元數值，這是 URI 規範中的百分比編碼，也就是俗稱的 URI 編碼或 URL 編碼。

+ 在 URI 規範中，空白字元是編碼 %20，而在 HTTP 規範中空白字元是編碼 +。
+ URI 規範的 URI 編碼針對的是字元 UTF-8 編碼的八個字元數值，
+ 在HTTP規範下的URI編碼並不限使用 UTF-8。

+ 就 Web 安全這塊來說，想要認識基本的安全弱點從何產生，可以從 OWASP TOP 10 出發，這是由 OWASP (open web application security project) 發起的計畫之一，於 2002 年發起，針對 **Web 應用程式最重大的十個弱點**進行排行，就本書撰寫的這個時間點，最新版的 OWASP Top 10 於 2017 年 11 月正式釋出。

+ 在學習 Servlet/JSP 時，有個重要觀念：「Web 容器 (container) 是Servlet / JSP 唯一認得的 HTTP 伺服器。」如果希望用 Servlet/JSP 撰寫的 Web 應用程式可以正常運作，就必須知道 Servlet/JSP 如何與 Web 容器做溝通，web 容器如何管理 servlet/JSP 的各種物件等問題。

+ Servlet 的執行依賴於 Web 容器提供的服務，沒有容器，servlet 只是單純的一個 java 類別，不能稱為可提供服務的 Servlet。對每個請求，容器是建立一個執行續並轉發給適當的Servlet 來處理，因而可以大幅減經效能上的負擔，但也因此要注意執行緒安全問題。

+ JSP 最後終究會被容器轉譯為 Servlet 並載入執行，了解 JSP 與 Servlet 中各物件的對應關係是必要的，必要時可以配合適當的工具，檢視 JSP 轉譯為 Servlet 之後的原始碼內容。

## 2. 撰寫、設定 Servlet
+ Tomcat 提供的主要是 Web 容器的功能，而不是 HTTP 伺服器的功能，然而為了給開發者便利，下載的 Tomcat 會附帶一個簡單的 HTTP 伺服器，相較於真正的 HTTP 伺服器而言，Tomcat 附帶的 HTTP 伺服器功能太簡單，僅做開發用途，不建議日後直接上線服務。

+ 要編譯 Hello.java，類別路徑 (Classpath) 中必須包括 Servlet API 的相關類別，如果使用的是 Tomcat，這些類別通常是封裝在 Tomcat 資料夾的 lib 資料夾中的 servlet-api.jar。

+ 要撰寫 Servlet 類別，必須繼承 HttpServlet 類別，並重新定義 doGet()、doPost() 等對應 HTTP 請求的方法。容器會分別建立代表請求、回應的 HttpServletRequest 與 HttpServletResponse，可以從前者 (request) 取得所有關於該次請求的相關資訊，從後者 (response)  對瀏覽器進行各種回應。

+ 在 Servlet 的 API 定義中，Servlet 是個介面，當中定義了與 Servlet 生命週期相關的 init()、destroy() 方法，以及提供服務的 service() 方法等。GenericServlet 實作了 Servlet 介面，不過它直接將 service() 標示為 abstract，GenericServlet 還實作了 ServletConfig 介面，將容器初始化 Servlet 呼叫 init() 時傳入的 ServletConfig 封裝起來。

+ 真正在 service() 方法中定義了 HTTP 請求基本處理流程是 HttpServlet，
doGet()、doPost() 中傳入的參數是 **HttpServletRequest、HttpServletResponse**，
而不是通用的 ServletRequest、ServletResponse。 

+ 在 Servlet 3.0 以後，可以使用 `@WebServlet` 標註 (Annotation) 來告知容器哪些 Servlet 會提供服務以及額外資訊，也可以定義在部署描述檔 web.xml 中。

+ 一個 Servlet 至少會有三個名稱：**類別名稱**、**註冊的 Servlet 名稱**與 **URI 模式名稱**。

+ WEB-INF 中的資料無法直接請求取得，必須透過請求轉發才有可能存取。

+ web.xml 必須位於 WEB-INF 中。 lib 資料夾用來放置 Web 應用程式會使用到的 JAR 檔案。class 資料夾用來放置編譯好的 .class 檔案。

+ 可以將整個 Web 應用程式使用到的所有檔案與資料夾，封裝為 WAR (Web Archive) 檔案，副檔名為 .war，再利用 Web 應用程式伺服器提供的工具，進行應用程式部屬。

+ 一個請求 URI 實際上是由三個部分組成：
    + `requestURI = contextPath + servletPath + pathInfo`

+ 一個 JAR 檔案中，除了可以使用標註定義的 Servlet、傾聽器、過濾器外，也可以擁有自己的部署描述黨，檔案名稱是 web-fragment.xml，必須放置在 JAR 檔案中的 META-INF 資料夾之中。基本上，web.xml 中可定義的元素，在 web-fragment.xml 中也可以定義。


## 3. 請求與回應
+ HttpServletRequest 是瀏覽器請求的代表物件，可以用來取得 HTTP 請求的相關訊息，像是使用 getParameter() 取得請求參數，使用 getHeader() 取得標頭資訊等。在取得請求參數的時候，要注意請求物件處理字元編碼的問題，才可以正確處理非 ASCII 編碼範圍的字元。

+ 可以使用 HttpServletRequest 的 setCharacterEncoding() 方法指定取得 POST 請求參數時使用的編碼，這必須在取得任何請求值之「前」執行，setCharacterEncoding() 方法只對於請求本體中的字元編碼才有作用，若採用 GET，必須注意伺服器的處理 URI 時預設的編碼。

+ 可以使用 HttpServletRequest 的 getRequestDispatcher() 方法取得 RequestDispatcher 物件，使用時必須指定 URI 相對路徑，之後就可以利用 RequestDispatcher 物件的 forwarder() 或 include() 來進行請求轉發或包括。使用 forward() 作請求轉發，是將回應的職責轉發給別的 URI，在這之前不可以有實際的回應，否則會發生 IllegalStateException 例外。

+ 請求轉發是在容器中進行，可以取得 WEB-INF 中的資源，而瀏覽器不會知道請求被轉發了，網址列上不會看到變化。使用 HttpServletResponse 的 sendRedirect() 則要求瀏覽器重新請求另一個 URI，又稱為重新導向，在網址列上會發現 URI 的變更。

+ 在進行請求轉發或包含時，若有請求週期內必須共用的資源，可以透過 HttpServletRequest 的 setAttribute() 設定為請求範圍屬性，而透過 getAttribute() 可以將請求屬性取出。

+ 大部分情況下，會使用 HttpServletResponse 的 getWriter() 來取得 PrintWriter 物件，並使用其 println() 等方法進行 HTML 輸出等字元回應。有時候，必須直接對瀏覽器輸出位元組資料，這時可以使用 getOutputStream() 來取得 ServletOutputStream 實例，以進行位元組輸出，為了讓瀏覽器知道如何處理回應的內容，記得設定正確的 content-type 標頭。

+ Servlet 3.0 新增了 Part 介面，可以方便地進行檔案上傳處理。可以透過 HttpServletRequest 的 getPart() 取得 Part 實作物件。

+ 從 Servlet 4.0 開始，可以在 web.xml 中加入 `<request-character-encoding>`、`<response-character-encoding>`，分別設定整個 Web 應用程式預設的請求編碼與回應編碼。


## 4. 會話管理
+ HTTP 本身是無狀態通訊設定，要進行會話管理的基本原理，就是將需要維持的狀態回應給瀏覽器，由瀏覽器在下次請求時主動發送狀態資訊，讓 Web 應用程式「得知」請求之間的關聯。

+ 隱藏欄位是將狀態資訊以表單中看不到的輸入欄位回應給瀏覽器，在下次發表單時一併發送這些隱藏的這些隱藏的輸入欄位值。

+ Cookie 是儲存在瀏覽器上的一個小檔案，可設定存活期限，在瀏覽器請求 Web 應用程式時，會一併將屬於網站的 Cookie 發送給應用程式。

+ URI 重寫是使用超鏈結，並在超鏈結的 URI 網址附加資訊，以 GET 的方式請求 Web 應用程式。

+ 如果要建立 Cookie，可以使用 Cookie 類別，建立時指定 Cookie 的名稱與數值，並使用 HttpServletResponse 的 addCookie() 方法在回應中新增 Cookie。可以使用 setMaxAge() 來設定 Cookie 的有效期限，預設是關閉瀏覽器之後 Cookie 就失效。

+ 執行 HttpServletRequest 的 `getSession()` 可以取得 HttpSession 物件。在會話階段，可以使用 HttpSession 的 `setAttribute()` 方法來設定會話期間要保留的資訊，利用 `getAttribute()` 方法就可以取得資訊。如果要讓 HttpSession 失效，可以執行 `invalidate()` 方法。

+ HttpSession 是 Web 容器中的一個 Java 物件，每個 HttpSession 實例都有個獨特的 Session ID。容器預設使用 Cookie 於瀏覽器儲存 Session ID，在下次請求時，瀏覽器將包括 Session ID 的 Cookie 送至應用程式，應用程式再根據 Session ID 取得相對應的 HttpSession 物件。

+ 如果瀏覽器禁用 Cookie，無法使用 Cookie 在瀏覽器儲存 Session ID，此時若仍打算運用 HttpSession 來維持會話資訊，可使用 URI 重寫機制。HttpServletResponse 的 encodeURL() 方法在容器無法從 Cookie 中取得 Session ID 時，會在指定的 URI 附上 Session ID，以便設定 URI 重寫時的超鏈結資訊。HttpServletResponse 的 encodeRedirectURL() 方法，可以在指定的重新導向 URI 附上 Session ID 的訊息。

+ 執行 HttpSession 的 `setMaxInactiveInterval()` 方法，設定的是 HttpSession 物件在瀏覽器多久沒活動就失效的時間，而不是儲存 Session ID 的 Cookie 失效時間。HttpSession 是用於當次會話階段的狀態維持，如果有相關的資訊，希望在關閉瀏覽器後，下次開啟瀏覽器請求 Web 應用程式時，仍可以發送給應用程式，則要使用 Cookie。


## 5. Servlet 進階 API、過濾器、傾聽器
+ Servlet 介面上，與生命週期及請求服務相關的三個方法是 **init()、service() 與 destroy()** 方法。當 Web 容器載入 Servlet 類別並實例化之後，會生成 ServletConfig 物件並呼叫 init() 方法，將 ServletConfig 物件當作參數傳入。ServletConfig 相當於 Servlet 在 web.xml 中的設定代表物件，可以利用它來取得 Servlet 初始參數。

+ GenericServlet 同時實作了 Servlet 及 ServletConfig。主要的目的，就是將初始 Servlet 呼叫 init() 方法傳入的 ServletConfig 封裝起來。

+ 希望撰寫的程式碼在 Servlet 初始化時執行，要重新定義無參數的 init() 方法，而不是有 ServletConfig 參數的 init() 方法或建構式。

+ ServletConfig 上還定義了 getServletContext() 方法，這可以取得 ServletContext 實例，這個物件代表了整個 Web 應用程式，可以從這個物件取得 ServletContext 初始參數，或是設定、取得、移除 ServletContext 屬性。

+ 每個 Web 應用程式都會有一個相對應的 ServletContext，針對應用程式初始化時需用到的一些參數資料，可以在 web.xml 中設定應用程式初始參數，設定時使用 `<context-param>` 標籤來定義。每一對初始參數要使用一個 `<context-param> `來定義。

+ 在整個 Web 應用程式生命周期，Servlet 需共用的資料，可以設定為 ServletContext 屬性。由於 ServletContext 在 Web 應用程式存活期間都會一直存在，設定為 ServletContext 屬性的資料，除非主動移除，否則也是一直存活於 Web 應用程式之中。

+ 傾聽器顧名思義，就是可聆聽某些事情的發生，然後進行些想作的事情。在Servlet/JSP中，如果想在 ServletRequest、HttpSession 與 ServletContext 物件建立、銷毀時收到通知，可以實作以下相對應的傾聽器：
    + ServletRequestListener
    + HttpSessionListener
    + ServletContextListener

+ Servlet/JSP 中可以設定屬性的物件有 ServletRequest、HttpSession 與 ServletContext。如果想在這些物件被設定、移除、替換屬性時收到通知，可以實作以下相對應的傾聽器：
    + ServletRequestAttributeListener
    + HttpSessionAttributeListener
    + ServletContextAttributeListener

+ Servlet/JSP 如果某個物件即將加入 HttpSession 中成為屬性，而想要該物件在加入 HttpSession、從 HttpSession 移除、HttpSession 物件在 JVM 間遷移時收到通知，可以在將成為屬性的物件上，實作以下相對應的傾聽器：
    + HttpSessionBindingListener
    + HttpSessionActivationListener

+ 在 Servlet/JSP 中要實作過濾器，必須時作 Filter 介面，並在 web.xml 中定義過濾器，讓容器知道載入哪個過濾器類別。Filter 介面有三個要實作的方法，init()、doFilter() 與 destroy()，三個方法的作用與 Servlet 介面的 init()、service() 與 destroy() 類似。

+ Filter 介面的 init() 方法上參數是 FilterConfig，FilterConfig 為過濾器定義的代表物件，可以透過 FilterConfig 的 getInitParameter() 方法來取得初始參數。

+ 當請求來到過濾器時，會呼叫 Filter 介面的 doFilter() 方法，doFilter() 上除了 ServletRequest 與 ServletResponse 之外，還有一個 FilterChain 參數。
+ 如果呼叫了 FilterChain 的 doFilter() 方法，就會執行下一個過濾器，
如果沒有下一個過濾器了，就呼叫請求目標 Servlet 的 service() 方法。
如果因為某個條件 (例如使用者沒有通過驗證) 而不呼叫 FilterChain 的 doFilter()，
請求就不會繼續至目標 Servlet，這時就是所謂的攔截請求。

+ 在實作 Filter 時，不用理會這個 Filter 前後是否有其他 Filter，
完全作為一個獨立的元件進行設計。

+ 在 Servlet 4.0 中，新增了 Generic Filter 類別，目的類似於 GenericServlet，
GenericFilter 將 FilterConfig 設定、Filter 初始參數的取得做了封裝，
也新增了 HttpFilter，繼承自 GenericFilter，對於 HTTP 方法的處理，
新增了另一個版本的 doFilter() 方法等。

+ 對於容器產生的 HttpServletRequest 物件，無法直接修改某些資訊，像是請求參數值。
可以繼承 HttpServletRequestWrapper 類別 (父類別 ServletRequestWrapper)，
並撰寫想要重新定義的方法。對於 HttpServletResponse 物件，可以繼承 HttpServletResponseWrapper 類別 (父類別 ServletResponseWrapper)
來對 HttpServletResponse 物件進行包裹。


## 6. 使用 JSP
+ JSP 最後會被容器轉譯為 Servlet 原始碼、自動編譯為 .class 檔案、載入 .class 檔案然後生成 Servlet 物件。JSP 在轉譯為 Servlet 並載入容器生成物件之後，會呼叫_jspInit() 方法進行初始化工作，銷毀前呼叫 _jspDestroy() 方法進行善後工作。在 Servlet 中，每個請求到來時，容器會呼叫 service() 方法，而在 JSP 轉譯為 Servlet 後，請求的到來則是呼叫_jspService() 方法。

+ 如果想在 JSP 網頁載入執行時做些初始動作，可以重新定義 jspInit() 方法。如果在 JSP 實例從容器移除前想要作一些收尾動作，可以重新定義 jspDestroy() 方法。

+ JSP 指示 (Directive) 元素的主要目的，在於指示容器將 JSP 轉譯為 Servlet 原始碼時，一些必須遵守的的資訊。page 指示類型的 import 屬性告知容器轉譯 JSP 時，必須在原始碼中包括的 import 陳述。contentType 屬性告知容器轉譯 JSP 時，必須使用 HttpServletRequest 的 setContentType() ，呼叫方法時傳入的參數就是 contentType 的屬性值。pageEncoding 屬性告知容器轉譯及編譯如何處理這個 JSP 網頁中的文字編碼，以及內容類型附加的 charset 設定。include 指示類型，它用來告知容器包括另一個網頁的內容進行轉譯。

+ JSP 轉譯後的 Servlet 類別應該包括哪些類別成員、方法宣告或是哪些陳述句，在撰寫 JSP 時，可以使用宣告 (Declaration) 元素、Scriptlet 元素及運算式 (Expression) 元素來指定。在 <%! 與 %>  之間宣告的程式碼，都將轉譯為 Servlet 中的類別成員或方法。 <% 與 %> 之間包括的內容，將被轉譯為 Servlet 原始碼 _jspService() 方法中的內容。 <%= 與 %> 運算式元素中撰寫 Java 運算式，運算式的運算結果將直接輸出為網頁的一部分。

+ JSP 中像 out、request 這樣的字眼，在轉譯為 Servlet 之後，對應於 Servlet 中的某個物件，例如 request 就對應 HttpServletRequest 物件。像 out、request 這樣的字眼，稱為隱含物件或隱含變數。

+ out 隱含物件在轉譯之後，對應於 javax.servlet.jsp.JspWriter 類別的實例。JspWriter 在內部也是使用 PrintWriter 來進行輸出，但 JspWriter 具有緩衝區功能。當使用 JspWriter 的 print() 或 println() 進行回應輸出時，如果 JSP 頁面沒有緩衝，直接建立 PrintWriter 來輸出回應，如果 JSP 頁面有作緩衝，只有在出清緩衝區時，才會真正建立 PrintWriter 物件進行輸出。

+ JSP 終究會轉譯為 Servlet，錯誤可能發生在三個時候：JSP 轉換為 Servlet 原始碼時、Servlet 原始碼進行編譯時，以及 Servlet 載入容器進行服務但發生執行時期錯誤時。只有 isErrorPage 設定為 true 的頁面，才可以使用 exception 隱含物件。

+ `<jsp:include>` 或 `<jsp:forward>` 標籤，在轉譯為 Servlet 原始碼之後，底層也是取得 RequestDispatcher 物件，並執行對應的 forward() 或 include() 方法。

+ JSP 中的 JavaBean 元件，指的是只要滿足以下條件的純粹 Java 物件：
    + 必須實作 java.io.Serializable 介面
    + 沒有公開 (public) 的類別變數
    + 具有無參數的建構式
    + 具有公開的設值方法 (Setter) 與取值方法 (Getter)

+ 使用 JavaBean 的目的，基本上是在於減少 JSP 頁面上 Scriptlet 的使用。可以搭配 `<jsp:useBean>` 來使用 JavaBean，並使用 `<jsp:setProperty>` 與 `<jsp:getProperty>` 存取 JavaBean 的屬性。

+ 對於 JSP 中一些簡單的屬性、請求參數、標頭與 Cookie 等訊息的取得，一些簡單的運算或判斷，可以試著使用運算是語言來處理，甚至可以將一些常用的公用函式撰寫為 EL 函式，如此對於網頁上的 Scriptlet，又可以有一定份量的減少。

+ EL 在某些情況下，可以使用點運算子 (.) 的場合，也可以使用 [ ] 運算子：
    + 如果使用點 **(.) 運算子**，則左邊可以是 JavaBean 或 Map 物件。
    + 如果使用 **[ ] 運算子**，則左邊可以是 JavaBean、Map、陣列或 List 物件。

+ 在 Java EE 7 以後，釋出了 Expression Language 3.0，成為一個獨立的規格 (JSR 341)，具有直接呼叫靜態成員等進階功能。


## 7. 使用 JSTL
+ 可以使用 JSTL (JavaServer Pages Standard Tag Library) 取代 JSP 中用來實現頁面邏輯的 Scriptlet，這會使得設計網頁簡單多了，可以隨時調整畫面而不用費心地修改 Scriptlet。JSTL 提供的標籤庫分作五個大類：**核心標籤庫、格式標籤庫、SQL 標籤庫、XML 標籤庫與函式標籤庫**。

+ `<c:if>` 標籤的 test 屬性中可以放置 EL 運算式，如果運算式的結果式 true，會將 `<c:if>` 本體輸出。`<c:if>` 標籤沒有相對應的 `<c:else>` 標籤。如果想在某條件式成立時顯示某些內容，否則就顯示另一個內容，可以使用 `<c:choose>`、`<c:when>` 以及 `<c:otherwise>` 標籤。

+ 若不想使用 Scriptlet 撰寫 Java 程式碼的 for 迴圈，可以使用 JSTL 的 `<c:forEach>` 標籤來實現這項需求。 `<c:forEach>` 標籤的 items 屬性可以是陣列或 Collection 物件，每次會循序取出陣列或 Collection 物件中的一個元素，並指定給 var 屬性設定的變數，之後就可以在 `<c:forEach>` 標籤本體中，使用 var 屬性設定的變數來取得該元素。如果想在 JSP 網頁上，將某個字串切割為數個字符 (Token)，就可以使用 `<c:forTokens>`。

+ 如果要在發生例外的網頁直接捕捉例外物件，就可以使用 `<c:catch> `將可能產生例外的網頁段落包起來。如果例外真的發生，例外物件會設定給 var 屬性指定的名稱，這樣才有機會使用這個例外物件。

+ 在 JSTL 中，有個 `<c:import>` 標籤，可以視作是 `<jsp:include>` 的加強版，也是可以於執行時期動態匯入另一個網頁，並可搭配 `<c:param>` 在匯入另一網頁時代有參數。除了可以匯入目前 Web 應用程式中的網頁之外，`<c:import>` 標籤還可以匯入非目前 Web 應用程式中的網頁。  

+ `<c:redirect>` 標籤的作用，就如同 sendRedirect() 方法，如此就不用撰寫 Scriptlet 來使用 HttpServletResponse 的 sendRedirect() 方法，也可以達到重新導向的作用。

+ 如果只是要在 page、request、session、application 等範圍設定屬性，或者還想要設定 Map 物件的鍵與值，可以使用 `<c:set>` 標籤。var 用來設定屬性名稱，value 用來設定屬性值。若要設定 JavaBean 或 Map 物件，可使用 target 屬性進行設定。

+ `<c:out>` 會自動將角括號、單引號、雙引號等字元用替代字元取代。這個功能是由 `<c:out>` 的 escapeXml 屬性來控制，預設是 true，如果設定為 false，就不會作字元的取代。

+ 可以使用 JSTL 的 `<c:url>`，它會在使用者關閉 Cookie 功能時，自動用 Session ID 作 URI 重寫。

+ JSTL 提供許多 EL 公用函式，像是 length() 函式，以及字串處理相關函式：
    + 改變字串大小寫：toLowerCase、toUpperCase
    + 取得子字串：substring、substringAfter、substringBefore
    + 裁減字串前後空白：trim
    + 字串取代：replace
    + 檢查是否包括子字串：startsWith、endsWith、contains、containsIgnoreCase
    + 檢查子字串位置：indexOf
    + 切割字串為字串陣列：split
    + 連接字串陣列為字串：join
    + 替換 XML 字元：escapeXML


## 8. 自訂標籤
+ Tag File 是為了不會 Java 的網頁設計人員存在，它是一個副檔名為 .tag 的檔案，放在 WEB-INF/tags 底下。Tag File 中可使用 tag 指示元素，它就像是 JSP 的 page 指示元素，用來告知容器如何轉譯 Tag File。在需要 Tag File 的 JSP 頁面中，要使用 taglib 指示元素的 prefix 定義前置名稱，以及使用 tagdir 屬性定義 Tag File 的位置。Tag File 會被容器轉譯，實際上是轉譯為 javax.servlet.jsp.tagext.SimpleTagSupport 的子類別。

+ 可以繼承 javax.servlet.jsp.tagext.SimpleTagSupport 來實作 Simple Tag 標籤處理器 (Tag Handler)，並重新定義 doTag() 方法來進行標籤

+ 要定義 Tag 標籤處理器，可以透過繼承 javax.servlet.jsp.tagext.TagSupport 來實作。Tag 介面繼承自 JspTag 介面，定義了基本 Tag 行為。單是使用 Tag 介面的話，無法重複執行本體內容，這是用子介面 IterationTag 介面的 doAfterBody() 定義。TagSupport 類別實作了 IteratorTag 介面，對介面上所有方法做了基本實作，只需要在繼承 TagSupport 之後，針對必要的方法重新定義即可。


## 9. 整合資料庫
+ JDBC (Java DataBase Connectivity) 是執行 SQL 的解決方案，開發人員使用 JDBC 的標準介面，資料庫廠商對介面進行實作，開發人員無需接觸底層資料庫驅動程式的差異性。

+ 資料庫操作相關的 JDBC 介面或類別都位於 java.sql 套件。要連接資料庫，可以向 DriverManager 取得 Connection 物件。Connection 是資料庫連線的代表物件，一個 Connection 物件就代表一個資料庫連線。SQLException 是在處理 JDBC 時很常遇到的例外，為資料庫操作過程發生錯誤時的代表物件。

+ 在 Java EE 的環境中，將取得連線與資料庫來源的行為，規範在 javax.sql.DataSource 介面，如何取得 Connection，由實作介面的物件來負責。

+ Connection 是資料庫連接的代表物件，接下來要執行 SQL 的話，必須取得 java.sql.Statement 物件，他是 SQL 陳述的代表物件，可以使用 Connection 的 createStatement() 來建立 Statement 物件。

+ Statement 的 executeQuery() 方法適用於 SELECT 是查詢資料庫的 SQL，executeUpate() 會傳回 int 結果，表示資料變動的筆數，executeQuery() 會傳回 java.sql.ResultSet 物件，代表查詢的結果，查詢的結果會是一筆一筆的資料。可以使用 ResultSet 的 next() 來移動至下一筆資料，它會傳回 true 或 false 表示是否有下一筆資料，接著可以使用 getXXX() 來取得資料。

+ 在使用 Connection、Statement 或 ResultSet 之後，記得關閉以釋放相關資源。

+ 如果有些操作只是 SQL 語句當中某些參數不同，其餘的 SQL 子句皆相同，可以使用 java.sql.PreparedStatement。可以使用 Connection 的 preparedStatement() 方法建立好一個預先編譯的 SQL 語句，當中參數會變動的部分，先指定 '?' 這個佔位字元。等到需要真正指定參數執行時，在使用相對應的 setInt()、setString() 等方法，指定 '?' 處真正應有的參數。


## 10. Web 容器安全管理
+ 當應用程式要求具備安全性時，可以歸納為四個基本需求：驗證、授權、機密性完整性。

+ 在使用 Web 容器提供的安全實作之前，必須先了解幾個 Java EE 的名詞與觀念：使用者、群組、角色、Realm。

+ 角色是 Java 應用程式授權管理的依據。Java 應用程式的開發人員在進行授權管理時，無法事先得知應用程式將部署在哪個伺服器上，無法直接使用伺服器系統上的使用者及群組來進行授權管理，而必須根據角色來定義。屆時 Java 應用程式真正部署至伺服器時，再透過伺服器特定的設定方式，將角色對應至使用者或群組。

+ 使用 Web 容器安全管理，基本上可以提供兩個安全管理的方式：宣告式安全 () 與程設式安全 ()。

+ 沒有設定 `<http-method>`，所有 HTTP 方法都受到限制。設定了 `<http-method>`，只有設定被設定的 HTTP 方法受到限制，其他方法不受限制。如果沒有設定 `<auto-constraint>` 標籤，或是 `<auth-constraint>` 標籤中設定 `<role-name> * </role-name>`，表示任何角色都可以存取。如果直接撰寫 `<auto-constraint/>` ，那就沒有任何角色可以存取了。

+ 容器基本驗證是使用對話方塊輸入名稱、密碼，所以使用基本驗證時無法自訂登入畫面，而傳送名稱、密碼時是使用 Authentication 標頭，無法設計登出機制，關閉瀏覽器是結束會話的唯一方式。容器表單驗證時，發送的 URI 要是 j_security_check，發送名稱的請求參數必須是j_username，發送密碼的請求參數必須是j_password，登入字符是儲存在HttpSession中，如果要讓此次登入失敗，可以呼叫HttpSession的invalidate()方法，因此在表單驗證時可以設計登出機制。

+ 在`<auth-method>`可以設定的值是BASIC、FORM、DIGEST或CLIENT-CERT。

+ 通常Web應用程式要在傳輸過程中保護資料，會採用HTTP over SSL，就是俗稱的HTTPS。如果要使用HTTPS來傳輸資料，只要在web.xml中需要安全傳輸的`<security-constraint>`中設定：
    ```xml
    <user-data-constraint>
        <transport-guarantee>CONFIDENTIAL</transport-guarantee>
    </user-data-constraint>
    ```
+ `<transport-guarantee>`預設值是NONE，還可以設定的值是CONFIDENTIAL或INTEGRAL。事實上無論設定CONFIDENTIAL或者INTEGRAL，都可以保證機密性與完整性，慣例上都設定CONFIDENTIAL。

+ 如果使用容器的驗證及授權管理，那麼有五個HttpServletRequest上的方法與安全管理有關：login()、logout()、getUserPrincipal()、getRemoteUser()及isUserInRole()。


## 11. Java Mail 入門
+ 要使用JavaMail進行郵件傳送，首先必須建立代表當次郵件會話的javax.mail.Session物件，Session中包括了SMTP郵件伺服器位址、連接埠、使用者名稱，密碼等資訊。在取得代表當次郵件傳送會話的Session物件之後，接著要建立郵件訊息，設定寄件人、收件人、主旨、傳送日期與郵件本文。最後再以javax.mail.Transport的靜態send()方法傳送訊息。

+ 如果郵件要包括HTML或附加檔案等多重內容，必須javax.mail.Multipart物件，並在這個物件中增加代表多重內容的javax.mail.internet.MimeBodyPart物件。

+ 在使用MimeBodyPart的setFileName()設定附檔名稱時，必須做MIME編碼，可借助MimiUtility.encodeText()方法，在使用ByteArrayDataSource設定來源時，還需指定內容類型。


## 12. Spring 起步走
+ 從Spring 4.x開始，推薦使用Gradle或Maven來下載。
+ Gradle提供預設專案及相關慣例設定之外，對於Java中程式庫或框架相依性問題，也提供了集中式儲藏室解決方案。
+ 物件的建立與相依注入當然是必要的關切點，只不過當過程太過冗長，模糊了商務流程之時，應該適當地將之分離，Spring框架的核心功能之一，就是用來解決物件的建立與相依注入的問題。


## 13. Spring MVC
+ 在開始使用框架之後，會發現框架主導了城市運行的流程，必須在框架的規範下定義某些類別，框架會在適當時候調用你實作的方式，也就是說，對應用程式的流程控制權被反轉了，現在是框架在定義流程，由框架來呼叫你的程式，而不是由你來呼叫框架。

+ 框架本質上也是個程式庫，不過會被定位為框架，表示它對程式主要流程擁有更多的控制權，然而，框架本身是個半成品，想要完成整個流程，必須在框架的流程規範下，實現自定義元件，然而可以自行掌控的部分與使用程式庫相比，對流程控制的自由度少了許多。

+ 使用程式庫時，開發者會擁有較高的自由度；使用框架時，開發者會受到較大的限制，只有換取而來的益處超越了犧牲掉的流程自由度，才會使得使用框架具有意義。

+ 判定一個框架是否適用之時，有一個方式是看看，框架是否有個最小集合，它最好可以基於開發者既有的技術背景，在略為重構(原型)應用程式以使用此最小集合後，就能使程式運行起來，之後隨著對框架認識的越多，在判定框架中的特定功能是否適用之後，再逐步重構應用程式能使用該功能，如此就算框架本身包山包海，也能從中掌握真正有益於應用程式的部分。


## 14. 使用 Spring Boot
+ Spring Boot將開發時必要的相依程式庫，都整理Starter相依之中，因此就不用自行設定spring-webmvc、spring-context等相依程式庫，至於使用的Spring版本，決定於使用的Spring Boot版本。

+ Spring Boot會自動看看相依程式庫設定，自動產生並注入元件。在Spring Boot一開始感覺像是零組態，然而這並不是表示不需要任何設定，而是有許多設定都有預設值或行為了，在想要預設值以外的設定時，才需要進行相關組態。

+ 使用Spring Boot本身的spring指令，使用spring init連線至 start.spring.io來產生初始專案。

+ 可以在既有的Eclipse，透過「Help/Eclipse Marketplace」來安裝Spring Tool Suite，或者是直接在 Spring 官網下載 Spring Tool Suite。