package com.example.thu2_springio.dto;

import com.example.thu2_springio.model.XepLoai;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StudentDTO {
    @NotBlank(message = "Không được để tên trống")
    @Size(min = 2, max =50, message = "Tên phải từ 2 đến 50 ký tự")
    private String ten;
    @NotBlank(message = "Không được để trống thành phố")
    private String thanhPho;
    @DateTimeFormat(pattern = "dd/MM/yyyy")

    private LocalDate ngaySinh;

    @NotNull(message = "Xếp loại không được để trống")
    @Enumerated(EnumType.STRING)
    private XepLoai xepLoai;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data

    public static class StudentImagesDto {
        @JsonProperty("student_id")
        @Min(value = 1, message = "Student id must be greater than 0")
        private Long studentId;
        @Size(min = 5, max = 100, message = "Image URL must be between 5 and 100")
        @JsonProperty("image_url")
        private String imageURL;
    }
}
