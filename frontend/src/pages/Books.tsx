import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { getBooks } from '../api';
import { BookSummary } from '../types';
import TopBar from '../components/TopBar';
import StatusBadge from '../components/StatusBadge';

const Books: React.FC = () => {
  const [books, setBooks] = useState<BookSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchParams, setSearchParams] = useSearchParams();
  const [searchInput, setSearchInput] = useState(() => searchParams.get('q') ?? '');
  const [searchQuery, setSearchQuery] = useState(() => searchParams.get('q') ?? '');
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      setSearchQuery(searchInput);
      if (searchInput) {
        setSearchParams({ q: searchInput }, { replace: true });
      } else {
        setSearchParams({}, { replace: true });
      }
    }, 300);
    return () => clearTimeout(timer);
  }, [searchInput, setSearchParams]);

  useEffect(() => {
    setLoading(true);
    getBooks(searchQuery || undefined)
      .then(setBooks)
      .finally(() => setLoading(false));
  }, [searchQuery]);

  if (loading && !searchQuery) {
    return (
      <div className="flex items-center justify-center h-screen">
        <span className="material-symbols-outlined animate-spin text-primary text-[48px]">autorenew</span>
      </div>
    );
  }

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" searchValue={searchInput} onSearch={setSearchInput} autoFocusSearch={!!searchParams.get('q')} />

      <div className="max-w-container-max mx-auto p-lg space-y-xl">
        <div className="flex justify-between items-center">
          <h2 className="text-h1 font-bold text-primary">
            {searchQuery ? `Results for "${searchQuery}"` : 'All Books'}
          </h2>
          <button
            onClick={() => navigate('/books/new')}
            className="flex items-center gap-2 px-lg py-sm bg-primary text-on-primary rounded-lg font-bold hover:opacity-90 transition-opacity"
          >
            <span className="material-symbols-outlined text-[20px]">add</span>
            Add Book
          </button>
        </div>

        {loading ? (
          <div className="flex justify-center py-xl">
            <span className="material-symbols-outlined animate-spin text-primary text-[48px]">autorenew</span>
          </div>
        ) : books.length === 0 ? (
          <div className="card p-xl text-center">
            <span className="material-symbols-outlined text-[64px] text-outline-variant mb-md block">library_books</span>
            <p className="text-on-surface-variant text-body-md mb-md">
              {searchQuery ? `No books found matching "${searchQuery}".` : 'Your library is empty.'}
            </p>
            {!searchQuery && (
              <button
                onClick={() => navigate('/books/new')}
                className="px-lg py-sm bg-primary text-on-primary rounded-lg font-bold hover:opacity-90 transition-opacity"
              >
                Add Your First Book
              </button>
            )}
          </div>
        ) : (
          <div className="space-y-md">
            {books.map(book => (
              <div
                key={book.bookName}
                className="card p-lg flex flex-col md:flex-row md:items-center gap-lg cursor-pointer"
                onClick={() => navigate(`/books/${encodeURIComponent(book.bookName)}`)}
              >
                <div className="w-12 h-16 bg-surface-container-high rounded shadow-sm flex-shrink-0 overflow-hidden flex items-center justify-center">
                  {book.hasCoverImage
                    ? <img src={`/api/books/${encodeURIComponent(book.bookName)}/cover`}
                           alt={`${book.bookName} cover`}
                           className="w-full h-full object-cover" />
                    : <span className="material-symbols-outlined text-on-surface-variant">book</span>}
                </div>

                <div className="flex-grow space-y-xs">
                  <h4 className="text-[18px] font-semibold leading-tight text-on-surface">{book.bookName}</h4>
                  <p className="text-body-sm text-on-surface-variant">
                    Started {book.startDate} · Target {book.targetEndDate}
                  </p>
                  <div className="space-y-xs pt-xs">
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

                <div className="flex flex-col items-end gap-sm min-w-[150px]">
                  <StatusBadge status={book.currentStatus} />
                  <span className="font-mono text-[12px] text-on-surface-variant">
                    {book.completedPages} / {book.totalPages} pgs
                  </span>
                  <span className="text-body-sm text-on-surface-variant">
                    {book.daysRemaining} days left
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Books;
