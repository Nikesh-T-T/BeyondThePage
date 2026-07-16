# Book Setup — curl Commands

All 20 books across 7 categories, start date 2026-07-17.

---

## DSA

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Computer Science Distilled: Learn the Art of Solving Computational Problems",
    "category": "DSA",
    "author": "Wladston Ferreira Filho",
    "totalPages": 168,
    "plannedDays": 20,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Basics", "startPage": 1, "endPage": 20 },
      { "chapterNumber": 2, "chapterTitle": "Complexity", "startPage": 21, "endPage": 42 },
      { "chapterNumber": 3, "chapterTitle": "Strategy", "startPage": 43, "endPage": 70 },
      { "chapterNumber": 4, "chapterTitle": "Data", "startPage": 71, "endPage": 100 },
      { "chapterNumber": 5, "chapterTitle": "Algorithms", "startPage": 101, "endPage": 130 },
      { "chapterNumber": 6, "chapterTitle": "Databases", "startPage": 131, "endPage": 152 },
      { "chapterNumber": 7, "chapterTitle": "Computers", "startPage": 153, "endPage": 168 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Grokking Algorithms: An Illustrated Guide for Programmers and Other Curious People",
    "category": "DSA",
    "author": "Aditya Bhargava",
    "totalPages": 246,
    "plannedDays": 29,
    "startDate": "2026-08-06",
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Introduction to algorithms", "startPage": 1, "endPage": 22 },
      { "chapterNumber": 2, "chapterTitle": "Selection sort", "startPage": 23, "endPage": 42 },
      { "chapterNumber": 3, "chapterTitle": "Recursion", "startPage": 43, "endPage": 60 },
      { "chapterNumber": 4, "chapterTitle": "Quicksort", "startPage": 61, "endPage": 82 },
      { "chapterNumber": 5, "chapterTitle": "Hash tables", "startPage": 83, "endPage": 106 },
      { "chapterNumber": 6, "chapterTitle": "Breadth-first search", "startPage": 107, "endPage": 128 },
      { "chapterNumber": 7, "chapterTitle": "Dijkstra algorithm", "startPage": 129, "endPage": 150 },
      { "chapterNumber": 8, "chapterTitle": "Greedy algorithms", "startPage": 151, "endPage": 170 },
      { "chapterNumber": 9, "chapterTitle": "Dynamic programming", "startPage": 171, "endPage": 198 },
      { "chapterNumber": 10, "chapterTitle": "K-nearest neighbors", "startPage": 199, "endPage": 220 },
      { "chapterNumber": 11, "chapterTitle": "Where to go next", "startPage": 221, "endPage": 246 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Introduction to Algorithms",
    "category": "DSA",
    "author": "Thomas H. Cormen",
    "totalPages": 1141,
    "plannedDays": 134,
    "startDate": "2026-09-04",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "The Role of Algorithms in Computing", "startPage": 1,    "endPage": 14 },
      { "chapterNumber": 2,  "chapterTitle": "Getting Started", "startPage": 15,   "endPage": 42 },
      { "chapterNumber": 3,  "chapterTitle": "Growth of Functions", "startPage": 43,   "endPage": 64 },
      { "chapterNumber": 4,  "chapterTitle": "Divide-and-Conquer", "startPage": 65,   "endPage": 112 },
      { "chapterNumber": 5,  "chapterTitle": "Probabilistic Analysis and Randomized Algorithms", "startPage": 113,  "endPage": 142 },
      { "chapterNumber": 6,  "chapterTitle": "Heapsort", "startPage": 143,  "endPage": 164 },
      { "chapterNumber": 7,  "chapterTitle": "Quicksort", "startPage": 165,  "endPage": 194 },
      { "chapterNumber": 8,  "chapterTitle": "Sorting in Linear Time", "startPage": 195,  "endPage": 216 },
      { "chapterNumber": 9,  "chapterTitle": "Medians and Order Statistics", "startPage": 217,  "endPage": 234 },
      { "chapterNumber": 10, "chapterTitle": "Elementary Data Structures", "startPage": 235,  "endPage": 260 },
      { "chapterNumber": 11, "chapterTitle": "Hash Tables", "startPage": 261,  "endPage": 296 },
      { "chapterNumber": 12, "chapterTitle": "Binary Search Trees", "startPage": 297,  "endPage": 320 },
      { "chapterNumber": 13, "chapterTitle": "Red-Black Trees", "startPage": 321,  "endPage": 350 },
      { "chapterNumber": 14, "chapterTitle": "Augmenting Data Structures", "startPage": 351,  "endPage": 372 },
      { "chapterNumber": 15, "chapterTitle": "Dynamic Programming", "startPage": 373,  "endPage": 430 },
      { "chapterNumber": 16, "chapterTitle": "Greedy Algorithms", "startPage": 431,  "endPage": 470 },
      { "chapterNumber": 17, "chapterTitle": "Amortized Analysis", "startPage": 471,  "endPage": 494 },
      { "chapterNumber": 18, "chapterTitle": "B-Trees", "startPage": 495,  "endPage": 516 },
      { "chapterNumber": 19, "chapterTitle": "Fibonacci Heaps", "startPage": 517,  "endPage": 542 },
      { "chapterNumber": 20, "chapterTitle": "van Emde Boas Trees", "startPage": 543,  "endPage": 574 },
      { "chapterNumber": 21, "chapterTitle": "Data Structures for Disjoint Sets", "startPage": 575,  "endPage": 602 },
      { "chapterNumber": 22, "chapterTitle": "Elementary Graph Algorithms", "startPage": 603,  "endPage": 642 },
      { "chapterNumber": 23, "chapterTitle": "Minimum Spanning Trees", "startPage": 643,  "endPage": 662 },
      { "chapterNumber": 24, "chapterTitle": "Single-Source Shortest Paths", "startPage": 663,  "endPage": 710 },
      { "chapterNumber": 25, "chapterTitle": "All-Pairs Shortest Paths", "startPage": 711,  "endPage": 742 },
      { "chapterNumber": 26, "chapterTitle": "Maximum Flow", "startPage": 743,  "endPage": 800 },
      { "chapterNumber": 27, "chapterTitle": "Multithreaded Algorithms", "startPage": 801,  "endPage": 840 },
      { "chapterNumber": 28, "chapterTitle": "Matrix Operations", "startPage": 841,  "endPage": 878 },
      { "chapterNumber": 29, "chapterTitle": "Linear Programming", "startPage": 879,  "endPage": 940 },
      { "chapterNumber": 30, "chapterTitle": "Polynomials and the FFT", "startPage": 941,  "endPage": 976 },
      { "chapterNumber": 31, "chapterTitle": "Number-Theoretic Algorithms", "startPage": 977,  "endPage": 1028 },
      { "chapterNumber": 32, "chapterTitle": "String Matching", "startPage": 1029, "endPage": 1062 },
      { "chapterNumber": 33, "chapterTitle": "Computational Geometry", "startPage": 1063, "endPage": 1100 },
      { "chapterNumber": 34, "chapterTitle": "NP-Completeness", "startPage": 1101, "endPage": 1132 },
      { "chapterNumber": 35, "chapterTitle": "Approximation Algorithms", "startPage": 1133, "endPage": 1141 }
    ]
  }'
