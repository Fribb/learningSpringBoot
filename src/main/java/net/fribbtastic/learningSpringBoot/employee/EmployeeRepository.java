package net.fribbtastic.learningSpringBoot.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Frederic Eßer
 * The Repository for the Employee that extends the Spring CrudRepository
 * this will handle all of our methods that we need to interact with the stored data like findById, deleteAll etc
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
