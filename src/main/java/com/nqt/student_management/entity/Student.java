package com.nqt.student_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    @Column(name = "id", length = 10)
    private String id;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "student_name", length = 45)
    private String name;

    @Column(name = "email", length = 45)
    private String email;

    public Student() {}

    public Student(String id, String firstName, String name, String email) {
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getName(), student.getName()) && Objects.equals(getEmail(), student.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getName(), getEmail());
    }
}
