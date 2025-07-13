package com.undoschool.coursesearch.controller;

import com.undoschool.coursesearch.dto.CourseSearchRequest;
import com.undoschool.coursesearch.dto.CourseSearchResponse;
import com.undoschool.coursesearch.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public CourseSearchResponse searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
            @RequestParam(defaultValue = "upcoming") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        CourseSearchRequest request = new CourseSearchRequest();
        request.setKeyword(q);
        request.setMinAge(minAge);
        request.setMaxAge(maxAge);
        request.setCategory(category);
        request.setType(type);
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        request.setFromDate(startDate);
        request.setSort(sort);
        request.setPage(page);
        request.setSize(size);

        return searchService.searchCourses(request);
    }
}
