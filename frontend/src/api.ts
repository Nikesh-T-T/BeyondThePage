import axios from 'axios';
import {
  BookSummary,
  BookDetail,
  DashboardSummary,
  MonthlyDashboard,
  WeeklyDashboard,
  DailyBookProgress,
  CreateBookRequest,
} from './types';

const api = axios.create({
  baseURL: '/api',
});

interface ApiResponse<T> {
  status: string;
  message: string;
  data: T;
}

export const getBooks = () =>
  api.get<ApiResponse<BookSummary[]>>('/books').then(r => r.data.data);

export const getBook = (bookName: string) =>
  api.get<ApiResponse<BookDetail>>(`/books/${encodeURIComponent(bookName)}`).then(r => r.data.data);

export const createBook = (req: CreateBookRequest) =>
  api.post<ApiResponse<BookSummary>>('/books', req).then(r => r.data.data);

export const updateProgress = (bookName: string, completedPages: number) =>
  api.put(`/books/${encodeURIComponent(bookName)}/progress`, { completedPages });

export const getDashboardSummary = () =>
  api.get<ApiResponse<DashboardSummary>>('/dashboard/summary').then(r => r.data.data);

export const getMonthlyDashboard = (month: string) =>
  api.get<ApiResponse<MonthlyDashboard>>(`/dashboard/monthly?month=${month}`).then(r => r.data.data);

export const getWeeklyDashboard = (date: string) =>
  api.get<ApiResponse<WeeklyDashboard>>(`/dashboard/weekly?date=${date}`).then(r => r.data.data);

export const getDailyDashboard = (date: string) =>
  api.get<ApiResponse<DailyBookProgress[]>>(`/dashboard/daily?date=${date}`).then(r => r.data.data);
