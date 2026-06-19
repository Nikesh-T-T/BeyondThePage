export type BookStatus = 'NOT_STARTED' | 'ON_TRACK' | 'AT_RISK' | 'OVERDUE' | 'COMPLETED';
export type ChapterStatus = 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED' | 'OVERDUE';

export interface ChapterDetail {
  chapterNumber: number;
  chapterTitle: string;
  startPage: number;
  endPage: number;
  status: ChapterStatus;
}

export interface BookSummary {
  bookName: string;
  totalPages: number;
  plannedDays: number;
  startDate: string;
  completedPages: number;
  remainingPages: number;
  completionPercentage: number;
  plannedDailyPages: number;
  targetEndDate: string;
  currentStatus: BookStatus;
  daysElapsed: number;
  daysRemaining: number;
}

export interface BookDetail extends BookSummary {
  expectedPagesByToday: number;
  variancePages: number;
  completedChapters: number;
  pendingChapters: number;
  overdueChapters: number;
  chapters: ChapterDetail[];
}

export interface DashboardSummary {
  totalBooks: number;
  notStartedBooks: number;
  inProgressBooks: number;
  completedBooks: number;
  overdueBooks: number;
}

export interface MonthlyDashboard {
  selectedMonth: string;
  booksPlannedToFinish: number;
  booksCompleted: number;
  booksOverdue: number;
  booksNotStarted: number;
  booksOnTrack: number;
}

export interface WeeklyDashboard {
  weekStartDate: string;
  weekEndDate: string;
  chaptersPlanned: number;
  chaptersCompleted: number;
  chaptersOverdue: number;
  chaptersNotStarted: number;
  totalPlannedPages: number;
  totalCompletedPages: number;
}

export interface DailyBookProgress {
  bookName: string;
  plannedPagesByDate: number;
  completedPages: number;
  variancePages: number;
  status: BookStatus;
}

export interface CreateBookRequest {
  bookName: string;
  totalPages: number;
  plannedDays: number;
  startDate: string;
  chapters: {
    chapterNumber: number;
    chapterTitle: string;
    startPage: number;
    endPage: number;
  }[];
}
