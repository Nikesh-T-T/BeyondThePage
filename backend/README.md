# BeyondThePage — Backend

A personal reading progress tracker backend built with Java 21 and Spring Boot 3.

---

## Prerequisites

- Java 21+
- Maven 3.9+

---

## Running the Application

```bash
cd backend
mvn spring-boot:run
```

The server starts on `http://localhost:8080`.

---

## H2 Console

During development an in-memory H2 database is used. Access the console at:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:beyondthepage
Username: sa
Password: (blank)
```

---

## Project Structure

```
backend/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/beyondthepage/
    │   │   ├── BeyondThePageApplication.java
    │   │   ├── config/
    │   │   │   └── JacksonConfig.java
    │   │   ├── controller/
    │   │   │   ├── BookController.java
    │   │   │   └── DashboardController.java
    │   │   ├── dto/
    │   │   │   ├── request/
    │   │   │   │   ├── ChapterRequest.java
    │   │   │   │   ├── CreateBookRequest.java
    │   │   │   │   └── UpdateProgressRequest.java
    │   │   │   └── response/
    │   │   │       ├── ApiErrorResponse.java
    │   │   │       ├── ApiResponse.java
    │   │   │       ├── BookCreatedResponse.java
    │   │   │       ├── BookDetailResponse.java
    │   │   │       ├── BookSummaryResponse.java
    │   │   │       ├── ChapterDetailResponse.java
    │   │   │       ├── DailyBookProgressResponse.java
    │   │   │       ├── DashboardSummaryResponse.java
    │   │   │       ├── MonthlyDashboardResponse.java
    │   │   │       └── WeeklyDashboardResponse.java
    │   │   ├── entity/
    │   │   │   ├── Book.java
    │   │   │   ├── BookChapter.java
    │   │   │   ├── BookChapterId.java
    │   │   │   └── ReadingProgress.java
    │   │   ├── enums/
    │   │   │   ├── BookStatus.java
    │   │   │   └── ChapterStatus.java
    │   │   ├── exception/
    │   │   │   ├── BookAlreadyExistsException.java
    │   │   │   ├── BookNotFoundException.java
    │   │   │   ├── GlobalExceptionHandler.java
    │   │   │   └── InvalidChapterDataException.java
    │   │   ├── repository/
    │   │   │   ├── BookChapterRepository.java
    │   │   │   ├── BookRepository.java
    │   │   │   └── ReadingProgressRepository.java
    │   │   └── service/
    │   │       ├── BookService.java
    │   │       ├── DashboardService.java
    │   │       └── ProgressCalculationService.java
    │   └── resources/
    │       ├── application.properties
    │       └── db/migration/
    │           ├── V1__init.sql
    │           └── V2__add_cover_image.sql
    └── test/
        └── java/com/beyondthepage/
            ├── exception/
            │   └── GlobalExceptionHandlerTest.java
            ├── integration/
            │   └── BeyondThePageIntegrationTest.java
            └── service/
                ├── BookServiceTest.java
                ├── DashboardServiceTest.java
                └── ProgressCalculationServiceTest.java
