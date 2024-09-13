package com.example.thu2_springio.response;

import com.example.thu2_springio.model.Student;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentListResponse {
    private List<Student> studentList;
    private int totalPages;
}