```

---

## Distributed Systems

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Distributed Systems: For Fun and Profit",
    "category": "Distributed Systems",
    "author": "Mikito Takada",
    "totalPages": 128,
    "plannedDays": 27,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Distributed systems at a high level", "startPage": 1,   "endPage": 20 },
      { "chapterNumber": 2, "chapterTitle": "Up and down the level of abstraction", "startPage": 21,  "endPage": 40 },
      { "chapterNumber": 3, "chapterTitle": "Time and order", "startPage": 41,  "endPage": 70 },
      { "chapterNumber": 4, "chapterTitle": "Replication: preventing divergence", "startPage": 71,  "endPage": 100 },
      { "chapterNumber": 5, "chapterTitle": "Replication: accepting divergence", "startPage": 101, "endPage": 128 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Understanding Distributed Systems",
    "category": "Distributed Systems",
    "author": "Roberto Vitillo",
    "totalPages": 265,
    "plannedDays": 56,
    "startDate": "2026-08-13",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Introduction", "startPage": 1,   "endPage": 10 },
      { "chapterNumber": 2,  "chapterTitle": "Reliable links", "startPage": 11,  "endPage": 24 },
      { "chapterNumber": 3,  "chapterTitle": "Secure links", "startPage": 25,  "endPage": 40 },
      { "chapterNumber": 4,  "chapterTitle": "Discovery", "startPage": 41,  "endPage": 52 },
      { "chapterNumber": 5,  "chapterTitle": "APIs", "startPage": 53,  "endPage": 68 },
      { "chapterNumber": 6,  "chapterTitle": "System models", "startPage": 69,  "endPage": 82 },
      { "chapterNumber": 7,  "chapterTitle": "Failure detection", "startPage": 83,  "endPage": 96 },
      { "chapterNumber": 8,  "chapterTitle": "Time", "startPage": 97,  "endPage": 112 },
      { "chapterNumber": 9,  "chapterTitle": "Leader election", "startPage": 113, "endPage": 126 },
      { "chapterNumber": 10, "chapterTitle": "Replication", "startPage": 127, "endPage": 148 },
      { "chapterNumber": 11, "chapterTitle": "Coordination avoidance", "startPage": 149, "endPage": 166 },
      { "chapterNumber": 12, "chapterTitle": "Transactions", "startPage": 167, "endPage": 192 },
      { "chapterNumber": 13, "chapterTitle": "Asynchronous transactions", "startPage": 193, "endPage": 214 },
      { "chapterNumber": 14, "chapterTitle": "Caching", "startPage": 215, "endPage": 232 },
      { "chapterNumber": 15, "chapterTitle": "Microservices", "startPage": 233, "endPage": 250 },
      { "chapterNumber": 16, "chapterTitle": "Testing", "startPage": 251, "endPage": 265 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Designing Data-Intensive Applications",
    "category": "Distributed Systems",
    "author": "Martin Kleppmann",
    "totalPages": 469,
    "plannedDays": 100,
    "startDate": "2026-10-08",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Reliable, Scalable, and Maintainable Applications", "startPage": 1,   "endPage": 28 },
      { "chapterNumber": 2,  "chapterTitle": "Data Models and Query Languages", "startPage": 29,  "endPage": 66 },
      { "chapterNumber": 3,  "chapterTitle": "Storage and Retrieval", "startPage": 67,  "endPage": 106 },
      { "chapterNumber": 4,  "chapterTitle": "Encoding and Evolution", "startPage": 107, "endPage": 136 },
      { "chapterNumber": 5,  "chapterTitle": "Replication", "startPage": 137, "endPage": 180 },
      { "chapterNumber": 6,  "chapterTitle": "Partitioning", "startPage": 181, "endPage": 208 },
      { "chapterNumber": 7,  "chapterTitle": "Transactions", "startPage": 209, "endPage": 258 },
      { "chapterNumber": 8,  "chapterTitle": "The Trouble with Distributed Systems", "startPage": 259, "endPage": 312 },
      { "chapterNumber": 9,  "chapterTitle": "Consistency and Consensus", "startPage": 313, "endPage": 376 },
      { "chapterNumber": 10, "chapterTitle": "Batch Processing", "startPage": 377, "endPage": 420 },
      { "chapterNumber": 11, "chapterTitle": "Stream Processing", "startPage": 421, "endPage": 458 },
      { "chapterNumber": 12, "chapterTitle": "The Future of Data Systems", "startPage": 459, "endPage": 469 }
    ]
  }'
```

