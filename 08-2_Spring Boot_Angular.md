# Spring Boot 搭配 Angular 框架的實作

https://youtu.be/Gx4iBLKLVHk?t=4861
>> 還沒看完，找一個開始工作之後的周末再來補看完

#### @Id  /  @GeneratedValue(......)
+ strategy = GenerationType.IDENTITY
+ strategy = GenerationType.AUTO
+ strategy = GenerationType.SEQUENCE
+ strategy = GenerationType.TABLE

spring.jpa.show-sql=true
> Tells the IDE to print sql on console

spring.jpa.hibernate.ddl-auto=
> update / create-drop / 還有哪些

UUID 通用唯一辨識碼，UUID具有唯一性，這與其他大多數編號方案不同。重複UUID碼概率接近零，可以忽略不計。
> 根據標準方法生成，不依賴中央機構的註冊和分配


##### UUID 在 addEmployee 方法裡面的實作
```JAVA
@Service
@Transactional
public EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees(){
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }    

    public void deleteEmployee(Long id){
        employeeRepo.deleteEmployeeById(id);
    }  // 在 EmployRepo 自建方法

    public Employee findEmployeeById (Long id){
        return employeeRepo.findEmployeeById(id).orElseTHrow(() -> new UserNotFoundException ("User by id" + id + "was not found"));
        // 這裡要建立一個 class UserNotFoundException
    }

    publice void deleteEmployee(Long id){
        employeeRepo.deleteEmployeeById(id);
    }
}
```
```JAVA
public interface EmployeeRepo extends JpaReposiotry<Employee, Long> {

    // 因為 JPA magic 不用寫 @Query
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);
}

```
```JAVA
// 這個類別建議放在 model/service/controller 以外的 exception package
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String s) {
        super(message);
    }
}
```
#### HttpStatus enum 以下的幾個狀態
+ HttpStatus.OK : 200
+ HttpStatus.CREATED : 201



```JAVA
@RestController
@RequestMapping("/employee")  // 讓所有方法前面的路徑都有/employee/
public class EmployeeResource {
    private final EmployeeService employeeService;

    public EmployeeResource (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all/{id}")  // List泛型 要說明是 list of what
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")  // List泛型 要說明是 list of what
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employees = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

```
```Java
@Entity
public class Employee implementes Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String phone;
    private String imageUrl;
    @Column(nullable = false, updatable = false)
    private String employeeCode;

    public Employee() {}

    public Employee(String name, String email, String jobTitle, String phone, String imageUrl, String employeeCode) {
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.image = imageUrl;
        this.employeeCode = employeeCode;
    }
}
```

#### Angular CLI - angular 框架的 cmd
+ localhost: 4200  |  the default page you get when creating an angular application.


```TS
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {
    private apiServerUrl = '';

    constructor(private http: HttpClient) { }
                                    // 這裡的 employee 是Employee.ts interface
    public getEmployees(): Observable<Employee[]> {
        return this.http.get<Employee[]>(`${this.apiServerUrl}/employee/all`);
    }

    public addEmployee(employee: Employee): Observable<Employee> {
        return this.http.post<Employee>(`${this.apiServerUrl}/employee/add`, employee);
    }

    public updateEmployee(employee: Employee): Observable<Employee> {
        return this.http.put<Employee>(`${this.apiServerUrl}/employee/update`, employee);
    }

    public deleteEmployee(employeeId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/employee/delete/${employeeId}`);
    }
}
```

```TS
export interface Employee {
    id: number;
    name: string;
    email: string;
    jobTitle: string;
    phone: string;
    imageUrl: string;
    employeeCode: string;
}
```
```TS
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable ({
    providedIn: 'root'
})
export class EmployeeService {
    private apiServerUrl = '';

    constructor(private http: HttpClient) { }

    public getEmployees(): Observable<any> {
        return this.http.get<any>(`${this.apiServerUrl}/employee/all`);
    }

}

```

```TS
import { Component } from '@angular/core';
import { Employee } from './employee';
import { EmployeeService } from './employee.service';

@Component({
    selector: 'app-root';
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    public employees: Employee[];

    constructor(private employeeService: EmployeeService) { }

    public getEmploygges(): void {
        this.employeeService.getEmployees().subsrcibe(
            (response: Employee[]) => {
                this.employees = 
            }
        );
    }

}



```
