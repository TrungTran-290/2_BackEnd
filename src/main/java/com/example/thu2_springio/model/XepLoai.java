package com.example.thu2_springio.model;

public enum XepLoai {
    GIOI("Giỏi"), KHA("Khá"), TRUNG_BINH("Trung bình"), YEU("Yếu");
    private String xl;
    XepLoai(String xl) {
    this.xl = xl;
    }
    public String getXl() {
        return xl;
    }

}
