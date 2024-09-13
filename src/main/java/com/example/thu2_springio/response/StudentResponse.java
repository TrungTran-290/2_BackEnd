package com.example.thu2_springio.response;

import com.example.thu2_springio.model.Student;
import com.example.thu2_springio.model.XepLoai;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class StudentResponse extends BaseResponse {
    private Long id;
    private String ten;
    private String thanhPho;
    private LocalDate ngaySinh;
    private XepLoai xepLoai;

    public static StudentResponse fromStudent(Student student){
         StudentResponse studentReponse=  StudentResponse.builder()
                .id(student.getId())
                .ten(student.getTen())
                .thanhPho(student.getThanhPho())
                .ngaySinh(student.getNgaySinh())
                .xepLoai(student.getXepLoai())
                .build();
        studentReponse.setCreatedAt(student.getCreatedAt());
        studentReponse.setUpdatedAt(student.getUpdatedAt());
        return studentReponse;
    }
}