```

---

## Database Structure

Schema is managed by Flyway (`V1__init.sql`, `V2__add_cover_image.sql`, `V3__add_category.sql`, `V4__add_author.sql`). The application uses `ddl-auto=validate` — Hibernate validates against the schema but never modifies it.

### `books`

| Column | Type | Constraints |
|--------|------|-------------|
| `book_name` | `VARCHAR(255)` | PK, NOT NULL |
| `total_pages` | `INT` | NOT NULL, > 0 |
| `planned_days` | `INT` | NOT NULL, > 0 |
| `start_date` | `DATE` | NOT NULL |
| `category` | `VARCHAR(100)` | NOT NULL |
| `author` | `VARCHAR(255)` | NOT NULL |
| `cover_image` | `BYTEA` | NULL |
| `cover_image_type` | `VARCHAR(50)` | NULL |

### `book_chapters`

| Column | Type | Constraints |
|--------|------|-------------|
| `book_name` | `VARCHAR(255)` | PK (composite), FK → `books.book_name` ON DELETE CASCADE |
| `chapter_number` | `INT` | PK (composite) |
| `chapter_title` | `VARCHAR(500)` | NOT NULL |
| `start_page` | `INT` | NOT NULL |
| `end_page` | `INT` | NOT NULL, `start_page <= end_page` |

### `reading_progress`

| Column | Type | Constraints |
|--------|------|-------------|
| `book_name` | `VARCHAR(255)` | PK, FK → `books.book_name` ON DELETE CASCADE |
| `completed_pages` | `INT` | NOT NULL, DEFAULT 0, >= 0 |

---

## API Reference

All responses are wrapped in a consistent envelope:

**Success**
```json
{
  "status": "SUCCESS",
  "message": "...",
  "data": { }
}
```

**Error**
```json
{
  "status": "ERROR",
  "message": "...",
  "errors": ["field: reason"]
}
```

---

### Books

#### `POST /api/books` — Create a book

**Request body**
```json
{
  "bookName": "Clean Code",
  "category": "Technology",
  "author": "Robert C. Martin",
  "totalPages": 431,
  "plannedDays": 30,
  "startDate": "2026-07-01",
  "chapters": [
    { "chapterNumber": 1, "chapterTitle": "Clean Code",        "startPage": 1,   "endPage": 50  },
    { "chapterNumber": 2, "chapterTitle": "Meaningful Names",  "startPage": 51,  "endPage": 120 },
    { "chapterNumber": 3, "chapterTitle": "Functions",         "startPage": 121, "endPage": 431 }
  ]
}
```

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `bookName` | string | yes | not blank |
| `category` | string | yes | not blank |
| `author` | string | yes | not blank |
| `totalPages` | int | yes | > 0 |
| `plannedDays` | int | yes | > 0 |
| `startDate` | date (`yyyy-MM-dd`) | yes | not null |
| `chapters` | array | yes | not empty, each chapter valid |
| `chapters[].chapterNumber` | int | yes | not null |
| `chapters[].chapterTitle` | string | yes | not blank |
| `chapters[].startPage` | int | yes | > 0 |
| `chapters[].endPage` | int | yes | > 0 |

Chapter validation (service layer):
- First chapter `startPage` must be `1`
- Last chapter `endPage` must equal `totalPages`
- No gaps or overlaps between consecutive chapters
- Each chapter `startPage <= endPage`

**Response — `201 Created`**
```json
{
  "status": "SUCCESS",
  "message": "Book created successfully",
  "data": {
    "bookName": "Clean Code",
    "category": "Technology",
    "author": "Robert C. Martin",
    "totalPages": 431,
    "plannedDays": 30,
    "startDate": "2026-07-01",
    "completedPages": 0,
    "hasCoverImage": false
  }
}
```

**curl example**
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Clean Code",
    "category": "Technology",
    "author": "Robert C. Martin",
    "totalPages": 431,
    "plannedDays": 30,
    "startDate": "2026-07-01",
    "chapters": [
      {"chapterNumber": 1, "chapterTitle": "Clean Code",       "startPage": 1,   "endPage": 50 },
      {"chapterNumber": 2, "chapterTitle": "Meaningful Names", "startPage": 51,  "endPage": 120},
      {"chapterNumber": 3, "chapterTitle": "Functions",        "startPage": 121, "endPage": 431}
    ]
  }'
```

---

#### `GET /api/books` — List all books

No request body or parameters.

**Response — `200 OK`**
```json
{
  "status": "SUCCESS",
  "message": "Books fetched successfully",
  "data": [
    {
      "bookName": "Clean Code",
      "category": "Technology",
      "author": "Robert C. Martin",
      "totalPages": 431,
      "plannedDays": 30,
      "startDate": "2026-07-01",
      "completedPages": 120,
      "remainingPages": 311,
      "completionPercentage": 27.84,
      "plannedDailyPages": 14.37,
      "targetEndDate": "2026-07-31",
      "currentStatus": "ON_TRACK",
      "daysElapsed": 10,
      "daysRemaining": 20,
      "hasCoverImage": true
    }
  ]
}
```

**curl example**
```bash
curl http://localhost:8080/api/books
```

---

#### `GET /api/books/{bookName}` — Get book detail

**Path parameter:** `bookName` — URL-encoded if it contains spaces

