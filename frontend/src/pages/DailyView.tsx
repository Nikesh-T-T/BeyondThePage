import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getDailyDashboard, updateProgress } from '../api';
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

  const activeBooks = data.filter(b => b.status !== 'COMPLETED' && b.status !== 'NOT_STARTED');
  const onTrack = data.filter(b => b.status === 'ON_TRACK' || b.status === 'AT_RISK').length;
  const totalBooks = activeBooks.length;
  const pct = totalBooks > 0 ? Math.round((onTrack / totalBooks) * 100) : 0;

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
          <div className="grid grid-cols-12 gap-lg">
            {/* Main Column */}
            <div className="col-span-12 lg:col-span-8 space-y-lg">
              <div className="flex items-end justify-between">
                <h3 className="text-headline-lg-mobile font-semibold text-primary">Daily Reading List</h3>
                {totalBooks > 0 && (
                  <div className="bg-surface-container-low px-md py-sm rounded-xl border border-outline-variant flex items-center gap-md">
                    <div className="relative w-14 h-14 flex items-center justify-center">
                      <svg className="w-full h-full transform -rotate-90" viewBox="0 0 48 48">
                        <circle cx="24" cy="24" fill="transparent" r="18" stroke="#e5e3d7" strokeWidth="4" />
                        <circle
                          cx="24" cy="24" fill="transparent" r="18"
                          stroke="#605e55" strokeWidth="4"
                          strokeDasharray={`${2 * Math.PI * 18}`}
                          strokeDashoffset={`${2 * Math.PI * 18 * (1 - pct / 100)}`}
                          strokeLinecap="round"
                        />
                      </svg>
                      <span className="absolute text-[9px] font-bold text-primary">{pct}%</span>
                    </div>
                    <div>
                      <p className="font-label-caps text-[10px] text-on-surface-variant">DAILY STATUS</p>
                      <p className="text-[13px] font-bold text-primary">{onTrack} of {totalBooks} on track</p>
                    </div>
                  </div>
                )}
              </div>

              {data.length === 0 ? (
                <div className="card p-xl text-center">
                  <span className="material-symbols-outlined text-[48px] text-outline-variant mb-md block">menu_book</span>
                  <p className="text-on-surface-variant text-body-md">No books to track for this date.</p>
                </div>
              ) : (
                <div className="space-y-md">
                  {data.map(book => {
                    const pctDone = book.plannedPagesByDate > 0
                      ? Math.min(100, Math.round((book.completedPages / book.plannedPagesByDate) * 100))
                      : book.completedPages > 0 ? 100 : 0;
                    const isOverdue = book.status === 'OVERDUE';

                    return (
                      <div
                        key={book.bookName}
                        className="card p-lg flex items-center gap-lg cursor-pointer hover:translate-y-[-2px] transition-transform"
                        onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                      >
                        <div className="flex-shrink-0 w-16 h-20 rounded bg-surface-container-high overflow-hidden flex items-center justify-center">
                          {book.hasCoverImage
                            ? <img src={`/api/books/${encodeURIComponent(book.bookName)}/cover`}
                                   alt={`${book.bookName} cover`}
                                   className="w-full h-full object-cover" />
                            : <span className="material-symbols-outlined text-on-surface-variant">book</span>}
                        </div>

                        <div className="flex-grow">
                          <div className="flex justify-between items-start mb-2">
                            <h3 className="text-[18px] font-bold text-primary leading-tight">{book.bookName}</h3>
                            <StatusBadge status={book.status} />
                          </div>
                          <div className="mt-md">
                            <div className="flex items-center justify-between mb-2">
                              <span className="text-body-sm text-on-surface-variant">
                                {book.completedPages} / {Math.round(book.plannedPagesByDate)} planned pages
                              </span>
                              <span className={`text-[12px] font-bold ${isOverdue ? 'text-error' : 'text-primary'}`}>
                                {pctDone}%
                              </span>
                            </div>
                            <div className="w-full h-1.5 bg-surface-container-highest rounded-full overflow-hidden">
                              <div
                                className={`h-full rounded-full ${isOverdue ? 'bg-error' : 'bg-secondary'}`}
                                style={{ width: `${pctDone}%` }}
                              />
                            </div>
                            <div className="mt-2 flex items-center justify-between">
                              <span className={`text-[11px] font-semibold ${book.variancePages >= 0 ? 'text-secondary' : 'text-error'}`}>
                                {book.variancePages >= 0 ? '+' : ''}{Math.round(book.variancePages)} pages vs plan
                              </span>
                              <button
                                onClick={e => openModal(book, e)}
                                className="text-[11px] font-bold text-primary hover:underline flex items-center gap-1"
                              >
                                <span className="material-symbols-outlined text-[14px]">edit</span>
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

            {/* Sidebar Stats */}
            <div className="col-span-12 lg:col-span-4 space-y-lg">
              <div className="bg-primary-fixed text-on-primary-fixed p-lg rounded-xl shadow-lg">
                <div className="flex justify-between items-center mb-md">
                  <h3 className="font-label-caps text-[11px] opacity-70 tracking-widest">TODAY'S OVERVIEW</h3>
                  <span className="material-symbols-outlined text-[20px] opacity-60">trending_up</span>
                </div>
                <div className="flex items-baseline gap-2 mb-sm">
                  <span className="text-headline-xl font-bold leading-none">{totalBooks}</span>
                  <span className="text-[16px] font-medium opacity-70">active books</span>
                </div>
                <div className="w-full h-2 bg-on-primary-fixed/15 rounded-full mb-lg">
                  <div className="h-full bg-on-primary-fixed rounded-full" style={{ width: `${pct}%` }} />
                </div>
                <div className="grid grid-cols-2 gap-md">
                  <div className="bg-on-primary-fixed/10 p-md rounded-lg border border-on-primary-fixed/10">
                    <p className="text-[10px] font-bold opacity-60 mb-1 uppercase">On Track</p>
                    <p className="text-[22px] font-bold">{onTrack}</p>
                  </div>
                  <div className="bg-on-primary-fixed/10 p-md rounded-lg border border-on-primary-fixed/10">
                    <p className="text-[10px] font-bold opacity-60 mb-1 uppercase">Overdue</p>
                    <p className="text-[22px] font-bold">{data.filter(b => b.status === 'OVERDUE').length}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
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
