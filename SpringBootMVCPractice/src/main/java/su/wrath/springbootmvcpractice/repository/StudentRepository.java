package su.wrath.springbootmvcpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.wrath.springbootmvcpractice.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
