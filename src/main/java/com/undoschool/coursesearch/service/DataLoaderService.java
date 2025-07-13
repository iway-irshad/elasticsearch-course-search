package com.undoschool.coursesearch.service;

import com.undoschool.coursesearch.model.CourseDocument;
import com.undoschool.coursesearch.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderService {

    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void loadData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/sample-courses.json");
            List<CourseDocument> courses = objectMapper.readValue(inputStream, new TypeReference<>() {});
            courseRepository.saveAll(courses);
            log.info("✅ Successfully indexed {} courses into Elasticsearch", courses.size());
        } catch (Exception e) {
            log.error("❌ Failed to load sample courses", e);
        }
    }
}
