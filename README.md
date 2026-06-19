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
└── frontend/   # React + Vite frontend
```

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 21, Spring Boot 3, Spring Data JPA |
| Database | H2 (dev, in-memory) · PostgreSQL via Supabase (prod) |
| Migrations | Flyway |
| Frontend | React, TypeScript, Create React App, Tailwind CSS |
| Build | Maven (backend) · npm (frontend) |

See [`backend/README.md`](backend/README.md) for API reference and database schema.

---

## Deployment

### Frontend (React/Vite → Render Static Site)

#### Environment Variables

Create a `.env.production` file in the `frontend/` directory, or set these in the Render dashboard before building.

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `REACT_APP_API_BASE_URL` | Yes (prod) | `/api` | Backend API URL (e.g., `https://your-backend.onrender.com/api`) |

**Example `frontend/.env.production`:**
```env
REACT_APP_API_BASE_URL=https://your-backend.onrender.com/api
```

> ⚠️ Create React App environment variables are embedded at **build time**. Set them before building and clear the build cache when redeploying with new values.

#### Render Static Site Settings

| Setting | Value |
|---------|-------|
| Root Directory | `frontend` |
| Build Command | `npm install && npm run build` |
| Publish Directory | `dist` |

#### SPA Routing

The `frontend/public/_redirects` file handles client-side routing so page reloads on routes like `/books/Clean%20Code` don't return 404:

```
/* /index.html 200
```

If the `_redirects` file isn't working, add a rewrite rule manually in the Render dashboard:
1. Go to your static site → **Settings** → **Redirects/Rewrites**
2. Add a rewrite rule:
   - **Source:** `/*`
   - **Destination:** `/index.html`
   - **Action:** `Rewrite`

---

### Backend (Spring Boot → Render Web Service)

#### Environment Variables

Set these in the Render dashboard (or a local `.env` file for testing).

| Variable | Required | Default | Description |
|----------|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | Yes (prod) | — | Must be `prod` to activate the PostgreSQL profile |
| `DATABASE_HOST` | Yes (prod) | — | PostgreSQL host |
| `DATABASE_PORT` | No | `5432` | PostgreSQL port |
| `DATABASE_NAME` | Yes (prod) | — | PostgreSQL database name |
| `DATABASE_USERNAME` | Yes (prod) | — | PostgreSQL username |
| `DATABASE_PASSWORD` | Yes (prod) | — | PostgreSQL password |
| `PORT` | No | `8080` | Server port (Render injects this automatically) |
| `APP_CORS_ALLOWED_ORIGINS` | Yes (prod) | `*` | Allowed frontend origins, comma-separated (e.g., `https://beyond-the-page.onrender.com`) |

#### Render Web Service Settings

| Setting | Value |
|---------|-------|
| Root Directory | `backend` |
| Build Command | `mvn clean package -DskipTests` |
| Start Command | `java -jar target/beyond-the-page-0.0.1-SNAPSHOT.jar` |

---

### Database (Supabase PostgreSQL)

#### Parsing the Supabase Connection String

Supabase provides a pooler connection string under **Project Settings → Database → Connection string (URI)**. Extract the individual parts:

```
postgresql://USERNAME:PASSWORD@HOST:PORT/DATABASE_NAME

Example:
postgresql://postgres.wqcfdrywormbpmpvuokk:MySecurePass123@aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres
            └───────────┬───────────────┘ └──────┬──────┘ └──────────────────┬──────────────────────┘ └─┬─┘ └──┬──┘
                        │                        │                           │                          │      │
            DATABASE_USERNAME           DATABASE_PASSWORD              DATABASE_HOST             DATABASE_PORT  DATABASE_NAME
```

> ⚠️ The Supabase username includes the project reference (e.g., `postgres.wqcfdrywormbpmpvuokk`). Use the full string as `DATABASE_USERNAME`.

#### Example Backend Environment Variables (Supabase)

```bash
SPRING_PROFILES_ACTIVE=prod
DATABASE_HOST=aws-0-ap-southeast-1.pooler.supabase.com
DATABASE_PORT=5432
DATABASE_NAME=postgres
DATABASE_USERNAME=postgres.wqcfdrywormbpmpvuokk
DATABASE_PASSWORD=your-secure-password
PORT=8080
APP_CORS_ALLOWED_ORIGINS=https://beyond-the-page.onrender.com
```

#### Flyway Migrations

On first startup with `SPRING_PROFILES_ACTIVE=prod`, Flyway automatically runs all pending migrations (`V1__init.sql`, `V2__add_cover_image.sql`) against the Supabase database. No manual schema setup is required.

