import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;

public class TestRange {
    public static void main(String[] args) {
        RangeQuery rq = RangeQuery.of(r -> r
                .field("price")
                .gte(JsonData.of(100))
                .lte(JsonData.of(500))
        );
        System.out.println(rq);
    }
}
