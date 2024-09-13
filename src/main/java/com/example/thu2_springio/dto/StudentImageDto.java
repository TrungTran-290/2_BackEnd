package com.example.thu2_springio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentImageDto {
    @JsonProperty("student_id")
    @Min(value = 1, message = "Student id must be greater than 0")
    private Long studentId;
    @Size(min = 5, max = 100, message = "Image URL must be between 5 and 100")
    @JsonProperty("image_url")
    private String imageURL;
}
