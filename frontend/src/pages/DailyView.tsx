import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getDailyDashboard } from '../api';
import { DailyBookProgress } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

const DailyView: React.FC = () => {
  const [currentDate, setCurrentDate] = useState(new Date().toISOString().split('T')[0]);
  const [data, setData] = useState<DailyBookProgress[]>([]);
  const [loading, setLoading] = useState(true);
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

  const goToPrev = () => {
    const d = new Date(currentDate);
    d.setDate(d.getDate() - 1);
    setCurrentDate(d.toISOString().split('T')[0]);
  };

  const goToNext = () => {
    const d = new Date(currentDate);
    d.setDate(d.getDate() + 1);
    setCurrentDate(d.toISOString().split('T')[0]);
  };

  const formatDate = (dateStr: string) => {
    const d = new Date(dateStr + 'T00:00:00');
    return d.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' });
  };

  const onTrack = data.filter(b => b.status === 'ON_TRACK' || b.status === 'COMPLETED').length;
  const totalBooks = data.length;
  const pct = totalBooks > 0 ? Math.round((onTrack / totalBooks) * 100) : 0;

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" />

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
            <button
              onClick={() => setCurrentDate(new Date().toISOString().split('T')[0])}
              className="bg-surface-container-lowest border border-outline-variant px-md py-sm rounded-lg font-bold text-on-surface-variant hover:border-primary transition-colors flex items-center gap-2"
            >
              <span className="material-symbols-outlined text-[18px]">calendar_today</span>
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
                    <div className="relative w-10 h-10 flex items-center justify-center">
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
                      <span className="absolute text-[10px] font-bold text-primary">{pct}%</span>
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
                          <span className="material-symbols-outlined text-on-surface-variant">book</span>
                        </div>

                        <div className="flex-grow">
                          <div className="flex justify-between items-start mb-2">
                            <div>
                              <h3 className="text-[18px] font-bold text-primary leading-tight">{book.bookName}</h3>
                            </div>
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
                            <div className="mt-2 flex items-center gap-1">
                              <span className={`text-[11px] font-semibold ${book.variancePages >= 0 ? 'text-secondary' : 'text-error'}`}>
                                {book.variancePages >= 0 ? '+' : ''}{Math.round(book.variancePages)} pages
                              </span>
                              <span className="text-[11px] text-on-surface-variant">vs plan</span>
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
              {/* Overview */}
              <div className="bg-secondary text-on-primary p-lg rounded-xl shadow-lg">
                <div className="flex justify-between items-center mb-md">
                  <h3 className="font-label-caps text-[11px] text-on-primary/70 tracking-widest">TODAY'S OVERVIEW</h3>
                  <span className="material-symbols-outlined text-[20px] text-on-primary/60">trending_up</span>
                </div>
                <div className="flex items-baseline gap-2 mb-sm">
                  <span className="text-headline-xl font-bold leading-none">{totalBooks}</span>
                  <span className="text-[16px] font-medium text-on-primary/70">active books</span>
                </div>
                <div className="w-full h-2 bg-white/10 rounded-full mb-lg border border-white/5">
                  <div className="h-full bg-white rounded-full" style={{ width: `${pct}%` }} />
                </div>
                <div className="grid grid-cols-2 gap-md">
                  <div className="bg-white/5 p-md rounded-lg border border-white/5">
                    <p className="text-[10px] font-bold text-on-primary/50 mb-1 uppercase">On Track</p>
                    <p className="text-[22px] font-bold">{onTrack}</p>
                  </div>
                  <div className="bg-white/5 p-md rounded-lg border border-white/5">
                    <p className="text-[10px] font-bold text-on-primary/50 mb-1 uppercase">Overdue</p>
                    <p className="text-[22px] font-bold">{data.filter(b => b.status === 'OVERDUE').length}</p>
                  </div>
                </div>
              </div>

              {/* Variance Summary */}
              {data.length > 0 && (
                <div className="card p-lg space-y-md">
                  <h4 className="text-h2 font-semibold">Variance Summary</h4>
                  {data.map(book => (
                    <div key={book.bookName} className="flex justify-between items-center">
                      <span className="text-body-sm text-on-surface truncate max-w-[60%]">{book.bookName}</span>
                      <span className={`text-body-sm font-bold ${book.variancePages >= 0 ? 'text-primary' : 'text-error'}`}>
                        {book.variancePages >= 0 ? '+' : ''}{Math.round(book.variancePages)}p
                      </span>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default DailyView;
