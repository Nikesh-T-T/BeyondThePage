CREATE TABLE books (
    book_name    VARCHAR(255) NOT NULL,
    total_pages  INT          NOT NULL,
    planned_days INT          NOT NULL,
    start_date   DATE         NOT NULL,
    CONSTRAINT pk_books PRIMARY KEY (book_name),
    CONSTRAINT chk_books_total_pages  CHECK (total_pages  > 0),
    CONSTRAINT chk_books_planned_days CHECK (planned_days > 0)
);

CREATE TABLE book_chapters (
    book_name      VARCHAR(255) NOT NULL,
    chapter_number INT          NOT NULL,
    chapter_title  VARCHAR(500) NOT NULL,
    start_page     INT          NOT NULL,
    end_page       INT          NOT NULL,
    CONSTRAINT pk_book_chapters PRIMARY KEY (book_name, chapter_number),
    CONSTRAINT fk_book_chapters_book FOREIGN KEY (book_name)
        REFERENCES books (book_name) ON DELETE CASCADE,
    CONSTRAINT chk_chapter_pages CHECK (start_page <= end_page)
);

CREATE TABLE reading_progress (
    book_name       VARCHAR(255) NOT NULL,
    completed_pages INT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_reading_progress PRIMARY KEY (book_name),
    CONSTRAINT fk_reading_progress_book FOREIGN KEY (book_name)
        REFERENCES books (book_name) ON DELETE CASCADE,
    CONSTRAINT chk_completed_pages CHECK (completed_pages >= 0)
);
