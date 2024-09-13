package com.example.thu2_springio.repository;

import com.example.thu2_springio.model.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentImageRepo extends JpaRepository<StudentImage, Long> {
    List<StudentImage> findByStudentId(Long id);
//    List<StudentImage> getStudentImages(Long studentId);
}
