package com.undoschool.coursesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "courses")  // Elasticsearch index name
public class CourseDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)  // Full-text search
    private String title;

    @Field(type = FieldType.Text)  // Full-text search
    private String description;

    @Field(type = FieldType.Keyword)  // Exact match filtering
    private String category;

    @Field(type = FieldType.Keyword)  // ENUM-like filtering
    private String type;

    @Field(type = FieldType.Keyword)
    private String gradeRange;

    @Field(type = FieldType.Integer)
    private int minAge;

    @Field(type = FieldType.Integer)
    private int maxAge;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime nextSessionDate;
}
