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

```
📘 How to Test the Search API
Make sure:

Elasticsearch is running (docker-compose up)

Spring Boot app is running (localhost:8080)


### 1. Basic keyword search
curl "http://localhost:8080/api/search?q=math"

### 2. Filter by category and type
curl "http://localhost:8080/api/search?category=Math&type=COURSE"

### 3. Filter by age range
curl "http://localhost:8080/api/search?minAge=6&maxAge=10"

### 4. Price range + sorting
curl "http://localhost:8080/api/search?minPrice=20&maxPrice=100&sort=priceAsc"

### 5. Future sessions only
curl "http://localhost:8080/api/search?startDate=2025-07-15T00:00:00Z"

### 6. Full-text search + pagination
curl "http://localhost:8080/api/search?q=science&page=0&size=5"


## ✅ Testing the Application

### Prerequisites
- Elasticsearch running locally (`localhost:9200`)
- Spring Boot app running (`localhost:8080`)

### Sample CURL Commands

```bash
curl "http://localhost:8080/api/search?q=math"
curl "http://localhost:8080/api/search?category=Science&type=COURSE&minAge=7"

```
## ✨ Autocomplete Suggestions

**Endpoint:** `GET /api/search/suggest?q=prefix`

Returns up to 10 autocomplete suggestions based on course title.

### Example:

```bash
curl "http://localhost:8080/api/search/suggest?q=phy"

```
## 🔍 Fuzzy Search Support

When performing a keyword search (`q` param), the system supports **fuzzy matching** on the `title` field.

This allows users to find courses even with small typos.

### Example:

```bash
curl "http://localhost:8080/api/search?q=dinors"

```
## Assignment B – Autocomplete & Fuzzy Search (Implemented on `assignment-b-autocomplete-fuzzy` branch)

### Status: Partial

- ✅ Fuzzy search implemented on `title` field using `fuzziness: AUTO`
- ⚠️ Autocomplete via Completion Suggester is **not working fully** due to:
    - Incompatibility with `Completion` field in Elasticsearch Java Client v8.13.4
    - The `co.elastic.clients.elasticsearch._types.mapping.Completion` class was removed in this version
- ✅ We isolated this in a separate branch for independent review


