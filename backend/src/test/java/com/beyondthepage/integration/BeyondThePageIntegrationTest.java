package com.beyondthepage.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.beyondthepage.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BeyondThePageIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	private static final String CREATE_CLEAN_CODE_JSON = """
			{
			  "bookName": "Clean Code",
			  "category": "Technology",
			  "author": "Robert C. Martin",
			  "totalPages": 100,
			  "plannedDays": 10,
			  "startDate": "2026-07-01",
			  "chapters": [
			    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 50 },
			    { "chapterNumber": 2, "chapterTitle": "Chapter 2", "startPage": 51, "endPage": 100 }
			  ]
			}
			""";

	@BeforeEach
	void setUp() throws Exception {
		bookRepository.deleteAll();
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(CREATE_CLEAN_CODE_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void shouldCreateBookAndReturnCreatedResponse() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "Refactoring",
						  "category": "Technology",
						  "author": "Martin Fowler",
						  "totalPages": 200,
						  "plannedDays": 20,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 200 }
						  ]
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data.bookName", is("Refactoring")))
				.andExpect(jsonPath("$.data.category", is("Technology")))
				.andExpect(jsonPath("$.data.author", is("Martin Fowler")))
				.andExpect(jsonPath("$.data.totalPages", is(200)))
				.andExpect(jsonPath("$.data.plannedDays", is(20)))
				.andExpect(jsonPath("$.data.completedPages", is(0)));
	}

	@Test
	void shouldCreateBookWithCategoryAndReturnItInResponses() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "Domain-Driven Design",
						  "category": "Technology",
						  "author": "Eric Evans",
						  "totalPages": 150,
						  "plannedDays": 15,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 150 }
						  ]
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.category", is("Technology")));

		mockMvc.perform(get("/api/books/Domain-Driven Design"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.category", is("Technology")));

		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[?(@.bookName == 'Domain-Driven Design')].category",
						org.hamcrest.Matchers.hasItem("Technology")));
	}

	@Test
	void shouldReturn400WhenCategoryIsOmitted() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "Refactoring",
						  "totalPages": 200,
						  "plannedDays": 20,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 200 }
						  ]
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldCreateBookWithAuthorAndReturnItInResponses() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "Domain-Driven Design",
						  "category": "Technology",
						  "author": "Eric Evans",
						  "totalPages": 150,
						  "plannedDays": 15,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 150 }
						  ]
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.author", is("Eric Evans")));

		mockMvc.perform(get("/api/books/Domain-Driven Design"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.author", is("Eric Evans")));

		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[?(@.bookName == 'Domain-Driven Design')].author",
						org.hamcrest.Matchers.hasItem("Eric Evans")));
	}

	@Test
	void shouldReturn400WhenAuthorIsOmitted() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "Refactoring",
						  "category": "Technology",
						  "totalPages": 200,
						  "plannedDays": 20,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 200 }
						  ]
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturn409WhenCreatingBookWithDuplicateName() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(CREATE_CLEAN_CODE_JSON))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.status", is("ERROR")))
				.andExpect(jsonPath("$.message", is("Book already exists: Clean Code")));
	}

	@Test
	void shouldReturn400WhenFirstChapterDoesNotStartAtPageOne() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "The Pragmatic Programmer",
						  "category": "Technology",
						  "author": "Andrew Hunt",
						  "totalPages": 100,
						  "plannedDays": 10,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 5, "endPage": 100 }
						  ]
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturn400WhenLastChapterDoesNotEndAtTotalPages() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "The Pragmatic Programmer",
						  "category": "Technology",
						  "author": "Andrew Hunt",
						  "totalPages": 100,
						  "plannedDays": 10,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 90 }
						  ]
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturn400WhenChaptersHaveGap() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "The Pragmatic Programmer",
						  "category": "Technology",
						  "author": "Andrew Hunt",
						  "totalPages": 100,
						  "plannedDays": 10,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 40 },
						    { "chapterNumber": 2, "chapterTitle": "Chapter 2", "startPage": 50, "endPage": 100 }
						  ]
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturn400WhenBookNameIsBlank() throws Exception {
		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "bookName": "",
						  "category": "Technology",
						  "author": "Andrew Hunt",
						  "totalPages": 100,
						  "plannedDays": 10,
						  "startDate": "2026-07-01",
						  "chapters": [
						    { "chapterNumber": 1, "chapterTitle": "Chapter 1", "startPage": 1, "endPage": 100 }
						  ]
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturnAllBooksWithDerivedFields() throws Exception {
		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data[0].bookName", is("Clean Code")))
				.andExpect(jsonPath("$.data[0].category", is("Technology")))
				.andExpect(jsonPath("$.data[0].author", is("Robert C. Martin")))
				.andExpect(jsonPath("$.data[0].totalPages", is(100)))
				.andExpect(jsonPath("$.data[0].completedPages", is(0)))
				.andExpect(jsonPath("$.data[0].remainingPages", is(100)))
				.andExpect(jsonPath("$.data[0].targetEndDate", notNullValue()))
				.andExpect(jsonPath("$.data[0].currentStatus", notNullValue()));
	}

	@Test
	void shouldReturnBookDetailWithChapters() throws Exception {
		mockMvc.perform(get("/api/books/Clean Code"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data.bookName", is("Clean Code")))
				.andExpect(jsonPath("$.data.category", is("Technology")))
				.andExpect(jsonPath("$.data.author", is("Robert C. Martin")))
				.andExpect(jsonPath("$.data.chapters", hasSize(2)))
				.andExpect(jsonPath("$.data.chapters[0].chapterTitle", is("Chapter 1")))
				.andExpect(jsonPath("$.data.chapters[0].startPage", is(1)))
				.andExpect(jsonPath("$.data.chapters[0].endPage", is(50)))
				.andExpect(jsonPath("$.data.chapters[1].chapterTitle", is("Chapter 2")))
				.andExpect(jsonPath("$.data.completedChapters", notNullValue()))
				.andExpect(jsonPath("$.data.pendingChapters", notNullValue()));
	}

	@Test
	void shouldReturn404WhenBookNotFound() throws Exception {
		mockMvc.perform(get("/api/books/Unknown Book"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is("ERROR")))
				.andExpect(jsonPath("$.message", is("Book not found: Unknown Book")));
	}

	@Test
	void shouldUpdateProgressSuccessfully() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 75 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/books/Clean Code"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.completedPages", is(75)))
				.andExpect(jsonPath("$.data.remainingPages", is(25)))
				.andExpect(jsonPath("$.data.completionPercentage", is(75.0)));
	}

	@Test
	void shouldAllowProgressToBeReducedToLowerValue() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 80 }"))
				.andExpect(status().isOk());

		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 30 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/books/Clean Code"))
				.andExpect(jsonPath("$.data.completedPages", is(30)));
	}

	@Test
	void shouldAllowProgressToBeSetToZero() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 50 }"))
				.andExpect(status().isOk());

		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 0 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/books/Clean Code"))
				.andExpect(jsonPath("$.data.completedPages", is(0)));
	}

	@Test
	void shouldReturn400WhenCompletedPagesExceedsTotalPages() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 999 }"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturn404WhenUpdatingProgressForUnknownBook() throws Exception {
		mockMvc.perform(put("/api/books/Unknown Book/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 10 }"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldSetBookStatusToCompletedWhenAllPagesRead() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 100 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/books"))
				.andExpect(jsonPath("$.data[0].currentStatus", is("COMPLETED")));
	}

	@Test
	void shouldSetBookStatusToNotStartedWhenZeroPagesRead() throws Exception {
		mockMvc.perform(get("/api/books"))
				.andExpect(jsonPath("$.data[0].currentStatus", is("NOT_STARTED")));
	}

	@Test
	void shouldReturnDashboardSummaryWithCorrectCounts() throws Exception {
		mockMvc.perform(get("/api/dashboard/summary"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data.totalBooks", is(1)))
				.andExpect(jsonPath("$.data.notStartedBooks", is(1)))
				.andExpect(jsonPath("$.data.inProgressBooks", is(0)))
				.andExpect(jsonPath("$.data.completedBooks", is(0)))
				.andExpect(jsonPath("$.data.overdueBooks", is(0)));
	}

	@Test
	void shouldReturnDashboardSummaryWithCompletedBookCounted() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 100 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/dashboard/summary"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.totalBooks", is(1)))
				.andExpect(jsonPath("$.data.completedBooks", is(1)))
				.andExpect(jsonPath("$.data.notStartedBooks", is(0)));
	}

	@Test
	void shouldReturnMonthlyDashboard() throws Exception {
		mockMvc.perform(get("/api/dashboard/monthly?month=2026-07"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data.selectedMonth", is("2026-07")))
				.andExpect(jsonPath("$.data.booksPlannedToFinish", notNullValue()));
	}

	@Test
	void shouldReturn400ForInvalidMonthFormat() throws Exception {
		mockMvc.perform(get("/api/dashboard/monthly?month=not-a-month"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldReturnWeeklyDashboard() throws Exception {
		mockMvc.perform(get("/api/dashboard/weekly?date=2026-07-08"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data.weekStartDate", is("2026-07-06")))
				.andExpect(jsonPath("$.data.weekEndDate", is("2026-07-12")))
				.andExpect(jsonPath("$.data.books", notNullValue()))
				.andExpect(jsonPath("$.data.books[0].bookName", is("Clean Code")))
				.andExpect(jsonPath("$.data.books[0].chapters", hasSize(2)))
				.andExpect(jsonPath("$.data.books[0].chapters[0].chapterTitle", is("Chapter 1")))
				.andExpect(jsonPath("$.data.books[0].chapters[0].status", notNullValue()));
	}

	@Test
	void shouldReturnDailyDashboard() throws Exception {
		mockMvc.perform(get("/api/dashboard/daily?date=2026-07-06"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("SUCCESS")))
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data[0].bookName", is("Clean Code")))
				.andExpect(jsonPath("$.data[0].completedPages", is(0)))
				.andExpect(jsonPath("$.data[0].status", notNullValue()))
				.andExpect(jsonPath("$.data[0].plannedPageRangeStart", notNullValue()))
				.andExpect(jsonPath("$.data[0].plannedPageRangeEnd", notNullValue()));
	}

	@Test
	void shouldExcludeBooksNotYetStartedFromDailyDashboard() throws Exception {
		mockMvc.perform(get("/api/dashboard/daily?date=2026-06-30"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)));
	}

	@Test
	void shouldExcludeNotStartedBooksAfterTargetEndDateFromDailyDashboard() throws Exception {
		mockMvc.perform(get("/api/dashboard/daily?date=2026-07-12"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)));
	}

	@Test
	void shouldIncludeOverdueBooksInDailyDashboard() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 50 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/dashboard/daily?date=2026-07-12"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(1)))
				.andExpect(jsonPath("$.data[0].status", is("OVERDUE")));
	}

	@Test
	void shouldExcludeCompletedBooksFromDailyDashboard() throws Exception {
		mockMvc.perform(put("/api/books/Clean Code/progress")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"completedPages\": 100 }"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/dashboard/daily?date=2026-07-06"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)));
	}

	@Test
	void shouldReturnEmptyDailyDashboardWhenNoBooksExist() throws Exception {
		bookRepository.deleteAll();

		mockMvc.perform(get("/api/dashboard/daily?date=2026-07-06"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)));
	}

	@Test
	void shouldReturn400ForInvalidDateFormat() throws Exception {
		mockMvc.perform(get("/api/dashboard/daily?date=not-a-date"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	void shouldDeleteBookAndAllRelatedData() throws Exception {
		mockMvc.perform(delete("/api/books/Clean Code"))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/books/Clean Code"))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)));
	}

	@Test
	void shouldReturn404WhenDeletingNonExistentBook() throws Exception {
		mockMvc.perform(delete("/api/books/Unknown Book"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is("ERROR")))
				.andExpect(jsonPath("$.message", is("Book not found: Unknown Book")));
	}
}
