package com.undoschool.coursesearch.service;

import co.elastic.clients.elasticsearch.*;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.undoschool.coursesearch.dto.CourseSearchResponse;
import com.undoschool.coursesearch.dto.CourseSearchRequest;
import com.undoschool.coursesearch.model.CourseDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchClient elasticsearchClient;

    public CourseSearchResponse searchCourses(CourseSearchRequest request) {
        try {
            BoolQuery.Builder boolQuery = new BoolQuery.Builder();

            // Full-text search
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                boolQuery.must(MultiMatchQuery.of(m -> m
                        .fields("title", "description")
                        .query(request.getKeyword())
                )._toQuery());
            }

            // Exact match filters
            if (request.getCategory() != null) {
                boolQuery.filter(TermQuery.of(t -> t
                        .field("category")
                        .value(request.getCategory())
                )._toQuery());
            }

            if (request.getType() != null) {
                boolQuery.filter(TermQuery.of(t -> t
                        .field("type")
                        .value(request.getType())
                )._toQuery());
            }

            // Age range
            if (request.getMinAge() != null || request.getMaxAge() != null) {
                RangeQuery.Builder ageRange = new RangeQuery.Builder().field("minAge");
                if (request.getMinAge() != null) ageRange.gte(JsonData.of(request.getMinAge()));
                if (request.getMaxAge() != null) ageRange.lte(JsonData.of(request.getMaxAge()));
                boolQuery.filter(q -> q.range(ageRange.build()));
            }

            // Price range
            if (request.getMinPrice() != null || request.getMaxPrice() != null) {
                RangeQuery.Builder priceRange = new RangeQuery.Builder().field("price");
                if (request.getMinPrice() != null) priceRange.gte(JsonData.of(request.getMinPrice()));
                if (request.getMaxPrice() != null) priceRange.lte(JsonData.of(request.getMaxPrice()));
                boolQuery.filter(q -> q.range(priceRange.build()));
            }

            // Future session date
            if (request.getFromDate() != null) {
                RangeQuery dateRange = new RangeQuery.Builder()
                        .field("nextSessionDate")
                        .gte(JsonData.of(request.getFromDate()))
                        .build();
                boolQuery.filter(q -> q.range(dateRange));
            }

            // Sorting
            String sortField;
            boolean ascending;
            if ("priceAsc".equalsIgnoreCase(request.getSort())) {
                ascending = true;
                sortField = "price";
            } else if ("priceDesc".equalsIgnoreCase(request.getSort())) {
                sortField = "price";
                ascending = false;
            } else {
                ascending = true;
                sortField = "nextSessionDate";
            }

            SearchResponse<CourseDocument> response = elasticsearchClient.search(s -> s
                            .index("courses")
                            .query(q -> q.bool(boolQuery.build()))
                            .from(request.getPage() * request.getSize())
                            .size(request.getSize())
                            .sort(srt -> srt
                                    .field(f -> f
                                            .field(sortField)
                                            .order(ascending ? SortOrder.Asc : SortOrder.Desc)
                                    )
                            ),
                    CourseDocument.class
            );

            List<CourseSearchResponse.CourseDTO> results = response.hits().hits().stream()
                    .map(hit -> {
                        CourseDocument doc = hit.source();
                        return new CourseSearchResponse.CourseDTO(
                                doc.getId(),
                                doc.getTitle(),
                                doc.getCategory(),
                                doc.getPrice(),
                                doc.getNextSessionDate()
                        );
                    }).toList();

            return new CourseSearchResponse(response.hits().total().value(), results);

        } catch (Exception e) {
            throw new RuntimeException("Error while searching courses", e);
        }
    }
}
