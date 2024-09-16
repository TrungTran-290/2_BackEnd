package com.example.thu2_springio.service;

import com.example.thu2_springio.dto.StudentDTO;
import com.example.thu2_springio.dto.StudentImageDto;
import com.example.thu2_springio.model.Student;
import com.example.thu2_springio.model.StudentImage;
import com.example.thu2_springio.model.XepLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ServiceBasic {
    Page<Student> getStudents(Pageable pageable);

    List<Student> getallStudent();

    Student getStudentById(Long id);

    Student saveStudent(StudentDTO studentDTO);

    Student updateStudent(Long id, StudentDTO studentDTO);

    void removeStudent(Long id);

    List<Student> fingByThanhPho(String ThanhPho);

    List<Student> fingByThanhPhoOrTen(String ThanhPhoOrTen);
    List<Student> findByNgaySinhBetween(int year1, int year2);

    List<Student> findByXepLoai(XepLoai XepLoai);
    List<Student> searchStudents(
            XepLoai xepLoai, String ten, String thanhPho,
            int startYear,int endYear
    );
    List<StudentImage> getStudentImages(Long studentId);
    StudentImage getStudentImageById(Long id);
    StudentImage saveStudentImage(Long id, StudentImageDto studentImage);

    void removeStudentImage(Long id);
}

