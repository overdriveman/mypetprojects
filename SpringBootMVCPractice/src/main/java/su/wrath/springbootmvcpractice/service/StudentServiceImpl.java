package su.wrath.springbootmvcpractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.wrath.springbootmvcpractice.model.Student;
import su.wrath.springbootmvcpractice.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    @Autowired
    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteStudentById(long id) {
        repo.findById(id).ifPresent(repo::delete);
    }

    @Override
    public void updateStudent(Student student) {
        repo.save(student);
    }

    @Override
    public boolean isStudentExists(Long id) {
        return getStudentById(id).isPresent();
    }
}
