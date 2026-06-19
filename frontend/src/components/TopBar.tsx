import React from 'react';

interface TopBarProps {
  title: string;
}

const TopBar: React.FC<TopBarProps> = ({ title }) => (
  <header className="sticky top-0 z-40 w-full bg-surface-bright border-b border-outline-variant flex items-center justify-between px-lg py-sm">
    <div className="flex items-center gap-md">
      <span className="text-h2 font-bold text-primary">{title}</span>
      <div className="relative hidden md:block">
        <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-[18px]">search</span>
        <input
          className="pl-9 pr-4 py-2 bg-surface-container-low border border-outline-variant rounded-full text-body-sm focus:outline-none focus:ring-2 focus:ring-primary w-64 transition-all"
          placeholder="Search your library..."
          type="text"
        />
      </div>
    </div>
  </header>
);

export default TopBar;
