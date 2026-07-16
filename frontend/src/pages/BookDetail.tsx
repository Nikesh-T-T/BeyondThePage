import React, { useEffect, useRef, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getBook, uploadCover, coverUrl } from '../api';
import { BookDetail, ChapterStatus } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

const chapterDot: Record<ChapterStatus, string> = {
  COMPLETED: 'bg-primary',
  IN_PROGRESS: 'bg-primary animate-pulse',
  OVERDUE: 'bg-error',
  NOT_STARTED: 'bg-outline-variant',
};

const BookDetailPage: React.FC = () => {
  const { bookName } = useParams<{ bookName: string }>();
  const navigate = useNavigate();
  const [book, setBook] = useState<BookDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [uploadingCover, setUploadingCover] = useState(false);
  const coverInputRef = useRef<HTMLInputElement>(null);

  const decoded = decodeURIComponent(bookName ?? '');

  useEffect(() => {
    getBook(decoded)
      .then(b => {
        setBook(b);
      })
      .catch(() => navigate('/books'))
      .finally(() => setLoading(false));
  }, [decoded, navigate]);

  const handleCoverUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    setUploadingCover(true);
    try {
      await uploadCover(decoded, file);
      const updated = await getBook(decoded);
      setBook(updated);
    } catch {
      setError('Failed to upload cover. Use JPEG, PNG or WEBP under 5 MB.');
    } finally {
      setUploadingCover(false);
      if (coverInputRef.current) coverInputRef.current.value = '';
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <span className="material-symbols-outlined animate-spin text-primary text-[48px]">autorenew</span>
      </div>
    );
  }

  if (!book) return null;

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" onSearch={q => { if (q) navigate(`/books?q=${encodeURIComponent(q)}`); }} />

      <div className="max-w-container-max mx-auto p-lg md:p-xl space-y-xl">
        {/* Breadcrumb */}
        <nav className="flex items-center gap-2 text-on-surface-variant font-label-caps text-label-caps">
          <span
            className="cursor-pointer hover:text-primary transition-colors"
            onClick={() => navigate('/books')}
          >
            BOOKS
          </span>
          <span className="material-symbols-outlined text-[14px]">chevron_right</span>
          <span className="text-primary">{book.bookName.toUpperCase()}</span>
        </nav>

        {/* Hero Section */}
        <section className="grid grid-cols-1 lg:grid-cols-12 gap-lg items-start">
          <div className="lg:col-span-3 flex flex-col items-center gap-sm">
            <div className="w-full max-w-[200px] aspect-[2/3] bg-surface-container-high rounded-xl shadow-xl overflow-hidden flex items-center justify-center">
              {book.hasCoverImage
                ? <img src={coverUrl(decoded)}
                       alt={`${book.bookName} cover`}
                       className="w-full h-full object-cover" />
                : <span className="material-symbols-outlined text-[64px] text-outline-variant">book</span>}
            </div>
            <input ref={coverInputRef} id="cover-upload-detail" type="file"
              accept="image/jpeg,image/png,image/webp"
              onChange={handleCoverUpload} className="hidden" />
            <label htmlFor="cover-upload-detail"
              className="inline-flex items-center gap-xs cursor-pointer px-md py-sm border border-outline-variant rounded-lg text-body-sm text-on-surface-variant hover:bg-surface-container transition-colors w-full max-w-[200px] justify-center">
              {uploadingCover
                ? <span className="material-symbols-outlined text-[16px] animate-spin">autorenew</span>
                : <span className="material-symbols-outlined text-[16px]">upload</span>}
              <span>{uploadingCover ? 'Uploading…' : book.hasCoverImage ? 'Replace Cover' : 'Upload Cover'}</span>
            </label>
            {error && <p className="text-error text-body-sm text-center max-w-[200px]">{error}</p>}
          </div>

          <div className="lg:col-span-9 space-y-lg">
            <div className="flex flex-col md:flex-row md:items-end justify-between gap-md">
              <div>
                <h1 className="text-h1 font-bold leading-none mb-1">{book.bookName}</h1>
                {book.author && (
                  <p className="text-body-md text-on-surface-variant mb-2">by {book.author}</p>
                )}
                <p className="text-body-md text-on-surface-variant">
                  {book.category && <span className="mr-2">{book.category} ·</span>}
                  Started {book.startDate} · Target {book.targetEndDate}
                </p>
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-md">
              {/* Progress Card */}
              <div className="bg-surface-container p-lg rounded-xl border border-outline-variant">
                <div className="flex justify-between items-center mb-sm">
                  <span className="font-label-caps text-label-caps text-on-surface-variant">CURRENT PROGRESS</span>
                  <StatusBadge status={book.currentStatus} />
                </div>
                <div className="flex items-baseline gap-2 mb-sm">
                  <span className="text-h1 font-bold">{Math.round(book.completionPercentage)}%</span>
                  <span className="text-on-surface-variant text-body-sm">of {book.totalPages} pages</span>
                </div>
                <div className="w-full h-2 bg-surface-container-highest rounded-full overflow-hidden">
                  <div
                    className={`h-full rounded-full ${book.currentStatus === 'OVERDUE' ? 'bg-error' : 'bg-primary'}`}
                    style={{ width: `${book.completionPercentage}%` }}
                  />
                </div>
                <div className="mt-3 flex justify-between text-on-surface-variant text-body-sm">
                  <span>{book.completedPages} Completed</span>
                  <span>{book.remainingPages} Remaining</span>
                </div>
              </div>

              {/* Chapter Stats */}
              <div className="bg-surface-container p-lg rounded-xl border border-outline-variant">
                <span className="font-label-caps text-label-caps text-on-surface-variant block mb-4">CHAPTER STATS</span>
                <div className="flex items-center gap-4">
                  <div className="w-12 h-12 rounded-full border-4 border-primary-fixed border-t-primary flex items-center justify-center">
                    <span className="font-bold text-primary text-[12px]">
                      {book.completedChapters}/{book.chapters.length}
                    </span>
                  </div>
                  <div>
                    <p className="text-h2 font-semibold leading-none">{book.completedChapters} Done</p>
                    <p className="text-on-surface-variant text-body-sm mt-1">
                      {book.pendingChapters} pending · {book.overdueChapters} overdue
                    </p>
                  </div>
                </div>
              </div>

              {/* Deadline */}
              <div className="bg-surface-container p-lg rounded-xl border border-outline-variant">
                <span className="font-label-caps text-label-caps text-on-surface-variant block mb-4">DEADLINE</span>
                <div className="flex items-center gap-md">
                  <span className="material-symbols-outlined text-primary text-[36px]">event_available</span>
                  <div>
                    <p className="text-h2 font-semibold leading-none">{book.targetEndDate}</p>
                    <p className="text-on-surface-variant text-body-sm mt-1">
                      {book.daysRemaining > 0 ? `${book.daysRemaining} days remaining` : 'Past deadline'}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>

        {/* Chapter Table */}
        <section className="grid grid-cols-1 lg:grid-cols-12 gap-lg">
          <div className="lg:col-span-8 bg-surface-container rounded-xl border border-outline-variant overflow-hidden">
            <div className="px-lg py-md border-b border-outline-variant">
              <h3 className="text-h2 font-semibold">Chapter Progress</h3>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full text-left border-collapse">
                <thead>
                  <tr className="bg-surface-container-low">
                    <th className="px-lg py-3 font-label-caps text-label-caps text-on-surface-variant">#</th>
                    <th className="px-lg py-3 font-label-caps text-label-caps text-on-surface-variant">TITLE</th>
                    <th className="px-lg py-3 font-label-caps text-label-caps text-on-surface-variant">PAGE RANGE</th>
                    <th className="px-lg py-3 font-label-caps text-label-caps text-on-surface-variant">STATUS</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-outline-variant">
                  {book.chapters.map(ch => (
                    <tr
                      key={ch.chapterNumber}
                      className={`hover:bg-surface-bright transition-colors ${
                        ch.status === 'IN_PROGRESS' ? 'bg-surface-container-high' : ''
                      }`}
                    >
                      <td className="px-lg py-4 text-body-sm text-on-surface-variant">
                        {String(ch.chapterNumber).padStart(2, '0')}
                      </td>
                      <td className="px-lg py-4 font-semibold text-[15px]">{ch.chapterTitle}</td>
                      <td className="px-lg py-4 text-body-sm text-on-surface-variant">
                        {ch.startPage} — {ch.endPage}
                      </td>
                      <td className="px-lg py-4">
                        <span className="flex items-center gap-2 font-bold text-[11px] uppercase tracking-wider">
                          <span className={`w-2 h-2 rounded-full ${chapterDot[ch.status]}`} />
                          {ch.status.replace('_', ' ')}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          {/* Side Panel */}
          <div className="lg:col-span-4 space-y-lg">
            <div className="bg-primary-container p-lg rounded-xl space-y-md">
              <h4 className="text-h2 font-semibold text-on-primary-container">Reading Plan</h4>
              <div className="space-y-sm">
                <div className="flex justify-between text-body-sm text-on-primary-container">
                  <span>Daily Target</span>
                  <span className="font-bold">{book.plannedDailyPages.toFixed(1)} pages/day</span>
                </div>
                <div className="flex justify-between text-body-sm text-on-primary-container">
                  <span>Expected by Today</span>
                  <span className="font-bold">{Math.round(book.expectedPagesByToday)} pages</span>
                </div>
                <div className="flex justify-between text-body-sm text-on-primary-container">
                  <span>Variance</span>
                  <span className={`font-bold ${book.variancePages >= 0 ? '' : 'text-error'}`}>
                    {book.variancePages >= 0 ? '+' : ''}{Math.round(book.variancePages)} pages
                  </span>
                </div>
              </div>
            </div>

            <div className="bg-surface-container p-lg rounded-xl border border-outline-variant space-y-md">
              <h4 className="text-h2 font-semibold">Timeline</h4>
              <div className="space-y-sm">
                <div className="flex justify-between text-body-sm">
                  <span className="text-on-surface-variant">Start Date</span>
                  <span className="font-semibold">{book.startDate}</span>
                </div>
                <div className="flex justify-between text-body-sm">
                  <span className="text-on-surface-variant">Target End</span>
                  <span className="font-semibold">{book.targetEndDate}</span>
                </div>
                <div className="flex justify-between text-body-sm">
                  <span className="text-on-surface-variant">Days Elapsed</span>
                  <span className="font-semibold">{book.daysElapsed}</span>
                </div>
                <div className="flex justify-between text-body-sm">
                  <span className="text-on-surface-variant">Days Remaining</span>
                  <span className="font-semibold">{book.daysRemaining}</span>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
};

export default BookDetailPage;
