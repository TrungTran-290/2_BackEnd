package com.example.thu2_springio.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApiResponse {
    private int status;
    private String message;
    private Object data;
}
