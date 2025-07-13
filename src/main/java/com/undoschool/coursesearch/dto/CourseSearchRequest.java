package com.undoschool.coursesearch.dto;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class CourseSearchRequest {
    private String keyword;
    private String category;
    private String type;
    private Integer minAge;
    private Integer maxAge;
    private Double minPrice;
    private Double maxPrice;
    private ZonedDateTime fromDate; // for nextSessionDate >=
    private String sort;  // priceAsc, priceDesc, or null
    private int page = 0;
    private int size = 10;
}
