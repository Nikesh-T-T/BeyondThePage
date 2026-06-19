# BeyondThePage — Frontend

React + TypeScript frontend for the BeyondThePage reading progress tracker.

---

## Prerequisites

- Node.js 18+
- npm 9+
- Backend running on `http://localhost:8080` (see [`../backend/README.md`](../backend/README.md))

---

## Running the Application

```bash
cd frontend
npm install
npm start
```

The app starts on `http://localhost:3000`. API calls are proxied to `http://localhost:8080`.

To build for production:

```bash
npm run build
```

---

## Project Structure

```
frontend/
├── public/
├── src/
│   ├── api.ts                  # Axios API client (all backend calls)
│   ├── types.ts                # TypeScript types matching backend DTOs
│   ├── App.tsx                 # Root component with React Router setup
│   ├── index.css               # Tailwind base + global styles
│   ├── components/
│   │   ├── Sidebar.tsx         # Left navigation sidebar
│   │   ├── TopBar.tsx          # Sticky top header with search
│   │   └── StatusBadge.tsx     # Status pill (On Track / At Risk / Overdue / etc.)
│   └── pages/
│       ├── Dashboard.tsx       # Overview with summary stats and current books
│       ├── Books.tsx           # Full book library list
│       ├── BookDetail.tsx      # Book detail with chapter table and progress modal
│       ├── AddBook.tsx         # Add book form with dynamic chapter builder
│       ├── WeeklyView.tsx      # Weekly chapter progress view with navigation
│       └── DailyView.tsx       # Daily per-book progress view with variance
├── tailwind.config.js
├── postcss.config.js
└── package.json
```

---

## Pages

| Route | Page | Description |
|-------|------|-------------|
| `/` | Dashboard | Summary stats, current reading list, finishing-soon sidebar |
| `/books` | Books | Full library with status and progress for every book |
| `/books/new` | Add Book | Form to add a book with chapters |
| `/books/:bookName` | Book Detail | Chapter table, progress metrics, update-progress modal |
| `/weekly` | Weekly View | Chapter completion for the current week with prev/next navigation |
| `/daily` | Daily View | Per-book planned vs actual progress for any selected date |

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Framework | React 19, TypeScript |
| Styling | Tailwind CSS v3, Inter font, Material Symbols |
| Routing | React Router v7 |
| HTTP | Axios (proxied to backend at `:8080` in dev) |
| Build | Create React App |
