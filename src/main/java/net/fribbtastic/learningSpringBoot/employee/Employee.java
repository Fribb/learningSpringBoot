package net.fribbtastic.learningSpringBoot.employee;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Frederic Eßer
 */
@Entity                     // defines this class as a JPA Entity that then can be persisted to a relational Database
@Table(name = "EMPLOYEES")  // defines the name of the table in the database
@NoArgsConstructor          // lombok will provide a constructor with no parameters
@AllArgsConstructor         // same as above just with all parameters
@Getter                     // lombok will provide Getters
@Setter                     // lombok will provide Setters
@ToString                   // lombok will provide a ToString method
@Builder                    // provides a builder API
public class Employee {

    @Id                                                 // mark the field as ID and therefore primary key in the database
    @GeneratedValue(strategy = GenerationType.AUTO)     // strategy for generating the value of the primary key
    private Long id;

    @NonNull                                            // mark the field that it can't be null
    private String firstName;
    @NonNull
    private String lastName;
}