---

## Data Engineering

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Fundamentals of Data Engineering",
    "category": "Data Engineering",
    "author": "Joe Reis",
    "totalPages": 436,
    "plannedDays": 101,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Data Engineering Described", "startPage": 1,   "endPage": 30 },
      { "chapterNumber": 2,  "chapterTitle": "The Data Engineering Lifecycle", "startPage": 31,  "endPage": 66 },
      { "chapterNumber": 3,  "chapterTitle": "Designing Good Data Architecture", "startPage": 67,  "endPage": 108 },
      { "chapterNumber": 4,  "chapterTitle": "Choosing Technologies Across the Data Engineering Lifecycle", "startPage": 109, "endPage": 142 },
      { "chapterNumber": 5,  "chapterTitle": "Data Generation in Source Systems", "startPage": 143, "endPage": 180 },
      { "chapterNumber": 6,  "chapterTitle": "Storage", "startPage": 181, "endPage": 220 },
      { "chapterNumber": 7,  "chapterTitle": "Ingestion", "startPage": 221, "endPage": 260 },
      { "chapterNumber": 8,  "chapterTitle": "Queries, Modeling, and Transformation", "startPage": 261, "endPage": 320 },
      { "chapterNumber": 9,  "chapterTitle": "Serving Data for Analytics, Machine Learning, and Reverse ETL", "startPage": 321, "endPage": 370 },
      { "chapterNumber": 10, "chapterTitle": "Security and Privacy", "startPage": 371, "endPage": 400 },
      { "chapterNumber": 11, "chapterTitle": "The Future of Data Engineering", "startPage": 401, "endPage": 436 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Streaming Systems: The What, Where, When, and How of Large-Scale Data Processing",
    "category": "Data Engineering",
    "author": "Tyler Akidau",
    "totalPages": 360,
    "plannedDays": 82,
    "startDate": "2026-10-26",
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Streaming 101", "startPage": 1,   "endPage": 30 },
      { "chapterNumber": 2, "chapterTitle": "The What, Where, When, and How of Data Processing", "startPage": 31,  "endPage": 80 },
      { "chapterNumber": 3, "chapterTitle": "Watermarks", "startPage": 81,  "endPage": 130 },
      { "chapterNumber": 4, "chapterTitle": "Advanced Windowing", "startPage": 131, "endPage": 176 },
      { "chapterNumber": 5, "chapterTitle": "Exactly-Once and Side Effects", "startPage": 177, "endPage": 220 },
      { "chapterNumber": 6, "chapterTitle": "Streams and Tables", "startPage": 221, "endPage": 268 },
      { "chapterNumber": 7, "chapterTitle": "The Practicalities of Persistent State", "startPage": 269, "endPage": 310 },
      { "chapterNumber": 8, "chapterTitle": "Streaming SQL", "startPage": 311, "endPage": 340 },
      { "chapterNumber": 9, "chapterTitle": "Streaming Joins", "startPage": 341, "endPage": 360 }
    ]
  }'
