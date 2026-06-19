import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createBook, uploadCover } from '../api';
import TopBar from '../components/TopBar';

interface ChapterRow {
  chapterNumber: number;
  chapterTitle: string;
  startPage: string;
  endPage: string;
}

const AddBook: React.FC = () => {
  const navigate = useNavigate();
  const [bookName, setBookName] = useState('');
  const [totalPages, setTotalPages] = useState('');
  const [plannedDays, setPlannedDays] = useState('');
  const [startDate, setStartDate] = useState(new Date().toISOString().split('T')[0]);
  const [chapters, setChapters] = useState<ChapterRow[]>([
    { chapterNumber: 1, chapterTitle: '', startPage: '1', endPage: '' },
  ]);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const [coverFile, setCoverFile] = useState<File | null>(null);
  const [coverPreview, setCoverPreview] = useState<string | null>(null);

  useEffect(() => {
    return () => { if (coverPreview) URL.revokeObjectURL(coverPreview); };
  }, [coverPreview]);

  const handleCoverChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] ?? null;
    setCoverFile(file);
    setCoverPreview(file ? URL.createObjectURL(file) : null);
  };

  const addChapter = () => {
    const prev = chapters[chapters.length - 1];
    const nextStart = prev.endPage ? String(parseInt(prev.endPage, 10) + 1) : '';
    setChapters(c => [
      ...c,
      { chapterNumber: c.length + 1, chapterTitle: '', startPage: nextStart, endPage: '' },
    ]);
  };

  const removeChapter = (idx: number) => {
    setChapters(c => c.filter((_, i) => i !== idx).map((ch, i) => ({ ...ch, chapterNumber: i + 1 })));
  };

  const updateChapter = (idx: number, field: keyof ChapterRow, value: string) => {
    setChapters(c => c.map((ch, i) => (i === idx ? { ...ch, [field]: value } : ch)));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSaving(true);
    try {
      const created = await createBook({
        bookName,
        totalPages: parseInt(totalPages, 10),
        plannedDays: parseInt(plannedDays, 10),
        startDate,
        chapters: chapters.map(ch => ({
          chapterNumber: ch.chapterNumber,
          chapterTitle: ch.chapterTitle,
          startPage: parseInt(ch.startPage, 10),
          endPage: parseInt(ch.endPage, 10),
        })),
      });
      if (coverFile) await uploadCover(created.bookName, coverFile);
      navigate('/books');
    } catch (err: any) {
      const msg = err?.response?.data?.message ?? 'Failed to create book. Check your input and try again.';
      setError(msg);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="min-h-screen">
      <TopBar title="Beyond The Page" />

      <div className="max-w-[720px] mx-auto p-lg md:p-xl space-y-xl">
        <div className="flex items-center gap-md">
          <button
            onClick={() => navigate('/books')}
            className="text-on-surface-variant hover:text-on-surface transition-colors"
          >
            <span className="material-symbols-outlined">arrow_back</span>
          </button>
          <h1 className="text-h1 font-bold text-primary">Add New Book</h1>
        </div>

        <form onSubmit={handleSubmit} className="space-y-xl">
          {/* Book Details */}
          <div className="card p-lg space-y-md">
            <h3 className="text-h2 font-semibold">Book Details</h3>

            <div className="space-y-xs">
              <label className="block font-label-caps text-label-caps text-on-surface-variant">BOOK TITLE</label>
              <input
                type="text"
                required
                value={bookName}
                onChange={e => setBookName(e.target.value)}
                placeholder="e.g. Clean Code"
                className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-md">
              <div className="space-y-xs">
                <label className="block font-label-caps text-label-caps text-on-surface-variant">TOTAL PAGES</label>
                <input
                  type="number"
                  required
                  min={1}
                  value={totalPages}
                  onChange={e => setTotalPages(e.target.value)}
                  placeholder="e.g. 431"
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md focus:outline-none focus:ring-2 focus:ring-primary"
                />
              </div>

              <div className="space-y-xs">
                <label className="block font-label-caps text-label-caps text-on-surface-variant">PLANNED DAYS</label>
                <input
                  type="number"
                  required
                  min={1}
                  value={plannedDays}
                  onChange={e => setPlannedDays(e.target.value)}
                  placeholder="e.g. 30"
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md focus:outline-none focus:ring-2 focus:ring-primary"
                />
              </div>

              <div className="space-y-xs">
                <label className="block font-label-caps text-label-caps text-on-surface-variant">START DATE</label>
                <input
                  type="date"
                  required
                  value={startDate}
                  onChange={e => setStartDate(e.target.value)}
                  className="w-full bg-surface-container-low border border-outline-variant rounded-lg px-md py-sm text-body-md focus:outline-none focus:ring-2 focus:ring-primary"
                />
              </div>
            </div>

            <div className="space-y-xs">
              <label className="font-label-caps text-label-caps text-on-surface-variant block">COVER IMAGE (OPTIONAL)</label>
              <div className="flex items-center gap-md">
                <div className="w-16 h-24 rounded-lg bg-surface-container-high overflow-hidden flex-shrink-0 flex items-center justify-center">
                  {coverPreview
                    ? <img src={coverPreview} alt="Preview" className="w-full h-full object-cover" />
                    : <span className="material-symbols-outlined text-outline-variant">book</span>}
                </div>
                <div className="space-y-xs">
                  <input id="cover-upload" type="file"
                    accept="image/jpeg,image/png,image/webp"
                    onChange={handleCoverChange} className="hidden" />
                  <label htmlFor="cover-upload"
                    className="inline-flex items-center gap-xs cursor-pointer px-md py-sm border border-outline-variant rounded-lg text-body-sm hover:bg-surface-container transition-colors">
                    <span className="material-symbols-outlined text-[18px]">upload</span>
                    {coverFile ? coverFile.name : 'Choose image'}
                  </label>
                  {coverFile && (
                    <button type="button"
                      onClick={() => { setCoverFile(null); setCoverPreview(null); }}
                      className="block text-body-sm text-on-surface-variant hover:text-error transition-colors">
                      Remove
                    </button>
                  )}
                  <p className="text-body-sm text-on-surface-variant">JPEG · PNG · WEBP · max 5 MB</p>
                </div>
              </div>
            </div>
          </div>

          {/* Chapters */}
          <div className="card p-lg space-y-md">
            <div className="flex justify-between items-center">
              <h3 className="text-h2 font-semibold">Chapters</h3>
              <button
                type="button"
                onClick={addChapter}
                className="flex items-center gap-xs text-primary font-label-caps text-label-caps hover:underline"
              >
                <span className="material-symbols-outlined text-[18px]">add</span>
                ADD CHAPTER
              </button>
            </div>

            <p className="text-body-sm text-on-surface-variant">
              First chapter must start at page 1. Last chapter must end at total pages.
            </p>

            <div className="space-y-md">
              {chapters.map((ch, idx) => (
                <div key={idx} className="bg-surface-container-low rounded-lg p-md border border-outline-variant space-y-sm">
                  <div className="flex justify-between items-center">
                    <span className="font-label-caps text-label-caps text-on-surface-variant">CHAPTER {ch.chapterNumber}</span>
                    {chapters.length > 1 && (
                      <button
                        type="button"
                        onClick={() => removeChapter(idx)}
                        className="text-on-surface-variant hover:text-error transition-colors"
                      >
                        <span className="material-symbols-outlined text-[18px]">delete</span>
                      </button>
                    )}
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-3 gap-sm">
                    <div className="md:col-span-1 space-y-xs">
                      <label className="block font-label-caps text-[10px] text-on-surface-variant">TITLE</label>
                      <input
                        type="text"
                        required
                        value={ch.chapterTitle}
                        onChange={e => updateChapter(idx, 'chapterTitle', e.target.value)}
                        placeholder="Chapter title"
                        className="w-full bg-surface-container-lowest border border-outline-variant rounded px-sm py-xs text-body-sm focus:outline-none focus:ring-1 focus:ring-primary"
                      />
                    </div>
                    <div className="space-y-xs">
                      <label className="block font-label-caps text-[10px] text-on-surface-variant">START PAGE</label>
                      <input
                        type="number"
                        required
                        min={1}
                        value={ch.startPage}
                        onChange={e => updateChapter(idx, 'startPage', e.target.value)}
                        className="w-full bg-surface-container-lowest border border-outline-variant rounded px-sm py-xs text-body-sm focus:outline-none focus:ring-1 focus:ring-primary"
                      />
                    </div>
                    <div className="space-y-xs">
                      <label className="block font-label-caps text-[10px] text-on-surface-variant">END PAGE</label>
                      <input
                        type="number"
                        required
                        min={1}
                        value={ch.endPage}
                        onChange={e => updateChapter(idx, 'endPage', e.target.value)}
                        className="w-full bg-surface-container-lowest border border-outline-variant rounded px-sm py-xs text-body-sm focus:outline-none focus:ring-1 focus:ring-primary"
                      />
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {error && (
            <div className="bg-error-container text-on-error-container rounded-lg p-md text-body-sm">
              {error}
            </div>
          )}

          <div className="flex gap-sm">
            <button
              type="button"
              onClick={() => navigate('/books')}
              className="flex-1 py-md border border-outline-variant rounded-lg font-bold text-on-surface-variant hover:bg-surface-container transition-colors"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={saving}
              className="flex-1 py-md bg-primary text-on-primary rounded-lg font-bold hover:opacity-90 transition-opacity disabled:opacity-50"
            >
              {saving ? 'Adding Book…' : 'Add Book'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddBook;