**Response — `200 OK`**
```json
{
  "status": "SUCCESS",
  "message": "Book details fetched successfully",
  "data": {
    "bookName": "Clean Code",
    "category": "Technology",
    "author": "Robert C. Martin",
    "totalPages": 431,
    "plannedDays": 30,
    "startDate": "2026-07-01",
    "targetEndDate": "2026-07-31",
    "completedPages": 120,
    "remainingPages": 311,
    "completionPercentage": 27.84,
    "plannedDailyPages": 14.37,
    "expectedPagesByToday": 143.7,
    "variancePages": -23.7,
    "currentStatus": "AT_RISK",
    "daysElapsed": 10,
    "daysRemaining": 20,
    "completedChapters": 2,
    "pendingChapters": 1,
    "overdueChapters": 0,
    "hasCoverImage": true,
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Clean Code",       "startPage": 1,   "endPage": 50,  "status": "COMPLETED"   },
      { "chapterNumber": 2, "chapterTitle": "Meaningful Names", "startPage": 51,  "endPage": 120, "status": "COMPLETED"   },
      { "chapterNumber": 3, "chapterTitle": "Functions",        "startPage": 121, "endPage": 431, "status": "IN_PROGRESS" }
    ]
  }
}
```

**Error — `404 Not Found`**
```json
{ "status": "ERROR", "message": "Book not found: Clean Code" }
```

**curl example**
```bash
curl "http://localhost:8080/api/books/Clean%20Code"
```

---

#### `PUT /api/books/{bookName}/progress` — Update reading progress

**Path parameter:** `bookName`

**Request body**
```json
{ "completedPages": 150 }
```

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| `completedPages` | int | yes | >= 0, <= `totalPages` (service-enforced) |

**Response — `200 OK`** (no body)

**Errors**

| Status | Condition |
|--------|-----------|
| `400` | `completedPages` is negative or exceeds `totalPages` |
| `404` | Book not found |

**curl example**
```bash
curl -X PUT "http://localhost:8080/api/books/Clean%20Code/progress" \
  -H "Content-Type: application/json" \
  -d '{"completedPages": 150}'
```

---

#### `DELETE /api/books/{bookName}` — Delete a book

Permanently removes the book and all related data (chapters and reading progress).

**Path parameter:** `bookName`

**Response — `204 No Content`** (no body)

**Error — `404 Not Found`**
```json
{ "status": "ERROR", "message": "Book not found: Clean Code" }
```

**curl example**
```bash
curl -X DELETE "http://localhost:8080/api/books/Clean%20Code"
```

---

#### `PUT /api/books/{bookName}/cover` — Upload or replace book cover

**Content-Type:** `multipart/form-data`

| Part | Type | Required | Validation |
|------|------|----------|------------|
| `file` | image file | yes | JPEG, PNG, or WEBP; max 5 MB |

**Response — `200 OK`**
```json
{ "status": "SUCCESS", "message": "Cover uploaded successfully", "data": null }
```

**Errors**

| Status | Condition |
|--------|-----------|
| `400` | File missing, unsupported type, or exceeds 5 MB |
| `404` | Book not found |

**curl example**
```bash
curl -X PUT "http://localhost:8080/api/books/Clean%20Code/cover" \
  -F "file=@/path/to/cover.jpg"
```

---

#### `GET /api/books/{bookName}/cover` — Fetch cover image

Returns raw image bytes with the correct `Content-Type` header (`image/jpeg`, `image/png`, or `image/webp`). Use directly as an `<img src>` URL.

**Response — `200 OK`** binary image body  
**Response — `404 Not Found`** if the book has no cover

**curl example**
```bash
curl "http://localhost:8080/api/books/Clean%20Code/cover" --output cover.jpg
```

---

### Dashboard

#### `GET /api/dashboard/summary` — Overview counts

No parameters.

**Response — `200 OK`**
```json
{
  "status": "SUCCESS",
  "message": "Dashboard summary fetched successfully",
  "data": {
    "totalBooks": 5,
    "notStartedBooks": 1,
    "inProgressBooks": 3,
    "completedBooks": 1,
    "overdueBooks": 0
  }
}
```

> `inProgressBooks` = `ON_TRACK` + `AT_RISK` books combined.

**curl example**
```bash
curl http://localhost:8080/api/dashboard/summary
```

---

#### `GET /api/dashboard/monthly?month={yyyy-MM}` — Monthly view

**Query parameter:** `month` — year-month in `yyyy-MM` format (e.g. `2026-07`)

**Response — `200 OK`**
```json
{
  "status": "SUCCESS",
  "message": "Monthly dashboard fetched successfully",
  "data": {
    "selectedMonth": "2026-07",
    "booksPlannedToFinish": 2,
    "booksCompleted": 1,
    "booksOverdue": 0,
    "booksNotStarted": 1,
    "booksOnTrack": 3
  }
}
```