```

---

## Machine Learning

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Introduction to Machine Learning",
    "category": "Machine Learning",
    "author": "Ethem Alpaydin",
    "totalPages": 485,
    "plannedDays": 86,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Introduction", "startPage": 1,   "endPage": 28 },
      { "chapterNumber": 2,  "chapterTitle": "Supervised Learning", "startPage": 29,  "endPage": 70 },
      { "chapterNumber": 3,  "chapterTitle": "Bayesian Decision Theory", "startPage": 71,  "endPage": 104 },
      { "chapterNumber": 4,  "chapterTitle": "Parametric Methods", "startPage": 105, "endPage": 140 },
      { "chapterNumber": 5,  "chapterTitle": "Multivariate Methods", "startPage": 141, "endPage": 178 },
      { "chapterNumber": 6,  "chapterTitle": "Dimensionality Reduction", "startPage": 179, "endPage": 212 },
      { "chapterNumber": 7,  "chapterTitle": "Clustering", "startPage": 213, "endPage": 248 },
      { "chapterNumber": 8,  "chapterTitle": "Nonparametric Methods", "startPage": 249, "endPage": 284 },
      { "chapterNumber": 9,  "chapterTitle": "Decision Trees", "startPage": 285, "endPage": 316 },
      { "chapterNumber": 10, "chapterTitle": "Linear Discrimination", "startPage": 317, "endPage": 350 },
      { "chapterNumber": 11, "chapterTitle": "Multilayer Perceptrons", "startPage": 351, "endPage": 392 },
      { "chapterNumber": 12, "chapterTitle": "Local Models", "startPage": 393, "endPage": 424 },
      { "chapterNumber": 13, "chapterTitle": "Kernel Machines", "startPage": 425, "endPage": 456 },
      { "chapterNumber": 14, "chapterTitle": "Graphical Models", "startPage": 457, "endPage": 485 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "The Hundred-Page Machine Learning Book",
    "category": "Machine Learning",
    "author": "Andriy Burkov",
    "totalPages": 160,
    "plannedDays": 29,
    "startDate": "2026-10-11",
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Introduction", "startPage": 1,   "endPage": 10 },
      { "chapterNumber": 2, "chapterTitle": "Notation and Definitions", "startPage": 11,  "endPage": 24 },
      { "chapterNumber": 3, "chapterTitle": "Fundamental Algorithms", "startPage": 25,  "endPage": 48 },
      { "chapterNumber": 4, "chapterTitle": "Anatomy of a Learning Algorithm", "startPage": 49,  "endPage": 62 },
      { "chapterNumber": 5, "chapterTitle": "Basic Practice", "startPage": 63,  "endPage": 86 },
      { "chapterNumber": 6, "chapterTitle": "Neural Networks and Deep Learning", "startPage": 87,  "endPage": 108 },
      { "chapterNumber": 8, "chapterTitle": "Unsupervised Learning", "startPage": 109, "endPage": 124 },
      { "chapterNumber": 9, "chapterTitle": "Advanced Practice", "startPage": 125, "endPage": 144 },
      { "chapterNumber": 10, "chapterTitle": "Conclusion", "startPage": 145, "endPage": 160 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Machine Learning: The Art and Science of Algorithms that Make Sense of Data",
    "category": "Machine Learning",
    "author": "Peter Flach",
    "totalPages": 383,
    "plannedDays": 68,
    "startDate": "2026-11-09",
    "chapters": [
      { "chapterNumber": 0,  "chapterTitle": "Prologue: A machine learning sampler", "startPage": 1,   "endPage": 12 },
      { "chapterNumber": 1,  "chapterTitle": "The ingredients of machine learning", "startPage": 13,  "endPage": 48 },
      { "chapterNumber": 2,  "chapterTitle": "Binary classification and related tasks", "startPage": 49,  "endPage": 80 },
      { "chapterNumber": 3,  "chapterTitle": "Beyond binary classification", "startPage": 81,  "endPage": 103 },
      { "chapterNumber": 4,  "chapterTitle": "Concept learning", "startPage": 104, "endPage": 128 },
      { "chapterNumber": 5,  "chapterTitle": "Tree models", "startPage": 129, "endPage": 156 },
      { "chapterNumber": 6,  "chapterTitle": "Rule models", "startPage": 157, "endPage": 193 },
      { "chapterNumber": 7,  "chapterTitle": "Linear models", "startPage": 194, "endPage": 230 },
      { "chapterNumber": 8,  "chapterTitle": "Distance-based models", "startPage": 231, "endPage": 261 },
      { "chapterNumber": 9,  "chapterTitle": "Probabilistic models", "startPage": 262, "endPage": 297 },
      { "chapterNumber": 10, "chapterTitle": "Features", "startPage": 298, "endPage": 329 },
      { "chapterNumber": 11, "chapterTitle": "Model ensembles", "startPage": 330, "endPage": 342 },
      { "chapterNumber": 12, "chapterTitle": "Machine learning experiments", "startPage": 343, "endPage": 359 },
      { "chapterNumber": 13, "chapterTitle": "Epilogue: Where to go from here", "startPage": 360, "endPage": 362 },
      { "chapterNumber": 14, "chapterTitle": "Important points to remember", "startPage": 363, "endPage": 366 },
      { "chapterNumber": 15, "chapterTitle": "References", "startPage": 367, "endPage": 382 },
      { "chapterNumber": 16, "chapterTitle": "Index", "startPage": 383, "endPage": 383 }
    ]
  }'
```

