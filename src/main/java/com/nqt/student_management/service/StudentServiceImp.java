package com.nqt.student_management.service;

import com.nqt.student_management.entity.Student;
import com.nqt.student_management.repository.StudentDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    private final StudentDataRepository studentDataRepository;

    @Autowired
    public StudentServiceImp(StudentDataRepository studentDataRepository) {
        this.studentDataRepository = studentDataRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDataRepository.findAll();
    }

    @Override
    public List<Student> getByKeyword(String keyword) {
        return this.studentDataRepository.findByKeyword(keyword);
    }

    @Override
    public Page<Student> getNStudents(int page, int n) {
        return studentDataRepository.findAll(PageRequest.of(page, n));
    }

    @Override
    public Student getStudentById(String id) {
        return this.studentDataRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Student addStudent(Student student) {
        if (student.getId().isBlank() || student.getEmail().isBlank() || student.getName().isBlank() || student.getFirstName().isBlank()) {
            return null;
        }
        return studentDataRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        Student foundStudent = studentDataRepository.findById(student.getId()).orElse(null);
        if (foundStudent == null) {
            return null;
        }
        return studentDataRepository.saveAndFlush(student);
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        studentDataRepository.deleteById(id);
    }

    @Override
    public void sortByName(List<Student> students) {
        if(students == null || students.isEmpty()) {
            return;
        }
        students.sort((o1, o2) -> {
            if (o1 == o2) return 0;
            return o1.getName().compareToIgnoreCase(o2.getName());
        });
    }

    @Override
    public void sortById(List<Student> students) {
        if(students == null || students.isEmpty()) {
            return;
        }
        students.sort((o1, o2) -> {
            if (o1 == o2) return 0;
            return o1.getId().compareToIgnoreCase(o2.getId());
        });
    }

    @Override
    public void sortByFirstName(List<Student> students) {
        if(students == null || students.isEmpty()) {
            return;
        }
        students.sort((o1, o2) -> {
            if (o1 == o2) return 0;
            return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
        });
    }
}
