package com.example.thu2_springio.repository;

import com.example.thu2_springio.model.Student;
import com.example.thu2_springio.model.StudentImage;
import com.example.thu2_springio.model.XepLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);
    List<Student> findByXepLoai(XepLoai xepLoai);
    List<Student> findByTenContainingIgnoreCase(String ten);

    @Query("SELECT s FROM Student s WHERE LOWER(s.thanhPho) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Student> findByThanhPho(String name);


    @Query("SELECT s FROM Student s WHERE LOWER(s.thanhPho) LIKE LOWER(CONCAT('%',:name,'%')) OR LOWER(s.ten) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Student> findByThanhPhoAndTen(String name);

    @Query("SELECT s FROM Student s WHERE YEAR(s.ngaySinh) BETWEEN :nam1 AND :nam2")
    List<Student> findByNgaySinhBetween(int nam1, int nam2);

    @Query("SELECT s FROM Student s WHERE" +
            "(:xepLoai IS NULL OR s.xepLoai = :xepLoai) AND" +
            "(:ten IS NULL OR s.ten like %:ten%) AND" +
            "(:thanhPho IS NULL OR s.thanhPho LIKE %:thanhPho%) AND" +
            "(:startYear IS NULL OR YEAR(s.ngaySinh) >= :startYear) AND" +
            "(:endYear IS NULL OR YEAR(s.ngaySinh) <= :endYear)")
    List<Student> search(
            @Param("xepLoai") XepLoai xepLoai,
            @Param("ten") String ten,
            @Param("thanhPho") String thanhPho,
            @Param("startYear") int startYear,
            @Param("endYear") int endYear
    );



}
