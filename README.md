# BeyondThePage

BeyondThePage is a personal reading progress tracker that helps you stay on top of your reading goals. You set a book, define how many days you want to finish it in, and the app tracks whether you're on pace — chapter by chapter, day by day.

## What it does

- **Track books** — add books with a page count, a planned duration, and a start date
- **Break it down by chapters** — define chapters with page ranges so progress is visible at a granular level
- **Stay on pace** — the app computes how many pages you should have read by any given date and tells you whether you're on track, at risk, or overdue
- **Dashboard views** — get a summary of all your books, or drill into monthly, weekly, and daily breakdowns to see exactly where you stand

## How it works

There is no history, no authentication, and no complexity. You update your current page number whenever you read, and the app derives everything else — completion percentage, variance from the plan, chapter statuses, and projected finish date — from that single value and the book's configuration.

## Repository Structure

```
BeyondThePage/
├── backend/    # Spring Boot REST API (Java 21)
└── frontend/   # Frontend (design TBD)
```

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 21, Spring Boot 3, Spring Data JPA |
| Database | H2 (in-memory, dev) via Flyway migrations |
| Build | Maven |

See [`backend/README.md`](backend/README.md) for setup instructions, API reference, and database schema.
