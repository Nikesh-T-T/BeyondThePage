import React, { useEffect, useRef, useState } from 'react';

interface TopBarProps {
  title: string;
  searchValue?: string;
  onSearch?: (value: string) => void;
  autoFocusSearch?: boolean;
}

const TopBar: React.FC<TopBarProps> = ({ title, searchValue, onSearch, autoFocusSearch }) => {
  const controlled = searchValue !== undefined;
  const [localValue, setLocalValue] = useState('');
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (autoFocusSearch && inputRef.current) {
      inputRef.current.focus();
      const len = inputRef.current.value.length;
      inputRef.current.setSelectionRange(len, len);
    }
  }, [autoFocusSearch]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const val = e.target.value;
    if (controlled) {
      onSearch?.(val);
    } else {
      setLocalValue(val);
      onSearch?.(val);
    }
  };

  return (
    <header className="sticky top-0 z-40 w-full bg-surface-bright border-b border-outline-variant flex items-center justify-between px-lg py-sm">
      <div className="flex items-center gap-md">
        <span className="text-h2 font-bold text-primary">{title}</span>
        {onSearch !== undefined && (
          <div className="relative hidden md:block">
            <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">search</span>
            <input
              ref={inputRef}
              className="pl-9 pr-4 py-2 bg-surface-container-low border border-outline-variant rounded-full text-body-sm focus:outline-none focus:ring-2 focus:ring-primary w-64 transition-all"
              placeholder="Search your library..."
              type="text"
              value={controlled ? searchValue : localValue}
              onChange={handleChange}
              autoComplete="off"
            />
          </div>
        )}
      </div>
    </header>
  );
};

export default TopBar;
