package com.undoschool;

import com.undoschool.coursesearch.dto.CourseSearchRequest;
import com.undoschool.coursesearch.dto.CourseSearchResponse;
import com.undoschool.coursesearch.model.CourseDocument;
import com.undoschool.coursesearch.repository.CourseRepository;
import com.undoschool.coursesearch.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class SearchApiIntegrationTest {

    static ElasticsearchContainer elasticsearch = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.13.4")
            .withEnv("xpack.security.enabled", "false");

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SearchService searchService;

    @BeforeEach
    public void setUp() {
        courseRepository.deleteAll();

        // Add a test course
        CourseDocument doc = new CourseDocument();
        doc.setId("test-123");
        doc.setTitle("Dinosaurs 101");
        doc.setDescription("Learn about ancient creatures.");
        doc.setCategory("Science");
        doc.setType("COURSE");
        doc.setGradeRange("4th–6th");
        doc.setMinAge(7);
        doc.setMaxAge(10);
        doc.setPrice(49.99);
        doc.setNextSessionDate(ZonedDateTime.now().plusDays(5));

        courseRepository.save(doc);
    }

//    @Test
//    public void testFuzzySearchFindsMisspelledTitle() {
//        CourseSearchRequest request = new CourseSearchRequest();
//        request.setKeyword("dinors");
//
//        CourseSearchResponse response = searchService.searchCourses(request);
//        List<CourseSearchResponse.CourseDTO> courses = response.getCourses();
//
//        assertFalse(courses.isEmpty());
//        assertTrue(courses.get(0).getTitle().toLowerCase().contains("dino"));
//    }
}
