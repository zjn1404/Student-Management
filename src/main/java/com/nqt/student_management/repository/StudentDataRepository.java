package com.nqt.student_management.repository;

import com.nqt.student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDataRepository extends JpaRepository<Student, String> {

    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:keyword% OR s.name LIKE %:keyword% OR s.email LIKE %:keyword% OR concat(s.firstName, ' ', s.name) LIKE :keyword")
    List<Student> findByKeyword(String keyword);

}
