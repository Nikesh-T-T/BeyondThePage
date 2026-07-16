import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getWeeklyDashboard } from '../api';
import { WeeklyDashboard } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

const addDays = (dateStr: string, n: number) => {
  const [y, m, d] = dateStr.split('-').map(Number);
  const date = new Date(y, m - 1, d + n);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
};

const todayStr = () => {
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`;
};

const formatDisplay = (dateStr: string) => {
  const d = new Date(dateStr + 'T00:00:00');
  return d.toLocaleDateString('en-US', { month: 'long', day: 'numeric' });
};

const WeeklyView: React.FC = () => {
  const navigate = useNavigate();
  const [currentDate, setCurrentDate] = useState(todayStr);
  const [data, setData] = useState<WeeklyDashboard | null>(null);
  const [loading, setLoading] = useState(true);

  const load = useCallback((date: string) => {
    setLoading(true);
    getWeeklyDashboard(date)
      .then(setData)
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    load(currentDate);
  }, [currentDate, load]);

  const goToPrev = () => data && setCurrentDate(addDays(data.weekStartDate, -1));
  const goToNext = () => data && setCurrentDate(addDays(data.weekEndDate, 1));
  const goToToday = () => setCurrentDate(todayStr());

  const completionPct = data && data.chaptersPlanned > 0
    ? Math.round((data.chaptersCompleted / data.chaptersPlanned) * 100)
    : 0;

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" onSearch={q => { if (q) navigate(`/books?q=${encodeURIComponent(q)}`); }} />

      <div className="max-w-container-max mx-auto p-lg md:p-xl space-y-xl">
        {/* Header */}
        <section className="flex flex-col md:flex-row md:items-end justify-between gap-md">
          <div>
            <p className="font-label-caps text-label-caps text-secondary mb-1">WEEKLY SUMMARY</p>
            <h2 className="text-h1 font-bold text-on-surface">
              {data
                ? `${formatDisplay(data.weekStartDate)} – ${formatDisplay(data.weekEndDate)}`
                : 'Loading…'}
            </h2>
          </div>
          <div className="flex items-center gap-2">
            <button
              onClick={goToPrev}
              className="bg-surface-container-lowest border border-outline-variant px-md py-sm rounded-lg font-bold text-on-surface-variant hover:border-primary transition-colors flex items-center"
            >
              <span className="material-symbols-outlined">chevron_left</span>
            </button>
            <div className="bg-surface-container-lowest border border-outline-variant px-lg py-sm rounded-lg flex items-center gap-2 min-w-[240px] justify-center">
              <span className="material-symbols-outlined text-[18px] text-on-surface-variant">date_range</span>
              <span className="text-body-sm font-semibold text-on-surface">
                {data
                  ? `${formatDisplay(data.weekStartDate)} – ${formatDisplay(data.weekEndDate)}`
                  : '…'}
              </span>
            </div>
            <button
              onClick={goToToday}
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
        ) : data ? (
          <>
            {/* Stats Bento Grid */}
            <section className="grid grid-cols-1 md:grid-cols-12 gap-lg">
              <div className="md:col-span-8 card p-lg flex flex-col justify-between min-h-[200px]">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="text-h2 font-semibold mb-1">Weekly Chapter Target</h3>
                    <p className="text-body-sm text-on-surface-variant">
                      {completionPct}% of your weekly goal completed
                    </p>
                  </div>
                  <div className="text-right">
                    <span className="text-headline-xl font-bold text-primary leading-none">{data.chaptersCompleted}</span>
                    <span className="text-h2 text-on-surface-variant">/ {data.chaptersPlanned}</span>
                    <p className="font-label-caps text-label-caps text-on-surface-variant">CHAPTERS</p>
                  </div>
                </div>
                <div className="mt-lg">
                  <div className="w-full h-4 bg-surface-container-highest rounded-full overflow-hidden">
                    <div
                      className="h-full bg-primary rounded-full transition-all duration-700"
                      style={{ width: `${completionPct}%` }}
                    />
                  </div>
                  <div className="flex justify-between mt-2">
                    <span className="font-label-caps text-label-caps text-on-surface-variant">
                      {formatDisplay(data.weekStartDate).toUpperCase()}
                    </span>
                    <span className="font-label-caps text-label-caps text-primary">
                      {formatDisplay(data.weekEndDate).toUpperCase()}
                    </span>
                  </div>
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
                      {data.totalCompletedPages} / {data.totalPlannedPages}
                    </p>
                  </div>
                </div>

                <div className="card p-lg flex items-center gap-lg">
                  <div className="w-12 h-12 rounded-xl bg-secondary-container flex items-center justify-center text-on-secondary-container">
                    <span className="material-symbols-outlined">priority_high</span>
                  </div>
                  <div>
                    <p className="font-label-caps text-label-caps text-on-surface-variant">OVERDUE</p>
                    <p className={`text-h2 font-semibold ${data.chaptersOverdue > 0 ? 'text-error' : ''}`}>
                      {data.chaptersOverdue} chapters
                    </p>
                  </div>
                </div>
              </div>
            </section>

            {/* Chapter Breakdown + Pages Progress */}
            <section className="grid grid-cols-2 md:grid-cols-4 gap-md">
              {[
                { label: 'PLANNED', value: data.chaptersPlanned, color: 'text-on-surface' },
                { label: 'COMPLETED', value: data.chaptersCompleted, color: 'text-primary' },
                { label: 'NOT STARTED', value: data.chaptersNotStarted, color: 'text-on-surface-variant' },
                { label: 'OVERDUE', value: data.chaptersOverdue, color: 'text-error' },
              ].map(stat => (
                <div key={stat.label} className="card p-md text-center">
                  <p className="font-label-caps text-[10px] text-on-surface-variant mb-xs">{stat.label}</p>
                  <p className={`text-headline-xl font-bold ${stat.color}`}>{stat.value}</p>
                </div>
              ))}
            </section>

            {/* This Week's Chapters */}
            <section className="space-y-lg">
              <h3 className="text-h2 font-semibold text-on-surface">This Week's Chapters</h3>
              {data.books.length === 0 ? (
                <div className="card p-xl text-center">
                  <p className="text-on-surface-variant">No chapters planned for this week.</p>
                </div>
              ) : (
                <div className="card overflow-hidden">
                  <table className="w-full text-body-sm border-collapse">
                    <thead>
                      <tr className="bg-surface-container-high border-b border-outline-variant">
                        <th className="px-md py-sm text-left font-label-caps text-label-caps text-on-surface-variant">Book</th>
                        <th className="px-md py-sm text-left font-label-caps text-label-caps text-on-surface-variant">Chapter</th>
                        <th className="px-md py-sm text-right font-label-caps text-label-caps text-on-surface-variant">Pages</th>
                        <th className="px-md py-sm text-center font-label-caps text-label-caps text-on-surface-variant">Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {data.books.map(book => {
                        return book.chapters.map((ch, i) => (
                          <tr
                            key={`${book.bookName}-${ch.chapterNumber}`}
                            className={`border-b border-outline-variant cursor-pointer transition-colors hover:bg-surface-container-high ${
                              i === 0 && data.books.indexOf(book) !== 0 ? 'border-t-2 border-t-outline' : ''
                            }`}
                            onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
                          >
                            <td className="px-md py-sm text-on-surface-variant text-[12px] whitespace-nowrap">
                              {i === 0 ? book.bookName : ''}
                            </td>
                            <td className="px-md py-sm text-on-surface font-medium">{ch.chapterTitle}</td>
                            <td className="px-md py-sm text-right font-mono text-[12px] text-on-surface-variant whitespace-nowrap">
                              {ch.startPage}–{ch.endPage}
                            </td>
                            <td className="px-md py-sm text-center">
                              <StatusBadge status={ch.status} />
                            </td>
                          </tr>
                        ));
                      })}
                    </tbody>
                  </table>
                </div>
              )}
            </section>
          </>
        ) : (
          <div className="card p-xl text-center">
            <p className="text-on-surface-variant">No data available for this week.</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default WeeklyView;
