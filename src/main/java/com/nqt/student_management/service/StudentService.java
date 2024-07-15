package com.nqt.student_management.service;

import com.nqt.student_management.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    List<Student> getByKeyword(String keyword);

    Page<Student> getNStudents(int page, int n);

    Student getStudentById(String id);

    Student addStudent(Student student);

    Student updateStudent(Student student);

    void deleteStudent(String id);

    void sortByName(List<Student> students);

    void sortById(List<Student> students);

    void sortByFirstName(List<Student> students);
}
