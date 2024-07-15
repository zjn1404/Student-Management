package com.nqt.student_management.controller;

import com.nqt.student_management.entity.Student;
import com.nqt.student_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String homePage(Model model) {
        final String DEFAULTSORTTYPE = "id";
        return getStudents(0, DEFAULTSORTTYPE, model);
    }

    @GetMapping("/{page}")
    public String getStudents(@PathVariable("page") int page, @RequestParam(value = "sort", required = false) String sort, Model model) {
        final int studentsPerPage = 10;
        Page<Student> studentPage = studentService.getNStudents(page, studentsPerPage);
        List<Student> students = new ArrayList<>(studentPage.getContent());
        sortStudents(students, sort);
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("isSearch", false);
        return "students/students";
    }

    @GetMapping("/save")
    public String addStudent(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        model.addAttribute("isUpdate", false);
        return "students/students-form";
    }

    @PostMapping("/save")
    public String addStudent(@ModelAttribute("student") Student student, @RequestParam boolean isUpdate) {
        if (isUpdate) {
            studentService.updateStudent(student);
        } else {
            studentService.addStudent(student);
        }
        return "redirect:/students";
    }

    @GetMapping("/update")
    public String updateStudent(@RequestParam("id") String id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("isUpdate", true);
        return "students/students-form";
    }

    @GetMapping("/delete")
    public String deleteStudent(@RequestParam("id") String id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam("input") String input, @RequestParam(value = "sort", required = false) String sort, Model model) {
        List<Student> foundStudents = new ArrayList<>();

        if (input.matches("\\d+")) {
            foundStudents.add(this.studentService.getStudentById(input));
        } else {
            foundStudents.addAll(this.studentService.getByKeyword(input));
        }
        sortStudents(foundStudents, sort);
        model.addAttribute("students", foundStudents);
        model.addAttribute("isSearch", true);
        model.addAttribute("searchInput", input);
        return "students/students";
    }

    private void sortStudents(List<Student> students, String sort) {
        if (sort == null) {
            sort = "id";
        }
        switch (sort) {
            case "id" -> this.studentService.sortById(students);
            case "firstName" -> this.studentService.sortByFirstName(students);
            case "name" -> this.studentService.sortByName(students);
        }
    }
}