> `booksPlannedToFinish` counts books whose `targetEndDate` falls within the selected month.

**Error — `400`** if `month` cannot be parsed as `yyyy-MM`.

**curl example**
```bash
curl "http://localhost:8080/api/dashboard/monthly?month=2026-07"
```

---

#### `GET /api/dashboard/weekly?date={yyyy-MM-dd}` — Weekly chapter view

**Query parameter:** `date` — any date within the week (week is Mon–Sun)

**Response — `200 OK`**
```json
{
  "status": "SUCCESS",
  "message": "Weekly dashboard fetched successfully",
  "data": {
    "weekStartDate": "2026-07-06",
    "weekEndDate":   "2026-07-12",
    "chaptersPlanned":   3,
    "chaptersCompleted": 1,
    "chaptersOverdue":   0,
    "chaptersNotStarted": 2,
    "totalPlannedPages":   130,
    "totalCompletedPages": 45,
    "books": [
      {
        "bookName": "Clean Code",
        "category": "Technology",
        "chapters": [
          { "chapterNumber": 1, "chapterTitle": "Clean Code",      "startPage": 1,  "endPage": 50,  "status": "COMPLETED"    },
          { "chapterNumber": 2, "chapterTitle": "Meaningful Names", "startPage": 51, "endPage": 120, "status": "IN_PROGRESS"  }
        ]
      }
    ]
  }
}
```

> `books` contains one entry per book that has at least one chapter falling within the week window. Each chapter entry includes its status computed at the time of the request.

**Error — `400`** if `date` cannot be parsed as `yyyy-MM-dd`.

**curl example**
```bash
curl "http://localhost:8080/api/dashboard/weekly?date=2026-06-19"
```

---

#### `GET /api/dashboard/daily?date={yyyy-MM-dd}` — Per-book daily view

**Query parameter:** `date` — the reference date for plan vs actual comparison

**Response — `200 OK`**
```json
{
  "status": "SUCCESS",
  "message": "Daily dashboard fetched successfully",
  "data": [
    {
      "bookName": "Clean Code",
      "totalPages": 431,
      "plannedPagesByDate": 143.7,
      "completedPages": 120,
      "variancePages": -23.7,
      "status": "AT_RISK",
      "hasCoverImage": true,
      "plannedPageRangeStart": 121,
      "plannedPageRangeEnd": 144
    }
  ]
}
```

> Only books whose reading window covers the requested date (`startDate <= date <= targetEndDate`) and are not yet completed are returned. `plannedPageRangeStart` is `completedPages + 1`; `plannedPageRangeEnd` is `ceil(plannedPagesByDate)` capped at `totalPages`. Together they show which pages are pending up to today's plan target. `variancePages` is `completedPages - plannedPagesByDate` (negative means behind).

**Error — `400`** if `date` cannot be parsed as `yyyy-MM-dd`.

**curl example**
```bash
curl "http://localhost:8080/api/dashboard/daily?date=2026-07-17"
```

---

## Derived Values

All fields below are computed at query time — never stored in the database.

| Field | Formula |
|-------|---------|
| `targetEndDate` | `startDate + plannedDays` |
| `completionPercentage` | `completedPages / totalPages × 100` |
| `remainingPages` | `totalPages − completedPages` |
| `plannedDailyPages` | `totalPages / plannedDays` |
| `expectedPagesByToday` | `min(plannedDailyPages × daysElapsed, totalPages)` |
| `variancePages` | `completedPages − expectedPagesByToday` |
| `daysElapsed` | `max(0, referenceDate − startDate)` |
| `daysRemaining` | `max(0, targetEndDate − referenceDate)` |

---

## Book Status

| Status | Condition |
|--------|-----------|
| `NOT_STARTED` | `completedPages == 0` |
| `COMPLETED` | `completedPages >= totalPages` |
| `OVERDUE` | Past `targetEndDate` and not completed |
| `ON_TRACK` | `completedPages >= expectedPagesByToday` |
| `AT_RISK` | Behind expected pace, target date not yet passed |

## Chapter Status

| Status | Condition |
|--------|-----------|
| `COMPLETED` | `completedPages >= chapter.endPage` |
| `IN_PROGRESS` | `completedPages >= chapter.startPage` (and not completed) |
| `OVERDUE` | `expectedPagesByToday > chapter.endPage` (and not completed) |
| `NOT_STARTED` | None of the above |
