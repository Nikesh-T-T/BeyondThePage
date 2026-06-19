import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getDashboardSummary, getBooks } from '../api';
import { DashboardSummary, BookSummary } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

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

  const inProgress = books.filter(b => b.currentStatus === 'ON_TRACK' || b.currentStatus === 'AT_RISK');
  const overdue = books.filter(b => b.currentStatus === 'OVERDUE');

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" />

      <div className="max-w-container-max mx-auto p-lg space-y-xl">
        {/* Summary Cards */}
        <section className="grid grid-cols-2 md:grid-cols-4 gap-lg">
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
        </section>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-xl">
          {/* Books List */}
          <section className="lg:col-span-8 space-y-lg">
            <div className="flex justify-between items-center">
              <h3 className="text-headline-lg-mobile font-semibold text-on-surface">Current Reading List</h3>
              <button
                className="text-primary font-label-caps text-label-caps hover:underline"
                onClick={() => navigate('/books')}
              >
                VIEW ALL
              </button>
            </div>

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
              <div className="space-y-md">
                {books.slice(0, 5).map(book => (
                  <div
                    key={book.bookName}
                    className="card p-lg flex flex-col md:flex-row md:items-center gap-lg cursor-pointer"
                    onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                  >
                    <div className="w-12 h-16 bg-surface-container-high rounded shadow-sm flex-shrink-0 flex items-center justify-center">
                      <span className="material-symbols-outlined text-on-surface-variant">book</span>
                    </div>
                    <div className="flex-grow space-y-xs">
                      <h4 className="text-[18px] font-semibold leading-tight text-on-surface">{book.bookName}</h4>
                      <div className="space-y-xs pt-sm">
                        <div className="flex justify-between font-label-caps text-[10px]">
                          <span className="text-on-surface-variant">PROGRESS</span>
                          <span className={book.currentStatus === 'OVERDUE' ? 'text-error' : 'text-primary'}>
                            {Math.round(book.completionPercentage)}%
                          </span>
                        </div>
                        <div className="w-full h-2 bg-surface-container-highest rounded-full overflow-hidden">
                          <div
                            className={`h-full rounded-full transition-all duration-700 ${
                              book.currentStatus === 'OVERDUE' ? 'bg-error' : 'bg-primary'
                            }`}
                            style={{ width: `${book.completionPercentage}%` }}
                          />
                        </div>
                      </div>
                    </div>
                    <div className="flex flex-col items-end gap-sm min-w-[130px]">
                      <StatusBadge status={book.currentStatus} />
                      <span className="font-mono text-[12px] text-on-surface-variant">
                        {book.completedPages} / {book.totalPages} pgs
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </section>

          {/* Side Panel */}
          <aside className="lg:col-span-4 space-y-xl">
            {overdue.length > 0 && (
              <div className="space-y-lg">
                <h3 className="text-headline-lg-mobile font-semibold text-on-surface flex items-center gap-2">
                  <span className="material-symbols-outlined text-error">warning</span>
                  Needs Attention
                </h3>
                <div className="space-y-md">
                  {overdue.map(book => (
                    <div
                      key={book.bookName}
                      className="card p-lg border-l-4 border-l-error cursor-pointer"
                      onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                    >
                      <p className="font-semibold text-on-surface">{book.bookName}</p>
                      <p className="text-body-sm text-error mt-xs">
                        {book.daysRemaining === 0
                          ? 'Past deadline'
                          : `${book.daysRemaining} days remaining`}
                        {' · '}{book.remainingPages} pages left
                      </p>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {inProgress.length > 0 && (
              <div className="space-y-lg">
                <h3 className="text-headline-lg-mobile font-semibold text-on-surface">Finishing Soon</h3>
                <div className="card p-lg space-y-md">
                  {inProgress.slice(0, 2).map(book => (
                    <div
                      key={book.bookName}
                      className="flex items-center gap-md cursor-pointer"
                      onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                    >
                      <div className="w-10 h-10 rounded-lg bg-surface-variant flex items-center justify-center flex-shrink-0">
                        <span className="material-symbols-outlined text-primary">auto_stories</span>
                      </div>
                      <div>
                        <p className="text-body-md font-semibold text-on-surface">{book.bookName}</p>
                        <p className="text-body-sm text-on-surface-variant">
                          {book.remainingPages} pages · {book.daysRemaining} days left
                        </p>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}

            <div className="card p-lg bg-primary-container text-on-primary border-none shadow-lg">
              <h3 className="text-headline-lg-mobile font-semibold mb-sm text-white">Reading Stats</h3>
              <p className="text-body-sm mb-lg opacity-90 text-white">
                {summary?.completedBooks ?? 0} of {summary?.totalBooks ?? 0} books completed
              </p>
              <div className="flex items-center justify-center py-md relative">
                <svg className="w-28 h-28 transform -rotate-90" viewBox="0 0 100 100">
                  <circle cx="50" cy="50" fill="transparent" r="44" stroke="rgba(255,255,255,0.15)" strokeWidth="8" />
                  {(summary?.totalBooks ?? 0) > 0 && (
                    <circle
                      cx="50" cy="50" fill="transparent" r="44"
                      stroke="white" strokeWidth="8"
                      strokeDasharray={`${2 * Math.PI * 44}`}
                      strokeDashoffset={`${2 * Math.PI * 44 * (1 - (summary!.completedBooks / summary!.totalBooks))}`}
                      strokeLinecap="round"
                    />
                  )}
                </svg>
                <div className="absolute inset-0 flex flex-col items-center justify-center text-white">
                  <span className="text-headline-xl font-bold leading-none">
                    {summary?.completedBooks ?? 0}/{summary?.totalBooks ?? 0}
                  </span>
                  <span className="font-label-caps text-[10px]">BOOKS</span>
                </div>
              </div>
            </div>
          </aside>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
