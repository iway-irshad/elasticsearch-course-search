package com.undoschool.coursesearch.repository;

import com.undoschool.coursesearch.model.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {
}
