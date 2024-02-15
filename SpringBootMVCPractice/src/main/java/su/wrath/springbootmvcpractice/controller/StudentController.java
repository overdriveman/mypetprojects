package su.wrath.springbootmvcpractice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import su.wrath.springbootmvcpractice.model.Student;
import su.wrath.springbootmvcpractice.service.StudentService;

@Slf4j
@Controller
@RequestMapping("/")
public class StudentController {

    public static final String MODEL_ERROR = "errorMessage";
    public static final String EMAIL_ALREADY_EXIST = "Такой email уже есть в базе!";
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String showMainPage() {
        return "redirect:/students-list";
    }

    @GetMapping("/students-list")
    public String showAllStudentsList(Model model) {
        model.addAttribute("students", service.getAllStudents());
        return "students-list.html";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") long id, Model model) {
        if (service.isStudentExists(id)) {
            model.addAttribute("student", service.getStudentById(id).get());
            return "edit-student";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable("id") long id, @Valid Student student,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-student";
        } else {
            try {
                service.updateStudent(student);
            } catch (DataIntegrityViolationException exception) {
                return handleValidationError("edit-student",EMAIL_ALREADY_EXIST, model);
            }
            return "redirect:/students-list";
        }
    }

    @GetMapping("/add-student")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student.html";
    }

    @PostMapping("/add-student")
    public String addStudent(@Valid Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-student";
        } else {
            try {
                service.saveStudent(student);
            } catch (DataIntegrityViolationException exception) {
                return handleValidationError("add-student",EMAIL_ALREADY_EXIST, model);
            }
            return "redirect:/students-list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        service.deleteStudentById(id);
        return "redirect:/students-list";
    }

    public String handleValidationError(String returnError, String errorMessage, Model model) {
        log.error(errorMessage);
        model.addAttribute("errorMessage", errorMessage);

        return returnError;
    }
}
