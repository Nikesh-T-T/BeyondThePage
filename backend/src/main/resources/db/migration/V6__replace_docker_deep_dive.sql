-- Replace Docker Deep Dive with Docker In Action

DELETE FROM book_chapters WHERE book_name = 'Docker Deep Dive';
DELETE FROM reading_progress WHERE book_name = 'Docker Deep Dive';
DELETE FROM books WHERE book_name = 'Docker Deep Dive';

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Docker In Action', 'DevOps', 'Jeff Nickoloff, Stephen Kuenzli', 301, 30, DATE '2026-10-06');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Docker In Action', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 1, 'Welcome to Docker', 1, 18);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 2, 'Running software in containers', 19, 46);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 3, 'Software installation simplified', 47, 61);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 4, 'Working with storage and volumes', 62, 79);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 5, 'Single-host networking', 80, 98);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 6, 'Limiting risk with resource controls', 99, 122);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 7, 'Packaging software in images', 125, 143);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 8, 'Building images automatically with Dockerfiles', 144, 173);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 9, 'Public and private software distribution', 174, 196);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 10, 'Image pipelines', 197, 216);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 11, 'Services with Docker and Compose', 219, 243);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 12, 'First-class configuration abstractions', 244, 263);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker In Action', 13, 'Orchestrating services on a cluster of Docker hosts with Swarm', 264, 300);
