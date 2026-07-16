import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getDashboardSummary, getBooks } from '../api';
import { DashboardSummary, BookSummary } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

const UNCATEGORIZED = 'Uncategorized';

const fmt = (dateStr: string): string => {
  const d = new Date(dateStr + 'T00:00:00');
  return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
};

const Dashboard: React.FC = () => {
  const [summary, setSummary] = useState<DashboardSummary | null>(null);
  const [books, setBooks] = useState<BookSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    Promise.all([getDashboardSummary(), getBooks()])
      .then(([s, b]) => {
        setSummary(s);
        setBooks(b);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <span className="material-symbols-outlined animate-spin text-primary text-[48px]">autorenew</span>
      </div>
    );
  }

  const grouped: Record<string, BookSummary[]> = {};
  for (const book of books) {
    const key = book.category ?? UNCATEGORIZED;
    if (!grouped[key]) grouped[key] = [];
    grouped[key].push(book);
  }
  for (const cat of Object.keys(grouped)) {
    grouped[cat].sort((a, b) => a.startDate.localeCompare(b.startDate));
  }

  type Row = { seq: number; book: BookSummary; showCategory: boolean };
  const rows: Row[] = [];
  let seq = 1;
  for (const cat of Object.keys(grouped)) {
    grouped[cat].forEach((book, i) => {
      rows.push({ seq: seq++, book, showCategory: i === 0 });
    });
  }

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" onSearch={q => { if (q) navigate(`/books?q=${encodeURIComponent(q)}`); }} />

      <div className="max-w-container-max mx-auto p-lg space-y-xl">
        {/* Summary Cards */}
        <section className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-lg">
          <div className="card p-lg flex flex-col">
            <span className="font-label-caps text-label-caps text-on-surface-variant mb-xs">TOTAL BOOKS</span>
            <div className="flex items-end gap-sm mt-auto">
              <span className="text-headline-xl font-bold text-primary leading-none">{summary?.totalBooks ?? 0}</span>
              <span className="material-symbols-outlined text-outline-variant mb-1">library_books</span>
            </div>
          </div>

          <div className="card p-lg flex flex-col border-l-4 border-l-primary">
            <span className="font-label-caps text-label-caps text-on-surface-variant mb-xs">IN PROGRESS</span>
            <div className="flex items-end gap-sm mt-auto">
              <span className="text-headline-xl font-bold text-primary leading-none">{summary?.inProgressBooks ?? 0}</span>
              <span className="material-symbols-outlined text-primary mb-1">auto_stories</span>
            </div>
          </div>

          <div className="card p-lg flex flex-col">
            <span className="font-label-caps text-label-caps text-on-surface-variant mb-xs">COMPLETED</span>
            <div className="flex items-end gap-sm mt-auto">
              <span className="text-headline-xl font-bold text-primary leading-none">{summary?.completedBooks ?? 0}</span>
              <span className="material-symbols-outlined text-secondary mb-1">check_circle</span>
            </div>
          </div>

          <div className="card p-lg flex flex-col border-l-4 border-l-error">
            <span className="font-label-caps text-label-caps text-error mb-xs">OVERDUE</span>
            <div className="flex items-end gap-sm mt-auto">
              <span className="text-headline-xl font-bold text-error leading-none">{summary?.overdueBooks ?? 0}</span>
              <span className="material-symbols-outlined text-error mb-1">warning</span>
            </div>
          </div>

          <div className="col-span-2 p-lg rounded-xl bg-primary-fixed text-on-primary-fixed shadow-lg flex items-center gap-lg">
            <div className="relative flex-shrink-0">
              <svg className="w-16 h-16 transform -rotate-90" viewBox="0 0 100 100">
                <circle cx="50" cy="50" fill="transparent" r="44" stroke="currentColor" strokeOpacity="0.15" strokeWidth="10" />
                {(summary?.totalBooks ?? 0) > 0 && (
                  <circle
                    cx="50" cy="50" fill="transparent" r="44"
                    stroke="currentColor" strokeWidth="10"
                    strokeDasharray={`${2 * Math.PI * 44}`}
                    strokeDashoffset={`${2 * Math.PI * 44 * (1 - (summary!.completedBooks / summary!.totalBooks))}`}
                    strokeLinecap="round"
                  />
                )}
              </svg>
              <div className="absolute inset-0 flex flex-col items-center justify-center">
                <span className="text-sm font-bold leading-none">{summary?.completedBooks ?? 0}/{summary?.totalBooks ?? 0}</span>
              </div>
            </div>
            <div>
              <span className="font-label-caps text-label-caps opacity-70 block mb-xs">READING STATS</span>
              <p className="text-headline-lg-mobile font-bold leading-tight">
                {summary?.completedBooks ?? 0} of {summary?.totalBooks ?? 0}
              </p>
              <p className="text-body-sm opacity-70">books completed</p>
            </div>
          </div>
        </section>

        {/* Reading Plan Table */}
        <section className="space-y-lg">
          <h3 className="text-headline-lg-mobile font-semibold text-on-surface">Reading Plan</h3>

          {books.length === 0 ? (
            <div className="card p-xl text-center">
              <span className="material-symbols-outlined text-[48px] text-outline-variant mb-md block">menu_book</span>
              <p className="text-on-surface-variant text-body-md mb-md">No books yet. Start tracking your reading!</p>
              <button
                onClick={() => navigate('/books/new')}
                className="px-lg py-sm bg-primary text-on-primary rounded-lg font-bold hover:opacity-90 transition-opacity"
              >
                Add Your First Book
              </button>
            </div>
          ) : (
            <div className="card overflow-hidden">
              <table className="w-full text-body-sm border-collapse">
                <thead>
                  <tr className="bg-surface-container-high border-b border-outline-variant">
                    <th className="px-md py-sm text-right font-label-caps text-label-caps text-on-surface-variant w-8">#</th>
                    <th className="px-md py-sm text-left font-label-caps text-label-caps text-on-surface-variant">Category</th>
                    <th className="px-md py-sm text-left font-label-caps text-label-caps text-on-surface-variant">Book</th>
                    <th className="px-md py-sm text-right font-label-caps text-label-caps text-on-surface-variant">Pages</th>
                    <th className="px-md py-sm text-right font-label-caps text-label-caps text-on-surface-variant">Days</th>
                    <th className="px-md py-sm text-center font-label-caps text-label-caps text-on-surface-variant">Start</th>
                    <th className="px-md py-sm text-center font-label-caps text-label-caps text-on-surface-variant">End</th>
                    <th className="px-md py-sm text-center font-label-caps text-label-caps text-on-surface-variant">Status</th>
                  </tr>
                </thead>
                <tbody>
                  {rows.map(({ seq: n, book, showCategory }, idx) => (
                    <tr
                      key={book.bookName}
                      className={`border-b border-outline-variant cursor-pointer transition-colors hover:bg-surface-container-high ${
                        showCategory && idx !== 0 ? 'border-t-2 border-t-outline' : ''
                      }`}
                      onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                    >
                      <td className="px-md py-sm text-right font-mono text-[11px] text-on-surface-variant">{n}</td>
                      <td className="px-md py-sm text-on-surface-variant text-[12px] whitespace-nowrap">
                        {showCategory ? book.category : ''}
                      </td>
                      <td className="px-md py-sm text-on-surface font-medium">{book.bookName}</td>
                      <td className="px-md py-sm text-right font-mono text-[12px] text-on-surface-variant">{book.totalPages}</td>
                      <td className="px-md py-sm text-right font-mono text-[12px] text-on-surface-variant">{book.plannedDays}</td>
                      <td className="px-md py-sm text-center font-mono text-[12px] text-on-surface-variant whitespace-nowrap">{fmt(book.startDate)}</td>
                      <td className="px-md py-sm text-center font-mono text-[12px] text-on-surface-variant whitespace-nowrap">{fmt(book.targetEndDate)}</td>
                      <td className="px-md py-sm text-center">
                        <StatusBadge status={book.currentStatus} />
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </section>
      </div>
    </div>
  );
};

export default Dashboard;
