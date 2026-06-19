import React, { useEffect, useState, useCallback } from 'react';
import { getWeeklyDashboard } from '../api';
import { WeeklyDashboard } from '../types';
import TopBar from '../components/TopBar';

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

  const pagesPct = data && data.totalPlannedPages > 0
    ? Math.round((data.totalCompletedPages / data.totalPlannedPages) * 100)
    : 0;

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" />

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
              {/* Main Progress Card */}
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

              {/* Snapshot Stats */}
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

            {/* Detailed Breakdown */}
            <section className="grid grid-cols-1 lg:grid-cols-3 gap-xl">
              <div className="lg:col-span-2 space-y-lg">
                <h3 className="text-h2 font-semibold">Chapter Breakdown</h3>

                <div className="grid grid-cols-2 md:grid-cols-4 gap-md">
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
                </div>

                {/* Pages Progress */}
                <div className="card p-lg">
                  <div className="flex justify-between items-center mb-md">
                    <h4 className="text-h2 font-semibold">Pages Progress</h4>
                    <span className="font-label-caps text-label-caps text-primary">{pagesPct}%</span>
                  </div>
                  <div className="w-full h-3 bg-surface-container-highest rounded-full overflow-hidden">
                    <div
                      className="h-full bg-primary rounded-full transition-all duration-700"
                      style={{ width: `${pagesPct}%` }}
                    />
                  </div>
                  <div className="flex justify-between mt-2 text-body-sm text-on-surface-variant">
                    <span>{data.totalCompletedPages} completed</span>
                    <span>{data.totalPlannedPages} planned</span>
                  </div>
                </div>
              </div>

              {/* Side summary */}
              <div className="space-y-lg">
                <h3 className="text-h2 font-semibold">Week at a Glance</h3>
                <div className="bg-primary-fixed p-lg rounded-xl text-on-primary-fixed space-y-md">
                  <div className="flex items-center gap-md">
                    <span className="material-symbols-outlined">event_note</span>
                    <div>
                      <p className="font-label-caps text-label-caps opacity-80">WEEK DATES</p>
                      <p className="font-semibold">{data.weekStartDate} → {data.weekEndDate}</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-md">
                    <span className="material-symbols-outlined">library_books</span>
                    <div>
                      <p className="font-label-caps text-label-caps opacity-80">COMPLETION RATE</p>
                      <p className="font-semibold">{completionPct}% of chapters done</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-md">
                    <span className="material-symbols-outlined">import_contacts</span>
                    <div>
                      <p className="font-label-caps text-label-caps opacity-80">PAGES COMPLETED</p>
                      <p className="font-semibold">{data.totalCompletedPages} of {data.totalPlannedPages}</p>
                    </div>
                  </div>
                </div>
              </div>
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
