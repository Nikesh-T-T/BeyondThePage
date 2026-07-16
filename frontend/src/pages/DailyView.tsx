import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getDailyDashboard, updateProgress, coverUrl } from '../api';
import { DailyBookProgress } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

const todayDateStr = () => {
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`;
};

const shiftDate = (dateStr: string, days: number) => {
  const [y, m, d] = dateStr.split('-').map(Number);
  const date = new Date(y, m - 1, d + days);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
};

const DailyView: React.FC = () => {
  const [currentDate, setCurrentDate] = useState(todayDateStr);
  const [data, setData] = useState<DailyBookProgress[]>([]);
  const [loading, setLoading] = useState(true);
  const [modalBook, setModalBook] = useState<DailyBookProgress | null>(null);
  const [progressInput, setProgressInput] = useState('');
  const [saving, setSaving] = useState(false);
  const [modalError, setModalError] = useState('');
  const navigate = useNavigate();

  const load = useCallback((date: string) => {
    setLoading(true);
    getDailyDashboard(date)
      .then(setData)
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    load(currentDate);
  }, [currentDate, load]);

  const goToPrev = () => setCurrentDate(shiftDate(currentDate, -1));
  const goToNext = () => setCurrentDate(shiftDate(currentDate, 1));

  const formatDate = (dateStr: string) => {
    const d = new Date(dateStr + 'T00:00:00');
    return d.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' });
  };

  const openModal = (book: DailyBookProgress, e: React.MouseEvent) => {
    e.stopPropagation();
    setModalBook(book);
    setProgressInput(String(book.completedPages));
    setModalError('');
  };

  const closeModal = () => {
    setModalBook(null);
    setModalError('');
  };

  const handleSave = async () => {
    if (!modalBook) return;
    const pages = parseInt(progressInput, 10);
    if (isNaN(pages) || pages < 0 || pages > modalBook.totalPages) {
      setModalError(`Enter a value between 0 and ${modalBook.totalPages}`);
      return;
    }
    setSaving(true);
    setModalError('');
    try {
      await updateProgress(modalBook.bookName, pages);
      closeModal();
      load(currentDate);
    } catch {
      setModalError('Failed to update progress. Please try again.');
    } finally {
      setSaving(false);
    }
  };

  const activeBooks = data;
  const totalBooks = activeBooks.length;

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" onSearch={q => { if (q) navigate(`/books?q=${encodeURIComponent(q)}`); }} />

      <div className="max-w-container-max mx-auto p-lg md:p-xl space-y-xl">
        {/* Header */}
        <section className="flex flex-col md:flex-row md:items-end justify-between gap-md">
          <div>
            <p className="font-label-caps text-label-caps text-secondary mb-1">DAILY VIEW</p>
            <h2 className="text-h1 font-bold text-on-surface">{formatDate(currentDate)}</h2>
          </div>
          <div className="flex items-center gap-2">
            <button
              onClick={goToPrev}
              className="bg-surface-container-lowest border border-outline-variant px-md py-sm rounded-lg font-bold text-on-surface-variant hover:border-primary transition-colors flex items-center"
            >
              <span className="material-symbols-outlined">chevron_left</span>
            </button>
            <div className="bg-surface-container-lowest border border-outline-variant px-lg py-sm rounded-lg flex items-center gap-2 min-w-[200px] justify-center">
              <span className="material-symbols-outlined text-[18px] text-on-surface-variant">calendar_today</span>
              <span className="text-body-sm font-semibold text-on-surface">{formatDate(currentDate)}</span>
            </div>
            <button
              onClick={() => setCurrentDate(todayDateStr())}
              className="bg-surface-container-lowest border border-outline-variant px-md py-sm rounded-lg font-bold text-on-surface-variant hover:border-primary transition-colors flex items-center gap-2"
            >
              <span className="text-body-sm">Today</span>
            </button>
            <button
              onClick={goToNext}
              className="bg-surface-container-lowest border border-outline-variant px-md py-sm rounded-lg font-bold text-on-surface-variant hover:border-primary transition-colors flex items-center"
            >
              <span className="material-symbols-outlined">chevron_right</span>
            </button>
          </div>
        </section>

        {loading ? (
          <div className="flex justify-center py-xl">
            <span className="material-symbols-outlined animate-spin text-primary text-[48px]">autorenew</span>
          </div>
        ) : (
          <>
            {/* Stats Bento Grid */}
            <section className="grid grid-cols-1 md:grid-cols-12 gap-lg">
              <div className="md:col-span-8 card p-lg flex items-center justify-between">
                <div>
                  <h3 className="text-h2 font-semibold mb-1">Daily Reading Target</h3>
                  <p className="text-body-sm text-on-surface-variant">
                    {totalBooks} book{totalBooks !== 1 ? 's' : ''} active today
                  </p>
                </div>
                <div className="text-right">
                  <span className="text-headline-xl font-bold text-primary leading-none">
                    {data.filter(b => b.completedPages >= b.plannedPageRangeEnd).length}
                  </span>
                  <span className="text-h2 text-on-surface-variant">/ {totalBooks}</span>
                  <p className="font-label-caps text-label-caps text-on-surface-variant">ON TRACK</p>
                </div>
              </div>

              <div className="md:col-span-4 grid grid-rows-2 gap-lg">
                <div className="card p-lg flex items-center gap-lg">
                  <div className="w-12 h-12 rounded-xl bg-surface-variant flex items-center justify-center text-primary">
                    <span className="material-symbols-outlined">menu_book</span>
                  </div>
                  <div>
                    <p className="font-label-caps text-label-caps text-on-surface-variant">PAGES READ</p>
                    <p className="text-h2 font-semibold">
                      {data.reduce((sum, b) => sum + b.completedPages, 0)} / {data.reduce((sum, b) => sum + Math.ceil(b.plannedPagesByDate), 0)}
                    </p>
                  </div>
                </div>

                <div className="card p-lg flex items-center gap-lg">
                  <div className="w-12 h-12 rounded-xl bg-secondary-container flex items-center justify-center text-on-secondary-container">
                    <span className="material-symbols-outlined">priority_high</span>
                  </div>
                  <div>
                    <p className="font-label-caps text-label-caps text-on-surface-variant">OVERDUE</p>
                    <p className={`text-h2 font-semibold ${data.filter(b => b.status === 'OVERDUE').length > 0 ? 'text-error' : ''}`}>
                      {data.filter(b => b.status === 'OVERDUE').length} books
                    </p>
                  </div>
                </div>
              </div>
            </section>

          <div className="space-y-lg">
            <div className="space-y-lg">
              <h3 className="text-headline-lg-mobile font-semibold text-primary">Daily Reading List</h3>

              {totalBooks === 0 ? (
                <div className="card p-xl text-center">
                  <span className="material-symbols-outlined text-[48px] text-outline-variant mb-md block">menu_book</span>
                  <p className="text-on-surface-variant text-body-md">No books to track for this date.</p>
                </div>
              ) : (
                <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-md">
                  {activeBooks.map(book => {
                    const pctDone = book.plannedPagesByDate > 0
                      ? Math.min(100, Math.round((book.completedPages / book.plannedPagesByDate) * 100))
                      : book.completedPages > 0 ? 100 : 0;
                    const isOverdue = book.status === 'OVERDUE';

                    return (
                      <div
                        key={book.bookName}
                        className="card p-md flex flex-col gap-sm cursor-pointer hover:translate-y-[-2px] transition-transform"
                        onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                      >
                        <div className="w-full aspect-[2/3] rounded bg-surface-container-high overflow-hidden flex items-center justify-center">
                          {book.hasCoverImage
                            ? <img src={coverUrl(book.bookName)}
                                   alt={`${book.bookName} cover`}
                                   className="w-full h-full object-cover" />
                            : <span className="material-symbols-outlined text-on-surface-variant text-[36px]">book</span>}
                        </div>

                        <div className="flex-grow flex flex-col gap-xs">
                          <h3 className="text-[13px] font-bold text-on-surface leading-tight min-h-[2.6em] line-clamp-2">{book.bookName}</h3>
                          <StatusBadge status={book.status} />

                          <div className="mt-auto space-y-xs">
                            <div className="flex items-center justify-between">
                              <span className="text-[11px] text-on-surface-variant">
                                p.{book.plannedPageRangeStart}–{book.plannedPageRangeEnd}
                              </span>
                              <span className={`text-[11px] font-bold ${isOverdue ? 'text-error' : 'text-primary'}`}>
                                {pctDone}%
                              </span>
                            </div>
                            <div className="w-full h-1 bg-surface-container-highest rounded-full overflow-hidden">
                              <div
                                className={`h-full rounded-full ${isOverdue ? 'bg-error' : 'bg-secondary'}`}
                                style={{ width: `${pctDone}%` }}
                              />
                            </div>
                            <div className="flex items-center justify-between">
                              <span className={`text-[10px] font-semibold ${book.variancePages >= 0 ? 'text-secondary' : 'text-error'}`}>
                                {book.variancePages >= 0 ? '+' : ''}{Math.round(book.variancePages)} pg
                              </span>
                              <button
                                onClick={e => openModal(book, e)}
                                className="text-[11px] font-bold text-primary hover:underline flex items-center gap-xs"
                              >
                                <span className="material-symbols-outlined text-[13px]">edit</span>
                                Update
                              </button>
                            </div>
                          </div>
                        </div>
                      </div>
                    );
                  })}
                </div>
              )}
            </div>

          </div>
          </>
        )}
      </div>

      {/* Progress Update Modal */}
      {modalBook && (
        <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50 p-md">
          <div className="bg-surface-container-lowest rounded-xl shadow-2xl w-full max-w-md p-xl space-y-lg">
            <div className="flex justify-between items-center">
              <h3 className="text-h2 font-semibold">Update Progress</h3>
              <button onClick={closeModal} className="text-on-surface-variant hover:text-on-surface">
                <span className="material-symbols-outlined">close</span>
              </button>
            </div>

            <div>
              <p className="text-body-sm text-on-surface-variant mb-md">
                {modalBook.bookName} · {modalBook.totalPages} total pages
              </p>
              <label className="block font-label-caps text-label-caps text-on-surface-variant mb-xs">
                COMPLETED PAGES
              </label>
              <input
                type="number"
                min={0}
                max={modalBook.totalPages}
                value={progressInput}
                onChange={e => setProgressInput(e.target.value)}
                className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-h2 font-bold text-primary focus:outline-none focus:ring-2 focus:ring-primary"
                autoFocus
              />
              {modalError && <p className="text-error text-body-sm mt-xs">{modalError}</p>}
            </div>

            <div className="flex gap-sm">
              <button
                onClick={closeModal}
                className="flex-1 py-sm border border-outline-variant rounded-lg font-bold text-on-surface-variant hover:bg-surface-container transition-colors"
              >
                Cancel
              </button>
              <button
                onClick={handleSave}
                disabled={saving}
                className="flex-1 py-sm bg-primary text-on-primary rounded-lg font-bold hover:opacity-90 transition-opacity disabled:opacity-50"
              >
                {saving ? 'Saving…' : 'Save Progress'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default DailyView;
