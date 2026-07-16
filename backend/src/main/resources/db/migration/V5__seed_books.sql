-- Seed data: 20 books with chapters and reading progress

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 'DSA', 'Wladston Ferreira Filho', 168, 20, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 1, 'Basics', 1, 20);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 2, 'Complexity', 21, 42);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 3, 'Strategy', 43, 70);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 4, 'Data', 71, 100);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 5, 'Algorithms', 101, 130);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 6, 'Databases', 131, 152);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Computer Science Distilled: Learn the Art of Solving Computational Problems', 7, 'Computers', 153, 168);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 'DSA', 'Aditya Bhargava', 246, 29, DATE '2026-08-06');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 1, 'Introduction to algorithms', 1, 22);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 2, 'Selection sort', 23, 42);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 3, 'Recursion', 43, 60);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 4, 'Quicksort', 61, 82);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 5, 'Hash tables', 83, 106);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 6, 'Breadth-first search', 107, 128);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 7, 'Dijkstra algorithm', 129, 150);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 8, 'Greedy algorithms', 151, 170);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 9, 'Dynamic programming', 171, 198);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 10, 'K-nearest neighbors', 199, 220);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People', 11, 'Where to go next', 221, 246);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Introduction to Algorithms', 'DSA', 'Thomas H. Cormen', 1141, 134, DATE '2026-09-04');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Introduction to Algorithms', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 1, 'The Role of Algorithms in Computing', 1, 14);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 2, 'Getting Started', 15, 42);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 3, 'Growth of Functions', 43, 64);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 4, 'Divide-and-Conquer', 65, 112);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 5, 'Probabilistic Analysis and Randomized Algorithms', 113, 142);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 6, 'Heapsort', 143, 164);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 7, 'Quicksort', 165, 194);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 8, 'Sorting in Linear Time', 195, 216);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 9, 'Medians and Order Statistics', 217, 234);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 10, 'Elementary Data Structures', 235, 260);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 11, 'Hash Tables', 261, 296);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 12, 'Binary Search Trees', 297, 320);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 13, 'Red-Black Trees', 321, 350);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 14, 'Augmenting Data Structures', 351, 372);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 15, 'Dynamic Programming', 373, 430);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 16, 'Greedy Algorithms', 431, 470);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 17, 'Amortized Analysis', 471, 494);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 18, 'B-Trees', 495, 516);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 19, 'Fibonacci Heaps', 517, 542);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 20, 'van Emde Boas Trees', 543, 574);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 21, 'Data Structures for Disjoint Sets', 575, 602);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 22, 'Elementary Graph Algorithms', 603, 642);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 23, 'Minimum Spanning Trees', 643, 662);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 24, 'Single-Source Shortest Paths', 663, 710);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 25, 'All-Pairs Shortest Paths', 711, 742);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 26, 'Maximum Flow', 743, 800);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 27, 'Multithreaded Algorithms', 801, 840);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 28, 'Matrix Operations', 841, 878);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 29, 'Linear Programming', 879, 940);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 30, 'Polynomials and the FFT', 941, 976);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 31, 'Number-Theoretic Algorithms', 977, 1028);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 32, 'String Matching', 1029, 1062);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 33, 'Computational Geometry', 1063, 1100);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 34, 'NP-Completeness', 1101, 1132);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Algorithms', 35, 'Approximation Algorithms', 1133, 1141);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Distributed Systems: For Fun and Profit', 'Distributed Systems', 'Mikito Takada', 128, 27, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Distributed Systems: For Fun and Profit', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Distributed Systems: For Fun and Profit', 1, 'Distributed systems at a high level', 1, 20);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Distributed Systems: For Fun and Profit', 2, 'Up and down the level of abstraction', 21, 40);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Distributed Systems: For Fun and Profit', 3, 'Time and order', 41, 70);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Distributed Systems: For Fun and Profit', 4, 'Replication: preventing divergence', 71, 100);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Distributed Systems: For Fun and Profit', 5, 'Replication: accepting divergence', 101, 128);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Understanding Distributed Systems', 'Distributed Systems', 'Roberto Vitillo', 265, 56, DATE '2026-08-13');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Understanding Distributed Systems', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 1, 'Introduction', 1, 10);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 2, 'Reliable links', 11, 24);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 3, 'Secure links', 25, 40);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 4, 'Discovery', 41, 52);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 5, 'APIs', 53, 68);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 6, 'System models', 69, 82);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 7, 'Failure detection', 83, 96);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 8, 'Time', 97, 112);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 9, 'Leader election', 113, 126);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 10, 'Replication', 127, 148);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 11, 'Coordination avoidance', 149, 166);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 12, 'Transactions', 167, 192);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 13, 'Asynchronous transactions', 193, 214);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 14, 'Caching', 215, 232);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 15, 'Microservices', 233, 250);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Understanding Distributed Systems', 16, 'Testing', 251, 265);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Designing Data-Intensive Applications', 'Distributed Systems', 'Martin Kleppmann', 469, 100, DATE '2026-10-08');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Designing Data-Intensive Applications', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 1, 'Reliable, Scalable, and Maintainable Applications', 1, 28);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 2, 'Data Models and Query Languages', 29, 66);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 3, 'Storage and Retrieval', 67, 106);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 4, 'Encoding and Evolution', 107, 136);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 5, 'Replication', 137, 180);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 6, 'Partitioning', 181, 208);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 7, 'Transactions', 209, 258);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 8, 'The Trouble with Distributed Systems', 259, 312);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 9, 'Consistency and Consensus', 313, 376);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 10, 'Batch Processing', 377, 420);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 11, 'Stream Processing', 421, 458);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Data-Intensive Applications', 12, 'The Future of Data Systems', 459, 469);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Fundamentals of Data Engineering', 'Data Engineering', 'Joe Reis', 436, 101, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Fundamentals of Data Engineering', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 1, 'Data Engineering Described', 1, 30);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 2, 'The Data Engineering Lifecycle', 31, 66);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 3, 'Designing Good Data Architecture', 67, 108);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 4, 'Choosing Technologies Across the Data Engineering Lifecycle', 109, 142);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 5, 'Data Generation in Source Systems', 143, 180);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 6, 'Storage', 181, 220);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 7, 'Ingestion', 221, 260);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 8, 'Queries, Modeling, and Transformation', 261, 320);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 9, 'Serving Data for Analytics, Machine Learning, and Reverse ETL', 321, 370);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 10, 'Security and Privacy', 371, 400);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Fundamentals of Data Engineering', 11, 'The Future of Data Engineering', 401, 436);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 'Data Engineering', 'Tyler Akidau', 360, 82, DATE '2026-10-26');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 1, 'Streaming 101', 1, 30);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 2, 'The What, Where, When, and How of Data Processing', 31, 80);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 3, 'Watermarks', 81, 130);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 4, 'Advanced Windowing', 131, 176);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 5, 'Exactly-Once and Side Effects', 177, 220);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 6, 'Streams and Tables', 221, 268);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 7, 'The Practicalities of Persistent State', 269, 310);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 8, 'Streaming SQL', 311, 340);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing', 9, 'Streaming Joins', 341, 360);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Introduction to Machine Learning', 'Machine Learning', 'Ethem Alpaydin', 485, 86, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Introduction to Machine Learning', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 1, 'Introduction', 1, 28);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 2, 'Supervised Learning', 29, 70);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 3, 'Bayesian Decision Theory', 71, 104);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 4, 'Parametric Methods', 105, 140);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 5, 'Multivariate Methods', 141, 178);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 6, 'Dimensionality Reduction', 179, 212);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 7, 'Clustering', 213, 248);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 8, 'Nonparametric Methods', 249, 284);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 9, 'Decision Trees', 285, 316);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 10, 'Linear Discrimination', 317, 350);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 11, 'Multilayer Perceptrons', 351, 392);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 12, 'Local Models', 393, 424);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 13, 'Kernel Machines', 425, 456);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Introduction to Machine Learning', 14, 'Graphical Models', 457, 485);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('The Hundred-Page Machine Learning Book', 'Machine Learning', 'Andriy Burkov', 160, 29, DATE '2026-10-11');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('The Hundred-Page Machine Learning Book', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 1, 'Introduction', 1, 10);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 2, 'Notation and Definitions', 11, 24);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 3, 'Fundamental Algorithms', 25, 48);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 4, 'Anatomy of a Learning Algorithm', 49, 62);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 5, 'Basic Practice', 63, 86);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 6, 'Neural Networks and Deep Learning', 87, 108);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 8, 'Unsupervised Learning', 109, 124);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 9, 'Advanced Practice', 125, 144);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Hundred-Page Machine Learning Book', 10, 'Conclusion', 145, 160);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 'Machine Learning', 'Peter Flach', 383, 68, DATE '2026-11-09');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 0, 'Prologue: A machine learning sampler', 1, 12);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 1, 'The ingredients of machine learning', 13, 48);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 2, 'Binary classification and related tasks', 49, 80);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 3, 'Beyond binary classification', 81, 103);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 4, 'Concept learning', 104, 128);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 5, 'Tree models', 129, 156);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 6, 'Rule models', 157, 193);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 7, 'Linear models', 194, 230);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 8, 'Distance-based models', 231, 261);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 9, 'Probabilistic models', 262, 297);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 10, 'Features', 298, 329);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 11, 'Model ensembles', 330, 342);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 12, 'Machine learning experiments', 343, 359);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 13, 'Epilogue: Where to go from here', 360, 362);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 14, 'Important points to remember', 363, 366);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 15, 'References', 367, 382);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Machine Learning: The Art and Science of Algorithms that Make Sense of Data', 16, 'Index', 383, 383);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 'DevOps', 'Gene Kim', 432, 52, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 1, 'Tuesday, September 3', 1, 20);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 2, 'Tuesday, September 3 (continued)', 21, 44);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 3, 'Wednesday, September 4', 45, 72);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 4, 'Thursday, September 5', 73, 100);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 5, 'Friday, September 6', 101, 132);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 6, 'Monday, September 9', 133, 168);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 7, 'Tuesday, September 10', 169, 210);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 8, 'Wednesday, September 18', 211, 258);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 9, 'Thursday, October 10', 259, 320);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 10, 'Friday, November 8', 321, 380);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 11, 'Epilogue', 381, 432);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 'DevOps', 'Nicole Forsgren', 240, 29, DATE '2026-09-07');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 0, 'Introduction', 1, 14);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 1, 'The Research', 15, 50);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 2, 'The Practices', 51, 96);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 3, 'The Metrics', 97, 140);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 4, 'Transformational Leadership', 141, 180);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 5, 'The Science', 181, 220);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Lean DevOps: Building and Scaling High Performing Technology Organizations', 6, 'Conclusion', 221, 240);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Docker Deep Dive', 'DevOps', 'Nigel Poulton', 200, 24, DATE '2026-10-06');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Docker Deep Dive', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 1, 'Containers from 30,000 feet', 1, 18);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 2, 'Docker', 19, 34);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 3, 'Installing Docker', 35, 50);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 4, 'The big picture', 51, 68);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 5, 'The Docker Engine', 69, 84);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 6, 'Images', 85, 106);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 7, 'Containers', 107, 126);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 8, 'Containerizing an app', 127, 148);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 9, 'Deploying apps with Docker Compose', 149, 168);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 10, 'Docker Swarm', 169, 184);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Docker Deep Dive', 11, 'Docker networking', 185, 200);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 'DevOps', 'Chip Huyen', 644, 78, DATE '2026-10-30');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 1, 'Overview of Machine Learning Systems', 1, 36);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 2, 'Introduction to Machine Learning Systems Design', 37, 80);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 3, 'Data Engineering Fundamentals', 81, 130);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 4, 'Training Data', 131, 188);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 5, 'Feature Engineering', 189, 244);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 6, 'Model Development and Offline Evaluation', 245, 312);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 7, 'Model Deployment and Prediction Service', 313, 368);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 8, 'Data Distribution Shifts and Monitoring', 369, 428);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 9, 'Continual Learning and Test in Production', 429, 492);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 10, 'Infrastructure and Tooling for MLOps', 493, 564);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications', 11, 'The Human Side of Machine Learning', 565, 644);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 'System Design', 'Alex Xu', 298, 80, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('System Design Interview: An Insider Guide Volume 1', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 1, 'Scale From Zero To Millions Of Users', 1, 28);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 2, 'Back-of-the-envelope Estimation', 29, 38);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 3, 'A Framework For System Design Interviews', 39, 50);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 4, 'Design A Rate Limiter', 51, 72);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 5, 'Design Consistent Hashing', 73, 90);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 6, 'Design A Key-Value Store', 91, 118);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 7, 'Design A Unique Id Generator In Distributed Systems', 119, 134);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 8, 'Design A Url Shortener', 135, 150);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 9, 'Design A Web Crawler', 151, 170);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 10, 'Design A Notification System', 171, 194);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 11, 'Design A News Feed System', 195, 212);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 12, 'Design A Chat System', 213, 238);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 13, 'Design A Search Autocomplete System', 239, 260);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 14, 'Design Youtube', 261, 282);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 1', 15, 'Design Google Drive', 283, 298);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 'System Design', 'Alex Xu', 387, 103, DATE '2026-10-05');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('System Design Interview: An Insider Guide Volume 2', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 1, 'Proximity Service', 1, 30);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 2, 'Nearby Friends', 31, 56);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 3, 'Google Maps', 57, 92);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 4, 'Distributed Message Queue', 93, 130);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 5, 'Metrics Monitoring and Alerting System', 131, 162);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 6, 'Ad Click Event Aggregation', 163, 196);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 7, 'Hotel Reservation System', 197, 228);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 8, 'Distributed Email Service', 229, 264);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 9, 'S3-like Object Storage', 265, 300);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 10, 'Real-time Gaming Leaderboard', 301, 330);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 11, 'Payment System', 331, 360);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('System Design Interview: An Insider Guide Volume 2', 12, 'Digital Wallet', 361, 387);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 'Low Level Design', 'Eric Freeman', 694, 95, DATE '2026-07-17');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 1, 'Intro to Design Patterns: Welcome to Design Patterns', 1, 52);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 2, 'The Observer Pattern: Keeping your Objects in the Know', 53, 100);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 3, 'The Decorator Pattern: Decorating Objects', 101, 144);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 4, 'The Factory Pattern: Baking with OO Goodness', 145, 196);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 5, 'The Singleton Pattern: One of a Kind Objects', 197, 226);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 6, 'The Command Pattern: Encapsulating Invocation', 227, 278);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 7, 'Being Adaptive: The Adapter and Facade Patterns', 279, 326);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 8, 'The Template Method Pattern: Encapsulating Algorithms', 327, 362);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 9, 'The Iterator and Composite Patterns: Well-Managed Collections', 363, 418);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 10, 'The State Pattern: The State of Things', 419, 458);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 11, 'The Proxy Pattern: Controlling Object Access', 459, 520);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 12, 'Compound Patterns: Patterns of Patterns', 521, 594);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 13, 'Patterns in the Real World: Better Living with Patterns', 595, 638);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software', 14, 'Appendix: Leftover Patterns', 639, 694);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('The Object-Oriented Thought Process', 'Low Level Design', 'Matt Weisfeld', 336, 46, DATE '2026-10-20');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('The Object-Oriented Thought Process', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 0, 'Introduction', 1, 12);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 1, 'Introduction to Object-Oriented Concepts', 13, 50);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 2, 'How to Think in Terms of Objects', 51, 80);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 3, 'Advanced Object-Oriented Concepts', 81, 114);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 4, 'The Anatomy of a Class', 115, 148);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 5, 'Class Design Guidelines', 149, 178);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 6, 'Designing with Objects', 179, 212);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 7, 'Mastering Inheritance and Composition', 213, 248);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 8, 'Frameworks and Reuse: Designing with Interfaces and Abstract Classes', 249, 278);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 9, 'Building Objects and Object-Oriented Design', 279, 306);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 10, 'Creating Object Models', 307, 322);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('The Object-Oriented Thought Process', 11, 'Objects and Portable Data: XML and JSON', 323, 336);

INSERT INTO books (book_name, category, author, total_pages, planned_days, start_date)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 'Low Level Design', 'Erich Gamma', 292, 40, DATE '2026-12-05');
INSERT INTO reading_progress (book_name, completed_pages) VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 0);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 1, 'Introduction', 1, 30);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 2, 'A Case Study: Designing a Document Editor', 31, 68);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 3, 'Creational Patterns', 69, 126);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 4, 'Structural Patterns', 127, 200);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 5, 'Behavioral Patterns', 201, 278);
INSERT INTO book_chapters (book_name, chapter_number, chapter_title, start_page, end_page)
    VALUES ('Design Patterns: Elements of Reusable Object-Oriented Software', 6, 'Conclusion', 279, 292);
