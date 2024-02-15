package su.wrath.springbootmvcpractice.service;

import su.wrath.springbootmvcpractice.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudentById(long id);

    void deleteStudentById(long id);

    void updateStudent(Student student);

    boolean isStudentExists(Long id);

}
