package com.undoschool.coursesearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CourseSearchResponse {
    private long total;
    private List<CourseDTO> courses;

    @Data
    @AllArgsConstructor
    public static class CourseDTO {
        private String id;
        private String title;
        private String category;
        private double price;
        private ZonedDateTime nextSessionDate;
    }
}
