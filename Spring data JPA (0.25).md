### What is Spring Data JPA
+ Hibernate:   
### Connect to a real DB and not in memory DB
+ Application.properties
  + spring.datasource.url=jdbc:postgresql://localhost:5432/jpademo
  + spring.datasource.username=  (macOs 可不填, windows+資料庫有帳號密碼的話要填)
  + spring.datasource.password=
  + spring.jpa.hibernate.ddl-auto=create-drop  (drop everything before the application shuts down)
  + spring.jpa.show-sql=true
  + spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  + spring.jpa.properties.hibernate.format_sql=true
  
### How to map classes to tables

```SQL
SELECT * FROM student
JOIN student_id_card sic on student.id = sic.student_id
JOIN book b ON student.id = b.student_id
```
#####Examples of an Entity
```Java
javax.persistence.Entity  //avoid anything from org.hibernate.annotations

@Entity(name = "Student") //it's best to specify the name yourself
@Table(
        name = "student",
        uniqueContraints = {
                @UniqueConstraint(name = "student_email_unique", columnName = "email")
        }
)
public class Student{
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1  // increment 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
          name = "last_name",
          nullable = false,
          columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
          name = "email",
          nullable = false,
          columnDefinition = "TEXT",
  //        unique = true  // 輸入的值不可重複
    )
    private String email;
    
    @Column(
            name = "age",
            nullable = false
    )
    private String age;
    
    // +getter/setter // +toString 
    // +Constructor(w/all items selected) **REMOVE Identifier**
    // +no-arg constructor
}
```
##### StudentRepository - Data Access Layer
+ JpaRepository extends PagingAndSortingRepository extends CrudRepository
```Java
import org.springframework.data.jpa.repository;
interface StudentRepository extends JpaRepository<Student, Long>{
}
```
```Java
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
    @Bean   // 啟動 Spring Boot 時，Hibernate 會執行 insert into SQL 
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones@i3.com", 
                    21
            );
            studentRepository.save(maria);
        };
    }
}
```
### Hibernate Entity Life Cycle
### Queries
### Paging and Sorting
### One-to-One Relationships
### One-to-Many Relationships
### Many-to-Many relationships
### Transactions