---

## DevOps / MLOps

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win",
    "category": "DevOps",
    "author": "Gene Kim",
    "totalPages": 432,
    "plannedDays": 52,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Tuesday, September 3", "startPage": 1,   "endPage": 20 },
      { "chapterNumber": 2,  "chapterTitle": "Tuesday, September 3 (continued)", "startPage": 21,  "endPage": 44 },
      { "chapterNumber": 3,  "chapterTitle": "Wednesday, September 4", "startPage": 45,  "endPage": 72 },
      { "chapterNumber": 4,  "chapterTitle": "Thursday, September 5", "startPage": 73,  "endPage": 100 },
      { "chapterNumber": 5,  "chapterTitle": "Friday, September 6", "startPage": 101, "endPage": 132 },
      { "chapterNumber": 6,  "chapterTitle": "Monday, September 9", "startPage": 133, "endPage": 168 },
      { "chapterNumber": 7,  "chapterTitle": "Tuesday, September 10", "startPage": 169, "endPage": 210 },
      { "chapterNumber": 8,  "chapterTitle": "Wednesday, September 18", "startPage": 211, "endPage": 258 },
      { "chapterNumber": 9,  "chapterTitle": "Thursday, October 10", "startPage": 259, "endPage": 320 },
      { "chapterNumber": 10, "chapterTitle": "Friday, November 8", "startPage": 321, "endPage": 380 },
      { "chapterNumber": 11, "chapterTitle": "Epilogue", "startPage": 381, "endPage": 432 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Lean DevOps: Building and Scaling High Performing Technology Organizations",
    "category": "DevOps",
    "author": "Nicole Forsgren",
    "totalPages": 240,
    "plannedDays": 29,
    "startDate": "2026-09-07",
    "chapters": [
      { "chapterNumber": 0, "chapterTitle": "Introduction", "startPage": 1,   "endPage": 14 },
      { "chapterNumber": 1, "chapterTitle": "The Research", "startPage": 15,  "endPage": 50 },
      { "chapterNumber": 2, "chapterTitle": "The Practices", "startPage": 51,  "endPage": 96 },
      { "chapterNumber": 3, "chapterTitle": "The Metrics", "startPage": 97,  "endPage": 140 },
      { "chapterNumber": 4, "chapterTitle": "Transformational Leadership", "startPage": 141, "endPage": 180 },
      { "chapterNumber": 5, "chapterTitle": "The Science", "startPage": 181, "endPage": 220 },
      { "chapterNumber": 6, "chapterTitle": "Conclusion", "startPage": 221, "endPage": 240 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Docker In Action",
    "category": "DevOps",
    "author": "Jeff Nickoloff, Stephen Kuenzli",
    "totalPages": 301,
    "plannedDays": 30,
    "startDate": "2026-10-06",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Welcome to Docker", "startPage": 1,   "endPage": 18 },
      { "chapterNumber": 2,  "chapterTitle": "Running software in containers", "startPage": 19,  "endPage": 46 },
      { "chapterNumber": 3,  "chapterTitle": "Software installation simplified", "startPage": 47,  "endPage": 61 },
      { "chapterNumber": 4,  "chapterTitle": "Working with storage and volumes", "startPage": 62,  "endPage": 79 },
      { "chapterNumber": 5,  "chapterTitle": "Single-host networking", "startPage": 80,  "endPage": 98 },
      { "chapterNumber": 6,  "chapterTitle": "Limiting risk with resource controls", "startPage": 99,  "endPage": 122 },
      { "chapterNumber": 7,  "chapterTitle": "Packaging software in images", "startPage": 125, "endPage": 143 },
      { "chapterNumber": 8,  "chapterTitle": "Building images automatically with Dockerfiles", "startPage": 144, "endPage": 173 },
      { "chapterNumber": 9,  "chapterTitle": "Public and private software distribution", "startPage": 174, "endPage": 196 },
      { "chapterNumber": 10, "chapterTitle": "Image pipelines", "startPage": 197, "endPage": 216 },
      { "chapterNumber": 11, "chapterTitle": "Services with Docker and Compose", "startPage": 219, "endPage": 243 },
      { "chapterNumber": 12, "chapterTitle": "First-class configuration abstractions", "startPage": 244, "endPage": 263 },
      { "chapterNumber": 13, "chapterTitle": "Orchestrating services on a cluster of Docker hosts with Swarm", "startPage": 264, "endPage": 300 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Designing Machine Learning Systems: An Iterative Process for Production-Ready Applications",
    "category": "DevOps",
    "author": "Chip Huyen",
    "totalPages": 644,
    "plannedDays": 78,
    "startDate": "2026-10-30",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Overview of Machine Learning Systems", "startPage": 1,   "endPage": 36 },
      { "chapterNumber": 2,  "chapterTitle": "Introduction to Machine Learning Systems Design", "startPage": 37,  "endPage": 80 },
      { "chapterNumber": 3,  "chapterTitle": "Data Engineering Fundamentals", "startPage": 81,  "endPage": 130 },
      { "chapterNumber": 4,  "chapterTitle": "Training Data", "startPage": 131, "endPage": 188 },
      { "chapterNumber": 5,  "chapterTitle": "Feature Engineering", "startPage": 189, "endPage": 244 },
      { "chapterNumber": 6,  "chapterTitle": "Model Development and Offline Evaluation", "startPage": 245, "endPage": 312 },
      { "chapterNumber": 7,  "chapterTitle": "Model Deployment and Prediction Service", "startPage": 313, "endPage": 368 },
      { "chapterNumber": 8,  "chapterTitle": "Data Distribution Shifts and Monitoring", "startPage": 369, "endPage": 428 },
      { "chapterNumber": 9,  "chapterTitle": "Continual Learning and Test in Production", "startPage": 429, "endPage": 492 },
      { "chapterNumber": 10, "chapterTitle": "Infrastructure and Tooling for MLOps", "startPage": 493, "endPage": 564 },
      { "chapterNumber": 11, "chapterTitle": "The Human Side of Machine Learning", "startPage": 565, "endPage": 644 }
    ]
  }'
```

---

## System Design

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "System Design Interview: An Insider Guide Volume 1",
    "category": "System Design",
    "author": "Alex Xu",
    "totalPages": 298,
    "plannedDays": 80,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Scale From Zero To Millions Of Users", "startPage": 1,   "endPage": 28 },
      { "chapterNumber": 2,  "chapterTitle": "Back-of-the-envelope Estimation", "startPage": 29,  "endPage": 38 },
      { "chapterNumber": 3,  "chapterTitle": "A Framework For System Design Interviews", "startPage": 39,  "endPage": 50 },
      { "chapterNumber": 4,  "chapterTitle": "Design A Rate Limiter", "startPage": 51,  "endPage": 72 },
      { "chapterNumber": 5,  "chapterTitle": "Design Consistent Hashing", "startPage": 73,  "endPage": 90 },
      { "chapterNumber": 6,  "chapterTitle": "Design A Key-Value Store", "startPage": 91,  "endPage": 118 },
      { "chapterNumber": 7,  "chapterTitle": "Design A Unique Id Generator In Distributed Systems", "startPage": 119, "endPage": 134 },
      { "chapterNumber": 8,  "chapterTitle": "Design A Url Shortener", "startPage": 135, "endPage": 150 },
      { "chapterNumber": 9,  "chapterTitle": "Design A Web Crawler", "startPage": 151, "endPage": 170 },
      { "chapterNumber": 10, "chapterTitle": "Design A Notification System", "startPage": 171, "endPage": 194 },
      { "chapterNumber": 11, "chapterTitle": "Design A News Feed System", "startPage": 195, "endPage": 212 },
      { "chapterNumber": 12, "chapterTitle": "Design A Chat System", "startPage": 213, "endPage": 238 },
      { "chapterNumber": 13, "chapterTitle": "Design A Search Autocomplete System", "startPage": 239, "endPage": 260 },
      { "chapterNumber": 14, "chapterTitle": "Design Youtube", "startPage": 261, "endPage": 282 },
      { "chapterNumber": 15, "chapterTitle": "Design Google Drive", "startPage": 283, "endPage": 298 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "System Design Interview: An Insider Guide Volume 2",
    "category": "System Design",
    "author": "Alex Xu",
    "totalPages": 387,
    "plannedDays": 103,
    "startDate": "2026-10-05",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Proximity Service", "startPage": 1,   "endPage": 30 },
      { "chapterNumber": 2,  "chapterTitle": "Nearby Friends", "startPage": 31,  "endPage": 56 },
      { "chapterNumber": 3,  "chapterTitle": "Google Maps", "startPage": 57,  "endPage": 92 },
      { "chapterNumber": 4,  "chapterTitle": "Distributed Message Queue", "startPage": 93,  "endPage": 130 },
      { "chapterNumber": 5,  "chapterTitle": "Metrics Monitoring and Alerting System", "startPage": 131, "endPage": 162 },
      { "chapterNumber": 6,  "chapterTitle": "Ad Click Event Aggregation", "startPage": 163, "endPage": 196 },
      { "chapterNumber": 7,  "chapterTitle": "Hotel Reservation System", "startPage": 197, "endPage": 228 },
      { "chapterNumber": 8,  "chapterTitle": "Distributed Email Service", "startPage": 229, "endPage": 264 },
      { "chapterNumber": 9,  "chapterTitle": "S3-like Object Storage", "startPage": 265, "endPage": 300 },
      { "chapterNumber": 10, "chapterTitle": "Real-time Gaming Leaderboard", "startPage": 301, "endPage": 330 },
      { "chapterNumber": 11, "chapterTitle": "Payment System", "startPage": 331, "endPage": 360 },
      { "chapterNumber": 12, "chapterTitle": "Digital Wallet", "startPage": 361, "endPage": 387 }
    ]
  }'
```

---

## Low Level Design

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software",
    "category": "Low Level Design",
    "author": "Eric Freeman",
    "totalPages": 694,
    "plannedDays": 95,
    "startDate": "2026-07-17",
    "chapters": [
      { "chapterNumber": 1,  "chapterTitle": "Intro to Design Patterns: Welcome to Design Patterns", "startPage": 1,   "endPage": 52 },
      { "chapterNumber": 2,  "chapterTitle": "The Observer Pattern: Keeping your Objects in the Know", "startPage": 53,  "endPage": 100 },
      { "chapterNumber": 3,  "chapterTitle": "The Decorator Pattern: Decorating Objects", "startPage": 101, "endPage": 144 },
      { "chapterNumber": 4,  "chapterTitle": "The Factory Pattern: Baking with OO Goodness", "startPage": 145, "endPage": 196 },
      { "chapterNumber": 5,  "chapterTitle": "The Singleton Pattern: One of a Kind Objects", "startPage": 197, "endPage": 226 },
      { "chapterNumber": 6,  "chapterTitle": "The Command Pattern: Encapsulating Invocation", "startPage": 227, "endPage": 278 },
      { "chapterNumber": 7,  "chapterTitle": "Being Adaptive: The Adapter and Facade Patterns", "startPage": 279, "endPage": 326 },
      { "chapterNumber": 8,  "chapterTitle": "The Template Method Pattern: Encapsulating Algorithms", "startPage": 327, "endPage": 362 },
      { "chapterNumber": 9,  "chapterTitle": "The Iterator and Composite Patterns: Well-Managed Collections", "startPage": 363, "endPage": 418 },
      { "chapterNumber": 10, "chapterTitle": "The State Pattern: The State of Things", "startPage": 419, "endPage": 458 },
      { "chapterNumber": 11, "chapterTitle": "The Proxy Pattern: Controlling Object Access", "startPage": 459, "endPage": 520 },
      { "chapterNumber": 12, "chapterTitle": "Compound Patterns: Patterns of Patterns", "startPage": 521, "endPage": 594 },
      { "chapterNumber": 13, "chapterTitle": "Patterns in the Real World: Better Living with Patterns", "startPage": 595, "endPage": 638 },
      { "chapterNumber": 14, "chapterTitle": "Appendix: Leftover Patterns", "startPage": 639, "endPage": 694 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "The Object-Oriented Thought Process",
    "category": "Low Level Design",
    "author": "Matt Weisfeld",
    "totalPages": 336,
    "plannedDays": 46,
    "startDate": "2026-10-20",
    "chapters": [
      { "chapterNumber": 0,  "chapterTitle": "Introduction", "startPage": 1,   "endPage": 12 },
      { "chapterNumber": 1,  "chapterTitle": "Introduction to Object-Oriented Concepts", "startPage": 13,  "endPage": 50 },
      { "chapterNumber": 2,  "chapterTitle": "How to Think in Terms of Objects", "startPage": 51,  "endPage": 80 },
      { "chapterNumber": 3,  "chapterTitle": "Advanced Object-Oriented Concepts", "startPage": 81,  "endPage": 114 },
      { "chapterNumber": 4,  "chapterTitle": "The Anatomy of a Class", "startPage": 115, "endPage": 148 },
      { "chapterNumber": 5,  "chapterTitle": "Class Design Guidelines", "startPage": 149, "endPage": 178 },
      { "chapterNumber": 6,  "chapterTitle": "Designing with Objects", "startPage": 179, "endPage": 212 },
      { "chapterNumber": 7,  "chapterTitle": "Mastering Inheritance and Composition", "startPage": 213, "endPage": 248 },
      { "chapterNumber": 8,  "chapterTitle": "Frameworks and Reuse: Designing with Interfaces and Abstract Classes", "startPage": 249, "endPage": 278 },
      { "chapterNumber": 9,  "chapterTitle": "Building Objects and Object-Oriented Design", "startPage": 279, "endPage": 306 },
      { "chapterNumber": 10, "chapterTitle": "Creating Object Models", "startPage": 307, "endPage": 322 },
      { "chapterNumber": 11, "chapterTitle": "Objects and Portable Data: XML and JSON", "startPage": 323, "endPage": 336 }
    ]
  }'
```

```bash
curl -X POST "http://localhost:8080/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Design Patterns: Elements of Reusable Object-Oriented Software",
    "category": "Low Level Design",
    "author": "Erich Gamma",
    "totalPages": 292,
    "plannedDays": 40,
    "startDate": "2026-12-05",
    "chapters": [
      { "chapterNumber": 1, "chapterTitle": "Introduction", "startPage": 1,   "endPage": 30 },
      { "chapterNumber": 2, "chapterTitle": "A Case Study: Designing a Document Editor", "startPage": 31,  "endPage": 68 },
      { "chapterNumber": 3, "chapterTitle": "Creational Patterns", "startPage": 69,  "endPage": 126 },
      { "chapterNumber": 4, "chapterTitle": "Structural Patterns", "startPage": 127, "endPage": 200 },
      { "chapterNumber": 5, "chapterTitle": "Behavioral Patterns", "startPage": 201, "endPage": 278 },
      { "chapterNumber": 6, "chapterTitle": "Conclusion", "startPage": 279, "endPage": 292 }
    ]
  }'
```
