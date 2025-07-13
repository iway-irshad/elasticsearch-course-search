## 🚀 Elasticsearch Setup

To run Elasticsearch locally:

1. Ensure Docker and Docker Compose are installed.
2. Run the following command in the root directory:

```bash
docker-compose up -d

```
## 🔧 Elasticsearch Configuration

This application connects to a local Elasticsearch instance running at: http:localhost:9200
### No authentication is required.

Make sure Elasticsearch is running using Docker:

```bash 
docker-compose up -d

```
## 📦 Sample Course Data Ingestion

Upon application startup, the app will automatically:

- Load `src/main/resources/sample-courses.json`
- Parse all course objects
- Index them into Elasticsearch’s `courses` index

### ✅ How to verify:

After starting the app and Elasticsearch (via `docker-compose up`), visit:

```bash
curl http://localhost:9200/courses/_search?pretty

```
## 🔍 Search API

**Endpoint:** `GET /api/search`

### Query Parameters:
- `q` – full-text keyword search (title, description)
- `category`, `type` – exact match
- `minAge`, `maxAge`, `minPrice`, `maxPrice` – numeric filters
- `startDate` – ISO-8601 datetime filter (from session date)
- `sort` – `upcoming` (default), `priceAsc`, `priceDesc`
- `page`, `size` – for pagination

### Sample:
```http
GET /api/search?q=art&minPrice=10&sort=priceDesc&page=0&size=5